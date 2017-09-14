package api;

import constant.PushUrlConstants;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import service.AdMessagePushService;
import service.ConfigService;
import service.NonAdMessagePushService;
import service.PassengerUnSendService;
import xservice.RestAPIVerticle;

public class HttpServerVerticle extends RestAPIVerticle {

	private final Logger logger = LoggerFactory.getLogger(HttpServerVerticle.class);

	private AdMessagePushService adMessagePushService;

	private NonAdMessagePushService nonAdMessagePushService;

	private PassengerUnSendService passengerUnSendService;

	private ConfigService configService;

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
		router.route(PushUrlConstants.PUSH_MSG_URL).handler(this::pushAdMsg);
		// 非广告消息推送
		router.route(PushUrlConstants.PUSH_MSG_NO_ADVER_URL).handler(this::pushNonAdMsg);
		// 获取senderId senderKey
		router.route(PushUrlConstants.PUSH_MSG_SENDERKEY).handler(this::getVerifyFromMsgCenter);
		// 用户上线，未推送成功的消息继续推送给用户
		router.route(PushUrlConstants.PUSH_MSG_UNSEND).handler(this::callUnSend);

		logger.info("================= http server starting port " + config.getInteger("PUSH_MSG_PORT") + " ===============");
		httpServer.requestHandler(router::accept).listen(config.getInteger("PUSH_MSG_PORT"));

	}

	private void initService() {
		adMessagePushService = AdMessagePushService.createProxy(vertx);
		nonAdMessagePushService = NonAdMessagePushService.createProxy(vertx);
		configService=ConfigService.createProxy(vertx);
		passengerUnSendService = PassengerUnSendService.createProxy(vertx);
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
		HttpServerRequest request=context.request();
		configService.getVerifyFromMsgCenter(request.getParam("senderId"),request.getParam("senderKey"),resultHandler(context));
		logger.info("###getVerifyFromMsgCenter method end###");
	}

	private void callUnSend(RoutingContext context){
		HttpServerRequest request = context.request();
		String phone = request.getParam("phone");
		passengerUnSendService.pushUnSendMsg(phone, resultHandler(context));
	}

}
