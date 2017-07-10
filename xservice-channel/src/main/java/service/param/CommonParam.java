package service.param;

import org.apache.commons.lang.StringUtils;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * request params of interface:GET_SHORT_CUT
 */
@DataObject(generateConverter = true)
public class CommonParam {
    String langcode;
    String terminalApplication;
    String history;
    String uid;
    String devId;
    String imeiArea;
    String countryArea;
    String ip;
    Integer support;
    /**
     * 业务编码
     */
    private String bizCode;
    
    public CommonParam(){}

    public CommonParam(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        CommonParamConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        CommonParamConverter.toJson(this, json);
        return json;
    }

    public String getAreaCode() {
        return StringUtils.isNotBlank(this.getImeiArea()) ? this.getImeiArea().toLowerCase():getIp();
    }

    public String getLangcode() {
        return langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

    public Integer getSupport() {
        return support;
    }

    public void setSupport(Integer support) {
        this.support = support;
    }

    public String getTerminalApplication() {
        return terminalApplication;
    }

    public void setTerminalApplication(String terminalApplication) {
        this.terminalApplication = terminalApplication;
    }

    public String getWcode() {
        return StringUtils.isNotBlank(this.getImeiArea()) ? this.getImeiArea().toLowerCase():getIp();
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
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

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }




    //

    //
}
