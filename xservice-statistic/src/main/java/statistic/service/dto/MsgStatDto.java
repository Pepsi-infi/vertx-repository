package statistic.service.dto;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import statistic.constants.ChannelEnum;
import statistic.constants.OsTypeEnum;
import statistic.constants.PushActionEnum;

/**
 * Created by lufei
 * Date : 2017/7/26 15:56
 * Description :
 */
@DataObject(generateConverter = true)
public class MsgStatDto {
    //消息id
    private String msgId;
    //消息发送动作,参照：PushActionEnum
    private Integer action;
    //渠道，参照：ChannelEnum
    private Integer channel;
    //消息发送时间
    private String sendTime;
    //设备类型,参照：OsTypeEnum
    private Integer osType;
    //是否接收推送消息 1：是 0 否
    private Integer isAcceptPush;


    public MsgStatDto() {
        super();
    }

    public MsgStatDto(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        MsgStatDtoConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        MsgStatDtoConverter.toJson(this, json);
        return json;
    }


    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }


    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }


    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getOsType() {
        return osType;
    }

    public void setOsType(Integer osType) {
        this.osType = osType;
    }

    public Integer getIsAcceptPush() {
        return isAcceptPush;
    }

    public void setIsAcceptPush(Integer isAcceptPush) {
        this.isAcceptPush = isAcceptPush;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MsgStatDto{");
        sb.append("msgId='").append(msgId).append('\'');
        sb.append(", action=").append(action);
        sb.append(", channel=").append(channel);
        sb.append(", sendTime='").append(sendTime).append('\'');
        sb.append(", osType=").append(osType);
        sb.append(", isAcceptPush=").append(isAcceptPush);
        sb.append('}');
        return sb.toString();
    }
}
