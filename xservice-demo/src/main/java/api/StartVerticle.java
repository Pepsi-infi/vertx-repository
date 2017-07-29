package api;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.rxjava.core.AbstractVerticle;

public class StartVerticle extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		super.start();
		// 提供EventBus服务

		deployVerticle("server.TCPServer");
		// 提供其他非EventBus服务
	}

	public void deployVerticle(String verticleName) {
		Future<String> future = Future.future();
		vertx.deployVerticle(verticleName, readBossOpts().setConfig(config()), future.completer());
	}

	public static DeploymentOptions readBossOpts() {
		DeploymentOptions options = new DeploymentOptions();
		options.setInstances(Runtime.getRuntime().availableProcessors());
		return options;
	}
}
