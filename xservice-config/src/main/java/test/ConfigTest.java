package test;

import api.RestConfigVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;
import service.impl.ImCommonLanguageServiceImpl;
import service.impl.SensitiveWordServiceImpl;

/**
 * Created by lufei Date : 2017/7/25 14:27 Description :
 */
public class ConfigTest {

	public static void main(String[] args) {
		try {
			Vertx vertx = Vertx.vertx();
			ConfigTest msgStatTest = new ConfigTest();
			msgStatTest.deployRestService(vertx);

			DeploymentOptions deploymentOptions = new DeploymentOptions();
			deploymentOptions.setConfig(config());

			vertx.deployVerticle(ImCommonLanguageServiceImpl.class.getName(), deploymentOptions);
			vertx.deployVerticle(SensitiveWordServiceImpl.class.getName(), deploymentOptions);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Future<Void> deployRestService(Vertx vertx) {
		Future<String> future = Future.future();
		DeploymentOptions deploymentOptions = new DeploymentOptions();
		deploymentOptions.setConfig(config());
		vertx.deployVerticle(RestConfigVerticle.class.getName(), deploymentOptions, future.completer());

		return future.map(r -> null);
	}

	private static JsonObject config() {
		String conf = "{\"mysql\":{\"mc-config\":{\"host\":\"192.168.0.18\",\"port\":3306,\"maxPoolSize\":100,\"username\":\"sqyc_test\",\"password\":\"sqyc_test@01zhuanche.com\",\"database\":\"mc_admin\",\"charset\":\"UTF-8\",\"queryTimeout\":3000}},\"service.port\":9200}";
		JsonObject jsonObject = new JsonObject(conf);

		return jsonObject;
	}
}
