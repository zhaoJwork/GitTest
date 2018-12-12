#!/bin/sh

usage() {
	echo "Usage:"
	echo "    startup.sh [-c config_folder] [-l log_folder] [-d debug mode] [-h]"
	echo "Description:"
	echo "    config_folder - config folder path, not must,if empty,default classpath"
	echo "    log_folder - hsb server's logs base folder, /home/work  log path: /home/work/logs"
	echo "    debug_mode - 1|0  1 means debug port is open, 0 close ,default 0"
	echo "    -h - show this help"
	exit -1
}
LOG_BASE_DIR=""
CONFIG_DIR=""
DEBUG_MODE="0";

while getopts "h:l:c:d:" arg
do
	case $arg in
	    l) LOG_BASE_DIR=$OPTARG;;
		c) CONFIG_DIR=$OPTARG;;
		d) DEBUG_MODE=$OPTARG;;
		h) usage;;
		?) usage;;
	esac
done

# echo Baidu.com,Inc.
# echo 'Copyright (c) 2000-2013 All Rights Reserved.'
# echo Distributed
# echo https://appAddresslistAPIthub.com/brucexx/heisenberg
# echo brucest0078@gmail.com
#JAVA_HOME=/app/jdk1.8.0_151
JAVA_HOME=/app/was/jdk1.8.0_161
#JAVA_HOME=/app/jdk/jdk1.8.0_151
PATH=$JAVA_HOME/bin:$PATH
CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export JAVA_HOME
export PATH
export CLASSPATH
#check JAVA_HOME & java
noJavaHome=false
if [ -z "$JAVA_HOME" ] ; then
    noJavaHome=true
fi
if [ ! -e "$JAVA_HOME/bin/java" ] ; then
    noJavaHome=true
fi
if $noJavaHome ; then
    echo
    echo "Error: JAVA_HOME environment variable is not set."
    echo
    exit 1
fi
#==============================================================================
#set JAVA_OPTS


appAddresslistAPI_HOME=`pwd`
JAVA_OPTS="-server -Xmx1000M -Xms1000M -Xmn1000M"
#JAVA_OPTS="$JAVA_OPTS -XX:PermSize=500M -XX:MaxPermSize=500M -Xss256K"
JAVA_OPTS="$JAVA_OPTS -XX:+DisableExplicitGC -XX:SurvivorRatio=1 -XX:+UseConcMarkSweepGC"
JAVA_OPTS="$JAVA_OPTS -XX:+UseParNewGC -XX:+CMSParallelRemarkEnabled"
#JAVA_OPTS="$JAVA_OPTS -XX:+UseCMSCompactAtFullCollection -XX:CMSFullGCsBeforeCompaction=0"
JAVA_OPTS="$JAVA_OPTS -XX:+CMSClassUnloadingEnabled -XX:LargePageSizeInBytes=128M"
JAVA_OPTS="$JAVA_OPTS -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly"
JAVA_OPTS="$JAVA_OPTS -XX:CMSInitiatingOccupancyFraction=80 -XX:SoftRefLRUPolicyMSPerMB=0"
JAVA_OPTS="$JAVA_OPTS -XX:+PrintClassHistogram -XX:+PrintGCDetails -XX:+PrintGCTimeStamps"
JAVA_OPTS="$JAVA_OPTS -XX:+PrintHeapAtGC -Xloggc:${appAddresslistAPI_HOME}/logs/gc.log"
#LOG4J2 ASYNC
JAVA_OPTS="$JAVA_OPTS -DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector"
#JMX REMOTE
#JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote=true"
#JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote.port=9988"
#JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote.ssl=false"
#JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote.authenticate=false"

#==============================================================================

#set HOME
CURR_DIR=`pwd`
cd `dirname "$0"`/..
cd $CURR_DIR
if [ -z "$appAddresslistAPI_HOME" ] ; then
    echo
    echo "Error: appAddresslistAPI_HOME environment variable is not defined correctly."
    echo
    exit 1
fi
#==============================================================================

#set CLASSPATH
appAddresslistAPI_CLASSPATH="$appAddresslistAPI_HOME/conf:$appAddresslistAPI_HOME/lib/*"
#==============================================================================

#startup Server
RUN_CMD="\"$JAVA_HOME/bin/java\""
RUN_CMD="$RUN_CMD -DappName=appAddresslistAPI -DappAddresslistAPI_HOME=\"$appAddresslistAPI_HOME\""
RUN_CMD="$RUN_CMD -classpath \"$appAddresslistAPI_CLASSPATH\""
RUN_CMD="$RUN_CMD $JAVA_OPTS"
#RUN_CMD="$RUN_CMD cn.com.jamboree.search.Application $@"
RUN_CMD="$RUN_CMD com.lin.AddressBookApplication $@"
RUN_CMD="$RUN_CMD >> \"$appAddresslistAPI_HOME/logs/console.log\" 2>&1 &"
echo $RUN_CMD
eval $RUN_CMD
echo $! > $appAddresslistAPI_HOME/bin/appAddresslistAPI.pid
#==============================================================================