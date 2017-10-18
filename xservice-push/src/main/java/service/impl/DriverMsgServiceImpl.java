package service.impl;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.KeyValue;
import org.apache.commons.lang.ArrayUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import constant.PushConsts;
import domain.DriverMsg;
import domain.Page;
import enums.ErrorCodeEnum;
import enums.MessageTypeEnum;
import helper.XProxyHelper;
import io.netty.util.internal.StringUtil;
import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.sql.UpdateResult;
import result.ResultData;
import serializer.ByteUtils;
import service.DriverMsgService;
import service.DriverService;
import service.RedisService;
import util.DateUtil;
import xservice.BaseServiceVerticle;

public class DriverMsgServiceImpl extends BaseServiceVerticle implements DriverMsgService {

	private final Logger logger = LoggerFactory.getLogger(DriverMsgServiceImpl.class);

	private SQLClient sqlClient;

	private RedisService redisService;

	private DriverService driverService;

	// 下游地址列表
	private List<KeyValue> hostList = new ArrayList<>();

	// 每次批量处理条数
	public static final int BATCH_UPDATE_SIZE = 1000;

	private MongoClient mongoClient;

	@Override
	public void start() throws Exception {
		super.start();
		XProxyHelper.registerService(DriverMsgService.class, vertx, this, DriverMsgService.class.getName());

		JsonObject mysqlOptions = config().getJsonObject("mysql.config");
		sqlClient = MySQLClient.createNonShared(vertx, mysqlOptions);

		JsonObject jsonObject = config().getJsonObject("mongo.config");
		mongoClient = MongoClient.createShared(vertx, jsonObject);

		redisService = RedisService.createProxy(vertx);
		driverService = DriverService.createProxy(vertx);
		// 加载下游地址
		this.initSendTo();
	}

	private void initSendTo() {
		// 当前verticle加载时从配置文件中读取下游SOCKET的地址列表
		String socketAddrs = config().getJsonObject("push.config").getString("UDP_HOSTS");
		logger.info(" upstream socket addr : [" + socketAddrs + "]");
		String[] addrArray = socketAddrs.split(",");
		if (ArrayUtils.isNotEmpty(addrArray)) {
			for (String addr : addrArray) {
				final String[] host = addr.split(":");
				KeyValue kv = new KeyValue() {
					@Override
					public Object getKey() {
						return host[0];
					}

					@Override
					public Object getValue() {
						return host[1];
					}
				};
				hostList.add(kv);
			}
		}
	}

	public interface Sql {

		String ADD = "insert into driver_msg "
				+ "(title,synopsis,content,is_shells_screen,msg_type,jump_url,is_important,create_user,update_user,create_time,update_time) "
				+ "values " + "(?,?,?,?,?,?,?,'admin','admin',now(),now())";

		String SELECT_PAGE = "select "
				+ "a.id,a.title,a.synopsis,a.content,a.is_shells_screen as isShellsScreen,a.`status`,a.msg_type as msgType,a.jump_url as jumpUrl,a.is_important as isImportant,a.enabled,a.create_user as createUser,a.update_user as updateUser,a.create_time as createTime,a.update_time as updateTime,"
				+ "SUM(CASE when b.`status`=1 THEN 1 else 0 END) as readNum,SUM(CASE when b.`status`=0 THEN 1 else 0 END) as unReadNum "
				+ "from driver_msg a left join driver_msg_item b on a.id=b.driver_msg_id " + "where 1=1 %s";

		String SELECT_COUNT = "select count(1) from driver_msg where 1=1 %s";

		String SELECT_ONE = "select "
				+ "id,title,synopsis,content,is_shells_screen as isShellsScreen,`status`,msg_type as msgType,jump_url as jumpUrl,is_important as isImportant,enabled,create_user as createUser,update_user as updateUser,create_time as createTime,update_time as updateTime "
				+ "from driver_msg where 1=1 and id=? ";

		String ADD_BATCH = "insert into driver_msg_item "
				+ "(driver_msg_id,driver_id,`status`,create_time,update_time) " + "values " + "(?,?,?,now(),now())";

		String SIMPLE_ADD_BATCH = "insert into driver_msg_item "
				+ "(driver_msg_id,driver_id,`status`,create_time,update_time) " + "values ";

	}

