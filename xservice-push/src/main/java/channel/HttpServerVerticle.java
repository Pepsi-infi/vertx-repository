package channel;

import java.util.concurrent.LinkedBlockingQueue;

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
import xservice.RestAPIVerticle;

public class HttpServerVerticle extends RestAPIVerticle {

	private final Logger logger = LoggerFactory.getLogger(HttpServerVerticle.class);

	private AdMessagePushService adMessagePushService;

	private NonAdMessagePushService nonAdMessagePushService;
	
	private ConfigService configService;

	private JsonObject config;
	
	private final int max_msg_size=100000;
	
	private LinkedBlockingQueue<String> queue=new LinkedBlockingQueue<>(max_msg_size);
	
	//TODO 用RocketMQ来实现  接收到的消息就放入消息队列，消费过程中加入处理标识true，判断标识来分批消费 false，批量消费成功，标识置为true

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
		// 获取senderId senderKey
		router.route("/mc-push/message/getVerifyFromMsgCenter.json").handler(this::getVerifyFromMsgCenter);

		httpServer.requestHandler(router::accept).listen(config.getInteger("PUSH_MSG_PORT"));

	}

	private void initService() {
		adMessagePushService = AdMessagePushService.createProxy(vertx);
		nonAdMessagePushService = NonAdMessagePushService.createProxy(vertx);
		configService=ConfigService.createProxy(vertx);
	}

	private void pushAdMsg(RoutingContext context) {
		logger.info("###pushAdMsg method start###");
		HttpServerRequest request = context.request();
		String msg=request.getParam("body");
		if(!queue.offer(msg)){
			//队列已经满了
			logger.error("this mc message queue is full");
		}
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
	
	public static void main(String[] args) {
		LinkedBlockingQueue<String> queue=new LinkedBlockingQueue<>(10);
		
		while(queue.offer("好好学习")){
			System.out.println("队列大小："+queue.size());

		}
		System.out.println("队列大小："+queue.size());

		System.out.println(queue.offer("1234"));
		
		System.out.println("队列大小："+queue.size());


	}
}
