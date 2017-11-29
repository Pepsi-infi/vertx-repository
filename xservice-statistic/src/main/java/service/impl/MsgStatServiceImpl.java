package service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import constants.CacheConstants;
import constants.OsTypeEnum;
import constants.PushActionEnum;
import helper.XProxyHelper;
import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import iservice.MsgStatService;
import iservice.dto.MsgStatDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import redis.RedisCluster;
import redis.RedisClusterOptions;
import rxjava.BaseServiceVerticle;
import util.ConfigUtil;
import utils.BaseResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lufei Date : 2017/7/25 14:54 Description :
 */
public class MsgStatServiceImpl extends BaseServiceVerticle implements MsgStatService {
	private static final Logger logger = LoggerFactory.getLogger(MsgStatServiceImpl.class);

	private RedisCluster redisClient;

	public MsgStatServiceImpl() {

	}

	@Override
	public void start() throws Exception {
		super.start();

		XProxyHelper.registerService(MsgStatService.class, vertx.getDelegate(), this, MsgStatService.SERVICE_ADDRESS);
		publishEventBusService(MsgStatService.SERVICE_NAME, MsgStatService.SERVICE_ADDRESS, MsgStatService.class);

		RedisClusterOptions redisClusterOptions = ConfigUtil.getRedisClusterOptions(config().getJsonObject("redis"));
		redisClient = RedisCluster.create(vertx.getDelegate(), redisClusterOptions);
	}

	private void statSinglePushMsg(MsgStatDto msgStatDto, Handler<AsyncResult<Map>> result) {
		//如果msgId为空或者不是纯数字，不做处理
		if(!isAllowMsgId(msgStatDto)){
			result.handle(Future.failedFuture("非广告类消息msgId不做处理"));
			return;
		}
		//构建统计redisKey
		String msgSendKey = CacheConstants.getPushMsgKey(msgStatDto);
		//采用的是redis的hash数据结构做的数据上报，fields中存放的是同一个key中的所有field集合
		List<String> fields = getFieldsForMsgStat(msgStatDto);
		if (CollectionUtils.isEmpty(fields)) {
			logger.warn("the msgStat:{} need stat is null ", msgStatDto);
			result.handle(Future.failedFuture("the msgStat :" + msgStatDto + "need stat is null"));
		} else if (PushActionEnum.SEND.getType() != msgStatDto.getAction()
				&& StringUtils.isEmpty(msgStatDto.getAntFingerprint())) {
			logger.warn("the antFingerprint of msgStat:{} is null ", msgStatDto);
			result.handle(Future.failedFuture("the antFingerprint of msgStat is null"));
		} else {
			List<Future> futures = Lists.newArrayList();
			// 设置过期时间7天
			Future<Long> expireFuture = Future.future();
			futures.add(expireFuture);
			redisClient.expire(msgSendKey, 86400 * 7, expireFuture.completer());
			expireFuture.setHandler(handler -> {
				if (handler.succeeded()) {
					logger.info("key : {} for  msgStatDto :{} set expire success : {} .", msgSendKey, msgStatDto,
							handler.result());
				} else {
					logger.error("key : {} for  msgStatDto :{} set expire error : {} .", msgSendKey, msgStatDto,
							handler.cause());
				}
			});
			for (String field : fields) {
				Future<Long> fieldSetFuture = Future.future();
				futures.add(fieldSetFuture);
				//对同一个key中的不同属性字段执行上报+1的操作  msgSendKey=CacheConstants.getPushMsgKey(msgStatDto); field=getFieldsForMsgStat(msgStatDto); 1.推送 2.到达 3.点击
				redisClient.hincrby(msgSendKey, field, 1, fieldSetFuture.completer());
				fieldSetFuture.setHandler(handler -> {
					if (handler.succeeded()) {
						logger.info("stat msgStatDto : {}  by filed :{} success.", msgStatDto, field);
					} else {
						logger.error("stat msgStatDto : {} by filed :{} error.", msgStatDto, field, handler.cause());
					}
				});
			}
			CompositeFuture compositeFuture = CompositeFuture.all(futures);
			compositeFuture.setHandler(res -> {
				if (res.succeeded()) {
					result.handle(Future.succeededFuture());
				} else {
					logger.error(res.cause());
					result.handle(Future.failedFuture(res.cause()));
				}
			});

			redisClient.sadd(CacheConstants.getAllPushMsgKey(), msgSendKey, ar -> {
				if (ar.succeeded()) {
					logger.info("add  pushMsgKey:{} to redis  success.", msgSendKey);
				} else {
					logger.error("add  pushMsgKey:{} to redis  error.", msgSendKey, ar.cause());
				}
			});

		}
	}

