package cache.dto;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
@DataObject(generateConverter = true)
public class PicAll {
    
    @Protobuf(fieldType = FieldType.STRING, order = 1)
    private String picKey;

    @Protobuf(fieldType = FieldType.STRING, order = 2)
    private String picValue;

    public PicAll() {
    }
    
    public PicAll(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        PicAllConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        PicAllConverter.toJson(this, json);
        return json;
    }


//    public PicAll(String picKey, String picValue) {
//        this.picKey = picKey;
//        this.picValue = picValue;
//    }

    public String getPicKey() {
        return picKey;
    }

    public void setPicKey(String picKey) {
        this.picKey = picKey;
    }

    public String getPicValue() {
        return picValue;
    }

    public void setPicValue(String picValue) {
        this.picValue = picValue;
    }

    @Override
    public String toString() {
        return "PicAll [picKey=" + picKey + ", picValue=" + picValue + "]";
    }
    
}
