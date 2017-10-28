package config.quickphrase;

import constants.EventbusAddressConstant;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;

public class QuickPhraseConfigVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(QuickPhraseConfigVerticle.class);

	private EventBus eb;

	private SQLClient mySQLClient;

	private JsonArray params = new JsonArray();

	private DeliveryOptions options;

	@Override
	public void start() throws Exception {
		super.start();

		JsonObject mysqlConfig = config().getJsonObject("mysql").getJsonObject("mc-config");
		logger.info("mysqlConfig={}", mysqlConfig.encode());
		mySQLClient = MySQLClient.createShared(vertx, mysqlConfig);

		eb = vertx.eventBus();
		eb.<JsonObject>consumer(QuickPhraseConfigVerticle.class.getName(), res -> {
			MultiMap headers = res.headers();
			JsonObject body = res.body();
			if (headers != null) {
				String action = headers.get("action");
				switch (action) {
				case "retriveQuickPhrase":
					int type = body.getInteger("type").intValue();
					retriveQuickPhrase(type);
					break;

				default:
					break;
				}
			}
		});
	}

	private static final String select_quick_phrase = "SELECT content FROM im_common_language WHERE type = ? ORDER BY weight limit 10";

	public void retriveQuickPhrase(int type) {
		mySQLClient.getConnection(res -> {
			if (res.succeeded()) {
				SQLConnection connection = res.result();
				params.clear();
				params.add(type);
				logger.info("retriveQuickPhrase, params={}", params.encode());
				connection.queryWithParams(select_quick_phrase, params, SQLRes -> {
					if (SQLRes.succeeded()) {
						logger.info("retriveQuickPhrase, result={}", SQLRes.result().getRows().size());

						JsonObject message = new JsonObject();
						message.put("result", SQLRes.result().getRows());
						eb.send(EventbusAddressConstant.quick_phrase_verticle, message, options);
					} else {
						logger.error("retriveQuickPhrase, result={}", SQLRes.cause().getMessage());
					}
				}).close();
			} else {
				logger.error("retriveQuickPhrase, res={}", res.cause().getMessage());
			}
		});
	}
}
