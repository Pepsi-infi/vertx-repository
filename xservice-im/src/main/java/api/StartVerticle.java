package api;

import cluster.impl.SocketConsistentHashingVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import logic.impl.SocketSessionVerticle;
import server.MessageSendVerticle;
import server.RestSocketVerticle;
import server.SocketServerVerticle;
import server.UdpServerVerticle;
import tp.impl.TpServiceImpl;

public class StartVerticle extends AbstractVerticle {

	public void start() throws Exception {
		vertx.deployVerticle(SocketConsistentHashingVerticle.class.getName(), readBossOpts().setConfig(config()));

		vertx.deployVerticle(SocketServerVerticle.class.getName(), readBossOpts().setConfig(config()));

		vertx.deployVerticle(TpServiceImpl.class.getName(), readBossOpts().setConfig(config()));
		vertx.deployVerticle(MessageSendVerticle.class.getName(), readBossOpts().setConfig(config()));

		vertx.deployVerticle(RestSocketVerticle.class.getName(), readBossOpts().setConfig(config()));

		// Just one instance because of port conflicts.
		vertx.deployVerticle(UdpServerVerticle.class.getName());

		// Just one instance because of ehcache.
		vertx.deployVerticle(SocketSessionVerticle.class.getName());
	};

	public static DeploymentOptions readBossOpts() {
		DeploymentOptions options = new DeploymentOptions();
		options.setInstances(Runtime.getRuntime().availableProcessors());

		return options;
	}
}
