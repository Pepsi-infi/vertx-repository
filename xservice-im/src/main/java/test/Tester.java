package test;

import io.vertx.core.Vertx;
import server.ValidateServerVerticle;

public class Tester {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		// vertx.deployVerticle(MongoVerticle.class.getName(), new
		// DeploymentOptions().setConfig(config()));
		vertx.deployVerticle(ValidateServerVerticle.class.getName());

		// MongoService service = MongoService.createProxy(vertx);
		// JsonObject test = new JsonObject().put("collection", "message").put("data",
		// new JsonObject().put("test", 1111));
		// service.saveData(test, res -> {
		// });
	}

	// private static JsonObject config() {
	// String prop = "{\"db_name\":\"im-mc\",
	// \"connection_string\":\"mongodb://10.10.10.178:27017\",
	// \"username\":\"im-mc\", \"password\":\"im-mc\",
	// \"authMechanism\":\"SCRAM-SHA-1\"}";
	//
	// JsonObject jsonObject = new JsonObject(prop);
	// return jsonObject;
	// }
}
