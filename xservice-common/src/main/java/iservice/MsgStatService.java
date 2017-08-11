package iservice;

import helper.XProxyHelper;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import iservice.dto.MsgStatDto;

import java.util.List;

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
        return XProxyHelper.createProxy(MsgStatService.class, MsgStatService.class, vertx, MsgStatService.SERVICE_ADDRESS);
    }

    /**
     * @param msgStatDtos
     * @param result
     */
    void statPushMsg(List<MsgStatDto> msgStatDtos, Handler<AsyncResult<String>> result);

}
