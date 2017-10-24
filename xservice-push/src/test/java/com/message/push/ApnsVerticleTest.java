package com.message.push;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.Json;
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
		// 9da57cd69196589bdfe93d12050f52613c84179338be2c8327eead94fc9fb12e
		// 564d6ea438cbbab366df45153c6bc0b8e34fa8f2c79e7fbdb29eebd643d254da

		// aff5175d8ad711399fc071c2bb7e98025e5037e1d911223faab298082dda3c89

		// BwgJYcBIOpZj5A4HEHu2nXUeiIEGRLqGVW38hLaMCtj5v+bo2wOZYOfS2V14TdhI4RWUnQ==
		// 959bfeab4ff644787a12fe72d640cab1ea1210562c7811712500095378c8293c
		msg.setTitle("1");
		msg.setBody(String.valueOf(System.currentTimeMillis()));
		Extend extend = new Extend();
		extend.setJumpPage("8");
		msg.setExtend(extend);

		DeliveryOptions option = new DeliveryOptions();
		option.addHeader("action", "apnsSend");

		eb.send(ApnsVerticle.class.getName() + "local", JsonObject.mapFrom(msg), option);
	}

}
