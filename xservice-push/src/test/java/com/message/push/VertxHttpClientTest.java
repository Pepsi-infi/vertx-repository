package com.message.push;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;

public class VertxHttpClientTest {

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();

        HttpClient httpClient = vertx.createHttpClient();
        JsonObject json = new JsonObject()
                .put("expireTime", "1502419460000")
                .put("phone", "13621241006")
                .put("status", 1)
                .put("sendTime", "1502418620000")
                .put("type", 1)
                .put("url", "")
                .put("customerId", 13666050)
                .put("deviceToken", "")
                .put("psnCenterImgUrl", "")
                .put("msgId", 345)
                .put("devicePushType", "")
                .put("apnsToken", "")
                .put("jumpPage", 5)
                .put("isIntoPsnCenter", 1)
                .put("title", "发券啦")
                .put("content", "送您一张10元优惠券");

        WebClient client = WebClient.create(vertx);
        client.post(18080, "10.10.10.105", "/webservice/passenger/webservice/chat/checkSocket").addQueryParam("uid", "13666050").
                putHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8").send(res -> {
            if (res.succeeded()) {
                System.out.println(res.result().statusCode());
                System.out.println("异步Http调用socket检查接口返回结果：" + res.result().bodyAsJsonObject());
            }
        });

        client.postAbs("http://10.10.10.105:18085/apns/push").putHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8").addQueryParam("deviceToken", "")
                .addQueryParam("title", "温馨提示")
                .addQueryParam("body", "温馨提示").addQueryParam("msgbody", "")
                .send(responseHandler -> {
                    if (responseHandler.succeeded()) {
                        String result = responseHandler.result().bodyAsString();
                        System.out.println("apns推送返回结果：" + result);
                    }
                });

    }
}
