package test;

import api.RestChannelVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import service.impl.ChannelServiceImpl;

/**
 * Created by zhushenghao1 on 17/5/4.
 */
public class ChannelTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelServiceImpl.class);
    private Vertx vertx;
    public ChannelTest(Vertx vertx) {
        this.vertx = vertx;
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        ChannelTest test = new ChannelTest(vertx);
        test.deployVerticle(RestChannelVerticle.class.getName());
    }

    private void deployVerticle(String verticleName){
        Future<String> future = Future.future();
        future.setHandler(ar->LOGGER.info(ar.succeeded()?"success:"+ar.result():"failed:"+ar.cause()));
        vertx.deployVerticle(verticleName, new DeploymentOptions().setConfig(config()),future.completer());
    }

    private JsonObject config() {
        String prop = "{\"service.name\":\"le.mobile.channel\"," +
                "\"service.host\":\"127.0.0.1\"," +
                "\"service.port\":1103," +
                "\"service.root\":\"/mobile-channel\"}";
        JsonObject jsonObject = new JsonObject(prop);
        return jsonObject;
    }

}