	@Override
	public void addDriverMsg(JsonObject dto, Handler<AsyncResult<UpdateResult>> resultHandler) {

		JsonArray params = new JsonArray();
		params.add(dto.getString("title")).add(dto.getString("synopsis")).add(dto.getString("content"))
				.add(Integer.valueOf(dto.getString("isShellsScreen"))).add(Integer.valueOf(dto.getString("msgType")))
				.add(StringUtil.isNullOrEmpty(dto.getString("jumpUrl")) ? "" : dto.getString("jumpUrl"))
				.add(Integer.valueOf(dto.getString("isImportant")));

		Future<UpdateResult> addFuture = this.add(Sql.ADD, params);
		addFuture.setHandler(handler -> {
			if (handler.succeeded()) {
				resultHandler.handle(Future.succeededFuture(handler.result()));
			} else {
				logger.error("数据新增/更新失败", handler.cause());
				resultHandler.handle(Future.failedFuture(handler.cause()));

			}
		});

	}

	private Future<UpdateResult> add(String sql, JsonArray params) {
		return getConnection().compose(conn -> {
			Future<UpdateResult> future = Future.future();
			conn.updateWithParams(sql, params, res -> {
				if (res.succeeded()) {
					future.complete(res.result());

				} else {
					future.fail(res.cause());
				}
				conn.close();
			});
			return future;
		});
	}

	public void selectByPage(JsonObject dto, Handler<AsyncResult<String>> resultHandler) {

		JsonArray params = new JsonArray();
		Integer pageNumber = (StringUtil.isNullOrEmpty(dto.getString("page")) || "0".equals(dto.getString("page"))) ? 1
				: Integer.valueOf(dto.getString("page"));// 页码有问题默认传第一页
		Integer pageSize = (StringUtil.isNullOrEmpty(dto.getString("size")) || "0".equals(dto.getString("size"))) ? 10
				: Integer.valueOf(dto.getString("size"));// 页码有问题默认传第一页

		String countSql = buildSql(Sql.SELECT_COUNT, dto, params);
		Future<Long> countFuture = this.queryTotalCount(countSql, params);

		String pageSql = buildPageSql(Sql.SELECT_PAGE, dto, params);
		Future<List<JsonObject>> listFuture = this.queryList(pageSql, params);

		this.buildPageResult(pageNumber, pageSize, countFuture, listFuture, resultHandler);

	}

	private void buildPageResult(Integer pageNumber, Integer pageSize, Future<Long> countFuture,
			Future<List<JsonObject>> listFuture, Handler<AsyncResult<String>> resultHandler) {
		CompositeFuture comFuture = CompositeFuture.all(countFuture, listFuture);
		comFuture.setHandler(handler -> {
			if (handler.succeeded()) {
				Long totalCount = handler.result().resultAt(0);

				List<JsonObject> jsonList = handler.result().resultAt(1);
				List<DriverMsg> dtoList = Lists.transform(jsonList, new Function<JsonObject, DriverMsg>() {
					@Nullable
					@Override
					public DriverMsg apply(@Nullable JsonObject jsonObject) {
						return jsonObject.mapTo(DriverMsg.class);
					}
				});
				Page page = new Page(pageNumber, pageSize, dtoList, totalCount);

				resultHandler.handle(Future.succeededFuture(new ResultData<>(ErrorCodeEnum.SUCCESS, page).toString()));

			} else {
				logger.error("司机消息分页失败", handler.cause());
				resultHandler.handle(Future.failedFuture(handler.cause()));
			}
		});

	}

	private Future<List<JsonObject>> queryList(String pageSql, JsonArray params) {
		return getConnection().compose(conn -> {
			Future<List<JsonObject>> future = Future.future();
			conn.queryWithParams(pageSql, params, res -> {
				if (res.succeeded()) {
					List<JsonObject> list = res.result().getRows();
					future.complete(list);
				} else {
					logger.error("queryList is error:", res.cause());
					future.fail(res.cause());
				}
				conn.close();
			});
			return future;
		});
	}

	private Future<Long> queryTotalCount(String countSql, JsonArray params) {

		return getConnection().compose(conn -> {
			Future<Long> future = Future.future();
			conn.queryWithParams(countSql, params, res -> {
				if (res.succeeded()) {
					List<JsonObject> list = res.result().getRows();
					Long totalCount = list.get(0).getLong("count(1)");
					future.complete(totalCount);
				} else {
					future.fail(res.cause());
				}
				conn.close();
			});
			return future;
		});
	}

