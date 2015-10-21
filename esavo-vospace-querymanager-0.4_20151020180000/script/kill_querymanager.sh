#!/bin/sh
#
# Stop Server
#

OPERATING_SYSTEM=`uname -s`
SERVER_JAVA_CLASS=esavo.vospace.dl.querymanager.QueryManager
RELEASE_JAR_FILE=esavo-vospace-querymanager-X.x.jar

if [ "$OPERATING_SYSTEM" = "Linux" ]
then
  PS_COMMAND="/bin/ps "
else
  PS_COMMAND="/usr/ucb/ps "
fi

echo Stopping $SERVER_JAVA_CLASS ...

pid=`$PS_COMMAND auxww | egrep $SERVER_JAVA_CLASS | egrep $RELEASE_JAR_FILE | egrep -v grep | /usr/bin/awk '{printf("%s ", $2)}'`
kill $pid

echo $SERVER_JAVA_CLASS Server Stopped
