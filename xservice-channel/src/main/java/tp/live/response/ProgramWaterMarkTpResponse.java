package tp.live.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProgramWaterMarkTpResponse {
    private Integer result;

    private String statusCode;

    private String code;

    private ProgramWaterMarkData data;

    public ProgramWaterMarkTpResponse(){}
    public ProgramWaterMarkTpResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        ProgramWaterMarkTpResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ProgramWaterMarkTpResponseConverter.toJson(this, json);
        return json;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public ProgramWaterMarkData getData() {
        return data;
    }

    public void setData(ProgramWaterMarkData data) {
        this.data = data;
    }
}
