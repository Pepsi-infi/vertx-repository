package user.childlock.response;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import utils.BaseResponse;

@DataObject(generateConverter = true)
public class ChildLockTpResponse extends BaseResponse {

    private ChildLockResponse data;

    public ChildLockTpResponse() {
        super();
    }

    public ChildLockTpResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        ChildLockTpResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ChildLockTpResponseConverter.toJson(this, json);
        return json;
    }
    
    public ChildLockResponse getData() {
        return data;
    }

    public void setData(ChildLockResponse data) {
        this.data = data;
    }

}
