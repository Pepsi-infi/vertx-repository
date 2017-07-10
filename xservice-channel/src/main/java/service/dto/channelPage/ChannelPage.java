package service.dto.channelPage;

import constants.ChannelConstants;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * Created by zhushenghao1 on 16/12/27.
 */
@DataObject(generateConverter = true,inheritConverter = true)
public class ChannelPage {
    private List<BaseDto> focus; // 焦点图
    private List<ChannelBlockDto> block; // 频道模块
    private String backUrl;//备份接口请求地址
    private String focusDataType=ChannelConstants.DATA_TYPE_CHANNEL;

    public ChannelPage(){}

    public ChannelPage(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        ChannelPageConverter.fromJson(json, this);
        if (json.getValue("focus") instanceof JsonArray) {
            java.util.ArrayList<BaseDto> list = new java.util.ArrayList<>();
            json.getJsonArray("focus").forEach( item -> {
                if (item instanceof JsonObject) {
                    switch (json.getString("focusDataType")) {
                        case ChannelConstants.DATA_TYPE_CHANNEL:
                            list.add(new BlockDataDto((JsonObject) item));
                            break;
                        case ChannelConstants.DATA_TYPE_ADDON:
                            list.add(new AddOnInfoDto((JsonObject) item));
                            break;
                        default:
                            list.add(new BaseDto((JsonObject) item));
                            break;
                    }
                }
                this.setFocus(list);
            });
        }
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ChannelPageConverter.toJson(this, json);
        return json;
    }

    public List<BaseDto> getFocus() {
        return focus;
    }

    public void setFocus(List<BaseDto> focus) {
        this.focus = focus;
    }

    public List<ChannelBlockDto> getBlock() {
        return block;
    }

    public void setBlock(List<ChannelBlockDto> block) {
        this.block = block;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public String getFocusDataType() {
        return focusDataType;
    }

    public void setFocusDataType(String focusDataType) {
        this.focusDataType = focusDataType;
    }
}
