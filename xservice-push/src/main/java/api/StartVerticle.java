package api;

import channel.AmqpConsumerVerticle;
import channel.HttpConsumerVerticle;
import channel.MiPushVerticle;
import dao.impl.DeviceDaoImpl;
import io.vertx.rxjava.core.AbstractVerticle;
import service.impl.RedisServiceImpl;

public class StartVerticle extends AbstractVerticle{
	

    @Override
    public void start() throws Exception {
        super.start();
        // 提供EventBus服务
        

		vertx.deployVerticle(DeviceDaoImpl.class.getName());
		vertx.deployVerticle(RedisServiceImpl.class.getName());
		
		vertx.deployVerticle(MiPushVerticle.class.getName());
//		vertx.deployVerticle(SocketVerticle.class.getName());

		vertx.deployVerticle(AmqpConsumerVerticle.class.getName());
		vertx.deployVerticle(HttpConsumerVerticle.class.getName());
        
        // 提供其他非EventBus服务
    }

}
