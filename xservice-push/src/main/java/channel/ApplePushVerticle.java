package channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import com.dbay.apns4j.IApnsService;
import com.dbay.apns4j.impl.ApnsServiceImpl;
import com.dbay.apns4j.model.ApnsConfig;
import com.dbay.apns4j.model.Feedback;
import com.dbay.apns4j.model.Payload;

import cn.teaey.apns4j.Apns4j;
import cn.teaey.apns4j.network.ApnsChannelFactory;
import cn.teaey.apns4j.network.ApnsNettyChannel;
import cn.teaey.apns4j.network.async.ApnsFuture;
import cn.teaey.apns4j.protocol.ApnsPayload;
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

	private ApnsChannelFactory apnsChannelFactory;

	private ApnsNettyChannel apnsChannel;

	// private ApnsService apnsService;

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

		if ("dev".equals(PushConsts.ENV_PATH)) {
			devEnv = true;
		}

		if ("prod".equals(PushConsts.ENV_PATH)) {
			devEnv = false;
		}

		if (apnsService == null) {
			try {
				ApnsConfig config = new ApnsConfig();
				InputStream is = new FileInputStream(new File(keyStorePath));
				config.setKeyStore(is);
				config.setDevEnv(devEnv);
				config.setPassword(keyStorePwd);
				config.setPoolSize(3);
				apnsService = ApnsServiceImpl.createInstance(config);
			} catch (Exception e) {
				logger.error("apns推送初始化失败", e);
			}
		}

		// String keyStorePwd = config.getString("push.apns.keyStorePwd");
		// String keyStorePath = config.getString("push.apns.keyStorePath");
		// logger.info("keyStorePath="+keyStorePath);
		//
		// ApnsGateway gateway = null;
		// if ("dev".equals(PushConsts.ENV_PATH)) {
		// gateway = ApnsGateway.DEVELOPMENT;
		// }
		//
		// if ("prod".equals(PushConsts.ENV_PATH)) {
		// gateway = ApnsGateway.PRODUCTION;
		// }
		//
		// if (gateway == null) {
		// logger.error("apns service init error:ApnsGateway is null");
		// return;
		// }
		//
		// try {
		// apnsChannelFactory =
		// Apns4j.newChannelFactoryBuilder().keyStoreMeta(keyStorePath).keyStorePwd(keyStorePwd)
		// .apnsGateway(gateway).build();
		// apnsChannel = apnsChannelFactory.newChannel(keyStorePath,
		// keyStorePwd);
		// apnsService = new ApnsService(3, apnsChannelFactory, 3, keyStorePath,
		// keyStorePwd);
		// } catch (Exception e) {
		// logger.error("apns config init fail", e);
		// }
		// logger.info("apns config init success");

	}

	@Override
	public void sendMsg(JsonObject receiveMsg, Handler<AsyncResult<BaseResponse>> resultHandler) {
		// 测试专用，防止测试推错推到线上
		// receiveMsg = testSendControl(receiveMsg);

		logger.info("enter applePushService sendMsg method");

		String deviceToken = receiveMsg.getString("apnsToken");
		String title = receiveMsg.getString("title");
		String body = receiveMsg.getString("content");
		String msgbody = receiveMsg.toString();

		// Map<String, String> addQueryParam = new HashMap<>();
		// addQueryParam.put("deviceToken", deviceToken);
		// addQueryParam.put("title", title);
		// addQueryParam.put("body", body);
		// addQueryParam.put("msgbody", msgbody);

		// apns推送逻辑
		// sendApns(deviceToken, title, body, msgbody, resultHandler);
		sendApnsByDbay(deviceToken, title, body, msgbody, resultHandler);

		// String appleUrl =
		// config().getJsonObject("push.config").getString("apple.push.url");
		//
		// if (StringUtil.isNullOrEmpty(appleUrl)) {
		// resultHandler.handle(Future.failedFuture("Apple push host is null"));
		// return;
		// }
		//
		// WebClient webClient = WebClient.create(vertx);
		//
		// webClient.postAbs(appleUrl).putHeader("Content-Type",
		// "application/x-www-form-urlencoded;charset=utf-8")
		// .addQueryParam("deviceToken", deviceToken).addQueryParam("title",
		// receiveMsg.getString("title"))
		// .addQueryParam("body",
		// receiveMsg.getString("content")).addQueryParam("msgbody",
		// receiveMsg.toString())
		// .send(responseHandler -> {
		//
		// if (responseHandler.succeeded()) {
		// String result = responseHandler.result().bodyAsString();
		// logger.info("apns推送返回结果deviceToken=" + deviceToken + ", result=" +
		// result);
		//
		// if (StringUtil.isNullOrEmpty(result)) {
		// logger.error("Apple push result is null deviceToken=" + deviceToken);
		// resultHandler.handle(Future.failedFuture("Apple push result is
		// null"));
		// } else {
		// resultHandler.handle(Future.succeededFuture(new BaseResponse()));
		// }
		// } else {
		// logger.error("Apple push error deviceToken=" + deviceToken,
		// responseHandler.cause());
		// resultHandler.handle(Future.failedFuture("Apple push error" +
		// responseHandler.cause()));
		// }
		//
		// });

	}

	private void sendApnsByDbay(String deviceToken, String title, String body, String msgbody,
			Handler<AsyncResult<BaseResponse>> resultHandler) {

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
			resultHandler.handle(Future.failedFuture(e));
			return;
		}
		logger.info("apns消息推送成功");
		resultHandler.handle(Future.succeededFuture(new BaseResponse()));
	}

	private void sendApns(String deviceToken, String title, String body, String msgbody,
			Handler<AsyncResult<BaseResponse>> resultHandler) {

		ApnsPayload apnsPayload = Apns4j.newPayload().alertTitle(title).alertBody(body).extend("msgbody", msgbody)
				.sound("default");

		logger.info("apns send data,deviceToken:" + deviceToken + ",apnsPayload:" + apnsPayload);
		ApnsFuture result = apnsChannel.send(deviceToken, apnsPayload);

		if (result == null) {
			logger.error("apns push error:result is null");
			resultHandler.handle(Future.failedFuture("apns result is null"));
			return;
		}

		logger.info("apns push result:" + result);
		resultHandler.handle(Future.succeededFuture(new BaseResponse()));

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
