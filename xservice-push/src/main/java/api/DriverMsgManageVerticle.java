package api;

import domain.DriverMsg;
import domain.Page;
import enums.ErrorCodeEnum;
import io.netty.util.internal.StringUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import service.DriverMsgService;
import util.HttpUtil;

import java.util.*;

public class DriverMsgManageVerticle extends AbstractVerticle {

	private final Logger logger = LoggerFactory.getLogger(DriverMsgManageVerticle.class);

	private HttpServer httpServer;

	private Router router;

	private JsonObject config;

	private DriverMsgService driverMsgService;

	@Override
	public void start() throws Exception {

		super.start();

		config = config().getJsonObject("push.config");

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
		// 查看消息详情
		router.route("/mc-push/message/getDriverMsgDetail.json").handler(this::getDriverMsgDetail);
		// 监听8080端口
		httpServer.requestHandler(router::accept).listen(8080);
	}

	private void initService() {

		driverMsgService = DriverMsgService.createProxy(vertx);
	}

	private void saveAndSendMsg2Driver(RoutingContext context) {
		HttpServerRequest request = context.request();
		HttpServerResponse response = context.response();

		MultiMap params = request.params();
		String title= params.get("title");
		JsonObject dto = JsonObject.mapFrom(params);

		Future<Integer> addFuture = Future.future();
		driverMsgService.addDriverMsg(dto, addFuture.completer());
		addFuture.setHandler(handler -> {

			if (handler.succeeded()) {
				int addResult = handler.result();
				if (addResult == 1) {
					this.sendDriverMsg(dto);
				}
			} else {

			}
		});
	}

	private void sendDriverMsg(JsonObject dto) {
		// TODO Auto-generated method stub

	}

	private DriverMsg buildAnDriverMsg(MultiMap params) {
		DriverMsg driverMsg = new DriverMsg();

		driverMsg.setTitle(params.get("title"));
		driverMsg.setIsImportant(Integer.valueOf(params.get("isImportant")));
		driverMsg.setIsShellsScreen(Integer.valueOf(params.get("isShellsScreen")));
		driverMsg.setContent(params.get("content"));
		driverMsg.setJumpUrl(params.get("jumpUrl"));
		driverMsg.setSynopsis(params.get("synopsis"));
		return driverMsg;
	}

	private void getDriverMsgList(RoutingContext context) {
		HttpServerRequest request = context.request();
		HttpServerResponse response = context.response();
		MultiMap params = request.params();
		JsonObject dto = JsonObject.mapFrom(params);
		
		DriverMsg record=buildTestDriverMsg();
		List<DriverMsg> list=new ArrayList<>();
		list.add(record);
		Page page=new Page(1, 10, list, 1L);
		HttpUtil.writeSuccessResponse2Client(response, page);
		return;


//		Future<Page> pageFuture = Future.future();
//		driverMsgService.selectByPage(dto, pageFuture.completer());
//		pageFuture.setHandler(pageHandler -> {
//			if (pageHandler.succeeded()) {
//				HttpUtil.writeSuccessResponse2Client(response, pageHandler.result());
//			} else {
//				HttpUtil.writeFailResponse2Client(response, "server is error");
//			}
//		});
	}

	private DriverMsg buildTestDriverMsg() {
		DriverMsg record = new DriverMsg();
		record.setId(1l);
		record.setContent("好好学习,天天向上");
		record.setCreatedTime(new Date());
		record.setCreatedUser("admin");
		record.setEnabled(1);
		record.setIsImportant(1);
		record.setIsShellsScreen(1);
		record.setJumpUrl("http://www.baidu.com");
		record.setMsgType(1);
		record.setStatus(1);
		record.setSynopsis("如何好好学习");
		record.setTitle("学习");
		record.setUpdatedTime(new Date());
		record.setUpdatedUser("admin");
		return record;
	}

	private void getProviderList(RoutingContext context) {
		// TODO 供应商接口暂时未提供
		HttpServerResponse response = context.response();

		Future<JsonObject> resultFuture = Future.future();
		Map<String, String> params = new HashMap<>();

		HttpUtil.doGet(params, config.getString("provider.url"), resultFuture.completer());

		resultFuture.setHandler(handler -> {
			if (handler.succeeded()) {
				JsonObject result = handler.result();
				logger.info("query provider list result:" + result);
				this.dealRemoteCityListResult(result, response);
			} else {
				logger.error("供应商列表接口调用异常", handler.cause());
				HttpUtil.writeFailResponse2Client(response, "server is error");
			}
		});
	}

	/**
	 * 获取城市列表http接口
	 * 
	 * @param context
	 */
	private void getCityList(RoutingContext context) {
		HttpServerResponse response = context.response();

		Future<JsonObject> resultFuture = Future.future();
		Map<String, String> params = new HashMap<>();
		params.put("pid", "");

		HttpUtil.doGet(params, config.getString("city.url"), resultFuture.completer());

		resultFuture.setHandler(handler -> {
			if (handler.succeeded()) {
				JsonObject result = handler.result();
				logger.info("query city list result:" + result);
				this.dealRemoteCityListResult(result, response);
			} else {
				logger.error("城市列表接口远程调用异常", handler.cause());
				HttpUtil.writeFailResponse2Client(response, "server is error");
			}
		});

	}

	private void dealRemoteCityListResult(JsonObject result, HttpServerResponse response) {
		if (result == null) {
			logger.error("query city list result is null");
			HttpUtil.writeFailResponse2Client(response, "query result is null");
			return;
		}

		int code = result.getInteger("code");
		String msg = result.getString("msg");

		if (ErrorCodeEnum.SUCCESS.getCode() != code) {
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

	private void getDriverMsgDetail(RoutingContext context) {
		HttpServerRequest request = context.request();
		HttpServerResponse response = context.response();

		String msgId = request.getParam("msgId");
		if (StringUtil.isNullOrEmpty(msgId)) {
			logger.error("msgId is null");
			HttpUtil.writeFailResponse2Client(response, "msgId is null");
			return;
		}
		Long id = Long.valueOf(msgId);

		Future<DriverMsg> detailFuture = Future.future();
		driverMsgService.getDriverMsgDetail(id, detailFuture.completer());
		detailFuture.setHandler(handler -> {
			if (handler.succeeded()) {
				HttpUtil.writeSuccessResponse2Client(response, handler.result());
			} else {
				logger.error("getDriverMsgDetail error:", handler.cause());
				HttpUtil.writeFailResponse2Client(response, "server is error");
			}
		});

	}

}
