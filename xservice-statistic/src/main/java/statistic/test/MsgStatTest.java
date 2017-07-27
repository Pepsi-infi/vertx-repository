package statistic.test;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;
import statistic.api.RestMsgStatVerticle;
import statistic.dao.msg.impl.DeviceDaoImpl;
import statistic.service.impl.MsgStatServiceImpl;
import statistic.service.impl.DeviceServiceImpl;

/**
 * Created by lufei
 * Date : 2017/7/25 14:27
 * Description :
 */
public class MsgStatTest {

    private Vertx vertx;

    public MsgStatTest(Vertx vertx) {
        this.vertx = vertx;
    }

    public static void main(String[] args) {
        try {
            Vertx vertx = Vertx.vertx();
            MsgStatTest msgStatTest = new MsgStatTest(vertx);
            msgStatTest.deployRestService();

            DeploymentOptions deploymentOptions = new DeploymentOptions();
            deploymentOptions.setConfig(config());

            vertx.deployVerticle(MsgStatServiceImpl.class.getName(), deploymentOptions);
            vertx.deployVerticle(DeviceDaoImpl.class.getName(), deploymentOptions);
            vertx.deployVerticle(DeviceServiceImpl.class.getName(), deploymentOptions);

            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private Future<Void> deployVerticle(String name) {
//        Future<String> future = Future.future();
//        DeploymentOptions deploymentOptions = new DeploymentOptions();
//        deploymentOptions.setConfig(config());
//        vertx.deployVerticle(name, deploymentOptions, future.completer());
//        return future.map(r -> null);
//    }

    private Future<Void> deployRestService() {
        Future<String> future = Future.future();
        DeploymentOptions deploymentOptions = new DeploymentOptions();
        deploymentOptions.setConfig(config());
        vertx.deployVerticle(RestMsgStatVerticle.class.getName(), deploymentOptions, future.completer());
//        vertx.deployVerticle(RestDeviceVerticle.class.getName(), deploymentOptions, future.completer());
        return future.map(r -> null);
    }

    private static JsonObject config() {
        String prop = "{\"service.name\":\"le.mc.statistic\",\"service.host\":\"127.0.0.1\",\"service.port\":2203,\"service.root\":\"/mc-statistic\","
                + "\"redis.properties\":{ \"host\": \"localhost\", \"port\": 6379,\"encoding\": \"UTF-8\",\"tcpKeepAlive\": \"true\",\"tcpNoDelay\": \"true\",\"password\":\"redis\"}}";
        JsonObject jsonObject = new JsonObject(prop);
        return jsonObject;
    }
}
