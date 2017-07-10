package tp.live.response;

import java.util.List;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class ZhiBoDataSetTpResponse {

    private List<ZhiBoDataResponse> rows;

    public ZhiBoDataSetTpResponse() {
    }

    public ZhiBoDataSetTpResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        ZhiBoDataSetTpResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ZhiBoDataSetTpResponseConverter.toJson(this, json);
        return json;
    }

    public List<ZhiBoDataResponse> getRows() {
        return rows;
    }

    public void setRows(List<ZhiBoDataResponse> rows) {
        this.rows = rows;
    }

 

}