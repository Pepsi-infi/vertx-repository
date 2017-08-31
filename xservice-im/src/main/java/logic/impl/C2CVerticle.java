package logic.impl;

import constants.IMCmdConstants;
import constants.IMMessageConstant;
import helper.XProxyHelper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import logic.C2CService;
import persistence.MongoService;
import protocol.MessageBuilder;

public class C2CVerticle extends AbstractVerticle implements C2CService {

	private static final Logger logger = LoggerFactory.getLogger(C2CVerticle.class);

	private EventBus eb;

	// private SharedData sharedData;
	// private LocalMap<String, String> sessionMap;// uid -> handlerID
	// private LocalMap<String, String> sessionReverse; // handlerID -> uid

	private MongoService mongoService;

	private static final String MONGO_COLLECTION = "message";

	@Override
	public void start() throws Exception {
		// sharedData = vertx.sharedData();
		// sessionMap = sharedData.getLocalMap("session");
		// sessionReverse = sharedData.getLocalMap("sessionReverse");

		XProxyHelper.registerService(C2CService.class, vertx, this, C2CService.SERVICE_ADDRESS);

		mongoService = MongoService.createProxy(vertx);

		eb = vertx.eventBus();
	}

	@Override
	public void doWithLogin(JsonObject msg, Handler<AsyncResult<JsonObject>> resultHandler) {
		String fromHandlerID = msg.getString("fromHandlerID");

		// 给FROM发A
		Buffer aMsgHeader = MessageBuilder.buildMsgHeader(IMMessageConstant.HEADER_LENGTH,
				msg.getInteger("clientVersion"), IMCmdConstants.LOGIN + 100, 0);
		logger.info("DoWithLogin " + aMsgHeader);
		eb.send(fromHandlerID, aMsgHeader.appendString("\001"));
	}

	@Override
	public void doWithLogout(JsonObject msg, Handler<AsyncResult<JsonObject>> resultHandler) {
		String fromHandlerID = msg.getString("fromHandlerID");

		// 给FROM发A
		Buffer aMsgHeader = MessageBuilder.buildMsgHeader(IMMessageConstant.HEADER_LENGTH,
				msg.getInteger("clientVersion"), IMCmdConstants.LOGOUT + 100, 0);
		eb.send(fromHandlerID, aMsgHeader.appendString("\001"));
	}

	public void doWithMsgRequest(JsonObject msg, Handler<AsyncResult<JsonObject>> resultHandler) {
		String fromHandlerID = msg.getString("fromHandlerID");
		String toHandlerID = msg.getString("toHandlerID");
		JsonObject body = msg.getJsonObject("body");
		String from = body.getString("fromTel");// 接入层校验
		String to = body.getString("toTel");// 接入层校验
		String content = body.getString("content", null);

		long ts = System.currentTimeMillis();

		JsonObject mongoMsg = new JsonObject();
		mongoMsg.put("collection", MONGO_COLLECTION);
		body.put("timeStamp", ts);
		mongoMsg.put("data", body);

		if (from != null && to != null) {
			Future<JsonObject> saveF = Future.future();
			mongoService.saveData(mongoMsg, saveF.completer());

			saveF.setHandler(res -> {
				if (res.succeeded()) {
					// 给FROM发A
					JsonObject aMsgBody = new JsonObject();
					aMsgBody.put("timeStamp", ts);
					aMsgBody.put("msgId", msg.getInteger("seq"));
					Buffer aMsgHeader = MessageBuilder.buildMsgHeader(IMMessageConstant.HEADER_LENGTH,
							msg.getInteger("clientVersion"), IMCmdConstants.MSG_R + 100, aMsgBody.toString().length());
					eb.send(fromHandlerID, aMsgHeader.appendString(aMsgBody.toString()).appendString("\001"));

					// 给TO发N {ts: 时间戳}
					// TODO 消息格式有点问题
					JsonObject nMsgBody = new JsonObject();
					nMsgBody.put("fromTel", from).put("content", content).put("timeStamp", System.currentTimeMillis())
							.put("msgId", msg.getInteger("seq"));
					Buffer nMsgHeader = MessageBuilder.buildMsgHeader(IMMessageConstant.HEADER_LENGTH,
							msg.getInteger("clientVersion"), IMCmdConstants.MSG_N, nMsgBody.toString().length());

					eb.send(toHandlerID, nMsgHeader.appendString(nMsgBody.toString()).appendString("\001"));
				} else {

				}
			});
		}
	}

	public void doWithAckRequest(JsonObject msg, Handler<AsyncResult<JsonObject>> resultHandler) {
		String fromHandlerID = msg.getString("fromHandlerID");
		String toHandlerID = msg.getString("toHandlerID");
		JsonObject mongoMsg = new JsonObject();
		mongoMsg.put("collection", MONGO_COLLECTION);
		mongoMsg.put("data", msg);

		JsonObject body = msg.getJsonObject("body");
		Long from = body.getLong("from");
		Long to = body.getLong("to");

		if (from != null && to != null) {
			Future<JsonObject> saveF = Future.future();
			// mongoService.saveData(mongoMsg, saveF.completer());

			saveF.setHandler(res -> {
				if (res.succeeded()) {

					// ack 给FROM发 A
					JsonObject aMsgBody = new JsonObject();
					aMsgBody.put("ts", System.currentTimeMillis());
					aMsgBody.put("id", msg.getInteger("seq"));
					Buffer aMsgHeader = MessageBuilder.buildMsgHeader(IMMessageConstant.HEADER_LENGTH,
							msg.getInteger("clientVersion"), IMCmdConstants.ACK_A, aMsgBody.toString().length());
					eb.send(fromHandlerID, aMsgHeader.appendString(aMsgBody.toString()).appendString("\001"));

					// ack 给TO发 N {ts: 时间戳}
					// String toHandlerId = sessionMap.get(to);
					JsonObject nMsgBody = new JsonObject();
					nMsgBody.put("from", from).put("content", body).put("ts", System.currentTimeMillis()).put("id",
							msg.getInteger("seq"));
					Buffer nMsgHeader = MessageBuilder.buildMsgHeader(IMMessageConstant.HEADER_LENGTH,
							msg.getInteger("clientVersion"), IMCmdConstants.ACK_N, nMsgBody.toString().length());

					eb.send(toHandlerID, nMsgHeader.appendString(nMsgBody.toString()).appendString("\001"));

					resultHandler.handle(Future.succeededFuture());
				} else {
					resultHandler.handle(Future.failedFuture(res.cause()));
				}
			});
		}
	}

	@Override
	public void doWithFileUpload(JsonObject msg, Handler<AsyncResult<JsonObject>> resultHandler) {
		//  gei to fa msg, xiao xi ru ku
	}
}
