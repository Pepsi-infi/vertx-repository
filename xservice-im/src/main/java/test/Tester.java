package test;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import logic.iml.SocketSessionVerticle;
import server.SocketServerVerticle;
import server.TestUdpServerVerticle;
import server.UdpServerVerticle;
import tp.impl.TpServiceImpl;

public class Tester {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		// vertx.deployVerticle(MongoVerticle.class.getName(), new
		// DeploymentOptions().setConfig(config()));
		// vertx.deployVerticle(ValidateServerVerticle.class.getName());
		// ----------------------
		// vertx.deployVerticle(SocketServerVerticle.class.getName());
		// vertx.deployVerticle(TpServiceImpl.class.getName());
		// vertx.deployVerticle(SocketSessionVerticle.class.getName());
		// vertx.deployVerticle(UdpServerVerticle.class.getName());
		// vertx.deployVerticle(TestUdpServerVerticle.class.getName());
		// ----------------------
		vertx.deployVerticle(TestUdpServerVerticle.class.getName(), new DeploymentOptions().setInstances(1));

		// MongoService service = MongoService.createProxy(vertx);
		// JsonObject test = new JsonObject().put("collection", "message").put("data",
		// new JsonObject().put("test", 1111));
		// service.saveData(test, res -> {
		// });

		// vertx.setPeriodic(1000, handler -> {
		// DatagramSocket socketSend = vertx.createDatagramSocket(new
		// DatagramSocketOptions());
		// JsonObject j = new JsonObject();
		// j.put("key", "test");
		// socketSend.send(j.encode(), 1101, "192.168.2.220", res -> {
		//
		// });
		// });
		//
		vertx.createHttpServer();

		String a = "a";
		System.out.println(a);

		a = "abc";
		System.out.println(a);

		String b = "b";

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
