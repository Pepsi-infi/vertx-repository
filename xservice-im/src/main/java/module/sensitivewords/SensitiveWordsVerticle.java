package module.sensitivewords;

import java.util.Collection;
import java.util.List;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;
import org.ahocorasick.trie.Trie.TrieBuilder;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import module.config.ConfigAddressConstant;

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

		TrieBuilder builder = Trie.builder();
		trie = Trie.builder().build();

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

		eb.<JsonObject>consumer(ConfigAddressConstant.sensitive_word, res -> {
			JsonArray keyWords = res.body().getJsonArray("result");
			for (Object object : keyWords) {
				builder.addKeyword(JsonObject.mapFrom(object).getString("word"));
			}
			trie = builder.build();
			logger.info("keyWords={}", keyWords.size());
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
