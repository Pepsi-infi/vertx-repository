package server;

import java.io.UnsupportedEncodingException;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.Future;
import logic.impl.SocketSessionVerticle;
import util.ByteUtil;
import utils.IPUtil;

public class MessageSendVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(MessageSendVerticle.class);

	private EventBus eb;
	private String innerIP;

	@Override
	public void start() throws Exception {
		innerIP = IPUtil.getInnerIP();
		logger.info("innerIP={}", innerIP);

		eb = vertx.eventBus();
		eb.<JsonObject>consumer(MessageSendVerticle.class.getName() + innerIP, res -> {
			MultiMap headers = res.headers();
			JsonObject body = res.body();
			if (headers != null) {
				String action = headers.get("action");
				String userId = body.getString("userId");
				String msg = body.getString("msg");

				switch (action) {
				case "sendMsg":
					res.reply(sendMsg(userId, msg));
					break;
				default:
					res.reply(1);// Fail!
					break;
				}
			}
		});
	}

	private JsonObject sendMsg(String userId, String msg2Send) {
		DeliveryOptions SocketSessionOption = new DeliveryOptions();
		SocketSessionOption.setSendTimeout(3000);
		SocketSessionOption.addHeader("action", "getHandlerIDByUid");

		JsonObject param = new JsonObject();
		param.put("userId", userId);

		Future<Message<JsonObject>> ssFuture = Future.future();
		eb.<JsonObject>send(SocketSessionVerticle.class.getName() + innerIP, param, SocketSessionOption,
				ssFuture.completer());

		ssFuture.setHandler(res -> {
			if (res.succeeded()) {
				if (res.result().body() != null) {
					String handlerID = res.result().body().getString("handlerID");

					Buffer bf = null;
					try {
						bf = Buffer.buffer(ByteUtil.intToBytes(msg2Send.getBytes("UTF-8").length))
								.appendString(msg2Send);
						logger.info("sendMsg, userId={} innerIP={} handlerID={} header={} bf={}", userId, innerIP,
								handlerID, msg2Send.getBytes("UTF-8").length, msg2Send);
					} catch (UnsupportedEncodingException e) {
						logger.error("UnsupportedEncodingException, {}", e.getCause().getMessage());
					}
					eb.send(handlerID, bf);
				}
			} else {

			}
		});

		return new JsonObject();
	}
}