	@Override
	public void statPushMsg(List<MsgStatDto> msgStatDtos, Handler<AsyncResult<String>> result) {
		logger.info("[MsgStatServiceImpl] the need msgStatDtos: " + Json.encode(msgStatDtos));
		List<Future> pushFutureList = new ArrayList<>();
		Map<Future<Map>, String> futureMap = Maps.newHashMap();
		for (MsgStatDto msgStatDto : msgStatDtos) {
			Future<Map> msgStatDtoFuture = Future.future();
			pushFutureList.add(msgStatDtoFuture);
			futureMap.put(msgStatDtoFuture, msgStatDto.getMsgId());
			statSinglePushMsg(msgStatDto, msgStatDtoFuture.completer());
		}
		CompositeFuture compositeFuture = CompositeFuture.all(pushFutureList);
		compositeFuture.setHandler(res -> {
			if (res.succeeded()) {
				result.handle(Future.succeededFuture(Json.encode(buildSuccessResponse())));
			} else {
				Map responseMap = Maps.newHashMap();
				responseMap.put("status", BaseResponse.RESPONSE_FAIL_CODE);
				List<Map> list = Lists.newArrayList();
				for (int i = 0; i < pushFutureList.size(); i++) {
					Map msgMap = Maps.newHashMap();
					msgMap.put("msgId", futureMap.get(pushFutureList.get(i)));
					list.add(msgMap);
				}
				responseMap.put("msgList", list);
				result.handle(Future.succeededFuture(Json.encode(responseMap)));
			}
		});
	}

	private List<String> getFieldsForMsgStat(MsgStatDto msgStatDto) {
		List<String> fieldsList = Lists.newArrayList();
		if (PushActionEnum.SEND.getType() == msgStatDto.getAction()) {
			fieldsList.add(CacheConstants.PUSH_SEND_SUM);
			if (msgStatDto.getOsType() != null && msgStatDto.getOsType() > 0) {
				String filed = new StringBuilder(CacheConstants.PUSH_SEND_OSTYPE).append(msgStatDto.getOsType())
						.toString();
				fieldsList.add(filed);
			}
			if (msgStatDto.getChannel() != null && msgStatDto.getChannel() > 0) {
				String filed = new StringBuilder(CacheConstants.PUSH_SEND_CHANNEL).append(msgStatDto.getChannel())
						.toString();
				fieldsList.add(filed);
			}
			// IOS 发送即到达
			if (OsTypeEnum.IOS.getType() == msgStatDto.getOsType()) {
				fieldsList.add(CacheConstants.PUSH_ARRIVE_SUM);
				fieldsList.add(new StringBuilder(CacheConstants.PUSH_ARRIVE_OSTYPE).append(OsTypeEnum.IOS.getType())
						.toString());
			}
		} else if (PushActionEnum.ARRIVE.getType() == msgStatDto.getAction()) {
			fieldsList.add(CacheConstants.PUSH_ARRIVE_SUM);
			if (msgStatDto.getOsType() != null && msgStatDto.getOsType() > 0) {
				String filed = new StringBuilder(CacheConstants.PUSH_ARRIVE_OSTYPE).append(msgStatDto.getOsType())
						.toString();
				fieldsList.add(filed);
			}
			if (msgStatDto.getChannel() != null && msgStatDto.getChannel() > 0) {
				String filed = new StringBuilder(CacheConstants.PUSH_ARRIVE_CHANNEL).append(msgStatDto.getChannel())
						.toString();
				fieldsList.add(filed);
			}
		} else if (PushActionEnum.CLICK.getType() == msgStatDto.getAction()) {
			fieldsList.add(CacheConstants.PUSH_CLICK_SUM);
			if (msgStatDto.getOsType() != null && msgStatDto.getOsType() > 0) {
				String filed = new StringBuilder(CacheConstants.PUSH_CLICK_OSTYPE).append(msgStatDto.getOsType())
						.toString();
				fieldsList.add(filed);
			}
			if (msgStatDto.getChannel() != null && msgStatDto.getChannel() > 0) {
				String filed = new StringBuilder(CacheConstants.PUSH_CLICK_CHANNEL).append(msgStatDto.getChannel())
						.toString();
				fieldsList.add(filed);
			}
		}
		logger.info("the msgStatDto : {} need stat fields : {}", msgStatDto, Json.encode(fieldsList));
		return fieldsList;
	}

	private Map<String, Object> buildSuccessResponse() {
		Map map = Maps.newHashMap();
		map.put("status", BaseResponse.RESPONSE_SUC_CODE);
		return map;
	}

	private boolean isAllowMsgId(MsgStatDto msgStatDto){
		if(msgStatDto != null){
			String msgId = msgStatDto.getMsgId();
			logger.info(msgId);
			//如果msgId为空或者不是纯数字，不做处理
			if(StringUtils.isNotBlank(msgId) && msgId.length() < 10 && msgId.matches("^[0-9]*$") ){
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		MsgStatDto msgStatDto = new MsgStatDto();
//		msgStatDto.setMsgId("113-1111");
	}
}
