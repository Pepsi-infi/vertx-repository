package com.message.push;

import java.util.UUID;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.JsonObject;

public class HttpConsumerVerticleTest {

	public static void main(String[] args) {
		
		Vertx vertx=Vertx.vertx();
		
		HttpClient httpClient= vertx.createHttpClient();
		
		JsonObject json=new JsonObject().put("deviceType","2").put("msgId", UUID.randomUUID().toString()).put("customerId", 123).put("deviceToken", "macAddr").put("title", "发券啦").put("content", "送您一张10元优惠券");
		
		
//		httpClient.post("http:/127.0.0.1:8989/sqyc/push/sokcet.htm", res->{
//			
//			System.out.println(res.statusCode());
//			
//		}).putHeader("content-type", "text/plain").end(json.toString());
		
		//httpClient.post(8989, "http://127.0.0.1", "/").putHeader("content-type", "text/plain").end(json.toString());
		
		
//		httpClient.getNow("http://localhost:8989/sqyc/push/sokcet.htm?body="+json.toString(), res->{
//			System.out.println(res.statusCode());
//		});
		
		httpClient.getNow(8989, "127.0.0.1", "/sqyc/push/sokcet.htm?body="+json.toString(), res->{
			System.out.println(res.statusCode());
		});
		
	}
}
