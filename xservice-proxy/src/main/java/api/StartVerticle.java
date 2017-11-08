package api;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import proxy.server.UdpProxyServerVerticle;

public class StartVerticle extends AbstractVerticle {

	public void start() throws Exception {

		// Just one instance because of port conflicts.
		vertx.deployVerticle(UdpProxyServerVerticle.class.getName(), new DeploymentOptions().setConfig(config()));
	};

	public static DeploymentOptions readBossOpts() {
		DeploymentOptions options = new DeploymentOptions();
		options.setInstances(Runtime.getRuntime().availableProcessors());

		return options;
	}
}
