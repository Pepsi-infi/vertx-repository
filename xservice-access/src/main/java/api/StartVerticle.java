package api;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import module.c2c.C2CVerticle;
import module.hash.IMConsistentHashingVerticle;
import module.persistence.MongoVerticle;
import module.quickphrase.QuickPhraseVerticle;
import module.session.IMSessionVerticle;
import server.IMServerVerticle;
import server.RestIMVerticle;

public class StartVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(StartVerticle.class);

	public void start() throws Exception {
		vertx.deployVerticle(IMServerVerticle.class.getName(), readBossOpts().setConfig(config()));
		// vertx.deployVerticle(FileServerVerticle.class.getName(),
		// readBossOpts().setConfig(config()));
		vertx.deployVerticle(IMConsistentHashingVerticle.class.getName(), readBossOpts().setConfig(config()));
		vertx.deployVerticle(RestIMVerticle.class.getName(), readBossOpts().setConfig(config()));
		vertx.deployVerticle(C2CVerticle.class.getName(), readBossOpts().setConfig(config()));
		vertx.deployVerticle(QuickPhraseVerticle.class.getName(), readBossOpts().setConfig(config()));

		vertx.deployVerticle(MongoVerticle.class.getName(), readBossOpts().setConfig(config()));

		/**
		 * Instance should be 1 because of ehcache.
		 */
		vertx.deployVerticle(IMSessionVerticle.class.getName());
	};

	public static DeploymentOptions readBossOpts() {
		DeploymentOptions options = new DeploymentOptions();
		options.setInstances(Runtime.getRuntime().availableProcessors());

		logger.info("Runtime.getRuntime().availableProcessors()={}", Runtime.getRuntime().availableProcessors());

		return options;
	}
}
