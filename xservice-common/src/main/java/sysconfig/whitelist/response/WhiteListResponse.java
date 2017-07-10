package sysconfig.whitelist.response;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class WhiteListResponse{
    private Integer affected;

    public WhiteListResponse() {
    }

    public WhiteListResponse(Integer affected) {
        this.affected = affected;
    }

    public WhiteListResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        WhiteListResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        // BaseResponseConverter.toJson(this, json);
        WhiteListResponseConverter.toJson(this, json);
        return json;
    }

    public Integer getAffected() {
        return affected;
    }

    public void setAffected(Integer affected) {
        this.affected = affected;
    }
}
