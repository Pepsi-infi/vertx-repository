package iservice.dto;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Created by lufei
 * Date : 2017/7/26 17:21
 * Description :
 */
@DataObject(generateConverter = true)
public class DeviceDto {
    private Long id;
    //用户id
    private Long uid;
    //电话
    private String phone;
    //设备型号
    private String deviceType;
    //push 渠道
    private Integer channel;
    //对应渠道token
    private String deviceToken;
    //系统类型
    private Integer osType;
    //系统类型
    private String osVersion;
    //应用标识
    private Integer appCode;
    //应用版本
    private String appVersion;
    //蚂蚁金服指纹
    private String antFingerprint;
    //是否接收推送消息 1：是 0 否
    private Integer isAcceptPush;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getOsType() {
        return osType;
    }

    public void setOsType(Integer osType) {
        this.osType = osType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getAppCode() {
        return appCode;
    }

    public void setAppCode(Integer appCode) {
        this.appCode = appCode;
    }

    public String getAntFingerprint() {
        return antFingerprint;
    }

    public void setAntFingerprint(String antFingerprint) {
        this.antFingerprint = antFingerprint;
    }

    public Integer getIsAcceptPush() {
        return isAcceptPush;
    }

    public void setIsAcceptPush(Integer isAcceptPush) {
        this.isAcceptPush = isAcceptPush;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public DeviceDto() {
        super();
    }

    public DeviceDto(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        DeviceDtoConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        DeviceDtoConverter.toJson(this, json);
        return json;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeviceDto{");
        sb.append("id=").append(id);
        sb.append(", uid=").append(uid);
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", deviceType='").append(deviceType).append('\'');
        sb.append(", channel=").append(channel);
        sb.append(", deviceToken='").append(deviceToken).append('\'');
        sb.append(", osType=").append(osType);
        sb.append(", osVersion='").append(osVersion).append('\'');
        sb.append(", appCode=").append(appCode);
        sb.append(", appVersion='").append(appVersion).append('\'');
        sb.append(", antFingerprint='").append(antFingerprint).append('\'');
        sb.append(", isAcceptPush=").append(isAcceptPush);
        sb.append('}');
        return sb.toString();
    }
}
