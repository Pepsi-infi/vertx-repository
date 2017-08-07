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
    //小米设备token
    private String miToken;
    //gcmToken
    private String gcmToken;
    //apple推送token
    private String apnsToken;
    //
    private String imei;
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

    public String getMiToken() {
        return miToken;
    }

    public void setMiToken(String miToken) {
        this.miToken = miToken;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
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

    public String getGcmToken() {
        return gcmToken;
    }

    public void setGcmToken(String gcmToken) {
        this.gcmToken = gcmToken;
    }

    public String getApnsToken() {
        return apnsToken;
    }

    public void setApnsToken(String apnsToken) {
        this.apnsToken = apnsToken;
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
        if (miToken != null ? !miToken.equals(deviceDto.miToken) : deviceDto.miToken != null)
            return false;
        if (gcmToken != null ? !gcmToken.equals(deviceDto.gcmToken) : deviceDto.gcmToken != null) return false;
        if (apnsToken != null ? !apnsToken.equals(deviceDto.apnsToken) : deviceDto.apnsToken != null) return false;
        if (imei != null ? !imei.equals(deviceDto.imei) : deviceDto.imei != null) return false;
        if (osType != null ? !osType.equals(deviceDto.osType) : deviceDto.osType != null) return false;
        if (osVersion != null ? !osVersion.equals(deviceDto.osVersion) : deviceDto.osVersion != null) return false;
        if (appCode != null ? !appCode.equals(deviceDto.appCode) : deviceDto.appCode != null) return false;
        if (appVersion != null ? !appVersion.equals(deviceDto.appVersion) : deviceDto.appVersion != null) return false;
        if (!antFingerprint.equals(deviceDto.antFingerprint)) return false;
        return isAcceptPush.equals(deviceDto.isAcceptPush);

    }

    @Override
    public int hashCode() {
        int result = uid != null ? uid.hashCode() : 0;
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (deviceType != null ? deviceType.hashCode() : 0);
        result = 31 * result + (miToken != null ? miToken.hashCode() : 0);
        result = 31 * result + (gcmToken != null ? gcmToken.hashCode() : 0);
        result = 31 * result + (apnsToken != null ? apnsToken.hashCode() : 0);
        result = 31 * result + (imei != null ? imei.hashCode() : 0);
        result = 31 * result + (osType != null ? osType.hashCode() : 0);
        result = 31 * result + (osVersion != null ? osVersion.hashCode() : 0);
        result = 31 * result + (appCode != null ? appCode.hashCode() : 0);
        result = 31 * result + (appVersion != null ? appVersion.hashCode() : 0);
        result = 31 * result + antFingerprint.hashCode();
        result = 31 * result + isAcceptPush.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeviceDto{");
        sb.append("id=").append(id);
        sb.append(", uid=").append(uid);
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", deviceType='").append(deviceType).append('\'');
        sb.append(", miToken='").append(miToken).append('\'');
        sb.append(", gcmToken='").append(gcmToken).append('\'');
        sb.append(", apnsToken='").append(apnsToken).append('\'');
        sb.append(", imei='").append(imei).append('\'');
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
