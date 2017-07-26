package channel;

import domain.ChatMsgVO;
import constant.ConnectionConsts;
import constant.MsgConstant;
import constant.PushConsts;
import enums.EnumPassengerMessageType;
import enums.PushTypeEnum;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.eventbus.MessageProducer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import org.apache.commons.lang.StringUtils;
import serializer.ByteUtils;
import util.JsonUtil;
import util.MsgUtil;
import util.PropertiesLoaderUtils;

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

    public void start() {

        EventBus eb = vertx.eventBus();
        MessageConsumer<String> message = eb.consumer(PushConsts.PUSH_CHANNEL_VERTICLE_PREFIX + PushTypeEnum.SOCKET.getCode());
        message.handler(handler ->{
            String msg = handler.body();
            logger.info(" recived msg : " + msg);
            if(StringUtils.isBlank(msg)){
               logger.info("socket接收到的数据为空");
                return;
            }
            sendMsg(msg);
        });

    }

    public void stop() throws Exception {
        logger.info("socket verticle stop!");
    }

    /**
     *
     * @param msg
     */
    public void sendMsg(String msg) {

        Map revicedMap = JsonUtil.toJavaObject(msg, Map.class);
        EnumPassengerMessageType messageType = EnumPassengerMessageType.ADVERTISEMENT;

        Integer to = (Integer) revicedMap.get("to");
        Integer srcExpireTime = (Integer)revicedMap.get("expireTime");
        Long expireTime = new Long(srcExpireTime);
        String token = null;
        //新生成一个消息id
        String msgId = createMsgId();

        //超时时间： 秒
        Long seconds = (expireTime != null) ? (expireTime - System.currentTimeMillis()) / 1000 : 600;
        //消息内容
        String msgBody = msg;

        //保存msgId对应消息体到redis
        ChatMsgVO chatMsgVO = new ChatMsgVO();
        chatMsgVO.setTo(to);
        chatMsgVO.setMsgTitle(messageType.getName());
        chatMsgVO.setMsgBody(msgBody);
        chatMsgVO.setType(messageType.getType());
        RedisClient redisClient = getRedisClient();
        if (redisClient == null) {
            logger.error("redis服务器连接失败");
        }else{
            redisClient.rpush(PushConsts._MSG_LIST_PASSENGER + to, msgId, r ->{
                if(!r.succeeded()){
                    logger.error("redis设置失败， msgId :" + msgId);
                }
            });
            byte[] objByte = MsgUtil.objectToByte(chatMsgVO);
            String msgStr = new String(objByte);
            redisClient.set(msgId, msgStr, r ->{
                if(!r.succeeded()){
                    logger.error("redis设置失败， msgId :" + msgId);
                }
            });
            redisClient.expire(msgId, seconds, r ->{
                if(!r.succeeded()){
                    logger.error("redis设置失败， msgId :" + msgId);
                }
            });
        }

        Map<String, Object> msgInfo = new HashMap<>();
        msgInfo.put("nick", null);
        msgInfo.put("msgId", msgId);
        msgInfo.put("title", messageType.getName());
        msgInfo.put("body", msgBody);
        if (StringUtils.isNotBlank(token) && token.length() > 10) {
            msgInfo.put("token", token);
        }

        List<Object> params = new ArrayList<Object>();
        params.add(to);
        params.add(messageType.getType());
        params.add(MsgConstant.ZERO);
        params.add(msgInfo);

        Map<String, Object> sendMap = new HashMap<String, Object>();
        DatagramSocket client = null;
        try {
            String socketAddress = PropertiesLoaderUtils.get(ConnectionConsts.SOCKET_SENDTO_ADDRESS);
            String[] Host = socketAddress.split(":");
            sendMap.put("method", MsgConstant.SendMethod.SEND_MSG.getMsg());
            sendMap.put("params", params);
            byte[] sendBuf = ByteUtils.objectToByte(sendMap);
            client = new DatagramSocket();
            InetAddress addr = InetAddress.getByName(Host[0]);
            int port = Integer.valueOf(Host[1]);
            DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, addr, port);
            client.send(sendPacket);
            logger.info(MsgConstant.SendMethod.SEND_MSG.getMsg() + " udp host:" +  Host[0] + ":" + Host[1]+ " msg: " +
                    new String(sendBuf, "UTF-8"));
        } catch (Exception e) {
            logger.error("socket推送消息出错 " + e.getMessage(),e);
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    private String createMsgId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    private RedisClient getRedisClient() {
        RedisOptions config = new RedisOptions().setAddress(ConnectionConsts.redis_server_address);
        return RedisClient.create(vertx, config);
    }

    /**
     * 测试示例
     * @param args
     */
    public static void main(String[] args) {
        Vertx vertx1 = Vertx.vertx();
        vertx1.deployVerticle("com.message.channel.socket.SocketVerticle");
        MessageProducer<String> mp = vertx1.eventBus().sender(PushConsts.PUSH_CHANNEL_VERTICLE_PREFIX+PushTypeEnum.SOCKET.getCode());
        String json = "{\n" +
                "  \"customerId\": 111111,\n" +
                "  \"expireTime\": 1673832000,\n" +
                "  \"msg\": \"\\\"我是消息内容，阿斯蒂芬工阿斯蒂芬 阿斯蒂芬 模压模压硒鼓 \\\"\",\n" +
                "  \"token\": \"\",\n" +
                "\"to\": 111" +
                "}";
        mp.send(json);
    }
}
