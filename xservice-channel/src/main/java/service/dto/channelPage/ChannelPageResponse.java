package service.dto.channelPage;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import service.dto.channelPage.ChannelPageResponseConverter;
import utils.BaseResponse;

/**
 * Created by zhushenghao1 on 16/12/30.
 */
@DataObject(generateConverter = true,inheritConverter = true)
public class ChannelPageResponse extends BaseResponse {

    /**
     * 数据
     */
    private ChannelPage data;

    public ChannelPageResponse() {

    }

    public ChannelPageResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        ChannelPageResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ChannelPageResponseConverter.toJson(this, json);
        return json;
    }

    public ChannelPage getData() {
        return data;
    }

    public void setData(ChannelPage data) {
        this.data = data;
    }
}
