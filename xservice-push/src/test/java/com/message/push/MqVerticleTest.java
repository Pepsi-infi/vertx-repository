package com.message.push;

import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;

import io.vertx.core.AbstractVerticle;

public class MqVerticleTest extends AbstractVerticle {
	
	@Override
	public void start() throws Exception {
		super.start();
		this.initConsumer();
		this.initProducer();
	}

	private void initConsumer() throws Exception {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("mcAdMessageGroup");
        consumer.setNamesrvAddr(config().getJsonObject("rocketMq.config").getString("mc.message.namesrvAddr"));
        consumer.setInstanceName("mcmessage");
        consumer.subscribe("mc_ad_message", "*");
        consumer.registerMessageListener(new MessageListener());
        consumer.start();		
	}
	
	private class MessageListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            System.out.println(Thread.currentThread().getName() + " Receive New Messages: " + msgs.size());
            MessageExt msg = msgs.get(0);
            System.out.println(" Receive New Messages: " + new String(msg.getBody()));
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }

	private void initProducer() throws Exception {
		DefaultMQProducer producer=new DefaultMQProducer("mcAdMessageGroup");
		producer.setNamesrvAddr(config().getJsonObject("rocketMq.config").getString("mc.message.namesrvAddr"));
		producer.setInstanceName("mcmessage");
		try {
			producer.start();
		} catch (MQClientException e) {
			System.out.println("rocket mq producer init error");
			e.printStackTrace();
			return;
		}
		
		for(int i=0;i<10;i++){
			String s="好好学习"+i;
			Message msg=new Message(config().getJsonObject("rocketMq.config").getString("mc.ad.message.topic"), s.getBytes());
			SendResult result=producer.send(msg);
			System.out.println("返回结果:"+result);
		}
		
	}

}
