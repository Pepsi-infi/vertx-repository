package test;
import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.rxjava.core.RxHelper;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.http.HttpClient;
import io.vertx.rxjava.core.http.HttpClientResponse;
import rx.Observable;
import tp.live.response.TheaterWaterMarkTpResponse;

public class CircuitBreakerTest implements Runnable{

    public static String url = "http://localhost:2203/tv-channel/theaterpacked/get.json?channelId=10010";

    private static HttpClient httpClient;
    
    public static  CircuitBreaker circuitBreaker;
    
    public CircuitBreakerTest(Vertx vertx){
        if(httpClient == null) {
            httpClient = vertx.createHttpClient();
        }
        if(circuitBreaker == null){
            circuitBreaker = CircuitBreaker.create(("circuit-breaker"), vertx.getDelegate(),
                    new CircuitBreakerOptions().setMaxFailures(2)
                            .setTimeout(3000L).setFallbackOnFailure(true)
                            .setResetTimeout(3000L));
        }
    }

    @Override
    public void run() {
        circuitBreaker.<TheaterWaterMarkTpResponse> execute(future -> {
            System.err.println("request http");
            url = "/mms/inner/loop/channel/watermarks?channelId=10010&playDate=2017-05-09&platform=tv";
            if(Math.random() > 0.3){
                url = "/mms/inner/loop/channel/watermark";
            }
            Observable<HttpClientResponse> observable = RxHelper.get(httpClient, 80, "i.api.letv.com", url);
            observable.subscribe(ob -> {
                ob.toObservable().lift(RxHelper.unmarshaller(TheaterWaterMarkTpResponse.class)).subscribe(pojo -> {
                    future.complete(pojo);
                });
            }, err -> {
                future.fail(err);
            });
        }).setHandler(ar -> {
            if (ar.succeeded()) {
                System.err.println("SUCCESS: " + ar);
            } else {
                System.err.println("ERROR: " + ar.cause());
            }
            System.err.println("STATE: " + Thread.currentThread().getName() + "  " + circuitBreaker.failureCount() + "  " + circuitBreaker.state());
        });
    }

}
