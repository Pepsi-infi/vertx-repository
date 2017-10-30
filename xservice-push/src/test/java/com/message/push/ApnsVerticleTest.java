package com.message.push;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import constant.PushConsts;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import push.apns.ApnsMsg;
import push.apns.ApnsVerticle;
import push.apns.Extend;

public class ApnsVerticleTest {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		DeploymentOptions options = new DeploymentOptions();
		vertx.deployVerticle(ApnsVerticle.class.getName(), options.setWorker(true));

		EventBus eb = vertx.eventBus();

		ApnsMsg msg = new ApnsMsg();
		msg.setDeviceToken("564d6ea438cbbab366df45153c6bc0b8e34fa8f2c79e7fbdb29eebd643d254da");
		msg.setTitle("消息标题");
		msg.setBody("test 发送消息内容");
		Extend extend = new Extend();
		extend.setJumpPage("8");
		msg.setExtend(extend);

		DeliveryOptions option = new DeliveryOptions();
		option.addHeader("action", "apnsSend");

		options.setConfig(ApnsVerticleTest.getJsonConf("config.json"));
		// 请注意：Worker Verticle 和 HTTP/2 不兼容
		vertx.deployVerticle(ApnsVerticle.class.getName(), options.setWorker(false), res -> {
			eb.send(ApnsVerticle.class.getName(), JsonObject.mapFrom(msg), option);
		});

	}

	public static JsonObject getJsonConf(String configPath) {
		configPath = PushConsts.ENV_PATH + "/" + configPath;
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
		} catch (Exception e) {
		}
		return conf;
	}
}
