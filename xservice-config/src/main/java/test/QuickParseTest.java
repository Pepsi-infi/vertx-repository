package test;

import config.quickphrase.QuickPhraseConfigVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * @author JHMI on 2017/11/6.
 */
public class QuickParseTest {
    private static JsonObject config() {
        String conf = "{\"mysql\":{\"mc-config\":{\"host\":\"192.168.0.18\",\"port\":3306,\"maxPoolSize\":100,\"username\":\"sqyc_test\",\"password\":\"sqyc_test@01zhuanche.com\",\"database\":\"mc_admin\",\"charset\":\"UTF-8\",\"queryTimeout\":3000}},\"service.port\":9200}";
        JsonObject jsonObject = new JsonObject(conf);

        return jsonObject;
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        DeploymentOptions deploymentOptions = new DeploymentOptions();
        deploymentOptions.setConfig(config());
        System.out.println("--"+QuickPhraseConfigVerticle.class.getName());
        vertx.deployVerticle(QuickPhraseConfigVerticle.class.getName(),deploymentOptions);
    }
}
