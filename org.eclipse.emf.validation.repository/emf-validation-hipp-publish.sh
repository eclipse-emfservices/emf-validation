#!/bin/sh
# Copyright (c) 2012, 2014 IBM Corporation and others.
# All rights reserved.   This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Contributors:
#   IBM - Initial API and implementation

# Script may take 5-6 command line parameters:
# Hudson job name: ${JOB_NAME}
# Hudson build id: ${BUILD_ID}
# Hudson workspace: ${WORKSPACE}
# $1: Build type: n(ightly), m(aintenance), s(table), r(elease)
# $2: An optional label to append to the version string when creating drop files, e.g. M5 or RC1
# 
set -e

if [ $# -eq 1 -o $# -eq 2 ]; then
	buildType=$1
	if [ -n "$2" ]; then
		dropFilesLabel=$2
	fi
else
	echo "Usage: $0 [i | s | r | m] [qualifier]"
	echo "Example: $0 i"
	echo "Example: $0 s M7"
	exit 1
	if [ $# -ne 0 ]; then
		exit 1
	fi
fi

if [ -z "$JOB_NAME" ]; then
	echo "Error there is no Hudson JOB_NAME defined"; 
	exit 0
fi

if [ -z "$BUILD_ID" ]; then
	echo "Error there is no Hudson BUILD_ID defined"; 
	exit 0
fi

if [ -z "$WORKSPACE" ]; then
	echo "Error there is no Hudson WORKSPACE defined"; 
	exit 0
fi

# Determine the local update site we want to publish from
localTarget=${WORKSPACE}/org.eclipse.emf.validation.repository/target
localUpdateSite=${localTarget}/repository/
echo "`date +%Y-%m-%d-%H:%M:%S` Using local update-site: $localUpdateSite"

# Determine remote update site we want to promote to (integration and maintenance are published on interim site, stable builds on milestone site, release builds on releases site)
case $buildType in
	m|M) remoteSite=maintenance ;;
	i|I) remoteSite=interim ;;
	s|S) remoteSite=milestones ;;
	r|R) remoteSite=releases ;;
	*) exit 0 ;;
esac
echo "`date +%Y-%m-%d-%H:%M:%S` Publishing as $remoteSite ( $buildType ) build"
remoteUpdateSiteBase="modeling/emf/validation/updates/$remoteSite"
remoteUpdateSite="/home/data/httpd/download.eclipse.org/$remoteUpdateSiteBase"
echo "`date +%Y-%m-%d-%H:%M:%S` Publishing to remote update-site: $remoteUpdateSite"

if [ -z "$dropFilesLabel" -a "$buildType" != i ]; then
	echo "Please provide a drop files label to append to the version (e.g. M5, RC1) if this is not an I build."
	exit 0
fi

# Prepare a temp directory
tmpDir="$localTarget/$JOB_NAME-publish-tmp"
rm -fr $tmpDir
mkdir -p $tmpDir
cd $tmpDir
echo "`date +%Y-%m-%d-%H:%M:%S` Working in `pwd`"

# Download and prepare Eclipse SDK, which is needed to process the update site
echo "`date +%Y-%m-%d-%H:%M:%S` Downloading eclipse to $PWD"
cp /home/data/httpd/download.eclipse.org/eclipse/downloads/drops4/R-4.4-201406061215/eclipse-SDK-4.4-linux-gtk-x86_64.tar.gz .
tar -xzf eclipse-SDK-4.4-linux-gtk-x86_64.tar.gz
cd eclipse
chmod 700 eclipse
cd ..
if [ ! -d "eclipse" ]; then
	echo "Failed to download an Eclipse SDK, being needed for provisioning."
	exit
fi
# Prepare Eclipse SDK to provide WTP releng tools (used to postprocess repository, i.e set p2.mirrorsURL property)
echo "`date +%Y-%m-%d-%H:%M:%S` Installing WTP Releng tools"
./eclipse/eclipse -nosplash --launcher.suppressErrors -clean -debug -application org.eclipse.equinox.p2.director -repository http://download.eclipse.org/webtools/releng/repository/ -installIUs org.eclipse.wtp.releng.tools.feature.feature.group
# Clean up
rm eclipse-SDK-4.4-linux-gtk-x86_64.tar.gz

