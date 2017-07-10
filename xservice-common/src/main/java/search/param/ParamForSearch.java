package search.param;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Created by zhushenghao1 on 17/1/16.
 */
@DataObject(generateConverter = true,inheritConverter = true)
public class ParamForSearch{
    private String terminalApplication;
    private String areaCode;
    private String langcode;
    private String uid;
    private String imeiArea;
    private String countryArea;
    private String ip;

    public ParamForSearch() {
    }


    public ParamForSearch(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        ParamForSearchConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ParamForSearchConverter.toJson(this, json);
        return json;
    }

    public String getTerminalApplication() {
        return terminalApplication;
    }

    public void setTerminalApplication(String terminalApplication) {
        this.terminalApplication = terminalApplication;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getLangcode() {
        return langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
}
