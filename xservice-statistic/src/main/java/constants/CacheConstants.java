package constants;

import iservice.dto.MsgStatDto;

/**
 * Created by lufei
 * Date : 2017/7/25 15:40
 * Description :
 */
public class CacheConstants {

    public static final String PUSH_MSG = "PUSH_MSG_";

    public static final String PUSH_SEND_SUM = "SEND_SUM";

    public static final String PUSH_ARRIVE_SUM = "ARRIVE_SUM";

    public static final String PUSH_SEND_CHANNEL = "SEND_CHANNEL_";

    public static final String PUSH_ARRIVE_CHANNEL = "ARRIVE_CHANNEL_";

    public static final String PUSH_SEND_OSTYPE = "SEND_OSTYPE_";

    public static final String PUSH_ARRIVE_OSTYPE = "ARRIVE_OSTYPE_";


    public static final String getPushMsgKey(MsgStatDto msgStatDto) {
        return new StringBuffer().append(PUSH_MSG).append(msgStatDto.getMsgId()).toString();
    }

}
