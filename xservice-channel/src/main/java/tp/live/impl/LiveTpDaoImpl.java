package tp.live.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;

import helper.XProxyHelper;
import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.redis.RedisOptions;
import io.vertx.rxjava.core.RxHelper;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.http.HttpClient;
import io.vertx.rxjava.core.http.HttpClientResponse;
import io.vertx.rxjava.ext.web.client.HttpResponse;
import io.vertx.rxjava.ext.web.client.WebClient;
import io.vertx.rxjava.ext.web.codec.BodyCodec;
import rx.Observable;
import rx.Single;
import rxjava.BaseServiceVerticle;
import tp.live.LiveTpDao;
import tp.live.request.LmsDataRequest;
import tp.live.request.PlayBillCurrentRequest;
import tp.live.request.ProgramWaterMarkRequest;
import tp.live.request.TheaterWaterMarkRequest;
import tp.live.response.PlayBillCurrentTpResponse;

public class LiveTpDaoImpl extends BaseServiceVerticle implements LiveTpDao {
    private static final Logger logger = LoggerFactory.getLogger(LiveTpDaoImpl.class);
    private static final int PORT = 80;
    private CircuitBreaker circuitBreaker;
    private WebClient webClient;
    private HttpClient rxHttpClient;// Don't use static!


    public LiveTpDaoImpl() {
    }

    @Override
    public void start() throws Exception {
        super.start();

        XProxyHelper.registerService(LiveTpDao.class, vertx.getDelegate(), this, LiveTpDao.SERVICE_ADDRESS);
        publishEventBusService(LiveTpDao.SERVICE_NAME, LiveTpDao.SERVICE_ADDRESS, LiveTpDao.class);

        XProxyHelper.registerService(LiveTpDao.class, vertx.getDelegate(), this, LiveTpDao.getLocalAddress());
        publishEventBusService(LiveTpDao.LOCAL_SERVICE_NAME, LiveTpDao.getLocalAddress(), LiveTpDao.class);

        webClient = this.createWebClient(vertx);
        circuitBreaker = this.createCircuitBreaker(vertx, config());
        rxHttpClient = vertx.createHttpClient();

        String root = System.getProperty("config", "mobile");
        String redis = System.getProperty("redis", "dev");
        JsonObject jsonObject = this.getJsonConf(root + "/" + redis + "/redis.json");
        RedisOptions redisOptions = new RedisOptions();
        if (jsonObject != null && !jsonObject.isEmpty()) {
            redisOptions.setAuth(jsonObject.getString("password"));
            redisOptions.setAddress(jsonObject.getString("host"));
            redisOptions.setPort(jsonObject.getInteger("port"));
            redisOptions.setTcpKeepAlive(Boolean.getBoolean(jsonObject.getString("tcpKeepAlive")));
            redisOptions.setEncoding(jsonObject.getString("encoding"));
            redisOptions.setTcpNoDelay(Boolean.getBoolean(jsonObject.getString("tcpNoDelay")));
        }
    }

