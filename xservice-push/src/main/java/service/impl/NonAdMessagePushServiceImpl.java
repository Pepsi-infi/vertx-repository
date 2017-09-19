package service.impl;

import channel.ApplePushService;
import channel.SocketPushService;
import channel.XiaoMiPushService;
import constant.PushConsts;
import domain.MsgRecord;
import enums.ErrorCodeEnum;
import enums.MsgStatusEnum;
import enums.PushTypeEnum;
import helper.XProxyHelper;
import io.netty.util.internal.StringUtil;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.client.WebClient;
import iservice.DeviceService;
import iservice.MsgStatService;
import iservice.dto.DeviceDto;
import iservice.dto.MsgStatDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import result.ResultData;
import service.MsgRecordService;
import service.NonAdMessagePushService;
import service.PassengerUnSendService;
import service.RedisService;
import util.DateUtil;
import util.Md5Util;
import utils.BaseResponse;
import utils.IPUtil;
import xservice.RestAPIVerticle;

import java.util.*;

public class NonAdMessagePushServiceImpl extends RestAPIVerticle implements NonAdMessagePushService {

	private static final Logger logger = LoggerFactory.getLogger(NonAdMessagePushServiceImpl.class);

	private SocketPushService socketPushService;

	private XiaoMiPushService xiaomiPushService;

	private RedisService redisService;

	private MsgRecordService msgRecordService;

	private MsgStatService msgStatService;

	private DeviceService deviceService;

	private ApplePushService applePushService;

	private PassengerUnSendService passengerUnSendService;

	private String token;
	private Integer channel;

	private JsonObject config;

	@Override
	public void start() throws Exception {

		super.start();

		XProxyHelper.registerService(NonAdMessagePushService.class, vertx, this,
				NonAdMessagePushService.SERVICE_ADDRESS);
		publishEventBusService(NonAdMessagePushService.SERVICE_NAME, NonAdMessagePushService.SERVICE_ADDRESS,
				NonAdMessagePushService.class);

		String ip = IPUtil.getInnerIP();
		XProxyHelper.registerService(NonAdMessagePushService.class, vertx, this,
				NonAdMessagePushService.getLocalAddress(ip));
		publishEventBusService(NonAdMessagePushService.LOCAL_SERVICE_NAME, NonAdMessagePushService.getLocalAddress(ip),
				NonAdMessagePushService.class);

		// 初始化化服务
		this.initService();

	}

	public void pushMsg(String senderId, String senderKey, String httpMsg, Handler<AsyncResult<String>> resultHandler) {
		logger.info("接收到的消息内容：" + httpMsg);
		try {
			if (StringUtil.isNullOrEmpty(httpMsg)) {
				logger.error("body is null");
				resultHandler.handle(Future.succeededFuture(
						new ResultData<Object>(ErrorCodeEnum.FAIL.getCode(), "body is null", Collections.EMPTY_MAP)
								.toString()));
			} else {
				JsonObject receiveMsg = new JsonObject(httpMsg);
				receiveMsg.put("senderId", senderId);
				receiveMsg.put("senderKey", senderKey);
				this.dealHttpMessage(receiveMsg, senderId, senderKey, resultHandler);
			}
		} catch (Exception e) {
			logger.error("消息推送异常", e);
			resultHandler.handle(Future.succeededFuture(
					new ResultData<Object>(ErrorCodeEnum.FAIL.getCode(), e.getMessage(), Collections.EMPTY_MAP)
							.toString()));
		}
	}

