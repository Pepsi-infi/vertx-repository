package channel;

import com.xiaomi.push.sdk.ErrorCode;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;

import constant.PushConsts;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.serviceproxy.ProxyHelper;
import service.XiaoMiPushService;
import util.MsgUtil;
import utils.BaseResponse;

/**
 * @author yanglf
 *         <p>
 *         小米推送
 */
public class MiPushVerticle extends AbstractVerticle implements XiaoMiPushService {

	private static final Logger logger = LoggerFactory.getLogger(MiPushVerticle.class);

	private JsonObject config;

	@Override
	public void start() throws Exception {
		super.start();
		ProxyHelper.registerService(XiaoMiPushService.class, vertx, this, XiaoMiPushService.class.getName());

		config = config().getJsonObject("push.config");
	}

	@Override
	public void sendMsg(JsonObject receiveMsg, Handler<AsyncResult<BaseResponse>> resultHandler) {
		// 测试专用，防止测试推错推到线上
		// receiveMsg = testSendControl(receiveMsg);

		logger.info("进入小米推送Verticle");

		if (receiveMsg == null) {
			logger.error("进入小米推送消息内容为空");
			return;
		}

		try {
			Result result = sendMessage(receiveMsg);

			if (result == null) {
				resultHandler.handle(Future.failedFuture("xiaomi push result is null"));
				return;
			}

			if (ErrorCode.Success == result.getErrorCode()) {
				resultHandler.handle(Future.succeededFuture(new BaseResponse()));
			} else {
				resultHandler.handle(Future.failedFuture(result.getReason()));
			}

		} catch (Exception e) {
			resultHandler.handle(Future.failedFuture(e));
			logger.error("recieveMsg=" + receiveMsg, e);
		}
	}

	public Result sendMessage(JsonObject recieveMsg) throws Exception {

		String regId = (String) recieveMsg.getValue("regId");
		Sender sender = new Sender(config.getString("xiaomi.appsecret"));

		Message message = buildMessage(recieveMsg);

		logger.info("xiaomi send before :" + Json.encode(message));
		Result sendResult = sender.send(message, regId, 0); // 根据regID，发送消息到指定设备上，不重试。

		logger.info("regId=" + regId + ", 小米推送返回结果：" + Json.encode(sendResult));
		return sendResult;
	}

	private Message buildMessage(JsonObject recieveMsg) throws Exception {
		// app包名
		String packageName = config.getString("xiaomi.packagename");
		String title = recieveMsg.getString("title");
		String wholeMsg = recieveMsg.toString();
		String msgId = recieveMsg.getValue("msgId") + "";
		Integer jumpPage = recieveMsg.getInteger("jumpPage");
		String content = recieveMsg.getString("content");
		Integer isIntoPsnCenter = recieveMsg.getInteger("isIntoPsnCenter");
		Integer openType = recieveMsg.getInteger("type");
		String url = recieveMsg.getString("url");
		String psnCenterImgUrl = recieveMsg.getString("psnCenterImgUrl");

		String action;
		if (PushConsts.PUSH_OPEN_TYPE_APP == openType) {
			action = MsgUtil.getEnumByCode(jumpPage);
		} else if (PushConsts.PUSH_OPEN_TYPE_HTML == openType) {
			action = url;
		} else {
			logger.error("type is error,set action");
			action = MsgUtil.getEnumByCode(jumpPage);
		}

		Message message = new Message.Builder().title(title).description(content).payload(wholeMsg)
				.extra("messageId", msgId).extra("action", action).extra("title", title).extra("content", content)
				.extra("isIntoPsnCenter", isIntoPsnCenter + "").extra("psnCenterImgUrl", psnCenterImgUrl)
				.restrictedPackageName(packageName).passThrough(PushConsts.XIAOMI_PASS_THROUGH_TOUCHUAN) // 设置消息是否通过透传的方式送给app，1表示透传消息，0表示通知栏消息。
				.notifyType(PushConsts.XIAOMI_NOTIFY_TYPE_DEFAULT_SOUND) // 使用默认提示音提示
				.build();
		return message;
	}

	// 测试专用，防止消息推送到线上用户
	private JsonObject testSendControl(JsonObject jsonMsg) {
		if ("dev".equals(PushConsts.ENV_PATH)) {
			String phone = config.getString("xiaomi.test.phone");
			String token = config.getString("xiaomi.test.token");
			if (jsonMsg != null) {
				jsonMsg.put("phone", phone);
				jsonMsg.put("regId", token);
			}
		}
		return jsonMsg;
	}

	public static void main(String[] args) throws Exception {

		MiPushVerticle verticle = new MiPushVerticle();

		JsonObject recieveMsg = new JsonObject();
		recieveMsg.put("msgId", "1231313132");
		recieveMsg.put("regId", "FpXBF9sOub875uWnPRyasdXnec/turJwqI8fsJmgblk=");
		recieveMsg.put("title", "发券啦");
		recieveMsg.put("content", "送您一张10元优惠券");
		recieveMsg.put("jumpPage", 4);
		recieveMsg.put("isIntoPsnCenter", 0);
		System.out.println(verticle.sendMessage(recieveMsg));
	}

}
