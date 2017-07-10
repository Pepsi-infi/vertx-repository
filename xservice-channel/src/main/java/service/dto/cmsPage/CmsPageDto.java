package service.dto.cmsPage;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.List;

@DataObject(generateConverter = true)
public class CmsPageDto {
    private List<CmsCategoryDto> categoryList;
    private String pageId;

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public CmsPageDto() {
        super();
    }

    public CmsPageDto(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        CmsPageDtoConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        CmsPageDtoConverter.toJson(this, json);
        return json;
    }
    
    public List<CmsCategoryDto> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CmsCategoryDto> categoryList) {
        this.categoryList = categoryList;
    }
}
