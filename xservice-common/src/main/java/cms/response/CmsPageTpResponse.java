package cms.response;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * CMS页面
 */
@DataObject(generateConverter = true)
public class CmsPageTpResponse{

    private CmsPageTpResponseData data;

    public CmsPageTpResponse() {
    }


    public CmsPageTpResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        CmsPageTpResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        CmsPageTpResponseConverter.toJson(this, json);
        return json;
    }
    public CmsPageTpResponseData getData() {
        return this.data;
    }

    public void setData(CmsPageTpResponseData data) {
        this.data = data;
    }


}
