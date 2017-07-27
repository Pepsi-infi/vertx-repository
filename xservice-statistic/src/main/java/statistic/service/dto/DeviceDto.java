package statistic.service.dto;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Created by lufei
 * Date : 2017/7/26 17:21
 * Description :
 */
@DataObject(generateConverter = true)
public class DeviceDto {
    //用户id
    private Long uid;
    //用户类型
    private Integer userType;
    //电话
    private String phone;
    //设备型号
    private String deviceType;
    //
    private String deviceToken;
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

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
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

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
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
        sb.append("uid=").append(uid);
        sb.append(", userType=").append(userType);
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", deviceType='").append(deviceType).append('\'');
        sb.append(", deviceToken='").append(deviceToken).append('\'');
        sb.append(", imei='").append(imei).append('\'');
        sb.append(", osType=").append(osType);
        sb.append(", osVersion='").append(osVersion).append('\'');
        sb.append(", appCode=").append(appCode);
        sb.append(", appVersion='").append(appVersion).append('\'');
        sb.append(", antFingerprint='").append(antFingerprint).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
