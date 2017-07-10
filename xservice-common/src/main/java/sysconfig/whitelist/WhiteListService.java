package sysconfig.whitelist;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import sysconfig.whitelist.request.WhiteListRequest;
import sysconfig.whitelist.response.WhiteListResponse;

/**
 * Created by wangbingbing on 2016/11/15.
 */
@ProxyGen
@VertxGen
public interface WhiteListService {
    /**
     * The name of the event bus service.
     */
    String SERVICE_NAME = "whiteList-eb-service";

    /**
     * The address on which the service is published.
     */
    String SERVICE_ADDRESS = "service.sys.config";

    void addDevId(String uuid, WhiteListRequest request, Handler<AsyncResult<WhiteListResponse>> resultHandler);

    void removeDevId(String uuid, WhiteListRequest request, Handler<AsyncResult<WhiteListResponse>> resultHandler);

    void retrieveDevId(String uuid, WhiteListRequest request, Handler<AsyncResult<WhiteListResponse>> resultHandler);
}
