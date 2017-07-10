package cache.dto;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;


@DataObject(generateConverter = true)
public class WatchingFocusCache {
    @Protobuf(fieldType = FieldType.STRING,order = 1)
    private String desc;

    @Protobuf(fieldType = FieldType.INT32,order = 2)
    private Integer id;

    @Protobuf(fieldType = FieldType.INT64,order = 3)
    private Long time;

    public WatchingFocusCache() {
    }

    public WatchingFocusCache(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        WatchingFocusCacheConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        WatchingFocusCacheConverter.toJson(this, json);
        return json;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    //    public void setTime(String time) {
//        this.time = TimeUtil.hhmmss2Timestamp(time);
//    }
}
