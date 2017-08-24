package test;

import io.vertx.core.Vertx;
import server.UploadServerVerticle;

public class Tester {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();

		vertx.deployVerticle(UploadServerVerticle.class.getName());
	}

}
