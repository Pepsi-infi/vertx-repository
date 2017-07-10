package tp.live.response;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LmsDataListResponse{
    public static final String SUCCESS = "0";// 返回成功

    private String code; // 0表示请求成功
    private List<LmsDataResponse> data;
    private String message;

    public LmsDataListResponse() {
        super();
    }

    public LmsDataListResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        LmsDataListResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        LmsDataListResponseConverter.toJson(this, json);
        return json;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<LmsDataResponse> getData() {
        return data;
    }

    public void setData(List<LmsDataResponse> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
