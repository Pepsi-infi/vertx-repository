package test;

import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import server.TCPServerVerticle;
import util.ByteUtil;

public class Tester {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(TCPServerVerticle.class.getName());
		// vertx.deployVerticle(ConsistentHashingVerticle.class.getName());
		// vertx.deployVerticle(HttpServerVerticle.class.getName());

		/**
		 * Instance should be 1 because of ehcache.
		 */
		// vertx.deployVerticle(SessionVerticle.class.getName(), new
		// DeploymentOptions().setConfig(config()));

//		LoginMsgData t = new LoginMsgData();
//		System.out.println(Json.encodePrettily(t));

//		byte[] s = ByteUtil.unsignedShortToByte2(44);
//		for (byte b : s) {
//			System.out.print(b);
//		}
	}

	private static JsonObject config() {
		String prop = "{\"db_name\":\"im-mc\", \"connection_string\":\"mongodb://10.10.10.178:27017\", \"username\":\"im-mc\", \"password\":\"im-mc\", \"authMechanism\":\"SCRAM-SHA-1\"}";

		JsonObject jsonObject = new JsonObject(prop);
		return jsonObject;
	}
}
