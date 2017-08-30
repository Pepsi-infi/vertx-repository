package api;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import server.FileServerVerticle;

public class StartVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(StartVerticle.class);

	@Override
	public void start() throws Exception {
		super.start();

		vertx.deployVerticle(FileServerVerticle.class.getName(), readBossOpts());
	}

	public static DeploymentOptions readBossOpts() {
		DeploymentOptions options = new DeploymentOptions();
		options.setInstances(Runtime.getRuntime().availableProcessors());

		return options;
	}
}
