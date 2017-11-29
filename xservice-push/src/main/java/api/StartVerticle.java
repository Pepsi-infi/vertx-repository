package api;

import channel.ApplePushVerticle;
import channel.HttpServerVerticle;
import channel.MiPushVerticle;
import channel.SocketVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.AbstractVerticle;
import push.apns.ApnsVerticle;
import service.impl.AdMessagePushServiceImpl;
import service.impl.ConfigServiceImpl;
import service.impl.MsgRecordServiceImpl;
import service.impl.NonAdMessagePushServiceImpl;
import service.impl.PassengerUnSendServiceImpl;
import service.impl.RedisServiceImpl;
import xservice.HttpClientVerticle;

public class StartVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(StartVerticle.class);

	@Override
	public void start() throws Exception {
		super.start();
		// 提供EventBus服务

		this.deployVerticle(MsgRecordServiceImpl.class.getName());
		this.deployVerticle(RedisServiceImpl.class.getName());

		this.deployVerticle(MiPushVerticle.class.getName());
		this.deployVerticle(SocketVerticle.class.getName());
		this.deployVerticle(ApplePushVerticle.class.getName());

		this.deployVerticle(HttpClientVerticle.class.getName());
		this.deployVerticle(HttpServerVerticle.class.getName());
		this.deployVerticle(AdMessagePushServiceImpl.class.getName());
		this.deployVerticle(NonAdMessagePushServiceImpl.class.getName());
		this.deployVerticle(ConfigServiceImpl.class.getName());
		//部署APNS Verticle
		//this.deployVerticle(ApnsVerticle.class.getName());
		//消息补发
		this.deployVerticle(PassengerUnSendServiceImpl.class.getName());

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
