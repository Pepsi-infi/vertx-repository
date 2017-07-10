package service.dto.theaterpack;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import utils.BaseResponse;
@DataObject(generateConverter = true)
public class TheaterPackedWapper extends BaseResponse {

    private WaterMarksDto data;

    
    public TheaterPackedWapper(){}

    public TheaterPackedWapper(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        TheaterPackedWapperConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        TheaterPackedWapperConverter.toJson(this, json);
        return json;
    }
    public WaterMarksDto getData() {
        return data;
    }

    public void setData(WaterMarksDto data) {
        this.data = data;
    }

}
