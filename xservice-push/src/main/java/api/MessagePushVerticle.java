package api;

import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.http.HttpServerRequest;
import io.vertx.rxjava.core.http.HttpServerResponse;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import rxjava.RestAPIVerticle;
import service.MessagePushService;

/**
 * 接收乘客端消息， http方式
 */
public class MessagePushVerticle extends RestAPIVerticle {

	private static final Logger logger = LoggerFactory.getLogger(MessagePushVerticle.class);
	private JsonObject config;

	MessagePushService pushMessageService;
	@Override
	public void start() throws Exception {
		config = config().getJsonObject("push.config");
		// 接收消息
		this.recivedHttpMessage();

		this.initService();
	}

	private void initService(){
		pushMessageService = MessagePushService.createProxy(vertx.getDelegate());
	}
	private void recivedHttpMessage() {
		Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create());
		router.route(config.getString("PUSH_MSG_URL")).handler(this::callPushMsgVerticle);
		vertx.createHttpServer().requestHandler(router::accept).listen(config.getInteger("PUSH_MSG_PORT"));
	}

	private void callPushMsgVerticle(RoutingContext context){
		HttpServerResponse resp = context.response();
		HttpServerRequest request = context.request();
		String httpMsg = request.getParam("body");
		pushMessageService.bisnessMessage(httpMsg, resultHandler(context));
	}
}
