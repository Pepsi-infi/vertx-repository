package dao.impl;

import java.util.Date;

import dao.CarBizEuroService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLClient;
import utils.CalendarUtil;

public class CarBizEuroVerticle extends AbstractVerticle implements CarBizEuroService {

	private static final Logger logger = LoggerFactory.getLogger(CarBizEuroVerticle.class);

	private SQLClient rentCarMySQLClient;

	private SQLClient deviceMySQLClient;

	@Override
	public void start() throws Exception {
		super.start();

		logger.info("rentcat " + config().getJsonObject("rentcar"));
		logger.info("mc-device " + config().getJsonObject("mc-device"));
		rentCarMySQLClient = MySQLClient.createNonShared(vertx, config().getJsonObject("rentcar"));

		deviceMySQLClient = MySQLClient.createNonShared(vertx, config().getJsonObject("mc-device"));

		transData();

	}

	public void countCarBizEuro(Handler<AsyncResult<ResultSet>> resultHandler) {
		rentCarMySQLClient.getConnection(res -> {
			if (res.succeeded()) {
				res.result().query(SQL.COUNT_CAR_BIZ_EURO, resultHandler);
			} else {
				resultHandler.handle(Future.failedFuture(res.cause()));
			}
		}).close();
	}

	public void queryCarBizEurop(int from, int to, Handler<AsyncResult<ResultSet>> resultHandler) {
		rentCarMySQLClient.getConnection(res -> {
			if (res.succeeded()) {
				res.result().query(SQL.QUERY_CAR_BIZ_EURO, resultHandler);
			} else {
				resultHandler.handle(Future.failedFuture(res.cause()));
			}
		}).close();
	}

	public void transData() {
		rentCarMySQLClient.getConnection(res -> {
			if (res.succeeded()) {
				res.result().queryStream(SQL.select_all_ios_token_from_car_biz_europ, stream -> {
					if (stream.succeeded()) {
						stream.result().handler(row -> {
							JsonArray params = new JsonArray().add(-1).add(row.getString(0)).add("").add(-1)
									.add(row.getString(1)).add(-1).add("").add(-1).add("").add("").add(0)
									.add(CalendarUtil.format(new Date())).add(CalendarUtil.format(new Date()));

							deviceMySQLClient.getConnection(con -> {
								if (con.succeeded()) {
									con.result().updateWithParams(SQL.replace_into_device, params, r -> {
										if (r.succeeded()) {
											logger.info(r.result());
										} else {
											logger.error(r.cause());
										}
									});
								} else {

								}
							}).close();
						});
					} else {
						logger.error(stream.cause());
					}
				});
			} else {
				logger.error(res.cause());
			}
		}).close();
	}

	interface SQL {
		static final String COUNT_CAR_BIZ_EURO = "select count(*) from car_biz_europ where 1=1 and device_token !='' ";

		static final String select_all_ios_token_from_car_biz_europ = "select phone, device_token from car_biz_europ where 1=1 and device_token !=''";

		static final String replace_into_device = "replace into "
				+ "device (uid, phone, deviceType, channel, deviceToken, "
				+ "osType, osVersion, appCode, appVersion, antFingerprint, " + "isAcceptPush, createTime, updateTime) "
				+ "values " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		static final String select_all_from_device = "select * from device";

		static final String QUERY_CAR_BIZ_EURO = "SELECT * FROM car_biz_europ WHERE device_token !='' ORDER BY id LIMIT ?,?;";

		static final String ADD_USER_DEVICE = "insert into device (uid,phone,deviceType,channel,deviceToken,osType,osVersion,appCode,appVersion,antFingerprint,isAcceptPush,createTime,updateTime) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
	}
}
