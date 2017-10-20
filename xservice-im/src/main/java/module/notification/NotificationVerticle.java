package module.notification;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import constants.IMCmd;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import module.c2c.protocol.MessageBuilder;
import module.c2c.protocol.SQIMBody;
import utils.IPUtil;

public class NotificationVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(NotificationVerticle.class);

	private EventBus eb;

	private String innerIP;

	@Override
	public void start() throws Exception {
		super.start();

		innerIP = IPUtil.getInnerIP();

		eb = vertx.eventBus();
		eb.consumer(NotificationVerticle.class.getName() + innerIP, res -> {
			MultiMap headers = res.headers();
			String action = headers.get("action");

			switch (action) {
			case "sendMessage":

				break;
			default:
				res.reply(1);// Fail!
				break;
			}
		});
	}

	private void sendTextNotification(String handlerID, int clientVersion, int cmd) {
		SQIMBody noti = new SQIMBody();
		noti.setMsgId(UUID.randomUUID().toString());
		noti.setContent("这是系统通知！这是系统通知！这是系统通知！这是系统通知！这是系统通知！这是系统通知！这是系统通知！这是系统通知！");
		noti.setMsgType(1);

		String body = Json.encode(noti);
		int bodyLength = 0;
		try {
			bodyLength = Json.encode(noti).getBytes("UTF-8").length;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("sendTextNotification, msg={}", noti.toString());

		Buffer aMsgHeader = MessageBuilder.buildMsgHeader(MessageBuilder.HEADER_LENGTH, clientVersion,
				IMCmd.Notification, bodyLength);

		eb.send(handlerID, aMsgHeader.appendString(body));

	}

	private void sendAd(String handlerID, int clientVersion, int cmd) {
		SQIMBody noti = new SQIMBody();
		noti.setMsgId(UUID.randomUUID().toString());
		noti.setContent("http://img3.redocn.com/tupian/20150430/mantenghuawenmodianshiliangbeijing_3924704.jpg");
		noti.setJump("https://www.baidu.com/");
		noti.setMsgType(4);

		String body = Json.encode(noti);
		int bodyLength = 0;
		try {
			bodyLength = Json.encode(noti).getBytes("UTF-8").length;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("sendAd, msg={}", noti.toString());

		Buffer aMsgHeader = MessageBuilder.buildMsgHeader(MessageBuilder.HEADER_LENGTH, clientVersion, IMCmd.adWithPic,
				bodyLength);

		eb.send(handlerID, aMsgHeader.appendString(body));
	}

}
