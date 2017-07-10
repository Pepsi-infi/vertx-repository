package cache.dto;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

@SuppressWarnings("serial")
@DataObject(generateConverter = true)
public abstract class BaseCache implements Serializable {

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    protected static ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    public BaseCache() {
    }

    public BaseCache(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        BaseCacheConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        BaseCacheConverter.toJson(this, json);
        return json;
    }

    public JsonObject toDocumentJson() {
        JsonObject json = new JsonObject();
        return json;
    }
}
