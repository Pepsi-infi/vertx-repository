package config.quickphrase;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.sql.SQLClient;

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

	public void retriveQuickPhrase() {

	}
}
