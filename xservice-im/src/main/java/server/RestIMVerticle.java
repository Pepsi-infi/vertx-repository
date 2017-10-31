package server;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import constants.EventbusAddressConstant;
import constants.RestIMConstants;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.rxjava.core.Future;
import io.vertx.rxjava.core.shareddata.LocalMap;
import io.vertx.rxjava.core.shareddata.SharedData;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import module.hash.IMConsistentHashingVerticle;
import module.health.HealthCheckHandler;
import module.quickphrase.QuickPhraseVerticle;
import rxjava.RestAPIVerticle;
import utils.IPUtil;

public class RestIMVerticle extends RestAPIVerticle {

	private static final Logger logger = LoggerFactory.getLogger(RestIMVerticle.class);

	private EventBus eb;
	private MongoClient client;

	private JsonObject httpResp = new JsonObject();
	private JsonObject message = new JsonObject();

	private int imTCPPort;
	private int imHTTPPort;

	private SharedData sharedData;
	private LocalMap<String, Integer> localMap;

	@Override
	public void start() throws Exception {
		super.start();

		eb = vertx.getDelegate().eventBus();
		sharedData = vertx.sharedData();
		localMap = sharedData.getLocalMap("health");
		localMap.put("health", 200);

		client = MongoClient.createShared(vertx.getDelegate(), config().getJsonObject("mongo"));

		logger.info("Rest mc-access Verticle: Start...");

		imHTTPPort = config().getInteger("im.http.port");
		imTCPPort = config().getInteger("im.tcp.port");

		Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create());

		router.route(RestIMConstants.SERVER).handler(this::getIMServer);

		router.route(RestIMConstants.GET_OFFLINE_MESSAGE).handler(this::getOfflineMessage);
		router.route(RestIMConstants.GET_OFFLINE_MESSAGE_4_KF).handler(this::getOfflineMessage);

		router.route(RestIMConstants.get_quick_phrase).handler(this::getQuickPhrase);
		router.post(RestIMConstants.add_quick_phrase).handler(this::addQuickPhrase);
		router.post(RestIMConstants.del_quick_phrase).handler(this::delQuickPhrase);

		// router.route(RestIMConstants.lbs_health_check).handler(new
		// HealthCheckHandler(localMap.get("health"))::handle);
		// router.route(RestIMConstants.offline).handler(this::offline);

		Future<Void> voidFuture = Future.future();

