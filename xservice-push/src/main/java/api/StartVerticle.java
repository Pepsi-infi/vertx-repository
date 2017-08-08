package api;

import channel.*;
import dao.impl.MsgRecordDaoImpl;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.AbstractVerticle;
import service.impl.MsgRecordServiceImpl;
import service.impl.RedisServiceImpl;

public class StartVerticle extends AbstractVerticle{

    private static final Logger logger = LoggerFactory.getLogger(StartVerticle.class);
    @Override
    public void start() throws Exception {
        super.start();
        // 提供EventBus服务
        

		this.deployVerticle(MsgRecordDaoImpl.class.getName());
        this.deployVerticle(MsgRecordServiceImpl.class.getName());
        this.deployVerticle(RedisServiceImpl.class.getName());

        this.deployVerticle(MiPushVerticle.class.getName());
		this.deployVerticle(SocketVerticle.class.getName());
        this.deployVerticle(ApplePushVerticle.class.getName());

        this.deployVerticle(HttpConsumerVerticle.class.getName());
//        this.deployVerticle(AmqpConsumerVerticle.class.getName());
        // 提供其他非EventBus服务
    }

    public void deployVerticle(String verticleName) {
        Future<String> future = Future.future();
        future.setHandler(ar -> logger.info(ar.succeeded() ? "success:" + ar.result() : "failed:" + ar.cause()));
        vertx.deployVerticle(verticleName, readBossOpts().setConfig(config()), future.completer());
    }

    public static DeploymentOptions readBossOpts() {
        DeploymentOptions options = new DeploymentOptions();
        options.setInstances(Runtime.getRuntime().availableProcessors());
        return options;
    }
}
