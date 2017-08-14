package com.message.push;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.JsonObject;

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
		
		
//		httpClient.post("http:/127.0.0.1:8989/sqyc/push/sokcet.htm", res->{
//			
//			System.out.println(res.statusCode());
//			
//		}).putHeader("content-type", "text/plain").end(json.toString());
		
		//httpClient.post(8989, "http://127.0.0.1", "/").putHeader("content-type", "text/plain").end(json.toString());
		
		
//		httpClient.getNow("http://localhost:8989/sqyc/push/sokcet.htm?body="+json.toString(), res->{
//			System.out.println(res.statusCode());
//		});
		
//		httpClient.getNow(8989, "127.0.0.1", "/sqyc/push/sokcet.htm?body="+json.toString(), res->{
//			System.out.println(res.statusCode());
//		});
		httpClient.getNow(8989, "127.0.0.1", "/mc-push/message/push.json?body="+json.toString(), res->{
			res.handler(handle->{
				System.out.println(handle.toString());
			});
		});
		
	}
}
