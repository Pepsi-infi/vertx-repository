package api;

import channel.AmqpConsumerVerticle;
import channel.HttpConsumerVerticle;
import channel.MiPushVerticle;
import dao.impl.DeviceDaoImpl;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import service.impl.RedisServiceImpl;

public class Launcher extends AbstractVerticle{

	private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();

		vertx.deployVerticle(DeviceDaoImpl.class.getName());
		vertx.deployVerticle(RedisServiceImpl.class.getName());
		
		vertx.deployVerticle(MiPushVerticle.class.getName());
//		vertx.deployVerticle(SocketVerticle.class.getName());

		vertx.deployVerticle(AmqpConsumerVerticle.class.getName());
		vertx.deployVerticle(HttpConsumerVerticle.class.getName());
	}

	private static void deployRestService() {



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
