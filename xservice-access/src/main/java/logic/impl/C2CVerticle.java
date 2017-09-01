package logic.impl;

import org.apache.commons.lang.StringUtils;

import constants.IMMessageConstant;
import helper.XProxyHelper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import logic.C2CService;
import logic.SessionService;
import persistence.MongoService;
import protocol.MessageBuilder;
import utils.IPUtil;

public class C2CVerticle extends AbstractVerticle implements C2CService {

	private static final Logger logger = LoggerFactory.getLogger(C2CVerticle.class);

	private EventBus eb;

	// private SharedData sharedData;
	// private LocalMap<String, String> sessionMap;// uid -> handlerID
	// private LocalMap<String, String> sessionReverse; // handlerID -> uid

	private MongoService mongoService;

	private SessionService sessionService;

	private static final String MONGO_COLLECTION = "message";

	@Override
	public void start() throws Exception {
		// sharedData = vertx.sharedData();
		// sessionMap = sharedData.getLocalMap("session");
		// sessionReverse = sharedData.getLocalMap("sessionReverse");

		XProxyHelper.registerService(C2CService.class, vertx, this, C2CService.SERVICE_ADDRESS);

		mongoService = MongoService.createProxy(vertx);
		sessionService = SessionService.createProxy(vertx);

		String innerIP = IPUtil.getInnerIP();
		eb = vertx.eventBus();
		eb.<JsonObject>consumer(SessionService.SERVICE_ADDRESS + innerIP, res -> {
			MultiMap headers = res.headers();
			JsonObject param = res.body();
			if (headers != null) {
				String action = headers.get("action");
				switch (action) {
				case "sendMessage":
					res.reply(sendMessage(headers, param));
					break;
				default:
					res.reply(1);// Fail!
					break;
				}
			}
		});
	}

	private int sendMessage(MultiMap headers, JsonObject msg) {
		int result = 0;
		if (msg != null) {
			String to = null;
			try {
				to = msg.getString("to");
			} catch (Exception e) {
				result = 1;
				logger.error("Parse json error.", e);
			}
			if (StringUtils.isNotEmpty(to)) {
				sessionService.getHandlerIDByUid(to, res -> {
					if (res.succeeded()) {
						String toHandlerID = res.result();
						JsonObject header = msg.getJsonObject("header");
						JsonObject body = msg.getJsonObject("body");

						int clientVersion = header.getInteger("clientVersion");
						int cmd = header.getInteger("cmd");
						int bodyLength = body.toString().length();

						Buffer headerBuffer = MessageBuilder.buildMsgHeader(IMMessageConstant.HEADER_LENGTH,
								clientVersion, cmd, bodyLength);
						eb.send(toHandlerID, headerBuffer.appendString(body.toString()).appendString("\001"));
					} else {
						logger.error("sendMessage error.", res.cause());
					}
				});
			} else {
				result = 1;
			}
		}

		return result;
	}

	@Override
	public void doWithFileUpload(JsonObject msg, Handler<AsyncResult<JsonObject>> resultHandler) {
		//  gei to fa msg, xiao xi ru ku
	}
}
