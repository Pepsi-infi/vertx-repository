package service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import domain.DriverMsg;
import domain.Page;
import enums.ErrorCodeEnum;
import helper.XProxyHelper;
import io.netty.util.internal.StringUtil;
import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import result.ResultData;
import service.DriverMsgService;
import utils.IPUtil;
import xservice.BaseServiceVerticle;

public class DriverMsgServiceImpl extends BaseServiceVerticle implements DriverMsgService {

	private final Logger logger = LoggerFactory.getLogger(DriverMsgServiceImpl.class);
	private SQLClient sqlClient;

	@Override
	public void start() throws Exception {
		super.start();
		XProxyHelper.registerService(DriverMsgService.class, vertx, this, DriverMsgService.class.getName());
//		publishEventBusService(DriverMsgService.SERVICE_NAME, DriverMsgService.SERVICE_ADDRESS, DriverMsgService.class);

//		String ip = IPUtil.getInnerIP();
//		XProxyHelper.registerService(DriverMsgService.class, vertx, this, DriverMsgService.getLocalAddress(ip));
//		publishEventBusService(DriverMsgService.LOCAL_SERVICE_NAME, DriverMsgService.getLocalAddress(ip),
//				DriverMsgService.class);
		JsonObject mysqlOptions = config().getJsonObject("mysql.config");
		sqlClient = MySQLClient.createNonShared(vertx, mysqlOptions);
	}

	public interface Sql {

		String ADD = "insert into driver_msg "
				+ "(title,synopsis,content,is_shells_screen,msg_type,jump_url,is_important,create_user,update_user,create_time,update_time) "
				+ "values " + "(?,?,?,?,?,?,?,'admin','admin',now(),now())";

		String SELECT_PAGE = "select "
				+ "id,title,synopsis,content,is_shells_screen as isShellsScreen,`status`,msg_type as msgType,jump_url as jumpUrl,is_important as isImportant,enabled,create_user as createUser,update_user as updateUser,create_time as createTime,update_time as updateTime "
				+ "from driver_msg where 1=1 %s";

		String SELECT_COUNT = "select count(1) from driver_msg where 1=1 %s";

		String SELECT_ONE = "select "
				+ "id,title,synopsis,content,is_shells_screen as isShellsScreen,`status`,msg_type as msgType,jump_url as jumpUrl,is_important as isImportant,enabled,create_user as createUser,update_user as updateUser,create_time as createTime,update_time as updateTime "
				+ "from driver_msg where 1=1 and id=? ";

	}

	@Override
	public void addDriverMsg(JsonObject dto,Handler<AsyncResult<Integer>> resultHandler) {

		JsonArray params = new JsonArray();
		params.add(dto.getString("title"))
		      .add(dto.getString("synopsis"))
		      .add(dto.getString("content"))
			  .add(Integer.valueOf(dto.getString("isShellsScreen")))
			  .add(Integer.valueOf(dto.getString("msgType")))
			  .add(StringUtil.isNullOrEmpty(dto.getString("jumpUrl"))?"":dto.getString("jumpUrl"))
			  .add(Integer.valueOf(dto.getString("isImportant")));

		Future<Integer> addFuture = this.add(Sql.ADD, params);
		addFuture.setHandler(handler->{
			if(handler.succeeded()){							
				resultHandler.handle(Future.succeededFuture(handler.result()));
			}else{
				logger.error("数据新增/更新失败", handler.cause());
				resultHandler.handle(Future.failedFuture(handler.cause()));

			}
		});

	}

	private Future<Integer> add(String sql, JsonArray params) {
		return getConnection().compose(conn -> {
			Future<Integer> future = Future.future();
			conn.updateWithParams(sql, params, res -> {
				if (res.succeeded()) {
					future.complete(res.result().getUpdated());
				} else {
					future.fail(res.cause());
				}
				conn.close();
			});
			return future;
		});
	}

	private String buildAddSql(String add, JsonObject dto) {
		// TODO Auto-generated method stub
		return null;
	}

	public void selectByPage(JsonObject dto, Handler<AsyncResult<String>> resultHandler) {

		JsonArray params = new JsonArray();
		Integer pageNumber = (StringUtil.isNullOrEmpty(dto.getString("page")) || "0".equals(dto.getString("page"))) ? 1
				: Integer.valueOf(dto.getString("page"));// 页码有问题默认传第一页
		Integer pageSize = (StringUtil.isNullOrEmpty(dto.getString("size")) || "0".equals(dto.getString("size"))) ? 10
				: Integer.valueOf(dto.getString("size"));// 页码有问题默认传第一页

		String countSql = buildSql(Sql.SELECT_COUNT, dto, params);
		Future<Long> countFuture = this.queryTotalCount(countSql, params);

		String pageSql = buildPageSql(Sql.SELECT_PAGE, dto, params);
		Future<List<JsonObject>> listFuture = this.queryList(pageSql, params);

		this.buildPageResult(pageNumber, pageSize, countFuture, listFuture, resultHandler);

	}

