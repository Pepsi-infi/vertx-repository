package com.message.push;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.internal.StringUtil;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.rxjava.core.buffer.Buffer;
import util.HttpUtils;
import utils.BaseResponse;

public class HttpConsumerVerticleTest {

	public static void main(String[] args) {
		
		Vertx vertx=Vertx.vertx();
		
		HttpClient httpClient= vertx.createHttpClient();
		JsonObject json=new JsonObject()
				.put("expireTime","1502419460000")
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

		
		WebClient client=WebClient.create(vertx);
		
//		client.postAbs("http://127.0.0.1:8989/mc-push/message/push.json").addQueryParam("uid", "13666050").send(res->{
//			if(res.succeeded()){
//				System.out.println("异步Http调用本地接口返回结果"+res.result().bodyAsString());
//			}
//		});
//		
		Map<String, String> params=new HashMap<String, String>();
		params.put("uid", "13666050");
//		try {
//			String result= HttpUtils.URLPost("http://127.0.0.1:8989/mc-push/message/push.json", params, "utf-8");
//			System.out.println("同步Http调用本地接口返回结果"+result);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		
		client.post(18080, "10.10.10.105", "/webservice/passenger/webservice/chat/checkSocket").addQueryParam("uid", "13666050").
		putHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8").send(res->{
			if(res.succeeded()){
				System.out.println(res.result().statusCode());
				System.out.println("异步Http调用socket检查接口返回结果："+res.result().bodyAsJsonObject());
			}
		});
//			Content-Type: application/x-www-form-urlencoded;charset=utf-8
		
	   try {
		String result= HttpUtils.URLPost("http://10.10.10.105:18080/webservice/passenger/webservice/chat/checkSocket", params, "utf-8");
		System.out.println("同步Http调用socket检查接口返回结果："+result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	   
	   
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
