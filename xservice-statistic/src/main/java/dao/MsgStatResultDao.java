package dao;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.serviceproxy.ProxyHelper;
import service.dto.MsgStatResultDto;
import utils.BaseResponse;

/**
 * Created by lufei
 * Date : 2017/7/30 21:58
 * Description :
 */
@VertxGen
@ProxyGen
public interface MsgStatResultDao {
    /**
     * The name of the event bus service.
     */
    String SERVICE_NAME = "dao-msgStatResult-service";

    /**
     * The address on which the service is published.
     */
    String SERVICE_ADDRESS = "dao.msgStatResult.service";


    static MsgStatResultDao createProxy(Vertx vertx) {
        return ProxyHelper.createProxy(MsgStatResultDao.class, vertx, MsgStatResultDao.SERVICE_ADDRESS);
    }


    void addMsgStatResult(MsgStatResultDto msgStatResultDto, Handler<AsyncResult<BaseResponse>> resultHandler);
}
