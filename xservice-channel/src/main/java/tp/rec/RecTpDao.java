package tp.rec;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import tp.rec.request.RecBaseRequest;
import tp.rec.response.RecResponse;

/**
 * Created by IntelliJ IDEA.
 * User: xuli
 * Date：16/9/27
 * Time: 15:30
 * 暂时不对外提供eventbus服务
 */
//@ProxyGen
//@VertxGen
public interface RecTpDao {

    /**
     * The name of the event bus service.
     */
//    public static String SERVICE_NAME = "recommend-service";

    /**
     * The address on which the service is published.
     */
//    public static String SERVICE_ADDRESS = "service.recommend";

    public void getMultiBlocksResult(RecBaseRequest recRequest, Handler<AsyncResult<RecResponse>> resultHandler);

}
