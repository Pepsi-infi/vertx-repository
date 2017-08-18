#!/bin/sh
root_path=$(cd "$(dirname "${0}")"; pwd)

pid=$(ps -ef | grep xservice-push | grep java | awk '{print $2}')
if [ ! -z "$pid" ]
then 
  kill -9 $pid
fi

BUILD_ID=
java \
-server \
-XX:+PrintGCApplicationStoppedTime \
-XX:+PrintGCTimeStamps \
-XX:+PrintGCDetails \
-Xms1g -Xmx1g -Xmn768m -Xss256K -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m \
-XX:AutoBoxCacheMax=20000 -XX:+AlwaysPreTouch \
-XX:+UseParallelOldGC -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly \
-XX:MaxTenuringThreshold=2 -XX:+ExplicitGCInvokesConcurrent \
-XX:-UseCounterDecay \
-Djava.net.preferIPv4Stack=true \
-Xloggc:${root_path}/gc.log \
-Dlog.path=${root_path}/ \
-Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.Log4j2LogDelegateFactory \
-Dlog4j.configurationFile=log4j2.xml \
-Dvertx.zookeeper.config=zookeeper-dev.json \
-Dconfig=dev \
-jar ${root_path}/xservice-push-fat.jar >/dev/null 2>&1

exit 0