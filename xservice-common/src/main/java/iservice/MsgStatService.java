package iservice;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.serviceproxy.ProxyHelper;
import iservice.dto.MsgStatDto;
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


    static MsgStatService createProxy(Vertx vertx) {
        return ProxyHelper.createProxy(MsgStatService.class, vertx, MsgStatService.SERVICE_ADDRESS);
    }

    /**
     * 统计push消息
     *
     * @param msgStatDto
     * @param result
     */
    void statPushMsg(MsgStatDto msgStatDto, Handler<AsyncResult<BaseResponse>> result);


}