	private void dealHttpMessage(JsonObject receiveMsg, String senderId, String senderKey,
			Handler<AsyncResult<String>> resultHandler) {
		// 验证必填项
		ResultData checkResult = checkRecivedMsg(receiveMsg);
		if (ResultData.FAIL == checkResult.getCode()) {
			resultHandler.handle(Future.succeededFuture(
					new ResultData<Object>(ErrorCodeEnum.FAIL.getCode(), checkResult.getMsg(), Collections.EMPTY_MAP)
							.toString()));
			return;
		}

		// 验证发送方身份是否合法
		Future<Void> leaglFuture = Future.future();
		Future<BaseResponse> pushFuture = Future.future();
		this.checkSender(receiveMsg.getString("senderId"), receiveMsg.getString("senderKey"), leaglFuture.completer());
		leaglFuture.setHandler(res -> {
			if (res.succeeded()) {
				logger.info("发送方签名校验通过");
				pushMsgToDownStream(receiveMsg, pushFuture.completer());
			} else {
				logger.error("发送方签名校验未通过" + res.cause());
				resultHandler.handle(Future.succeededFuture(new ResultData<Object>(ErrorCodeEnum.FAIL.getCode(),
						res.cause().getMessage(), Collections.EMPTY_MAP).toString()));
			}
		});

		// 消息推送成功后，调用上报消息接口
		Future<BaseResponse> statFuture = Future.future();
		pushFuture.setHandler(res -> {
			if (res.succeeded()) {
				callStatPushMsg(receiveMsg, statFuture.completer());
			} else {
				// 输出推送时的错误
				logger.error("调用推送时出错：" + pushFuture.cause());
				saveUnSendMsg(receiveMsg);
				resultHandler.handle(Future.succeededFuture(new ResultData<Object>(ErrorCodeEnum.FAIL.getCode(),
						res.cause().getMessage(), Collections.EMPTY_MAP).toString()));
			}
		});

		// 根据推送结果返回结果数据给http调用方
		statFuture.setHandler(res -> {
			if (res.succeeded()) {
				resultHandler.handle(Future.succeededFuture(
						new ResultData<Object>(ErrorCodeEnum.SUCCESS, Collections.EMPTY_MAP).toString()));
			} else {
				resultHandler.handle(Future.succeededFuture(new ResultData<Object>(ErrorCodeEnum.FAIL.getCode(),
						res.cause().getMessage(), Collections.EMPTY_MAP).toString()));
			}
		});

	}

	//未推送成功的消息入库，用户上线继续推送
	private void saveUnSendMsg(JsonObject srcMsg){
		try {
			JsonObject param = new JsonObject();
			param.put("msgId", srcMsg.getValue("msgId") + "");
			param.put("phone", srcMsg.getString("phone"));
			param.put("userId", srcMsg.getValue("customerId") + "");
            Object expireTime = srcMsg.getValue("expireTime");
			//如果没传过期时间，就一天后过期
			expireTime = (expireTime == null) ? System.currentTimeMillis() + 86400000 : expireTime;
			param.put("expireTime",  expireTime + "");
			param.put("content", srcMsg.toString());
			param.put("callFlag", "2");
			param.put("senderId", srcMsg.getString("senderId"));
			param.put("senderKey", srcMsg.getString("senderKey"));
			passengerUnSendService.pushAddUnSendMsg(param, Future.future());
		}catch (Exception e){
			logger.error("保存未推送成功的消息失败" + e);
		}
	}

	private void checkSender(String senderId, String senderKey, Handler<AsyncResult<Void>> handler) {
		String key = PushConsts.MESSAGE_SENDER_PREFIX + senderId;

		redisService.get(key, result -> {
			String senderSign;
			try {
				senderSign = Md5Util.encodeByMd5AndSalt(senderId);
				logger.info("senderSign=" + senderSign);
			} catch (Exception e) {
				logger.error("md5 compute error", e);
				handler.handle(Future.failedFuture("server is error"));
				return;
			}
			if (StringUtil.isNullOrEmpty(senderSign)) {
				handler.handle(Future.failedFuture("sender sign is null"));
				return;
			}
			if (result.succeeded()) {
				String serverSign = result.result();
				logger.info("serverSign=" + serverSign);
				if (senderSign.equals(serverSign)) {
					handler.handle(Future.succeededFuture());
				} else {
					handler.handle(Future.failedFuture("sender is ileagl"));
				}
			} else {
				handler.handle(Future.failedFuture(result.cause()));
			}
		});

	}

	public void responseSuccess(HttpServerResponse resp, String msg) {
		resp.putHeader("content-type", "text/plain;charset=UTF-8").end(msg);
	}

	public void responseError(HttpServerResponse resp, String msg) {
		ResultData resultData = new ResultData(ErrorCodeEnum.FAIL);
		resultData.setMsg(msg);
		resp.putHeader("content-type", "text/plain;charset=UTF-8").end(resultData.toString());
	}

