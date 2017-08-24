package service;

import domain.PageBean;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHelper;

/**
 * Created by weim on 2017/8/22.
 */
@ProxyGen
@VertxGen
public interface PassengerMessageService {

    static PassengerMessageService createProxy(Vertx vertx){
        return ProxyHelper.createProxy(PassengerMessageService.class, vertx, PassengerMessageService.class.getName());
    }

    public void list(JsonObject param, Handler<AsyncResult<PageBean>> resultHandler);

    public void add(JsonObject param, Handler<AsyncResult<String>> resultHandler);
}
