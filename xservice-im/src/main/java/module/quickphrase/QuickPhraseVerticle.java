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

	public static interface method {
		public static final String addQuickPhrase = "addQuickPhrase";
		public static final String delQuickPhrase = "delQuickPhrase";
		public static final String getQuickPhrase = "getQuickPhrase";
	}

	@Override
	public void start() throws Exception {
		super.start();

		eb = vertx.eventBus();
		logger.info("mysql config={}", config().getJsonObject("mysql").getJsonObject("mc-im").encode());
		mySQLClient = MySQLClient.createShared(vertx, config().getJsonObject("mysql").getJsonObject("mc-im"));

		eb.<JsonObject>consumer(QuickPhraseVerticle.class.getName(), res -> {
			MultiMap headers = res.headers();
			JsonObject body = res.body();
			if (headers != null) {
				String action = headers.get("action");
				Integer userId = null;
				int identity = 0;
				logger.info("start ... body={}", body.encode());
				switch (action) {
				case method.addQuickPhrase:
					userId = body.getInteger("userID");
					String content = body.getString("content");
					identity = body.getInteger("identity");
					String title = body.getString("title");
					addQuickPhrase(userId, identity, title, content, resultHandler -> {
						res.reply(resultHandler.result());
					});
					break;

				case method.delQuickPhrase:
					String ids = body.getString("ids");
					delQuickPhrase(ids, resultHandler -> {
						res.reply(resultHandler.result());
					});
					break;

				case method.getQuickPhrase:
					userId = body.getInteger("userID");
					identity = body.getInteger("identity");
					getQuickPhrase(userId, identity, resultHandler -> {
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

	private static final String sql_addQuickPhrase = "insert into quick_phrase (userId, identity, content, createTime, title) values (?, ?, ?, ?, ?)";

	private void addQuickPhrase(Integer userId, int identity, String title, String content,
			Handler<AsyncResult<JsonObject>> resultHandler) {
		logger.info("addQuickPhrase, userId={},identity={},content={},", userId, identity, content);
		if (userId != null && StringUtils.isNotEmpty(content)) {
			result.clear();// Must do clear before use it!
			params.clear();// Must do clear before use it!
			mySQLClient.getConnection(res -> {
				if (res.succeeded()) {
					SQLConnection connection = res.result();
					long createTime = System.currentTimeMillis();
					params.add(userId).add(identity).add(content).add(createTime);
					if (StringUtils.isNotEmpty(title)) {
						params.add(title);
					} else {
						params.addNull();
					}
					connection.updateWithParams(sql_addQuickPhrase, params, sqlRes -> {
						JsonObject sqlResJson = new JsonObject();
						if (sqlRes.succeeded()) {
							logger.info("操作数据库条数="+sqlRes.result().getUpdated());
							sqlResJson.put("result", sqlRes.result().getUpdated());
							//设置标识数据库更新成功
							sqlResJson.put("flag",true);
							resultHandler.handle(Future.succeededFuture(sqlResJson));
						} else {
							sqlRes.cause().printStackTrace();
							sqlResJson.put("result", sqlRes.cause().getMessage());
							//设置标识数据库更新失败
							sqlResJson.put("flag",false);
							resultHandler.handle(Future.succeededFuture(sqlResJson));
							//resultHandler.handle(Future.failedFuture(SQLRes.cause()));
						}
					}).close();
				} else {
					resultHandler.handle(Future.succeededFuture(result.put("result", res.succeeded())));
				}
			});
		} else {
			resultHandler.handle(Future.succeededFuture(result.put("result", 1)));
			logger.error("addQuickPhrase, userId={}identity={}content={}", userId, identity, content);
		}
	}

	private void delQuickPhrase(String ids, Handler<AsyncResult<JsonObject>> resultHandler) {
		result.clear();// Must do clear before use it!
		logger.info("delQuickPhrase, ids={}", ids);
		mySQLClient.getConnection(res -> {
			if (res.succeeded()) {
				SQLConnection connection = res.result();
				String sql_delQuickPhrase = "delete from quick_phrase where id in (" + ids + ")";
				connection.update(sql_delQuickPhrase, SQLRes -> {
					if (SQLRes.succeeded()) {
						resultHandler.handle(Future.succeededFuture(result.put("result", SQLRes.result())));
					} else {
						resultHandler.handle(Future.succeededFuture(result.put("result", SQLRes.succeeded())));
					}
				}).close();
			} else {
				resultHandler.handle(Future.succeededFuture(result.put("result", res.succeeded())));
			}
		});
	}

	private static final String sql_getQuickPhrase = "select id, userId, identity, title, content, createTime from quick_phrase where userId = ? and identity = ?";

	private void getQuickPhrase(Integer userId, int identity, Handler<AsyncResult<JsonObject>> resultHandler) {
		result.clear();// Must do clear before use it!
		params.clear();// Must do clear before use it!
		mySQLClient.getConnection(res -> {
			if (res.succeeded()) {
				SQLConnection connection = res.result();
				params.add(userId).add(identity);
				logger.info("getQickPhrase, params={}", params.encode());
				connection.queryWithParams(sql_getQuickPhrase, params, SQLRes -> {
					JsonObject sqlResJson = new JsonObject();
					if (SQLRes.succeeded()) {
						logger.info("getQickPhrase, result={}", SQLRes.result().getRows());
						//设置标识数据库查询成功
						sqlResJson.put("flag",true);
						sqlResJson.put("result", SQLRes.result().getRows());
						resultHandler.handle(Future.succeededFuture(sqlResJson));
					} else {
						logger.error("getQickPhrase, result={}", SQLRes.cause().getMessage());
						//设置标识数据库查询失败
						sqlResJson.put("flag",false);
						sqlResJson.put("result", SQLRes.cause().getMessage());
						resultHandler.handle(Future.succeededFuture(sqlResJson));
					}
				}).close();
			} else {
				logger.error("getConnection, {}", res.cause().getMessage());
				resultHandler.handle(Future.succeededFuture(result.put("result", res.succeeded())));
			}
		});
	}

}
