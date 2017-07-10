package tp.live.response;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 直播频道列表返回
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@DataObject(generateConverter = true)

public class ChannelListResponse {

    private List<ChannelResponse> rows;

    
    public ChannelListResponse() {
        super();
    }

    public ChannelListResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        ChannelListResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ChannelListResponseConverter.toJson(this, json);
        return json;
    }
    
    public List<ChannelResponse> getRows() {
        return rows;
    }

    public void setRows(List<ChannelResponse> rows) {
        this.rows = rows;
    }

}