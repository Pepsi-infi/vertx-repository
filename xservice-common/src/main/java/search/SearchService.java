package search;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import search.param.ParamForSearch;
import search.request.SearchRequest;
import search.response.SearchMixResultTp;

/**
 * Created by zhushenghao1 on 17/1/16.
 */

@VertxGen
@ProxyGen
public interface SearchService {

    /**
     * The name of the event bus service.
     */
    String SERVICE_NAME = "search-service";

    /**
     * The address on which the service is published.
     */
    String SERVICE_ADDRESS = "service.search";

    /**
     * search数据
     * @return
     */
    void searchTypes(String uuid, SearchRequest request, Handler<AsyncResult<SearchMixResultTp>> resultHandler);

    /**
     * search addOn 分页数据
     * @param params
     * @param productId
     * @param pageNum
     * @param pageSize
     * @param dataType
     * @return
     */
    void searchAddOnData(String uuid, ParamForSearch params, Integer productId, Integer pageNum, Integer pageSize,
            String dataType, Handler<AsyncResult<SearchMixResultTp>> resultHandler);

}
