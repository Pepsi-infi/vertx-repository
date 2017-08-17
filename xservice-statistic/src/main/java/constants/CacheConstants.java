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

    //消息中心前缀
    public static final String MC_PREFIX = "MC_STAT_";

    //所有消息key
    public static final String PUSH_MSG_ALL = "PUSH_MSG_ALL";

    public static final String PUSH_MSG = "PUSH_MSG_";
    //发送总数
    public static final String PUSH_SEND_SUM = "SEND_SUM";
    //达到总数
    public static final String PUSH_ARRIVE_SUM = "ARRIVE_SUM";
    //点击总数
    public static final String PUSH_CLICK_SUM = "CLICK_SUM";
    //发送渠道_
    public static final String PUSH_SEND_CHANNEL = "SEND_CHANNEL_";
    //到达渠道_
    public static final String PUSH_ARRIVE_CHANNEL = "ARRIVE_CHANNEL_";
    //点击渠道_
    public static final String PUSH_CLICK_CHANNEL = "CLICK_CHANNEL_";
    //发送系统型号_
    public static final String PUSH_SEND_OSTYPE = "SEND_OSTYPE_";
    //到达系统型号_
    public static final String PUSH_ARRIVE_OSTYPE = "ARRIVE_OSTYPE_";
    //点击系统型号_
    public static final String PUSH_CLICK_OSTYPE = "CLICK_OSTYPE_";


    public static final List<String> PUSH_MSG_FIELDS = Lists.newArrayList();


    public static final String getPushMsgKey(MsgStatDto msgStatDto) {
        return new StringBuffer().append(MC_PREFIX).append(PUSH_MSG).append(msgStatDto.getMsgId()).toString();
    }

    public static final String getAllPushMsgKey() {
        return new StringBuffer().append(MC_PREFIX).append(PUSH_MSG_ALL).toString();
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
        PUSH_MSG_FIELDS.add(CacheConstants.PUSH_CLICK_SUM);
        PUSH_MSG_FIELDS.add(CacheConstants.PUSH_CLICK_OSTYPE + OsTypeEnum.ANDROID.getType());
        PUSH_MSG_FIELDS.add(CacheConstants.PUSH_CLICK_OSTYPE + OsTypeEnum.IOS.getType());
        PUSH_MSG_FIELDS.add(CacheConstants.PUSH_CLICK_CHANNEL + ChannelEnum.SOCKET.getType());
        PUSH_MSG_FIELDS.add(CacheConstants.PUSH_CLICK_CHANNEL + ChannelEnum.GCM.getType());
        PUSH_MSG_FIELDS.add(CacheConstants.PUSH_CLICK_CHANNEL + ChannelEnum.XIAOMI.getType());
    }

}
