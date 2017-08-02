package test;

import io.vertx.core.Vertx;
import logic.impl.C2CVerticle;
import persistence.impl.MongoVerticle;
import server.TCPServerVerticle;

public class Launcher {
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		// vertx.deployVerticle(new TestTCPServerVerticle());
		vertx.deployVerticle(new TCPServerVerticle());
		vertx.deployVerticle(new C2CVerticle());
//		vertx.deployVerticle(new MongoVerticle());
	}
}
