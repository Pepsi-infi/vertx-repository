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
 * Date : 2017/8/9 18:37
 * Description :
 */
@ProxyGen
@VertxGen
public interface TransferDeviceService {

    public static final String SERVICE_NAME = "http.transferDevice.eb.service";

    public static final String SERVICE_ADDRESS = "http-transferDevice-eb-service";

    static TransferDeviceService createProxy(Vertx vertx) {
        return XProxyHelper.createProxy(TransferDeviceService.class, TransferDeviceService.class, vertx, TransferDeviceService.SERVICE_ADDRESS);
    }


    void transferDevice(Handler<AsyncResult<BaseResponse>> result);
}
