package module.sensitivewords;

import java.util.Collection;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class SensitiveWordsVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(SensitiveWordsVerticle.class);

	private EventBus eb;

	private Trie trie;

	public static interface method {
		public static final String filterSensitiveWords = "filterSensitiveWords";
	}

	@Override
	public void start() throws Exception {
		super.start();

		trie = Trie.builder().addKeyword("日").addKeyword("我曹").build();

		eb = vertx.eventBus();
		eb.<String>consumer(SensitiveWordsVerticle.class.getName(), res -> {
			MultiMap headers = res.headers();
			String content = res.body();
			if (headers != null) {
				String action = headers.get("action");
				switch (action) {
				case method.filterSensitiveWords:
					res.reply(filterSensitiveWords(content));
					break;

				default:
					res.reply(1);// Fail!
					break;
				}
			}
		});

		ConfigStoreOptions ebStore = new ConfigStoreOptions().setType("event-bus")
				.setConfig(new JsonObject().put("address", "config.sensitivewords.SensitiveWordConfigVerticle"));

		ConfigRetrieverOptions options = new ConfigRetrieverOptions().setScanPeriod(3000).addStore(ebStore);

		ConfigRetriever retriever = ConfigRetriever.create(Vertx.vertx(), options);
		retriever.getConfig(json -> {
			logger.info("SensitiveWordsVerticle, config={}", json.result());
		});

		retriever.listen(change -> {
			// Previous configuration
			JsonObject previous = change.getPreviousConfiguration();

			logger.info("previous, config={}", previous.encode());

			// New configuration
			JsonObject conf = change.getNewConfiguration();

			logger.info("conf, config={}", conf.encode());
		});
	}

	private String filterSensitiveWords(String content) {
		Collection<Emit> emits = trie.parseText(content);

		for (Emit emit : emits) {
			content = content.replaceAll(emit.getKeyword(), "*");
		}

		return content;
	}
}
