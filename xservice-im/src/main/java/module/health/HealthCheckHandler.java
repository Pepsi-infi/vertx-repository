package module.health;

import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.RoutingContext;

public class HealthCheckHandler implements Handler<RoutingContext> {

	private int status;

	public HealthCheckHandler(int status) {
		this.status = status;
	}

	@Override
	public void handle(RoutingContext context) {
		context.response().setStatusCode(status).end();
	}

}
