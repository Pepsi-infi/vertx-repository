package tp.impl;

import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.MultiMap;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.web.client.HttpResponse;
import io.vertx.rxjava.ext.web.client.WebClient;
import io.vertx.rxjava.ext.web.codec.BodyCodec;
import rx.Single;

public class TpServiceImpl extends AbstractVerticle {

	private CircuitBreaker circuitBreaker;

	private WebClient webClient;

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
		super.start();

		this.circuitBreaker = this.createCircuitBreaker(vertx, new JsonObject());

		webClient = WebClient.create(vertx);
	}

	private CircuitBreaker createCircuitBreaker(Vertx vertx, JsonObject config) {
		JsonObject circuitObject = config.getJsonObject("circuit-breaker");
		JsonObject cbOptions = circuitObject != null ? circuitObject : new JsonObject();
		CircuitBreakerOptions options = new CircuitBreakerOptions();
		options.setMaxFailures(cbOptions.getInteger("max-failures", 5));
		options.setTimeout(cbOptions.getLong("timeout", 10000L));
		options.setFallbackOnFailure(true);
		options.setResetTimeout(cbOptions.getLong("reset-timeout", 30000L));
		String name = cbOptions.getString("name", "circuit-breaker");

		return CircuitBreaker.create(name, vertx.getDelegate(), options);
	}

	public void updateOnlineState(String uid, String date, String content, Handler<AsyncResult<String>> result) {
		circuitBreaker.<String>execute(future -> {
			MultiMap form = MultiMap.caseInsensitiveMultiMap();
			form.set("uid", uid);
			form.set("time", date);
			form.set("msg", content);
			Single<HttpResponse<String>> httpRequest = webClient.post(80, "", "/webservice/chat/updateOnlineState/")
					.as(BodyCodec.string()).rxSendForm(form);
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

	public void updateOnlineSimple(String uid, String date, String content, Handler<AsyncResult<String>> result) {
		circuitBreaker.<String>execute(future -> {
			MultiMap form = MultiMap.caseInsensitiveMultiMap();
			form.set("uid", uid);
			form.set("time", date);
			form.set("msg", content);
			Single<HttpResponse<String>> httpRequest = webClient
					.post(80, "", "/webservice/chat/updateSimpleOnlineState/").as(BodyCodec.string()).rxSendForm(form);
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

}
