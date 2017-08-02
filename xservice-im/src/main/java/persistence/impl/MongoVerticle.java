package persistence.impl;

import helper.XProxyHelper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import persistence.MongoService;

public class MongoVerticle extends AbstractVerticle implements MongoService {

	private MongoClient client;

	@Override
	public void start() throws Exception {
		client = MongoClient.createShared(vertx, config());

		XProxyHelper.registerService(MongoService.class, vertx, this, MongoService.SERVICE_ADDRESS);
	}

	@Override
	public void saveData(JsonObject json, Handler<AsyncResult<JsonObject>> resultHandler) {
		String collection = json.getString("collection");
		JsonObject doc = json.getJsonObject("data");

		client.save(collection, doc, res -> {
			if (res.succeeded()) {
				resultHandler.handle(Future.succeededFuture(new JsonObject().put("result", 0)));
			} else {
				resultHandler.handle(Future.failedFuture(res.cause()));
			}
		});
	}

	@Override
	public void updateData(JsonObject json, Handler<AsyncResult<JsonObject>> resultHandler) {
		String collection = json.getString("collection");
		JsonObject doc = json.getJsonObject("data");

	}
}
