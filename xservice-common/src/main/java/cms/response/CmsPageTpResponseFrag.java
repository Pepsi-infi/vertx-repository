package cms.response;

import java.util.List;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
@DataObject(generateConverter = true)
public class CmsPageTpResponseFrag {

    private List<CmsBlockContentTpResponse> blockContents;

    private String contentName;

    private String contentStyle;

    private String contentRid;

    private Integer contentType;

    private Integer contentManulNum;

    private String redirectPageId;

    private String redirectSubUrl;

    private String redirectSubPageId;
    /**
     * 主标题跳转地址－tv版用作配置色块推荐dataurl
     */
    private String redirectUrl;

    public CmsPageTpResponseFrag() {
    }


    public CmsPageTpResponseFrag(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        CmsPageTpResponseFragConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        CmsPageTpResponseFragConverter.toJson(this, json);
        return json;
    }
    public List<CmsBlockContentTpResponse> getBlockContents() {
        return this.blockContents;
    }

    public void setBlockContents(List<CmsBlockContentTpResponse> blockContents) {
        this.blockContents = blockContents;
    }

    public String getContentName() {
        return this.contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getContentStyle() {
        return contentStyle;
    }

    public void setContentStyle(String contentStyle) {
        this.contentStyle = contentStyle;
    }

    public String getContentRid() {
        return contentRid;
    }

    public void setContentRid(String contentRid) {
        this.contentRid = contentRid;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public Integer getContentManulNum() {
        return contentManulNum;
    }

    public void setContentManulNum(Integer contentManulNum) {
        this.contentManulNum = contentManulNum;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectPageId() {
        return redirectPageId;
    }

    public void setRedirectPageId(String redirectPageId) {
        this.redirectPageId = redirectPageId;
    }

    public String getRedirectSubUrl() {
        return redirectSubUrl;
    }

    public void setRedirectSubUrl(String redirectSubUrl) {
        this.redirectSubUrl = redirectSubUrl;
    }

    public String getRedirectSubPageId() {
        return redirectSubPageId;
    }

    public void setRedirectSubPageId(String redirectSubPageId) {
        this.redirectSubPageId = redirectSubPageId;
    }

}
