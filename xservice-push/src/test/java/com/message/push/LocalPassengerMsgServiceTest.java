package com.message.push;

import api.PassengerMsgVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import service.impl.PassengerMessageServiceImpl;
import util.HttpUtil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LocalPassengerMsgServiceTest extends AbstractVerticle{

	private static final Logger logger = LoggerFactory.getLogger(LocalPassengerMsgServiceTest.class);

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		DeploymentOptions options = new DeploymentOptions();
		vertx.deployVerticle(PassengerMsgVerticle.class.getName());
		vertx.deployVerticle(PassengerMessageServiceImpl.class.getName(), options.setConfig(getJsonConf("config.json")));
		vertx.deployVerticle(HttpUtil.class.getName());
	}


	public static JsonObject getJsonConf(String configPath) {
		configPath = "local/" + configPath;
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
