package api;

import com.google.common.collect.Lists;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertx.rxjava.ext.web.handler.CorsHandler;
import model.Result;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import rxjava.RestAPIVerticle;
import service.ImCommonLanguageService;
import service.SensitiveWordService;
import utils.IPUtil;
import utils.JsonUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by lufei Date : 2017/7/25 11:40 Description :
 */
public class RestConfigVerticle extends RestAPIVerticle {
	private static final Logger logger = LoggerFactory.getLogger(RestConfigVerticle.class);

	private ImCommonLanguageService imCommonLanguageService;

	private SensitiveWordService sensitiveWordService;

	@Override
	public void start() throws Exception {
		super.start();

		logger.info("Rest xservice-config verticle: Start...");

		Router router = Router.router(vertx);
		router.route().handler(CorsHandler.create("*"));
		router.route().handler(BodyHandler.create());
		router.get(RestConstants.Config.QUERY_IM_COMMON_LANGUAGE).handler(this::queryImCommonLanguage);
		router.get(RestConstants.Config.GET_IM_COMMON_LANGUAGE).handler(this::getImCommonLanguage);
		router.post(RestConstants.Config.QUERY_IM_COMMON_LANGUAGE).handler(this::saveImCommonLanguage);
		router.get(RestConstants.Config.DELETE_IM_COMMON_LANGUAGE).handler(this::deleteImCommonLanguage);

		router.get(RestConstants.Config.QUERY_SENSITIVE_WORD).handler(this::querySensitiveWord);
		router.get(RestConstants.Config.GET_SENSITIVE_WORD).handler(this::getSensitiveWord);
		router.post(RestConstants.Config.QUERY_SENSITIVE_WORD).handler(this::saveSensitiveWord);
		router.get(RestConstants.Config.DELETE_SENSITIVE_WORD).handler(this::deleteSensitiveWord);

		Future<Void> voidFuture = Future.future();
		String serverHost = this.getServerHost();
		createHttpServer(router, serverHost, config().getInteger("service.port"))
				.compose(serverCreated -> publishHttpEndpoint(RestConstants.Config.SERVICE_NAME, serverHost,
						config().getInteger("service.port"), RestConstants.Config.SERVICE_ROOT))
				.setHandler(voidFuture.completer());

		this.initMsgStatService();
	}

	private void initMsgStatService() {
		imCommonLanguageService = ImCommonLanguageService.createProxy(vertx.getDelegate());
		sensitiveWordService = SensitiveWordService.createProxy(vertx.getDelegate());
	}

	private String getServerHost() {
		return StringUtils.isNotBlank(IPUtil.getInnerIP()) ? IPUtil.getInnerIP() : "127.0.0.1";
	}

	Function<List<JsonObject>, String> converter = (jsonObjects) -> {
		List<Map> list = Lists.transform(jsonObjects, new com.google.common.base.Function<JsonObject, Map>() {
			@Nullable
			@Override
			public Map apply(@Nullable JsonObject input) {
				return input.getMap();
			}
		});
		Result r = Result.success(list);
		return JsonUtil.encodePrettily(r);
	};

	private void queryImCommonLanguage(RoutingContext context) {
		String type = context.request().params().get("type");

		imCommonLanguageService.queryImCommonLanguage(NumberUtils.toInt(type), resultHandler(context, converter));
	}

	private void getImCommonLanguage(RoutingContext context) {
		String id = context.request().params().get("id");
		imCommonLanguageService.getImCommonLanguage(NumberUtils.toInt(id), resultJsonHandler(context));
	}

	private void saveImCommonLanguage(RoutingContext context) {
		String id = context.request().formAttributes().get("id");
		String content = context.request().formAttributes().get("content");
		String weight = context.request().formAttributes().get("weight");
		String type = context.request().formAttributes().get("type");
		JsonObject jsonObject = new JsonObject().put("content", content).put("weight", NumberUtils.toInt(weight));
		if (NumberUtils.toInt(id) > 0) {
			jsonObject.put("id", NumberUtils.toInt(id));
			imCommonLanguageService.updateImCommonLanguage(jsonObject, resultJsonHandler(context));
		} else {
			jsonObject.put("type", NumberUtils.toInt(type));
			imCommonLanguageService.addImCommonLanguage(jsonObject, resultJsonHandler(context));
		}
	}

	private void deleteImCommonLanguage(RoutingContext context) {
		String id = context.request().params().get("id");
		imCommonLanguageService.deleteImCommonLanguage(NumberUtils.toInt(id), resultJsonHandler(context));
	}

	private void querySensitiveWord(RoutingContext context) {
		String page = context.request().params().get("page");
		String size = context.request().params().get("size");
		String word = context.request().params().get("word");
		JsonObject jsonObject = new JsonObject();
		if (StringUtils.isNotBlank(word)) {
			jsonObject.put("word", word);
		}
		sensitiveWordService.querySensitiveWord(jsonObject, NumberUtils.toInt(page), NumberUtils.toInt(size),
				resultStringHandler(context));
	}

	private void getSensitiveWord(RoutingContext context) {
		String id = context.request().params().get("id");
		sensitiveWordService.getSensitiveWord(NumberUtils.toInt(id), resultJsonHandler(context));
	}

	private void saveSensitiveWord(RoutingContext context) {
		String id = context.request().formAttributes().get("id");
		String word = context.request().formAttributes().get("word");
		String status = context.request().formAttributes().get("status");
		JsonObject jsonObject = new JsonObject().put("word", word);
		if (NumberUtils.toInt(id) > 0) {
			jsonObject.put("id", NumberUtils.toInt(id));
			jsonObject.put("status", NumberUtils.toInt(status));
			sensitiveWordService.updateSensitiveWord(jsonObject, resultJsonHandler(context));
		} else {
			sensitiveWordService.addSensitiveWord(jsonObject, resultJsonHandler(context));
		}
	}

	private void deleteSensitiveWord(RoutingContext context) {
		String id = context.request().params().get("id");
		sensitiveWordService.deleteSensitiveWord(NumberUtils.toInt(id), resultJsonHandler(context));
	}

}
