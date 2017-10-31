package test;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class Tester {

	public static void main(String[] args) throws InterruptedException {
		Vertx vertx = Vertx.vertx();

		// vertx.deployVerticle(FileServerVerticle.class.getName());
		// vertx.deployVerticle(TranscodingVerticle.class.getName());
		vertx.deployVerticle("groovy:file/transcoding/Transcoding.groovy", new DeploymentOptions().setWorker(true));

		Thread.sleep(3000);

		vertx.setPeriodic(1000, s -> {
			System.out.println("aaaaaa");
			vertx.eventBus().send("file.transcoding.Transcoding", "aaa");
		});
	}

}
