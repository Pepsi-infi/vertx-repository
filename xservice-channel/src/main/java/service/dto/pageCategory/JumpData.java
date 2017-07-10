package service.dto.pageCategory;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * 数据基类
 */
@DataObject(generateConverter = true)

public class JumpData {

    /**
     * 跳转类型
     */
    private Integer type;

    /**
     * 跳转信息体
     */
    private Channel Value;

    public JumpData(){}

    public JumpData(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        JumpDataConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        JumpDataConverter.toJson(this, json);
        return json;
    }
    
    
    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Channel getValue() {
        return Value;
    }

    public void setValue(Channel value) {
        Value = value;
    }

}
