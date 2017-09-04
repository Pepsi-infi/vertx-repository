package test;

import api.RestConfigVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;
import service.impl.ImCommonLanguageServiceImpl;
import service.impl.SensitiveWordServiceImpl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lufei Date : 2017/7/25 14:27 Description :
 */
public class ConfigTest {

    private Vertx vertx;

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
