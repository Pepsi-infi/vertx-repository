package test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.impl.Utils;
import io.vertx.rxjava.core.MultiMap;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.web.client.HttpResponse;
import io.vertx.rxjava.ext.web.client.WebClient;
import io.vertx.rxjava.ext.web.codec.BodyCodec;
import rx.Single;

public class Tester {

	public static void main(String[] args) throws UnsupportedEncodingException {
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
		// vertx.deployVerticle(TestUdpServerVerticle.class.getName(), new
		// DeploymentOptions().setInstances(1));

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
		WebClient webClient = WebClient.create(vertx);

		MultiMap form = MultiMap.caseInsensitiveMultiMap();
		form.add("uid", "13666098");

		form.add("time", Utils.normalizePath("2017-09-29 05:44:59"));
		form.add("msg",
				Utils.normalizePath("{\"lon\":116.432981,\"lat\":39.920212,\"groupId\":\"34\",\"module\":5001}").substring(1));

		System.out.println(URLEncoder
				.encode("{\"lon\":116.432981,\"lat\":39.920212,\"groupId\":\"34\",\"module\":5001}", "UTF-8"));
		System.out.println(URLEncoder.encode("2017-09-29 05:44:59", "UTF-8"));

		System.out.println(URLEncoder
				.encode("{\"lon\":116.432981,\"lat\":39.920212,\"groupId\":\"34\",\"module\":5001}", "UTF-8"));
		Single<HttpResponse<String>> httpRequest = webClient
				.post(18080, "10.10.10.105", "/webservice/passenger/webservice/chat/updateSimpleOnlineState/")
				.as(BodyCodec.string()).rxSendForm(form);

		System.out.println(Utils.normalizePath("{\"cmd\":14,\"data\":{\"lat\":39.920256,\"lon\":116.433006}}").substring(1));

		httpRequest.subscribe(resp -> {
			if (resp.statusCode() == 200) {
				System.out.println(resp.body());
			} else {
				System.out.println(resp.statusCode() + resp.statusMessage());
			}
		});

		String a = "a";
		System.out.println(a);

		a = "abc";
		System.out.println(a);

		String b = "b";

	}

	public void updateOnlineSimple(String uid, String date, JsonObject content, Handler<AsyncResult<String>> result) {

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
