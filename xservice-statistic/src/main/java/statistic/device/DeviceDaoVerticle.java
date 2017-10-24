package statistic.device;

import org.apache.commons.lang.StringUtils;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;

public class DeviceDaoVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(DeviceDaoVerticle.class);

	private EventBus eb;

	private SQLClient mySQLClient;

	private JsonArray params = new JsonArray();
	private JsonObject result = new JsonObject();

	@Override
	public void start() throws Exception {
		super.start();

		JsonObject mysqlConfig = config().getJsonObject("mysql").getJsonObject("mc-device");
		logger.info("mySQLClient, config={}", mysqlConfig.encode());
		mySQLClient = MySQLClient.createShared(vertx, mysqlConfig);

		eb = vertx.eventBus();
		eb.<JsonObject>consumer(DeviceDaoVerticle.class.getName() + "local", res -> {
			String action = res.headers().get("action");
			if (StringUtils.isNotEmpty(action)) {
				JsonObject body = res.body();
				switch (action) {
				case "getDeviceByPhone":
					Integer phone = body.getInteger("phone");
					getDeviceByPhone(phone, resultHandler -> {
						res.reply(resultHandler.result());
					});
					break;

				default:
					break;
				}
			}
		});
	}

	private static final String GET_DEVICE_BY_PHONE = "select channel, deviceToken from device where phone = ?";

	private void getDeviceByPhone(Integer phone, Handler<AsyncResult<JsonObject>> resultHandler) {
		result.clear();// Must do clear before use it!
		params.clear();// Must do clear before use it!
		mySQLClient.getConnection(res -> {
			if (res.succeeded()) {
				SQLConnection connection = res.result();
				params.add(phone);
				logger.info("getDeviceByPhone, params={}", params.encode());
				connection.queryWithParams(GET_DEVICE_BY_PHONE, params, SQLRes -> {
					if (SQLRes.succeeded()) {
						logger.info("getDeviceByPhone, result={}", SQLRes.result().getRows());
						resultHandler.handle(Future.succeededFuture(result.put("result", SQLRes.result().getRows())));
					} else {
						logger.error("getDeviceByPhone, result={}", SQLRes.cause().getMessage());
						resultHandler.handle(Future.succeededFuture(result.put("result", SQLRes.result())));
					}
				}).close();
			} else {
				logger.error("getDeviceByPhone, {}", res.cause().getMessage());
				resultHandler.handle(Future.succeededFuture(result.put("result", res.succeeded())));
			}
		});
	}
}
