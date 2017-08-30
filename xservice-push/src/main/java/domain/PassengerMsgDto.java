package domain;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.io.Serializable;

/**
 * Created by weim on 2017/8/23.
 */
@DataObject(generateConverter = true)
public class PassengerMsgDto implements Serializable {

    private static final long serialVersionUID = 123465438652346L;
    private Integer id;
    private String title;
    private String content;
    private Integer action;
    private String msgCenterImgUrl;
    private Integer inMsgCenter;
    private String openUrl;
    private Integer openType;
    private Integer sendType;
    private Integer status;
    private String expireTime;
    private String sendTime;

    private String importFile;
    private String cityIds;

    public PassengerMsgDto() {
    }

    public String getImportFile() {
        return importFile;
    }

    public void setImportFile(String importFile) {
        this.importFile = importFile;
    }

    public String getCityIds() {
        return cityIds;
    }

    public void setCityIds(String cityIds) {
        this.cityIds = cityIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public String getMsgCenterImgUrl() {
        return msgCenterImgUrl;
    }

    public void setMsgCenterImgUrl(String msgCenterImgUrl) {
        this.msgCenterImgUrl = msgCenterImgUrl;
    }

    public Integer getInMsgCenter() {
        return inMsgCenter;
    }

    public void setInMsgCenter(Integer inMsgCenter) {
        this.inMsgCenter = inMsgCenter;
    }

    public String getOpenUrl() {
        return openUrl;
    }

    public void setOpenUrl(String openUrl) {
        this.openUrl = openUrl;
    }

    public Integer getOpenType() {
        return openType;
    }

    public void setOpenType(Integer openType) {
        this.openType = openType;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public PassengerMsgDto(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        PassengerMsgDtoConverter.fromJson(json,this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        PassengerMsgDtoConverter.toJson(this, json);
        return json;
    }
}
