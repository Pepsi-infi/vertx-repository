package test;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class SensitiveWordConfigVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(SensitiveWordConfigVerticle.class);

	private EventBus eb;

	@Override
	public void start() throws Exception {
		super.start();

		logger.info("SensitiveWordConfigVerticle, ");
		
		eb = vertx.eventBus();
		eb.<JsonObject>consumer(SensitiveWordConfigVerticle.class.getName(), res -> {

			logger.info("SensitiveWordConfigVerticle, ");
			res.reply(new JsonObject().put("1", "test"));
		});
	}
}
