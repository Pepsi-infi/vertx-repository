package dao;

import helper.XProxyHelper;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import service.dto.MsgStatResultDto;
import utils.BaseResponse;

import java.util.List;
import java.util.Map;

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
        return XProxyHelper.createProxy(MsgStatResultDao.class, MsgStatResultDao.class, vertx, MsgStatResultDao.SERVICE_ADDRESS);
    }


    void addMsgStatResult(MsgStatResultDto msgStatResultDto, Handler<AsyncResult<BaseResponse>> resultHandler);

    void updateMsgStatResult(MsgStatResultDto msgStatResultDto, Handler<AsyncResult<BaseResponse>> resultHandler);

    void getMsgStatResult(MsgStatResultDto msgStatResultDto, Handler<AsyncResult<MsgStatResultDto>> resultHandler);

    void queryMsgStatResultByPage(Map<String, String> params, int page, int limit, Handler<AsyncResult<List<MsgStatResultDto>>> resultHandler);
}
