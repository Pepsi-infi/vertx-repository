package service;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHelper;

import java.util.List;

/**
 * Created by weim on 2017/8/22.
 */
@ProxyGen
@VertxGen
public interface PassengerUnSendService {

    static PassengerUnSendService createProxy(Vertx vertx){
        return ProxyHelper.createProxy(PassengerUnSendService.class, vertx, PassengerUnSendService.class.getName());
    }

    public void getUnSendMsg(JsonObject param, Handler<AsyncResult<List<JsonObject>>> resultHandler);

    public void addUnSendMsg(JsonObject param, Handler<AsyncResult<Integer>> resultHandler);

    public void delExpireUnSendMsg(Handler<AsyncResult<Integer>> resultHandler);

    public void delUnSendMsg(JsonObject param, Handler<AsyncResult<Integer>> resultHandler);

    public void pushUnSendMsg(String phone, Handler<AsyncResult<String>> resultHandler);

    public void pushAddUnSendMsg(JsonObject param, Handler<AsyncResult<String>> resultHandler);
}
