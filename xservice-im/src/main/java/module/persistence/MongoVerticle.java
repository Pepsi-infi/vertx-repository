package module.persistence;

import java.util.ArrayList;
import java.util.List;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.BulkOperation;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.mongo.MongoClientBulkWriteResult;
import utils.IPUtil;

public class MongoVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(MongoVerticle.class);

	private MongoClient client;

	private EventBus eb;

	private String innerIP;

	public interface method {

		public static final String saveData = "saveData";

		public static final String findOffLineMessage = "findOffLineMessage";

	}

	@Override
	public void start() throws Exception {

		logger.info("config={}", config().encode());

		innerIP = IPUtil.getInnerIP();

		client = MongoClient.createShared(vertx, config().getJsonObject("mongo"));

		eb = vertx.eventBus();
		eb.<JsonObject>consumer(MongoVerticle.class.getName() + innerIP, res -> {
			MultiMap headers = res.headers();
			JsonObject param = res.body();
			if (headers != null) {
				String action = headers.get("action");
				switch (action) {
				case "saveData":
					saveData(param, r -> {
						res.reply(r.result());
					});
					break;

				default:
					res.reply(1);// Fail!
					break;
				}
			}
		});
	}

	public void saveData(JsonObject json, Handler<AsyncResult<JsonObject>> resultHandler) {
		JsonObject result = new JsonObject();
		String collection = json.getString("collection");
		JsonObject doc = json.getJsonObject("data");

		logger.info("saveData, json={}", json.encode());

		client.save(collection, doc, mongoRes -> {
			if (mongoRes.succeeded()) {
				logger.info("mongoRes={}", mongoRes.result());
				result.put("status", 0);

				resultHandler.handle(Future.succeededFuture(result));
			} else {
				logger.error("mongoRes={}", mongoRes.cause().getMessage());
				result.put("status", 1);

				resultHandler.handle(Future.succeededFuture(result));
			}
		});
	}

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

	public void updateData(JsonObject json) {
		String collection = json.getString("collection");
		JsonObject doc = json.getJsonObject("data");

	}
}
