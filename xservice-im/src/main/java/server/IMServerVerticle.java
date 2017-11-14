package server;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import constants.IMCmd;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.NetSocket;
import io.vertx.core.parsetools.RecordParser;
import io.vertx.rxjava.core.Future;
import module.c2c.C2CVerticle;
import module.c2c.protocol.MessageBuilder;
import module.c2c.protocol.SQIMBody;
import module.hash.IMConsistentHashingVerticle;
import module.persistence.MongoVerticle;
import module.session.IMSessionVerticle;
import utils.ByteUtil;
import utils.IPUtil;
import xservice.BaseServiceVerticle;

public class IMServerVerticle extends BaseServiceVerticle {

	private static final Logger logger = LoggerFactory.getLogger(IMServerVerticle.class);

	private EventBus eb;

	private String innerIP;
	private Map<String, String> ipMap = new HashMap<String, String>();

	// TCP连接保活时间1个小时
	private static final int KEEP_ALIVE_TIME_SECONDS = 3600;

	@Override
	public void start() throws Exception {
		super.start();

		JsonArray socketNodes = config().getJsonArray("im");
		for (Object object : socketNodes) {
			JsonObject node = JsonObject.mapFrom(object);
			ipMap.put(node.getString("innerIP"), node.getString("node"));
		}
		innerIP = IPUtil.getInnerIP();
		publishIMService(IMServerVerticle.class.getName(), innerIP,
				new JsonObject().put("publicAddress", ipMap.get(innerIP)).put("innerIP", innerIP));

		logger.info("start ... ");
		eb = vertx.eventBus();

		NetServerOptions options = new NetServerOptions().setPort(config().getInteger("im.tcp.port"))
				.setIdleTimeout(KEEP_ALIVE_TIME_SECONDS);
		// options.setSsl(true).setPemKeyCertOptions(
		// new
		// PemKeyCertOptions().setKeyPath("server-key2.pem").setCertPath("server-cert.pem"));
		NetServer server = vertx.createNetServer(options);

		server.connectHandler(new Handler<NetSocket>() {

			@Override
			public void handle(NetSocket event) {
				String handlerID = event.writeHandlerID();

				final RecordParser parser = RecordParser.newFixed(MessageBuilder.HEADER_LENGTH, null);
				parser.setOutput(new Handler<Buffer>() {
					private int bodyLength = -1;
					private int clientVersion = -1;
					private int cmd = -1;

					@Override
					public void handle(Buffer buffer) {
						if (bodyLength == -1) {
							int headerLength = ByteUtil.byte2ToUnsignedShort(buffer.getBytes(0, 2));
							clientVersion = ByteUtil.byte2ToUnsignedShort(buffer.getBytes(2, 4));
							cmd = ByteUtil.bytesToInt(buffer.getBytes(4, 8));
							bodyLength = ByteUtil.bytesToInt(buffer.getBytes(8, 12));
							logger.info("imMessage header, headerLength={}clientVersion={}cmd={}bodyLength={}",
									headerLength, clientVersion, cmd, bodyLength);

							if (bodyLength != 0) {
								parser.fixedSizeMode(bodyLength);
							} else {
								parser.fixedSizeMode(MessageBuilder.HEADER_LENGTH);
								bodyLength = -1;

								heartBeat(handlerID, clientVersion, cmd);
							}
						} else {
							SQIMBody imMessage = Json.decodeValue(buffer, SQIMBody.class);
							logger.info("cmd={},imMessage body={}", cmd,imMessage.toString());
							switch (cmd) {
							case IMCmd.HEART_BEAT:
								heartBeat(handlerID, clientVersion, cmd);

								break;
							case IMCmd.LOGIN:
								if (imMessage != null && StringUtils.isNotEmpty(imMessage.getUserTel())) {
									login(handlerID, clientVersion, cmd, bodyLength, imMessage);
								}

								break;
							case IMCmd.LOGOUT:
								if (imMessage != null && StringUtils.isNotEmpty(imMessage.getUserTel())) {
									logout(handlerID, clientVersion, cmd, bodyLength, imMessage);
								}

								break;
							case IMCmd.MSG_R:
								if (imMessage != null && StringUtils.isNotEmpty(imMessage.getFromTel())
										&& StringUtils.isNotEmpty(imMessage.getToTel())) {
									msgRequest(handlerID, clientVersion, cmd, bodyLength, imMessage);
								}

								break;
							case IMCmd.ACK_N:
								if (imMessage != null) {
									ackNCmd(handlerID, clientVersion, cmd, imMessage);
								}

								break;
							case IMCmd.POSITION_SHARE_START:
								if (imMessage != null) {
									msgRequest(handlerID, clientVersion, cmd, bodyLength, imMessage);
								}
								break;
							case IMCmd.POSITION_SHARE_END:
								if (imMessage != null) {
									msgRequest(handlerID, clientVersion, cmd, bodyLength, imMessage);
								}
								break;
							case IMCmd.POSITION_SHARE_ING:
								if (imMessage != null) {
									msgRequest(handlerID, clientVersion, cmd, bodyLength, imMessage);
								}
								break;
							default:
								break;
							}

							// reset
							clientVersion = -1;
							cmd = -1;
							bodyLength = -1;
							parser.fixedSizeMode(MessageBuilder.HEADER_LENGTH);
						}

					}

				});
				event.handler(parser);
				event.closeHandler(v -> {
					logger.info("socket 连接关闭");
					socketClose(handlerID);
					event.close();
				});
				event.exceptionHandler(t -> {
					logger.info("socket 连接异常="+t.getMessage());
					socketClose(handlerID);
					event.close();
				});
			}
		});

		server.listen();
	}

