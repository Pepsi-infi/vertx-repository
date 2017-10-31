package api;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import module.transcoding.TranscodingVerticle;
import server.FileServerVerticle;

public class StartVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(StartVerticle.class);

	@Override
	public void start() throws Exception {
		super.start();

		logger.info("config " + config().encode());
		vertx.deployVerticle(FileServerVerticle.class.getName(), readBossOpts().setConfig(config()));
		// vertx.deployVerticle(TranscodingVerticle.class.getName(),
		// readBossOpts().setConfig(config()));

		vertx.deployVerticle("groovy:file/transcoding/Transcoding.groovy", new DeploymentOptions().setWorker(true));
	}

	public static DeploymentOptions readBossOpts() {
		DeploymentOptions options = new DeploymentOptions();
		options.setInstances(Runtime.getRuntime().availableProcessors());

		return options;
	}
}
