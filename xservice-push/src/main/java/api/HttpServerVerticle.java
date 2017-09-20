package api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import constant.MsgHttpConsts;
import constant.PushUrlConstants;
import constant.RestConstants;
import enums.ErrorCodeEnum;
import io.netty.util.internal.StringUtil;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.sql.UpdateResult;
import io.vertx.rxjava.core.MultiMap;
import io.vertx.rxjava.core.http.HttpServerRequest;
import io.vertx.rxjava.core.http.HttpServerResponse;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertx.rxjava.ext.web.handler.CorsHandler;
import rxjava.RestAPIVerticle;
import service.AdMessagePushService;
import service.ConfigService;
import service.DriverMsgService;
import service.DriverService;
import service.MessagePushService;
import service.NonAdMessagePushService;
import service.PassengerService;
import service.PassengerUnSendService;
import util.HttpUtil;

/**
 * 接收乘客端消息， http方式   
 */
public class HttpServerVerticle extends RestAPIVerticle {

	private static final Logger logger = LoggerFactory.getLogger(HttpServerVerticle.class);
	private JsonObject config;

	MessagePushService messagePushService;

	PassengerService passengerService;

	private DriverService driverService;

	private DriverMsgService driverMsgService;

	private AdMessagePushService adMessagePushService;

	private NonAdMessagePushService nonAdMessagePushService;

	private PassengerUnSendService passengerUnSendService;

	private ConfigService configService;

	@Override
	public void start() throws Exception {
		config = config().getJsonObject("push.config");
		// 接收消息
		this.recivedHttpMessage();

		this.initService();
	}

	private void initService() {
		messagePushService = MessagePushService.createProxy(vertx.getDelegate());
		passengerService = PassengerService.createProxy(vertx.getDelegate());
		driverService = DriverService.createProxy(vertx.getDelegate());
		driverMsgService = DriverMsgService.createProxy(vertx.getDelegate());
		adMessagePushService = AdMessagePushService.createProxy(vertx.getDelegate());
		nonAdMessagePushService = NonAdMessagePushService.createProxy(vertx.getDelegate());
		configService = ConfigService.createProxy(vertx.getDelegate());
		passengerUnSendService = PassengerUnSendService.createProxy(vertx.getDelegate());
	}

	private void recivedHttpMessage() {
		Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create());
		router.route().handler(CorsHandler.create("*"));
		router.route(config.getString("PUSH_MSG_URL")).handler(this::callPushMsgVerticle);

		// 大后台乘客端广告消息推送
		router.route(PushUrlConstants.PUSH_MSG_URL).handler(this::pushAdMsg);
		// 非广告消息推送
		router.route(PushUrlConstants.PUSH_MSG_NO_ADVER_URL).handler(this::pushNonAdMsg);
		// 获取senderId senderKey
		router.route(PushUrlConstants.PUSH_MSG_SENDERKEY).handler(this::getVerifyFromMsgCenter);
		// 用户上线，未推送成功的消息继续推送给用户
		router.route(PushUrlConstants.PUSH_MSG_UNSEND).handler(this::callUnSend);

		/**
		 * 乘客端相关
		 */
		router.route(MsgHttpConsts.PASSENGERMSG_LIST).handler(this::list);
		router.route(MsgHttpConsts.PASSENGERMSG_ADDOREDIT).handler(this::addOrUpdate);
		router.route(MsgHttpConsts.PASSENGERMSG_GET).handler(this::get);
		router.route(MsgHttpConsts.PASSENGERMSG_DEL).handler(this::del);
		router.route(MsgHttpConsts.PASSENGERMSG_PUSH).handler(this::pushMsg);
		router.route(MsgHttpConsts.PASSENGERMSG_GET_IMPORTFILELIST).handler(this::getImportFileList);
		router.route(MsgHttpConsts.PASSENGERMSG_GET_CITYLIST).handler(this::getCityListForPassenger);
		router.route(MsgHttpConsts.PASSENGERMSG_GET_FILEPAGE).handler(this::getImportFilePage);

		//router.route(RestConstants.DRIVER_QUERY).handler(this::queryDriverForPage);

		/**
		 * 司机端相关
		 */
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