	private void ackNCmd(String handlerID, int clientVersion, int cmd, SQIMBody imMessage) {
		String msgId = imMessage.getMsgId();

		JsonObject data = new JsonObject().put("msgId", msgId).put("cmdId", IMCmd.MSG_A);
		JsonObject update = new JsonObject().put("collection", "message").put("data", data);

		DeliveryOptions mongoOp = new DeliveryOptions();
		mongoOp.addHeader("action", MongoVerticle.method.saveData);
		mongoOp.setSendTimeout(3000);

		eb.<JsonObject>send(MongoVerticle.class.getName(), update, mongoOp, mongoRes -> {
			if (mongoRes.succeeded()) {
				// eb.send(handlerID, mongoRes.result());
			} else {
				logger.error(mongoRes.cause().getMessage());
			}
		});
	}

	private void heartBeat(String writeHandlerID, int clientVersion, int cmd) {
		Buffer aMsgHeader = MessageBuilder.buildMsgHeader(MessageBuilder.HEADER_LENGTH, clientVersion,
				cmd + MessageBuilder.MSG_ACK_CMD_RADIX, 0);

		// 1、心跳消息确认
		eb.send(writeHandlerID, aMsgHeader);
	}

	private void login(String handlerID, int clientVersion, int cmd, int bodyLength, SQIMBody imMessage) {
		String from = imMessage.getUserTel();

		DeliveryOptions option = new DeliveryOptions();
		option.addHeader("action", IMSessionVerticle.method.setUserSocket);
		option.setSendTimeout(3000);
		JsonObject msg = new JsonObject().put("handlerID", handlerID).put("from", from);
		eb.send(IMSessionVerticle.class.getName() + innerIP, msg, option);

		// 1、给FROM发A
		Buffer aMsgHeader = MessageBuilder.buildMsgHeader(MessageBuilder.HEADER_LENGTH, clientVersion,
				cmd + MessageBuilder.MSG_ACK_CMD_RADIX, 0);
		eb.send(handlerID, aMsgHeader);

		// sendTextNotification(handlerID, clientVersion, cmd);
		// sendAd(handlerID, clientVersion, cmd);
	}

	private void logout(String handlerID, int clientVersion, int cmd, int bodyLength, SQIMBody imMessage) {
		String from = imMessage.getUserTel();
		if (StringUtils.isNotEmpty(from)) {
			DeliveryOptions option = new DeliveryOptions();
			option.addHeader("action", "delUserSocket");
			option.setSendTimeout(3000);
			JsonObject msg = new JsonObject().put("handlerID", handlerID).put("from", from);
			eb.send(IMSessionVerticle.class.getName() + innerIP, msg, option);

			// 给FROM发A
			Buffer aMsgHeader = MessageBuilder.buildMsgHeader(MessageBuilder.HEADER_LENGTH,
					msg.getInteger("clientVersion"), cmd + MessageBuilder.MSG_ACK_CMD_RADIX, 0);
			eb.send(handlerID, aMsgHeader);
		}
	}

