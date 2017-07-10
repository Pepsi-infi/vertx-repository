package service.dto.page;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * 频道墙返回格式
 */
@DataObject(generateConverter = true,inheritConverter = true)
public class ChannelList {
    private String name; // 频道组名称
    private List<ChannelInfo> channels; // 频道组下的频道数据

    public ChannelList(){}

    public ChannelList(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        ChannelListConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ChannelListConverter.toJson(this, json);
        return json;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChannelInfo> getChannels() {
        return channels;
    }

    public void setChannels(List<ChannelInfo> channels) {
        this.channels = channels;
    }
}
