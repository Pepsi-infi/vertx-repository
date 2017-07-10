package service.param;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * 接口通用参数
 */
@DataObject(generateConverter = true)
public class LiveCommonParam {
    private String pcode; // 产品编码
    private String devId; // 客户端设备唯一id
    private String terminalSeries; // 设备型号
    private String appVersion; // 版本号
    private String uid; // 用户id
    private String bsChannel; // 渠道号
    private String terminalApplication; // 应用
    private String langcode; // 语言代码
    private String wcode; // 区域代码
    private String terminalBrand;// 品牌
    private String splatClient; // live的客户端来源
    private String token; // 验证号
    private String versionCode; // 版本号
    private String cityInfo; // 城市信息
    private String countryArea; // 用户设置地区
    private String salesArea; // 售卖地
    private String broadcastId; // 播控方
    /**
     * mac地址，目前手机端没有传，电视端传了
     */
    private String mac;
    private String from;

    public LiveCommonParam() {
        super();
    }

    public LiveCommonParam(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        LiveCommonParamConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        LiveCommonParamConverter.toJson(this, json);
        return json;
    }
    public String getBroadcastId() {
        return broadcastId;
    }

    public void setBroadcastId(String broadcastId) {
        this.broadcastId = broadcastId;
    }

    public String getCountryArea() {
        return countryArea;
    }

    public void setCountryArea(String countryArea) {
        this.countryArea = countryArea;
    }

    public String getSalesArea() {
        return salesArea;
    }

    public void setSalesArea(String salesArea) {
        this.salesArea = salesArea;
    }

    public String getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(String cityInfo) {
        this.cityInfo = cityInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getTerminalSeries() {
        return terminalSeries;
    }

    public void setTerminalSeries(String terminalSeries) {
        this.terminalSeries = terminalSeries;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBsChannel() {
        return bsChannel;
    }

    public void setBsChannel(String bsChannel) {
        this.bsChannel = bsChannel;
    }

    public String getTerminalApplication() {
        return terminalApplication;
    }

    public void setTerminalApplication(String terminalApplication) {
        this.terminalApplication = terminalApplication;
    }

    public String getLangcode() {
        return langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

    public String getWcode() {
        return wcode;
    }

    public void setWcode(String wcode) {
        this.wcode = wcode;
    }

    public String getTerminalBrand() {
        return terminalBrand;
    }

    public void setTerminalBrand(String terminalBrand) {
        this.terminalBrand = terminalBrand;
    }

    public String getSplatClient() {
        return splatClient;
    }

    public void setSplatClient(String splatClient) {
        this.splatClient = splatClient;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public String toString() {
        return "CommonParam [pcode=" + pcode + ", devId=" + devId + ", terminalSeries=" + terminalSeries
                + ", appVersion=" + appVersion + ", uid=" + uid + ", bsChannel=" + bsChannel + ", terminalApplication="
                + terminalApplication + ", langcode=" + langcode + ", wcode=" + wcode + ", terminalBrand="
                + terminalBrand + ", splatClient=" + splatClient + ", token=" + token + ", versionCode=" + versionCode
                + ", cityInfo=" + cityInfo + ", countryArea=" + countryArea + ", salesArea=" + salesArea
                + ", broadcastId=" + broadcastId + ", mac=" + mac + ", from=" + from + "]";
    }
}
