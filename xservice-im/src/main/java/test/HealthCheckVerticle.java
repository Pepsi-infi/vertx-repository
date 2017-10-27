package test;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class HealthCheckVerticle extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
		super.start();

		System.out.println("start");
	}

	@Override
	public void stop(Future<Void> stopFuture) throws Exception {
		// TODO Auto-generated method stub
		super.stop();

		System.out.println("stop");
	}
}
