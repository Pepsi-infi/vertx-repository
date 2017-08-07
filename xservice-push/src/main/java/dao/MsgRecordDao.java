package dao;

import domain.MsgRecord;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.serviceproxy.ProxyHelper;
import utils.BaseResponse;
import utils.IPUtil;

@VertxGen
@ProxyGen
public interface MsgRecordDao {
    /**
     * The name of the event bus service.
     */
    String SERVICE_NAME = "push-msgrecord-dao";

    /**
     * The address on which the service is published.
     */
    String SERVICE_ADDRESS = "push.msgrecord.dao";

    String LOCAL_SERVICE_NAME = "local-push-msgrecord-dao";

    static MsgRecordDao createProxy(Vertx vertx) {
        return ProxyHelper.createProxy(MsgRecordDao.class, vertx, MsgRecordDao.SERVICE_ADDRESS);
    }

    static MsgRecordDao createLocalProxy(Vertx vertx) {
        return ProxyHelper.createProxy(MsgRecordDao.class, vertx, getLocalAddress(IPUtil.getInnerIP()),
                new DeliveryOptions().setSendTimeout(3000));
    }

    static String getLocalAddress(String ip) {
        return new StringBuffer().append(ip).append("-").append(SERVICE_ADDRESS).toString();
    }

    void addMessage(MsgRecord dto, Handler<AsyncResult<BaseResponse>> resultHandler);

}
