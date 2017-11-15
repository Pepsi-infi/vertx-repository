package module.webclient;

import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.core.*;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.client.WebClient;
import utils.MD5Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

/**
 * 发起HTTP请求操作
 * @author JHMI on 2017/11/10.
 */
public class HttpRequestVerticle extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestVerticle.class.getName());
    private WebClient webClient;
    private CircuitBreaker circuitBreaker;
    private EventBus eb;
    private JsonObject config;

    public interface HttpMethod{
        String GET_REAL_PHONE = "getRealPhone";
    }

    public interface HttpResCode{
        String SUCCESS_CODE = "0000";
        String FAIL_CODE = "9999";
    }

    @Override
    public void start() throws Exception {
        eb = vertx.eventBus();
        webClient = WebClient.create(vertx);
        config = vertx.getOrCreateContext().config();
        circuitBreaker = this.createCircuitBreaker(vertx,vertx.getOrCreateContext().config());

        eb.<JsonObject>consumer(HttpRequestVerticle.class.getName(), req->{
            String action = req.headers().get("action");
            JsonObject reqJson = req.body();
            logger.info("HTTP vert收到请求，action = "+action+"req = "+reqJson.toString());
            switch (action){

                case HttpMethod.GET_REAL_PHONE:
                    String orderNo = reqJson.getString("orderNo");
                    try {
                        this.getRealPhone(config,orderNo,res->{
                            req.reply(res.result());
                        });
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    logger.error("请求action不能为空");
                    break;
            }

        });
    }

    /**
     * getRealPhone
     * @param config
     * @param orderNo
     * @param handler
     */
    public void getRealPhone(JsonObject config, String orderNo, Handler<AsyncResult<JsonObject>> handler) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        JsonObject jsonObject = config.getJsonObject("order.api");
        String host = jsonObject.getString("host");
        String path = jsonObject.getString("path");

        StringBuilder sign = new StringBuilder();
        sign.append("businessId=17")
                .append("&orderNo=").append(orderNo)
                .append("&key=3das1fd84467fa671c40e8fdfd8e97gf");

        //封装请求URL
        StringBuilder reqString = new StringBuilder();
        reqString.append(host).append(path).append("?businessId=17")
        .append("&orderNo=").append(orderNo)
        .append("&sign="+ URLEncoder.encode(MD5Utils.getMD5DigestBase64(sign.toString()),"UTF-8"));
        //businessId 17
        //key 3das1fd84467fa671c40e8fdfd8e97gf
        //步骤2:  计算MD5:    byte[] md5 = md5(str);
        //步骤3: String sign = new String(Base64.encodeBase64(md5));


        JsonObject response = new JsonObject();

        Future<JsonObject> cir = circuitBreaker.execute(future -> {
            // 调用依赖服务
            logger.info("请求url= "+reqString.toString());
            webClient.getAbs(reqString.toString())
                    .send(res->{
                        if(res.result().statusCode() == 200){
                            JsonObject resJson = res.result().bodyAsJsonObject();
                            logger.info("请求order api 成功，返回报文="+resJson.toString());
                            future.complete(resJson);
                        }else{
                            logger.error("请求order api 失败，code="+res.result().statusCode()+"msg="+res.result().statusMessage());
                            future.fail(res.result().statusCode()+res.result().statusMessage());
                        }
                    });

        });
        //断路器的回调
        cir.setHandler(res->{
            if(res.succeeded()){
                JsonObject resJson = res.result();
                String msg = resJson.getString("msg");
                int code = resJson.getInteger("code");
                if(0 == code){
                    response.put("code", HttpResCode.SUCCESS_CODE);
                    response.put("result",resJson.getJsonObject("data"));
                    handler.handle(Future.succeededFuture(response));
                }else{
                    response.put("code", HttpResCode.FAIL_CODE);
                    response.put("result",msg);
                    handler.handle(Future.succeededFuture(response));
                }
            }else{
                logger.error("请求order api 失败="+res.result());
                response.put("code", HttpResCode.FAIL_CODE);
                response.put("result",res.result());
                handler.handle(Future.failedFuture(response.toString()));
            }
        });
        //回退逻辑
        circuitBreaker.<JsonObject>fallback(res->{
            res.printStackTrace();
            logger.error("请求order api失败，执行fallback方法");
            response.put("code", HttpResCode.FAIL_CODE);
            response.put("result",res.getMessage());
            return response;
        });
    }

    private CircuitBreaker createCircuitBreaker(Vertx vertx, JsonObject config) {
        JsonObject circuitObject = config.getJsonObject("circuit-breaker");
        JsonObject cbOptions = circuitObject != null ? circuitObject : new JsonObject();
        CircuitBreakerOptions options = new CircuitBreakerOptions();
        options.setMaxFailures(cbOptions.getInteger("max-failures", 3));
        options.setTimeout(cbOptions.getLong("timeout", 3000L));
        options.setFallbackOnFailure(true);
        options.setResetTimeout(cbOptions.getLong("reset-timeout", 30000L));
        String name = cbOptions.getString("name", "circuit-breaker");

        return CircuitBreaker.create(name, vertx, options);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

}