	/**
	 * 已推送消息上报 //接口参见wiki :
	 * http://cowiki.01zhuanche.com/pages/viewpage.action?pageId=329268
	 */
	private void callStatPushMsg(JsonObject receiveMsg, Handler<AsyncResult<BaseResponse>> resultHandler) {
		String msgId = receiveMsg.getValue("msgId") + "";
		// String customerId = receiveMsg.getValue("customerId") + "";
		// // 推送成功的消息把msgId保存到redis,用来防止重复推送
		// Future<Void> setRedisFuture = Future.future();
		// this.setMsgToRedis(msgId, customerId,
		// receiveMsg.getLong("expireTime"), setRedisFuture.completer());

		// 已推送消息上报接口
		List<MsgStatDto> msgList = new ArrayList<>();
		MsgStatDto msgStatDto = new MsgStatDto();
		// 首约app乘客端 1001；首约app司机端 1002
		msgStatDto.setAppCode(PushConsts.MsgStat_APPCODE_ENGER);
		msgStatDto.setChannel(channel);
		msgStatDto.setMsgId(receiveMsg.getString("senderId") + "_" + msgId);// msgId上报规则
		// 1 安卓
		if (PushTypeEnum.APNS.getSrcCode() == channel) {
			msgStatDto.setOsType(PushConsts.MsgStat_OSTYPE_IOS);
		} else {
			msgStatDto.setOsType(PushConsts.MsgStat_OSTYPE_ANDROID);
		}

		// 1发送，2接收
		msgStatDto.setAction(PushConsts.MsgStat_ACTION_SEND);
		msgStatDto.setSendTime(DateUtil.getDateTime(System.currentTimeMillis()));
		msgList.add(msgStatDto);
		logger.info("已推送消息上报msgId=" + msgId + ",JsonDetail:" + Json.encode(msgStatDto));
		msgStatService.statPushMsg(msgList, statRes -> {
			if (statRes.succeeded()) {
				logger.info("已推送消息上报成功msgId=" + msgId);
				String result = statRes.result();
				exeStatPushMsgResult(msgId, result, resultHandler);
			} else {
				logger.error("已推送消息上报调用异常msgId=" + msgId, statRes.cause());
				resultHandler.handle(Future.failedFuture(statRes.cause()));
			}
		});

	}

	/**
	 * 处理返回结果
	 *
	 * @param result
	 * @param resultHandler
	 */
	private void exeStatPushMsgResult(String msgId, String result, Handler<AsyncResult<BaseResponse>> resultHandler) {
		if (StringUtils.isNotBlank(result)) {
			logger.info("exeStatPushMsgResult > result :" + result);
			JsonObject json = new JsonObject(result);
			if (json != null) {
				Object status = json.getValue("status");
				Integer sts = (status != null) ? (Integer) status : null;
				if (sts != null && PushConsts.MsgStat_StatPushMsg_SUCCESS == sts) {
					logger.error("消息上报返回成功msgId=" + msgId);
					resultHandler.handle(Future.succeededFuture());
				} else {
					logger.error("消息上报返回失败msgId=" + msgId);
					resultHandler.handle(Future.failedFuture("消息上报返回失败msgId=" + msgId));
				}
			}
		} else {
			logger.error("消息上报没有返回结果msgId :" + msgId);
			resultHandler.handle(Future.failedFuture("消息上报没有返回结果msgId :" + msgId));
		}
	}

	private void initService() {
		config = config().getJsonObject("push.config");
		socketPushService = SocketPushService.createProxy(vertx);
		xiaomiPushService = XiaoMiPushService.createProxy(vertx);
		// msgRecordService = MsgRecordService.createProxy(vertx);
		redisService = RedisService.createProxy(vertx);
		msgStatService = MsgStatService.createProxy(vertx);
		deviceService = DeviceService.createProxy(vertx);
		applePushService = ApplePushService.createProxy(vertx);
		passengerUnSendService = PassengerUnSendService.createProxy(vertx);
	}

	private ResultData checkRecivedMsg(JsonObject receiveMsg) {
		ResultData result = new ResultData();
		String senderId = receiveMsg.getString("senderId");
		if (StringUtil.isNullOrEmpty(senderId)) {
			result.reSetResult(ResultData.FAIL, "senderId is null");
			return result;
		}

		String senderKey = receiveMsg.getString("senderKey");
		if (StringUtil.isNullOrEmpty(senderKey)) {
			result.reSetResult(ResultData.FAIL, "senderKey is null");
			return result;
		}

		// 校验必填项
		String msgId = receiveMsg.getValue("msgId") + "";
		if (StringUtils.isBlank(msgId)) {
			result.reSetResult(ResultData.FAIL, "msgId不能为空");
			return result;
		}
		// 用户id
		Object customerId = receiveMsg.getValue("customerId");
		if (null == customerId) {
			result.reSetResult(ResultData.FAIL, "customerId不能为空");
			return result;
		}

		String phone = (String) receiveMsg.getValue("phone");
		if (StringUtils.isBlank(phone)) {
			result.reSetResult(ResultData.FAIL, "上送手机号不能为空");
			return result;
		}
		// sokit、gcm,小米连接token
		token = (String) receiveMsg.getValue("deviceToken");

		Integer openType = receiveMsg.getInteger("type");
		if (openType == null) {
			result.reSetResult(ResultData.FAIL, "type is null");
			return result;
		}

		String url = receiveMsg.getString("url");
		if (openType == PushConsts.PUSH_OPEN_TYPE_HTML && StringUtil.isNullOrEmpty(url)) {
			result.reSetResult(ResultData.FAIL, "select opening html but url is null");
			return result;
		}

		return result;
	}

