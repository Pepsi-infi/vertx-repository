package channel;

import java.io.InputStream;
import java.util.List;

import com.dbay.apns4j.IApnsService;
import com.dbay.apns4j.impl.ApnsServiceImpl;
import com.dbay.apns4j.model.ApnsConfig;
import com.dbay.apns4j.model.Feedback;
import com.dbay.apns4j.model.Payload;

import constant.PushConsts;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.serviceproxy.ProxyHelper;
import service.ApplePushService;
import utils.BaseResponse;
import xservice.BaseServiceVerticle;

public class ApplePushVerticle extends BaseServiceVerticle implements ApplePushService {

	private static final Logger logger = LoggerFactory.getLogger(ApplePushVerticle.class);

	private JsonObject config;

	private IApnsService apnsService;

	@Override
	public void start() throws Exception {
		super.start();
		ProxyHelper.registerService(ApplePushService.class, vertx, this, ApplePushService.class.getName());

		config = config().getJsonObject("push.config");

		// apns配置初始化
		initApnsConfig();
	}

	private void initApnsConfig() {

		String keyStorePwd = config.getString("push.apns.keyStorePwd");
		String keyStorePath = config.getString("push.apns.keyStorePath");

		boolean devEnv = false;

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(keyStorePath);

		if ("dev".equals(PushConsts.ENV_PATH)) {
			devEnv = true;
		}

		if ("test".equals(PushConsts.ENV_PATH)) {
			devEnv = true;
		}

		if ("prod".equals(PushConsts.ENV_PATH)) {
			devEnv = false;
		}

		if (is == null) {
			logger.error("apns初始化失败 inputstream is null");
			return;
		}

		if (apnsService == null) {
			try {
				ApnsConfig config = new ApnsConfig();
				// is = new FileInputStream(new File(keyStorePath));
				config.setKeyStore(is);
				config.setDevEnv(devEnv);
				config.setPassword(keyStorePwd);
				config.setPoolSize(3);
				apnsService = ApnsServiceImpl.createInstance(config);
			} catch (Exception e) {
				logger.error("apns推送初始化失败", e);
			}
		}

		logger.info("apns初始化成功");

	}

	@Override
	public void sendMsg(JsonObject receiveMsg, Handler<AsyncResult<BaseResponse>> resultHandler) {

		logger.info("enter applePushService sendMsg method");

		String deviceToken = receiveMsg.getString("apnsToken");
		String title = receiveMsg.getString("title");
		String body = receiveMsg.getString("content");
		String msgbody = receiveMsg.toString();

		// apns推送逻辑
		vertx.executeBlocking(future -> {
			try {
				logger.info("apns send data:" + receiveMsg);
				boolean sendResult = sendApnsByDbay(deviceToken, title, body, msgbody);
				future.complete(sendResult);
			} catch (Exception e) {
				logger.error("APNS推送调用异常", e);
				resultHandler.handle(Future.failedFuture(e.getMessage()));
			}

		}, res -> {

			if (res.succeeded()) {

				boolean sendResult = (boolean) res.result();

				if (!sendResult) {
					resultHandler.handle(Future.failedFuture("APNS PUSH FAILED"));
					return;
				}

				resultHandler.handle(Future.succeededFuture(new BaseResponse()));

			} else {
				logger.error("APNS推送调用异常", res.cause());
				resultHandler.handle(Future.failedFuture(res.cause()));
			}

		});

	}

	private boolean sendApnsByDbay(String deviceToken, String title, String body, String msgbody) {

		try {
			Payload payload = new Payload();

			payload.setAlertBody(body);
			payload.setAlertTitle(title);
			payload.setSound("default");
			payload.addParam("msgbody", msgbody);

			apnsService.sendNotification(deviceToken, payload);

			List<Feedback> list = apnsService.getFeedbacks();
			if (list != null && list.size() > 0) {
				for (Feedback feedback : list) {
					logger.info("[apns2发送消息]循环Feedback：" + feedback.getDate() + " ： " + feedback.getToken());
				}
			}
		} catch (Exception e) {
			logger.error("apns消息推送异常", e);
			return false;
		}
		logger.info("apns消息推送成功");
		return true;
	}

	// 测试专用，防止消息推送到线上用户
	private JsonObject testSendControl(JsonObject jsonMsg) {
		if ("dev".equals(PushConsts.ENV_PATH)) {
			String apnsToken = config.getString("apple.test.apnsToken");
			if (jsonMsg != null) {
				jsonMsg.put("apnsToken", apnsToken);
			}
		}
		return jsonMsg;
	}

}
