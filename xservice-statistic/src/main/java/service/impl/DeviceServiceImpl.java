package service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dao.DeviceDao;
import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.serviceproxy.ProxyHelper;
import iservice.DeviceService;
import iservice.dto.DeviceDto;
import org.apache.commons.lang.StringUtils;
import rxjava.BaseServiceVerticle;
import utils.BaseResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by lufei Date : 2017/7/27 10:09 Description :
 */
public class DeviceServiceImpl extends BaseServiceVerticle implements DeviceService {

	private static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);

	private DeviceDao deviceDao;

	@Override
	public void start() throws Exception {
		super.start();

		//XProxyHelper.registerService(DeviceService.class, vertx, this, DeviceService.SERVICE_ADDRESS);
		ProxyHelper.registerService(DeviceService.class, vertx.getDelegate(), this, DeviceService.SERVICE_ADDRESS);
		publishEventBusService(DeviceService.SERVICE_NAME, DeviceService.SERVICE_ADDRESS, DeviceService.class);
		deviceDao = DeviceDao.createProxy(vertx.getDelegate());
	}

	@Override
	public void reportDevice(DeviceDto deviceDto, Handler<AsyncResult<BaseResponse>> result) {
		BaseResponse baseResponse = new BaseResponse();
		if (StringUtils.isBlank(deviceDto.getAntFingerprint())) {
			logger.error("the antFingerprint is null");
			buildErrorBaseResponse(baseResponse, "the antFingerprint is null");
			result.handle(Future.succeededFuture(baseResponse));
		} else {
			Future<DeviceDto> getDeviceFuture = Future.future();
			Map<String, String> params = Maps.newHashMap();
			params.put("antFingerprint", deviceDto.getAntFingerprint());
			deviceDao.getDevice(params, getDeviceFuture.completer());

			Future<BaseResponse> addFuture = Future.future();
			Future<BaseResponse> updateFuture = Future.future();

			getDeviceFuture.setHandler(ar1 -> {
				if (ar1.succeeded()) {
					DeviceDto dbDevice = ar1.result();
					if (dbDevice == null) {
						deviceDao.addDevice(deviceDto, addFuture.completer());
					} else {
						copyDevice(dbDevice, deviceDto);
						deviceDao.updateDevice(dbDevice, updateFuture.completer());
					}
				} else {
					logger.error(ar1.cause());
					buildErrorBaseResponse(baseResponse, ar1.cause().toString());
					result.handle(Future.succeededFuture(baseResponse));
				}
			});

			addFuture.setHandler(ar2 -> {
				if (ar2.succeeded()) {
					result.handle(Future.succeededFuture(baseResponse));
				} else {
					logger.error("add device:" + deviceDto + " to db error.", ar2.cause());
					buildErrorBaseResponse(baseResponse, ar2.cause().toString());
					result.handle(Future.succeededFuture(baseResponse));
				}
			});
			updateFuture.setHandler(ar3 -> {
				if (ar3.succeeded()) {
					result.handle(Future.succeededFuture(baseResponse));
				} else {
					logger.error("update device:" + deviceDto + " from db error.", ar3.cause());
					buildErrorBaseResponse(baseResponse, ar3.cause().toString());
					result.handle(Future.succeededFuture(baseResponse));
				}
			});
		}
	}

	@Override
	public void queryDevices(Map<String, String> param, Handler<AsyncResult<List<DeviceDto>>> result) {
		Future<DeviceDto> resultFuture = Future.future();
		deviceDao.getDevice(param, resultFuture.completer());
		resultFuture.setHandler(handler -> {
			if (handler.succeeded()) {
				List<DeviceDto> list = Lists.newArrayList();
				if (handler.result() != null) {
					list.add(handler.result());
				}
				result.handle(Future.succeededFuture(list));
			} else {
				logger.error("query devices error.", handler.cause());
				result.handle(Future.failedFuture(handler.cause()));
			}
		});
	}

	private <T extends BaseResponse> void buildErrorBaseResponse(T response, String message) {
		if (response != null) {
			response.setStatus(BaseResponse.RESPONSE_FAIL_CODE);
			response.setErrorMessage(message);
		}
	}

	private void copyDevice(DeviceDto dest, DeviceDto src) {
		if (null != src.getAppCode()) {
			dest.setAppCode(src.getAppCode());
		}
		if (null != src.getChannel()) {
			dest.setChannel(src.getChannel());
		}
		if (null != src.getDeviceToken()) {
			dest.setDeviceToken(src.getDeviceToken());
		}
		if (null != src.getPhone()) {
			dest.setPhone(src.getPhone());
		}
		if (null != src.getAntFingerprint()) {
			dest.setAntFingerprint(src.getAntFingerprint());
		}
		if (null != src.getAppVersion()) {
			dest.setAppVersion(src.getAppVersion());
		}
		if (null != src.getDeviceType()) {
			dest.setDeviceType(src.getDeviceType());
		}
		if (null != src.getIsAcceptPush()) {
			dest.setIsAcceptPush(src.getIsAcceptPush());
		}
		if (null != src.getOsType()) {
			dest.setOsType(src.getOsType());
		}
		if (null != src.getOsVersion()) {
			dest.setOsVersion(src.getOsVersion());
		}
		if (null != src.getUid()) {
			dest.setUid(src.getUid());
		}
	}
}
