package tp.cms.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import utils.BaseResponse;

/**
 * get column response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@DataObject(generateConverter = true)
public class CmsColumnListResponse extends BaseResponse {
    /**
     * 返回码(200表示正常，非200表示异常)
     */
    private Integer statusCode;
    /**
     * 返回值描述信息
     */
    private String msg;
    /**
     * 栏目数据
     */
    private List<CmsColumnResponse> data;

    public CmsColumnListResponse() {
        super();
    }

    public CmsColumnListResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        CmsColumnListResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        CmsColumnListResponseConverter.toJson(this, json);
        return json;
    }
    
    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<CmsColumnResponse> getData() {
        return data;
    }

    public void setData(List<CmsColumnResponse> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ColumnInfoResponse [statusCode=" + statusCode + ", msg=" + msg + ", data=" + data + "]";
    }

}