	/**
	 * 消息是否重复接收
	 *
	 * @param resultHandler
	 */
	private void checkRepeatMsg(JsonObject receiveMsg, Handler<AsyncResult<BaseResponse>> resultHandler) {

		String msgId = receiveMsg.getValue("msgId") + "";
		String customerId = receiveMsg.getValue("customerId") + "";
		// 判断消息是否接收过
		String redisMsgKey = PushConsts.AD_PASSENGER_MSG_PREFIX + msgId + "_" + customerId;
		Future<String> redisFuture = Future.future();
		redisService.get(redisMsgKey, redisFuture.completer());

		redisFuture.setHandler(handler -> {
			if (handler.succeeded()) {
				// 验证redis
				String redisResult = handler.result();
				if (StringUtils.isNotBlank(redisResult)) {
					String repeatRecivedErrorMsg = "这个消息已发送过，禁止重复发送msgId=" + msgId;
					resultHandler.handle(Future.failedFuture(repeatRecivedErrorMsg));
					return;
				}
				resultHandler.handle(Future.succeededFuture());
			} else {
				resultHandler.handle(Future.failedFuture(handler.cause()));
			}
		});
	}

	/**
	 * 保存消息记录
	 */
	private void saveMsgRecord(JsonObject receiveMsg, Future<BaseResponse> checkFutrue) {
		String msgId = receiveMsg.getValue("msgId") + "";
		checkFutrue.setHandler(handler -> {
			if (handler.succeeded()) {
				MsgRecord msg = new MsgRecord();
				msg.setAmqpMsgId(msgId);
				msg.setChannel(channel);
				msg.setMsgBody(receiveMsg.toString());
				msg.setStatus(MsgStatusEnum.SUCCESS.getCode());
				msgRecordService.addMessage(msg, res -> {
					if (res.succeeded()) {
						logger.info("保存消息成功msgId=" + msgId);
					} else {
						logger.info("保存消息失败msgId=" + msgId, res.cause());
					}
				});
			} else {
				logger.error("数据验证未通过", checkFutrue.cause());
			}
		});

	}

	// 推送给下游
	private void pushMsgToDownStream(JsonObject receiveMsg, Handler<AsyncResult<BaseResponse>> resultHandler) {
		String apnsToken = receiveMsg.getString("apnsToken");
		String phone = receiveMsg.getString("phone");

		if (!StringUtil.isNullOrEmpty(apnsToken)) {
			this.pushByApns(receiveMsg, resultHandler);
			return;
		}

		Map<String, String> param = new HashMap<>();
		param.put("phone", phone);
		Future<List<DeviceDto>> deviceFuture = Future.future();
		deviceService.queryDevices(param, deviceFuture.completer());
		deviceFuture.setHandler(devRes -> {
			if (devRes.succeeded()) {
				List<DeviceDto> list = devRes.result();
				if (CollectionUtils.isNotEmpty(list)) {
					// 库里load出来的token
					String loadToken = list.get(0).getDeviceToken();
					channel = list.get(0).getChannel();
					// 如果渠道是ISO 并且token不为空，走APNS推送
					if (PushTypeEnum.APNS.getSrcCode() == channel && !StringUtil.isNullOrEmpty(loadToken)) {
						receiveMsg.put("apnsToken", loadToken);
						this.pushByApns(receiveMsg, resultHandler);
					} else {
						this.pushBySocketOrOther(loadToken, receiveMsg, resultHandler);
					}
				} else {
					this.pushBySocketOrOther(null, receiveMsg, resultHandler);
				}
			} else {
				resultHandler.handle(Future.failedFuture(devRes.cause()));
			}
		});
	}

	/**
	 * @param loadToken
	 *            查出来的token
	 * @param receiveMsg
	 *            接收的消息
	 * @param resultHandler
	 */
	private void pushBySocketOrOther(String loadToken, JsonObject receiveMsg,
			Handler<AsyncResult<BaseResponse>> resultHandler) {
		Integer customerId = receiveMsg.getInteger("customerId");
		Map<String, String> params = new HashMap<>();
		params.put("uid", customerId + "");

		Future<Boolean> socketFuture = Future.future();
		this.getSocketCheckResult(params, socketFuture.completer());

		socketFuture.setHandler(socketHandler -> {
			boolean isUseSocket = false;
			if (socketHandler.succeeded()) {
				isUseSocket = socketHandler.result();
			}
			// 推socket 或者 android
			pushMessage2AndroidDevice(loadToken, receiveMsg, isUseSocket, resultHandler);
		});

	}

