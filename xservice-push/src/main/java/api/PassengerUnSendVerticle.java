package api;

import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import rxjava.RestAPIVerticle;
import service.PassengerUnSendService;

/**
 *  清除未发送出去的过期消息，定时清除
 */
public class PassengerUnSendVerticle extends RestAPIVerticle {

	private static final Logger logger = LoggerFactory.getLogger(PassengerUnSendVerticle.class);

	PassengerUnSendService passengerUnSendService;
	@Override
	public void start() throws Exception {
		this.initService();

		vertx.setTimer(10000, id -> {
			logger.info("执行清除已过期的未发送消息定时任务启动，执行间隔10秒");
			this.delExpireUnSendMsg();
		});

	}

	private void initService(){
		passengerUnSendService = PassengerUnSendService.createProxy(vertx.getDelegate());
	}

	private void delExpireUnSendMsg(){
		Future<Integer> future = Future.future();
		passengerUnSendService.delExpireUnSendMsg(future.completer());
		future.setHandler(res -> {
			if(res.succeeded()){
				int count = res.result();
				if(count > 0){
					logger.info("清除已过期的未发送消息已完成，清除条数：" + res.result());
				}
			} else {
				logger.info("清除已过期的未发送消息出错，", res.cause());
			}
		});
		vertx.setTimer(10000, id -> {
			delExpireUnSendMsg();
		});
	}

//	private static int index = 1000;
//	private void addUnSendMsg(){
//		Future<Integer> future = Future.future();
//		JsonObject param = new JsonObject();
//		param.put("msgId", ++index + "");
//		param.put("phone", "13621241006");
//		param.put("userId", "136556");
//		param.put("expireTime", DateUtil.getDateTime(System.currentTimeMillis() + 2000));
//		passengerUnSendService.addUnSendMsg(param, future);
//		future.setHandler(res -> {
//			if(res.succeeded()){
//				logger.info("新增未发送成功消息,新增条数：" + res.result());
//			} else {
//				logger.info("新增未发送成功消息出错，", res.cause());
//			}
//		});
//		vertx.setTimer(3000, id -> {
//			addUnSendMsg();
//		});
//	}
}
