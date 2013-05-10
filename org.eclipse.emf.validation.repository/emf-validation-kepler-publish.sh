#!/bin/sh
# Copyright (c) 2012, 2013 IBM Corporation and others.
# All rights reserved.   This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Contributors:
#   IBM - Initial API and implementation

# Script may take 5-6 command line parameters:
# $1: Hudson job name: <name>
# $2: Hudson build id: <id>
# $3: Build type: n(ightly), m(aintenance), s(table), r(elease)
# $4: Whether to merge the site with an existing one: (y)es, (n)o
# $5: Whether to generate udpate-site and SDK drop files: (y)es, (n)o
# $6: An optional label to append to the version string when creating drop files, e.g. M5 or RC1
# 
if [ $# -eq 5 -o $# -eq 6 ]; then
	jobName=$1
	buildId=$2
	buildType=$3
	merge=$4
	dropFiles=$5
	if [ -n "$6" ]; then
		dropFilesLabel=$6
	fi
else
	if [ $# -ne 0 ]; then
		exit 1
	fi
fi

if [ -z "$jobName" ]; then
	echo -n "Please enter the name of the Hudson job you want to promote:"
	read jobName
fi

if [ -z "$buildId" ]; then
	for i in $( find /shared/jobs/$jobName/builds -type l | sed 's!.*/!!' | sort); do
		echo -n "$i, "
	done
	echo "lastStable, lastSuccessful"
	echo -n "Please enter the id/label of the Hudson build you want to promote:"
	read buildId
fi
if [ -z "$buildId" ]; then
	exit 0
fi

# Determine the local update site we want to publish to
if [ "$buildId" = "lastStable" -o "$buildId" = "lastSuccessful" ]; then
	jobDir=$(readlink -f /shared/jobs/$jobName/$buildId)
else
	jobDir=$(readlink -f /shared/jobs/$jobName/builds/$buildId)
fi
localUpdateSite=$jobDir/archive/update-site
echo "Using local update-site: $localUpdateSite"

# Reverse lookup the build id (in case lastSuccessful or lastStable was used)
for i in $(find /shared/jobs/$jobName/builds/ -type l); do
	if [ "$(readlink -f $i)" =  "$jobDir" ]; then
		buildId=${i##*/}
	fi
done
echo "Reverse lookup of build id yielded: $buildId"

# Select the build type
if [ -z "$buildType" ]; then
	echo -n "Please select which type of build you want to publish to [i(ntegration), m(aintenance), s(table), r(elease)]: "
	read buildType
fi
echo "Publishing as $buildType build"

# Determine remote update site we want to promote to (integration and maintenance are published on interim site, stable builds on milestone site, release builds on releases site)
case $buildType in
	m|M) remoteSite=maintenance ;;
	i|I) remoteSite=interim ;;
	s|S) remoteSite=milestones ;;
	r|R) remoteSite=releases ;;
	*) exit 0 ;;
esac
remoteUpdateSiteBase="modeling/emf/validation/updates/$remoteSite"
remoteUpdateSite="/home/data/httpd/download.eclipse.org/$remoteUpdateSiteBase"
echo "Publishing to remote update-site: $remoteUpdateSite"

if [ -d "$remoteUpdateSite" ]; then
	if [ -z "$merge" ]; then
		echo -n "Do you want to merge with the existing update-site? [(y)es, (n)o]:"
		read merge
	fi
	if [ "$merge" != y -a "$merge" != n ]; then
		exit 0
	fi
else
	merge=n
fi
echo "Merging with existing site: $merge"

if [ -z "$dropFiles" ]; then
	echo -n "Do you want to create update-site and SDK drop files? [(y)es, (n)o]:"
	read dropFiles
fi
if [ "$dropFiles" != y -a "$dropFiles" != n ]; then
	exit 0
fi
echo "Generating update-site and SDK drop files: $dropFiles"

if [ -z "$dropFilesLabel" -a "$dropFiles" = y ]; then
	echo -n "Please enter a drop files label to append to the version (e.g. M5, RC1) or leave empty to skip this [<empty>]:"
	read dropFilesLabel
fi

# Prepare a temp directory
tmpDir="$jobName-publish-tmp"
rm -fr $tmpDir
mkdir -p $tmpDir/update-site
cd $tmpDir

# Download and prepare Eclipse SDK, which is needed to merge update site and postprocess repository 
echo "Downloading eclipse to $PWD"
cp /home/data/httpd/download.eclipse.org/eclipse/downloads/drops4/R-4.2.2-201302041200/eclipse-SDK-4.2.2-linux-gtk-x86_64.tar.gz .
tar -xzf eclipse-SDK-4.2.2-linux-gtk-x86_64.tar.gz
cd eclipse
chmod 700 eclipse
cd ..
if [ ! -d "eclipse" ]; then
	echo "Failed to download an Eclipse SDK, being needed for provisioning."
	exit