		vertx.createHttpServer().requestHandler(router::accept).listen(config.getInteger("PUSH_MSG_PORT"));
	}

	private void pushAdMsg(RoutingContext context) {
		logger.info("###pushAdMsg method start###");
		HttpServerRequest request = context.request();
		adMessagePushService.pushMsg(request.getParam("body"), resultHandler(context));
		logger.info("###pushAdMsg method end###");
	}

	private void pushNonAdMsg(RoutingContext context) {
		logger.info("###pushNonAdMsg method start###");
		HttpServerRequest request = context.request();
		nonAdMessagePushService.pushMsg(request.getParam("senderId"), request.getParam("senderKey"),
				request.getParam("body"), resultHandler(context));
		logger.info("###pushNonAdMsg method end###");
	}

	private void getVerifyFromMsgCenter(RoutingContext context) {
		logger.info("###getVerifyFromMsgCenter method start###");
		HttpServerRequest request = context.request();
		configService.getVerifyFromMsgCenter(request.getParam("senderId"), request.getParam("senderKey"),
				resultHandler(context));
		logger.info("###getVerifyFromMsgCenter method end###");
	}

	private void callUnSend(RoutingContext context) {
		HttpServerRequest request = context.request();
		String phone = request.getParam("phone");
		passengerUnSendService.pushUnSendMsg(phone, resultHandler(context));
	}

	private void callPushMsgVerticle(RoutingContext context) {
		HttpServerResponse resp = context.response();
		HttpServerRequest request = context.request();
		String httpMsg = request.getParam("body");
		messagePushService.bisnessMessage(httpMsg, resultHandler(context));
	}

	private void saveAndSendMsg2Driver(RoutingContext context) {
		HttpServerRequest request = context.request();
		// 1.请求参数json处理
		JsonObject dto = HttpUtil.converteParams2JsonObject(request.params().getDelegate());

		// 2.新增消息入库
		Future<UpdateResult> addFuture = this.addDriverMsg(dto);

		// 3.批量新增消息明细入库,批量push
		Future<JsonObject> batchFuture = this.addDriverMsgItems(context, dto, addFuture);
		batchFuture.setHandler(handler -> {
			if (batchFuture.succeeded()) {
				logger.info("公司消息明细批量处理完成");
			} else {
				logger.error("公司消息明细批量处理失败", handler.cause());
			}
		});

		// 4.查询司机列表
		// Future<JsonObject> driversFuture = this.queryDriverList(context, dto,
		// addBatchFuture);

		// 5.发送消息
		// this.sendMsg(dto, driversFuture);
	}

	private Future<UpdateResult> addDriverMsg(JsonObject dto) {
		Future<UpdateResult> addFuture = Future.future();
		driverMsgService.addDriverMsg(dto, addFuture.completer());
		return addFuture;
	}

	private Future<JsonObject> addDriverMsgItems(RoutingContext context, JsonObject dto,
			Future<UpdateResult> addFuture) {
		return addFuture.compose(addRes -> {
			Future<JsonObject> batchFuture = Future.future();
			int updateNum = addRes.getUpdated();
			if (updateNum == 1) {
				logger.info("add new driverMsg to db success");
				HttpUtil.writeSuccessResponse2Client(context.response().getDelegate(), updateNum);

				JsonArray key = addRes.getKeys();
				Integer driverMsgId = key.getInteger(0);
				dto.put("driverMsgId", driverMsgId);
				driverMsgService.addDriverMsgItems(dto, batchFuture.completer());
			} else {
				logger.error("add new driverMsg to db error:updateNum=" + updateNum);
				HttpUtil.writeFailResponse2Client(context.response().getDelegate(), "server is error");
				batchFuture.fail("updateNum=" + updateNum);
			}
			return batchFuture;
		});
	}

	private void getDriverMsgList(RoutingContext context) {
		HttpServerRequest request = context.request();
		MultiMap params = request.params();
		JsonObject dto = HttpUtil.converteParams2JsonObject(params.getDelegate());
		driverMsgService.selectByPage(dto, resultHandler(context));
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
				HttpUtil.writeFailResponse2Client(response.getDelegate(), "server is error");
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

		// if (1 == 1) {
		// List<Map<String, Object>> list = new ArrayList<>();
		// for (int i = 0; i < 5; i++) {
		// Map<String, Object> map = new HashMap<>();
		// map.put("cityId", i + 1);
		// map.put("cityName", "cityName" + (i + 1));
		// list.add(map);
		// }
		// HttpUtil.writeSuccessResponse2Client(response.getDelegate(), list);
		// return;
		// }

		HttpUtil.doGet(params, config.getString("city.url"), resultFuture.completer());

		resultFuture.setHandler(handler -> {
			if (handler.succeeded()) {
				JsonObject result = handler.result();
				logger.info("query city list result:" + result);
				this.dealRemoteCityListResult(result, response);
			} else {
				logger.error("城市列表接口远程调用异常", handler.cause());
				HttpUtil.writeFailResponse2Client(response.getDelegate(), "server is error");
			}
		});

	}

	private void dealRemoteCityListResult(JsonObject result, HttpServerResponse response) {
		if (result == null) {
			logger.error("query city list result is null");
			HttpUtil.writeFailResponse2Client(response.getDelegate(), "query result is null");
			return;
		}

		int code = result.getInteger("code");
		String msg = result.getString("msg");

		if (ErrorCodeEnum.SUCCESS.getCode() != code) {
			logger.error(msg);
			HttpUtil.writeFailResponse2Client(response.getDelegate(), msg);
			return;
		}

		JsonObject cityJson = (JsonObject) result.getValue("data");

		JsonArray cityArray = new JsonArray();

		Iterator<Entry<String, Object>> iter = cityJson.iterator();
		while (iter.hasNext()) {
			Entry<String, Object> entry = iter.next();
			cityArray.addAll((JsonArray) entry.getValue());
		}

		HttpUtil.writeSuccessResponse2Client(response.getDelegate(), cityArray);

	}

	private void getDriverList(RoutingContext context) {
		HttpServerResponse response = context.response();
		HttpUtil.writeSuccessResponse2Client(response.getDelegate(), null);
	}

	private void getDriverMsgDetail(RoutingContext context) {
		HttpServerRequest request = context.request();
		HttpServerResponse response = context.response();

		String msgId = request.getParam("msgId");
		if (StringUtil.isNullOrEmpty(msgId)) {
			logger.error("msgId is null");
			HttpUtil.writeFailResponse2Client(response.getDelegate(), "msgId is null");
			return;
		}
		Long id = Long.valueOf(msgId);

		Future<JsonObject> detailFuture = Future.future();
		driverMsgService.getDriverMsgDetail(id, detailFuture.completer());
		detailFuture.setHandler(handler -> {
			if (handler.succeeded()) {
				HttpUtil.writeSuccessResponse2Client(response.getDelegate(), handler.result());
			} else {
				logger.error("getDriverMsgDetail error:", handler.cause());
				HttpUtil.writeFailResponse2Client(response.getDelegate(), "server is error");
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

	// 推送消息
	private void pushMsg(RoutingContext context) {
		String id = context.request().getParam("id");
		JsonObject param = new JsonObject().put("id", id);
		Future<String> future = Future.future();
		passengerService.getPushMsg(param, future);
		future.setHandler(res -> {
			if (res.succeeded()) {
				String result = res.result();
				pushPassengerMsg(result);
			} else {
				logger.error(res.cause());
			}
		});
	}

	private void list(RoutingContext context) {
		JsonObject param = buildParams(context);
		passengerService.list(param, resultHandler(context));
	}

	private void addOrUpdate(RoutingContext context) {
		HttpServerRequest request = context.request();
		JsonObject param = new JsonObject();
		param.put("id", request.getParam("id"));
		param.put("title", request.getParam("title"));
		param.put("content", request.getParam("content"));
		param.put("action", StringUtils.isBlank(request.getParam("action")) ? null : request.getParam("action"));
		param.put("msgCenterImgUrl", request.getParam("msgCenterImgUrl"));
		param.put("inMsgCenter", request.getParam("inMsgCenter"));
		param.put("openUrl", request.getParam("openUrl"));
		param.put("openType", request.getParam("openType"));
		param.put("sendType", request.getParam("sendType"));
		param.put("status", request.getParam("status"));
		param.put("expireTime", request.getParam("expireTime"));
		param.put("sendTime", request.getParam("sendTime"));
		param.put("importFileId", request.getParam("importFileId"));
		param.put("cityIds", request.getParam("cityIds"));
		param.put("inputPhones", request.getParam("inputPhones"));
		passengerService.addOrUpdate(param, resultHandler(context));
	}

	private void get(RoutingContext context) {
		String id = context.request().getParam("id");
		JsonObject param = new JsonObject().put("id", id);
		passengerService.get(param, resultHandler(context));
	}

	private void del(RoutingContext context) {
		String id = context.request().getParam("id");
		JsonObject param = new JsonObject().put("id", id);
		passengerService.del(param, resultHandler(context));
	}

	private JsonObject buildParams(RoutingContext context) {
		JsonObject param = new JsonObject();
		HttpServerRequest request = context.request();
		MultiMap map = request.params();
		for (String name : map.names()) {
			param.put(name, map.get(name));
		}
		setPageParams(request, param);
		return param;
	}

	private void setPageParams(HttpServerRequest request, JsonObject param) {
		// 查分页数据
		String pageS = request.getParam("page");
		String pageSizeS = request.getParam("pageSize");
		int page = Integer.parseInt(pageS);
		int pageSize = Integer.parseInt(pageSizeS);
		int pageIndex = (page - 1) * pageSize;
		param.put("page", page);
		param.put("pageIndex", pageIndex);
		param.put("pageSize", pageSize);
	}

	private void pushPassengerMsg(String jsonMsg) {
		if (StringUtils.isNotBlank(jsonMsg)) {
			JsonObject message = new JsonObject(jsonMsg);
			message.put("msgId", message.getValue("id"));
			message.put("jumpPage", message.getValue("action"));
			message.put("isIntoPsnCenter", message.getValue("inMsgCenter"));
			Optional<Long> sendTime = Optional.of(message.getLong("sendTime"));
			message.put("sendTime", sendTime.get());
			Optional<Long> expireTime = Optional.of(message.getLong("expireTime"));
			message.put("expireTime", expireTime.get());
			message.put("url", message.getValue("openUrl"));
			message.put("type", message.getValue("openType"));
			String sendType = message.getString("sendType");
			if (StringUtils.isNotBlank(sendType) && "1".equals(sendType)) {
				logger.info("全部用户推送消息");
				sendForAllUser(message);
			} else if (StringUtils.isNotBlank(sendType) && "2".equals(sendType)) {
				logger.info("指定用户推送消息");
				sendByOnlyUser(message);
			} else if (StringUtils.isNotBlank(sendType) && "3".equals(sendType)) {
				logger.info("指定城市推送消息");
				sendForCityUser(message);
			} else {
				logger.error("发送类型未指定，推送不执行");
			}
		} else {
			logger.error("无符合条件的消息数据，推送不执行");
		}
	}

	// 1、推送所有用户
	private void sendForAllUser(JsonObject message) {
		message.put("phone", "13621241006");
		message.put("customerId", 13666050);
	}

	// 2、按指定用户推送
	// private void sendByOnlyUser(JsonObject message){
	// String importFileId = message.getString("importFileId");
	// Future<List<JsonObject>> future = Future.future();
	// passengerService.getImportPhoneList(importFileId, future);
	// future.setHandler(res -> {
	// if (res.succeeded()) {
	// List<JsonObject> phoneList = res.result();
	// if (CollectionUtils.isNotEmpty(phoneList)) {
	// for (JsonObject phone : phoneList) {
	// message.put("phone", phone.getString("telephone"));
	// message.put("customerId", 13666050);
	// sendMessage(message);
	// }
	// } else {
	// logger.info("查询指定手机号列表为空，importFileId：" + importFileId);
	// }
	// } else {
	// logger.info("查询指定手机号列表为空失败，importFileId：" + importFileId);
	// }
	// });
	// }

	// 2、按指定用户推送
	private void sendByOnlyUser(JsonObject message) {
		try {
			// 新的指定用户，是把手机号以字符串的形式存到了数据库中
			String inputPhones = message.getString("inputPhones");
			String[] phones = inputPhones.split(",");
			if (phones != null && phones.length > 0) {
				for (String phone : phones) {
					message.put("phone", phone);
					sendMessage(message);
				}
			}
		} catch (Exception e) {
			logger.error("指定用户推送出错：" + e.getMessage());
		}
	}

	// 3、按城市推送
	private void sendForCityUser(JsonObject message) {
		message.put("phone", "13621241006");
		message.put("customerId", 13666050);
	}

	private void sendMessage(JsonObject message) {
		JsonObject buildMessage = new JsonObject();
		buildMessage.put("body", message.toString());
		Future<String> future = Future.future();
		// 调用发送消息
		messagePushService.bisnessMessage(buildMessage.toString(), future);
		future.setHandler(resPush -> {
			if (resPush.succeeded()) {
				logger.info(resPush.result());
			} else {
				logger.error(resPush.cause());
			}
		});
	}

	// 查询导入的手机号文件列表
	private void getImportFileList(RoutingContext context) {
		JsonObject param = new JsonObject();
		HttpServerRequest request = context.request();
		String createTime = request.getParam("createTime");
		param.put("createTime", createTime);
		passengerService.getImportFileList(param, resultHandler(context));
	}

	private void getCityListForPassenger(RoutingContext context) {
		Map params = new HashMap();
		logger.info("调用城市列表接口");
		HttpUtil.doGet(params, config().getString("city.list.url"), resultHandler(context));
	}

	private void getImportFilePage(RoutingContext context) {
		JsonObject param = new JsonObject();
		HttpServerRequest request = context.request();
		String createTime = request.getParam("createTime");
		param.put("createTime", createTime);
		setPageParams(request, param);
		passengerService.getImportFilePage(param, resultHandler(context));
	}

	/**
	 * 查询设备
	 * 
	 * @param context
	 */
	private void queryDriverForPage(RoutingContext context) {
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
		driverService.queryDriver(query, NumberUtils.toInt(page), NumberUtils.toInt(size),
				resultStringHandler(context));
	}

	public static void main(String[] args) {
		JsonObject cityJson = new JsonObject(
				"{\"B\":[{\"name\":\"北京\",\"id\":44},{\"name\":\"保定\",\"id\":70}],\"C\":[{\"name\":\"重庆\",\"id\":82},{\"name\":\"长春\",\"id\":88},{\"name\":\"常州\",\"id\":96}],\"D\":[{\"name\":\"大连\",\"id\":91},{\"name\":\"东莞\",\"id\":119}],\"F\":[{\"name\":\"福州\",\"id\":97},{\"name\":\"佛山\",\"id\":115}],\"G\":[{\"name\":\"广州\",\"id\":67},{\"name\":\"贵阳\",\"id\":123},{\"name\":\"桂林\",\"id\":143}],\"H\":[{\"name\":\"杭州\",\"id\":66},{\"name\":\"合肥\",\"id\":93},{\"name\":\"哈尔滨\",\"id\":94},{\"name\":\"邯郸\",\"id\":74},{\"name\":\"惠州\",\"id\":121},{\"name\":\"海口\",\"id\":139}],\"J\":[{\"name\":\"济南\",\"id\":87},{\"name\":\"嘉兴\",\"id\":141}],\"K\":[{\"name\":\"昆明\",\"id\":95}],\"L\":[{\"name\":\"兰州\",\"id\":145}],\"N\":[{\"name\":\"南京\",\"id\":73},{\"name\":\"南昌\",\"id\":89},{\"name\":\"宁波\",\"id\":92},{\"name\":\"南宁\",\"id\":99}],\"Q\":[{\"name\":\"青岛\",\"id\":83}],\"S\":[{\"name\":\"上海\",\"id\":79},{\"name\":\"苏州\",\"id\":84},{\"name\":\"沈阳\",\"id\":90},{\"name\":\"三亚\",\"id\":127},{\"name\":\"石家庄\",\"id\":105},{\"name\":\"深圳\",\"id\":111}],\"T\":[{\"name\":\"太原\",\"id\":101},{\"name\":\"天津\",\"id\":107}],\"W\":[{\"name\":\"武汉\",\"id\":71},{\"name\":\"无锡\",\"id\":85},{\"name\":\"温州\",\"id\":113},{\"name\":\"渭南\",\"id\":135}],\"X\":[{\"name\":\"厦门\",\"id\":72},{\"name\":\"西安\",\"id\":81},{\"name\":\"咸阳\",\"id\":125}],\"Y\":[{\"name\":\"银川\",\"id\":151}],\"Z\":[{\"name\":\"漳州\",\"id\":129},{\"name\":\"珠海\",\"id\":103},{\"name\":\"郑州\",\"id\":109},{\"name\":\"中山\",\"id\":117}]}");

		System.out.println(cityJson.size());
		JsonArray cityArray = new JsonArray();

		Iterator<Entry<String, Object>> iter = cityJson.iterator();
		while (iter.hasNext()) {
			Entry<String, Object> entry = iter.next();
			System.out.println("name=" + entry.getKey() + "~~value=" + cityJson.getJsonArray(entry.getKey()));
			cityArray.addAll((JsonArray) entry.getValue());
		}
		System.out.println(cityArray);
	}
}
