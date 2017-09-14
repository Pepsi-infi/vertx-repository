package constant;

/**
 * Created by weim on 2017/9/13.
 */
public class PushUrlConstants {

    //广告类消息
    public static final String PUSH_MSG_URL = "/mc-push/message/push.json";
    //非广告类消息
    public static final String PUSH_MSG_NO_ADVER_URL = "/mc-push/meesage-nonAdver/push.json";

    //验证身份
    public static final String PUSH_MSG_SENDERKEY = "/mc-push/message/getVerifyFromMsgCenter.json";

    //未推送成功的消息再次推送（用户上线触发，查库中的未过期消息进行推送）
    public static final  String PUSH_MSG_UNSEND = "/mc-push/message/callUnsend";
}