	private void buildPageResult(Integer pageNumber,Integer pageSize, Future<Long> countFuture, Future<List<JsonObject>> listFuture,
			Handler<AsyncResult<String>> resultHandler) {
		CompositeFuture comFuture = CompositeFuture.all(countFuture, listFuture);
		comFuture.setHandler(handler -> {
			if (handler.succeeded()) {
				Long totalCount = handler.result().resultAt(0);

				List<JsonObject> jsonList = handler.result().resultAt(1);
				List<DriverMsg> dtoList = Lists.transform(jsonList, new Function<JsonObject, DriverMsg>() {
					@Nullable
					@Override
					public DriverMsg apply(@Nullable JsonObject jsonObject) {
						return jsonObject.mapTo(DriverMsg.class);
					}
				});
				Page page = new Page(pageNumber, pageSize, dtoList, totalCount);

				resultHandler.handle(Future.succeededFuture(new ResultData<>(ErrorCodeEnum.SUCCESS, page).toString()));

			} else {
				logger.error("司机消息分页失败", handler.cause());
				resultHandler.handle(Future.failedFuture(handler.cause()));
			}
		});

	}

	private Future<List<JsonObject>> queryList(String pageSql, JsonArray params) {
		return getConnection().compose(conn -> {
			Future<List<JsonObject>> future = Future.future();
			conn.queryWithParams(pageSql, params, res -> {
				if (res.succeeded()) {

					List<JsonObject> list = res.result().getRows();
					future.complete(list);
				} else {
					logger.error("queryList is error:", res.cause());
					future.fail(res.cause());
				}
				conn.close();
			});
			return future;
		});
	}

	private Future<Long> queryTotalCount(String countSql, JsonArray params) {

		return getConnection().compose(conn -> {
			Future<Long> future = Future.future();
			conn.queryWithParams(countSql, params, res -> {
				if (res.succeeded()) {
					List<JsonObject> list = res.result().getRows();
					Long totalCount = list.get(0).getLong("count(1)");
					future.complete(totalCount);
				} else {
					future.fail(res.cause());
				}
				conn.close();
			});
			return future;
		});
	}

	private String buildPageSql(String sql, JsonObject dto, JsonArray params) {

		if (dto == null) {
			return String.format(sql, "");
		}

		StringBuffer sb = new StringBuffer();
		String title = dto.getString("title");
		Integer msgType = StringUtil.isNullOrEmpty(dto.getString("msgType")) ? null
				: Integer.valueOf(dto.getString("msgType"));
		String startTime = dto.getString("startTime");
		String endTime = dto.getString("endTime");
		if (!StringUtil.isNullOrEmpty(title)) {
			sb.append("and title=? ");
		}
		if (null != msgType) {
			sb.append("and msg_type=? ");
		}
		if (!StringUtil.isNullOrEmpty(startTime)) {
			sb.append("and create_time >=? ");
		}
		if (!StringUtil.isNullOrEmpty(endTime)) {
			sb.append("and create_time <=? ");
		}		
		Integer pageNumber = (StringUtil.isNullOrEmpty(dto.getString("page")) || "0".equals(dto.getString("page"))) ? 1
				: Integer.valueOf(dto.getString("page"));// 页码有问题默认传第一页
		Integer pageSize = (StringUtil.isNullOrEmpty(dto.getString("size")) || "0".equals(dto.getString("size"))) ? 10
				: Integer.valueOf(dto.getString("size"));// 页码有问题默认传第一页
		int pageBegin = (pageNumber - 1) * pageSize;
		int offset = pageSize;
		sb.append("order by update_time desc ")
		  .append("limit ").append(pageBegin).append(",").append(offset);

		return String.format(sql, sb.toString());
	}

	private String buildSql(String sql, JsonObject dto, JsonArray params) {
		if (dto == null) {
			return String.format(sql, "");
		}
		StringBuffer sb = new StringBuffer();
		String title = dto.getString("title");
		Integer msgType = StringUtil.isNullOrEmpty(dto.getString("msgType")) ? null
				: Integer.valueOf(dto.getString("msgType"));
		String startTime = dto.getString("startTime");
		String endTime = dto.getString("endTime");
		if (!StringUtil.isNullOrEmpty(title)) {
			sb.append("and title=? ");
			params.add(title);
		}
		if (null != msgType) {
			sb.append("and msg_type=? ");
			params.add(msgType);
		}
		if (!StringUtil.isNullOrEmpty(startTime)) {
			sb.append("and create_time >=? ");
			params.add(startTime);
		}
		if (!StringUtil.isNullOrEmpty(endTime)) {
			sb.append("and create_time <=?");
			params.add(endTime);
		}
		return String.format(sql, sb.toString());
	}

	protected Future<SQLConnection> getConnection() {
		Future<SQLConnection> future = Future.future();
		sqlClient.getConnection(future.completer());
		return future;
	}

	@Override
	public void getDriverMsgDetail(Long id, Handler<AsyncResult<JsonObject>> resultHandler) {
		JsonArray params = new JsonArray();
		params.add(id);
		Future<JsonObject> future = this.getOne(Sql.SELECT_ONE, params);
		future.setHandler(handler -> {
			if (handler.succeeded()) {
				resultHandler.handle(Future.succeededFuture(handler.result()));
			} else {
				resultHandler.handle(Future.failedFuture(handler.cause()));
			}
		});
	}

	private Future<JsonObject> getOne(String sql, JsonArray params) {
		return getConnection().compose(conn -> {
			Future<JsonObject> future = Future.future();
			conn.queryWithParams(sql, params, res -> {
				if (res.succeeded()) {
					List<JsonObject> list = res.result().getRows();
					if (list != null && list.size() > 0) {
						future.complete(list.get(0));
					} else {
						future.complete(null);
					}
				} else {
					future.fail(res.cause());
				}
				conn.close();
			});
			return future;
		});
	}
	
	
	@Override
	public void sendDriverMsg(Map<String, String> driverMsg, Handler<AsyncResult<String>> resultHandler) {
		
		resultHandler.handle(Future.succeededFuture("成功"));
		
	}


}
