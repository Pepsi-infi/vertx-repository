package util;

import java.util.Map;
import java.util.Set;

import enums.ErrorCodeEnum;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import result.ResultData;

public class HttpUtil extends AbstractVerticle{
	
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
		
		HttpRequest<Buffer> request= webClient.postAbs(url).putHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		
		if(keySet!=null&&keySet.size()!=0){
			for(String key:keySet){
				String value=params.get(key);
				request.addQueryParam(key, value);
			}
		}
		
		
		request.send(handler->{
			if(handler.succeeded()){
				future.handle(Future.succeededFuture(handler.result().bodyAsJsonObject()));
			}else{
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
		
		HttpRequest<Buffer> request= webClient.getAbs(url).putHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		
		if(keySet!=null&&keySet.size()!=0){
			for(String key:keySet){
				String value=params.get(key);
				request.addQueryParam(key, value);
			}
		}
		
		
		request.send(handler->{
			if(handler.succeeded()){
				future.handle(Future.succeededFuture(handler.result().bodyAsJsonObject()));
			}else{
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
	
	
}
