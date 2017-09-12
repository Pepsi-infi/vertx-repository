package service.impl;

import java.util.Collections;

import constant.PushConsts;
import enums.ErrorCodeEnum;
import helper.XProxyHelper;
import io.netty.util.internal.StringUtil;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import result.ResultData;
import service.ConfigService;
import service.RedisService;
import util.Md5Util;
import utils.IPUtil;
import xservice.BaseServiceVerticle;

public class ConfigServiceImpl extends BaseServiceVerticle implements ConfigService {

	private static final Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);

	private RedisService redisService;

	@Override
	public void start() throws Exception {
		super.start();

		XProxyHelper.registerService(ConfigService.class, vertx, this, ConfigService.SERVICE_ADDRESS);
		publishEventBusService(ConfigService.SERVICE_NAME, ConfigService.SERVICE_ADDRESS, ConfigService.class);

		String ip = IPUtil.getInnerIP();
		XProxyHelper.registerService(ConfigService.class, vertx, this, ConfigService.getLocalAddress(ip));
		publishEventBusService(ConfigService.LOCAL_SERVICE_NAME, ConfigService.getLocalAddress(ip),
				ConfigService.class);

		this.initService();
	}

	private void initService() {

		redisService = RedisService.createProxy(vertx);
	}

	@Override
	public void getVerifyFromMsgCenter(String senderId, String senderKey, Handler<AsyncResult<String>> resultHandler) {

		if (StringUtil.isNullOrEmpty(senderId)) {
			logger.error("senderId is null");
			resultHandler.handle(Future.succeededFuture(
					new ResultData<>(ErrorCodeEnum.FAIL.getCode(), "senderId is null", Collections.EMPTY_MAP)
							.toString()));
			return;
		}

		if (StringUtil.isNullOrEmpty(senderKey)) {
			logger.error("senderKey is null");
			resultHandler.handle(Future.succeededFuture(
					new ResultData<>(ErrorCodeEnum.FAIL.getCode(), "senderKey is null", Collections.EMPTY_MAP)
							.toString()));
			return;
		}

		String key = PushConsts.MESSAGE_SENDER_PREFIX + senderId;
		Future<String> checkFuture = this.getCheckFuture(key);

		Future<String> setFuture = this.generateNewSender(senderId, senderKey, checkFuture);
		setFuture.setHandler(handler -> {
			if (handler.succeeded()) {
				resultHandler.handle(
						Future.succeededFuture(new ResultData<>(ErrorCodeEnum.SUCCESS, handler.result()).toString()));
			} else {
				resultHandler.handle(Future.succeededFuture(
						new ResultData<>(ErrorCodeEnum.FAIL.getCode(), handler.cause().getMessage(), Collections.EMPTY_MAP)
								.toString()));
			}
		});
	}

	private Future<String> getCheckFuture(String key) {
		Future<String> checkFuture=Future.future();
		redisService.get(key, checkFuture.completer());
		return checkFuture;
	}

	private Future<String> generateNewSender(String senderId, String senderKey, Future<String> checkFuture) {
	   
		return checkFuture.compose(res -> {
			Future<String> setFuture = Future.future();
			String serverSign=res;
			try {
					
				if ((!StringUtil.isNullOrEmpty(serverSign)
						&& !(Md5Util.encodeByMd5AndSalt(senderId)).equals(serverSign))||StringUtil.isNullOrEmpty(serverSign)) {
					logger.info("sender is not exist in redis,ge a new ");
					redisService.setEx(PushConsts.MESSAGE_SENDER_PREFIX + senderId, 3600 * 24 * 365 * 100l,
							Md5Util.encodeByMd5AndSalt(senderId), setRes -> {
								if (setRes.succeeded()) {
									setFuture.complete(setRes.result());
								} else {
									logger.error("gen new sender to redis error:", setRes.cause());
									setFuture.fail(setRes.cause().getMessage());
								}
							});

				} else {
					setFuture.fail("sender is exsit");
				}
			} catch (Exception e) {
				logger.error("md5 exception", e);
				setFuture.fail("md5 exception");
			}

			return setFuture;
		});
	}

}
