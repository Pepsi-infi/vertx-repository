package com.message.channel.socket;

import com.message.constant.MsgConstant;
import com.message.constant.PushConsts;
import com.message.enums.EnumPassengerMessageType;
import com.message.enums.PushTypeEnum;
import com.message.serializer.ByteUtils;
import com.message.util.ApplicationProperties;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.eventbus.MessageProducer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.apache.commons.lang.StringUtils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

/**
 * 使用 socket 处理消息
 * Created by weim on 2017/7/25.
 */
public class SocketVerticle extends AbstractVerticle {

    private Logger logger = LoggerFactory.getLogger(SocketVerticle.class);

    private JsonObject recieveMsg;

    public void start() {

        MessageConsumer<String> message = vertx.eventBus().consumer(PushConsts.PUSH_CHANNEL_VERTICLE_PREFIX + PushTypeEnum.SOCKET.getCode());
        message.handler(handler ->{
            String msg = handler.body();
            if(StringUtils.isBlank(msg)){
               logger.info("socket接收到的数据为空");
                return;
            }
            recieveMsg = JsonObject.mapFrom(msg);
            sendMsg(recieveMsg);
        });

    }

    public void stop() throws Exception {
//        logger.info("socket verticle stop!");
    }

    /**
     *
     * @param msg
     */
    public void sendMsg(JsonObject msg) {

        EnumPassengerMessageType messageType = EnumPassengerMessageType.ADVERTISEMENT;

        Object customerId = recieveMsg.getValue("customerId");
        Object expireTime = recieveMsg.getValue("expireTime");
        String token = null;
        String msgId = createMsgId();
        Integer seconds = (expireTime != null) ? (int) ((Long)expireTime - System.currentTimeMillis()) / 1000 : 600;

        Map<String, Object> msgInfo = new HashMap<>();
        msgInfo.put("nick", null);
        msgInfo.put("msgId", msgId);
        msgInfo.put("title", messageType.getName());
        msgInfo.put("body", msg.toString());
        if (StringUtils.isNotBlank(token) && token.length() > 10) {
            msgInfo.put("token", token);
        }

        List<Object> params = new ArrayList<Object>();
        params.add(customerId);
        params.add(messageType.getType());
        params.add(MsgConstant.ZERO);
        params.add(msgInfo);

        Map<String, Object> sendMap = new HashMap<String, Object>();
        DatagramSocket client = null;
        try {
            String[] Host = ApplicationProperties.udpHostPassenger();
            sendMap.put("method", MsgConstant.SendMethod.SEND_MSG.getMsg());
            sendMap.put("params", params);
            byte[] sendBuf = ByteUtils.objectToByte(sendMap);
            client = new DatagramSocket();
            InetAddress addr = InetAddress.getByName(Host[0]);
            int port = Integer.valueOf(Host[1]);
            DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, addr, port);
            client.send(sendPacket);
            logger.info("{} udp host:{} {} msg: {}",MsgConstant.SendMethod.SEND_MSG.getMsg(),
                    Host[0], Host[1], new String(sendBuf, "UTF-8"));
        } catch (Exception e) {
            logger.error("socket推送消息出错" + e.getMessage(),e);
        } finally {
            if (client != null) {
                client.close();
            }
//            clearData(params);
        }
    }

    private String createMsgId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    public static void main(String[] args) {
        Vertx vertx1 = Vertx.vertx();
        vertx1.deployVerticle("com.message.channel.socket.SocketVerticle");

        MessageProducer<String> mp = vertx1.eventBus().sender(PushConsts.PUSH_CHANNEL_VERTICLE_PREFIX+PushTypeEnum.SOCKET.getCode());

        String json = "{\n" +
                "  \"customerId\": 111111,\n" +
                "  \"expireTime\": 1501032894,\n" +
                "  \"msg\": \"\\\"我是消息内容，阿斯蒂芬工阿斯蒂芬 阿斯蒂芬 模压模压硒鼓 \\\"\",\n" +
                "  \"token\": \"\"\n" +
                "}";
        mp.send(json);
    }
}
