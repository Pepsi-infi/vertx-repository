package push;

import org.apache.commons.lang.StringUtils;

import constants.EventbusAddressConstant;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import push.apns.ApnsMsg;
import push.apns.ApnsVerticle;

public class PushVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(PushVerticle.class);

	private EventBus eb;

	@Override
	public void start() throws Exception {
		super.start();

		eb = vertx.eventBus();
		eb.<JsonObject>consumer(PushVerticle.class.getName() + "local", res -> {
			String action = res.headers().get("action");
			if (StringUtils.isNotEmpty(action)) {
				JsonObject body = res.body();
				switch (action) {
				case "pushMsg2Phone":
					if (body != null) {
						Integer phone = body.getInteger("phone");
						String title = body.getString("title");
						String msgBody = body.getString("body");
						JsonObject extend = body.getJsonObject("extend");
						if (phone != null) {
							pushMsg2Phone(phone, title, msgBody, extend);
						}
					} else {
						logger.warn("apnsSend, device token is null.");
					}
					break;

				default:
					break;
				}
			}
		});
	}

	private void pushMsg2Phone(Integer phone, String title, String body, JsonObject extend) {
		DeliveryOptions option = new DeliveryOptions();
		option.addHeader("action", "getDeviceByPhone");
		option.setSendTimeout(3000);

		JsonObject message = new JsonObject();
		message.put("phone", phone);

		eb.<JsonObject>send(EventbusAddressConstant.device_dao_verticle, message, option, res -> {
			if (res.succeeded()) {
				JsonObject device = res.result().body();
				if (device != null) {
					Integer channel = device.getInteger("channel");
					String deviceToken = device.getString("deviceToken");
					switch (channel) {
					case 4:// apns

						DeliveryOptions apnsOption = new DeliveryOptions();
						apnsOption.addHeader("action", "getDeviceByPhone");
						apnsOption.setSendTimeout(3000);

						ApnsMsg apnsMsg = new ApnsMsg();
						apnsMsg.setTitle(title);
						apnsMsg.setBody(body);
						apnsMsg.setDeviceToken(deviceToken);
						apnsMsg.setExtend(null);

						eb.send(ApnsVerticle.class.getName(), apnsMsg, apnsOption);
						break;

					default:
						break;
					}
				}
			} else {
				logger.error("pushMsg2Phone, e={}", res.cause().getMessage());
			}
		});
	}
}
