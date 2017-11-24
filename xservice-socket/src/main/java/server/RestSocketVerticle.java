package server;

import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import api.RestConstants;
import cluster.impl.SocketConsistentHashingVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonArray;
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

	private int socketPort;

	private int socketHTTPPort;

	@Override
	public void start() throws Exception {
		super.start();

		logger.info("start ...");

		eb = vertx.eventBus();
		innerIP = IPUtil.getInnerIP();
		socketPort = config().getInteger("tcp.port");
		socketHTTPPort = config().getInteger("socket.http.port");

		Router router = Router.router(vertx);
		router.route(RestConstants.Socket.GET_SOCKET_HOST).handler(this::getSocketHost);
		router.route(RestConstants.Socket.GET_SOCKET_HOST_PASSENGER).handler(this::getSocketHost);
		router.route(RestConstants.Socket.SOCKET_STATUS).handler(this::socketStatus);
		Future<Void> voidFuture = Future.future();

		String serverHost = getServerHost();
		createHttpServer(router, serverHost, socketHTTPPort)
				.compose(serverCreated -> publishHttpEndpoint(RestConstants.Socket.SERVICE_NAME, serverHost,
						socketHTTPPort, RestConstants.Socket.SERVICE_ROOT))
				.setHandler(voidFuture.completer());
	}

	private String getServerHost() {
		return StringUtils.isNotBlank(IPUtil.getInnerIP()) ? IPUtil.getInnerIP() : config().getString("service.host");
	}

	private void getSocketHost(RoutingContext context) {
		String userId = context.request().params().get("userId");
		String chatUserId = context.request().params().get("chatUserId");
		String identity = context.request().params().get("identity");
		logger.info("getSocketHost, userId={}chatUserId={}identity={}", userId, chatUserId, identity);

		JsonObject result = new JsonObject();
		JsonObject data = new JsonObject();

		if (StringUtils.isNotEmpty(userId)) {
			// 判断白名单
			DeliveryOptions whiteListOp = new DeliveryOptions();
			whiteListOp.setSendTimeout(3000);
			if (0 == NumberUtils.toInt(identity)) {// 司机
				whiteListOp.addHeader("action", "isWhiteListUser");
			} else if (1 == NumberUtils.toInt(identity)) {
				whiteListOp.addHeader("action", "whiteListPassenger");
			}
			JsonObject whiteListMessage = new JsonObject();
			whiteListMessage.put("uid", userId);

			eb.<JsonObject>send("config.whitelist.WhiteListConfigVerticle", whiteListMessage, whiteListOp, r -> {
				if (r.succeeded()) {
					JsonObject whiteListR = r.result().body();
					if (whiteListR.getBoolean("result")) {
						DeliveryOptions option = new DeliveryOptions();
						option.setSendTimeout(3000);
						option.addHeader("action", "getSocketNode");

						JsonObject message = new JsonObject();
						message.put("userId", chatUserId);
						eb.<JsonObject>send(SocketConsistentHashingVerticle.class.getName() + innerIP, message, option,
								reply -> {
									if (reply.succeeded()) {
										result.put("code", 0);
										result.put("time", System.currentTimeMillis());
										result.put("data", data.put("host",
												reply.result().body().getString("host") + ":" + socketPort));

										logger.info("getSocketHost, chatUserId={}node={}", chatUserId,
												reply.result().body());

										context.response().putHeader("content-type", "application/json")
												.end(result.encode(), ENCODE);
									} else {
										result.put("code", 500);
										result.put("time", System.currentTimeMillis());
										result.put("msg", reply.cause().getMessage());
										context.response().setStatusCode(500)
												.putHeader("content-type", "application/json")
												.end(result.encode(), ENCODE);
									}
								});
					} else {
						JsonArray oldSocket = config().getJsonArray("oldSocket");
						Random rand = new Random();
						int pos = rand.nextInt(oldSocket.size());

						result.put("code", 0);
						result.put("time", System.currentTimeMillis());
						result.put("data", data.put("host", oldSocket.getString(pos) + ":" + socketPort));

						logger.info("getSocketHost, pos={}", pos);
						context.response().putHeader("content-type", "application/json").end(result.encode(), ENCODE);
					}
				} else {
					result.put("code", 500);
					result.put("time", System.currentTimeMillis());
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
