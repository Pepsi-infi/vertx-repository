package sysconfig.whitelist.request;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Created by wangbingbing on 2016/11/15.
 */
@DataObject(generateConverter = true)
public class WhiteListRequest {
    private String moduleId;
    private String devId;
    private String time;
    private String Operator;
    private int status;

    public WhiteListRequest() {

    }

    public WhiteListRequest(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        WhiteListRequestConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        WhiteListRequestConverter.toJson(this, json);
        return json;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getOperator() {
        return Operator;
    }

    public void setOperator(String operator) {
        Operator = operator;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
