package com.message.push;

import api.RestPushVerticle;
import constant.PushConsts;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import message.DriverMessageConsumer;
import service.impl.DriverServiceImpl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lufei
 * Date : 2017/8/22 18:45
 * Description :
 */
public class RocketMqTest {

    private static final Logger logger = LoggerFactory.getLogger(RocketMqTest.class);

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        DeploymentOptions options = new DeploymentOptions();
        options.setConfig(getJsonConf("config.json"));
//        vertx.deployVerticle(DriverMessageProduce.class.getName(), options);
        vertx.deployVerticle(DriverMessageConsumer.class.getName(), options);
        vertx.deployVerticle(DriverServiceImpl.class.getName(), options);
        vertx.deployVerticle(RestPushVerticle.class.getName(), options);
//        deployRestService(vertx);
    }


    private static Future<Void> deployRestService(Vertx vertx) {
        Future<String> future = Future.future();
        DeploymentOptions deploymentOptions = new DeploymentOptions();
        vertx.deployVerticle(RestPushVerticle.class.getName(), deploymentOptions, future.completer());

        return future.map(r -> null);
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
