package com.message.push;

import java.util.UUID;

import constant.ConnectionConsts;
import io.vertx.amqpbridge.AmqpBridge;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.eventbus.MessageProducer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.sql.UpdateResult;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import util.PropertiesLoaderUtils;

public class MessagePushTest {

	private static Vertx vertx;

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {

		vertx = Vertx.vertx();

		// testPushMessageXiaoMi();

		 testMysql();

		//testMQSend();

		//testMQConsume();
		
		//testRedis();

	}

	private static void testRedis() {
		RedisOptions config = new RedisOptions().setAddress(ConnectionConsts.redis_server_address);
		RedisClient redisClient = RedisClient.create(vertx, config);

		if (redisClient == null) {
			System.out.println("redis服务器连接失败");
			return;
		}
		System.out.println("redis服务器连接成功");
		
		redisClient.set("socket", "hello123", res->{
			
			if(res.succeeded()){
				System.out.println("消息发送成功");
			}
			
		});
		
		redisClient.get("socket", res->{
			if(res.succeeded()){
				System.out.println("获取结果："+res.result());
			}
		});
		
	}

	private static void testMQSend() {
		AmqpBridge bridge = AmqpBridge.create(vertx);

		bridge.start(ConnectionConsts.activemq_server_url, ConnectionConsts.activemq_server_port, res -> {
			MessageProducer<JsonObject> producer = bridge.createProducer("ylf");

			JsonObject amqpMsgPayload = new JsonObject();
			amqpMsgPayload.put("content", "hello123");

			producer.send(amqpMsgPayload);
		});

	}

	private static void testMQConsume() {
		AmqpBridge bridge = AmqpBridge.create(vertx);

		// bridge.start

		bridge.start(ConnectionConsts.activemq_server_url, ConnectionConsts.activemq_server_port, res -> {

			// if (res.succeeded()) {
			System.out.println("连接结果" + res.succeeded());

			MessageConsumer<JsonObject> consumer = bridge.createConsumer("ylf");

			consumer.handler(msg -> {

				JsonObject recieveMsg = msg.body();

			});
			// } else {
			// System.out.println("mq服务器连接失败");
			// return;
			// }

		});

	}

	private static void testMysql() throws InstantiationException, IllegalAccessException {
		JsonObject jsonObject = new JsonObject().put("host",
		 PropertiesLoaderUtils.multiProp.getProperty("host")).put("port",
		 PropertiesLoaderUtils.multiProp.getProperty("port")).put("username",
		 PropertiesLoaderUtils.multiProp.getProperty("root")).put("password",
		 "password")
		 .put("database",
		 PropertiesLoaderUtils.multiProp.getProperty("database"));

//		jsonObject.put("host", "localhost").put("port", "3306").put("username", "root").put("password", "123456")
//				.put("database", "kuaidian");

		SQLClient sqlClient = MySQLClient.createShared(vertx, jsonObject);

		vertx.executeBlocking(future -> {
			sqlClient.getConnection(result -> {
				String insertSql = "insert into amqp_consume_message " + "(amqp_msg_id,channel,msg_body,status,"
						+ "created_time,updated_time) " + "values " + "(?,?,?,?,now(),now())";

				SQLConnection connection = result.result();

				JsonArray array = new JsonArray().add(UUID.randomUUID().toString()).add("xiaomi").add("hello").add(1);

				connection.updateWithParams(insertSql, array, sqlRes -> {

					if (sqlRes.succeeded()) {
						UpdateResult upres = sqlRes.result();
						future.complete(upres);
					}

				});
			});
		}, res -> {
			System.out.println(res.result());
		});

	}

	private static void testPushMessageXiaoMi() {
		AmqpBridge bridge = AmqpBridge.create(vertx);

		bridge.start("localhost", 8890, res -> {

			MessageProducer<JsonObject> mp = bridge.createProducer("testXiaoMi");

			JsonObject json = new JsonObject();
			json.put("devicePushType", "2");
			// json.put("xiaoMiQueue", "")

		});

	}

}
