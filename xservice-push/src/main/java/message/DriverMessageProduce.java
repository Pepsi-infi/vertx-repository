package message;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

/**
 * Created by lufei
 * Date : 2017/8/22 18:10
 * Description :
 */
public class DriverMessageProduce extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
        producer.setNamesrvAddr("192.168.0.16:9876");
        producer.setInstanceName("Producer");
        Message msg = new Message("TopicTest123456",// topic
                "TagA",// tag
                "OrderID001",// key
                ("Hello rocketmq TagA---").getBytes());// body
        producer.start();
        SendResult sendResult = producer.send(msg);
        System.out.println(sendResult);
    }

}
