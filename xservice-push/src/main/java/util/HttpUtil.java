package util;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import api.RestPushVerticle;
import enums.ErrorCodeEnum;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import result.ResultData;

public class HttpUtil extends AbstractVerticle{

	private static final Logger logger = LoggerFactory.getLogger(RestPushVerticle.class);

	private static WebClient webClient;
	
	@Override
	public void start() throws Exception {
		super.start();
		webClient=WebClient.create(vertx);
	}

	public static void doPost(Map<String, String> params,String url,
			Handler<AsyncResult<JsonObject>> future) {
		
		if(params==null){
			future.handle(Future.failedFuture("params is null"));
			return;
		}
		
		Set<String> keySet=params.keySet();
		logger.info("开始调用接口地址：" + url + ", 参数：" + Json.encode(params));
		HttpRequest<Buffer> request= webClient.postAbs(url).putHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		
		if(keySet!=null&&keySet.size()!=0){
			for(String key:keySet){
				String value=params.get(key);
				request.addQueryParam(key, value);
			}
		}
		
		
		request.send(handler->{
			if(handler.succeeded()){
				logger.info("调用接口地址：" + url + ", 参数：" + Json.encode(params) + ", 返回结果：" + handler.result());
				future.handle(Future.succeededFuture(handler.result().bodyAsJsonObject()));
			}else{
				logger.error("调用接口地址：" + url + ", 参数：" + Json.encode(params) + ", 调用失败：" + handler.cause());
				future.handle(Future.failedFuture(handler.cause()));
			}
	    	 
	    });
		
	}
	
	public static void doGet(Map<String, String> params,String url,Handler<AsyncResult<JsonObject>> future){
		
		
		if(params==null){
			future.handle(Future.failedFuture("params is null"));
			return;
		}
		
		Set<String> keySet=params.keySet();
		logger.info("开始调用接口地址：" + url + ", 参数：" + Json.encode(params));
		HttpRequest<Buffer> request= webClient.getAbs(url).putHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		
		if(keySet!=null&&keySet.size()!=0){
			for(String key:keySet){
				String value=params.get(key);
				request.addQueryParam(key, value);
			}
		}
		
		
		request.send(handler->{
			if(handler.succeeded()){
				logger.info("调用接口地址：" + url + ", 参数：" + Json.encode(params) + ", 返回结果：" + handler.result());
				future.handle(Future.succeededFuture(handler.result().bodyAsJsonObject()));
			}else{
				logger.error("调用接口地址：" + url + ", 参数：" + Json.encode(params) + ", 调用失败：" + handler.cause());
				future.handle(Future.failedFuture(handler.cause()));
			}
	    	 
	    });
	}
	
	public static void writeFailResponse2Client(io.vertx.core.http.HttpServerResponse response,String msg){
		response.putHeader("content-type", "text/plain;charset=UTF-8").end(new ResultData<>(ErrorCodeEnum.FAIL.getCode(),msg,null).toString());
	}
	
	public static void writeSuccessResponse2Client(HttpServerResponse response,Object data){
		response.putHeader("content-type", "text/plain;charset=UTF-8").end(new ResultData<>(ErrorCodeEnum.SUCCESS, data).toString());
	}
	
	public static JsonObject converteParams2JsonObject(MultiMap params) {
		if (params == null) {
			return null;
		}
		List<Entry<String, String>> list = params.entries();

		if (list == null || list.size() == 0) {
			return null;
		}

		JsonObject dto = new JsonObject();
		for (Entry<String, String> entry : list) {
			dto.put(entry.getKey(), entry.getValue());
		}
		return dto;
	}
	
	
	
}