		String serverHost = this.getServerHost();
		createHttpServer(router, serverHost, imHTTPPort)
				.compose(serverCreated -> publishHttpEndpoint(RestIMConstants.SERVICE_NAME, serverHost, imHTTPPort,
						RestIMConstants.SERVICE_ROOT))
				.setHandler(voidFuture.completer());
	}

	private void offline(RoutingContext context) {
		localMap.put("health", 404);
		context.response().setStatusCode(200).end();
	}

	private String getServerHost() {
		return StringUtils.isNotBlank(IPUtil.getInnerIP()) ? IPUtil.getInnerIP() : config().getString("service.host");
	}

	private void getIMServer(RoutingContext context) {
		String userTel = context.request().getParam("userTel");
		logger.info("userTel={}", userTel);
		httpResp.clear();
		
		JsonObject data = new JsonObject();
		data.put("server", "124.250.33.162" + ":" + imTCPPort);
		httpResp.put("data", data);
		httpResp.put("code", 0);
		httpResp.put("time", System.currentTimeMillis());
		context.response().putHeader("content-type", "application/json").end(Json.encode(httpResp));
		
//		if (StringUtils.isNotEmpty(userTel)) {
//			DeliveryOptions chOption = new DeliveryOptions();
//			chOption.setSendTimeout(3000);
//			chOption.addHeader("action", "getIMNode");
//
//			Future<Message<JsonObject>> chFuture = Future.future();
//
//			JsonObject message = new JsonObject();
//			message.put("userId", userTel);
//			eb.<JsonObject>send(IMConsistentHashingVerticle.class.getName(), message, chOption, chFuture.completer());
//
//			chFuture.setHandler(res -> {
//				if (res.succeeded()) {
//					JsonObject data = new JsonObject();
//					data.put("server", res.result().body().getString("host") + ":" + imTCPPort);
//					httpResp.put("data", data);
//					httpResp.put("code", 0);
//					httpResp.put("time", System.currentTimeMillis());
//					context.response().putHeader("content-type", "application/json").end(Json.encode(httpResp));
//				} else {
//					httpResp.put("code", 1);
//					httpResp.put("msg", "Consistent Hash Error.");
//					httpResp.put("time", System.currentTimeMillis());
//					context.response().putHeader("content-type", "application/json").end(Json.encode(httpResp));
//					logger.error("msgRequest, {}", res.cause().getMessage());
//				}
//			});
//		} else {
//			httpResp.put("code", 1);
//			httpResp.put("msg", "param userTel is null.");
//			httpResp.put("time", System.currentTimeMillis());
//			context.response().putHeader("content-type", "application/json").end(Json.encode(httpResp));
//		}

	}

	public void getOfflineMessage(RoutingContext context) {
		String orderNo = context.request().getParam("orderNo");
		String timestamp = context.request().getParam("timestamp");// TODO

		httpResp.clear();

		JsonObject query = new JsonObject();
		query.put("sceneId", orderNo);
		if (StringUtils.isNotEmpty(timestamp) && 0 != NumberUtils.toLong(timestamp)) {
			query.put("timeStamp", new JsonObject().put("$lt", NumberUtils.toLong(timestamp)));
		}

		FindOptions options = new FindOptions();
		options.setLimit(100);

		options.setSort(new JsonObject().put("timeStamp", 1));

		JsonObject fields = new JsonObject();
		fields.put("_id", 0);
		fields.put("msgId", 1);
		fields.put("sceneType", 1);
		fields.put("sceneId", 1);
		fields.put("content", 1);
		fields.put("msgType", 1);
		fields.put("cmdId", 1);
		fields.put("fromTel", 1);
		fields.put("toTel", 1);
		fields.put("timeStamp", 1);
		fields.put("identity", 1);
		fields.put("lon", 1);
		fields.put("lat", 1);
		fields.put("sAddress", 1);
		fields.put("address", 1);
		fields.put("duration", 1);
		options.setFields(fields);

		client.findWithOptions("message", query, options, r -> {
			if (r.succeeded()) {
				logger.info("findOffLineMessage, query={}size={}", query.encode(), r.result().size());
				httpResp.put("code", 0);
				httpResp.put("time", System.currentTimeMillis());
				httpResp.put("data", r.result());
				context.response().putHeader("content-type", "application/json; charset=utf-8").end(httpResp.encode());
			} else {
				httpResp.put("code", 1);
				httpResp.put("time", System.currentTimeMillis());
				httpResp.put("message", r.cause().getMessage());
				context.response().putHeader("content-type", "application/json; charset=utf-8").end(httpResp.encode());
			}
		});
	}

	public void getQuickPhrase(RoutingContext context) {
		String userId = context.request().getParam("userId");
		String identity = context.request().getParam("identity");
		httpResp.clear();
		if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(identity)) {
			DeliveryOptions op = new DeliveryOptions();
			op.addHeader("action", QuickPhraseVerticle.method.getQuickPhrase);
			op.setSendTimeout(3000);

			message.clear();
			message.put("userID", Integer.valueOf(userId));
			message.put("identity", Integer.valueOf(identity));

			eb.<JsonObject>send(QuickPhraseVerticle.class.getName(), message, op, res -> {
				if (res.succeeded()) {
					httpResp.put("code", 0);
					JsonArray userQuickPhrase = res.result().body().getJsonArray("result");
					if (userQuickPhrase.size() < 10) {
						DeliveryOptions cOption = new DeliveryOptions();
						cOption.setSendTimeout(3000);
						cOption.addHeader("action", "retriveQuickPhrase");

						JsonObject cMsg = new JsonObject();
						cMsg.put("type", identity);
						eb.send(EventbusAddressConstant.quick_phrase_config_verticle, cMsg, cOption, confRes -> {
							if (confRes.succeeded()) {
							} else {

							}
						});
					}
					httpResp.put("data", res.result().body().getJsonArray("result"));
					httpResp.put("msg", "成功");

					logger.info("getQuickPhrase, result={}", res.result().body().encode());

					context.response().putHeader("content-type", "application/json; charset=utf-8")
							.end(httpResp.encode());
				} else {
					httpResp.put("code", 1);
					httpResp.put("msg", res.cause().getMessage());

					context.response().setStatusCode(500).putHeader("content-type", "application/json; charset=utf-8")
							.end(httpResp.encode());
				}
			});
		} else {
			// illegal param
			httpResp.put("code", 1);
			httpResp.put("data", "Illegal params!");

			context.response().setStatusCode(400).putHeader("content-type", "application/json; charset=utf-8")
					.end(httpResp.encode());
		}
	}

	public void addQuickPhrase(RoutingContext context) {
		String body = context.getBodyAsString();
		Map<String, String> paramMap = URLRequest(body);

		logger.info("addQuickPhrase, params=" + paramMap.toString());

		Integer userId = NumberUtils.toInt(paramMap.get("userId"));
		Integer identity = NumberUtils.toInt(paramMap.get("identity"));
		String content = null;
		String title = null;

		if (StringUtils.isNotEmpty(paramMap.get("title"))) {
			try {
				title = URLDecoder.decode(paramMap.get("title"), "utf-8");
			} catch (Exception e) {
				logger.error("addQuickPhrase, e={}", e.getCause().getMessage());
			}
		}

		if (StringUtils.isNotEmpty(paramMap.get("content"))) {
			try {
				content = URLDecoder.decode(paramMap.get("content"), "utf-8");
			} catch (Exception e) {
				logger.error("addQuickPhrase, e={}", e.getCause().getMessage());
			}
		}

		httpResp.clear();
		httpResp.put("time", System.currentTimeMillis());

		if (userId != null && identity != null && StringUtils.isNotEmpty(content)) {
			DeliveryOptions op = new DeliveryOptions();
			op.addHeader("action", QuickPhraseVerticle.method.addQuickPhrase);
			op.setSendTimeout(3000);

			message.clear();
			message.put("userID", userId);
			message.put("identity", identity);
			message.put("content", content);
			message.put("title", title);

			eb.<JsonObject>send(QuickPhraseVerticle.class.getName(), message, op, res -> {
				if (res.succeeded()) {// 发送消息成功
					JsonObject resJson = res.result().body();
					if (resJson.getBoolean("flag")) {
						httpResp.put("code", 0);
						httpResp.put("msg", "成功");
						httpResp.put("id", resJson.getLong("result"));
					} else {
						httpResp.put("code", 1);
						httpResp.put("msg", resJson.getString("result"));
					}

					context.response().putHeader("content-type", "application/json; charset=utf-8")
							.end(httpResp.encode());
				} else {
					httpResp.put("code", 1);
					httpResp.put("msg", res.cause().getMessage());

					context.response().setStatusCode(500).putHeader("content-type", "application/json; charset=utf-8")
							.end(httpResp.encode());
				}
			});
		} else {
			httpResp.put("code", 1);
			httpResp.put("msg", "Illegal params!");

			context.response().setStatusCode(400).putHeader("content-type", "application/json; charset=utf-8")
					.end(httpResp.encode());
		}
	}

	public void delQuickPhrase(RoutingContext context) {
		String body = context.getBodyAsString();
		Map<String, String> paramMap = URLRequest(body);
		logger.info("delQuickPhrase, params=" + paramMap.toString());

		String ids = paramMap.get("ids");
		if (StringUtils.isNotEmpty(ids)) {
			try {
				ids = URLDecoder.decode(ids, "utf-8");
				ids = ids.substring(1, ids.length() - 1);
			} catch (Exception e) {
				logger.error("delQuickPhrase, e={}", e.getMessage());
			}
		}

		httpResp.clear();
		httpResp.put("time", System.currentTimeMillis());
		if (!StringUtils.isEmpty(ids)) {
			DeliveryOptions op = new DeliveryOptions();
			op.addHeader("action", QuickPhraseVerticle.method.delQuickPhrase);
			op.setSendTimeout(3000);

			message.clear();

			message.put("ids", ids);

			eb.send(QuickPhraseVerticle.class.getName(), message, op, res -> {
				if (res.succeeded()) {
					httpResp.put("code", 0);
					httpResp.put("msg", "删除成功");

					context.response().putHeader("content-type", "application/json; charset=utf-8")
							.end(httpResp.encode());
				} else {
					httpResp.put("code", 1);
					httpResp.put("msg", res.cause().getMessage());

					context.response().setStatusCode(500).putHeader("content-type", "application/json; charset=utf-8")
							.end(httpResp.encode());
				}
			});
		} else {
			httpResp.put("code", 1);
			httpResp.put("msg", "params is null");

			context.response().setStatusCode(500).putHeader("content-type", "application/json; charset=utf-8")
					.end(httpResp.encode());
		}
	}

	/**
	 * 解析出url参数中的键值对 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
	 * 
	 * @param URL
	 *            url地址
	 * @return url请求参数部分
	 */
	public static Map<String, String> URLRequest(String URL) {
		Map<String, String> mapRequest = new HashMap<String, String>();

		String[] arrSplit = null;

		arrSplit = URL.split("[&]");
		for (String strSplit : arrSplit) {
			String[] arrSplitEqual = null;
			arrSplitEqual = strSplit.split("[=]");

			// 解析出键值
			if (arrSplitEqual.length > 1) {
				// 正确解析
				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
			} else {
				if (arrSplitEqual[0] != "") {
					// 只有参数没有值，不加入
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}

		return mapRequest;
	}

}
