package api;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import constants.ChannelEnum;
import dao.MsgStatResultDao;
import io.netty.util.internal.StringUtil;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertx.rxjava.ext.web.handler.CorsHandler;
import iservice.DeviceService;
import iservice.MsgStatService;
import iservice.dto.DeviceDto;
import iservice.dto.MsgStatDto;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import rxjava.RestAPIVerticle;
import utils.IPUtil;
import utils.JsonUtil;
import utils.TimeUtil;
import utils.VersionCompareUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lufei Date : 2017/7/25 11:40 Description :
 */
public class RestStatVerticle extends RestAPIVerticle {
	private static final Logger logger = LoggerFactory.getLogger(RestStatVerticle.class);

	private MsgStatService msgStatService;

	private MsgStatResultDao msgStatResultDao;

	private DeviceService deviceService;

	@Override
	public void start() throws Exception {
		super.start();

		logger.info("Rest xservice-statistic verticle: Start...");

		Router router = Router.router(vertx);
		router.route().handler(CorsHandler.create("*"));
		router.route().handler(BodyHandler.create());
		router.post(StatRestConstants.Stat.PUSH_MSG_REPORT).handler(this::statPushMsg);
		router.route(StatRestConstants.Stat.QUERY_PUSH_MSG_STAT).handler(this::queryMsgStatResult);
		router.post(StatRestConstants.Device.DEVICE_REPORT).handler(this::reportUserDevice);
		Future<Void> voidFuture = Future.future();

		String serverHost = this.getServerHost();
		createHttpServer(router, serverHost, config().getInteger("service.port"))
				.compose(serverCreated -> publishHttpEndpoint(StatRestConstants.Stat.SERVICE_NAME, serverHost,
						StatRestConstants.Stat.HTTP_PORT, StatRestConstants.Stat.SERVICE_ROOT))
				.setHandler(voidFuture.completer());

		this.initMsgStatService();
	}

	private void initMsgStatService() {
		msgStatService = MsgStatService.createProxy(vertx.getDelegate());
		msgStatResultDao = MsgStatResultDao.createProxy(vertx.getDelegate());
		deviceService = DeviceService.createProxy(vertx.getDelegate());
	}

	private String getServerHost() {
		return StringUtils.isNotBlank(IPUtil.getInnerIP()) ? IPUtil.getInnerIP() : "127.0.0.1";
	}

	private void statPushMsg(RoutingContext context) {
		List<MsgStatDto> msgStatDtos = buildMsgStatDto(context);
		logger.info("stat pushMsg,the msgStatDtos: {}", Json.encode(msgStatDtos));
		if (null == msgStatDtos) {
			paramBadRequest(context, "Param [appCode or osType or msgList] cannot be empty.");
		} else {
			msgStatService.statPushMsg(msgStatDtos, resultStringHandler(context));
		}
	}

	private void reportUserDevice(RoutingContext context) {
		DeviceDto userDeviceDto = buildDeviceDto(context);
		logger.info("the request params : userDeviceDto : " + userDeviceDto);
		if (null == userDeviceDto) {
			paramBadRequest(context, "Required  parameters cannot be empty.");
		} else {

			int cmp = getVersionCompareResult(userDeviceDto.getChannel() + "", userDeviceDto.getAppVersion());

			logger.info("cmp=" + cmp);

			if (cmp >= 0) {
				// 当前版本号小于等于5.2.2,按照蚂蚁指纹来进行上报
				deviceService.reportDevice(userDeviceDto, resultHandler(context, JsonUtil::encodePrettily));
			} else {
				// 当前版本号大于5.2.2 add by ylf 按照设备id号来更新设备表的数据
				deviceService.reportDeviceByAddDeviceId(userDeviceDto,
						resultHandler(context, JsonUtil::encodePrettily));
			}
		}
	}

	/**
	 * @param context
	 * @return
	 */
	private DeviceDto buildDeviceDto(RoutingContext context) {
		DeviceDto userDeviceDto = new DeviceDto();
		String uid = context.request().formAttributes().get("uid");
		String phone = context.request().formAttributes().get("phone");
		String deviceType = context.request().formAttributes().get("deviceType");
		String channel = context.request().formAttributes().get("channel");
		String deviceToken = context.request().formAttributes().get("deviceToken");
		String osType = context.request().formAttributes().get("osType");
		String osVersion = context.request().formAttributes().get("osVersion");
		String appCode = context.request().formAttributes().get("appCode");
		String appVersion = context.request().formAttributes().get("appVersion");
		String antFingerprint = context.request().formAttributes().get("antFingerprint");
		String isAcceptPush = context.request().formAttributes().get("isAcceptPush");
		// add by ylf
		String deviceId = context.request().formAttributes().get("deviceId");
		
		//去除对蚂蚁指纹的约束
		if (StringUtils.isBlank(deviceType) || StringUtils.isBlank(osType) || StringUtils.isBlank(osVersion)
				|| StringUtils.isBlank(appVersion) || StringUtils.isBlank(appCode)) {
			logger.warn("Required  parameters is empty. params : {}", Json.encode(context.request().formAttributes()));
			return null;
		}
		logger.info("appVersion=" + appVersion);

		int cmp = getVersionCompareResult(channel, appVersion);

		if (cmp < 0) {
			// 当前版本号大于5.2.2,需要增加对deviceId的判断
			if (StringUtil.isNullOrEmpty(deviceId)) {
				logger.error("deviceId is null");
				return null;
			}
		}

		userDeviceDto.setDeviceToken(deviceToken);
		userDeviceDto.setOsVersion(osVersion);
		userDeviceDto.setPhone(phone);
		userDeviceDto.setDeviceType(deviceType);
		userDeviceDto.setAppVersion(appVersion);
		userDeviceDto.setAntFingerprint(antFingerprint);
		// add by ylf
		userDeviceDto.setDeviceId(deviceId);
		if (StringUtils.isNotBlank(osType)) {
			userDeviceDto.setOsType(Integer.valueOf(osType));
		}
		if (StringUtils.isNotBlank(uid)) {
			userDeviceDto.setUid(Long.valueOf(uid));
		}
		if (StringUtils.isNotBlank(appCode)) {
			userDeviceDto.setAppCode(Integer.valueOf(appCode));
		}
		if (StringUtils.isNotBlank(appCode)) {
			userDeviceDto.setAppCode(Integer.valueOf(appCode));
		}
		if (StringUtils.isNotBlank(channel)) {
			userDeviceDto.setChannel(Integer.valueOf(channel));
		}
		if (StringUtils.isNotBlank(isAcceptPush)) {
			userDeviceDto.setIsAcceptPush(Integer.valueOf(isAcceptPush));
		}
		return userDeviceDto;
	}

