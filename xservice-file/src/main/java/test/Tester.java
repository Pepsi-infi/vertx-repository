package test;

import io.vertx.core.Vertx;
import server.FileServerVerticle;

public class Tester {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();

		vertx.deployVerticle(FileServerVerticle.class.getName());
	}

}
