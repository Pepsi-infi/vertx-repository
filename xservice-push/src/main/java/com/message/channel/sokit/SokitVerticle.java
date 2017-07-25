package com.message.channel.sokit;

import com.message.constant.EnumPassengerMessageType;
import com.message.constant.MsgConstant;
import com.message.serializer.ByteUtils;
import com.message.util.ApplicationProperties;
import com.message.util.JsonUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.apache.commons.lang.StringUtils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

/**
 * 使用 sokit 处理消息
 * Created by weim on 2017/7/25.
 */
public class SokitVerticle extends AbstractVerticle {

    private Logger logger = LoggerFactory.getLogger(SokitVerticle.class);

    public void start(Future<Void> startFuture) {

        Map<String, Object> msg = new HashMap<>();

    }

    public void stop(Future stopFuture) throws Exception {
        System.out.println("MyVerticle stopped!");
    }

    /**
     *
     * @param messageType
     * @param msg
     */
    public void sendMsg(EnumPassengerMessageType messageType, Map<String, Object> msg) {

        Integer customerId = Integer.parseInt(msg.get("customerId") + "");
        Long expireTime = Long.parseLong(msg.get("expireTime") + "");
        String token = null;
        String msgId = createMsgId();
        Integer seconds = (expireTime != null) ? (int) (expireTime - System.currentTimeMillis()) / 1000 : 600;

        Map<String, Object> msgInfo = new HashMap<>();
        msgInfo.put("nick", null);
        msgInfo.put("msgId", msgId);
        msgInfo.put("title", messageType.getName());
        msgInfo.put("body", JsonUtil.toJsonString(msg));
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
            String[] Host = ApplicationProperties.tcpHostPassenger();
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
            logger.error(e.getMessage());
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

}
