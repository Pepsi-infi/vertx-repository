package dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import dao.BaseDaoVerticle;
import dao.DeviceDao;
import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.MySQLClient;
import iservice.dto.DeviceDto;
import utils.BaseResponse;
import utils.CalendarUtil;

/**
 * Created by lufei Date : 2017/7/26 10:32 Description :
 */
public class DeviceDaoImpl extends BaseDaoVerticle implements DeviceDao {
	private static final Logger logger = LoggerFactory.getLogger(DeviceDaoImpl.class);

	public interface Sql {
		static final String ADD_USER_DEVICE = "insert into device (uid,phone,deviceType,channel,deviceToken,osType,osVersion,appCode,appVersion,antFingerprint,isAcceptPush,createTime,updateTime,deviceId) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		static final String UPDATE_USER_DEVICE = "UPDATE device SET uid=?,phone=?,deviceType=?,channel=?,deviceToken=?,osType=?,"
				+ "osVersion=?,appCode=?,appVersion=?,isAcceptPush=?,updateTime=?,deviceId=? " + "WHERE antFingerprint=?";

		static final String UPDATE_USER_DEVICE_BY_DEVICEID = "UPDATE device SET uid=?,phone=?,deviceType=?,channel=?,deviceToken=?,osType=?,"
				+ "osVersion=?,appCode=?,appVersion=?,isAcceptPush=?,updateTime=?,antFingerprint=? " + "WHERE deviceId=?";

		static final String QUERY_USER_DEVICE = "SELECT * FROM device WHERE 1=1 %s ORDER BY updateTime DESC limit 1";

	}

	public DeviceDaoImpl() {
	}

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		super.start(startFuture);

		XProxyHelper.registerService(DeviceDao.class, vertx, this, DeviceDao.SERVICE_ADDRESS);
		publishEventBusService(DeviceDao.SERVICE_NAME, DeviceDao.SERVICE_ADDRESS, DeviceDao.class);

