package service.impl;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import domain.DriverMsg;
import domain.Page;
import dto.DriverMsgDto;
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
import service.DriverMsgService;
import utils.IPUtil;
import xservice.BaseServiceVerticle;

public class DriverMsgServiceImpl extends BaseServiceVerticle implements DriverMsgService {

	private final Logger logger = LoggerFactory.getLogger(DriverMsgServiceImpl.class);
	private SQLClient sqlClient;

	@Override
	public void start() throws Exception {
		super.start();
		XProxyHelper.registerService(DriverMsgService.class, vertx, this, DriverMsgService.SERVICE_ADDRESS);
		publishEventBusService(DriverMsgService.SERVICE_NAME, DriverMsgService.SERVICE_ADDRESS, DriverMsgService.class);

		String ip = IPUtil.getInnerIP();
		XProxyHelper.registerService(DriverMsgService.class, vertx, this, DriverMsgService.getLocalAddress(ip));
		publishEventBusService(DriverMsgService.LOCAL_SERVICE_NAME, DriverMsgService.getLocalAddress(ip),
				DriverMsgService.class);
		JsonObject mysqlOptions = config().getJsonObject("mysql.config");
		sqlClient = MySQLClient.createNonShared(vertx, mysqlOptions);
		//MySQLClient.create

	}

	public interface Sql {
		
		String ADD = "insert into driver_msg "
				+ "(title,synopsis,content,is_shells_screen,status,msg_type,jump_url,is_important,enabled,create_user,update_user,create_time,update_time) "
				+ "values " + ("?,?,?,?,?,?,?,?,?,'admin','admin','now()','now()'");
		
		String SELECT_PAGE = "select "
				+ "(title,synopsis,content,is_shells_screen as isShellsScreen,status,msg_type as msgType,jump_url as jumpUrl,is_important as isImportant,enabled,create_user as createUser,update_user as updateUser,create_time as createTime,update_time as updateTime) "
				+ "from driver_msg where 1=1 %s ";
		
		String SELECT_COUNT = "select count(1) from driver_msg where 1=1 %s";
		
		String SELECT_ONE = "select "
				+ "(id,title,synopsis,content,is_shells_screen as isShellsScreen,status,msg_type as msgType,jump_url as jumpUrl,is_important as isImportant,enabled,create_user as createUser,update_user as updateUser,create_time as createTime,update_time as updateTime) "
				+ "from driver_msg where 1=1 and id=? ";

	}

	@Override
	public void addDriverMsg(JsonObject dto, Handler<AsyncResult<Integer>> resultHandler) {

		JsonArray params = new JsonArray();
		params.add(dto.getString("title")).add(dto.getString("synopsis")).add(dto.getString("content"))
				.add(dto.getInteger("isShellsScreen")).add(dto.getInstant("status")).add(dto.getInteger("msgType"))
				.add(dto.getString("jumpUrl")).add(dto.getInteger("isImportant"));

		resultHandler = this.add(Sql.ADD, params);

	}

	private Handler<AsyncResult<Integer>> add(String sql, JsonArray params) {
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

	public void selectByPage(JsonObject dto, Handler<AsyncResult<Page>> resultHandler) {

		JsonArray params = new JsonArray();

		String countSql = buildSql(Sql.SELECT_COUNT, dto);
		String pageSql = buildPageSql(Sql.SELECT_PAGE, dto);
		
		Future<Long> countFuture = this.queryTotalCount(countSql, params);
		Future<List<JsonObject>> listFuture = this.queryList(pageSql, params);

		this.buildPageResult(new DriverMsgDto(), countFuture, listFuture, resultHandler);

	}

	private void buildPageResult(DriverMsgDto dto, Future<Long> countFuture, Future<List<JsonObject>> listFuture,
			Handler<AsyncResult<Page>> resultHandler) {
		CompositeFuture comFuture = CompositeFuture.all(countFuture, listFuture);
		comFuture.setHandler(handler -> {
			if (handler.succeeded()) {

				List<JsonObject> jsonList = handler.result().resultAt(0);
				List<DriverMsg> dtoList = Lists.transform(jsonList, new Function<JsonObject, DriverMsg>() {
					@Nullable
					@Override
					public DriverMsg apply(@Nullable JsonObject jsonObject) {
						return jsonObject.mapTo(DriverMsg.class);
					}
				});

				Long totalCount = handler.result().resultAt(1);
				Page page = new Page(dto.getPageNumber(), dto.getPageSize(), dtoList, totalCount);

				resultHandler.handle(Future.succeededFuture(page));

			} else {
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
					future.fail(res.cause());
				}
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

	private String buildPageSql(String sql, JsonObject dto) {

		if (dto == null) {
			return String.format(sql, "");
		}

		StringBuffer sb = new StringBuffer();
		String title = dto.getString("title");
		Integer msgType = dto.getInteger("msgType");
		if (!StringUtil.isNullOrEmpty(title)) {
			sb.append("and title= ").append("'" + title + "' ");
		}
		if (null != msgType) {
			sb.append("and msg_type= ").append("'" + title + "' ");
		}

		Integer pageNumber = (dto.getInteger("pageNumber") == null || dto.getInteger("pageNumber") == 0) ? 1
				: dto.getInteger("pageNumber");// 页码有问题默认传第一页
		Integer pageSize = (dto.getInteger("pageSize") == null || dto.getInteger("pageSize") == 0) ? 10
				: dto.getInteger("pageSize");// 默认用10条每页

		int pageBegin = (pageNumber - 1) * pageSize;
		int offset = pageSize;

		sb.append("limit ").append("'" + pageBegin + ",' ").append("'").append(offset).append("'");

		return String.format(sql, sb.toString());
	}

	private String buildSql(String sql, JsonObject dto) {
		if (dto == null) {
			return String.format(sql, "");
		}
		StringBuffer sb = new StringBuffer();
		String title = dto.getString("title");
		Integer msgType = dto.getInteger("msgType");
		if (!StringUtil.isNullOrEmpty(title)) {
			sb.append("and title= ").append("'" + title + "' ");
		}
		if (null != msgType) {
			sb.append("and msg_type= ").append("'" + msgType + "' ");
		}
		return String.format(sql, sb.toString());
	}

	protected Future<SQLConnection> getConnection() {
		Future<SQLConnection> future = Future.future();
		sqlClient.getConnection(future.completer());
		return future;
	}

	@Override
	public void getDriverMsgDetail(Long id, Handler<AsyncResult<DriverMsg>> resultHandler) {					
		JsonArray params=new JsonArray();
		params.add(id);
		Future<DriverMsg> future=this.getOne(Sql.SELECT_ONE,params);
		future.setHandler(handler->{
			if(handler.succeeded()){
				resultHandler.handle(Future.succeededFuture(handler.result()));
			}else{
				resultHandler.handle(Future.failedFuture(handler.cause()));
			}
		});
	}

	private Future<DriverMsg> getOne(String sql, JsonArray params) {
		return getConnection().compose(conn->{
			Future<DriverMsg> future=Future.future();
			conn.queryWithParams(sql, params, res->{
				if(res.succeeded()){
					List<JsonObject> list= res.result().getRows();
					if(list!=null&&list.size()>0){
						future.complete(new DriverMsg(list.get(0)));
					}else{
						future.complete(null);
					}
				}else{
					future.fail(res.cause());
				}
				
			});
			return future;
		});
	}

}