	private String buildPageSql(String sql, JsonObject dto, JsonArray params) {

		if (dto == null) {
			return String.format(sql, "");
		}

		StringBuffer sb = new StringBuffer();
		String title = dto.getString("title");
		Integer msgType = StringUtil.isNullOrEmpty(dto.getString("msgType")) ? null
				: Integer.valueOf(dto.getString("msgType"));
		String startTime = dto.getString("startTime");
		String endTime = dto.getString("endTime");
		if (!StringUtil.isNullOrEmpty(title)) {
			sb.append("and a.title=? ");
		}
		if (null != msgType) {
			sb.append("and a.msg_type=? ");
		}
		if (!StringUtil.isNullOrEmpty(startTime)) {
			sb.append("and a.create_time >=? ");
		}
		if (!StringUtil.isNullOrEmpty(endTime)) {
			sb.append("and a.create_time <=? ");
		}
		Integer pageNumber = (StringUtil.isNullOrEmpty(dto.getString("page")) || "0".equals(dto.getString("page"))) ? 1
				: Integer.valueOf(dto.getString("page"));// 页码有问题默认传第一页
		Integer pageSize = (StringUtil.isNullOrEmpty(dto.getString("size")) || "0".equals(dto.getString("size"))) ? 10
				: Integer.valueOf(dto.getString("size"));// 页码有问题默认传第一页
		int pageBegin = (pageNumber - 1) * pageSize;
		int offset = pageSize;
		sb.append("group by a.id ");
		sb.append("order by a.update_time desc ").append("limit ").append(pageBegin).append(",").append(offset);

		return String.format(sql, sb.toString());
	}

	private String buildSql(String sql, JsonObject dto, JsonArray params) {
		if (dto == null) {
			return String.format(sql, "");
		}
		StringBuffer sb = new StringBuffer();
		String title = dto.getString("title");
		Integer msgType = StringUtil.isNullOrEmpty(dto.getString("msgType")) ? null
				: Integer.valueOf(dto.getString("msgType"));
		String startTime = dto.getString("startTime");
		String endTime = dto.getString("endTime");
		if (!StringUtil.isNullOrEmpty(title)) {
			sb.append("and title=? ");
			params.add(title);
		}
		if (null != msgType) {
			sb.append("and msg_type=? ");
			params.add(msgType);
		}
		if (!StringUtil.isNullOrEmpty(startTime)) {
			sb.append("and create_time >=? ");
			params.add(startTime);
		}
		if (!StringUtil.isNullOrEmpty(endTime)) {
			sb.append("and create_time <=?");
			params.add(endTime);
		}
		return String.format(sql, sb.toString());
	}

	protected Future<SQLConnection> getConnection() {
		Future<SQLConnection> future = Future.future();
		sqlClient.getConnection(future.completer());
		return future;
	}

	@Override
	public void getDriverMsgDetail(Long id, Handler<AsyncResult<JsonObject>> resultHandler) {
		JsonArray params = new JsonArray();
		params.add(id);
		Future<JsonObject> future = this.getOne(Sql.SELECT_ONE, params);
		future.setHandler(handler -> {
			if (handler.succeeded()) {
				resultHandler.handle(Future.succeededFuture(handler.result()));
			} else {
				resultHandler.handle(Future.failedFuture(handler.cause()));
			}
		});
	}

	private Future<JsonObject> getOne(String sql, JsonArray params) {
		return getConnection().compose(conn -> {
			Future<JsonObject> future = Future.future();
			conn.queryWithParams(sql, params, res -> {
				if (res.succeeded()) {
					List<JsonObject> list = res.result().getRows();
					if (list != null && list.size() > 0) {
						future.complete(list.get(0));
					} else {
						future.complete(null);
					}
				} else {
					future.fail(res.cause());
				}
				conn.close();
			});
			return future;
		});
	}

	private Future<String> sendDriverMsg(Map<String, String> driverMsg) {
		Future<String> sendFuture = Future.future();
		if (driverMsg == null) {
			logger.error("sendDriverMsg error:driverMsg is null");
			sendFuture.fail("fail");
			return sendFuture;
		}

		Future<Integer> chatUserIdFuture = this.getMsgDestination(driverMsg);
		// Future<Integer> chatUserId = this.buildPushDestination(toFuture);
		sendFuture = this.sendDriverMsg(driverMsg, chatUserIdFuture);

		return sendFuture;

	}

