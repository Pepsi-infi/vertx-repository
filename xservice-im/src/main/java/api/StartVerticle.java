package api;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import logic.impl.C2CVerticle;
import persistence.impl.MongoVerticle;
import server.TCPServerVerticle;

public class StartVerticle extends AbstractVerticle {

	public void start() throws Exception {
		vertx.deployVerticle(TCPServerVerticle.class.getName(), readBossOpts().setConfig(config()));
		vertx.deployVerticle(MongoVerticle.class.getName(), readBossOpts().setConfig(config()));
		vertx.deployVerticle(C2CVerticle.class.getName(), readBossOpts().setConfig(config()));
	};

	public static DeploymentOptions readBossOpts() {
		DeploymentOptions options = new DeploymentOptions();
		options.setInstances(Runtime.getRuntime().availableProcessors());

		return options;
	}
}
