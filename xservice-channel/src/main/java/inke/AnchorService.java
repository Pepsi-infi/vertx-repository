package inke;

import inke.dto.AnchorListWapperDto;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.rxjava.core.Vertx;
import io.vertx.serviceproxy.ProxyHelper;
import utils.IPUtil;

/**
 * Created by wanglonghu on 17/6/9.
 */
@VertxGen
@ProxyGen
public interface AnchorService {

    String SERVICE_NAME = "AnchorService.eb.service";

    String SERVICE_ADDRESS = "AnchorService.eb.service";

    void getOnlineAnchorList(int index, String uuid, Handler<AsyncResult<AnchorListWapperDto>> resultHandler);
}
