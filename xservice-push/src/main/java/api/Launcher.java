package api;

import channel.HttpConsumerVerticle;
import channel.MiPushVerticle;
import dao.impl.MsgRecordDaoImpl;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import service.impl.MsgRecordServiceImpl;
import service.impl.RedisServiceImpl;

public class Launcher extends AbstractVerticle{

	private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();

		vertx.deployVerticle(MsgRecordDaoImpl.class.getName());
		vertx.deployVerticle(MsgRecordServiceImpl.class.getName());
		vertx.deployVerticle(RedisServiceImpl.class.getName());
		
		vertx.deployVerticle(MiPushVerticle.class.getName());
//		vertx.deployVerticle(SocketVerticle.class.getName());

		//vertx.deployVerticle(AmqpConsumerVerticle.class.getName());
		vertx.deployVerticle(HttpConsumerVerticle.class.getName());
	}

}
