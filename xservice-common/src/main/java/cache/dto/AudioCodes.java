package cache.dto;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true,inheritConverter = true)
public class AudioCodes {
    @Protobuf(fieldType = FieldType.STRING, order = 1)
    private String status;
    @Protobuf(fieldType = FieldType.STRING, order = 2)
    private String stream;

    public AudioCodes() {
    }

    public AudioCodes(String k, String v) {
        this.status = k;
        this.stream = v;
    }

    public AudioCodes(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        AudioCodesConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        AudioCodesConverter.toJson(this, json);
        return json;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }
}
