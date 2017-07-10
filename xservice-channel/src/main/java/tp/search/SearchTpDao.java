package tp.search;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import search.request.SearchRequest;
import search.response.SearchMixResultTp;

/**
 * Created by IntelliJ IDEA.
 * User: xuli
 * Date：16/11/14
 * Time: 11:38
 * 暂时不对外提供eventbus服务
 */
//@VertxGen
//@ProxyGen
public interface SearchTpDao {

    public void searchTypes(SearchRequest request, Handler<AsyncResult<SearchMixResultTp>> resultHandler);
}