# Generate drop files
echo "`date +%Y-%m-%d-%H:%M:%S` Converting update site to runnable form"
./eclipse/eclipse -nosplash -consoleLog -application org.eclipse.equinox.p2.repository.repo2runnable -source file:$localUpdateSite -destination file:drops/eclipse
qualifiedVersion=$(find $localUpdateSite/features/ -maxdepth 1 | grep "org.eclipse.emf.validation_" | sed 's/.jar$//')
echo "`date +%Y-%m-%d-%H:%M:%S` qualifiedVersion is $qualifiedVersion"
qualifiedVersion=${qualifiedVersion#*_}
echo "`date +%Y-%m-%d-%H:%M:%S` qualifiedVersion is $qualifiedVersion"
qualifier=${qualifiedVersion##*.}
echo "`date +%Y-%m-%d-%H:%M:%S` qualifier is $qualifier"
qualifier=${qualifier#v}
echo "`date +%Y-%m-%d-%H:%M:%S` qualifier is $qualifier"
version=${qualifiedVersion%.*}
echo "`date +%Y-%m-%d-%H:%M:%S` version is $version"
dropDir="$(echo $buildType | tr '[:lower:]' '[:upper:]')$qualifier"
echo "`date +%Y-%m-%d-%H:%M:%S` dropDir is $dropDir"
localDropDir=drops/$version/$dropDir
echo "`date +%Y-%m-%d-%H:%M:%S` Creating drop files in local directory $tmpDir/$localDropDir"
mkdir -p $localDropDir

# Prepare local update site (merging is performed later, if required)
stagedUpdateSite="updates/$remoteSite/$dropDir"
mkdir -p $stagedUpdateSite
cp -R $localUpdateSite/* $stagedUpdateSite
echo "`date +%Y-%m-%d-%H:%M:%S` Copied $localUpdateSite to local directory $stagedUpdateSite."

# Append drop file suffix if one is specified				
if [ -n "$dropFilesLabel" ]; then
	version=$version$dropFilesLabel
	echo "`date +%Y-%m-%d-%H:%M:%S` version is now $version"
elif [ "$buildType" != r -a "$buildType" != R ]; then
	version="$(echo $buildType | tr '[:lower:]' '[:upper:]')$qualifier"
	echo "`date +%Y-%m-%d-%H:%M:%S` version is now $version"
else
	echo "`date +%Y-%m-%d-%H:%M:%S` version is now $version"
fi
				
cp eclipse/epl-v10.html drops/eclipse
cp eclipse/notice.html drops/eclipse
cd drops

# emf validation SDK
zip -r ../$localDropDir/emf-validation-SDK-$version.zip \
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
	eclipse/plugins/org.eclipse.emf.validation.ui_* \
	eclipse/plugins/org.eclipse.emf.validation.source_* \
	eclipse/plugins/org.eclipse.emf.validation.ocl.source_* \
	eclipse/plugins/org.eclipse.emf.validation.ui.ide.source_* \
	eclipse/plugins/org.eclipse.emf.validation.ui.source_*
md5sum ../$localDropDir/emf-validation-SDK-$version.zip > ../$localDropDir/emf-validation-SDK-$version.zip.md5
echo "`date +%Y-%m-%d-%H:%M:%S` Created emf-validation-SDK-$version.zip"
			
# emf-validation runtime
zip -r ../$localDropDir/emf-validation-runtime-$version.zip \
	eclipse/epl-v10.html eclipse/notice.html \
	eclipse/features/org.eclipse.emf.validation_* \
	eclipse/features/org.eclipse.emf.validation.ocl_* \
	eclipse/plugins/org.eclipse.emf.validation_* \
	eclipse/plugins/org.eclipse.emf.validation.ocl_* \
	eclipse/plugins/org.eclipse.emf.validation.ui.ide_* \
	eclipse/plugins/org.eclipse.emf.validation.ui_*
md5sum ../$localDropDir/emf-validation-runtime-$version.zip > ../$localDropDir/emf-validation-runtime-$version.zip.md5
echo "`date +%Y-%m-%d-%H:%M:%S` Created emf-validation-runtime-$version.zip"
			
# emf-validation examples
zip -r ../$localDropDir/emf-validation-examples-$version.zip \
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
md5sum ../$localDropDir/emf-validation-examples-$version.zip > ../$localDropDir/emf-validation-examples-$version.zip.md5
echo "`date +%Y-%m-%d-%H:%M:%S` Created emf-validation-examples-$version.zip"
			
# emf-validation automated-tests
zip -r ../$localDropDir/emf-validation-automated-tests-$version.zip \
	eclipse/epl-v10.html eclipse/notice.html \
	eclipse/features/org.eclipse.emf.validation.tests_* \
	eclipse/plugins/org.eclipse.emf.validation.tests_*
md5sum ../$localDropDir/emf-validation-automated-tests-$version.zip > ../$localDropDir/emf-validation-automated-tests-$version.zip.md5
echo "`date +%Y-%m-%d-%H:%M:%S` Created emf-validation-automated-tests-$version.zip"
	
cd $tmpDir
		
cd $stagedUpdateSite
zip -r ../../../$localDropDir/emf-validation-Update-$version.zip features plugins binary artifacts.jar content.jar
md5sum ../../../$localDropDir/emf-validation-Update-$version.zip > ../../../$localDropDir/emf-validation-Update-$version.zip.md5
echo "`date +%Y-%m-%d-%H:%M:%S` Created emf-validation-Update-Site-$version.zip"

cd $tmpDir

#generating build.cfg file to be referenced from downloads web page
echo "hudson.job.name=${JOB_NAME}" > $localDropDir/build.cfg
echo "hudson.job.id=${BUILD_NUMBER} (${jobDir##*/})" >> $localDropDir/build.cfg
echo "hudson.job.url=${BUILD_URL}" >> $localDropDir/build.cfg

remoteDropDir=/home/data/httpd/download.eclipse.org/modeling/emf/validation/downloads/$localDropDir
mkdir -p $remoteDropDir
cp -R $localDropDir/* $remoteDropDir/
echo "`date +%Y-%m-%d-%H:%M:%S` Published drop files in local directory $tmpDir/$localDropDir to $remoteDropDir"

# Ensure p2.mirrorURLs property is used in update site
echo "`date +%Y-%m-%d-%H:%M:%S` Setting p2.mirrorsURL to http://www.eclipse.org/downloads/download.php?format=xml&file=/$remoteUpdateSiteBase"
./eclipse/eclipse -nosplash --launcher.suppressErrors -clean -debug -application org.eclipse.wtp.releng.tools.addRepoProperties -vmargs -DartifactRepoDirectory=$PWD/$stagedUpdateSite -Dp2MirrorsURL="http://www.eclipse.org/downloads/download.php?format=xml&file=/$remoteUpdateSiteBase"

# Create p2.index file
if [ ! -e "$stagedUpdateSite/p2.index" ]; then
	echo "`date +%Y-%m-%d-%H:%M:%S` Creating p2.index file"
	echo "version = 1" > $stagedUpdateSite/p2.index
	echo "metadata.repository.factory.order = content.xml,\!" >> $stagedUpdateSite/p2.index
	echo "artifact.repository.factory.order = artifacts.xml,\!" >> $stagedUpdateSite/p2.index
fi

# Backup then clean remote update site
if [ -d "$remoteUpdateSite" ]; then
	echo "`date +%Y-%m-%d-%H:%M:%S` Creating backup of remote update site $remoteUpdateSite to $tmpDir/BACKUP."
	if [ -d $tmpDir/BACKUP ]; then
		rm -fr $tmpDir/BACKUP
	fi
	mkdir $tmpDir/BACKUP
	cp -R $remoteUpdateSite $tmpDir/BACKUP
	rm -fr $remoteUpdateSite
fi

echo "`date +%Y-%m-%d-%H:%M:%S` Publishing local $stagedUpdateSite directory to remote update site $remoteUpdateSite/$dropDir"
mkdir -p $remoteUpdateSite
cp -R $stagedUpdateSite $remoteUpdateSite

# Create the composite update site
cat > p2.composite.repository.xml <<EOF
<?xml version="1.0" encoding="UTF-8"?>
<project name="p2 composite repository">
<target name="default">
<p2.composite.repository>
<repository compressed="true" location="${remoteUpdateSite}" name="${JOB_NAME}"/>
<add>
<repository location="${dropDir}"/>
</add>
</p2.composite.repository>
</target>
</project>
EOF

echo "`date +%Y-%m-%d-%H:%M:%S` Update the composite update site"
./eclipse/eclipse -nosplash --launcher.suppressErrors -clean -debug -application org.eclipse.ant.core.antRunner -buildfile p2.composite.repository.xml default

# Clean up
echo "`date +%Y-%m-%d-%H:%M:%S` Cleaning up"
#rm -fr eclipse
#rm -fr update-site
