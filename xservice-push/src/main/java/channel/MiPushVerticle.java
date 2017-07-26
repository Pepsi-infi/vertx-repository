package channel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Sender;
import constant.PushConsts;
import enums.EnumPassengerMessageType;
import enums.PushTypeEnum;
import io.netty.util.internal.StringUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import service.XiaoMiPushService;
import util.PropertiesLoaderUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author yanglf
 * 
 *         小米推送
 * 
 */
public class MiPushVerticle extends AbstractVerticle implements XiaoMiPushService{

	private static final Logger logger = LoggerFactory.getLogger(MiPushVerticle.class);

	private EventBus eventBus;

	private JsonObject recieveMsg;

	@Override
	public void start() throws Exception {

		eventBus = vertx.eventBus();

		MessageConsumer<String> message = eventBus
				.consumer(PushConsts.AD_PASSENGER_MSG_PREFIX + PushTypeEnum.XIAOMI.getCode());

		message.handler(handler -> {

			String msg = handler.body();
			if (StringUtil.isNullOrEmpty(msg)) {
				logger.error("小米推送接受数据为空");
			}

			recieveMsg = JsonObject.mapFrom(msg);

		});

		sendMsg(recieveMsg);

	}

	public void sendMsg(JsonObject recieveMsg) {

		String title = EnumPassengerMessageType.ADVERTISEMENT.getName();
		String content = recieveMsg.toString();

		String description = "首汽约车";
		String regId = null;
		try {
			regId = (String) recieveMsg.getValue("regId");
			sendMessage(title, content, description, null, regId);
		} catch (Exception e) {
			logger.error("sendMsg error:regId=" + regId + ",mapName=" + title + ",map=" + recieveMsg, e);
		}
	}

	public void sendMessage(String title, String content, String description, String msgBody, String regId)
			throws Exception {
		Sender sender = new Sender(PropertiesLoaderUtils.get("xiaomi.appsecret"));
		Message message = buildMessage(title, content, description, msgBody);
		sender.send(message, regId, 0); // 根据regID，发送消息到指定设备上，不重试。

	}

	private Message buildMessage(String title, String content, String description, String msgBody) throws Exception {
		// app包名
		String packageName = PropertiesLoaderUtils.get("xiaomi.packagename");

		Message message = new Message.Builder().title(title).description(description).payload(content)
				.restrictedPackageName(packageName).passThrough(PushConsts.XIAOMI_PASS_THROUGH_TONGZHILAN) // 设置消息是否通过透传的方式送给app，1表示透传消息，0表示通知栏消息。
				.notifyType(PushConsts.XIAOMI_NOTIFY_TYPE_DEFAULT_SOUND) // 使用默认提示音提示
				.extra("body", msgBody).build();
		return message;
	}

	public void sendMessageRegIds(String title, String content, String description, String msgBody, List<String> regIds)
			throws Exception {
		Sender sender = new Sender(PropertiesLoaderUtils.get("xiaomi.appsecret"));
		Message message = this.buildMessage(title, content, description, msgBody);
		sender.send(message, regIds, 0);
	}

	public void sendMessageAlias(String title, String content, String description, String msgBody,
			List<String> aliasList) throws Exception {
		Sender sender = new Sender(PropertiesLoaderUtils.get("xiaomi.appsecret"));
		Message message = this.buildMessage(title, content, description, msgBody);
		sender.sendToAlias(message, aliasList, 0); // 根据aliasList，发送消息到指定设备上，不重试。
	}

	public static void main(String[] args) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "infi");
		map.put("age", 28);
		System.out.println(mapper.writeValueAsString(map));

		JsonObject json = new JsonObject();
		json.put("name", "infi");
		json.put("age", 28);
		System.out.println(json.toString());

	}

}
