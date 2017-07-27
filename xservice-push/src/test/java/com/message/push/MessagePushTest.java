package com.message.push;

import io.vertx.amqpbridge.AmqpBridge;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.MessageProducer;
import io.vertx.core.json.JsonObject;

public class MessagePushTest {
	
	private static Vertx vertx;
	
	public static void main(String[] args) {
		
		vertx=Vertx.vertx();
		
		testPushMessageXiaoMi();
		
	}

	private static void testPushMessageXiaoMi() {
		AmqpBridge bridge=AmqpBridge.create(vertx);
		
		bridge.start("localhost", 8890, res->{
			
			MessageProducer<JsonObject> mp=bridge.createProducer("testXiaoMi");
			
			JsonObject json=new JsonObject();
			json.put("devicePushType", "2");
			//json.put("xiaoMiQueue", "")
			
		});
		
	}

}
