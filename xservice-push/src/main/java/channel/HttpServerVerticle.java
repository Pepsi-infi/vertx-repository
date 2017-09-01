package channel;

import io.netty.util.internal.StringUtil;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import service.AdMessagePushService;
import service.NonAdMessagePushService;
import utils.JsonUtil;
import xservice.RestAPIVerticle;

public class HttpServerVerticle extends RestAPIVerticle {

	private final Logger logger = LoggerFactory.getLogger(HttpServerVerticle.class);

	private AdMessagePushService adMessagePushService;

	private NonAdMessagePushService nonAdMessagePushService;

	private JsonObject config;

	@Override
	public void start() throws Exception {
		config = config().getJsonObject("push.config");

		this.initWebService();

		// 初始化化服务
		this.initService();
	}

	private void initWebService() {
		HttpServer httpServer = vertx.createHttpServer();
		Router router = Router.router(vertx);
		router.route().handler(CorsHandler.create("*"));
		router.route().handler(BodyHandler.create());

		// 大后台乘客端广告消息推送
		router.route(config.getString("PUSH_MSG_URL")).handler(this::pushAdMsg);
		// 非广告消息推送
		router.route(config.getString("PUSH_MSG_NO_ADVER_URL")).handler(this::pushNonAdMsg);

		httpServer.requestHandler(router::accept).listen(config.getInteger("PUSH_MSG_PORT"));

	}

	private void initService() {
		adMessagePushService = AdMessagePushService.createProxy(vertx);
		nonAdMessagePushService=NonAdMessagePushService.createProxy(vertx);
	}

	private void pushAdMsg(RoutingContext context) {
		HttpServerRequest request = context.request();
		adMessagePushService.pushMsg(request.getParam("body"), resultHandler(context, JsonUtil::encodePrettily));
	}

	private void pushNonAdMsg(RoutingContext context) {
		HttpServerRequest request = context.request();
		nonAdMessagePushService.pushMsg(request.getParam("senderId"), request.getParam("senderKey"),request.getParam("body"), resultHandler(context, JsonUtil::encodePrettily));
	}
}
