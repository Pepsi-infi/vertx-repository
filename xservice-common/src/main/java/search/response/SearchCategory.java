package search.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@JsonIgnoreProperties(ignoreUnknown = true)
@DataObject(generateConverter = true,inheritConverter = true)
public class SearchCategory extends BaseResponse {
    private Integer category; // 分类ID 0:为明星
    private String category_name; // 分类名称
    private Integer count; // 分类总数
    private Integer dataType; // 数据类型:1、专辑 2、视频

    public SearchCategory() {
    }

    public SearchCategory(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        SearchCategoryConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SearchCategoryConverter.toJson(this, json);
        return json;
    }

    public Integer getCategory() {
        return this.category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getCategory_name() {
        return this.category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }
}
