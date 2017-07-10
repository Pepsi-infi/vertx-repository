package tp.rec.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.RxHelper;
import io.vertx.rxjava.core.http.HttpClient;
import io.vertx.rxjava.core.http.HttpClientResponse;
import org.apache.commons.lang.StringUtils;
import tp.rec.RecTpDao;
import tp.rec.request.LecomRecRequest;
import tp.rec.request.RecBaseRequest;
import tp.rec.response.RecResponse;

/**
 * Created by IntelliJ IDEA.
 * User: xuli
 * Date：16/10/18
 * Time: 15:01
 */
public class RecTpDaoImpl implements RecTpDao {

    private static final Logger httpLog = LoggerFactory.getLogger("httpClient");
    private HttpClient rxHttpClient;

    public RecTpDaoImpl(HttpClient httpClient) {
        this.rxHttpClient = httpClient;
    }

    @Override
    public void getMultiBlocksResult(RecBaseRequest recRequest, Handler<AsyncResult<RecResponse>> resultHandler) {
        if (recRequest == null || StringUtils.isBlank(recRequest.getPageid())) {
            resultHandler.handle(Future.failedFuture("pageId in null"));
        }
        else {
            String requestURI = recRequest.build();
            String host = LecomRecRequest.REQ_HOST;
            rx.Observable<HttpClientResponse> observable = RxHelper.get(rxHttpClient, 80, host, requestURI);
            observable.subscribe(ob -> {
                ob.toObservable().lift(RxHelper.unmarshaller(RecResponse.class)).subscribe(pojo -> {
                    httpLog.info("request_host={}#request_uri={}", host, requestURI);
                    resultHandler.handle(Future.succeededFuture(pojo));
                }, throwable -> httpLog.error("httpclient error"+throwable));
            }, err -> {
                resultHandler.handle(Future.failedFuture(err));
            });
        }
    }
}
