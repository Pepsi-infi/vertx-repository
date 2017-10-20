package module.notification;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class NotificationVerticle extends AbstractVerticle {

	private EventBus eb;

	@Override
	public void start() throws Exception {
		super.start();

		eb = vertx.eventBus();
		eb.consumer(NotificationVerticle.class.getName());
	}

	public void pushNotification() {

	}

}
