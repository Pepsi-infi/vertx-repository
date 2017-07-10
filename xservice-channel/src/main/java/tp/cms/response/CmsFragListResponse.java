package tp.cms.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CmsFragListResponse {

    /**
     * cms写入缓存时间(毫秒数)
     */
    private long cacheTime;

    /**
     * 页面id
     */
    private Integer pageId;

    /**
     * 栏目信息
     */
    private List<CmsFragResponse> frags;

    public CmsFragListResponse() {
        super();
    }

    public CmsFragListResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        CmsFragListResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        CmsFragListResponseConverter.toJson(this, json);
        return json;
    }
    
    public long getCacheTime() {
        return cacheTime;
    }

    public void setCacheTime(long cacheTime) {
        this.cacheTime = cacheTime;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public List<CmsFragResponse> getFrags() {
        return frags;
    }

    public void setFrags(List<CmsFragResponse> frags) {
        this.frags = frags;
    }

    @Override
    public String toString() {
        return "CmsFragListResponse [cacheTime=" + cacheTime + ", pageId=" + pageId + ", frags=" + frags + "]";
    }
}
