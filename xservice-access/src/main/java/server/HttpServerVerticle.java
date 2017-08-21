package server;

import org.apache.commons.lang.StringUtils;

import constants.RestAccessConstants;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
import io.vertx.rxjava.core.Future;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import rxjava.RestAPIVerticle;
import utils.IPUtil;

public class HttpServerVerticle extends RestAPIVerticle {

	private static final Logger logger = LoggerFactory.getLogger(HttpServerVerticle.class);

	private SharedData sharedData;
	private LocalMap<String, String> sessionMap;// uid -> handlerID
	private LocalMap<String, String> sessionReverse; // handlerID -> uid

	@Override
	public void start() throws Exception {
		super.start();

		sharedData = vertx.getDelegate().sharedData();
		sessionMap = sharedData.getLocalMap("session");
		sessionReverse = sharedData.getLocalMap("sessionReverse");

		logger.info("Rest mc-access Verticle: Start...");

		Router router = Router.router(vertx);
		router.route(RestAccessConstants.ONLINE_NUMBER).handler(this::getOnlineNumber);
		Future<Void> voidFuture = Future.future();

		String serverHost = this.getServerHost();
		createHttpServer(router, serverHost, RestAccessConstants.HTTP_PORT)
				.compose(serverCreated -> publishHttpEndpoint(RestAccessConstants.SERVICE_NAME, serverHost,
						RestAccessConstants.HTTP_PORT, RestAccessConstants.SERVICE_ROOT))
				.setHandler(voidFuture.completer());
	}

	private String getServerHost() {
		return StringUtils.isNotBlank(IPUtil.getInnerIP()) ? IPUtil.getInnerIP() : config().getString("service.host");
	}

	private void getOnlineNumber(RoutingContext context) {
		JsonObject result = new JsonObject();
		BaseDto base = new BaseDto();
		base.setCode(0);
		base.setTime(System.currentTimeMillis());
		base.setData(result);
		result.put("sessionMap", sessionMap.size());
		result.put("sessionReverse", sessionReverse.size());

		context.response().putHeader("content-type", "application/json").end(Json.encode(base));
	}
}
