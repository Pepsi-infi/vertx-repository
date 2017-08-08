package test;

import cluster.impl.ConsistentHashingVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import server.TCPServerVerticle;

public class Tester {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(TCPServerVerticle.class.getName());
		vertx.deployVerticle(ConsistentHashingVerticle.class.getName());
	}

	private static JsonObject config() {
		String prop = "{\"db_name\":\"im-mc\", \"connection_string\":\"mongodb://10.10.10.178:27017\", \"username\":\"im-mc\", \"password\":\"im-mc\", \"authMechanism\":\"SCRAM-SHA-1\"}";

		JsonObject jsonObject = new JsonObject(prop);
		return jsonObject;
	}
}
