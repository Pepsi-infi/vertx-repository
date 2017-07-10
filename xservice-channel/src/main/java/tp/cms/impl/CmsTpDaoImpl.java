package tp.cms.impl;

import helper.XProxyHelper;
import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import io.vertx.rxjava.core.RxHelper;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.http.HttpClient;
import io.vertx.rxjava.core.http.HttpClientResponse;
import io.vertx.rxjava.ext.web.client.HttpResponse;
import io.vertx.rxjava.ext.web.client.WebClient;
import io.vertx.rxjava.ext.web.codec.BodyCodec;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;

import org.apache.commons.lang.math.NumberUtils;

import rx.Single;
import rxjava.BaseServiceVerticle;
import tp.cms.CmsTpDao;
import tp.cms.request.CmsPageRequest;
import tp.cms.request.ColumnListRequest;
import tp.cms.request.MutiLangBlockUrl;
import tp.cms.response.CmsMutilangDataResponse;
import utils.IPUtil;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import constants.CacheConstants;
import utils.SystemCache;

/**
 * Created by IntelliJ IDEA. User: xuli Date：16/10/28 Time: 15:36
 * channel服务自己用的接口
 */
public class CmsTpDaoImpl extends BaseServiceVerticle implements CmsTpDao {
    private static final Logger logger = LoggerFactory.getLogger(CmsTpDaoImpl.class);
    private static final Logger httpLog = LoggerFactory.getLogger("httpClient");
    private static final int PORT = 80;
    private static final String CMS_HOST = "api.cms.le.com";
    private HttpClient rxHttpClient;// Don't use static!
    private CircuitBreaker circuitBreaker;
    private WebClient webClient;
    private RedisClient redisClient;

    // private ICache iCache;

    public CmsTpDaoImpl() {
    }

    public CmsTpDaoImpl(Vertx vertx, JsonObject config) {
    }