	private void pushByApns(JsonObject receiveMsg, Handler<AsyncResult<BaseResponse>> resultHandler) {
		logger.info("开始走apns推送");
		// 上报要用token
		token = receiveMsg.getString("apnsToken");
		channel = PushTypeEnum.APNS.getSrcCode();
		applePushService.sendMsg(receiveMsg, resultHandler);
	}

	private void getSocketCheckResult(Map<String, String> params, Handler<AsyncResult<Boolean>> resultHandler) {

		WebClient webClient = WebClient.create(vertx);
		webClient.postAbs(config.getString("socket.valid.url"))
				.putHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
				.addQueryParam("uid", params.get("uid")).send(responseHandler -> {
					if (responseHandler.succeeded()) {
						JsonObject checkSocket = responseHandler.result().bodyAsJsonObject();
						logger.info("socket状态检查返回结果：" + checkSocket);
						if (checkSocket == null) {
							resultHandler.handle(Future.succeededFuture(false));
							return;
						}
						String returnCode = checkSocket.getString("returnCode");
						String isValid = checkSocket.getString("isValid");
						if ("0".equals(returnCode) && "1".equals(isValid)) {
							resultHandler.handle(Future.succeededFuture(true));
						} else {
							logger.info("检测到socket未连接，" + Json.encode(checkSocket));
							resultHandler.handle(Future.succeededFuture(false));
						}
					} else {
						logger.error("sokcet状态检查异常" + responseHandler.cause());
						resultHandler.handle(Future.succeededFuture(false));
					}
				});

	}

	/**
	 * 选择推送渠道进行推送
	 *
	 * @param isSocket
	 * @param resultHandler
	 */
	private void pushMessage2AndroidDevice(String loadToken, JsonObject receiveMsg, boolean isSocket,
			Handler<AsyncResult<BaseResponse>> resultHandler) {

		if (isSocket) {
			logger.info("开始走socket推送");
			socketPushService.sendMsg(receiveMsg, resultHandler);
			channel = PushTypeEnum.SOCKET.getSrcCode();
			return;
		}

		// 接收到的消息中有传token
		if (!StringUtils.isBlank(token)) {
			// 只用作对安卓手机进行推送,目前没有gcm的推送逻辑
			logger.info("开始走小米推送");
			receiveMsg.put("regId", token);
			xiaomiPushService.sendMsg(receiveMsg, resultHandler);
			channel = PushTypeEnum.XIAOMI.getSrcCode();
			return;
		}

		// 消息中没有传token，但从库中查出了token
		if (StringUtils.isNotBlank(loadToken)) {
			// 只用作对安卓手机进行推送,目前没有gcm的推送逻辑
			logger.info("开始走小米推送");
			receiveMsg.put("regId", loadToken);
			xiaomiPushService.sendMsg(receiveMsg, resultHandler);
			channel = PushTypeEnum.XIAOMI.getSrcCode();
		} else {
			String errorMsg = "设备token不存在,推送操作不执行";
			logger.error(errorMsg);
			resultHandler.handle(Future.failedFuture(errorMsg));
		}

	}

	/**
	 * 推送成功的消息保存到redis中
	 * 
	 * @param expireTime
	 *
	 * @param resultHandler
	 */
	private void setMsgToRedis(String msgId, String customerId, Long expireTime,
			Handler<AsyncResult<Void>> resultHandler) {
		String redisMsgKey = PushConsts.AD_PASSENGER_MSG_PREFIX + msgId + "_" + customerId;
		long expire = (expireTime - System.currentTimeMillis()) / 1000;
		redisService.setEx(redisMsgKey, expire, msgId, res -> {
			if (res.succeeded()) {
				resultHandler.handle(Future.succeededFuture());
			} else {
				String errorMsg = "fail to set expire for message : key = " + redisMsgKey;
				logger.error(errorMsg, res.cause());
				resultHandler.handle(Future.failedFuture(res.cause()));
			}
		});
	}

	private void setMessageExpire2Redis(String redisMsgKey, Long expireTime, Handler<AsyncResult<Void>> resultHandler) {
		long expire = (expireTime - System.currentTimeMillis()) / 1000;
		redisService.expire(redisMsgKey, expire, res -> {
			if (res.succeeded()) {
				resultHandler.handle(Future.succeededFuture());
			} else {
				String errorMsg = "fail to set expire for message : key = " + redisMsgKey;
				logger.error(errorMsg, res.cause());
				resultHandler.handle(Future.failedFuture(res.cause()));
			}
		});

	}
}
