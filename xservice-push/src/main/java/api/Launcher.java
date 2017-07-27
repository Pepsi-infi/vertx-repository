package api;

import channel.AmqpConsumerVerticle;
import channel.MiPushVerticle;
import channel.SocketVerticle;
import io.vertx.core.Vertx;

public class Launcher {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();

		vertx.deployVerticle(AmqpConsumerVerticle.class.getName());
		vertx.deployVerticle(MiPushVerticle.class.getName());
		vertx.deployVerticle(SocketVerticle.class.getName());
	}

}
