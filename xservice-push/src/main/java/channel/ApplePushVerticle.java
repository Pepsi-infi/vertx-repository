package channel;

import java.util.HashMap;
import java.util.Map;

import constant.ServiceUrlConstant;
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
		
		logger.info("enter applePushService sendMsg method");
		
		Map<String, String> params=new HashMap<>();
		params.put("deviceToken", receiveMsg.getString("apnsToken"));
		params.put("title", receiveMsg.getString("title"));
		params.put("body", receiveMsg.getString("content"));
		params.put("msgbody", receiveMsg.toString());
		
		String host=PropertiesLoaderUtils.multiProp.getProperty("apple.push.host.dev");
		
		if(StringUtil.isNullOrEmpty(host)){
			resultHandler.handle(Future.failedFuture("Apple push host is null"));
			return;
		}
		
		String result = null;
		try {
			result = HttpUtils.URLPost(host+ServiceUrlConstant.APPLE_PUSH_URL, params, HttpUtils.URL_PARAM_DECODECHARSET_UTF8);
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
}
