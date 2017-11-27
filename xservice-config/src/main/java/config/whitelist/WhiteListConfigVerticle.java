package config.whitelist;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;

/**
 * socket白名单
 * 
 * @author wangxin5
 *
 */
public class WhiteListConfigVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(WhiteListConfigVerticle.class);

	private EventBus eb;

	private SQLClient mySQLClient;

	private JsonArray params = new JsonArray();

	private Set<Integer> driverSet = new HashSet<Integer>();

	private Set<Integer> passengerSet = new HashSet<Integer>();
	private static final int TYPE_DRIVER = 1;
	private static final int TYPE_PASSENGER = 2;

	@Override
	public void start() throws Exception {
		super.start();

		vertx.setPeriodic(30000, handler -> {
			logger.info("innitWhiteListUser timer run .....");
			initWhiteListUser();
		});

		JsonObject mysqlConfig = config().getJsonObject("mysql").getJsonObject("mc-config");
		logger.info("mysqlConfig={}", mysqlConfig.encode());
		mySQLClient = MySQLClient.createShared(vertx, mysqlConfig);

		eb = vertx.eventBus();
		eb.<JsonObject>consumer(WhiteListConfigVerticle.class.getName(), res -> {
			MultiMap headers = res.headers();
			JsonObject json = res.body();
			if (headers != null) {
				String action = headers.get("action");
				switch (action) {
				case "isWhiteListUser":
					res.reply(isWhiteListUser(NumberUtils.toInt(json.getString("uid"))));
					break;
				case "whiteListPassenger":
					res.reply(whiteListPassenger(NumberUtils.toInt(json.getString("uid"))));
					break;
				default:
					res.reply(1);// Fail!
					break;
				}
			}
		});
	}

	private static final String select_white_list_user = "SELECT uid, type FROM socket_white_list";

	public void initWhiteListUser() {
		mySQLClient.getConnection(res -> {
			if (res.succeeded()) {
				SQLConnection connection = res.result();
				params.clear();
				logger.info("initWhiteListUser, params={}", params.encode());
				connection.queryWithParams(select_white_list_user, params, SQLRes -> {
					if (SQLRes.succeeded()) {
						logger.info("initWhiteListUser, result={}", SQLRes.result().getRows().size());
						List<JsonObject> rows = SQLRes.result().getRows();
						if (CollectionUtils.isNotEmpty(rows)) {
							for (JsonObject json : rows) {
								if (TYPE_DRIVER == json.getInteger("type").intValue()) {
									driverSet.add(json.getInteger("uid"));
								} else if (TYPE_PASSENGER == json.getInteger("type").intValue()) {
									passengerSet.add(json.getInteger("uid"));
								}
							}
						}
					} else {
						logger.error("initWhiteListUser, result={}", SQLRes.cause().getMessage());
					}
				}).close();
			} else {
				logger.error("initWhiteListUser, res={}", res.cause().getMessage());
			}
		});
	}

	/**
	 * 判断用户是否在新socket白名单
	 * 
	 * @param uid
	 * @return
	 */
	public JsonObject isWhiteListUser(Integer uid) {
		JsonObject message = new JsonObject();
		if (driverSet.contains(uid)) {
			message.put("result", true);
		} else {
			message.put("result", false);
		}
		return message;
	}

	public JsonObject whiteListPassenger(Integer uid) {
		JsonObject message = new JsonObject();
		if (passengerSet.contains(uid)) {
			message.put("result", true);
		} else {
			message.put("result", false);
		}
		return message;
	}
}
