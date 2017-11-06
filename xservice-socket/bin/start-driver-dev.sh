#!/bin/sh
set -x
root_path=$(cd "$(dirname "${0}")"; pwd)

pid=$(ps -ef | grep xservice-socket | grep java | awk '{print $2}')
if [ ! -z "$pid" ]
then 
  kill -9 $pid
fi

sleep 5s

nohup /usr/local/jdk1.8/bin/java \
-server \
-XX:+PrintGCApplicationStoppedTime \
-XX:+PrintGCTimeStamps \
-XX:+PrintGCDetails \
-Xms1g -Xmx1g -Xmn768m -Xss256K -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m \
-XX:MaxDirectMemorySize=1g \
-XX:AutoBoxCacheMax=20000 -XX:+AlwaysPreTouch \
-XX:+UseParallelOldGC -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly \
-XX:MaxTenuringThreshold=2 -XX:+ExplicitGCInvokesConcurrent \
-XX:-UseCounterDecay \
-Djava.net.preferIPv4Stack=true \
-Xloggc:${root_path}/gc.log \
-Dlog.path=${root_path}/log \
-Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.Log4j2LogDelegateFactory \
-Dlog4j.configurationFile=log4j2.xml \
-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=7091 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -XX:+UnlockCommercialFeatures -XX:+FlightRecorder \
-Dconfig=driver-dev \
-Dvertx.zookeeper.config=zookeeper-dev.json \
-jar ${root_path}/xservice-socket-fat.jar >> ${root_path}/nohup.out &

sleep 2s

exit 0