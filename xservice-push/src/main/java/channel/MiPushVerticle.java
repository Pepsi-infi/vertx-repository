package channel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomi.push.sdk.ErrorCode;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;
import constant.PushConsts;
import enums.EnumPassengerMessageType;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.serviceproxy.ProxyHelper;
import service.XiaoMiPushService;
import util.JsonUtil;
import util.PropertiesLoaderUtils;
import utils.BaseResponse;

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
public class MiPushVerticle extends AbstractVerticle implements XiaoMiPushService {

	private static final Logger logger = LoggerFactory.getLogger(MiPushVerticle.class);

	@Override
	public void start() throws Exception {
		super.start();
		ProxyHelper.registerService(XiaoMiPushService.class, vertx, this,
				XiaoMiPushService.class.getName());

	}

	@Override
	public void sendMsg(JsonObject recieveMsg, Handler<AsyncResult<BaseResponse>> resultHandler) {

		logger.info("进入小米推送Verticle");
		
		if (recieveMsg == null) {
			logger.error("尚无消息");
			return;
		}

		String title = recieveMsg.getString("title");
		String content = recieveMsg.toString();
		String msgId=recieveMsg.getString("msgId");

		String description = recieveMsg.getString("content");
		String regId = null;
		try {
			regId = (String) recieveMsg.getValue("regId");
			
			Result result= sendMessage(title, content, description, regId,msgId);
			
			if(ErrorCode.Success == result.getErrorCode()){
				resultHandler.handle(Future.succeededFuture(new BaseResponse()));		
			}else{
				resultHandler.handle(Future.failedFuture(result.getReason()));
			}
			
		} catch (Exception e) {
			logger.error("sendMsg error:regId=" + regId + ", mapName=" + title + ", recieveMsg=" + recieveMsg, e);
		}
	}

	public Result sendMessage(String title, String content, String description, String regId, String msgId) throws Exception {
		Sender sender = new Sender(PropertiesLoaderUtils.singleProp.getProperty("xiaomi.appsecret"));
		Message message = buildMessage(title, content, description,msgId);
		Result sendResult = sender.send(message, regId, 0); // 根据regID，发送消息到指定设备上，不重试。

		logger.info("regId: " + regId + ", 小米推送返回结果：" + JsonUtil.toJsonString(sendResult));
		return sendResult;
	}

	private Message buildMessage(String title, String content, String description, String msgId) throws Exception {
		// app包名
		String packageName = PropertiesLoaderUtils.singleProp.getProperty("xiaomi.packagename");

		Message message = new Message.Builder().title(title).description(description).payload(content).extra("MESSAGE_ID",msgId)
				.restrictedPackageName(packageName).passThrough(PushConsts.XIAOMI_PASS_THROUGH_TONGZHILAN) // 设置消息是否通过透传的方式送给app，1表示透传消息，0表示通知栏消息。
				.notifyType(PushConsts.XIAOMI_NOTIFY_TYPE_DEFAULT_SOUND) // 使用默认提示音提示
				.build();
		return message;
	}

	public void sendMessageRegIds(String title, String content, String description, List<String> regIds)
			throws Exception {

		Sender sender = new Sender(PropertiesLoaderUtils.singleProp.getProperty("xiaomi.appsecret"));
		Message message = this.buildMessage(title, content, description,"");
		sender.send(message, regIds, 0);
	}

	public void sendMessageAlias(String title, String content, String description, List<String> aliasList)
			throws Exception {
		Sender sender = new Sender(PropertiesLoaderUtils.singleProp.getProperty("xiaomi.appsecret"));
		Message message = this.buildMessage(title, content, description,"");
		sender.sendToAlias(message, aliasList, 0); // 根据aliasList，发送消息到指定设备上，不重试。
	}

	public static void main(String[] args) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<>();
		map.put("name", "infi");
		map.put("age", 28);
		System.out.println(mapper.writeValueAsString(map));

		JsonObject json = new JsonObject();
		json.put("name", "infi");
		json.put("age", 28);
		System.out.println(json.toString());

	}

}
