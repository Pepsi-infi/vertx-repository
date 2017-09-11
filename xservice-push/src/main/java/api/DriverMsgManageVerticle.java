package api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import constant.RestConstants;
import enums.ErrorCodeEnum;
import io.netty.util.internal.StringUtil;
import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import service.DriverMsgService;
import service.DriverService;
import util.DateUtil;
import util.HttpUtil;
import xservice.RestAPIVerticle;

public class DriverMsgManageVerticle extends RestAPIVerticle {

	private final Logger logger = LoggerFactory.getLogger(DriverMsgManageVerticle.class);

	private HttpServer httpServer;

	private Router router;

	private JsonObject config;

	private DriverMsgService driverMsgService;

	private DriverService driverService;

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
		router.route().handler(CorsHandler.create("*"));
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
		// 获取司机列表
		router.route(RestConstants.DRIVER_QUERY).handler(this::queryDriver);
		// 监听8080端口
		httpServer.requestHandler(router::accept).listen(8080);
	}

	private void initService() {

		driverMsgService = DriverMsgService.createProxy(vertx);
		driverService = DriverService.createProxy(vertx);
	}

	private void saveAndSendMsg2Driver(RoutingContext context) {
		HttpServerRequest request = context.request();
		JsonObject dto = HttpUtil.converteParams2JsonObject(request.params());
		Future<Integer> addFuture = Future.future();
		driverMsgService.addDriverMsg(dto, addFuture.completer());
		addFuture.setHandler(res -> {
			if (res.succeeded()) {
				Integer updateNum = res.result();

				if (updateNum != 1) {
					HttpUtil.writeFailResponse2Client(context.response(), res.cause().getMessage());
					return;
				}
				//返回成功到前端页面
				HttpUtil.writeSuccessResponse2Client(context.response(), updateNum);
				//发送push
				this.sendDriverMsg(dto);

			} else {
				HttpUtil.writeFailResponse2Client(context.response(), res.cause().getMessage());
			}
		});

	}

	/**
	 * 发送消息
	 * 
	 * @param dto
	 */
	private void sendDriverMsg(JsonObject dto) {

		String sendAll = dto.getString("sendAll");
		String driverIds = dto.getString("driverIds");
		// supplierId=0 查询全部供应商
		String supplierId = dto.getString("supplierId");
		// cityIds=0 查询全部城市
		String cityIds = dto.getString("cityIds");

		if ("1".equals(sendAll) || "2".equals(sendAll) || "4".equals(sendAll)) {
			JsonObject driversJson = new JsonObject();
			driversJson.put("driverIds", driverIds);
			//this.sendDriverMsgByQueryDriverList(driversJson);
			return;
		}

		if ("3".equals(sendAll) && !StringUtil.isNullOrEmpty(driverIds) && driverIds.length() > 0) {
			this.sendDriverMsgByQueryDriverList(driverIds,dto);
		}

		Future<JsonObject> driverListFuture = Future.future();

		// driverService.queryBatchDriver(dto, driverListFuture.completer());
		// driverListFuture.setHandler(handler)

	}

	private void sendDriverMsgByQueryDriverList(String driverIdsStr, JsonObject dto) {
		JsonObject query = new JsonObject();
		JsonArray jsonArray=new JsonArray();
		String[] driverIds=driverIdsStr.split("\\,");
		for(String driverId:driverIds){
			jsonArray.add(Integer.valueOf(driverId));
		}	
		query.put("driverId", new JsonObject().put("$in", jsonArray));
		
		Future<JsonObject> driverFuture=Future.future();
		driverService.queryBatchDriver(query, driverFuture.completer());
			
		driverFuture.setHandler(res->{
			if(res.succeeded()){
				JsonObject result=res.result();
				List<JsonObject> driverList=(List<JsonObject>) result.getValue("driverList");
				for(JsonObject driver:driverList){
					JsonObject news=buildAnAdverNew(driver,dto);
					
				}
			}else{
				
			}
		});
		
	}

	private JsonObject buildAnAdverNew(JsonObject driver, JsonObject dto) {
		JsonObject news=new JsonObject();
		news.put("newId", dto.getString("id"));
		news.put("isScreen", dto.getString("isShellsScreen"));
		news.put("title", dto.getString("title"));
		news.put("detil", dto.getString("content"));
		news.put("linkAdd", dto.getString("jumpUrl"));
		news.put("msgTime", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		news.put("ifImport", dto.getString("isImportant"));
		news.put("userId", driver.getString("driverId"));
		news.put("userType", "1");//0：乘客  1：司机
		return null;
	}

	private void getDriverMsgList(RoutingContext context) {
		HttpServerRequest request = context.request();
		MultiMap params = request.params();
		JsonObject dto = HttpUtil.converteParams2JsonObject(params);
		driverMsgService.selectByPage(dto, resultHandler(context));
	}

	private void getProviderList(RoutingContext context) {
		// TODO 供应商接口暂时未提供
		HttpServerResponse response = context.response();

		Future<JsonObject> resultFuture = Future.future();
		Map<String, String> params = new HashMap<>();

//		if (1 == 1) {
//			List<Map<String, Object>> list = new ArrayList<>();
//			for (int i = 0; i < 5; i++) {
//				Map<String, Object> map = new HashMap<>();
//				map.put("providerId", i + 1);
//				map.put("providerName", "provider" + (i + 1));
//				list.add(map);
//			}
//			HttpUtil.writeSuccessResponse2Client(response, list);
//			return;
//		}

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

		if (1 == 1) {
			List<Map<String, Object>> list = new ArrayList<>();
			for (int i = 0; i < 5; i++) {
				Map<String, Object> map = new HashMap<>();
				map.put("cityId", i + 1);
				map.put("cityName", "cityName" + (i + 1));
				list.add(map);
			}
			HttpUtil.writeSuccessResponse2Client(response, list);
			return;
		}

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

		Future<JsonObject> detailFuture = Future.future();
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

	private void queryDriver(RoutingContext context) {
		JsonObject query = new JsonObject();
		String driverName = context.request().params().get("driverName");
		String driverPhone = context.request().params().get("driverPhone");
		String cityId = context.request().params().get("cityId");
		String supplierId = context.request().params().get("supplierId");
		String page = context.request().params().get("page");
		String size = context.request().params().get("size");
		if (StringUtils.isNotBlank(driverName)) {
			query.put("driverName", driverName);
		}
		if (StringUtils.isNotBlank(driverPhone)) {
			query.put("driverPhone", driverPhone);
		}
		if (StringUtils.isNotBlank(cityId)) {
			query.put("cityId", NumberUtils.toInt(cityId));
		}
		if (StringUtils.isNotBlank(supplierId)) {
			query.put("supplierId", NumberUtils.toInt(supplierId));
		}
		driverService.queryDriver(query, NumberUtils.toInt(page), NumberUtils.toInt(size), resultHandler(context));
	}

}
