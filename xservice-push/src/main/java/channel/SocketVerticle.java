package channel;

import constant.ConnectionConsts;
import constant.PushConsts;
import domain.ChatMsgVO;
import enums.EnumPassengerMessageType;
import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.serviceproxy.ProxyHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.KeyValue;
import org.apache.commons.lang.ArrayUtils;
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
import java.util.*;

/**
 * 使用 socket 处理消息
 * Created by weim on 2017/7/25.
 */
public class SocketVerticle extends BaseServiceVerticle implements SocketPushService {

    private Logger logger = LoggerFactory.getLogger(SocketVerticle.class);
    //下游地址列表
    private static List<KeyValue> hostList = new ArrayList<>();

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
        ProxyHelper.registerService(SocketPushService.class, vertx, this, SocketPushService.class.getName());

        //生成redis代理
        redisService = RedisService.createProxy(vertx);

        //加载下游地址
        this.initSendTo();
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
        params.add(PushConsts.ZERO);
        params.add(msgInfo);

        Map<String, Object> sendMsgMap = new HashMap<>();
        DatagramSocket client = null;
        try {


            sendMsgMap.put("method", PushConsts.SOCKET_SEND_METHOD);
            sendMsgMap.put("params", params);
            byte[] sendBuf = ByteUtils.objectToByte(sendMsgMap);

            client = new DatagramSocket();
            KeyValue host = getPollHost();
            InetAddress targetIp = InetAddress.getByName((String)host.getKey());
            int targetPort = Integer.valueOf((String)host.getValue());
            //调用下游服务
            DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, targetIp, targetPort);

            logger.info(" Socket [" + targetIp + ":" + targetPort + "], Push method [" + PushConsts.SOCKET_SEND_METHOD + "] : " +
                    new String(sendBuf, "UTF-8"));
//            client.send(sendPacket);
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


    protected void initSendTo(){
        //当前verticle加载时从配置文件中读取下游SOCKET的地址列表
        String socketAddrs = PropertiesLoaderUtils.singleProp.getProperty(ConnectionConsts.SOCKET_HOSTS);
//        String socketAddrs = "12.12.12.1:9000,32.32.22.33:9999";
        logger.info(" upstream socket addr : [" + socketAddrs + "]");
        String[] addrArray = socketAddrs.split(",");
        if(ArrayUtils.isNotEmpty(addrArray)){
            for(String addr : addrArray){
                final String[] host = addr.split(":");
                KeyValue kv = new KeyValue() {
                    @Override
                    public Object getKey() {
                        return host[0];
                    }
                    @Override
                    public Object getValue() {
                        return host[1];
                    }
                };
                hostList.add(kv);
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

        Future<Long> passEngerFuture = Future.future();
        //消息id放入队列
        redisService.rpush(PushConsts._MSG_LIST_PASSENGER + customerId, msgId, passEngerFuture.completer());
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
//                Long passEngerRes = res.result().resultAt(0);
//                Void msgRes = res.result().resultAt(1);
//                Long expireRes = res.result().resultAt(2);
                resultHandler.handle(Future.succeededFuture());
            } else {
                resultHandler.handle(Future.failedFuture(res.cause()));
            }
        });
    }

    //轮询指针
    private static Integer pos = 0;
    /**
     *  轮询取出地址
     * @return
     */
    private KeyValue getPollHost(){
        KeyValue host = null;
        if(CollectionUtils.isNotEmpty(hostList)){
            if(pos > hostList.size()){
                pos = 0;
            }
            host = hostList.get(pos);
            pos ++;
        }
        return host;
    }

    /**
     *  随机取出地址
     * @return
     */
    private static KeyValue getRandomHost(){
        KeyValue host = null;
        int pos = 0;
        if(CollectionUtils.isNotEmpty(hostList)){
            Random r = new Random();
            pos = r.nextInt(hostList.size());
            host = hostList.get(pos);
        }
        return host;
    }

    /**
     * 测试示例
     *
     * @param args
     */
    public static void main(String[] args) {
//        Vertx vertx1 = Vertx.vertx();
//        vertx1.deployVerticle(SocketVerticle.class.getName());
//        MessageProducer<String> mp = vertx1.eventBus().sender(SocketPushService.SERVICE_ADDRESS);
//        String json = "{\"phone\":\"13211112222\",\"devicePushType\":\"1\",\"msgId\":\"a56e4029-99f7-4b9d-829f-8627be78821a\"," +
//                "\"customerId\":123,\"title\":\"发券啦\",\"content\":\"送您一张10元优惠券\"}";
//        long timerID = vertx1.setTimer(3000, id -> {
//            mp.send(json);
//        });
    }
}
