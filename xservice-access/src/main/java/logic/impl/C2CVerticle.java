package logic.impl;

import java.time.LocalDate;

import org.apache.commons.lang.StringUtils;

import constants.IMMessageConstant;
import helper.XProxyHelper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.DeliveryOptions;
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
		eb.<JsonObject>consumer(C2CVerticle.SERVICE_ADDRESS + innerIP, res -> {
			logger.info("C2CVerticle, {}", res.body().encode());
			MultiMap headers = res.headers();
			JsonObject param = res.body();
			if (headers != null) {
				String action = headers.get("action");
				logger.info("action={}", action);
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
		logger.info("send start ... ");
		int result = 0;
		if (msg != null) {
			String to = null;
			try {
				to = msg.getJsonObject("body").getString("to");
				logger.info("send to={}", to);
			} catch (Exception e) {
				result = 1;
				logger.error("sendMessage, Parse json error.", e);
			}
			if (StringUtils.isNotEmpty(to)) {

				DeliveryOptions option = new DeliveryOptions();
				option.addHeader("action", "getHandlerIDByUid");
				option.setSendTimeout(3000);
				JsonObject p = new JsonObject().put("to", to);
				eb.<JsonObject>send(SessionService.SERVICE_ADDRESS + "10.10.10.193", p, option, res -> {
					logger.info("sendMessage, {}", res.result());
					if (res.succeeded()) {
						JsonObject res11 = res.result().body();
						String toHandlerID = res11.getString("handlerID");
						JsonObject header = msg.getJsonObject("header");
						JsonObject body = msg.getJsonObject("body");
						long ts = System.currentTimeMillis();
						int clientVersion = header.getInteger("clientVersion");
						int cmd = header.getInteger("cmd");
						body.put("msgId", ts);
						body.put("cmd", cmd);
						body.put("timeStamp", ts);
						body.put("date", LocalDate.now().toString());
						body.put("status", cmd);// 已发送，未确认

						int bodyLength = body.toString().length();

						Buffer headerBuffer = MessageBuilder.buildMsgHeader(IMMessageConstant.HEADER_LENGTH,
								clientVersion, cmd, bodyLength);
						logger.info("sendMessage, toHandlerID={}body={}", body.toString());
						eb.send(toHandlerID, headerBuffer.appendString(body.toString()).appendString("\001"));

						/**
						 * mongo message data: message body + msgId + timeStamp + date
						 */
						mongoService.saveDataBatch(body, mongo -> {
						});
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

	public static void main(String[] args) {
		System.out.println(LocalDate.now().toString());
	}
}
