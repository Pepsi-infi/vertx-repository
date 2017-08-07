package api;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import server.TCPServerVerticle;

public class StartVerticle extends AbstractVerticle {

	public void start() throws Exception {
		vertx.deployVerticle(TCPServerVerticle.class.getName(), readBossOpts().setConfig(config()));
	};

	public static DeploymentOptions readBossOpts() {
		DeploymentOptions options = new DeploymentOptions();
		options.setInstances(Runtime.getRuntime().availableProcessors());

		return options;
	}
}
