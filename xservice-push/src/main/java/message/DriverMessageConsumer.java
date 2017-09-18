package message;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import service.DriverService;

import java.util.List;

/**
 * Created by lufei
 * Date : 2017/8/22 18:07
 * Description :
 */
public class DriverMessageConsumer extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(DriverMessageConsumer.class);

    private DefaultMQPushConsumer consumer;

    private DriverService driverService;


    @Override
    public void start() throws Exception {
        consumer = new DefaultMQPushConsumer("driveConsumerGroup");
        consumer.setNamesrvAddr(config().getJsonObject("rocketMq.config").getString("drive.namesrvAddr"));
        consumer.setInstanceName("consumer.mc.drive");
        consumer.subscribe("driver_info", "*");
        consumer.registerMessageListener(new MessageListener());
        consumer.start();
        driverService = DriverService.createProxy(vertx);
    }

    private class MessageListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            logger.info(Thread.currentThread().getName() + " Receive New Messages: " + msgs.size());
            MessageExt msg = msgs.get(0);
            logger.info(" Receive New Messages: " + new String(msg.getBody()));
            JsonObject driver = new JsonObject(new String(msg.getBody()));
            driverService.saveDriver(driver, result -> {
                if (result.failed()) {
                    logger.error("store driver:{} to db error.", driver, result.cause());
                }
            });
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }

}
