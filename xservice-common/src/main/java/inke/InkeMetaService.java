package inke;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;
import io.vertx.serviceproxy.ProxyHelper;
import java.util.List;

/**
 * Created by wanglonghu on 17/6/8.
 */
@VertxGen
@ProxyGen
public interface InkeMetaService {

    String SERVICE_NAME = "InkeMetaService.eb.service";

    String SERVICE_ADDRESS = "InkeMetaService.eb.service";

    /**
     * 根据主播id获得主播信息
     *
     * @param resultHandler
     */
    void getAnchorById(String anchorId, Handler<AsyncResult<JsonObject>> resultHandler);

    /**
     * 获取在线主播列表(分页)
     *
     * @param page
     * @param pageSize
     * @param resultHandler : InkeAnchor.java
     */
    void getOnlineAnchors(int page, int pageSize, Handler<AsyncResult<List<JsonObject>>> resultHandler);

    void getOnlineAnchorsOrderByInke(int page, int pageSize, String uuid, Handler<AsyncResult<List<JsonObject>>> resultHandler);
}
