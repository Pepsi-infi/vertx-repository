package serializer;

import java.util.Map;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class SerialiazerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(SerialiazerVerticle.class);

	private EventBus eb;

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
		super.start();

		eb = vertx.eventBus();
		eb.<String>consumer(SerialiazerVerticle.class.getName(), res -> {
			MultiMap headers = res.headers();
			String data = res.body();
			if (headers != null) {
				String action = headers.get("action");
				switch (action) {
				case "unserialize":
					res.reply(unserialize(data));
					break;
				default:
					res.reply(1);// Fail!
					break;
				}
			}

		});
	}

	private JsonObject unserialize(String data) {
		JsonObject msgBody = null;
		try {
			msgBody = new JsonObject();
			Map<String, Object> map = (Map<String, Object>) SocketByteUtils.byteToObject(data.getBytes());
			msgBody.put("result", map.get("params"));
		} catch (Exception e) {
			logger.error("unserialize, data={} e={}", data, e.getMessage());
		}

		return msgBody;
	}
}