	private Future<String> sendDriverMsg(Map<String, String> driverMsg, Future<Integer> chatUserIdFuture) {
		return chatUserIdFuture.compose(to -> {
			if (to != null) {
				driverMsg.remove("userId");
				driverMsg.remove("userType");
				driverMsg.put("chatUserId", to.toString());
			} else {
				driverMsg.put("chatUserId", null);
			}
			String msg = null;
			try {
				msg = new ObjectMapper().writeValueAsString(driverMsg);
			} catch (JsonProcessingException e) {
				logger.error("parse exception", e);

			}
			return this.sendMsg(MessageTypeEnum.COMPANY_MESSAGE_PUSH, to, msg, null, null);

		});
	}

	private Future<String> sendMsg(MessageTypeEnum messageTypeEnum, Integer to, String msg, String from,
			Map<String, Object> otherParams) {
		Future<String> sendFuture = Future.future();

		if (StringUtil.isNullOrEmpty(msg)) {
			sendFuture.fail("send msg is null");
			return sendFuture;
		}

		String msgId = createMsgId();
		JsonObject chatMsgVO = new JsonObject();
		chatMsgVO.put("from", from);
		chatMsgVO.put("to", to);
		chatMsgVO.put("msgTitle", messageTypeEnum.getName());
		chatMsgVO.put("msgBody", msg);
		chatMsgVO.put("type", messageTypeEnum.getType());
		chatMsgVO.put("otherParams", from);

		// 302(司机抢单)，306(今日在线时长)，309(司机状态)，310(圈里圈外),些消息列表不需要进行生发
		if (isCanInbox(messageTypeEnum.getType())) {
			// 406(公司消息)不在其中不会执行
			this.setMsg2Redis(to, msgId, chatMsgVO);
		}

		// 通知接收人
		sendFuture = this.sendMsg(to, messageTypeEnum.getName(), msg, messageTypeEnum.getType(), from, otherParams,
				msgId);

		// 如果是心跳消息进行补发直600/30 = 20在网张状态好的情部下
		if (messageTypeEnum.getType() == messageTypeEnum.WORKTIME.getType()) {
			// TODO 补发逻辑需要加上
			reSendMsg(to, msgId);
		}

		return sendFuture;
	}

	private void reSendMsg(Integer to, String msgId) {
		// TODO Auto-generated method stub

	}

	private Future<String> sendMsg(Integer to, String title, String body, int type, String from,
			Map<String, Object> otherParams, String msgId) {
		Map<String, Object> msgInfo = new HashMap<String, Object>();
		List<Object> params = new ArrayList<Object>();
		try {
			msgInfo.put("nick", from);
			msgInfo.put("msgId", msgId);
			msgInfo.put("title", title);
			msgInfo.put("body", body);

			if (otherParams != null) {
				msgInfo.putAll(otherParams);
				otherParams.clear();
			}
			params.add(to);
			params.add(type);
			params.add(0);
			params.add(msgInfo);
			return msgAsyncCall("sendmsg", params);
		} finally {
			clearData(msgInfo, params);
		}

	}

