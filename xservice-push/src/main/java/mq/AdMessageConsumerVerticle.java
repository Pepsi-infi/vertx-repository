package mq;

import java.util.List;
import java.util.Random;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import service.AdMessagePushService;

/**
 * 广告消息消费
 * @author yanglf
 *
 */
public class AdMessageConsumerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(AdMessageConsumerVerticle.class);

	private DefaultMQPushConsumer adMsgConsumer;

	private AdMessagePushService adMessagePushService;

	@Override
	public void start() throws Exception {
		super.start();
		//初始化service
		initService();
		//初始化consumer
		initConsumer();
	}

	private void initService() {
		adMessagePushService = AdMessagePushService.createLocalProxy(vertx);
		logger.info("AdMessageConsumerVerticle service init success");
	}

	private void initConsumer() throws Exception {
		int number = new Random().nextInt(1000);
		adMsgConsumer = new DefaultMQPushConsumer("mcAdMessageGroup" + number);
		adMsgConsumer.setNamesrvAddr(config().getJsonObject("rocketMq.config").getString("mc.message.namesrvAddr"));
		adMsgConsumer.setInstanceName("mcmessage" + number);
		adMsgConsumer.subscribe(config().getJsonObject("rocketMq.config").getString("mc.ad.message.topic"), "*");
		adMsgConsumer.registerMessageListener(new MessageListener());
		adMsgConsumer.start();
		logger.info("AdMessageConsumerVerticle mq AdMsgConsumer init success");
	}

	private class MessageListener implements MessageListenerConcurrently {

		@Override
		public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

			MessageExt msg = msgs.get(0);

			if (msg == null) {
				return ConsumeConcurrentlyStatus.RECONSUME_LATER;
			}

			String httpMsg = new String(msg.getBody());
			Future<String> future = Future.future();
			adMessagePushService.pushMsg(httpMsg, future.completer());
			
			future.setHandler(handler -> {
				if (handler.succeeded()) {
					
				} else {
					logger.info("消费失败,消费数据：" + httpMsg + "失败原因：" + handler.cause().getMessage());
				}
			});
			
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}

	}

}
