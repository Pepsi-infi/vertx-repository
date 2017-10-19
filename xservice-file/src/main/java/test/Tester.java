package test;

import io.vertx.core.Vertx;
import module.transcoding.TranscodingVerticle;
import server.FileServerVerticle;

public class Tester {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();

		// vertx.deployVerticle(FileServerVerticle.class.getName());
		vertx.deployVerticle(TranscodingVerticle.class.getName());
	}

}