	private int getVersionCompareResult(String channel, String appVersion) {
		int cmp = 0;
		if ((ChannelEnum.APNS.getType() + "").equals(channel) && matchVersion(appVersion)) {
			// apns 用appVersion来判断版本问题
			cmp = VersionCompareUtil.hisCompare2Current("5.2.2", appVersion);
		} else if (((ChannelEnum.GCM.getType() + "").equals(channel)
				|| (ChannelEnum.XIAOMI.getType() + "").equals(channel))) {
			// 针对安卓版本做的适配 gcm 或者 xiaomi 采用其他方式 目前默认是历史版本 所以会走之前的设备上报逻辑
			// 版本控制方式需要再处理
			// 5.2.2版本之前（包括5.2.2）,安卓会传数字，5.2.2=43 该判断代表安卓端上送的是5.2.2之后的版本
			if (matchVersion(appVersion)) {
				cmp = VersionCompareUtil.hisCompare2Current("5.2.2", appVersion);
			} else {
				// 代表是之前安卓版本的逻辑
				cmp = 0;
			}
		}
		return cmp;
	}

	private boolean matchVersion(String appVersion) {
		String regex = "^([1-9]+[0-9]*)(\\.[1-9]*[0-9]+)(\\.[1-9]*[0-9]+)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(appVersion);
		return matcher.matches();
	}

	private void queryMsgStatResult(RoutingContext context) {
		String msgId = context.request().params().get("msgId");
		String page = context.request().params().get("page");
		String size = context.request().params().get("size");
		logger.info("query msgStatResult msgId: {}，page: {},size:{}", msgId, page, size);
		Map<String, String> params = Maps.newHashMap();
		if (StringUtils.isNotBlank(msgId)) {
			params.put("msgId", msgId);
		}
		msgStatResultDao.queryMsgStatResultByPage(params, NumberUtils.toInt(page), NumberUtils.toInt(size),
				resultHandler(context, JsonUtil::encodePrettily));

	}

	private List<MsgStatDto> buildMsgStatDto(RoutingContext context) {
		String appCode = context.request().formAttributes().get("appCode");
		String osType = context.request().formAttributes().get("osType");
		String antFingerprint = context.request().formAttributes().get("antFingerprint");
		String msgList = context.request().formAttributes().get("msgList");

		if (StringUtils.isBlank(appCode) || StringUtils.isBlank(osType) || StringUtils.isBlank(msgList)) {
			logger.warn(
					"Required  parameters is empty. params  appCode: {}, osType: {}, antFingerprint: {}, msgList:{}",
					appCode, osType, antFingerprint, msgList);
			return null;
		}
		List<MsgStatDto> msgStatDtos = Lists.newArrayList();
		List<HashMap> msgStatList = Json.decodeValue(msgList, List.class);
		for (HashMap msgStatMap : msgStatList) {
			MsgStatDto statDto = new MsgStatDto();
			if (StringUtils.isNotBlank(osType)) {
				statDto.setOsType(Integer.valueOf(osType));
			}
			if (StringUtils.isNotBlank(appCode)) {
				statDto.setAppCode(Integer.valueOf(appCode));
			}
			statDto.setAntFingerprint(antFingerprint);
			statDto.setAction(MapUtils.getInteger(msgStatMap, "action"));
			statDto.setMsgId(MapUtils.getString(msgStatMap, "msgId"));
			statDto.setChannel(MapUtils.getInteger(msgStatMap, "channel"));
			Long arriveTime = MapUtils.getLong(msgStatMap, "arriveTime");
			if (arriveTime != null) {
				statDto.setArriveTime(TimeUtil.timestamp2date(arriveTime));
			}
			msgStatDtos.add(statDto);
		}
		return msgStatDtos;
	}

	public static void main(String[] args) {

		String regex = "^([1-9]+[0-9]*)(\\.[1-9]*[0-9]+)(\\.[1-9]*[0-9]+)";

		Pattern pattern = Pattern.compile(regex);
		String input="05.02.01";
		Matcher matcher = pattern.matcher(input);
		System.out.println(matcher.matches());
				
		int cmp = VersionCompareUtil.hisCompare2Current("5.2.2", input);
		System.out.println(Integer.valueOf("00000"));
		System.out.println(cmp);
	}

}
