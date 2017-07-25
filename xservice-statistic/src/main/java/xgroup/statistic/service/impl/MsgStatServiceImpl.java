package xgroup.statistic.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import rxjava.BaseServiceVerticle;
import utils.BaseResponse;
import utils.SystemCache;
import xgroup.statistic.service.MsgStatService;
import xgroup.statistic.tp.msg.MsgStatDao;

import java.util.concurrent.TimeUnit;

/**
 * Created by lufei
 * Date : 2017/7/25 14:54
 * Description :
 */
public class MsgStatServiceImpl extends BaseServiceVerticle implements MsgStatService {
    private static final Logger logger = LoggerFactory.getLogger(MsgStatServiceImpl.class);

    private MsgStatDao msgStatDao;


    public MsgStatServiceImpl() {
    }

    @Override
    public void start() throws Exception {
        super.start();
        this.publishService(MsgStatService.SERVICE_NAME, MsgStatService.SERVICE_ADDRESS);
        this.publishService(MsgStatService.LOCAL_SERVICE_NAME, MsgStatService.getLocalAddress());

        msgStatDao = MsgStatDao.createLocalProxy(vertx.getDelegate());
    }

    private void publishService(String serviceName, String serviceAddress) {
        XProxyHelper.registerService(MsgStatService.class, vertx.getDelegate(), this, serviceAddress);
        publishEventBusService(serviceName, serviceAddress, MsgStatService.class);
    }


    @Override
    public void statPushMsg(int action, String msgId, int osType, Handler<AsyncResult<BaseResponse>> result) {
        Future<String> statPushMsgFuture = Future.future();
        msgStatDao.statPushMsg(action, msgId, osType, statPushMsgFuture.completer());
        statPushMsgFuture.setHandler(re -> {
            if (re.succeeded()) {
                result.handle(Future.succeededFuture());
            } else {

            }
        });
    }


}
