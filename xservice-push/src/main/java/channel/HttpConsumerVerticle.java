package channel;

import constant.ConnectionConsts;
import constant.PushConsts;
import domain.AmqpConsumeMessage;
import enums.MsgStatusEnum;
import enums.PushTypeEnum;
import io.netty.util.internal.StringUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.sql.UpdateResult;
import io.vertx.ext.web.Router;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import service.GcmPushService;
import service.SocketPushService;
import service.XiaoMiPushService;
import util.MsgUtil;
import util.PropertiesLoaderUtils;

public class HttpConsumerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(AmqpConsumerVerticle.class);

	private JsonObject recieveMsg = null;

	private Object activity = null;

	private RedisClient redisClient;

	private SocketPushService socketPushService;

	private XiaoMiPushService xiaomiPushService;

	private GcmPushService gcmPushService;

	private SQLClient mysqlClient;

	private JDBCClient jdbcClient;

	private HttpServer httpServer;

	private Router router;

	@Override
	public void start() throws Exception {
		vertx.executeBlocking(future -> {

			// 连接redis
			initRedis();

			// 连接数据库
			initMysql();

			// 初始化化服务
			this.initService();

			// 获取消息
			recieveMsg = getHttpMsg();
			future.complete(recieveMsg);

		}, res -> {
			logger.info("接收Http消息： " + res.result());
			// 消费消息
			consumMsg();
		});

	}

	private JsonObject getHttpMsg() {

		httpServer = vertx.createHttpServer();

		if (httpServer == null) {
			logger.error("HttpServer is null");
			return null;
		}

		router = Router.router(vertx);

		if (router == null) {
			logger.error("Router is null");
			return null;
		}

		router.route("/sqyc/push/sokcet.htm").handler(context -> {

			HttpServerResponse resp = context.response();
			resp.putHeader("content-type", "text/plain").end("好好学习", "GBK");

			HttpServerRequest request = context.request();

			String httpMsg = request.getParam("body");

			recieveMsg = new JsonObject(httpMsg);
			
			if(recieveMsg==null){
				logger.error("请求数据为空，不做处理");
				return;
			}
			
			logger.info("开始消费数据");
			consumMsg();
		});

		httpServer.requestHandler(router::accept).listen(8989);
		
		return recieveMsg;

	}

	private void initMysql() {
		JsonObject jsonObject = new JsonObject();
		jsonObject.put("host", PropertiesLoaderUtils.multiProp.getProperty("host"))
				.put("port", PropertiesLoaderUtils.multiProp.getProperty("port"))
				.put("username", PropertiesLoaderUtils.multiProp.getProperty("root")).put("password", "password")
				.put("database", PropertiesLoaderUtils.multiProp.getProperty("database"));

		mysqlClient = MySQLClient.createShared(vertx, jsonObject);

		logger.info("mysql连接结果=" + mysqlClient);

	}

	private RedisClient initRedis() {
		// 获取redis连接
		RedisOptions config = new RedisOptions().setAddress(ConnectionConsts.redis_server_address);
		redisClient = RedisClient.create(vertx, config);

		if (redisClient == null) {
			logger.error("redis服务器连接失败");
			return null;
		}
		logger.info("redis服务器连接成功");
		return redisClient;
	}

	private void initService() {

		if (socketPushService == null) {
			socketPushService = SocketPushService.createProxy(vertx);
		}

		if (xiaomiPushService == null) {
			xiaomiPushService = XiaoMiPushService.createProxy(vertx);
		}

		if (gcmPushService == null) {
			gcmPushService = GcmPushService.createProxy(vertx);
		}

	}

	private void consumMsg() {

		// 校验
		if (!validateRecieveMsg(recieveMsg)) {
			return;
		}

		String msgId =  (String) recieveMsg.getValue("msgId");
		Object customerId = recieveMsg.getValue("customerId");
		String apnsToken = (String) recieveMsg.getValue("apnsToken");

		redisClient.get(PushConsts.AD_PASSENGER_MSG_PREFIX + msgId + "_" + customerId, res -> {
			if (res.succeeded()) {
				activity = res.result();
			}
		});

		if (activity != null) {
			logger.error("消费PassengerMsg：这个消息发送过，禁止重复发送！，msgId==:" + msgId);
			return;
		}

		if (StringUtil.isNullOrEmpty(apnsToken) || "null".equals(apnsToken)) {

			String token = (String) recieveMsg.getValue("deviceToken"); // sokit、gcm,小米连接token
			String devicePushType = (String) recieveMsg.getValue("devicePushType"); // 消息推送类型
																					// 1:IOS；2：GCM(或FCM)；3:小米

			if (StringUtil.isNullOrEmpty(devicePushType)) {
				logger.error("消息体校验未通过：消息推送类型为空");
				return;
			}

			String sendType = getSendType(token, devicePushType);

			// 推送消息
			pushMsg(sendType, token);

			// 消息入库
			saveMsg(sendType);

		}

	}

	private void saveMsg(String sendType) {
		AmqpConsumeMessage msg = new AmqpConsumeMessage();

		msg.setAmqpMsgId(MsgUtil.createMsgId());
		msg.setChannel(sendType);
		msg.setMsgBody(recieveMsg.toString());
		msg.setStatus(MsgStatusEnum.SUCCESS.getCode());

		mysqlClient.getConnection(res -> {

			if (res.succeeded()) {

				logger.info("数据库连接建立");

				String insertSql = "insert into amqp_consume_message " + "(amqp_msg_id,channel,msg_body,status,"
						+ "created_time,updated_time) " + "values " + "(?,?,?,?,now(),now())";

				SQLConnection connection = res.result();

				JsonArray array = new JsonArray().add(msg.getAmqpMsgId()).add(msg.getChannel()).add(msg.getMsgBody())
						.add(msg.getStatus());

				connection.updateWithParams(insertSql, array, sqlRes -> {

					if (sqlRes.succeeded()) {
						UpdateResult result = sqlRes.result();
						logger.info("updateResult=" + result);
					}

				});

			} else {
				logger.error("数据库连接失败");
			}
		});

	}

	private String getSendType(String token, String devicePushType) {

		String sendType = "sokit";

		if (!StringUtil.isNullOrEmpty(token)) {

			if ("3".equals(devicePushType)) {
				sendType = "gcm";
			}

			if ("2".equals(devicePushType)) {
				sendType = "xiaomi";
			}
		}
		return sendType;
	}

	private void pushMsg(String sendType, String token) {

		recieveMsg.put("regId", token);

		if (PushTypeEnum.SOCKET.getCode().equals(sendType)) {

			token = null; // TODO socket推送
			logger.info("开始走socket推送");
			socketPushService.sendMsg(recieveMsg);

		} else if (PushTypeEnum.GCM.getCode().equals(sendType)) {

			token = null; // TODO gcm推送
			logger.info("开始走gcm推送");
			gcmPushService.sendMsg(recieveMsg);

		} else if (PushTypeEnum.XIAOMI.getCode().equals(sendType)) {

			// TODO 只用作对安卓手机进行推送
			logger.info("开始走小米推送");
			xiaomiPushService.sendMsg(recieveMsg);

		} else {
			logger.error("无效推送渠道");
			return;
		}

	}

	private boolean validateRecieveMsg(JsonObject msg) {

		if (msg == null) {
			logger.error("校验未通过，recieveMsg==" + msg);
			return false;
		}

		if (msg.getValue("msgId") == null) {
			logger.error("校验未通过：msgId为空");
			return false;
		}

		if (msg.getValue("customerId") == null) {
			logger.error("校验未通过：customerId为空");
			return false;
		}

		return true;
	}

}
