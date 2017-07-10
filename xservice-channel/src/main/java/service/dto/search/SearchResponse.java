package service.dto.search;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import utils.BaseResponse;

/**
 * Created by zhushenghao1 on 17/1/13.
 */

@DataObject(generateConverter = true,inheritConverter = true)
public class SearchResponse extends BaseResponse {

    /**
     * 数据
     */
    private FilterResultDto data;

    public SearchResponse(){}
    public SearchResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        SearchResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SearchResponseConverter.toJson(this, json);
        return json;
    }

    public SearchResponse(FilterResultDto data) {
        this.data = data;
    }

    public FilterResultDto getData() {
        return data;
    }

    public void setData(FilterResultDto data) {
        this.data = data;
    }
}
