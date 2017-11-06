package tp.impl;

import helper.XProxyHelper;
import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.impl.Utils;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.MultiMap;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.eventbus.EventBus;
import io.vertx.rxjava.ext.web.client.HttpResponse;
import io.vertx.rxjava.ext.web.client.WebClient;
import io.vertx.rxjava.ext.web.codec.BodyCodec;
import rx.Single;
import tp.TpService;

public class TpServiceImpl extends AbstractVerticle implements TpService {

	private static final Logger logger = LoggerFactory.getLogger(TpServiceImpl.class);

	private CircuitBreaker circuitBreaker;

	private WebClient webClient;

	private static String CAR_API_HOST;
	private static int CAR_API_PORT;

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
		super.start();

		this.circuitBreaker = this.createCircuitBreaker(vertx, new JsonObject());

		webClient = WebClient.create(vertx);

		XProxyHelper.registerService(TpService.class, vertx.getDelegate(), this, TpService.SERVICE_ADDRESS);

		CAR_API_HOST = config().getString("car-api-host");
		CAR_API_PORT = config().getInteger("car-api-port");
	}

	private CircuitBreaker createCircuitBreaker(Vertx vertx, JsonObject config) {
		JsonObject circuitObject = config.getJsonObject("circuit-breaker");
		JsonObject cbOptions = circuitObject != null ? circuitObject : new JsonObject();
		CircuitBreakerOptions options = new CircuitBreakerOptions();
		options.setMaxFailures(cbOptions.getInteger("max-failures", 3));
		options.setTimeout(cbOptions.getLong("timeout", 3000L));
		options.setFallbackOnFailure(true);
		options.setResetTimeout(cbOptions.getLong("reset-timeout", 30000L));
		String name = cbOptions.getString("name", "circuit-breaker");

		return CircuitBreaker.create(name, vertx.getDelegate(), options);
	}

	@Override
	public void updateOnlineState(String uid, String date, JsonObject content, Handler<AsyncResult<String>> result) {
		circuitBreaker.<String>execute(future -> {
			MultiMap form = MultiMap.caseInsensitiveMultiMap();
			form.add("uid", uid);
			form.add("time", Utils.normalizePath(date).substring(1));
			form.add("msg", Utils.normalizePath(content.encode()).substring(1));

			Single<HttpResponse<String>> httpRequest = webClient
					.post(CAR_API_PORT, CAR_API_HOST, config().getString("updateOnlineState")).as(BodyCodec.string())
					.rxSendForm(form);
			httpRequest.subscribe(resp -> {
				if (resp.statusCode() == 200) {
					logger.info("updateOnlineState, uid={}&time={}&msg={}, response={}", uid,
							Utils.normalizePath(date).substring(1), Utils.normalizePath(content.encode()).substring(1),
							resp.body());
					future.complete(resp.body());
				} else {
					logger.error("updateOnlineState={}", resp.statusMessage());
					future.fail(resp.statusCode() + resp.statusMessage());
				}
			});
		}).setHandler(ar -> {
			if (ar.succeeded()) {
				result.handle(Future.succeededFuture(ar.result()));
			} else {
				result.handle(Future.succeededFuture(null));
			}
		});
	}

	@Override
	public void updateOnlineSimple(String uid, String date, JsonObject content, Handler<AsyncResult<String>> result) {
		circuitBreaker.<String>execute(future -> {
			MultiMap form = MultiMap.caseInsensitiveMultiMap();
			form.add("uid", uid);
			form.add("time", Utils.normalizePath(date).substring(1));
			form.add("msg", Utils.normalizePath(content.encode()).substring(1));

			Single<HttpResponse<String>> httpRequest = webClient
					.post(CAR_API_PORT, CAR_API_HOST, config().getString("updateOnlineSimple")).as(BodyCodec.string())
					.rxSendForm(form);
			httpRequest.subscribe(resp -> {
				if (resp.statusCode() == 200) {
					logger.info("updateOnlineSimple, uid={}&time={}&msg={}, response={}", uid,
							Utils.normalizePath(date).substring(1), Utils.normalizePath(content.encode()).substring(1),
							resp.body());
					future.complete(resp.body());
				} else {
					logger.error("updateOnlineSimple, statusCode={} statusMessage={}", resp.statusCode(),
							resp.statusMessage());
					future.fail(resp.statusCode() + resp.statusMessage());
				}
			});
		}).setHandler(ar -> {
			if (ar.succeeded()) {
				result.handle(Future.succeededFuture(ar.result()));
			} else {
				result.handle(Future.succeededFuture(null));
			}
		});
	}

	public void subscribe(JsonObject msg, Handler<AsyncResult<String>> result) {
		circuitBreaker.<String>execute(future -> {
			MultiMap form = MultiMap.caseInsensitiveMultiMap();
			form.set("uid", msg.getString("userId"));
			form.set("msg", Utils.normalizePath(msg.getString("data")).substring(1));

			logger.info("subscribe, uid={} data={}", msg.getString("userId"), msg.getString("data"));

			Single<HttpResponse<String>> httpRequest = webClient
					.post(CAR_API_PORT, CAR_API_HOST, config().getString("subscribe")).as(BodyCodec.string())
					.rxSendForm(form);
			httpRequest.subscribe(resp -> {
				if (resp.statusCode() == 200) {
					logger.info("subscribe, {}", resp.body());
					future.complete(resp.body());
				} else {
					logger.error("subscribe, statusCode={} statusMessage={}", resp.statusCode(), resp.statusMessage());
					future.fail(resp.statusCode() + resp.statusMessage());
				}
			});
		}).setHandler(ar -> {
			if (ar.succeeded()) {
				result.handle(Future.succeededFuture(ar.result()));
			} else {
				result.handle(Future.succeededFuture(null));
			}
		});
	}

	public void unsubscribe(JsonObject msg, Handler<AsyncResult<String>> result) {
		circuitBreaker.<String>execute(future -> {
			MultiMap form = MultiMap.caseInsensitiveMultiMap();
			form.set("uid", msg.getString("userId"));
			form.set("msg", Utils.normalizePath(msg.getString("data")).substring(1));
			Single<HttpResponse<String>> httpRequest = webClient
					.post(CAR_API_PORT, CAR_API_HOST, config().getString("unsubscribe")).as(BodyCodec.string())
					.rxSendForm(form);
			httpRequest.subscribe(resp -> {
				if (resp.statusCode() == 200) {
					future.complete(resp.body());
				} else {
					future.fail(resp.statusCode() + resp.statusMessage());
				}
			});
		}).setHandler(ar -> {
			if (ar.succeeded()) {
				result.handle(Future.succeededFuture(ar.result()));
			} else {
				result.handle(Future.succeededFuture(null));
			}
		});
	}

	/**
	 * 
	 * @param msg
	 * @param result
	 */
	public void auth(JsonObject param, Handler<AsyncResult<String>> result) {
		circuitBreaker.<String>execute(future -> {
			String userId = param.getString("userId");
			String hash = param.getString("hash");
			String ip = param.getString("ip");
			String channelid = param.getString("channelid");
			String mark = param.getString("mark");
			String ver = param.getString("ver");
			String mode = param.getString("mode");

			String requestURI = new StringBuffer("/webservice/chat/signinUserHash/").append("user=").append(userId)
					.append("&hash=").append(hash).append("&ip=").append(ip).append("&channelid=").append(channelid)
					.append("&mark=").append(mark).append("&ver=").append(ver).append("&mode=").append(mode).toString();

			Single<HttpResponse<String>> httpRequest = webClient.get(CAR_API_PORT, CAR_API_HOST, requestURI)
					.as(BodyCodec.string()).rxSend();

			httpRequest.subscribe(resp -> {
				if (resp.statusCode() == 200) {
					logger.info("auth={}", resp.body());
					future.complete(resp.body());
				} else {
					logger.error("auth={}", resp.statusMessage());
					future.fail(resp.statusCode() + resp.statusMessage());
				}
			});
		}).setHandler(ar -> {
			if (ar.succeeded()) {
				result.handle(Future.succeededFuture(ar.result()));
			} else {
				result.handle(Future.succeededFuture(null));
			}
		});
	}

	public void setClientOnline(JsonObject param, Handler<AsyncResult<String>> result) {
		circuitBreaker.<String>execute(future -> {
			String userId = param.getString("userId");

			String requestURI = new StringBuffer(config().getString("setClientOnline")).append("uid=").append(userId)
					.toString();

			logger.info("setClientOnline, host={}port={}uri={}", CAR_API_HOST, CAR_API_PORT, requestURI);
			Single<HttpResponse<String>> httpRequest = webClient.get(CAR_API_PORT, CAR_API_HOST, requestURI)
					.as(BodyCodec.string()).rxSend();

			httpRequest.subscribe(resp -> {
				if (resp.statusCode() == 200) {
					logger.info("setClientOnline, {}", resp.body());
					future.complete(resp.body());
				} else {
					logger.error("setClientOnline, statusCode={} statusMessage={}", resp.statusCode(),
							resp.statusMessage());
					future.fail(resp.statusCode() + resp.statusMessage());
				}
			});
		}).setHandler(ar -> {
			if (ar.succeeded()) {
				result.handle(Future.succeededFuture(ar.result()));
			} else {
				result.handle(Future.succeededFuture(null));
			}
		});
	}

	public void setClientOffline(JsonObject param, Handler<AsyncResult<String>> result) {
		circuitBreaker.<String>execute(future -> {
			String userId = param.getString("userId");

			String requestURI = new StringBuffer(config().getString("setClientOffline")).append("uid=").append(userId)
					.toString();

			Single<HttpResponse<String>> httpRequest = webClient.get(CAR_API_PORT, CAR_API_HOST, requestURI)
					.as(BodyCodec.string()).rxSend();

			httpRequest.subscribe(resp -> {
				if (resp.statusCode() == 200) {
					logger.info("setClientOnline, {}", resp.body());
					future.complete(resp.body());
				} else {
					logger.error("setClientOnline, statusCode={} statusMessage={}", resp.statusCode(),
							resp.statusMessage());
					future.fail(resp.statusCode() + resp.statusMessage());
				}
			});
		}).setHandler(ar -> {
			if (ar.succeeded()) {
				result.handle(Future.succeededFuture(ar.result()));
			} else {
				result.handle(Future.succeededFuture(null));
			}
		});
	}
}
