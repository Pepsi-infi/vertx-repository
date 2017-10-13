package test;

import cluster.impl.ConsistentHashingVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import logic.impl.C2CVerticle;
import logic.impl.IMSessionVerticle;
import persistence.impl.MongoVerticle;
import server.RestIMVerticle;
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

		// vertx.deployVerticle(TCPTest.class.getName());

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

		vertx.deployVerticle(RestIMVerticle.class.getName());
		vertx.deployVerticle(MongoVerticle.class.getName(), new DeploymentOptions().setConfig(getMongo()));
	}

	private static JsonObject getMongo() {
		String m = "{\"host\":\"10.10.10.178\",\"port\":27017,\"serverSelectionTimeoutMS\":30000,\"maxPoolSize\":50,\"minPoolSize\":25,\"maxIdleTimeMS\":300000,\"maxLifeTimeMS\":3600000,\"waitQueueMultiple\":10,\"waitQueueTimeoutMS\":10000,\"maintenanceFrequencyMS\":2000,\"maintenanceInitialDelayMS\":500,\"username\":\"im-mc\",\"password\":\"im-mc\",\"authSource\":\"im-mc\",\"db_name\":\"im-mc\",\"connectTimeoutMS\":3000,\"socketTimeoutMS\":3000,\"sendBufferSize\":8192,\"receiveBufferSize\":8192,\"keepAlive\":true,\"heartbeat.socket\":{\"connectTimeoutMS\":3000,\"socketTimeoutMS\":3000,\"sendBufferSize\":8192,\"receiveBufferSize\":8192,\"keepAlive\":true},\"heartbeatFrequencyMS\":1000,\"minHeartbeatFrequencyMS\":500,\"socket\":[{\"innerIP\":\"10.10.10.102\",\"node\":\"111.206.162.233:8088\"},{\"innerIP\":\"10.10.10.103\",\"node\":\"111.206.162.234:8088\"}]}";
		return new JsonObject(m);
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
