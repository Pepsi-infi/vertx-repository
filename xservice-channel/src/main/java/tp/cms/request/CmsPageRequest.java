package tp.cms.request;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class CmsPageRequest {

    /**
     * 平台：live
     */
    private String platform;

    /**
     * 语言
     */
    private String lang;

    private String pageId;

    
    public CmsPageRequest() {
        super();
    }

    public CmsPageRequest(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        CmsPageRequestConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        CmsPageRequestConverter.toJson(this, json);
        return json;
    }
    
    
    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String build() {
        return new StringBuffer("/page_content_").append(pageId).append(".json?platform=").append(platform).toString();
    }
}
