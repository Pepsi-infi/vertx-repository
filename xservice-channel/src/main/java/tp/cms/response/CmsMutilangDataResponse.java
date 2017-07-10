package tp.cms.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cms.response.CmsBlockTpResponse;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Created by IntelliJ IDEA.
 * User: xuli
 * Date：16/4/25
 * Time: 18:59
 */
@DataObject(generateConverter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CmsMutilangDataResponse {

    private String innerCode;

    private String code;

    private CmsBlockTpResponse data;

    public CmsMutilangDataResponse(){}

    public CmsMutilangDataResponse(JsonObject json) {
        CmsMutilangDataResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        CmsMutilangDataResponseConverter.toJson(this, json);
        return json;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CmsBlockTpResponse getData() {
        return data;
    }

    public void setData(CmsBlockTpResponse data) {
        this.data = data;
    }

    public String getInnerCode() {
        return innerCode;
    }

    public void setInnerCode(String innerCode) {
        this.innerCode = innerCode;
    }

}
