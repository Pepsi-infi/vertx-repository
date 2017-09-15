package service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import domain.Pager;
import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.MongoClient;
import model.Result;
import service.DriverService;
import utils.JsonUtil;
import xservice.BaseServiceVerticle;

/**
 * Created by lufei Date : 2017/8/23 11:18 Description :
 */
public class DriverServiceImpl extends BaseServiceVerticle implements DriverService {

	private static final Logger logger = LoggerFactory.getLogger(DriverServiceImpl.class);

	private static final String COLLECTION_DRIVER = "driver";

	private MongoClient mongoClient;

	public DriverServiceImpl() {
	}

	@Override
	public void start() throws Exception {
		super.start();

		XProxyHelper.registerService(DriverService.class, vertx, this, DriverService.SERVICE_ADDRESS);
		publishEventBusService(DriverService.SERVICE_NAME, DriverService.SERVICE_ADDRESS, DriverService.class);

		JsonObject jsonObject = config().getJsonObject("mongo.config");
		mongoClient = MongoClient.createShared(vertx, jsonObject);
	}

	@Override
	public void saveDriver(JsonObject driver, Handler<AsyncResult<JsonObject>> result) {
		driver.put("_id", driver.getLong("driverId"));
		mongoClient.save(COLLECTION_DRIVER, driver, ares -> {
			if (ares.succeeded()) {
				result.handle(Future.succeededFuture(new JsonObject().put("result", 0)));
			} else {
				logger.error("save driver to db error ", ares.cause());
				result.handle(Future.failedFuture(ares.cause()));
			}
		});
	}

	@Override
	public void queryDriver(JsonObject query, int page, int size, Handler<AsyncResult<String>> result) {
		FindOptions findOptions = new FindOptions();
		findOptions.setSkip(calcPage(page, size)).setLimit(size);
		List<Future> futures = new ArrayList<>();
		Future<List<JsonObject>> listFuture = Future.future();
		futures.add(listFuture);
		mongoClient.findWithOptions(COLLECTION_DRIVER, query, findOptions, listFuture.completer());

		Future<Long> countFuture = Future.future();
		mongoClient.count(COLLECTION_DRIVER, query, countFuture.completer());
		futures.add(countFuture);

		CompositeFuture compositeFuture = CompositeFuture.all(futures);
		compositeFuture.setHandler(asr1 -> {
			if (asr1.succeeded()) {
				List<JsonObject> jsonObjects = asr1.result().resultAt(0);
				List<Map> lists = Lists.transform(jsonObjects, new Function<JsonObject, Map>() {
					@Nullable
					@Override
					public Map apply(@Nullable JsonObject jsonObject) {
						return jsonObject.mapTo(Map.class);
					}
				});
				long count = asr1.result().resultAt(1);
				Pager<Map> pager = new Pager(count, page, size, lists);
				Result res = Result.success(pager);
				result.handle(Future.succeededFuture(JsonUtil.encodePrettily(res)));
			} else {
				logger.error(asr1.cause());
				result.handle(Future.failedFuture(asr1.cause()));
			}
		});
	}

	public void queryBatchDriver(JsonObject query, Handler<AsyncResult<JsonObject>> result) {
		JsonArray driverArray = new JsonArray();
		JsonObject jsonObject = new JsonObject();
		mongoClient.findBatch(COLLECTION_DRIVER, query, res -> {
			if (res.succeeded()) {
				if (res.result() != null) {
					driverArray.add(res.result());
				} else {
					result.handle(Future.succeededFuture(jsonObject.put("driverList", driverArray)));
				}
				logger.info("result size" + driverArray.size());
			}
		});

	}

	protected int calcPage(int page, int limit) {
		if (page <= 0) {
			return 0;
		}
		return limit * (page - 1);
	}

	@Override
	public void queryDriverCount(JsonObject query, Handler<AsyncResult<Long>> resultHandler) {
		Future<Long> countFuture = Future.future();
		mongoClient.count(COLLECTION_DRIVER, query, countFuture.completer());
		countFuture.setHandler(handler -> {
			if (handler.succeeded()) {
				resultHandler.handle(Future.succeededFuture(handler.result()));
			} else {
				logger.error("司机数量获取异常", handler.cause());
				resultHandler.handle(Future.failedFuture(handler.cause()));
			}
		});

	}

	@Override
	public void queryOneDriver(JsonObject query, Handler<AsyncResult<String>> result) {
		
		
	}
	
	
}
