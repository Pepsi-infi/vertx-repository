package api;

import channel.impl.ApplePushVerticle;
import channel.impl.MiPushVerticle;
import channel.impl.SocketVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.AbstractVerticle;
<<<<<<< HEAD
import service.impl.*;
import util.HttpUtil;
=======
import push.apns.ApnsVerticle;
>>>>>>> feature/xservice-push-apns_2.2
import service.impl.AdMessagePushServiceImpl;
import service.impl.ConfigServiceImpl;
import service.impl.ImMessagePushServiceImpl;
import service.impl.MsgRecordServiceImpl;
import service.impl.NonAdMessagePushServiceImpl;
import service.impl.RedisServiceImpl;
import xservice.HttpClientVerticle;

public class StartVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(StartVerticle.class);

	@Override
	public void start() throws Exception {
		// 提供EventBus服务

		this.deployVerticle(MsgRecordServiceImpl.class.getName());
		this.deployVerticle(RedisServiceImpl.class.getName());
		this.deployVerticle(MiPushVerticle.class.getName());
		this.deployVerticle(SocketVerticle.class.getName());
		this.deployVerticle(ApplePushVerticle.class.getName());

		this.deployVerticle(HttpUtil.class.getName());
		this.deployVerticle(DriverMsgServiceImpl.class.getName());

		this.deployVerticle(PassengerServiceImpl.class.getName());
		this.deployVerticle(DriverServiceImpl.class.getName());

		this.deployVerticle(MessagePushServiceImpl.class.getName());
		this.deployVerticle(HttpServerVerticle.class.getName());

		this.deployVerticle(AdMessagePushServiceImpl.class.getName());
		this.deployVerticle(NonAdMessagePushServiceImpl.class.getName());
		this.deployVerticle(ConfigServiceImpl.class.getName());

		// 未过期消息补发
		this.deployVerticle(PassengerUnSendServiceImpl.class.getName());
		this.deployVerticle(PassengerUnSendVerticle.class.getName());
		this.deployVerticle(ImMessagePushServiceImpl.class.getName());
		//部署APNS Verticle
		//this.deployVerticle(ApnsVerticle.class.getName());

		// 提供其他非EventBus服务
	}

	public void deployVerticle(String verticleName) {
		Future<String> future = Future.future();
		// future.setHandler(ar -> logger.info(ar.succeeded() ? "success:" + ar.result()
		// : "failed:" + ar.cause()));
		vertx.deployVerticle(verticleName, readBossOpts().setConfig(config()));
	}

	public static DeploymentOptions readBossOpts() {
		DeploymentOptions options = new DeploymentOptions();
		// options.setInstances(Runtime.getRuntime().availableProcessors());
		return options;
	}

}