    @Override
    public void start() throws Exception {
        super.start();

        XProxyHelper.registerService(CmsTpDao.class, vertx.getDelegate(), this, CmsTpDao.SERVICE_ADDRESS);
        publishEventBusService(CmsTpDao.SERVICE_NAME, CmsTpDao.SERVICE_ADDRESS, CmsTpDao.class);

        String ip = IPUtil.getInnerIP();
        XProxyHelper.registerService(CmsTpDao.class, vertx.getDelegate(), this, CmsTpDao.getLocalAddress(ip));
        publishEventBusService(CmsTpDao.LOCAL_SERVICE_NAME, CmsTpDao.getLocalAddress(ip), CmsTpDao.class);

        this.rxHttpClient = this.createHttpClient(vertx);
        this.circuitBreaker = this.createCircuitBreaker(vertx, new JsonObject());
        webClient = WebClient.create(vertx);

        // iCache = ICache.createLocalProxy(vertx.getDelegate());
        logger.info("CmsTpDaoImpl start");

        String root = System.getProperty("config", "mobile");
        String redis = System.getProperty("redis", "dev");
        JsonObject jsonObject = this.getJsonConf(root + "/" + redis + "/redis.json");
        RedisOptions redisOptions = new RedisOptions();
        if (jsonObject != null && !jsonObject.isEmpty()) {
            redisOptions.setAuth(jsonObject.getString("password"));
            redisOptions.setAddress(jsonObject.getString("host"));
            redisOptions.setPort(jsonObject.getInteger("port"));
            // redisOptions.setTcpKeepAlive(Boolean.getBoolean(jsonObject.getString("tcpKeepAlive")));
            // redisOptions.setEncoding(jsonObject.getString("encoding"));
            // redisOptions.setTcpNoDelay(Boolean.getBoolean(jsonObject.getString("tcpNoDelay")));
        }
        redisClient = RedisClient.create(vertx.getDelegate(), redisOptions);
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
            logger.info(
                    "Loaded redis.json file from [" + configPath + "/redis.json] and config.json=" + conf.toString());
        } catch (Exception e) {
            logger.error("Failed to load configuration file" + e);
        }
        return conf;
    }

    private HttpClient createHttpClient(Vertx vertx) {
        HttpClientOptions httpClientOptions = new HttpClientOptions();
        httpClientOptions.setConnectTimeout(1000);
        httpClientOptions.setKeepAlive(true);
        httpClientOptions.setLogActivity(false);
        httpClientOptions.setMaxPoolSize(128);
        httpClientOptions.setTryUseCompression(true);
        httpClientOptions.setSendBufferSize(1048576);
        httpClientOptions.setReceiveBufferSize(1048576);
        httpClientOptions.setReuseAddress(true);
        httpClientOptions.setPipelining(true);

        return vertx.createHttpClient(httpClientOptions);
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

    @Override
    public void getCmsBlockForLang(String blockId, String langCode,
            Handler<AsyncResult<CmsMutilangDataResponse>> resultHandler) {
        if (StringUtils.isBlank(blockId)) {
            resultHandler.handle(Future.failedFuture("blockId is empty"));
        } else {
            MutiLangBlockUrl blockUrl = new MutiLangBlockUrl();
            blockUrl.setId(blockId);
            if (StringUtils.isNotBlank(langCode) && langCode.startsWith("en") && langCode.contains("_")) {
                langCode = "en";
            }
            blockUrl.setLang(langCode);
            String requestURI = blockUrl.build();
            rx.Observable<HttpClientResponse> observable = RxHelper.get(rxHttpClient, MutiLangBlockUrl.REQ_PORT,
                    MutiLangBlockUrl.REQ_HOST, requestURI);
            observable.subscribe(ob -> {
                ob.toObservable().lift(RxHelper.unmarshaller(CmsMutilangDataResponse.class)).subscribe(pojo -> {
                    resultHandler.handle(Future.succeededFuture(pojo));
                });
            }, err -> {
                resultHandler.handle(Future.failedFuture(err));
            });
        }
    }

    @Override
    public void getCMSDataByPageId(long uuid, String platform, String pageId, String langCode,
            Handler<AsyncResult<String>> result) {
        CmsPageRequest pageRequest = new CmsPageRequest();
        pageRequest.setPlatform(platform);
        pageRequest.setPageId(pageId);
        pageRequest.setLang(langCode);

        getCMSDataByPageIdHttpRequest(pageRequest, result);
    }

    public void getCMSDataByPageIdHttpRequest(CmsPageRequest request, Handler<AsyncResult<String>> result) {
        long start = SystemCache.currentTimeMillis;
        circuitBreaker.<String> execute(future -> {
            String url = request.build();

            Single<HttpResponse<String>> httpRequest = webClient.get(PORT, CMS_HOST, url).as(BodyCodec.string())
                    .rxSend();
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
                logger.info("getCMSDataByPageIdHttpRequest waste time: " + (SystemCache.currentTimeMillis - start));
                logger.info("getCMSDataByPageId SUCCESS: " + ar);
            } else {
                result.handle(Future.succeededFuture(null));
                logger.error("getCMSDataByPageId error: " + ar.cause());
            }
        });
    }

    @Override
    public void getCMSColumnList(String columnId, String langCode, String platform,
            Handler<AsyncResult<String>> result) {
        ColumnListRequest request = new ColumnListRequest();
        request.setPid(NumberUtils.toInt(columnId));
        request.setLang(langCode);
        request.setPlatform(platform);

        getCMSColumnListHttpRequest(request, result);
    }

    public void getCMSColumnListHttpRequest(ColumnListRequest request, Handler<AsyncResult<String>> result) {
        long start = System.currentTimeMillis();
        circuitBreaker.<String> execute(future -> {
            String url = request.build();
            Single<HttpResponse<String>> httpRequest = webClient.get(PORT, CMS_HOST, url).as(BodyCodec.string())
                    .rxSend();
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
                logger.info("getCMSColumnListHttpRequest waste time: " + (System.currentTimeMillis() - start));
                logger.info("getCMSColumnList SUCCESS: " + ar);
            } else {
                result.handle(Future.failedFuture(ar.cause()));
                logger.error("getCMSColumnList error: " + ar.cause());
            }
        });
    }

}