		client = MySQLClient.createNonShared(vertx, config().getJsonObject("mysql").getJsonObject("mc-device"));
	}

	@Override
	public void addDevice(DeviceDto deviceDto, Handler<AsyncResult<BaseResponse>> resultHandler) {
		// (uid,phone,deviceType,channel,deviceToken,osType,osVersion,appCode,appVersion,antFingerprint,isAcceptPush)
		JsonArray jsonArray = new JsonArray();
		jsonArray.add(deviceDto.getUid() != null ? deviceDto.getUid() : -1)
				.add(deviceDto.getPhone() != null ? deviceDto.getPhone() : "")
				.add(deviceDto.getDeviceType() != null ? deviceDto.getDeviceType() : "")
				.add(deviceDto.getChannel() != null ? deviceDto.getChannel() : -1)
				.add(deviceDto.getDeviceToken() != null ? deviceDto.getDeviceToken() : "")
				.add(deviceDto.getOsType() != null ? deviceDto.getOsType() : -1)
				.add(deviceDto.getOsVersion() != null ? deviceDto.getOsVersion() : "")
				.add(deviceDto.getAppCode() != null ? deviceDto.getAppCode() : -1)
				.add(deviceDto.getAppVersion() != null ? deviceDto.getAppVersion() : "")
				.add(deviceDto.getAntFingerprint() != null ? deviceDto.getAntFingerprint() : "")
				.add(deviceDto.getIsAcceptPush() != null ? deviceDto.getIsAcceptPush() : 0)
				.add(CalendarUtil.format(new Date())).add(CalendarUtil.format(new Date()));
		execute(jsonArray, Sql.ADD_USER_DEVICE, new BaseResponse(), resultHandler);

	}

	@Override
	public void updateDevice(DeviceDto deviceDto, Handler<AsyncResult<BaseResponse>> resultHandler) {
		if (StringUtils.isBlank(deviceDto.getAntFingerprint())) {
			logger.error("[updateDevice] the antFingerprint is null");
			resultHandler.handle(Future.failedFuture("the antFingerprint is null"));
		} else {
			JsonArray jsonArray = new JsonArray();
			// (uid,phone,deviceType,miToken,imei,osType,osVersion,appCode,appVersion,antFingerprint)
			jsonArray.add(deviceDto.getUid() != null ? deviceDto.getUid() : "")
					.add(deviceDto.getPhone() != null ? deviceDto.getPhone() : "")
					.add(deviceDto.getDeviceType() != null ? deviceDto.getDeviceType() : "")
					.add(deviceDto.getChannel() != null ? deviceDto.getChannel() : 0)
					.add(deviceDto.getDeviceToken() != null ? deviceDto.getDeviceToken() : "")
					.add(deviceDto.getOsType() != null ? deviceDto.getOsType() : -1)
					.add(deviceDto.getOsVersion() != null ? deviceDto.getOsVersion() : "")
					.add(deviceDto.getAppCode() != null ? deviceDto.getAppCode() : 0)
					.add(deviceDto.getAppVersion() != null ? deviceDto.getAppVersion() : "")
					.add(deviceDto.getIsAcceptPush() != null ? deviceDto.getIsAcceptPush() : 0)
					.add(CalendarUtil.format(new Date()))
					.add(deviceDto.getAntFingerprint() != null ? deviceDto.getAntFingerprint() : "");
			execute(jsonArray, Sql.UPDATE_USER_DEVICE, new BaseResponse(), resultHandler);
		}
	}

	@Override
	public void getDevice(Map<String, String> params, Handler<AsyncResult<DeviceDto>> resultHandler) {
		String sql = Sql.QUERY_USER_DEVICE;
		StringBuilder sb = new StringBuilder();
		String antFingerprint = MapUtils.getString(params, "antFingerprint");
		if (StringUtils.isNotBlank(antFingerprint)) {
			sb.append(" and antFingerprint = '").append(antFingerprint).append("'");
		}
		String phone = MapUtils.getString(params, "phone");
		if (StringUtils.isNotBlank(phone)) {
			sb.append(" and phone = '").append(phone).append("'");
		}
		String deviceId = MapUtils.getString(params, "deviceId");
		if (StringUtils.isNotBlank(deviceId)) {
			sb.append(" and deviceId = '").append(deviceId).append("'");
		}
		sql = String.format(sql, sb.toString());
		Future<List<JsonObject>> future = retrieveAll(sql);
		future.setHandler(result -> {
			if (result.succeeded()) {
				List<DeviceDto> deviceDtos = Lists.transform(result.result(), new Function<JsonObject, DeviceDto>() {
					@Nullable
					@Override
					public DeviceDto apply(@Nullable JsonObject jsonObject) {
						return jsonObject.mapTo(DeviceDto.class);
					}
				});
				resultHandler.handle(
						Future.succeededFuture(CollectionUtils.isNotEmpty(deviceDtos) ? deviceDtos.get(0) : null));
			} else {
				logger.error(result.cause());
				resultHandler.handle(Future.failedFuture(result.cause()));
			}
		});
	}

	@Override
	public void queryDevices(Map<String, String> params, Handler<AsyncResult<List<DeviceDto>>> resultHandler) {
		String sql = Sql.QUERY_USER_DEVICE;
		StringBuilder sb = new StringBuilder();
		String phone = MapUtils.getString(params, "phone");
		if (StringUtils.isNotBlank(phone)) {
			sb.append(" and phone = '").append(phone).append("'");
		}
		String deviceToken = MapUtils.getString(params, "deviceToken");
		if (StringUtils.isNotBlank(deviceToken)) {
			sb.append(" and deviceToken = '").append(deviceToken).append("'");
		}
		String channel = MapUtils.getString(params, "channel");
		if (StringUtils.isNotBlank(channel)) {
			sb.append(" and channel = ").append(channel);
		}
		sql = String.format(sql, sb.toString());
		Future<List<JsonObject>> future = retrieveMany(new JsonArray(), sql);
		future.setHandler(result -> {
			if (result.succeeded()) {
				List<DeviceDto> deviceDtos = Lists.transform(result.result(), new Function<JsonObject, DeviceDto>() {
					@Nullable
					@Override
					public DeviceDto apply(@Nullable JsonObject jsonObject) {
						return jsonObject.mapTo(DeviceDto.class);
					}
				});
				resultHandler.handle(Future.succeededFuture(deviceDtos));
			} else {
				logger.error(result.cause());
				resultHandler.handle(Future.failedFuture(result.cause()));
			}
		});
	}

	/**
	 * add by ylf
	 */
	@Override
	public void updateDeviceByDeviceId(DeviceDto deviceDto, Handler<AsyncResult<BaseResponse>> resultHandler) {
		if (StringUtils.isBlank(deviceDto.getDeviceId())) {
			logger.error("[updateDevice] the deviceId is null");
			resultHandler.handle(Future.failedFuture("the deviceId is null"));
		} else {
			JsonArray jsonArray = new JsonArray();
			// (uid,phone,deviceType,miToken,imei,osType,osVersion,appCode,appVersion,antFingerprint)
			jsonArray.add(deviceDto.getUid() != null ? deviceDto.getUid() : "")
					.add(deviceDto.getPhone() != null ? deviceDto.getPhone() : "")
					.add(deviceDto.getDeviceType() != null ? deviceDto.getDeviceType() : "")
					.add(deviceDto.getChannel() != null ? deviceDto.getChannel() : 0)
					.add(deviceDto.getDeviceToken() != null ? deviceDto.getDeviceToken() : "")
					.add(deviceDto.getOsType() != null ? deviceDto.getOsType() : -1)
					.add(deviceDto.getOsVersion() != null ? deviceDto.getOsVersion() : "")
					.add(deviceDto.getAppCode() != null ? deviceDto.getAppCode() : 0)
					.add(deviceDto.getAppVersion() != null ? deviceDto.getAppVersion() : "")
					.add(deviceDto.getIsAcceptPush() != null ? deviceDto.getIsAcceptPush() : 0)
					.add(CalendarUtil.format(new Date()))
					.add(deviceDto.getAntFingerprint() != null ? deviceDto.getAntFingerprint() : "")
					.add(deviceDto.getDeviceId() != null ? deviceDto.getDeviceId() : "");
			execute(jsonArray, Sql.UPDATE_USER_DEVICE_BY_DEVICEID, new BaseResponse(), resultHandler);
		}

	}
}
