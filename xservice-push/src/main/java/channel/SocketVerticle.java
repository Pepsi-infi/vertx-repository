package channel;

import constant.PushConsts;
import domain.ChatMsgVO;
import enums.EnumPassengerMessageType;
import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
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
import util.MsgUtil;
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
    private List<KeyValue> hostList = new ArrayList<>();

    private RedisService redisService;

    private JsonObject config;

    public void start() throws Exception {
        super.start();
        ProxyHelper.registerService(SocketPushService.class, vertx, this, SocketPushService.class.getName());

        //生成redis代理
        redisService = RedisService.createProxy(vertx);

        config = config().getJsonObject("push.config");
        //加载下游地址
        this.initSendTo();
    }

    public void stop() throws Exception {
        logger.info("socket verticle stop!");
    }


    /**
     * @param receiveMsg
     */
    public void sendMsg(JsonObject receiveMsg, Handler<AsyncResult<BaseResponse>> resultHandler) {
        //测试专用，防止测试推错推到线上
//        receiveMsg = testSendControl(receiveMsg);

        //转成下游结构
        Map<String, Object> sendMsgMap = this.convertMsgToBean(receiveMsg);

        //缓存到redis
        Future<BaseResponse> redisFuture = Future.future();
        this.setRedisCache(receiveMsg, redisFuture.completer());

        //发送数据
        this.socketSend(sendMsgMap, resultHandler);
    }

    /**
     * 消息转成下游需要的bean结构
     * @param receiveMsg
     * @return
     */
    public Map<String, Object> convertMsgToBean(JsonObject receiveMsg){
        //广告类的消息
        EnumPassengerMessageType messageType = EnumPassengerMessageType.ADVERTISEMENT;

        /**
         *  获取消息数据字段
         */
        String msgId = receiveMsg.getValue("msgId") + "";
        String customerId = receiveMsg.getValue("customerId") + "";
        String token = receiveMsg.getString("deviceToken");
        //超时时间，秒
        Long expireTime = receiveMsg.getLong("expireTime");
        //跳转页逻辑
        initActionUrl(receiveMsg);

        Map<String, Object> msgInfo = new HashMap<>();
        msgInfo.put("nick", null);
        msgInfo.put("msgId", msgId);
        msgInfo.put("title", messageType.getName());
        msgInfo.put("body", receiveMsg.toString());
        if (StringUtils.isNotBlank(token) && token.length() > 10) {
            msgInfo.put("token", token);
        }

        List<Object> params = new ArrayList<>();
        params.add(customerId);
        params.add(messageType.getType());
        params.add(PushConsts.ZERO);
        params.add(msgInfo);

        Map<String, Object> sendMsgMap = new HashMap<>();
        sendMsgMap.put("method", PushConsts.SOCKET_SEND_METHOD);
        sendMsgMap.put("params", params);
        logger.info("Socket Push ObjectToByte Before [customerId=" + customerId + "], sendMsgMap :" + Json.encode(sendMsgMap));
        return sendMsgMap;
    }


    /**
     * 组装发送soket需要的数据，并且通过socket发送
     *
     * @param resultHandler
     */
    private void socketSend(Map<String, Object> sendMsgMap, Handler<AsyncResult<BaseResponse>> resultHandler) {

        DatagramSocket client = null;
        try {

            byte[] sendBuf = ByteUtils.objectToByte(sendMsgMap);
            client = new DatagramSocket();
            KeyValue host = getPollHost();
            InetAddress targetIp = InetAddress.getByName((String) host.getKey());
            int targetPort = Integer.valueOf((String) host.getValue());
            //调用下游服务
            DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, targetIp, targetPort);

            logger.info(" Socket [" + targetIp + ":" + targetPort + "], Push method [" + PushConsts.SOCKET_SEND_METHOD + "] : " +
                    new String(sendBuf, "UTF-8"));
            client.send(sendPacket);
            resultHandler.handle(Future.succeededFuture(new BaseResponse()));
        } catch (Exception e) {
            resultHandler.handle(Future.failedFuture(e.getMessage()));
            //打出错误日志
            logger.error("socket send error !", e);
            List<Object> params = (List)sendMsgMap.get("params");
            logger.error("socket send error customerId=" + params.get(0) + ", msgInfo=" + Json.encode(params.get(3)));
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }


    protected void initSendTo() {
        //当前verticle加载时从配置文件中读取下游SOCKET的地址列表
        String socketAddrs = config.getString("SOCKET_HOSTS");
//        String socketAddrs = "12.12.12.1:9000,32.32.22.33:9999";
        logger.info(" upstream socket addr : [" + socketAddrs + "]");
        String[] addrArray = socketAddrs.split(",");
        if (ArrayUtils.isNotEmpty(addrArray)) {
            for (String addr : addrArray) {
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

    /**
     * 原来socket的逻辑，暂时没用
     * @param receiveMsg
     * @param resultHandler
     */
    private void setRedisCache(JsonObject receiveMsg, Handler<AsyncResult<BaseResponse>> resultHandler) {
        //广告类的消息
        EnumPassengerMessageType messageType = EnumPassengerMessageType.ADVERTISEMENT;
        /**
         *  获取消息数据字段
         */
        String msgId = receiveMsg.getValue("msgId") + "";
        String customerId = receiveMsg.getValue("customerId") + "";
        String token = receiveMsg.getString("deviceToken");
        //消息内容
        String msgBody = receiveMsg.toString();
        //超时时间，秒
        Long expireTime = receiveMsg.getLong("expireTime");

        //保存msgId对应消息体到redis
        ChatMsgVO chatMsgVO = new ChatMsgVO();
        chatMsgVO.setTo(Integer.parseInt(customerId));
        chatMsgVO.setMsgTitle(messageType.getName());
        chatMsgVO.setMsgBody(msgBody);
        chatMsgVO.setType(messageType.getType());

        Future<Long> passEngerFuture = Future.future();
        //消息id放入队列
        redisService.rpush(PushConsts._MSG_LIST_PASSENGER + customerId, msgId, passEngerFuture.completer());

        //消息放到redis
        //过期时间毫秒值, 没有过期时间设置为10分钟过期
        Long cacheExpireTime = (expireTime != null) ? expireTime : System.currentTimeMillis() +  600000;
        chatMsgVO.setExpireTime(cacheExpireTime);
        Future<String> msgFuture = Future.future();
        redisService.setEx(msgId, cacheExpireTime, Json.encode(chatMsgVO), msgFuture.completer());

        Future<CompositeFuture> future = CompositeFuture.all(passEngerFuture, msgFuture);
        future.setHandler(res -> {
            if (res.succeeded()) {
                resultHandler.handle(Future.succeededFuture());
            } else {
                resultHandler.handle(Future.failedFuture(res.cause()));
            }
        });
    }

    //轮询指针
    private static Integer pos = 0;

    /**
     * 轮询取出地址
     *
     * @return
     */
    private KeyValue getPollHost() {
        KeyValue host = null;
        if (CollectionUtils.isNotEmpty(hostList)) {
            if (pos >= hostList.size()) {
                pos = 0;
            }
            host = hostList.get(pos);
            pos++;
        }
        return host;
    }

    /**
     * 随机取出地址
     *
     * @return
     */
    private KeyValue getRandomHost() {
        KeyValue host = null;
        int pos = 0;
        if (CollectionUtils.isNotEmpty(hostList)) {
            Random r = new Random();
            pos = r.nextInt(hostList.size());
            host = hostList.get(pos);
        }
        return host;
    }

    //测试专用，防止消息推送到线上用户
    private JsonObject testSendControl(JsonObject jsonMsg){
       if("dev".equals(PushConsts.ENV_PATH)){
           String customerId = config.getString("socket.test.customerId");
           String phone = config.getString("socket.test.phone");
           if(jsonMsg != null){
               jsonMsg.put("phone", phone);
               jsonMsg.put("customerId", customerId);
           }
       }
       return jsonMsg;
    }

    private static void initActionUrl(JsonObject receiveMsg){
        String action = "";
        //如果配置了打开网页，则跳转到H5网页
        //type, 1是打开app,2是打开网页
        Object type = receiveMsg.getValue("type");
        Object url = receiveMsg.getValue("url");
        if(type != null){
            String actionUrl = (url != null) ? (String)url : "";
            action = ((Integer) type == 2 ) ? actionUrl : "";
        }
        if(StringUtils.isNotBlank(action)){
            receiveMsg.put("action", action);
            return;
        }

        //如果没有配置打开网面，走跳转页
        Object jumpPage = receiveMsg.getValue("jumpPage");
        if(jumpPage != null){
            Integer actionCode = (Integer) jumpPage;
            action = MsgUtil.getEnumByCode(actionCode);
            receiveMsg.put("action", action);
        }else{
            receiveMsg.put("action", "");
        }
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

          //2、测试跳转页
//       sonObject json  = new JsonObject("{\"type\":2,\"url\":\"www.baidu.com\",\"jumpPage\":0,\"isIntoPsnCenter\":1}");
//        initActionUrl(json);
//        System.out.println(json);

    }
}
