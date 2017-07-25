package xgroup.statistic.tp.msg;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.serviceproxy.ProxyHelper;
import utils.IPUtil;
import xgroup.statistic.constants.PushActionEnum;

/**
 * Created by lufei
 * Date : 2017/7/25 15:14
 * Description :
 */
@VertxGen
@ProxyGen
public interface MsgStatDao {

    public static final String SERVICE_NAME = "tp.msg.stat.eb.service";

    public static final String SERVICE_ADDRESS = "tp-msg-stat-eb-service";

    public static final String LOCAL_SERVICE_NAME = "local.tp.msg.stat.eb.service";

    static MsgStatDao createProxy(Vertx vertx) {
        return ProxyHelper.createProxy(MsgStatDao.class, vertx, MsgStatDao.SERVICE_ADDRESS);
    }

    static MsgStatDao createLocalProxy(Vertx vertx) {
        return ProxyHelper.createProxy(MsgStatDao.class, vertx, getLocalAddress());
    }

    static String getLocalAddress() {
        return new StringBuffer().append(IPUtil.getInnerIP()).append("-").append(SERVICE_ADDRESS).toString();
    }

    /**
     * 统计push消息
     *
     * @param action 消息动作 1：send 2：Arrive
     * @param msgId
     * @param osType
     * @param result
     */
    void statPushMsg(int action, String msgId, int osType, Handler<AsyncResult<String>> result);
}
