package channel;

import java.util.HashMap;
import java.util.Map;

import constant.ConnectionConsts;
import io.netty.util.internal.StringUtil;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.client.WebClient;
import io.vertx.serviceproxy.ProxyHelper;
import service.ApplePushService;
import util.PropertiesLoaderUtils;
import utils.BaseResponse;
import xservice.BaseServiceVerticle;

public class ApplePushVerticle extends BaseServiceVerticle implements ApplePushService {

	private static final Logger logger = LoggerFactory.getLogger(ApplePushVerticle.class);

	@Override
	public void start() throws Exception {

		super.start();

		ProxyHelper.registerService(ApplePushService.class, vertx, this, ApplePushService.class.getName());

	}

	@Override
	public void sendMsg(JsonObject receiveMsg, Handler<AsyncResult<BaseResponse>> resultHandler) {
		// 测试专用，防止测试推错推到线上
		receiveMsg = testSendControl(receiveMsg);

		logger.info("enter applePushService sendMsg method");

		Map<String, String> addQueryParam = new HashMap<>();
		addQueryParam.put("deviceToken", receiveMsg.getString("apnsToken"));
		addQueryParam.put("title", receiveMsg.getString("title"));
		addQueryParam.put("body", receiveMsg.getString("content"));
		addQueryParam.put("msgbody", receiveMsg.toString());

		String appleUrl = PropertiesLoaderUtils.multiProp.getProperty("apple.push.url");

		if (StringUtil.isNullOrEmpty(appleUrl)) {
			resultHandler.handle(Future.failedFuture("Apple push host is null"));
			return;
		}

		WebClient webClient = WebClient.create(vertx);

		webClient.postAbs(appleUrl)
				.putHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
				.addQueryParam("deviceToken", receiveMsg.getString("apnsToken"))
				.addQueryParam("title", receiveMsg.getString("title"))
				.addQueryParam("body", receiveMsg.getString("content"))
				.addQueryParam("msgbody", receiveMsg.toString())
				.send(responseHandler -> {

					if (responseHandler.succeeded()) {
						String result = responseHandler.result().bodyAsString();
						logger.info("apns推送返回结果：" + result);

						if (StringUtil.isNullOrEmpty(result)) {
							resultHandler.handle(Future.failedFuture("Apple push result is null"));
						} else {
							resultHandler.handle(Future.succeededFuture(new BaseResponse()));
						}
					} else {
						resultHandler.handle(Future.failedFuture("Apple push error:" + responseHandler.cause()));
					}

				});

	}

	// 测试专用，防止消息推送到线上用户
	private JsonObject testSendControl(JsonObject jsonMsg) {
		if ("dev".equals(ConnectionConsts.ENV_PATH)) {
			String apnsToken = PropertiesLoaderUtils.multiProp.getProperty("apple.test.apnsToken");
			if (jsonMsg != null) {
				jsonMsg.put("apnsToken", apnsToken);
			}
		}
		return jsonMsg;
	}

}