    private JsonObject getJsonConf(String configPath) {
        logger.info("rediPath: " + configPath);
        JsonObject conf = new JsonObject();
        ClassLoader ctxClsLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = ctxClsLoader.getResourceAsStream(configPath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(is)));
        try {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            conf = new JsonObject(sb.toString());
            logger.info("Loaded redis.json file from [" + configPath + "/redis.json] and config.json="
                    + conf.toString());
        } catch (Exception e) {
            logger.error("Failed to load configuration file" + e);
        }
        return conf;
    }

    private CircuitBreaker createCircuitBreaker(Vertx vertx, JsonObject config) {
        JsonObject circuitObject = config.getJsonObject("circuit-breaker");
        JsonObject cbOptions = circuitObject != null ? circuitObject : new JsonObject();
        CircuitBreakerOptions options = new CircuitBreakerOptions();
        options.setMaxFailures(cbOptions.getInteger("max-failures", 5));
        options.setTimeout(cbOptions.getLong("timeout", 10000L));
        options.setFallbackOnFailure(true);
        options.setResetTimeout(cbOptions.getLong("reset-timeout", 30000L));
        String name = cbOptions.getString("name", "circuit-breaker");
        return CircuitBreaker.create(name, vertx.getDelegate(), options);
    }

    private WebClient createWebClient(Vertx vertx) {
        WebClientOptions webClientOptions = new WebClientOptions();
        webClientOptions.setConnectTimeout(1000);
        webClientOptions.setKeepAlive(true);
        webClientOptions.setLogActivity(false);
        webClientOptions.setMaxPoolSize(128);
        webClientOptions.setTryUseCompression(true);
        webClientOptions.setSendBufferSize(1048576);
        webClientOptions.setReceiveBufferSize(1048576);
        webClientOptions.setReuseAddress(true);
        webClientOptions.setPipelining(true);
        return WebClient.create(vertx, webClientOptions);
    }

    /**
     * 获取轮播数据
     */
    @Override
    public void getLunBoDataFromCloudPlatform(String searchUrl, Handler<AsyncResult<String>> result) {
        circuitBreaker.<String> execute(future -> {
            String url = StringUtils.isNotBlank(searchUrl) ? searchUrl : "";
            logger.info("getLunBoDataFromCloudPlatformHttpRequest, url:  " + url);
            String suburl = url.substring(url.indexOf("//") + 2);
            String host = suburl.substring(0, suburl.indexOf("/"));
            url = suburl.substring(suburl.indexOf("/"));
            Single<HttpResponse<String>> httpRequest = webClient.get(PORT, host, url).as(BodyCodec.string()).rxSend();
            httpRequest.subscribe(resp -> {
                if (resp.statusCode() == 200) {
                    future.complete(resp.body());
                } else {
                    future.fail(resp.statusCode() + resp.statusMessage());
                }
            });
        }).setHandler(ar -> {
            if (ar.succeeded()) {
                result.handle(Future.succeededFuture(ar.result()));
                logger.info("SUCCESS: " + ar);
            } else {
                result.handle(Future.failedFuture(ar.cause()));
                logger.error("ERROR: " + ar.cause());
            }
        });
    }

    /**
     * 云平台获取直播数据
     */
    @Override
    public void getZhiBoDataFromCloudPlatform(String searchUrl, Handler<AsyncResult<String>> result) {
        // http://api.live.letv.com/v1/liveRoom/queryLives/10,20,30,40,50,60,70,80,90?clientId=1060704002&belongArea=100
        circuitBreaker.<String> execute(future -> {
            String url = StringUtils.isNotBlank(searchUrl) ? searchUrl : " ";
            logger.info("getZhiBoDataFromCloudPlatformHttpRequest, url:  " + url);
            String suburl = url.substring(url.indexOf("//") + 2);
            String host = suburl.substring(0, suburl.indexOf("/"));
            url = suburl.substring(suburl.indexOf("/"));
            Single<HttpResponse<String>> httpRequest = webClient.get(PORT, host, url).as(BodyCodec.string()).rxSend();
            httpRequest.subscribe(resp -> {
                if (resp.statusCode() == 200) {
                    future.complete(resp.body());
                } else {
                    future.fail(resp.statusCode() + resp.statusMessage());
                }
            });
        }).setHandler(ar -> {
            if (ar.succeeded()) {
                result.handle(Future.succeededFuture(ar.result()));
                logger.info("SUCCESS: " + ar);
            } else {
                result.handle(Future.failedFuture(ar.cause() + "; " + searchUrl));
                logger.error("ERROR: " + ar.cause());
            }
        });
    }

    /**
     * lms获取第三方直播数据
     */
    @Override
    public void getZhiBoDataFromLMSBySearchUrl(String searchUrl, String sourceId, int offSet, int fetchSize, String splatid, Handler<AsyncResult<String>> result) {
        // http://api.lms.le.com/api/http/liveRoom/queryLiveRoomsByCategory?lineTwoCode=28&lineOneCode=5&sourcetype=2&sourceId=5
        circuitBreaker.<String> execute(future -> {
            LmsDataRequest request = new LmsDataRequest();
            request.setUrl(searchUrl);
            request.setOffSet(offSet); // 默认第一页
            request.setFetchSize(fetchSize); // 默认200条
            
            String url = request.build();
            logger.info("getZhiBoDataFromLMSBySearchUrlHttpRequest, url:  " + url);
            String suburl = url.substring(url.indexOf("//") + 2);
            String host = suburl.substring(0, suburl.indexOf("/"));
            url = suburl.substring(suburl.indexOf("/"));
            Single<HttpResponse<String>> httpRequest = webClient.get(PORT, host, url).as(BodyCodec.string()).rxSend();
            httpRequest.subscribe(resp -> {
                if (resp.statusCode() == 200) {
                    future.complete(resp.body());
                } else {
                    future.fail(resp.statusCode() + resp.statusMessage());
                }
            });
        }).setHandler(ar -> {
            if (ar.succeeded()) {
                result.handle(Future.succeededFuture(ar.result()));
                logger.info("SUCCESS: " + ar);
            } else {
                result.handle(Future.succeededFuture(ar.result()));
                logger.error("ERROR: " + ar.cause());
            }
        });
    }

    /**
     * 节目水印获取
     */
    @Override
    public void getProgramWaterMark(ProgramWaterMarkRequest request, Handler<AsyncResult<String>> result) {
        circuitBreaker.<String> execute(future -> {
            String url = request.build();
            logger.info("getProgramWaterMark: http://" + TheaterWaterMarkRequest.HOST + url);
            Single<HttpResponse<String>> httpRequest = webClient.get(PORT, TheaterWaterMarkRequest.HOST, url).as(BodyCodec.string()).rxSend();
            httpRequest.subscribe(resp -> {
                if (resp.statusCode() == 200) {
                    future.complete(resp.body());
                } else {
                    future.fail(resp.statusCode() + resp.statusMessage());
                }
            });
        }).setHandler(ar -> {
            if (ar.succeeded()) {
                result.handle(Future.succeededFuture(ar.result()));
                logger.info("getProgramWaterMark SUCCESS: " + ar);
            } else {
                result.handle(Future.succeededFuture());
                logger.error("getProgramWaterMark ERROR: " + ar.cause());
            }
        });
    }

    /**
     * 剧场水印获取
     */
    @Override
    public void getTheaterWaterMark(TheaterWaterMarkRequest request, Handler<AsyncResult<String>> result) {
        circuitBreaker.<String> execute(future -> {
            String url = request.build();
            logger.info("getTheaterWaterMark: http://" + TheaterWaterMarkRequest.HOST + url);
            Single<HttpResponse<String>> httpRequest = webClient.get(PORT, TheaterWaterMarkRequest.HOST, url).as(BodyCodec.string()).rxSend();
            httpRequest.subscribe(resp -> {
                if (resp.statusCode() == 200) {
                    future.complete(resp.body());
                } else {
                    future.fail(resp.statusCode() + resp.statusMessage());
                }
            });
       }).setHandler(ar -> {
            if (ar.succeeded()) {
                result.handle(Future.succeededFuture(ar.result()));
                logger.info("getTheaterWaterMark SUCCESS: " + ar);
            } else {
                result.handle(Future.succeededFuture());
                logger.error("getTheaterWaterMark ERROR: " + ar.cause());
            }
        });
    }

    @Override
    public void getTpPlayBillCurrent(PlayBillCurrentRequest request,
            Handler<AsyncResult<PlayBillCurrentTpResponse>> result) {
        circuitBreaker.<PlayBillCurrentTpResponse> execute(future -> {
            String url = request.build();
            Observable<HttpClientResponse> observable = RxHelper.get(rxHttpClient, PlayBillCurrentRequest.HOST, url);
            observable.subscribe(ob -> {
                ob.toObservable().lift(RxHelper.unmarshaller(PlayBillCurrentTpResponse.class)).subscribe(pojo -> {
                    future.complete(pojo);
                    logger.info("getTpPlayBillCurrent " + url);
                }, e -> {
                    future.fail(e.getMessage());
                });
            }, e -> {
                result.handle(Future.failedFuture(e));
            });
        }).setHandler(ar -> {
            if (ar.succeeded()) {
                result.handle(Future.succeededFuture(ar.result()));
                logger.info("getTpPlayBillCurrent SUCCESS: " + ar);
            } else {
                result.handle(Future.succeededFuture(new PlayBillCurrentTpResponse()));
                logger.error("getTpPlayBillCurrent ERROR: " + ar.cause());
            }
        });
    }
}
