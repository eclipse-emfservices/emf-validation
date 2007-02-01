#!/bin/bash

echo -n "[relengbuild] relengbuildgtk.sh started on: `date +%Y%m%d\ %H\:%M\:%S`";

# environment variables
PATH=.:/bin:/usr/bin:/usr/bin/X11:/usr/local/bin:/usr/X11R6/bin:`pwd`/../linux;export PATH

export USERNAME=`whoami`
echo " running as $USERNAME";
echo " currently in dir: `pwd`";

Xflags="";
Dflags="";

# default target to run in org.eclipse.emft/releng/[subproject]/builder/tests/scripts/test.xml (as called by tests/scripts/runtests, below)
antTestTarget=all

# process command line arguments
while [ $# -gt 0 ]
do
	case "$1" in
		-vmExecutable) vmExecutable="$2"; shift;;
		-consolelog)   consolelog="$2";   shift;;
		-vmFileName)   vmFileName="$2";   shift;;
		-X*) Xflags=$Xflags\ $1;;
		-D*) Dflags=$Dflags\ $1;;
	esac
	shift
done

checkIfj9 ()
{
# given a series of -X flags, see if the string -Xj9 can be found
  j9=$Xflags;
  #echo "Xflags=$Xflags"
  j9=${j9/\-Xj9/} # substring replacement
  #echo "remaining: $j9"
  if [ "$j9" != "$Xflags" ]; then # found it
    j9="j9";
  else
    j9="";
  fi
}
checkIfj9;

defined=0;
checkIfDefined ()
{
	if [ -f $1 ] ; then
		defined=1;
	else
		defined=0;
	fi
}

execCmd ()
{
	echo ""; echo "[relengbuild] [`date +%H\:%M\:%S`]"; 
	echo "  $1" | perl -pe "s/ -/\n  -/g";
	if [ "x$2" != "x" ]; then
		$1 | tee $2;
	else
		$1;
	fi
}

doFunction ()
{
	cmd=$1;
	params=$2
	for pth in "." "/bin" "/usr/bin" "/usr/bin/X11" "/usr/local/bin" "/usr/X11R6/bin" "`pwd`/../linux" ; do
		defined=0;
		checkIfDefined $pth/$cmd
		if [ $defined -eq 1 ] ; then
			$cmd $params
			sleep 3
			break;
		fi
	done
	if [ $defined -eq 0 ] ; then
		echo "$cmd is not defined (command not found)";
	fi
}

# these don't work on emf.torolab server, so not point wrapping them to say so when we can just omit
# doFunction Xvfb ":42 -screen 0 1024x768x24 -ac & "
# doFunction Xnest ":43 -display :42 -depth 24 & "
# doFunction fvwm2 "-display localhost:43.0 & "

export DISPLAY=$HOSTNAME:43.0
ulimit -c unlimited

# /home/www-data/build/emft/$proj/downloads/drops/1.1.0/N200502112049/testing/N200502112049/testing/linux.gtk_consolelog.txt
echo "[relengbuild] runtests log: $PWD/$consolelog";

getBuildID()
{	# given $PWD: /home/www-data/build/emft/$proj/downloads/drops/1.1.0/N200502112049/testing/N200502112049/testing
	# return N200502110400
	buildID=$1; #echo "buildID=$buildID";
	buildID=${buildID##*drops\/}; # trim up to drops/ (from start) (substring notation)
	buildID=${buildID%%\/test*}; # trim off /test (to end) (substring notation)
	buildID=${buildID##*\/}; # trim up to / (from start) (substring notation)
}
buildID=""; getBuildID $PWD; #echo buildID=$buildID;

getBranch()
{	# given $PWD: /home/www-data/build/emft/$proj/downloads/drops/1.1.0/N200502112049/testing/N200502112049/testing
	# return 1.1.0
	branch=$1; #echo "branch=$branch";
	branch=${branch##*drops\/}; # trim up to drops/ (from start) (substring notation)
	branch=${branch%%\/*}; # trim off / (to end) (substring notation)
}
branch=""; getBranch $PWD; #echo branch=$branch;

# determine consolelog file based on value in properties file
testOnlyTimeStamp=`cat testing.properties | grep "emf.test.testOnlyTimeStamp" | grep -v "#emf.test.testOnlyTimeStamp"`; testOnlyTimeStamp=${testOnlyTimeStamp##*=};
if [ "x$testOnlyTimeStamp" != "x" ]; then
	consolelog=${consolelog%%\.txt}$testOnlyTimeStamp".txt"; # eg., linux.gtk_consolelog.txt -> linux.gtk_consolelog_200504031234.txt
fi

# execute command to run tests
chmod 755 runtests
execCmd "runtests -os linux -ws gtk -arch x86 -Dplatform=linux.gtk $Dflags -vm $vmExecutable $antTestTarget $Xflags" $consolelog

# supress errors by checking for the file first
if [ -r /tmp/.X43-lock ] ; then
	kill `cat /tmp/.X43-lock`
fi
if [ -r /tmp/.X42-lock ] ; then
	kill `cat /tmp/.X42-lock`
fi

if [[ ! -d $PWD/results ]]; then
	echo "[relengbuild] No test results found in $PWD/results!";
	echo "[relengbuild] Creating 'noclean' file to prevent cleanup after build completes."
	echo "1" > $PWD/../../../noclean;
else
# if the build failed for some reason, don't clean up!
xmls=`find $PWD/results/xml -name "*.xml"`;
testsFailed=0;
for xml in $xmls; do
	if [ $testsFailed -eq 0 ]; then
		testsFailed=`cat $xml | grep -c "Failure"`
		if [ $testsFailed -gt 0 ]; then
			echo "[relengbuild] Found test failure(s) in $xml!";
			echo "[relengbuild] Creating 'noclean' file to prevent cleanup after build completes."
			echo "1" > $PWD/../../../noclean;
			break;
		fi
	fi
done
fi

echo "[relengbuild] relengbuildgtk.sh completed on: `date +%Y%m%d\ %H\:%M\:%S`"