	private void msgRequest(String handlerID, int clientVersion, int cmd, int bodyLength, SQIMBody imMessage) {
		JsonObject param = new JsonObject();

		JsonObject header = new JsonObject();
		header.put("clientVersion", clientVersion);
		header.put("cmdId", cmd);
		header.put("fromHandlerID", handlerID);

		param.put("header", header);
		param.put("body", JsonObject.mapFrom(imMessage));

		DeliveryOptions option = new DeliveryOptions();
		option.addHeader("action", C2CVerticle.method.sendMessage);
		option.setSendTimeout(3000);

		//
		DeliveryOptions chOption = new DeliveryOptions();
		chOption.setSendTimeout(3000);
		chOption.addHeader("action", "getInnerNode");

		Future<Message<JsonObject>> chFuture = Future.future();

		JsonObject message = new JsonObject();
		message.put("userId", imMessage.getToTel());
		eb.<JsonObject>send(IMConsistentHashingVerticle.class.getName(), message, chOption, chFuture.completer());

		chFuture.setHandler(a -> {
			if (a.succeeded()) {
				String innerHost = a.result().body().getString("host");
				eb.send(C2CVerticle.class.getName() + innerHost, param, option);
			}
		});
		//

		SQIMBody ackMsg = new SQIMBody();
		ackMsg.setMsgId(imMessage.getMsgId());
		ackMsg.setTimeStamp(System.currentTimeMillis());

		int ackMsgBodyLength = 0;
		String ackMsgStr = Json.encode(ackMsg);
		try {
			ackMsgBodyLength = ackMsgStr.getBytes("UTF-8").length;
		} catch (Exception e) {
			logger.error(e);
		}
		// 给FROM发A
		Buffer aMsgHeader = MessageBuilder.buildMsgHeader(MessageBuilder.HEADER_LENGTH, clientVersion,
				cmd + MessageBuilder.MSG_ACK_CMD_RADIX, ackMsgBodyLength);

		logger.info("ACK, cmd={}ackMsgStr={}", cmd, ackMsgStr);
		eb.send(handlerID, aMsgHeader.appendString(ackMsgStr));
	}

	private void socketClose(String handlerID) {
		logger.info("socket.close handlerID={}", handlerID);

		DeliveryOptions option = new DeliveryOptions();
		option.addHeader("action", IMSessionVerticle.method.delUserSocket);
		option.setSendTimeout(3000);
		JsonObject msg = new JsonObject().put("handlerID", handlerID);
		eb.publish(IMSessionVerticle.class.getName(), msg, option);
	}

	private void sendTextNotification(String handlerID, int clientVersion, int cmd) {
		SQIMBody noti = new SQIMBody();
		noti.setMsgId(UUID.randomUUID().toString());
		noti.setContent("这是系统通知！这是系统通知！这是系统通知！这是系统通知！这是系统通知！这是系统通知！这是系统通知！这是系统通知！");
		noti.setMsgType(1);
		noti.setSceneType(0);
		noti.setSceneId("A123456789");

		String body = Json.encode(noti);
		int bodyLength = 0;
		try {
			bodyLength = Json.encode(noti).getBytes("UTF-8").length;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Buffer aMsgHeader = MessageBuilder.buildMsgHeader(MessageBuilder.HEADER_LENGTH, clientVersion,
				IMCmd.Notification, bodyLength);

		eb.send(handlerID, aMsgHeader.appendString(body));
	}

	private void sendAd(String handlerID, int clientVersion, int cmd) {
		SQIMBody noti = new SQIMBody();
		noti.setMsgId(UUID.randomUUID().toString());
		noti.setContent("http://img3.redocn.com/tupian/20150430/mantenghuawenmodianshiliangbeijing_3924704.jpg");
		noti.setJumpUrl("https://www.baidu.com/");
		noti.setMsgType(4);
		noti.setSceneType(0);
		noti.setSceneId("A123456789");
		noti.setWidth(480);
		noti.setHeight(720);

		String body = Json.encode(noti);
		int bodyLength = 0;
		try {
			bodyLength = Json.encode(noti).getBytes("UTF-8").length;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Buffer aMsgHeader = MessageBuilder.buildMsgHeader(MessageBuilder.HEADER_LENGTH, clientVersion,
				IMCmd.Notification, bodyLength);

		eb.send(handlerID, aMsgHeader.appendString(body));
	}
}
