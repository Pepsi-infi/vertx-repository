package tp.live.response;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@DataObject(generateConverter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayBillCurrentTpResponse {

    private List<PlayBillCurrentTpRows> rows;

    public PlayBillCurrentTpResponse() {
    }

    public PlayBillCurrentTpResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        PlayBillCurrentTpResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        PlayBillCurrentTpResponseConverter.toJson(this, json);
        return json;
    }

    public List<PlayBillCurrentTpRows> getRows() {
        return rows;
    }

    public void setRows(List<PlayBillCurrentTpRows> rows) {
        this.rows = rows;
    }
}
