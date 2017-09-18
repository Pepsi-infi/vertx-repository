package service;

import java.util.Map;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.UpdateResult;
import io.vertx.serviceproxy.ProxyHelper;

@ProxyGen
@VertxGen
public interface DriverMsgService {
	
	

	static DriverMsgService createProxy(Vertx vertx) {
		return ProxyHelper.createProxy(DriverMsgService.class, vertx, DriverMsgService.class.getName());
	}

	/**
	 * 新建司机端消息
	 * @param msg
	 * @param resultHandler
	 */
	void addDriverMsg(JsonObject msg, Handler<AsyncResult<UpdateResult>> resultHandler);
	
	/**
	 * 分页查询司机端消息
	 * @param dto
	 * @param resultHandler
	 */
	void selectByPage(JsonObject dto, Handler<AsyncResult<String>> resultHandler);
	/**
	 * 司机端消息详情查询
	 * @param id
	 * @param completer
	 */
	void getDriverMsgDetail(Long id, Handler<AsyncResult<JsonObject>> completer);
		
	/**
	 * 消息明细入库
	 * @param dto
	 * @param handler
	 * @return 
	 */
	void addDriverMsgItems(JsonObject dto, Handler<AsyncResult<JsonObject>> handler);
	
}
