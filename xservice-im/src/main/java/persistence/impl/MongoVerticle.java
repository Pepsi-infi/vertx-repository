package persistence.impl;

import helper.XProxyHelper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.Counter;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.mongo.MongoClient;
import persistence.MongoService;

public class MongoVerticle extends AbstractVerticle implements MongoService {

	private MongoClient client;

	private Counter counter;

	@Override
	public void start() throws Exception {
		System.out.println("config() " + config());
		client = MongoClient.createShared(vertx, config());

		XProxyHelper.registerService(MongoService.class, vertx, this, MongoService.SERVICE_ADDRESS);

		SharedData sd = vertx.sharedData();
		sd.getCounter("mongo_msg", res -> {
			if (res.succeeded()) {
				counter = res.result();
			} else {
				// Something went wrong!
			}
		});
	}

	@Override
	public void saveData(JsonObject json, Handler<AsyncResult<JsonObject>> resultHandler) {
		String collection = json.getString("collection");
		JsonObject doc = json.getJsonObject("data");

		Future<Long> counterFuture = Future.future();
		counter.getAndIncrement(counterFuture.completer());
		counterFuture.setHandler(res -> {
			if (res.succeeded()) {
				client.save(collection, doc, mongoRes -> {
					if (mongoRes.succeeded()) {
						resultHandler.handle(Future.succeededFuture(new JsonObject().put("result", 0)));
					} else {
						resultHandler.handle(Future.failedFuture(res.cause()));
					}
				});
			} else {
				resultHandler.handle(Future.failedFuture(res.cause()));
				System.out.println(res.cause());
			}
		});
	}

	@Override
	public void updateData(JsonObject json, Handler<AsyncResult<JsonObject>> resultHandler) {
		String collection = json.getString("collection");
		JsonObject doc = json.getJsonObject("data");

	}
}
