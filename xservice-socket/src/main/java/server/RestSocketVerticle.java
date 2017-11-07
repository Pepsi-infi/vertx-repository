package server;

import org.apache.commons.lang.StringUtils;

import api.RestConstants;
import cluster.impl.SocketConsistentHashingVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.Future;
import io.vertx.rxjava.core.eventbus.EventBus;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import logic.impl.SocketSessionVerticle;
import rxjava.RestAPIVerticle;
import utils.IPUtil;

public class RestSocketVerticle extends RestAPIVerticle {

	private static final Logger logger = LoggerFactory.getLogger(RestSocketVerticle.class);

	private EventBus eb;

	private static final String ENCODE = "utf-8";

	private String innerIP;

	@Override
	public void start() throws Exception {
		super.start();

		eb = vertx.eventBus();
		innerIP = IPUtil.getInnerIP();

		Router router = Router.router(vertx);
		router.route(RestConstants.Socket.GET_SOCKET_HOST).handler(this::getSocketHost);
		router.route(RestConstants.Socket.SOCKET_STATUS).handler(this::socketStatus);
		Future<Void> voidFuture = Future.future();

		String serverHost = getServerHost();
		createHttpServer(router, serverHost, RestConstants.Socket.HTTP_PORT)
				.compose(serverCreated -> publishHttpEndpoint(RestConstants.Socket.SERVICE_NAME, serverHost,
						RestConstants.Socket.HTTP_PORT, RestConstants.Socket.SERVICE_ROOT))
				.setHandler(voidFuture.completer());
	}

	private String getServerHost() {
		return StringUtils.isNotBlank(IPUtil.getInnerIP()) ? IPUtil.getInnerIP() : config().getString("service.host");
	}

	private void getSocketHost(RoutingContext context) {

		String userId = context.request().params().get("userId");
		String identity = context.request().params().get("identity");

		JsonObject result = new JsonObject();

		DeliveryOptions option = new DeliveryOptions();
		option.setSendTimeout(3000);
		option.addHeader("action", "getSocketNode");

		JsonObject message = new JsonObject();
		message.put("userId", userId);
		if (StringUtils.isNotEmpty(userId)) {
			eb.<JsonObject>send(SocketConsistentHashingVerticle.class.getName() + innerIP, message, option, reply -> {
				if (reply.succeeded()) {
					result.put("code", 0);
					result.put("time", System.currentTimeMillis());
					// TODO
					// result.put("data", reply.result().body());
					result.put("data", "111.206.162.232:8088");

					logger.info("getSocketHost, userId={}node={}", userId, reply.result().body());

					context.response().putHeader("content-type", "application/json").end(result.encode(), ENCODE);
				} else {
					result.put("code", 500);
					result.put("time", System.currentTimeMillis());
					result.put("data", reply.cause().getMessage());
					context.response().setStatusCode(500).putHeader("content-type", "application/json")
							.end(result.encode(), ENCODE);
				}
			});
		} else {
			result.put("code", 400);
			result.put("time", System.currentTimeMillis());
			result.put("data", "param userId must not be null");
			context.response().setStatusCode(400).putHeader("content-type", "application/json").end(result.encode(),
					ENCODE);
		}

	}

	private void socketStatus(RoutingContext context) {
		String userId = context.request().params().get("userId");

		DeliveryOptions option = new DeliveryOptions();
		option.setSendTimeout(3000);
		option.addHeader("action", "status");

		JsonObject message = new JsonObject();
		message.put("userId", userId);

		eb.<JsonObject>send(SocketSessionVerticle.class.getName() + IPUtil.getInnerIP(), message, option, reply -> {
			if (reply.succeeded()) {
				context.response().putHeader("content-type", "application/json").end(reply.result().body().encode(),
						ENCODE);
			} else {
				context.response().setStatusCode(500).putHeader("content-type", "application/json")
						.end(new JsonObject().put("error", reply.cause().getMessage()).encode(), ENCODE);
			}
		});
	}
}
