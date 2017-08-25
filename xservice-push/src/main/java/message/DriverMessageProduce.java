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

        DefaultMQProducer producer = new DefaultMQProducer("DriverProducerGroupName");
        producer.setNamesrvAddr("192.168.0.16:9876");
        producer.setInstanceName("Producer");
        producer.start();
        for (int i = 2000; i < 10000; i++) {
            String ss = "{\"carNumber\":\"京BZ0959\",\"carType\":\"null\",\"city\":\"北京\",\"cityId\":44,\"createBy\":1,\"driverId\":" + i + ",\"driverName\":\"路家豪_test\",\"driverPhone\":\"15613566958\",\"status\":1,\"supplierFullName\":\"北京祥龙出租客运有限公司\",\"supplierId\":51}";
            Message msg = new Message("driver_info_test", ss.getBytes());// body
            SendResult sendResult = producer.send(msg);
            System.out.println(sendResult);
        }

    }

}
