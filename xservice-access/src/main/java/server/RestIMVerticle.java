package server;

import org.apache.commons.lang.StringUtils;

import constants.RestIMConstants;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.rxjava.core.Future;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import module.persistence.IMData;
import module.quickphrase.QuickPhraseVerticle;
import rxjava.RestAPIVerticle;
import utils.IPUtil;

public class RestIMVerticle extends RestAPIVerticle {

	private static final Logger logger = LoggerFactory.getLogger(RestIMVerticle.class);

	private SharedData sharedData;
	private LocalMap<String, String> sessionMap;// uid -> handlerID
	private LocalMap<String, String> sessionReverse; // handlerID -> uid

	private EventBus eb;
	private MongoClient client;

	private JsonObject httpResp = new JsonObject();

	@Override
	public void start() throws Exception {
		super.start();

		eb = vertx.getDelegate().eventBus();
		client = MongoClient.createShared(vertx.getDelegate(), config());

		sharedData = vertx.getDelegate().sharedData();
		sessionMap = sharedData.getLocalMap("session");
		sessionReverse = sharedData.getLocalMap("sessionReverse");

		logger.info("Rest mc-access Verticle: Start...");

		Router router = Router.router(vertx);
		router.route(RestIMConstants.ONLINE_NUMBER).handler(this::getOnlineNumber);
		router.route(RestIMConstants.SERVER).handler(this::getIMServer);

		router.route(RestIMConstants.GET_OFFLINE_MESSAGE).handler(this::getOfflineMessage);

		router.route(RestIMConstants.get_quick_phrase).handler(this::getQuickPhrase);
		router.route(RestIMConstants.add_quick_phrase).handler(this::addQuickPhrase);

		Future<Void> voidFuture = Future.future();

//		String serverHost = this.getServerHost();
		String serverHost = "127.0.0.1";
		createHttpServer(router, serverHost, RestIMConstants.HTTP_PORT)
				.compose(serverCreated -> publishHttpEndpoint(RestIMConstants.SERVICE_NAME, serverHost,
						RestIMConstants.HTTP_PORT, RestIMConstants.SERVICE_ROOT))
				.setHandler(voidFuture.completer());
	}

	private String getServerHost() {
		return StringUtils.isNotBlank(IPUtil.getInnerIP()) ? IPUtil.getInnerIP() : config().getString("service.host");
	}

	private void getOnlineNumber(RoutingContext context) {
		JsonObject result = new JsonObject();
		Response base = new Response();
		base.setCode(0);
		base.setTime(System.currentTimeMillis());
		base.setData(result);
		result.put("sessionMap", sessionMap.size());
		result.put("sessionReverse", sessionReverse.size());

		context.response().putHeader("content-type", "application/json").end(Json.encode(base));
	}

	private void getIMServer(RoutingContext context) {
		String userTel = context.request().getParam("userTel");
		logger.info("userTel={}", userTel);
		JsonObject response = new JsonObject();
		if (StringUtils.isNotEmpty(userTel)) {
			Future<String> hashFuture = Future.future();
			hashFuture.setHandler(res -> {
				if (res.succeeded()) {
					JsonObject data = new JsonObject();
					data.put("server", res.result());
					response.put("data", data);
					response.put("code", 0);
					response.put("time", System.currentTimeMillis());
					context.response().putHeader("content-type", "application/json").end(Json.encode(response));
				} else {
					response.put("code", 1);
					response.put("msg", "Consistent Hash Error.");
					response.put("time", System.currentTimeMillis());
					context.response().putHeader("content-type", "application/json").end(Json.encode(response));
				}
			});
		} else {
			response.put("code", 1);
			response.put("msg", "param userTel is null.");
			response.put("time", System.currentTimeMillis());
			context.response().putHeader("content-type", "application/json").end(Json.encode(response));
		}

	}

	public void getOfflineMessage(RoutingContext context) {
		String orderNo = context.request().getParam("orderNo");
		String timestamp = context.request().getParam("timestamp");

		JsonObject response = new JsonObject();

		JsonObject query = new JsonObject();
		query.put("sceneId", orderNo);

		FindOptions options = new FindOptions();
		options.setLimit(100);
		options.setSort(new JsonObject().put(IMData.key_timeStamp, 1));

		JsonObject fields = new JsonObject();
		fields.put("_id", 0);
		fields.put("msgId", 1);
		fields.put("sceneType", 1);
		fields.put("sceneId", 1);
		fields.put("content", 1);
		fields.put("msgType", 1);
		fields.put("cmdId", 1);
		options.setFields(fields);

		client.findWithOptions("message", query, options, r -> {
			if (r.succeeded()) {
				logger.info("findOffLineMessage, query={}r={}", query.encode(), r.result());
				response.put("code", 0);
				response.put("time", System.currentTimeMillis());
				response.put("data", r.result());
				context.response().putHeader("content-type", "application/json; charset=utf-8").end(response.encode());
			} else {
				response.put("code", 1);
				response.put("time", System.currentTimeMillis());
				response.put("message", r.cause().getMessage());
				context.response().putHeader("content-type", "application/json; charset=utf-8").end(response.encode());
			}
		});
	}

	public void getQuickPhrase(RoutingContext context) {
		String userId = context.request().getParam("userId");
		String identity = context.request().getParam("identity");

		if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(identity)) {
			DeliveryOptions op = new DeliveryOptions();
			op.addHeader("action", "getQuickPhrase");

			JsonObject message = new JsonObject();
			message.put("userID", userId);
			message.put("identity", identity);

			eb.send(QuickPhraseVerticle.class.getName(), message, op, res -> {
				if (res.succeeded()) {
					httpResp.put("code", 0);

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
		}
	}

	public void addQuickPhrase(RoutingContext context) {
		String userId = context.request().getParam("userId");
		String identity = context.request().getParam("identity");
		String content = context.request().getParam("content");

		httpResp.clear();
		httpResp.put("time", System.currentTimeMillis());

		if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(identity) && StringUtils.isNotEmpty(content)) {
			DeliveryOptions op = new DeliveryOptions();
			op.addHeader("action", "addQuickPhrase");

			JsonObject message = new JsonObject();
			message.put("userID", userId);
			message.put("identity", identity);
			message.put("content", content);

			eb.send(QuickPhraseVerticle.class.getName(), message, op, res -> {
				if (res.succeeded()) {
					httpResp.put("code", 0);

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

}
