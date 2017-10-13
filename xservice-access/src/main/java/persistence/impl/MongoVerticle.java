package persistence.impl;

import java.util.ArrayList;
import java.util.List;

import helper.XProxyHelper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.BulkOperation;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.mongo.MongoClientBulkWriteResult;
import persistence.MongoService;

public class MongoVerticle extends AbstractVerticle implements MongoService {

	private static final Logger logger = LoggerFactory.getLogger(MongoVerticle.class);

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

		client.save(collection, doc, mongoRes -> {
			if (mongoRes.succeeded()) {
				resultHandler.handle(Future.succeededFuture(new JsonObject().put("result", 0)));
			} else {
				resultHandler.handle(Future.failedFuture(mongoRes.cause()));
			}
		});
	}

	@Override
	public void saveDataBatch(JsonObject json, Handler<AsyncResult<JsonObject>> resultHandler) {
		String collection = json.getString("collection");
		JsonObject doc = json.getJsonObject("data");

		List<BulkOperation> opList = new ArrayList<BulkOperation>();
		opList.add(BulkOperation.createInsert(doc));

		if (opList.size() == 100) {
			client.bulkWrite(collection, opList, res -> {
				if (res.succeeded()) {
					MongoClientBulkWriteResult bulkResult = res.result();
					resultHandler.handle(
							Future.succeededFuture(new JsonObject().put("result", bulkResult.getInsertedCount())));
				} else {
					resultHandler.handle(Future.failedFuture(res.cause()));
					logger.error("saveDataBatch " + res.cause());
				}
			});
			logger.info("saveDataBatch opList size={}", opList.size());
			opList = new ArrayList<BulkOperation>();
		}
	}

	/**
	 * {"msgId":1111111,"cmd":111,"timeStamp":11111,"date":"2017-01-01","toTel":123123123,"sceneId":11111}
	 * 
	 * @param json
	 */
	public void findOffLineMessage(JsonObject query, Handler<AsyncResult<JsonObject>> resultHandler) {
		FindOptions options = new FindOptions();
		options.setLimit(50);
		client.findBatchWithOptions("message", query, options, resultHandler);
	}

	@Override
	public void updateData(JsonObject json, Handler<AsyncResult<JsonObject>> resultHandler) {
		String collection = json.getString("collection");
		JsonObject doc = json.getJsonObject("data");

	}
}
