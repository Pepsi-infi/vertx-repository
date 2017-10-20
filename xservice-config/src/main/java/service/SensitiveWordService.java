package service;

import helper.XProxyHelper;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * Created by lufei
 * Date : 2017/9/4 11:53
 * Description :
 */
@ProxyGen
@VertxGen
public interface SensitiveWordService {

    public static final String SERVICE_NAME = "http.sensitiveWord.eb.service";

    public static final String SERVICE_ADDRESS = "http-sensitiveWord-eb-service";

    static SensitiveWordService createProxy(Vertx vertx) {
        return XProxyHelper.createProxy(SensitiveWordService.class, SensitiveWordService.class, vertx, SensitiveWordService.SERVICE_ADDRESS);
    }

    void addSensitiveWord(JsonObject jsonObject, Handler<AsyncResult<JsonObject>> result);

    void getSensitiveWord(int id, Handler<AsyncResult<JsonObject>> result);

    void updateSensitiveWord(JsonObject jsonObject, Handler<AsyncResult<JsonObject>> result);

    void deleteSensitiveWord(int id, Handler<AsyncResult<JsonObject>> result);

    void querySensitiveWord(JsonObject jsonObject, int page, int limit, Handler<AsyncResult<String>> result);
}
