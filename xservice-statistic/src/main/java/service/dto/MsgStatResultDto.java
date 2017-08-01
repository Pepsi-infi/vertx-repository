package service.dto;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Created by lufei
 * Date : 2017/7/30 22:02
 * Description :
 */
@DataObject(generateConverter = true)
public class MsgStatResultDto {
    //渠道
    private Integer channel;
    //消息id
    private String msgId;
    //统计时间
    private String statTime;
    //发送时间
    private String sendTime;
    //发送总数
    private Long sendSum;
    //Android发送总量
    private Long sendAndroidSum;
    //ios发送总量
    private Long sendIosSum;
    //socket渠道发送总量
    private Long sendSockSum;
    //小米渠道发送总量
    private Long sendMiSum;
    //gcm渠道发送总量
    private Long sendGcmSum;
    //到达总数
    private Long arriveSum;
    //Android到达总数
    private Long arriveAndroidSum;
    //ios达到总数
    private Long arriveIosSum;
    //socket渠道达到总数
    private Long arriveSockSum;
    //小米渠道到达总数
    private Long arriveMiSum;
    //gcm渠道到达总数
    private Long arriveGcmSum;
    //点击总数
    private Long clickSum;
    //Android点击总数
    private Long clickAndroidSum;
    //ios点击总数
    private Long clickIosSum;
    //socket点击总数
    private Long clickSockSum;
    //小米渠道点击总数
    private Long clickMiSum;
    //gcm渠道点击总数
    private Long clickGcmSum;

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getStatTime() {
        return statTime;
    }

    public void setStatTime(String statTime) {
        this.statTime = statTime;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public Long getSendSum() {
        return sendSum;
    }

    public void setSendSum(Long sendSum) {
        this.sendSum = sendSum;
    }

    public Long getSendAndroidSum() {
        return sendAndroidSum;
    }

    public void setSendAndroidSum(Long sendAndroidSum) {
        this.sendAndroidSum = sendAndroidSum;
    }

    public Long getSendIosSum() {
        return sendIosSum;
    }

    public void setSendIosSum(Long sendIosSum) {
        this.sendIosSum = sendIosSum;
    }

    public Long getSendSockSum() {
        return sendSockSum;
    }

    public void setSendSockSum(Long sendSockSum) {
        this.sendSockSum = sendSockSum;
    }

    public Long getSendMiSum() {
        return sendMiSum;
    }

    public void setSendMiSum(Long sendMiSum) {
        this.sendMiSum = sendMiSum;
    }

    public Long getSendGcmSum() {
        return sendGcmSum;
    }

    public void setSendGcmSum(Long sendGcmSum) {
        this.sendGcmSum = sendGcmSum;
    }

    public Long getArriveSum() {
        return arriveSum;
    }

    public void setArriveSum(Long arriveSum) {
        this.arriveSum = arriveSum;
    }

    public Long getArriveAndroidSum() {
        return arriveAndroidSum;
    }

    public void setArriveAndroidSum(Long arriveAndroidSum) {
        this.arriveAndroidSum = arriveAndroidSum;
    }

    public Long getArriveIosSum() {
        return arriveIosSum;
    }

    public void setArriveIosSum(Long arriveIosSum) {
        this.arriveIosSum = arriveIosSum;
    }

    public Long getArriveSockSum() {
        return arriveSockSum;
    }

    public void setArriveSockSum(Long arriveSockSum) {
        this.arriveSockSum = arriveSockSum;
    }

    public Long getArriveMiSum() {
        return arriveMiSum;
    }

    public void setArriveMiSum(Long arriveMiSum) {
        this.arriveMiSum = arriveMiSum;
    }

    public Long getArriveGcmSum() {
        return arriveGcmSum;
    }

    public void setArriveGcmSum(Long arriveGcmSum) {
        this.arriveGcmSum = arriveGcmSum;
    }

    public Long getClickSum() {
        return clickSum;
    }

    public void setClickSum(Long clickSum) {
        this.clickSum = clickSum;
    }

    public Long getClickAndroidSum() {
        return clickAndroidSum;
    }

    public void setClickAndroidSum(Long clickAndroidSum) {
        this.clickAndroidSum = clickAndroidSum;
    }

    public Long getClickIosSum() {
        return clickIosSum;
    }

    public void setClickIosSum(Long clickIosSum) {
        this.clickIosSum = clickIosSum;
    }

    public Long getClickSockSum() {
        return clickSockSum;
    }

    public void setClickSockSum(Long clickSockSum) {
        this.clickSockSum = clickSockSum;
    }

    public Long getClickMiSum() {
        return clickMiSum;
    }

    public void setClickMiSum(Long clickMiSum) {
        this.clickMiSum = clickMiSum;
    }

    public Long getClickGcmSum() {
        return clickGcmSum;
    }

    public void setClickGcmSum(Long clickGcmSum) {
        this.clickGcmSum = clickGcmSum;
    }

    public MsgStatResultDto() {
        super();
    }

    public MsgStatResultDto(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        MsgStatResultDtoConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        MsgStatResultDtoConverter.toJson(this, json);
        return json;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MsgStatResultDto{");
        sb.append("channel=").append(channel);
        sb.append(", msgId='").append(msgId).append('\'');
        sb.append(", statTime='").append(statTime).append('\'');
        sb.append(", sendTime='").append(sendTime).append('\'');
        sb.append(", sendSum=").append(sendSum);
        sb.append(", sendAndroidSum=").append(sendAndroidSum);
        sb.append(", sendIosSum=").append(sendIosSum);
        sb.append(", sendSockSum=").append(sendSockSum);
        sb.append(", sendMiSum=").append(sendMiSum);
        sb.append(", sendGcmSum=").append(sendGcmSum);
        sb.append(", arriveSum=").append(arriveSum);
        sb.append(", arriveAndroidSum=").append(arriveAndroidSum);
        sb.append(", arriveIosSum=").append(arriveIosSum);
        sb.append(", arriveSockSum=").append(arriveSockSum);
        sb.append(", arriveMiSum=").append(arriveMiSum);
        sb.append(", arriveGcmSum=").append(arriveGcmSum);
        sb.append(", clickSum=").append(clickSum);
        sb.append(", clickAndroidSum=").append(clickAndroidSum);
        sb.append(", clickIosSum=").append(clickIosSum);
        sb.append(", clickSockSum=").append(clickSockSum);
        sb.append(", clickMiSum=").append(clickMiSum);
        sb.append(", clickGcmSum=").append(clickGcmSum);
        sb.append('}');
        return sb.toString();
    }
}
