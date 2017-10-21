package config.sensitivewords;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import module.config.ConfigAddressConstant;

public class SensitiveWordConfigVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(SensitiveWordConfigVerticle.class);

	private EventBus eb;

	private SQLClient mySQLClient;

	private JsonArray params = new JsonArray();

	private DeliveryOptions options;

	@Override
	public void start() throws Exception {
		super.start();

		mySQLClient = MySQLClient.createShared(vertx, config().getJsonObject("mysql").getJsonObject("mc-im"));

		eb = vertx.eventBus();
		options = new DeliveryOptions();

		options.setSendTimeout(3000);

		vertx.setPeriodic(10000, handler -> {
			retriveSensitiveWord();
		});
	}

	private static final String retriveSensitiveWord = "select word from sensitive_word";

	public void retriveSensitiveWord() {
		mySQLClient.getConnection(res -> {
			if (res.succeeded()) {
				SQLConnection connection = res.result();
				logger.info("retriveSensitiveWord, params={}", params.encode());
				connection.queryWithParams(retriveSensitiveWord, params, SQLRes -> {
					if (SQLRes.succeeded()) {
						logger.info("retriveSensitiveWord, result={}", SQLRes.result().getRows());

						JsonObject message = new JsonObject();
						message.put("result", SQLRes.result().getRows());
						eb.send(ConfigAddressConstant.sensitive_word, message, options);
					} else {
						logger.error("retriveSensitiveWord, result={}", SQLRes.cause().getMessage());
					}
				});

			} else {
				logger.error("retriveSensitiveWord, res={}", res.cause().getMessage());
			}
		});
	}

}
