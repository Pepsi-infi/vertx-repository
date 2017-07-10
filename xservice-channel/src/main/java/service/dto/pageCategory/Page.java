package service.dto.pageCategory;

import constants.ChannelConstants;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * 定制页面
 */
@DataObject(generateConverter = true, inheritConverter=true)

public class Page extends Channel {

    /**
     * 标题
     */
    private String name;

    /**
     * 副标题
     */
    private String subName;

    /**
     * 图片
     */
    private String img;

    /**
     * 页面ID
     */
    private Integer pageId;

    
    public Page(){}

    public Page(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        PageConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        PageConverter.toJson(this, json);
        return json;
    }
    
    public int getDataType() {
        return ChannelConstants.DataType.DATA_TYPE_PAGE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }
}
