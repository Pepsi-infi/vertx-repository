package api;

import config.quickphrase.QuickPhraseConfigVerticle;
import config.sensitivewords.SensitiveWordConfigVerticle;
import config.whitelist.WhiteListConfigVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import service.impl.ImCommonLanguageServiceImpl;
import service.impl.SensitiveWordServiceImpl;
import xservice.BaseServiceVerticle;

/**
 * Created by lufei Date : 2017/8/29 14:08 Description :
 */
public class StartVerticle extends BaseServiceVerticle {
	private static final Logger logger = LoggerFactory.getLogger(StartVerticle.class);

	@Override
	public void start() throws Exception {
		super.start();

		vertx.deployVerticle(ImCommonLanguageServiceImpl.class.getName(), readBossOpts().setConfig(config()));
		vertx.deployVerticle(SensitiveWordServiceImpl.class.getName(), readBossOpts().setConfig(config()));
		vertx.deployVerticle(RestConfigVerticle.class.getName(), readBossOpts().setConfig(config()));

		vertx.deployVerticle(SensitiveWordConfigVerticle.class.getName(), readBossOpts().setConfig(config()));
		vertx.deployVerticle(WhiteListConfigVerticle.class.getName(), readBossOpts().setConfig(config()));
		vertx.deployVerticle(QuickPhraseConfigVerticle.class.getName(),readBossOpts().setConfig(config()));
	}

	public static DeploymentOptions readBossOpts() {
		DeploymentOptions options = new DeploymentOptions();
		options.setInstances(Runtime.getRuntime().availableProcessors());

		logger.info("Runtime.getRuntime().availableProcessors()={}", Runtime.getRuntime().availableProcessors());

		return options;
	}
}
