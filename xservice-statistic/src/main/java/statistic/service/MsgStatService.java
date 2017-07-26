package statistic.service;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.serviceproxy.ProxyHelper;
import utils.BaseResponse;
import utils.IPUtil;

/**
 * Created by lufei
 * Date : 2017/7/25 13:32
 * Description :
 */
@ProxyGen
@VertxGen
public interface MsgStatService {
    public static final String SERVICE_NAME = "http.msg.stat.eb.service";

    public static final String SERVICE_ADDRESS = "http-msg-stat-eb-service";

    public static final String LOCAL_SERVICE_NAME = "local.http.msg.stat.eb.service";

    static MsgStatService createProxy(Vertx vertx) {
        return ProxyHelper.createProxy(MsgStatService.class, vertx, MsgStatService.SERVICE_ADDRESS);
    }

    static MsgStatService createLocalProxy(Vertx vertx) {
        return ProxyHelper.createProxy(MsgStatService.class, vertx, getLocalAddress(),
                new DeliveryOptions().setSendTimeout(3000));
    }

    static String getLocalAddress() {
        return new StringBuffer().append(IPUtil.getInnerIP()).append("-").append(SERVICE_ADDRESS).toString();
    }

    /**
     * @param action
     * @param msgId
     * @param osType
     * @param result
     */
    void statPushMsg(int action, String msgId, int osType, Handler<AsyncResult<BaseResponse>> result);

}
