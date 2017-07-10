package tp.live.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TheaterWaterMarkTpResponse {

    private int result;

    private String statusCode;

    private List<WaterMarkDeliveryTpResponse> data;

    public TheaterWaterMarkTpResponse(){}
    public TheaterWaterMarkTpResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        TheaterWaterMarkTpResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        TheaterWaterMarkTpResponseConverter.toJson(this, json);
        return json;
    }
    
    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public List<WaterMarkDeliveryTpResponse> getData() {
        return data;
    }

    public void setData(List<WaterMarkDeliveryTpResponse> data) {
        this.data = data;
    }

}