	private Future<String> msgAsyncCall(String method, List<Object> list) {
		Future<String> sendFuture = Future.future();
		Map<String, Object> params = new HashMap<String, Object>();
		DatagramSocket client = null;
		try {
			KeyValue host = getPollHost();
			InetAddress addr = InetAddress.getByName((String) host.getKey());
			int port = Integer.valueOf((String) host.getValue());
			params.put("method", method);
			params.put("params", list);
			byte[] sendBuf = ByteUtils.objectToByte(params);
			client = new DatagramSocket();
			DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, addr, port);
			logger.info(method + " [] udp host:" + addr.getHostAddress() + " " + port + "  msg:" + new String(sendBuf, "UTF-8"));
			client.send(sendPacket);
			sendFuture.complete("success");
		} catch (Exception e) {
			logger.error("udp send msg error:", e);
			sendFuture.fail(e);
		} finally {
			if (client != null) {
				client.close();
			}
			clearData(params);
		}
		return sendFuture;

	}

	private void clearData(final Object... datas) {
		try {
			for (Object m : datas) {
				if (m != null) {
					if (m instanceof Map) {
						((Map) m).clear();
					} else if (m instanceof List) {
						((List) m).clear();
					}
				}
			}
		} catch (Exception e) {
			logger.error("clear data error:", e);
		}

	}

	// 轮询指针
	private static Integer pos = 0;

	/**
	 * 轮询取出地址
	 *
	 * @return
	 */
	private KeyValue getPollHost() {
		KeyValue host = null;
		if (CollectionUtils.isNotEmpty(hostList)) {
			if (pos >= hostList.size()) {
				pos = 0;
			}
			host = hostList.get(pos);
			pos++;
		}
		return host;
	}

	private void setMsg2Redis(Integer to, String msgId, JsonObject chatMsgVO) {
		Future<Long> rpushFuture = Future.future();
		redisService.rpush("MSGLIST" + to, msgId, rpushFuture.completer());

		Future<String> setFuture = Future.future();
		redisService.setEx(msgId, 600l, chatMsgVO.toString(), setFuture.completer());

		Future<CompositeFuture> future = CompositeFuture.all(rpushFuture, setFuture);
		future.setHandler(handler -> {
			if (handler.succeeded()) {
				logger.info("setMsg2Redis success");
			} else {
				logger.error("setMsg2Redis error", handler.cause());
			}
		});

	}

	/**
	 * 判断是否可以进消息列表
	 * 
	 * @param type
	 * @return
	 */
	private boolean isCanInbox(int type) {
		// 302(司机抢单)，306(今日在线时长)，309(司机状态)，310(圈里圈外),些消息列表不需要进行生发
		Integer[] types = { MessageTypeEnum.ROBORDERMSG.getType(), MessageTypeEnum.WORKTIME.getType(),
				MessageTypeEnum.DRIVERSTATUS.getType(), MessageTypeEnum.CIRCLEINNEROUTER.getType() };
		if (!Arrays.asList(types).contains(type)) {
			return true;
		}
		return false;
	}

	private String createMsgId() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");

	}

	private Future<Integer> buildPushDestination(Future<String> toFuture) {
		return toFuture.compose(msgRes -> {
			Future<Integer> chatUserId = Future.future();

			JsonObject json = new JsonObject(msgRes);
			chatUserId.complete(json.getInteger("chatUserId"));
			return chatUserId;

		});
	}

	private Future<Integer> getMsgDestination(Map<String, String> driverMsg) {
		Integer to = Integer.parseInt(driverMsg.get("userId"));
		Integer userType = Integer.parseInt(driverMsg.get("userType"));
		String key = "CU" + to + userType;
		Future<String> redisFuture = Future.future();
		Future<Integer> chatFuture = Future.future();
		redisService.get(key, redisFuture.completer());
		redisFuture.setHandler(handler -> {
			if (handler.succeeded()) {
				String res = handler.result();
				if (!StringUtil.isNullOrEmpty(res)) {
					JsonObject json = new JsonObject(res);
					// TODO ChatUserID有张表的逻辑需要处理
					chatFuture.complete(json.getInteger("chatUserId"));
				} else {
					chatFuture.complete(to);
				}
			} else {
				logger.error("get chatUserId error:", handler.cause());
				chatFuture.fail(handler.cause());
			}

		});
		return chatFuture;
	}

	@Override
	public void addDriverMsgItems(JsonObject dto, Handler<AsyncResult<JsonObject>> resultHandler) {

		// 构建查询司机信息请求参数
		JsonObject query = buildJsonQuery(dto);

		// 获取司机总数
		Future<Long> countFuture = this.queryDriverCount(query);

		Future<JsonObject> batchFuture = Future.future();
		this.addBatchMsgItem(query, dto, dto.getInteger("driverMsgId"), countFuture, batchFuture.completer());

		batchFuture.setHandler(handler -> {
			if (handler.succeeded()) {
				resultHandler.handle(Future.succeededFuture(handler.result()));
			} else {
				resultHandler.handle(Future.failedFuture(handler.cause()));
			}
		});
	}

	private void addBatchMsgItem(JsonObject query, JsonObject dto, Integer driverMsgId, Future<Long> countFuture,
			Handler<AsyncResult<JsonObject>> handler) {

		countFuture.setHandler(countHandler -> {

			if (countHandler.succeeded()) {

				long totalCount = countHandler.result();
				// 批量保存消息，批量发送至司机端
				this.addBatchMsgAndSend2DriverClient(totalCount, query, dto, driverMsgId, handler);

			} else {
				logger.error("司机总数获取异常", countHandler.cause());
				handler.handle(Future.failedFuture(countHandler.cause()));
			}

		});

	}

	@SuppressWarnings("rawtypes")
	private void addBatchMsgAndSend2DriverClient(long totalCount, JsonObject query, JsonObject dto, Integer driverMsgId,
			Handler<AsyncResult<JsonObject>> handler) {

		int listSize = (int) (totalCount % BATCH_UPDATE_SIZE == 0 ? totalCount / BATCH_UPDATE_SIZE
				: totalCount / BATCH_UPDATE_SIZE + 1);
		JsonArray driversArray = new JsonArray();
		// 批量新增Future集合
		List<Future> addFutureList = new ArrayList<>();
		// 批量发送消息Future集合
		List<Future> sendFutureList = new ArrayList<>();
		mongoClient.findBatch("driver", query, mongoRes -> {
			if (mongoRes.succeeded()) {
				Future<Integer> listFuture = Future.future();
				Future<Integer> totalSendFuture = Future.future();

				if (mongoRes.result() != null) {
					driversArray.add(mongoRes.result());
					if (driversArray.size() == BATCH_UPDATE_SIZE) {

						// 批量写入消息明细到DB
						listFuture = this.addDriverMsgByBatch(driversArray, driverMsgId);
						addFutureList.add(listFuture);

						// 批量push到司机端
						totalSendFuture = this.sendMsg(dto, driversArray);
						sendFutureList.add(totalSendFuture);

						// join 批量处理结果
						if (addFutureList.size() == listSize) {
							logger.info("addFutureList size=" + addFutureList.size());
							// this.dealAddListFutures(addFutureList,listSize,resultHandler);
							// join批量新增结果
							Future<Integer> addFuture = this.dealAddListFutures(addFutureList, listSize);
							// join批量发送结果
							Future<Integer> sendFuture = this.dealSendListFutures(sendFutureList, listSize);
							this.dealFuture(addFuture, sendFuture, handler);
						}

						driversArray.clear();

					}
				} else {

					// 批量写入消息明细到DB
					listFuture = this.addDriverMsgByBatch(driversArray, driverMsgId);
					addFutureList.add(listFuture);

					// 批量push到司机端
					totalSendFuture = this.sendMsg(dto, driversArray);
					sendFutureList.add(totalSendFuture);

					// join 批量处理结果
					if (addFutureList.size() == listSize) {
						logger.info("addFutureList size=" + addFutureList.size());
						// this.dealAddListFutures(addFutureList,listSize,resultHandler);
						Future<Integer> addFuture = this.dealAddListFutures(addFutureList, listSize);
						Future<Integer> sendFuture = this.dealSendListFutures(sendFutureList, listSize);
						this.dealFuture(addFuture, sendFuture, handler);
					}

					driversArray.clear();
				}
			} else {
				logger.error("mongo query error", mongoRes.cause());
				handler.handle(Future.failedFuture(mongoRes.cause()));
			}
		});

	}

	@SuppressWarnings("rawtypes")
	private Future<Integer> dealSendListFutures(List<Future> sendFutureList, int listSize) {
		CompositeFuture composeFuture = CompositeFuture.all(sendFutureList);
		return composeFuture.compose(result -> {
			Future<Integer> sendFuture = Future.future();
			int totalNums = 0;
			for (int i = 0; i < listSize; i++) {
				totalNums += result.resultAt(i) == null ? 0 : (Integer) result.resultAt(i);
			}
			logger.info("driver msg send  totalNums=" + totalNums);
			sendFuture.complete(totalNums);
			return sendFuture;

		});
	}

	@SuppressWarnings("rawtypes")
	private Future<Integer> dealAddListFutures(List<Future> addFutureList, int listSize) {
		CompositeFuture composeFuture = CompositeFuture.all(addFutureList);
		return composeFuture.compose(result -> {
			Future<Integer> addFuture = Future.future();
			int totalNums = 0;
			for (int i = 0; i < listSize; i++) {
				totalNums += result.resultAt(i) == null ? 0 : (Integer) result.resultAt(i);
			}
			logger.info("driver msg batch add  totalNums=" + totalNums);
			addFuture.complete(totalNums);
			return addFuture;

		});

	}

	private void dealFuture(Future<Integer> addFuture, Future<Integer> sendFuture,
			Handler<AsyncResult<JsonObject>> resultHandler) {
		CompositeFuture composeFuture = CompositeFuture.all(addFuture, sendFuture);
		composeFuture.setHandler(handler -> {
			if (handler.succeeded()) {

				int addNums = handler.result().resultAt(0);
				int sendNums = handler.result().resultAt(1);

				logger.info("司机消息发送成功,addNums=" + addNums + "~sendNums=" + sendNums);
				JsonObject json = new JsonObject();
				json.put("addNums", addNums);
				json.put("sendNums", sendNums);

				resultHandler.handle(Future.succeededFuture(json));

			} else {
				logger.error("join futures error", handler.cause());
				resultHandler.handle(Future.failedFuture(handler.cause()));
			}
		});

	}

	private Future<Integer> addDriverMsgByBatch(JsonArray driversArray, Integer driverMsgId) {
		// 1.司机分页数据
		logger.info("driversArray size=" + driversArray.size());
		Iterator<Object> iter = driversArray.iterator();

		// 2.组装批量消息明细
		List<JsonArray> msgItemList = new ArrayList<>();
		JsonObject driver;
		while (iter.hasNext()) {
			JsonArray itemMsg = new JsonArray();
			driver = (JsonObject) iter.next();
			itemMsg.add(driverMsgId);// 消息ID
			itemMsg.add(driver.getInteger("_id"));// 司机ID
			itemMsg.add(0);
			msgItemList.add(itemMsg);
		}
		// 3.批量写入数据库
		return this.addBatch(Sql.ADD_BATCH, msgItemList);
	}

	private Future<Integer> addBatch(String addBatch, List<JsonArray> msgItemList) {
		return getConnection().compose(conn -> {
			Future<Integer> listFuture = Future.future();
			StringBuffer sb = new StringBuffer();
			sb.append(Sql.SIMPLE_ADD_BATCH);
			for (JsonArray param : msgItemList) {
				String sql = buildSimpleSql(Sql.SIMPLE_ADD_BATCH, param);
				sb.append(sql);
			}
			sb.deleteCharAt(sb.lastIndexOf(","));
			String batchSql = sb.toString();
			conn.execute(batchSql, res -> {
				if (res.succeeded()) {
					listFuture.complete(msgItemList.size());
				} else {
					logger.error("司机消息明细批量入库异常", res.cause());
					listFuture.fail(res.cause());
				}
			});
			return listFuture;
		});

	}

	private String buildSimpleSql(String simpleAddBatch, JsonArray param) {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		sb.append(param.getInteger(0) + ",");
		sb.append(param.getInteger(1) + ",");
		sb.append(param.getInteger(2) + ",");
		sb.append("now(),now()),");
		return sb.toString();
	}

	private JsonObject buildJsonQuery(JsonObject dto) {
		JsonObject query = new JsonObject();
		String sendAll = dto.getString("sendAll");
		String driverIdsStr = dto.getString("driverIds");
		// supplierId=0 查询全部供应商
		String supplierId = dto.getString("providerId");
		// cityIds=0 查询全部城市
		String cityIdsStr = dto.getString("cityIds");

		if (PushConsts.COMPANY_MSG_SENDALL_TYPE_PROVIDER.equals(sendAll) || PushConsts.COMPANY_MSG_SENDALL_TYPE_CITY.equals(sendAll) || PushConsts.COMPANY_MSG_SENDALL_TYPE_ALLDRIVER.equals(sendAll)) {

			JsonArray cityIdsArray = new JsonArray();
			String[] cityIds = null;
			if (cityIdsStr.length() > 0) {
				cityIds = cityIdsStr.split("\\,");
				for (String cityId : cityIds) {

					cityIdsArray.add(Integer.valueOf(cityId));
				}
			}

			if (cityIdsArray.size() > 0) {
				query.put("cityId", new JsonObject().put("$in", cityIdsArray));
			}
			if (!StringUtil.isNullOrEmpty(supplierId)&&PushConsts.COMPANY_MSG_SENDALL_TYPE_PROVIDER.equals(sendAll)&&!"0".equals(supplierId)) {
				query.put("supplierId", Integer.valueOf(supplierId));
			}

			return query;
		}

		if (PushConsts.COMPANY_MSG_SENDALL_TYPE_DRIVERS.equals(sendAll) && !StringUtil.isNullOrEmpty(driverIdsStr) && driverIdsStr.length() > 0) {

			JsonArray driverIdsArray = new JsonArray();
			if (driverIdsStr.length() > 0) {
				String[] driverIds = driverIdsStr.split("\\,");
				for (String driverId : driverIds) {
					driverIdsArray.add(Integer.valueOf(driverId));
				}
			}

			if (driverIdsArray.size() > 0) {
				query.put("driverId", new JsonObject().put("$in", driverIdsArray));
			}
			return query;

		}

		return query;
	}

	private Future<Long> queryDriverCount(JsonObject query) {
		Future<Long> countFuture = Future.future();
		driverService.queryDriverCount(query, countFuture.completer());
		return countFuture;
	}

	private Future<JsonObject> drvierList(JsonObject dto) {
		String sendAll = dto.getString("sendAll");
		String driverIds = dto.getString("driverIds");
		// supplierId=0 查询全部供应商
		String supplierId = dto.getString("supplierId");
		// cityIds=0 查询全部城市
		String cityIds = dto.getString("cityIds");

		if ("1".equals(sendAll) || "2".equals(sendAll) || "4".equals(sendAll)) {
			JsonObject driversJson = new JsonObject();
			driversJson.put("driverIds", driverIds);
			// this.sendDriverMsgByQueryDriverList(driversJson);
			// return;
			return null;
		}

		if ("3".equals(sendAll) && !StringUtil.isNullOrEmpty(driverIds) && driverIds.length() > 0) {
			return this.queryDriverListByDriverIds(driverIds, dto);
		}

		return null;
	}

	private Future<JsonObject> queryDriverListByDriverIds(String driverIdsStr, JsonObject dto) {
		JsonObject query = new JsonObject();
		JsonArray jsonArray = new JsonArray();
		String[] driverIds = driverIdsStr.split("\\,");
		for (String driverId : driverIds) {
			jsonArray.add(Integer.valueOf(driverId));
		}
		query.put("driverId", new JsonObject().put("$in", jsonArray));

		Future<JsonObject> driverFuture = Future.future();
		driverService.queryBatchDriver(query, driverFuture.completer());
		return driverFuture;
	}

	private Future<Integer> sendMsg(JsonObject dto, JsonArray drivers) {

		List<Future> futures = new ArrayList<>();
		Iterator<Object> iter = drivers.iterator();

		while (iter.hasNext()) {
			// 逐条发送
			// 1.构建消息
			Map<String, String> driverMsg = composeMsg((JsonObject) iter.next(), dto);
			// 2.发送消息
			Future<String> sendFuture = this.sendDriverMsg(driverMsg);
			futures.add(sendFuture);
		}
		return this.getTotalSendFuture(futures);
	}

	private Future<Integer> getTotalSendFuture(List<Future> futures) {
		Future<Integer> totalFuture = Future.future();
		CompositeFuture comFutures = CompositeFuture.all(futures);
		comFutures.setHandler(handler -> {
			if (handler.succeeded()) {

				int size = comFutures.result().size();
				int batchSendNums = 0;
				for (int i = 0; i < size; i++) {
					batchSendNums += ("success".equals(comFutures.resultAt(i))) ? 1 : 0;
				}
				totalFuture.complete(batchSendNums);
				logger.info("batchSendNums=" + batchSendNums);
			} else {
				logger.error("send error", totalFuture.cause());
				totalFuture.fail("send error");
			}
		});
		return totalFuture;
	}

	private Map<String, String> composeMsg(JsonObject driver, JsonObject dto) {
		Map<String, String> news = new HashMap<>();
		news.put("newId", dto.getString("id"));//消息ID
		news.put("isScreen", dto.getString("isShellsScreen"));//是否弹屏: 1-是   0-否
		news.put("title", dto.getString("title"));//消息标题
		news.put("detil", dto.getString("content"));//消息内容
		news.put("linkAdd", StringUtil.isNullOrEmpty(dto.getString("jumpUrl"))?"":dto.getString("jumpUrl"));//跳转url
		news.put("msgTime", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));//消息发送时间
		news.put("ifImport", dto.getString("isImportant"));//是否重要：1-是  0-否
		news.put("userId", driver.getInteger("driverId") + "");//司机ID
		news.put("userType", PushConsts.USER_TYPE_DRIVER+"");// 0：乘客 1：司机
		return news;
	}
}
