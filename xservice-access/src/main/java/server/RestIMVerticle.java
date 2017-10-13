package server;

import org.apache.commons.lang.StringUtils;

import cluster.ConsistentHashingService;
import constants.RestAccessConstants;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
import io.vertx.rxjava.core.Future;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import persistence.impl.MongoVerticle;
import rxjava.RestAPIVerticle;
import utils.IPUtil;

public class RestIMVerticle extends RestAPIVerticle {

	private static final Logger logger = LoggerFactory.getLogger(RestIMVerticle.class);

	private SharedData sharedData;
	private LocalMap<String, String> sessionMap;// uid -> handlerID
	private LocalMap<String, String> sessionReverse; // handlerID -> uid
	private ConsistentHashingService consistentHashingService;

	private EventBus eb;

	@Override
	public void start() throws Exception {
		super.start();

		eb = vertx.getDelegate().eventBus();

		sharedData = vertx.getDelegate().sharedData();
		sessionMap = sharedData.getLocalMap("session");
		sessionReverse = sharedData.getLocalMap("sessionReverse");
		consistentHashingService = ConsistentHashingService.createProxy(vertx.getDelegate());

		logger.info("Rest mc-access Verticle: Start...");

		Router router = Router.router(vertx);
		router.route(RestAccessConstants.ONLINE_NUMBER).handler(this::getOnlineNumber);
		router.route(RestAccessConstants.SERVER).handler(this::getIMServer);

		router.route(RestAccessConstants.GET_OFFLINE_MESSAGE).handler(this::getOfflineMessage);

		Future<Void> voidFuture = Future.future();

		String serverHost = this.getServerHost();
		createHttpServer(router, serverHost, RestAccessConstants.HTTP_PORT)
				.compose(serverCreated -> publishHttpEndpoint(RestAccessConstants.SERVICE_NAME, serverHost,
						RestAccessConstants.HTTP_PORT, RestAccessConstants.SERVICE_ROOT))
				.setHandler(voidFuture.completer());
	}

	private String getServerHost() {
		return StringUtils.isNotBlank(IPUtil.getInnerIP()) ? IPUtil.getInnerIP() : config().getString("service.host");
	}

	private void getOnlineNumber(RoutingContext context) {
		JsonObject result = new JsonObject();
		BaseDto base = new BaseDto();
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
			consistentHashingService.getNode(userTel, hashFuture.completer());
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

	private void getOfflineMessage(RoutingContext context) {
		String orderNo = context.request().getParam("orderNo");
		String timestamp = context.request().getParam("timestamp");

		JsonObject query = new JsonObject();
		query.put("sceneId", orderNo);

		JsonObject response = new JsonObject();

		response.put("code", 0);
		response.put("time", System.currentTimeMillis());

		DeliveryOptions mongoOp = new DeliveryOptions();
		mongoOp.addHeader("action", MongoVerticle.method.findOffLineMessage);
		mongoOp.setSendTimeout(3000);

		eb.<JsonObject>send(MongoVerticle.class.getName(), query, mongoOp, mongoRes -> {
			if (mongoRes.succeeded()) {

				JsonObject data = new JsonObject();

				context.response().putHeader("content-type", "application/json").end(response.encode());
			} else {
				logger.error(mongoRes.cause().getMessage());
			}
		});
	}
}
