package api;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import proxy.server.UdpCopyServerVerticle;

public class StartVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(StartVerticle.class);

	public void start() throws Exception {

		// Just one instance because of port conflicts.
		vertx.deployVerticle(UdpCopyServerVerticle.class.getName(), new DeploymentOptions().setConfig(config()),
				res -> {
					if (res.succeeded()) {
						logger.info("UdpProxyServerVerticle={}", res.result());
					} else {
						logger.error("UdpProxyServerVerticle={}", res.cause().getCause());
					}
				});
	};

	public static DeploymentOptions readBossOpts() {
		DeploymentOptions options = new DeploymentOptions();
		options.setInstances(Runtime.getRuntime().availableProcessors());

		return options;
	}
}
