package channel;

import constant.ConnectionConsts;
import constant.MsgConstant;
import constant.PushConsts;
import domain.ChatMsgVO;
import enums.EnumPassengerMessageType;
import helper.XProxyHelper;
import io.vertx.core.*;
import io.vertx.core.eventbus.MessageProducer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import serializer.ByteUtils;
import service.RedisService;
import service.SocketPushService;
import util.JsonUtil;
import util.PropertiesLoaderUtils;
import utils.BaseResponse;
import xservice.BaseServiceVerticle;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用 socket 处理消息
 * Created by weim on 2017/7/25.
 */
public class SocketVerticle extends BaseServiceVerticle implements SocketPushService {

    private Logger logger = LoggerFactory.getLogger(SocketVerticle.class);

    private RedisService redisService;
    //消息id
    private static String msgId;
    //用户id
    private static String customerId;
    //token
    private static String token;
    //超时时间
    private static Long expireTime;
    //消息内容
    private static String msgBody;
    //消息类型杖举
    private EnumPassengerMessageType messageType;

    public void start() throws Exception {
        super.start();

        XProxyHelper.registerService(SocketPushService.class, vertx, this, SocketPushService.SERVICE_ADDRESS);
        publishEventBusService(SocketPushService.SERVICE_NAME, SocketPushService.SERVICE_ADDRESS, SocketPushService.class);

        redisService = RedisService.createProxy(vertx);
    }

    public void stop() throws Exception {
        logger.info("socket verticle stop!");
    }


    /**
     * @param jsonMsg
     */
    public void sendMsg(JsonObject jsonMsg, Handler<AsyncResult<BaseResponse>> resultHandler) {

        //广告类的消息
        messageType = EnumPassengerMessageType.ADVERTISEMENT;
        /**
         *  获取消息数据字段
         */
        msgId = jsonMsg.getString("msgId");
        customerId = jsonMsg.getString("customerId");
        token = jsonMsg.getString("deviceToken");
        //消息内容
        msgBody = jsonMsg.toString();
        //超时时间，秒
        expireTime = jsonMsg.getLong("expireTime");

        //缓存到redis
        Future redisFuture = Future.future();
        this.setRedisCache(redisFuture.completer());

        //组装数据发送
        this.socketSend(resultHandler);
    }

    /**
     * 组装发送soket需要的数据，并且通过socket发送
     *
     * @param resultHandler
     */
    private void socketSend(Handler<AsyncResult<BaseResponse>> resultHandler) {
        Map<String, Object> msgInfo = new HashMap<>();
        msgInfo.put("nick", null);
        msgInfo.put("msgId", msgId);
        msgInfo.put("title", messageType.getName());
        msgInfo.put("body", msgBody);
        if (StringUtils.isNotBlank(token) && token.length() > 10) {
            msgInfo.put("token", token);
        }

        List<Object> params = new ArrayList<>();
        params.add(customerId);
        params.add(messageType.getType());
        params.add(MsgConstant.ZERO);
        params.add(msgInfo);

        Map<String, Object> sendMsgMap = new HashMap<>();
        DatagramSocket client = null;
        try {
            String socketIpAddr = PropertiesLoaderUtils.singleProp.getProperty(ConnectionConsts.SOCKET_PASSENGER_IP);
            String socketPort = PropertiesLoaderUtils.singleProp.getProperty(ConnectionConsts.SOCKET_PASSENGER_PORT);
            sendMsgMap.put("method", MsgConstant.SendMethod.SEND_MSG.getMsg());
            sendMsgMap.put("params", params);
            byte[] sendBuf = ByteUtils.objectToByte(sendMsgMap);
            client = new DatagramSocket();
            InetAddress addr = InetAddress.getByName(socketIpAddr);
            int port = Integer.valueOf(socketPort);
            DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, addr, port);

            logger.info(" Socket [" + socketIpAddr + ":" + socketPort + "], Push method [" + MsgConstant.SendMethod.SEND_MSG.getMsg() + "] : " +
                    new String(sendBuf, "UTF-8"));
            client.send(sendPacket);
            resultHandler.handle(Future.succeededFuture(new BaseResponse()));
        } catch (Exception e) {
            logger.error("socket推送消息出错 " + e.getMessage(), e);
            resultHandler.handle(Future.failedFuture(e.getMessage()));
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    private void setRedisCache(Handler<AsyncResult<BaseResponse>> resultHandler) {
        //保存msgId对应消息体到redis
        ChatMsgVO chatMsgVO = new ChatMsgVO();
        chatMsgVO.setTo(Integer.parseInt(customerId));
        chatMsgVO.setMsgTitle(messageType.getName());
        chatMsgVO.setMsgBody(msgBody);
        chatMsgVO.setType(messageType.getType());

        Future<Void> passEngerFuture = Future.future();
        redisService.set(PushConsts._MSG_LIST_PASSENGER + customerId, msgId, passEngerFuture.completer());
        //把消息保存到redis中
        Future<Void> msgFuture = Future.future();
        redisService.set(msgId, JsonUtil.toJsonString(chatMsgVO), msgFuture.completer());
        //设置过期时间
        Long cacheExpireTime = (expireTime != null) ? (expireTime - System.currentTimeMillis()) / 1000 : 600;
        Future<Long> msgExpireFuture = Future.future();
        redisService.expire(msgId, cacheExpireTime, msgExpireFuture.completer());

        Future<CompositeFuture> future = CompositeFuture.all(passEngerFuture, msgFuture, msgExpireFuture);
        future.setHandler(res -> {
            if (res.succeeded()) {
//                Void passEngerRes = res.result().resultAt(0);
//                Void msgRes = res.result().resultAt(1);
//                Long expireRes = res.result().resultAt(2);
                resultHandler.handle(Future.succeededFuture());
            } else {
                resultHandler.handle(Future.failedFuture(res.cause()));
            }
        });
    }

    /**
     * 测试示例
     *
     * @param args
     */
    public static void main(String[] args) {
        Vertx vertx1 = Vertx.vertx();
        vertx1.deployVerticle(SocketVerticle.class.getName());
        MessageProducer<String> mp = vertx1.eventBus().sender(PushConsts.PUSH_CHANNEL_VERTICLE_PREFIX);
        String json = "";
        mp.send(json);
        long timerID = vertx1.setTimer(3000, id -> {
            mp.send(json);
        });
    }
}
