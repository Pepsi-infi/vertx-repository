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
    //创建时间
    private String createTime;
    //更新时间
    private String updateTime;


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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceDto deviceDto = (DeviceDto) o;

        if (uid != null ? !uid.equals(deviceDto.uid) : deviceDto.uid != null) return false;
        if (phone != null ? !phone.equals(deviceDto.phone) : deviceDto.phone != null) return false;
        if (deviceType != null ? !deviceType.equals(deviceDto.deviceType) : deviceDto.deviceType != null) return false;
        if (channel != null ? !channel.equals(deviceDto.channel) : deviceDto.channel != null) return false;
        if (deviceToken != null ? !deviceToken.equals(deviceDto.deviceToken) : deviceDto.deviceToken != null)
            return false;
        if (osType != null ? !osType.equals(deviceDto.osType) : deviceDto.osType != null) return false;
        if (osVersion != null ? !osVersion.equals(deviceDto.osVersion) : deviceDto.osVersion != null) return false;
        if (appCode != null ? !appCode.equals(deviceDto.appCode) : deviceDto.appCode != null) return false;
        if (appVersion != null ? !appVersion.equals(deviceDto.appVersion) : deviceDto.appVersion != null) return false;
        if (antFingerprint != null ? !antFingerprint.equals(deviceDto.antFingerprint) : deviceDto.antFingerprint != null)
            return false;
        return isAcceptPush != null ? isAcceptPush.equals(deviceDto.isAcceptPush) : deviceDto.isAcceptPush == null;

    }

    @Override
    public int hashCode() {
        int result = uid != null ? uid.hashCode() : 0;
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (deviceType != null ? deviceType.hashCode() : 0);
        result = 31 * result + (channel != null ? channel.hashCode() : 0);
        result = 31 * result + (deviceToken != null ? deviceToken.hashCode() : 0);
        result = 31 * result + (osType != null ? osType.hashCode() : 0);
        result = 31 * result + (osVersion != null ? osVersion.hashCode() : 0);
        result = 31 * result + (appCode != null ? appCode.hashCode() : 0);
        result = 31 * result + (appVersion != null ? appVersion.hashCode() : 0);
        result = 31 * result + (antFingerprint != null ? antFingerprint.hashCode() : 0);
        result = 31 * result + (isAcceptPush != null ? isAcceptPush.hashCode() : 0);
        return result;
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
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append(", updateTime='").append(updateTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
