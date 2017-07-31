package constants;

import com.google.common.collect.Lists;
import iservice.dto.MsgStatDto;
import org.apache.commons.lang.StringUtils;

import java.util.List;

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

    public static final List<String> PUSH_MSG_FIELDS = Lists.newArrayList();


    public static final String getPushMsgKey(MsgStatDto msgStatDto) {
        return new StringBuffer().append(PUSH_MSG).append(msgStatDto.getMsgId()).toString();
    }

    public static String getMsgId(String pushMsgKey) {
        if (StringUtils.isBlank(pushMsgKey)) {
            return null;
        }
        return pushMsgKey.substring(pushMsgKey.lastIndexOf("_") + 1, pushMsgKey.length());
    }

    static {
        PUSH_MSG_FIELDS.add(CacheConstants.PUSH_SEND_SUM);
        PUSH_MSG_FIELDS.add(CacheConstants.PUSH_SEND_OSTYPE + OsTypeEnum.ANDROID.getType());
        PUSH_MSG_FIELDS.add(CacheConstants.PUSH_SEND_OSTYPE + OsTypeEnum.IOS.getType());
        PUSH_MSG_FIELDS.add(CacheConstants.PUSH_SEND_CHANNEL + ChannelEnum.SOCKET.getType());
        PUSH_MSG_FIELDS.add(CacheConstants.PUSH_SEND_CHANNEL + ChannelEnum.GCM.getType());
        PUSH_MSG_FIELDS.add(CacheConstants.PUSH_SEND_CHANNEL + ChannelEnum.XIAOMI.getType());
        PUSH_MSG_FIELDS.add(CacheConstants.PUSH_ARRIVE_SUM);
        PUSH_MSG_FIELDS.add(CacheConstants.PUSH_ARRIVE_OSTYPE + OsTypeEnum.ANDROID.getType());
        PUSH_MSG_FIELDS.add(CacheConstants.PUSH_ARRIVE_OSTYPE + OsTypeEnum.IOS.getType());
        PUSH_MSG_FIELDS.add(CacheConstants.PUSH_ARRIVE_CHANNEL + ChannelEnum.SOCKET.getType());
        PUSH_MSG_FIELDS.add(CacheConstants.PUSH_ARRIVE_CHANNEL + ChannelEnum.GCM.getType());
        PUSH_MSG_FIELDS.add(CacheConstants.PUSH_ARRIVE_CHANNEL + ChannelEnum.XIAOMI.getType());
    }

}
