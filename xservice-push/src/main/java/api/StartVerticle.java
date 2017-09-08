package api;

import channel.impl.ApplePushVerticle;
import channel.impl.MiPushVerticle;
import channel.impl.SocketVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.AbstractVerticle;
import service.impl.DriverMsgServiceImpl;
import service.impl.DriverServiceImpl;
import service.impl.MessagePushServiceImpl;
import service.impl.MsgRecordServiceImpl;
import service.impl.PassengerServiceImpl;
import service.impl.RedisServiceImpl;
import util.HttpUtil;

public class StartVerticle extends AbstractVerticle{

    private static final Logger logger = LoggerFactory.getLogger(StartVerticle.class);
    @Override
    public void start() throws Exception {
        // 提供EventBus服务

        this.deployVerticle(MsgRecordServiceImpl.class.getName());
        this.deployVerticle(RedisServiceImpl.class.getName());

        this.deployVerticle(MiPushVerticle.class.getName());
		this.deployVerticle(SocketVerticle.class.getName());
        this.deployVerticle(ApplePushVerticle.class.getName());

//        this.deployVerticle(MessagePushContainer.class.getName());
//        this.deployVerticle(HttpClientVerticle.class.getName());
        this.deployVerticle(DriverMsgManageVerticle.class.getName());
        this.deployVerticle(HttpUtil.class.getName());
        this.deployVerticle(DriverMsgServiceImpl.class.getName());

        this.deployVerticle(MessagePushServiceImpl.class.getName());
//        this.deployVerticle(MessagePushVerticle.class.getName());

        this.deployVerticle(PassengerServiceImpl.class.getName());
        this.deployVerticle(PassengerMsgVerticle.class.getName());
        this.deployVerticle(DriverServiceImpl.class.getName());
        // 提供其他非EventBus服务
    }

    public void deployVerticle(String verticleName) {
        Future<String> future = Future.future();
//        future.setHandler(ar -> logger.info(ar.succeeded() ? "success:" + ar.result() : "failed:" + ar.cause()));
        vertx.deployVerticle(verticleName, readBossOpts().setConfig(config()));
    }

    public static DeploymentOptions readBossOpts() {
        DeploymentOptions options = new DeploymentOptions();
//        options.setInstances(Runtime.getRuntime().availableProcessors());
        return options;
    }

}
