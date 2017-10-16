package module.quickphrase;

import org.apache.commons.lang.StringUtils;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;

public class QuickPhraseVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(QuickPhraseVerticle.class);

	private EventBus eb;

	private SQLClient mySQLClient;

	private JsonObject result = new JsonObject();
	private JsonArray params = new JsonArray();

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
		super.start();

		eb = vertx.eventBus();
		logger.info("mysql config={}", config().getJsonObject("mysql").getJsonObject("mc-im").encode());
		mySQLClient = MySQLClient.createShared(vertx, config().getJsonObject("mysql").getJsonObject("mc-im"));

		logger.info("mysql client={}", mySQLClient);

		eb.<JsonObject>consumer(QuickPhraseVerticle.class.getName(), res -> {
			MultiMap headers = res.headers();
			JsonObject body = res.body();
			if (headers != null) {
				String action = headers.get("action");
				String userId = body.getString("userID");
				logger.info("start ... body={}", body.encode());
				switch (action) {
				case "addQuickPhrase":

					int identity = Integer.valueOf(body.getString("identity")).intValue();
					String content = body.getString("content");
					addQuickPhrase(userId, identity, content, resultHandler -> {
						res.reply(resultHandler.result());
					});
					break;

				case "delQuickPhrase":
					long id = body.getLong("id");
					delQuickPhrase(id, resultHandler -> {
						res.reply(resultHandler.result());
					});
					break;

				case "getQuickPhrase":
					getQuickPhrase(userId, resultHandler -> {
						res.reply(resultHandler.result());
					});
					break;
				default:
					res.reply(1);// Fail!
					break;
				}
			}
		});
	}

	private static final String sql_addQuickPhrase = "insert into quick_phrase (userId, identity, content, createTime) values (?, ?, ?, ?)";

	private void addQuickPhrase(String userId, int identity, String content,
			Handler<AsyncResult<JsonObject>> resultHandler) {
		if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(content)) {
			result.clear();// Must do clear before use it!
			params.clear();// Must do clear before use it!
			mySQLClient.getConnection(res -> {
				if (res.succeeded()) {
					SQLConnection connection = res.result();
					params.add(userId).add(identity).add(content).add(System.currentTimeMillis());
					connection.updateWithParams(sql_addQuickPhrase, params, SQLRes -> {
						if (SQLRes.succeeded()) {
							resultHandler.handle(Future.succeededFuture(result.put("result", SQLRes.result())));
						} else {
							resultHandler.handle(Future.succeededFuture(result.put("result", SQLRes.result())));
						}
					});
				} else {
					resultHandler.handle(Future.succeededFuture(result.put("result", res.succeeded())));
				}
			}).close();
		} else {
			logger.error("addQuickPhrase, userId={}identity={}content={}", userId, identity, content);
		}
	}

	private static final String sql_delQuickPhrase = "delete from quick_phrase where id = ?";

	private void delQuickPhrase(long id, Handler<AsyncResult<JsonObject>> resultHandler) {
		result.clear();// Must do clear before use it!
		params.clear();// Must do clear before use it!
		mySQLClient.getConnection(res -> {
			if (res.succeeded()) {
				SQLConnection connection = res.result();
				params.add(id);
				connection.updateWithParams(sql_delQuickPhrase, params, SQLRes -> {
					if (SQLRes.succeeded()) {
						resultHandler.handle(Future.succeededFuture(result.put("result", SQLRes.result())));
					} else {
						resultHandler.handle(Future.succeededFuture(result.put("result", SQLRes.result())));
					}
				});
			} else {
				resultHandler.handle(Future.succeededFuture(result.put("result", res.succeeded())));
			}
		}).close();
	}

	private static final String sql_getQuickPhrase = "select (id, userId, identity, content, createTime) from quick_phrase where userId = ?";

	private void getQuickPhrase(String userId, Handler<AsyncResult<JsonObject>> resultHandler) {
		result.clear();// Must do clear before use it!
		params.clear();// Must do clear before use it!
		mySQLClient.getConnection(res -> {
			if (res.succeeded()) {
				SQLConnection connection = res.result();
				params.add(userId);
				logger.info("getQickPhrase, params={}", params.encode());
				connection.queryWithParams(sql_getQuickPhrase, params, SQLRes -> {
					if (SQLRes.succeeded()) {
						logger.info("getQickPhrase, result={}", SQLRes.result().getRows());
						resultHandler.handle(Future.succeededFuture(result.put("result", SQLRes.result().getRows())));
					} else {
						resultHandler.handle(Future.succeededFuture(result.put("result", SQLRes.result())));
					}
				});
			} else {
				resultHandler.handle(Future.succeededFuture(result.put("result", res.succeeded())));
			}
		}).close();
	}
}
