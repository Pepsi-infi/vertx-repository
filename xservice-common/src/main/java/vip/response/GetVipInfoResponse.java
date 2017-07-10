package vip.response;

import java.util.List;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class GetVipInfoResponse {

    private Integer code; // 0,//0代表正常，其他值代表错误
    private List<VipInfo> data;

    public GetVipInfoResponse() {
    }

    public GetVipInfoResponse(JsonObject json) {
        GetVipInfoResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        GetVipInfoResponseConverter.toJson(this, json);
        return json;
    }
    
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<VipInfo> getData() {
        return data;
    }

    public void setData(List<VipInfo> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GetVipInfoResponse [code=" + code + ", data=" + data + "]";
    }
}
