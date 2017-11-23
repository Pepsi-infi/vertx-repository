#!/bin/sh
root_path=$(cd "$(dirname "${0}")"; pwd)

pid=$(ps -ef | grep xservice-socket | grep java | awk '{print $2}')
if [ ! -z "$pid" ]
then 
  kill -9 $pid
fi

nohup java \
-server \
-XX:+PrintGCApplicationStoppedTime -XX:+PrintGCTimeStamps -XX:+PrintGCDetails -Xloggc:${root_path}/gc.log \
-Xms2g -Xmx2g -Xmn1380m -Xss256K -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m \
-XX:+UseG1GC \
-XX:MaxDirectMemorySize=16g \
-XX:AutoBoxCacheMax=20000 -XX:+AlwaysPreTouch -XX:-UseCounterDecay \
-Djava.net.preferIPv4Stack=true \
-Dlog.path=${root_path}/log \
-Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.Log4j2LogDelegateFactory \
-Dlog4j.configurationFile=log4j2.xml \
-Dvertx.zookeeper.config=zookeeper-prod.json \
-Dconfig=passenger-prod \
-jar ${root_path}/xservice-socket-fat.jar >> ${root_path}/nohup.out &

exit 0