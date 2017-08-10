package channel;

import com.xiaomi.push.sdk.ErrorCode;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;
import constant.PushConsts;
import enums.JumpFlagEnum;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.serviceproxy.ProxyHelper;
import service.XiaoMiPushService;
import util.JsonUtil;
import util.PropertiesLoaderUtils;
import utils.BaseResponse;

/**
 * @author yanglf
 *         <p>
 *         小米推送
 */
public class MiPushVerticle extends AbstractVerticle implements XiaoMiPushService {

    private static final Logger logger = LoggerFactory.getLogger(MiPushVerticle.class);

    @Override
    public void start() throws Exception {
        super.start();
        ProxyHelper.registerService(XiaoMiPushService.class, vertx, this, XiaoMiPushService.class.getName());

    }

    @Override
    public void sendMsg(JsonObject recieveMsg, Handler<AsyncResult<BaseResponse>> resultHandler) {

        logger.info("进入小米推送Verticle");

        if (recieveMsg == null) {
            logger.error("尚无消息");
            return;
        }

        try {
            Result result = sendMessage(recieveMsg);

            if (ErrorCode.Success == result.getErrorCode()) {
                resultHandler.handle(Future.succeededFuture(new BaseResponse()));
            } else {
                resultHandler.handle(Future.failedFuture(result.getReason()));
            }

        } catch (Exception e) {
            resultHandler.handle(Future.failedFuture(e));
            logger.error("recieveMsg=" + recieveMsg, e);
        }
    }

    public Result sendMessage(JsonObject recieveMsg) throws Exception {

        String regId = (String) recieveMsg.getValue("regId");
        Sender sender = new Sender(PropertiesLoaderUtils.singleProp.getProperty("xiaomi.appsecret"));

        Message message = buildMessage(recieveMsg);

        Result sendResult = sender.send(message, regId, 0); // 根据regID，发送消息到指定设备上，不重试。

        logger.info("regId: " + regId + ", 小米推送返回结果：" + JsonUtil.toJsonString(sendResult));
        return sendResult;
    }

    private Message buildMessage(JsonObject recieveMsg) throws Exception {
        // app包名
        String packageName = PropertiesLoaderUtils.singleProp.getProperty("xiaomi.packagename");
        String title = recieveMsg.getString("title");
        String wholeMsg = recieveMsg.toString();
        String msgId = recieveMsg.getString("msgId");
        Integer jumpPage = recieveMsg.getInteger("jumpPage");
        String content = recieveMsg.getString("content");
        Integer isIntoPsnCenter = recieveMsg.getInteger("isIntoPsnCenter");
        if(isIntoPsnCenter != null && isIntoPsnCenter == 1){
            jumpPage = JumpFlagEnum.MESSAGE_CENTER_PAGE.getCode();
        }
        String action = getEnumByCode(jumpPage);

        Message message = new Message.Builder().title(title).description(content).payload(wholeMsg)
                .extra("messageId", msgId).extra("action", action).extra("title", title)
                .extra("content", content).restrictedPackageName(packageName)
                .passThrough(PushConsts.XIAOMI_PASS_THROUGH_TOUCHUAN) // 设置消息是否通过透传的方式送给app，1表示透传消息，0表示通知栏消息。
                .notifyType(PushConsts.XIAOMI_NOTIFY_TYPE_DEFAULT_SOUND) // 使用默认提示音提示
                .build();
        return message;
    }

    String getEnumByCode(Integer code) {
        if(code == null){
            return "";
        }
        for (JumpFlagEnum jump : JumpFlagEnum.values()) {
            if (jump.getCode() == code) {
                return jump.getMsg();
            }
        }
        return "";
    }

}
