package channel;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import enums.ErrorCodeEnum;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.handler.BodyHandler;
import result.ResultData;
import util.HttpUtil;

public class DriverMsgManageVerticle extends AbstractVerticle {
	
	private final Logger logger=LoggerFactory.getLogger(DriverMsgManageVerticle.class);

	private HttpServer httpServer;

	private Router router;

	private JsonObject config;

	@Override
	public void start() throws Exception {

		super.start();
		
		config=config().getJsonObject("push.config");
		
		this.initWebServer();

		this.initService();

	}

	private void initWebServer() {
		httpServer = vertx.createHttpServer();
		router = Router.router(vertx);
		router.route().handler(BodyHandler.create());
		// 保存发送司机端消息
		router.route("/mc-push/message/saveAndSendMsg2Driver.json").handler(this::saveAndSendMsg2Driver);
		// 获取司机端消息记录
		router.route("/mc-push/message/getDriverMsgList.json").handler(this::getDriverMsgList);
		// 获取供应商列表记录
		router.route("/mc-push/message/getProviderList.json").handler(this::getProviderList);
		// 获取城市列表记录
		router.route("/mc-push/message/getCityList.json").handler(this::getCityList);
		// 获取司机列表记录
		router.route("/mc-push/message/getDriverList.json").handler(this::getDriverList);
		// 监听8080端口
		httpServer.requestHandler(router::accept).listen(8080);
	}

	private void initService() {
		// TODO Auto-generated method stub

	}

	private void saveAndSendMsg2Driver(RoutingContext context) {
		HttpServerResponse response = context.response();
		HttpUtil.writeSuccessResponse2Client(response, null);	
	}

	private void getDriverMsgList(RoutingContext context) {
		HttpServerResponse response = context.response();
		HttpUtil.writeSuccessResponse2Client(response, null);
	}

	private void getProviderList(RoutingContext context) {
		//TODO 供应商接口暂时未提供
		HttpServerResponse response = context.response();

		Future<JsonObject> resultFuture = Future.future();
		Map<String, String> params = new HashMap<>();

		HttpUtil.doGet(params, config.getString("provider.url"),resultFuture.completer());

		resultFuture.setHandler(handler -> {
			if (handler.succeeded()) {
				JsonObject result=handler.result();
				logger.info("query provider list result:"+result);
				this.dealRemoteCityListResult(result,response);
			} else {
				logger.error("供应商列表接口调用异常",handler.cause());
				HttpUtil.writeFailResponse2Client(response, "server is error");
			}
		});
	}
	
	/**
	 * 获取城市列表http接口
	 * @param context
	 */
	private void getCityList(RoutingContext context) {
		HttpServerResponse response = context.response();

		Future<JsonObject> resultFuture = Future.future();
		Map<String, String> params = new HashMap<>();
		params.put("pid", "");

		HttpUtil.doGet(params, config.getString("city.url"),resultFuture.completer());

		resultFuture.setHandler(handler -> {
			if (handler.succeeded()) {
				JsonObject result=handler.result();
				logger.info("query city list result:"+result);
				this.dealRemoteCityListResult(result,response);
			} else {
				logger.error("城市列表接口远程调用异常",handler.cause());
				HttpUtil.writeFailResponse2Client(response, "server is error");
			}
		});

	}

	private void dealRemoteCityListResult(JsonObject result, HttpServerResponse response) {
		if(result==null){
			logger.error("query city list result is null");
			HttpUtil.writeFailResponse2Client(response, "query result is null");
			return;
		}
		
		int code=result.getInteger("code");
		String msg=result.getString("msg");
		
		if(ErrorCodeEnum.SUCCESS.getCode()!=code){
			logger.error(msg);
			HttpUtil.writeFailResponse2Client(response, msg);
			return;
		}
		
		HttpUtil.writeSuccessResponse2Client(response, result.getValue("data"));
		
	}

	private void getDriverList(RoutingContext context) {
		HttpServerResponse response = context.response();
		HttpUtil.writeSuccessResponse2Client(response, null);
	}

}
