package service.dto.channelPage;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Created by zhushenghao1 on 17/1/3.
 */
@DataObject(generateConverter = true,inheritConverter = true)
public class BaseDto {

    public BaseDto() {
    }

    public BaseDto(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        BaseDtoConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        BaseDtoConverter.toJson(this, json);
        return json;
    }
}