package service.impl;

import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import rxjava.BaseServiceVerticle;
import dao.DeviceDao;
import iservice.DeviceService;
import iservice.dto.DeviceDto;
import utils.BaseResponse;

import java.util.List;
import java.util.Map;

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
        deviceDao.addDevice(userDeviceDto, resultFuture.completer());
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

    @Override
    public void queryDevices(Map<String, String> param, Handler<AsyncResult<List<DeviceDto>>> result) {
        Future<List<DeviceDto>> resultFuture = Future.future();
        deviceDao.queryDevices(param, resultFuture.completer());
        resultFuture.setHandler(handler -> {
            if (handler.succeeded()) {
                result.handle(Future.succeededFuture(handler.result()));
            } else {
                logger.error("query devices error.", handler.cause());
                result.handle(Future.failedFuture(handler.cause()));
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
