package service.dto;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import utils.BaseResponse;

/**
 * Created by lufei
 * Date : 2017/8/3 17:43
 * Description :
 */
@DataObject(generateConverter = true)

public class MsgStatResultPageWrapper extends BaseResponse {

    private MsgStatResultPage data;

    public MsgStatResultPageWrapper() {
        super();
    }

    public MsgStatResultPageWrapper(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        MsgStatResultPageWrapperConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        MsgStatResultPageWrapperConverter.toJson(this, json);
        return json;
    }

    public MsgStatResultPage getData() {
        return data;
    }

    public void setData(MsgStatResultPage data) {
        this.data = data;
    }
}
