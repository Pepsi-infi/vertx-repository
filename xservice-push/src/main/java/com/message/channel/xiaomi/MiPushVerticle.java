package com.message.channel.xiaomi;

import com.message.constant.PushConsts;
import com.message.constant.PushTypeEnum;

import io.netty.util.internal.StringUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * 
 * @author yanglf
 * 
 *    小米推送
 * 
 */
public class MiPushVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(MiPushVerticle.class);
	
	private EventBus eventBus;
	
	private JsonObject recieveMsg;
	

	@Override
	public void start() throws Exception {

		eventBus=vertx.eventBus();
		
		MessageConsumer<String> message=eventBus.consumer(PushConsts.AD_PASSENGER_MSG_PREFIX+PushTypeEnum.XIAOMI.getCode());
		
		message.handler(handler->{
			
			String msg=handler.body();
			if(StringUtil.isNullOrEmpty(msg)){
				logger.error("小米推送接受数据为空");
			}
			
			recieveMsg=JsonObject.mapFrom(msg);
			
		});
		
		sendMsg(recieveMsg);
		
	}


	private void sendMsg(JsonObject recieveMsg2) {
		// TODO Auto-generated method stub
		
	}

}
