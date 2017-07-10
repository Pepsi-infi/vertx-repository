package cms.response;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

@SuppressWarnings("serial")
@DataObject(generateConverter = true)
public abstract class BaseResponse implements Serializable {
    private String innerCode;

    public BaseResponse() {
    }

    public BaseResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        BaseResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        BaseResponseConverter.toJson(this, json);
        return json;
    }

    public String getInnerCode() {
        return innerCode;
    }

    public void setInnerCode(String innerCode) {
        this.innerCode = innerCode;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
