package service.impl;

import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import rxjava.BaseServiceVerticle;
import dao.DeviceDao;
import service.DeviceService;
import service.dto.DeviceDto;
import utils.BaseResponse;

/**
 * Created by lufei
 * Date : 2017/7/27 10:09
 * Description :
 */
public class DeviceServiceImpl extends BaseServiceVerticle implements DeviceService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);

    private DeviceDao deviceDao;


    @Override
    public void start() throws Exception {
        super.start();

        XProxyHelper.registerService(DeviceService.class, vertx.getDelegate(), this, DeviceService.SERVICE_ADDRESS);
        publishEventBusService(DeviceService.SERVICE_NAME, DeviceService.SERVICE_ADDRESS, DeviceService.class);

        deviceDao = DeviceDao.createProxy(vertx.getDelegate());
    }


    @Override
    public void reportUserDevice(DeviceDto userDeviceDto, Handler<AsyncResult<BaseResponse>> result) {
        Future<BaseResponse> resultFuture = Future.future();
        deviceDao.addUserDevice(userDeviceDto, resultFuture.completer());
        resultFuture.setHandler(handler -> {
            BaseResponse baseResponse = new BaseResponse();
            if (handler.succeeded()) {
                result.handle(Future.succeededFuture(baseResponse));
            } else {
                buildErrorBaseResponse(baseResponse, null, handler.cause().toString());
                result.handle(Future.succeededFuture(baseResponse));
            }
        });
    }

    private <T extends BaseResponse> void buildErrorBaseResponse(T response, String errCode, String message) {
        if (response != null) {
            response.setStatus(BaseResponse.RESPONSE_FAIL_CODE);
            response.setErrorCode(errCode);
            response.setErrorMessage(message);
        }
    }
}
