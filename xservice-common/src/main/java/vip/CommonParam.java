package vip;


import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.apache.commons.lang.StringUtils;

/**
 * 接口通用参数
 */
@DataObject(generateConverter = true)
public class CommonParam {
    /* 领先版和电视共有的通用参数 */
    private String terminalSeries; // 设备型号,电视的设备型号仅针对乐视自有版本有效，如S40、X50等
    private String bsChannel; // 渠道号
    private String terminalApplication; // 应用,领先版,汽车,电视(letv--乐视自有版，letv-common--通用版，及CIBN版等)
    private String langcode = "zh_cn"; // 语言代码
    private String wcode; // 区域代码
    private String terminalBrand;// 设备品牌
    private String appVersion; // 版本号

    /* 领先版其它的通用参数 */
    private String pcode; // 产品编码
    private String devId; // 客户端设备唯一id
    private String uid; // 用户id
    private String token; // 验证号
    private String imeiArea;// 销售区域
    private String countryArea;// 国家代码

    private String ip;

    public CommonParam() {
    }

    public CommonParam(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        CommonParamConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        CommonParamConverter.toJson(this, json);
        return json;
    }

    public CommonParam(String terminalApplication, String terminalBrand, String bsChannel, String terminalSeries,
                       String devId, String langcode, String wcode, String imeiArea, String countryArea,
                       String token, String uid) {
        this.terminalApplication = terminalApplication;
        this.terminalBrand = terminalBrand;
        this.bsChannel = bsChannel;
        this.terminalSeries = terminalSeries;
        this.devId = devId;
        this.langcode = langcode;
        this.wcode = wcode;
        this.imeiArea = imeiArea;
        this.countryArea = countryArea;
        this.token = token;
        this.uid = uid;
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
        return this.wcode;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImeiArea() {
        return imeiArea;
    }

    public void setImeiArea(String imeiArea) {
        this.imeiArea = imeiArea;
    }

    public String getCountryArea() {
        return countryArea;
    }

    public void setCountryArea(String countryArea) {
        this.countryArea = countryArea;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAreaCode(){
        String areaCode = StringUtils.isNotBlank(this.getCountryArea()) ? this.getCountryArea()
                : StringUtils.isNotBlank(this.getImeiArea()) ? this.getImeiArea()
                : StringUtils.isNotBlank(this.getWcode()) ? this.getWcode() : "cn";
        return areaCode.toLowerCase();
    }


    @Override
    public String toString() {
        return "CommonParam{" + "pcode='" + pcode + '\'' + ", devId='" + devId + '\'' + ", terminalSeries='"
                + terminalSeries + '\'' + ", appVersion='" + appVersion + '\'' + ", uid='" + uid + '\''
                + ", bsChannel='" + bsChannel + '\'' + ", terminalApplication='" + terminalApplication + '\''
                + ", langcode='" + langcode + '\'' + ", wcode='" + wcode + '\'' + ", terminalBrand='" + terminalBrand
                + '\'' + ", token='" + token + '\'' + ", imeiArea='"
                + imeiArea + '\'' + ", countryArea='" + countryArea + '\'' + '}';
    }

}
