package service.impl;

import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.MongoClient;
import service.DriverService;
import xservice.BaseServiceVerticle;

import java.util.List;

/**
 * Created by lufei
 * Date : 2017/8/23 11:18
 * Description :
 */
public class DriverServiceImpl extends BaseServiceVerticle implements DriverService {

    private static final Logger logger = LoggerFactory.getLogger(DriverServiceImpl.class);

    private static final String COLLECTION_DRIVER = "driver";

    private MongoClient mongoClient;

    private static final int PAGE_SIEZ = 20;

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
    public void queryDriver(JsonObject query, int page, int size, Handler<AsyncResult<List<JsonObject>>> result) {
        if (size < 0) {
            size = PAGE_SIEZ;
        }
        FindOptions findOptions = new FindOptions();
        findOptions.setSkip(calcPage(page, size)).setLimit(size);
        mongoClient.findWithOptions(COLLECTION_DRIVER, query, findOptions, res -> {
            if (res.succeeded()) {
                result.handle(Future.succeededFuture(res.result()));
            } else {
                logger.error("query driver from db error.", res.cause());
                result.handle(Future.failedFuture(res.cause()));
            }
        });

    }



    protected int calcPage(int page, int limit) {
        if (page <= 0) {
            return 0;
        }
        return limit * (page - 1);
    }
}
