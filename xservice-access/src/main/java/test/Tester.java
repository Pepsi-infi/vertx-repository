package test;

import cluster.impl.ConsistentHashingVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import logic.impl.C2CVerticle;
import logic.impl.SessionVerticle;
import server.RestServerVerticle;
import server.IMServerVerticle;

public class Tester {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		// vertx.deployVerticle(IMServerVerticle.class.getName(),
		// readBossOpts().setConfig(config()));
		// vertx.deployVerticle(ConsistentHashingVerticle.class.getName(),
		// readBossOpts().setConfig(config()));
		// vertx.deployVerticle(RestServerVerticle.class.getName(),
		// readBossOpts().setConfig(config()));
		// vertx.deployVerticle(C2CVerticle.class.getName(),
		// readBossOpts().setConfig(config()));

		vertx.deployVerticle(TCPTest.class.getName());

		/**
		 * Instance should be 1 because of ehcache.
		 */
		// vertx.deployVerticle(SessionVerticle.class.getName());

		/**
		 * Instance should be 1 because of ehcache.
		 */
		// vertx.deployVerticle(SessionVerticle.class.getName(), new
		// DeploymentOptions().setConfig(config()));

		// LoginMsgData t = new LoginMsgData();
		// System.out.println(Json.encodePrettily(t));

		// byte[] s = ByteUtil.unsignedShortToByte2(44);
		// for (byte b : s) {
		// System.out.print(b);
		// }
	}

	private static JsonObject config() {
		String prop = "{\"service.host\":\"127.0.0.1\", \"db_name\":\"im-mc\", \"connection_string\":\"mongodb://10.10.10.178:27017\", \"username\":\"im-mc\", \"password\":\"im-mc\", \"authMechanism\":\"SCRAM-SHA-1\"}";

		JsonObject jsonObject = new JsonObject(prop);
		return jsonObject;
	}

	public static DeploymentOptions readBossOpts() {
		DeploymentOptions options = new DeploymentOptions();
		options.setInstances(Runtime.getRuntime().availableProcessors());

		return options;
	}
}
