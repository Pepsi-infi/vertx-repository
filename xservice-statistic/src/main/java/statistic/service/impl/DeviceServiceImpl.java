package statistic.service.impl;

import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import rxjava.BaseServiceVerticle;
import statistic.dao.msg.DeviceDao;
import statistic.service.DeviceService;
import statistic.service.dto.DeviceDto;
import utils.BaseResponse;

/**
 * Created by lufei
 * Date : 2017/7/27 10:09
 * Description :
 */
public class DeviceServiceImpl extends BaseServiceVerticle implements DeviceService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);

    private DeviceDao userDeviceDao;


    @Override
    public void start() throws Exception {
        super.start();

        XProxyHelper.registerService(DeviceService.class, vertx.getDelegate(), this, DeviceService.SERVICE_ADDRESS);
        publishEventBusService(DeviceService.SERVICE_NAME, DeviceService.SERVICE_ADDRESS, DeviceService.class);

        XProxyHelper.registerService(DeviceService.class, vertx.getDelegate(), this, DeviceService.getLocalAddress());
        publishEventBusService(DeviceService.LOCAL_SERVICE_NAME, DeviceService.getLocalAddress(), DeviceService.class);

        userDeviceDao = DeviceDao.createLocalProxy(vertx.getDelegate());
    }


    @Override
    public void reportUserDevice(DeviceDto userDeviceDto, Handler<AsyncResult<BaseResponse>> result) {
        Future<BaseResponse> resultFuture = Future.future();
        userDeviceDao.addUserDevice(userDeviceDto, resultFuture.completer());
        resultFuture.setHandler(handler -> {
            if (handler.succeeded()) {
                result.handle(Future.succeededFuture(new BaseResponse()));
            } else {
                result.handle(Future.failedFuture(handler.cause()));
            }
        });
    }
}
