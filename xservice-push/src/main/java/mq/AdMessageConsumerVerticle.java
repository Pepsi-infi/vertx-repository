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

public class AdMessageConsumerVerticle extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(AdMessageConsumerVerticle.class);

	private DefaultMQPushConsumer adMsgConsumer;

	private boolean consumerFlag = true;

	private AdMessagePushService adMessagePushService;

	@Override
	public void start() throws Exception {
		super.start();
		initService();
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
			MessageExt msg = null;

			if (!consumerFlag) {
				return ConsumeConcurrentlyStatus.RECONSUME_LATER;
			}

			msg = msgs.get(0);

			if (msg == null) {
				return ConsumeConcurrentlyStatus.RECONSUME_LATER;
			}

			consumerFlag = false;

			String httpMsg = new String(msg.getBody());
			logger.info("开始消费数据：" + httpMsg);
			Future<String> future = Future.future();
			adMessagePushService.pushMsg(httpMsg, future.completer());
			future.setHandler(handler -> {
				if (handler.succeeded()) {
					logger.info("消费成功，flag变为true");
					consumerFlag = true;
				} else {
					logger.info("消费失败，flag变为false");
					consumerFlag = false;
				}
			});
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}

	}

}
