package service.dto.cmsPage;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import utils.BaseResponse;

@DataObject(generateConverter = true)
public class CmsPageWrapper extends BaseResponse{

    private CmsPageDto data;

    public CmsPageWrapper() {
        super();
    }

    public CmsPageWrapper(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        CmsPageWrapperConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        CmsPageWrapperConverter.toJson(this, json);
        return json;
    }
    
    public CmsPageDto getData() {
        return data;
    }

    public void setData(CmsPageDto data) {
        this.data = data;
    }

}
