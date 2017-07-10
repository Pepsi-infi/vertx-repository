package vip.response;

import java.util.List;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class LecomGetPackageByProductIdResponse {
    private Integer code;
    private List<PackageInfo> data;
    
    
    public LecomGetPackageByProductIdResponse() {
    }

    public LecomGetPackageByProductIdResponse(JsonObject json) {
        LecomGetPackageByProductIdResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        LecomGetPackageByProductIdResponseConverter.toJson(this, json);
        return json;
    }
    
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public List<PackageInfo> getData() {
        return data;
    }
    public void setData(List<PackageInfo> data) {
        this.data = data;
    }
}
