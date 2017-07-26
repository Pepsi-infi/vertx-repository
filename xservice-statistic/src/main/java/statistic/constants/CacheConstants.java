package statistic.constants;

/**
 * Created by lufei
 * Date : 2017/7/25 15:40
 * Description :
 */
public class CacheConstants {

    public static final String PUSH_MSG = "PUSH_MSG_";

    public static final String PUSH_MSG_SEND = "SEND";

    public static final String PUSH_MSG_ARRIVE = "ARRIVE";


    public static final String getPushMsgKey(String msgId) {
        return new StringBuffer().append(PUSH_MSG).append(msgId).toString();
    }

}
