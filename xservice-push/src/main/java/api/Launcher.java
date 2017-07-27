package api;

import channel.SocketVerticle;
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


		vertx.deployVerticle(SocketVerticle.class.getName());
	}

	private void deployRestService() {

		this.deployVerticle(DeviceDaoImpl.class.getName());
		this.deployVerticle(RedisServiceImpl.class.getName());

		this.deployVerticle(DeviceDaoImpl.class.getName());

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
