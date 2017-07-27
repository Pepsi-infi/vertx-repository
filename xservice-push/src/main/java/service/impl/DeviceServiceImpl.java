package service.impl;

import dao.DeviceDao;
import domain.AmqpConsumeMessage;
import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import service.DeviceService;
import utils.BaseResponse;
import utils.IPUtil;
import xservice.BaseServiceVerticle;

/**
 *  保存消息记录
 */
public class DeviceServiceImpl extends BaseServiceVerticle implements DeviceService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);

    private DeviceDao deviceDao;

    public DeviceServiceImpl() {
    }

    @Override
    public void start() throws Exception {
        super.start();

        XProxyHelper.registerService(DeviceService.class, vertx, this, DeviceService.SERVICE_ADDRESS);
        publishEventBusService(DeviceService.SERVICE_NAME, DeviceService.SERVICE_ADDRESS, DeviceService.class);

        String ip = IPUtil.getInnerIP();
        XProxyHelper.registerService(DeviceService.class, vertx, this, DeviceService.getLocalAddress(ip));
        publishEventBusService(DeviceService.LOCAL_SERVICE_NAME, DeviceService.getLocalAddress(ip), DeviceService.class);

        deviceDao = DeviceDao.createLocalProxy(vertx);
    }

    @Override
    public void addMessage(AmqpConsumeMessage msg, Handler<AsyncResult<BaseResponse>> resultHandler) {
        Future<BaseResponse> resultFuture = Future.future();
        deviceDao.addMessage(msg, resultFuture.completer());
        resultFuture.setHandler(handler -> {
            if (handler.succeeded()) {
                resultHandler.handle(Future.succeededFuture(new BaseResponse()));
            } else {
                resultHandler.handle(Future.failedFuture(handler.cause()));
            }
        });
    }

}
