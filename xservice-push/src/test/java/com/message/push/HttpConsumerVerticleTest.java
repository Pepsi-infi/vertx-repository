package com.message.push;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.rxjava.core.buffer.Buffer;

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
//		httpClient.postAbs("http://127.0.0.1:8989/mc-push/message/push.json" , res->{
//			res.handler(handle->{
//				System.out.println(handle.toString());
//			});
//		}).putHeader("content-type", "text/plain").putHeader("Content-Length", "100000").end(json.toString(), "utf-8");;
		
//		HttpRequest<Buffer>
		//httpClient.
		
		WebClient client=WebClient.create(vertx);
		
		client.postAbs("http://127.0.0.1:8989/mc-push/message/push.json").addQueryParam("body", json.toString()).send(res->{
			
		});
		
//		httpClient.postAbs("http://127.0.0.1:8989/mc-push/message/push.json").putHeader("body", json.toString()).end();
		
	   
	}
}
