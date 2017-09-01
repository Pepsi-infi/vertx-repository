package api;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import persistence.impl.MongoVerticle;

public class StartVerticle extends AbstractVerticle {

	public void start() throws Exception {
		vertx.deployVerticle(MongoVerticle.class.getName(), readBossOpts().setConfig(config()));
	};

	public static DeploymentOptions readBossOpts() {
		DeploymentOptions options = new DeploymentOptions();
		options.setInstances(Runtime.getRuntime().availableProcessors());

		return options;
	}
}
