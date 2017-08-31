package service;

import domain.PageBean;
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
public interface PassengerService {

    static PassengerService createProxy(Vertx vertx){
        return ProxyHelper.createProxy(PassengerService.class, vertx, PassengerService.class.getName());
    }

    public void list(JsonObject param, Handler<AsyncResult<PageBean>> resultHandler);

    public void get(JsonObject param, Handler<AsyncResult<String>> resultHandler);

    public void addOrUpdate(JsonObject param, Handler<AsyncResult<String>> resultHandler);

    public void del(JsonObject param, Handler<AsyncResult<String>> resultHandler);

    public void getPushMsg(JsonObject param, Handler<AsyncResult<String>> resultHandler);

    public void addImportFile(JsonObject param, Handler<AsyncResult<String>> resultHandler);

    public void getImportFileList(JsonObject param, Handler<AsyncResult<List<JsonObject>>> resultHandler);

    public void getImportPhone(JsonObject param, Handler<AsyncResult<String>> resultHandler);
}
