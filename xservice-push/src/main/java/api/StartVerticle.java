package api;

import channel.ApplePushVerticle;
import channel.MessagePushContainer;
import channel.MiPushVerticle;
import channel.SocketVerticle;
import constant.PushConsts;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.AbstractVerticle;
import service.impl.MsgRecordServiceImpl;
import service.impl.RedisServiceImpl;
import xservice.HttpClientVerticle;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StartVerticle extends AbstractVerticle{

    private static final Logger logger = LoggerFactory.getLogger(StartVerticle.class);
    @Override
    public void start() throws Exception {
        super.start();
        // 提供EventBus服务

        this.deployVerticle(MsgRecordServiceImpl.class.getName());
        this.deployVerticle(RedisServiceImpl.class.getName());

        this.deployVerticle(MiPushVerticle.class.getName());
		this.deployVerticle(SocketVerticle.class.getName());
        this.deployVerticle(ApplePushVerticle.class.getName());

        this.deployVerticle(MessagePushContainer.class.getName());
        this.deployVerticle(HttpClientVerticle.class.getName());
        // 提供其他非EventBus服务
    }

    public void deployVerticle(String verticleName) {
        Future<String> future = Future.future();
        future.setHandler(ar -> logger.info(ar.succeeded() ? "success:" + ar.result() : "failed:" + ar.cause()));

        //本地启动注掉1，打开2 ;
        //1
        vertx.deployVerticle(verticleName, readBossOpts().setConfig(config()), future.completer());
        //2
//      vertx.deployVerticle(verticleName, readBossOpts().setConfig(getJsonConf("config.json")), future.completer());
    }

    public static DeploymentOptions readBossOpts() {
        DeploymentOptions options = new DeploymentOptions();
        options.setInstances(Runtime.getRuntime().availableProcessors());
        return options;
    }
















    public static JsonObject getJsonConf(String configPath) {
        configPath = PushConsts.ENV_PATH + "/" + configPath;
        logger.info("config Path: " + configPath);
        JsonObject conf = new JsonObject();
        ClassLoader ctxClsLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = ctxClsLoader.getResourceAsStream(configPath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(is)));
        try {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            conf = new JsonObject(sb.toString());
            logger.info("Loaded config file from [" + configPath + "] : " + conf.toString());
        } catch (Exception e) {
            logger.error("Failed to load config file" + e);
        }
        return conf;
    }

}
