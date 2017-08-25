package service;

import helper.XProxyHelper;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * Created by lufei
 * Date : 2017/8/23 11:13
 * Description :
 */
@ProxyGen
@VertxGen
public interface DriverService {

    public static final String SERVICE_NAME = "dao.driver.eb.service";

    public static final String SERVICE_ADDRESS = "dao-driver-eb-service";

    static DriverService createProxy(Vertx vertx) {
        return XProxyHelper.createProxy(DriverService.class, DriverService.class, vertx, DriverService.SERVICE_ADDRESS);
    }

    void saveDriver(JsonObject jsonObject, Handler<AsyncResult<JsonObject>> result);

    void queryDriver(JsonObject query, int page, int size, Handler<AsyncResult<List<JsonObject>>> result);
}
