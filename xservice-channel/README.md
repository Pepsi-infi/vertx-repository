config.json需要放在fat.jar对应目录下

## Run

```
java -jar xservice-channel-fat.jar -cluster true -conf config.json

java -Dlog.path=/Users/xuli/software/mobileconf/cn/logs/channel -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.Log4j2LogDelegateFactory -Dlog4j.configurationFile=log4j2.xml -jar xservice-channel-fat.jar

生成本地镜像:
     docker build -t 镜像名:镜像tag号 Dockerfile所在路径
     docker build -t channel:channel-1.0 .

本地运行镜像:
     docker run -d -p 9090:9090 channel:channel-1.0


push本地镜像(channel:channel-1.0)到远程(pre-bj-leengine.lecloud.com)上:
1)登录:
      docker login -u wanglonghu -p 9f8efcd8c71603146dc0938bef0af71c pre-bj-registry-leengine.lecloud.com

2)打tag:
      docker tag 本地镜像名:本地镜像tag号 远程镜像名:远程镜像tag号   #这里远程镜像名必须和远程一致,远程镜像tag号自己按需取
      docker tag channel:channel-1.0 pre-bj-registry-leengine.lecloud.com/lead/le_channel:channel-1.0

3)push tag到远程:
      docker push 远程镜像名:远程镜像tag号
      docker push pre-bj-registry-leengine.lecloud.com/lead/le_channel:channel-1.0