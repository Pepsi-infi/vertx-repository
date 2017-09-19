import api.StartVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lufei Date : 2017/7/25 14:27 Description :
 */
public class LocalMsgStatTest {

	private Vertx vertx;

	public static void main(String[] args) {
		try {
			Vertx vertx = Vertx.vertx();
			DeploymentOptions deploymentOptions = new DeploymentOptions();
			deploymentOptions.setConfig(config());
			vertx.deployVerticle(StartVerticle.class.getName(), deploymentOptions);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static JsonObject config() {
		ClassLoader ctxClsLoader = Thread.currentThread().getContextClassLoader();
		InputStream is = ctxClsLoader.getResourceAsStream("local/config.json");
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
