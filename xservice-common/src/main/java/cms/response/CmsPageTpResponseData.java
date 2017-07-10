package cms.response;

import java.util.List;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class CmsPageTpResponseData {

    private List<CmsPageTpResponseFrag> frags;

    public CmsPageTpResponseData() {
    }


    public CmsPageTpResponseData(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        CmsPageTpResponseDataConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        CmsPageTpResponseDataConverter.toJson(this, json);
        return json;
    }
    
    public List<CmsPageTpResponseFrag> getFrags() {
        return this.frags;
    }

    public void setFrags(List<CmsPageTpResponseFrag> frags) {
        this.frags = frags;
    }

}
