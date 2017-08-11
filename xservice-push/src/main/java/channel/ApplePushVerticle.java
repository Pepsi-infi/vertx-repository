package channel;

import constant.ConnectionConsts;
import io.netty.util.internal.StringUtil;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.serviceproxy.ProxyHelper;
import service.ApplePushService;
import util.HttpUtils;
import util.PropertiesLoaderUtils;
import utils.BaseResponse;
import xservice.BaseServiceVerticle;

import java.util.HashMap;
import java.util.Map;

import static constant.PushConsts.apnsToken;

public class ApplePushVerticle extends BaseServiceVerticle implements ApplePushService {
	
	private static final Logger logger=LoggerFactory.getLogger(ApplePushVerticle.class);
	
	@Override
	public void start() throws Exception {
		
		super.start();
		
		ProxyHelper.registerService(ApplePushService.class, vertx, this,
				ApplePushService.class.getName());
		
		
	}

	@Override
	public void sendMsg(JsonObject receiveMsg, Handler<AsyncResult<BaseResponse>> resultHandler) {
		//测试专用，防止测试推错推到线上
		receiveMsg = testSendControl(receiveMsg);

		logger.info("enter applePushService sendMsg method");
		
		Map<String, String> params=new HashMap<>();
		params.put("deviceToken", receiveMsg.getString("apnsToken"));
		params.put("title", receiveMsg.getString("title"));
		params.put("body", receiveMsg.getString("content"));
		params.put("msgbody", receiveMsg.toString());
		
		String host=PropertiesLoaderUtils.multiProp.getProperty("apple.push.host");
		
		if(StringUtil.isNullOrEmpty(host)){
			resultHandler.handle(Future.failedFuture("Apple push host is null"));
			return;
		}
		
		logger.info("请求地址："+host+"参数："+params);
		String result = null;
		try {
			result = HttpUtils.URLPost(host, params, HttpUtils.URL_PARAM_DECODECHARSET_UTF8);
		} catch (Exception e) {
			logger.error("apns推送调用异常", e);
			resultHandler.handle(Future.failedFuture(e));
			return;
		}
		
		logger.info("apns推送返回结果："+result);
		
		if(StringUtil.isNullOrEmpty(result)){
			resultHandler.handle(Future.failedFuture("Apple push result is null"));
		}else{
			resultHandler.handle(Future.succeededFuture(new BaseResponse()));
		}

	}

	//测试专用，防止消息推送到线上用户
	private JsonObject testSendControl(JsonObject jsonMsg){
		if("dev".equals(ConnectionConsts.ENV_PATH)){
			String apnsToken = PropertiesLoaderUtils.multiProp.getProperty("apple.test.apnsToken");
			if(jsonMsg != null){
				jsonMsg.put("apnsToken", apnsToken);
			}
		}
		return jsonMsg;
	}
}
