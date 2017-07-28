package service.impl;

import dao.MsgRecordDao;
import domain.AmqpConsumeMessage;
import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import service.MsgRecordService;
import utils.BaseResponse;
import utils.IPUtil;
import xservice.BaseServiceVerticle;

/**
 *  保存消息记录
 */
public class MsgRecordServiceImpl extends BaseServiceVerticle implements MsgRecordService {

    private static final Logger logger = LoggerFactory.getLogger(MsgRecordServiceImpl.class);

    private MsgRecordDao deviceDao;

    public MsgRecordServiceImpl() {
    }

    @Override
    public void start() throws Exception {
        super.start();

        XProxyHelper.registerService(MsgRecordService.class, vertx, this, MsgRecordService.SERVICE_ADDRESS);
        publishEventBusService(MsgRecordService.SERVICE_NAME, MsgRecordService.SERVICE_ADDRESS, MsgRecordService.class);

        String ip = IPUtil.getInnerIP();
        XProxyHelper.registerService(MsgRecordService.class, vertx, this, MsgRecordService.getLocalAddress(ip));
        publishEventBusService(MsgRecordService.LOCAL_SERVICE_NAME, MsgRecordService.getLocalAddress(ip), MsgRecordService.class);

        deviceDao = MsgRecordDao.createLocalProxy(vertx);
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