fi
# Prepare Eclipse SDK to provide WTP releng tools (used to postprocess repository, i.e set p2.mirrorsURL property)
echo "Installing WTP Releng tools"
./eclipse/eclipse -nosplash --launcher.suppressErrors -clean -debug -application org.eclipse.equinox.p2.director -repository http://download.eclipse.org/webtools/releng/repository/ -installIUs org.eclipse.wtp.releng.tools.feature.feature.group
# Clean up
echo "Cleaning up"
rm eclipse-SDK-4.2.2-linux-gtk-x86_64.tar.gz

# Prepare local update site (merging is performed later, if required)
cp -R $localUpdateSite/* update-site/
echo "Copied $localUpdateSite to local directory update-site."

# Generate drop files
if [ "$dropFiles" = y ]; then
	echo "Converting update site to runnable form"
	./eclipse/eclipse -nosplash -consoleLog -application org.eclipse.equinox.p2.repository.repo2runnable -source file:update-site -destination file:drops/eclipse
	qualifiedVersion=$(find drops/eclipse/features/ -maxdepth 1 | grep "org.eclipse.gmf_")
	echo "qualifiedVersion is $qualifiedVersion"
	qualifiedVersion=${qualifiedVersion#*_}
	echo "qualifiedVersion is $qualifiedVersion"
	qualifier=${qualifiedVersion##*.}
	echo "qualifier is $qualifier"
	version=${qualifiedVersion%.*}
	echo "version is $version"
	dropDir="$version/$(echo $buildType | tr '[:lower:]' '[:upper:]')$qualifier"
	echo "dropDir is $dropDir"
	localDropDir=drops/$dropDir
	echo "Creating drop files in local directory $localDropDir"
	mkdir -p $localDropDir

	cp eclipse/epl-v10.html drops/eclipse
	cp eclipse/notice.html drops/eclipse
	cd drops
				
	# Append drop file suffix if one is specified				
	if [ -n "$dropFilesLabel" ]; then
		version=$version$dropFilesLabel
		echo "version is now $version"
	else
		version="$(echo $buildType | tr '[:lower:]' '[:upper:]')$qualifier"
		echo "version is now $version"
	fi
				
	# emf validation SDK
	zip -r $dropDir/emf-validation-SDK-$version.zip \
		eclipse/epl-v10.html eclipse/notice.html \
		eclipse/features/org.eclipse.emf.validation.doc_* \
		eclipse/features/org.eclipse.emf.validation.ocl.source_* \
		eclipse/features/org.eclipse.emf.validation.sdk_* \
		eclipse/features/org.eclipse.emf.validation.source_* \
		eclipse/features/org.eclipse.emf.validation_* \
		eclipse/features/org.eclipse.emf.validation.ocl_* \
		eclipse/plugins/org.eclipse.emf.validation.doc_* \
		eclipse/plugins/org.eclipse.emf.validation_* \
		eclipse/plugins/org.eclipse.emf.validation.ocl_* \
		eclipse/plugins/org.eclipse.emf.validation.ui.ide_* \
		eclipse/plugins/org.eclipse.emf.validation.ui_*
		eclipse/plugins/org.eclipse.emf.validation.source_* \
		eclipse/plugins/org.eclipse.emf.validation.ocl.source_* \
		eclipse/plugins/org.eclipse.emf.validation.ui.ide.source_* \
		eclipse/plugins/org.eclipse.emf.validation.ui.source_*
	md5sum $dropDir/emf-validation-SDK-$version.zip > $dropDir/emf-validation-SDK-$version.zip.md5
	echo "Created emf-validation-SDK-$version.zip"
				
	# emf-validation runtime
	zip -r $dropDir/emf-validation-runtime-$version.zip \
		eclipse/epl-v10.html eclipse/notice.html \
		eclipse/features/org.eclipse.emf.validation_* \
		eclipse/features/org.eclipse.emf.validation.ocl_* \
		eclipse/plugins/org.eclipse.emf.validation_* \
		eclipse/plugins/org.eclipse.emf.validation.ocl_* \
		eclipse/plugins/org.eclipse.emf.validation.ui.ide_* \
		eclipse/plugins/org.eclipse.emf.validation.ui_*
	md5sum $dropDir/emf-validation-runtime-$version.zip > $dropDir/emf-validation-runtime-$version.zip.md5
	echo "Created emf-validation-runtime-$version.zip"
				
	# emf-validation examples
	zip -r $dropDir/emf-validation-examples-$version.zip \
		eclipse/epl-v10.html eclipse/notice.html \
		eclipse/features/org.eclipse.emf.validation.examples_* \
		eclipse/features/org.eclipse.emf.validation.examples.source_* \
		eclipse/plugins/org.eclipse.emf.validation.examples_* \
		eclipse/plugins/org.eclipse.emf.validation.examples.adapter_* \
		eclipse/plugins/org.eclipse.emf.validation.examples.general_* \
		eclipse/plugins/org.eclipse.emf.validation.examples.ocl_* \
		eclipse/plugins/org.eclipse.emf.validation.examples.source_* \
		eclipse/plugins/org.eclipse.emf.validation.examples.adapter.source_* \
		eclipse/plugins/org.eclipse.emf.validation.examples.general.source_* \
		eclipse/plugins/org.eclipse.emf.validation.examples.ocl.source_*
	md5sum $dropDir/emf-validation-examples-$version.zip > $dropDir/emf-validation-examples-$version.zip.md5
	echo "Created emf-validation-examples-$version.zip"
				
	# emf-validation automated-tests
	zip -r $dropDir/emf-validation-automated-tests-$version.zip \
		eclipse/epl-v10.html eclipse/notice.html \
		eclipse/features/org.eclipse.emf.validation.tests_* \
		eclipse/plugins/org.eclipse.emf.validation.tests_*
	md5sum $dropDir/emf-validation-automated-tests-$version.zip > $dropDir/emf-validation-automated-tests-$version.zip.md5
	echo "Created emf-validation-automated-tests-$version.zip"
				
	cd ../update-site

	zip -r ../$localDropDir/emf-validation-Update-$version.zip features plugins artifacts.jar content.jar
	md5sum ../$localDropDir/emf-validation-Update-$version.zip > ../$localDropDir/emf-validation-Update-$version.zip.md5
	echo "Created emf-validation-Update-Site-$version.zip"
	cd ..

	#generating build.cfg file to be referenced from downloads web page
	echo "hudson.job.name=$jobName" > $localDropDir/build.cfg
	echo "hudson.job.id=$buildId (${jobDir##*/})" >> $localDropDir/build.cfg
	echo "hudson.job.url=https://hudson.eclipse.org/hudson/job/$jobName/$buildId" >> $localDropDir/build.cfg

	remoteDropDir=/home/data/httpd/download.eclipse.org/modeling/emf/validation/downloads/drops/$dropDir
	mkdir -p $remoteDropDir
	cp -R $localDropDir/* $remoteDropDir/
fi

if [ "$merge" = y ]; then
	echo "Merging existing site into local one."
	./eclipse/eclipse -nosplash --launcher.suppressErrors -clean -debug -application org.eclipse.equinox.p2.metadata.repository.mirrorApplication -source file:$remoteUpdateSite -destination file:update-site
	./eclipse/eclipse -nosplash --launcher.suppressErrors -clean -debug -application org.eclipse.equinox.p2.artifact.repository.mirrorApplication -source file:$remoteUpdateSite -destination file:update-site
	echo "Merged $remoteUpdateSite into local directory update-site."
fi

# Ensure p2.mirrorURLs property is used in update site
echo "Setting p2.mirrorsURL to http://www.eclipse.org/downloads/download.php?format=xml&file=/$remoteUpdateSiteBase"
./eclipse/eclipse -nosplash --launcher.suppressErrors -clean -debug -application org.eclipse.wtp.releng.tools.addRepoProperties -vmargs -DartifactRepoDirectory=$PWD/update-site -Dp2MirrorsURL="http://www.eclipse.org/downloads/download.php?format=xml&file=/$remoteUpdateSiteBase"

# Create p2.index file
if [ ! -e "update-site/p2.index" ]; then
	echo "Creating p2.index file"
	echo "version = 1" > update-site/p2.index
	echo "metadata.repository.factory.order = content.xml,\!" >> update-site/p2.index
	echo "artifact.repository.factory.order = artifacts.xml,\!" >> update-site/p2.index
fi
		
# Backup then clean remote update site
echo "Creating backup of remote update site."
if [ -d "$remoteUpdateSite" ]; then
	if [ -d BACKUP ]; then
		rm -fr BACKUP
	fi
	mkdir BACKUP
	cp -R $remoteUpdateSite/* BACKUP/
	rm -fr $remoteUpdateSite
fi

echo "Publishing contents of local update-site directory to remote update site $remoteUpdateSite"
mkdir -p $remoteUpdateSite
cp -R update-site/* $remoteUpdateSite/

# Clean up
echo "Cleaning up"
rm -fr eclipse
rm -fr update-site
