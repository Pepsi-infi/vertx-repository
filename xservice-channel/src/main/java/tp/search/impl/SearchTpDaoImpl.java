package tp.search.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.rxjava.core.RxHelper;
import io.vertx.rxjava.core.http.HttpClient;
import io.vertx.rxjava.core.http.HttpClientResponse;
import rx.Observable;
import search.request.SearchRequest;
import search.response.SearchMixResultTp;
import tp.search.SearchTpDao;

/**
 * Created by IntelliJ IDEA.
 * User: xuli
 * Date：16/11/14
 * Time: 16:40
 */
public class SearchTpDaoImpl implements SearchTpDao {
    private static final int PORT = 80;
    private HttpClient rxHttpClient;

    public SearchTpDaoImpl(HttpClient httpClient) {
        this.rxHttpClient = httpClient;
    }

    @Override
    public void searchTypes(SearchRequest request, Handler<AsyncResult<SearchMixResultTp>> resultHandler) {
        if (request == null) {
            resultHandler.handle(Future.succeededFuture(null));
        } else {
            String url = request.build();
            Observable<HttpClientResponse> observable = RxHelper.get(rxHttpClient, PORT,  SearchRequest.REQ_HOST, url);
            observable.subscribe(ob -> {
                ob.toObservable().lift(RxHelper.unmarshaller(SearchMixResultTp.class)).subscribe(pojo -> {
                    resultHandler.handle(Future.succeededFuture(pojo));
                });
            }, err -> {
                resultHandler.handle(Future.failedFuture(err));
            });
        }
    }
}
