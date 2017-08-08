package api;

import cluster.impl.ConsistentHashingVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import logic.impl.SessionVerticle;
import server.HttpServerVerticle;
import server.TCPServerVerticle;

public class StartVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(StartVerticle.class);

	public void start() throws Exception {
		vertx.deployVerticle(TCPServerVerticle.class.getName(), readBossOpts().setConfig(config()));
		vertx.deployVerticle(ConsistentHashingVerticle.class.getName(), readBossOpts().setConfig(config()));
		vertx.deployVerticle(HttpServerVerticle.class.getName(), readBossOpts().setConfig(config()));
		vertx.deployVerticle(SessionVerticle.class.getName(), readBossOpts().setConfig(config()));
	};

	public static DeploymentOptions readBossOpts() {
		DeploymentOptions options = new DeploymentOptions();
		options.setInstances(Runtime.getRuntime().availableProcessors());

		logger.info("Runtime.getRuntime().availableProcessors()={}", Runtime.getRuntime().availableProcessors());

		return options;
	}
}
