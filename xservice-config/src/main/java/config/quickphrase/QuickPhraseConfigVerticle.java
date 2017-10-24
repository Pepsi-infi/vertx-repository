package config.quickphrase;

import constants.EventbusAddressConstant;
import io.vertx.core.AbstractVerticle;
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
		options = new DeliveryOptions();

		options.setSendTimeout(3000);

		vertx.setPeriodic(10000, handler -> {
			retriveQuickPhrase();
		});
	}

	private static final String select_quick_phrase = "SELECT * FROM im_common_language WHERE type= ? ORDER BY weight";

	public void retriveQuickPhrase() {
		mySQLClient.getConnection(res -> {
			if (res.succeeded()) {
				SQLConnection connection = res.result();
				logger.info("retriveSensitiveWord, params={}", params.encode());
				connection.queryWithParams("", params, SQLRes -> {
					if (SQLRes.succeeded()) {
						logger.info("retriveSensitiveWord, result={}", SQLRes.result().getRows().size());

						JsonObject message = new JsonObject();
						message.put("result", SQLRes.result().getRows());
						eb.send(EventbusAddressConstant.sensitive_word, message, options);
					} else {
						logger.error("retriveSensitiveWord, result={}", SQLRes.cause().getMessage());
					}
				}).close();
			} else {
				logger.error("retriveSensitiveWord, res={}", res.cause().getMessage());
			}
		});

	}
}
