package service;

import helper.XProxyHelper;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import utils.BaseResponse;

/**
 * Created by lufei
 * Date : 2017/7/28 17:26
 * Description :
 */
@ProxyGen
@VertxGen
public interface MsgStatResultService {

    public static final String SERVICE_NAME = "http.msgStatResult.eb.service";

    public static final String SERVICE_ADDRESS = "http-msgStatResult-eb-service";

    static MsgStatResultService createProxy(Vertx vertx) {
        return XProxyHelper.createProxy(MsgStatResultService.class, MsgStatResultService.class, vertx, MsgStatResultService.SERVICE_ADDRESS);
    }


    /**
     * 持久化push message 数据
     *
     * @param result
     */
    void storeMsgStatResult(Handler<AsyncResult<BaseResponse>> result);

    void repireData(Handler<AsyncResult<BaseResponse>> result);
}
