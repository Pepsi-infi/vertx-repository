package test;

import api.RestStatVerticle;
import dao.impl.DeviceDaoImpl;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;
import service.impl.DeviceServiceImpl;
import service.impl.MsgStatServiceImpl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lufei Date : 2017/7/25 14:27 Description :
 */
public class MsgStatTest {

	private Vertx vertx;

	public static void main(String[] args) {
		try {
			Vertx vertx = Vertx.vertx();
			MsgStatTest msgStatTest = new MsgStatTest();
			msgStatTest.deployRestService();

			DeploymentOptions deploymentOptions = new DeploymentOptions();
			deploymentOptions.setConfig(config());

			vertx.deployVerticle(MsgStatServiceImpl.class.getName(), deploymentOptions);

			vertx.deployVerticle(DeviceDaoImpl.class.getName(), deploymentOptions);
			vertx.deployVerticle(DeviceServiceImpl.class.getName(), deploymentOptions);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// private Future<Void> deployVerticle(String name) {
	// Future<String> future = Future.future();
	// DeploymentOptions deploymentOptions = new DeploymentOptions();
	// deploymentOptions.setConfig(config());
	// vertx.deployVerticle(name, deploymentOptions, future.completer());
	// return future.map(r -> null);
	// }

	private Future<Void> deployRestService() {
		Future<String> future = Future.future();
		DeploymentOptions deploymentOptions = new DeploymentOptions();
		deploymentOptions.setConfig(config());
		vertx.deployVerticle(RestStatVerticle.class.getName(), deploymentOptions, future.completer());
		// vertx.deployVerticle(RestDeviceVerticle.class.getName(), deploymentOptions,
		// future.completer());
		// vertx.deployVerticle(CronMsgStatVerticle.class.getName(), deploymentOptions,
		// future.completer());
		// vertx.deployVerticle(CronTransferDevcieVerticle.class.getName(),
		// deploymentOptions, future.completer());
		// vertx.deployVerticle(RestDeviceVerticle.class.getName(), deploymentOptions,
		// future.completer());
		return future.map(r -> null);
	}

	private static JsonObject config() {
		ClassLoader ctxClsLoader = Thread.currentThread().getContextClassLoader();
		InputStream is = ctxClsLoader.getResourceAsStream("dev/config.json");
		BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(is)));
		try {
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return new JsonObject(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
