package tp.cms.response;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@JsonIgnoreProperties(ignoreUnknown = true)
@DataObject(generateConverter = true)
public class CmsFragResponse {

    /**
     * 栏目
     */
    private CmsColumnResponse columnEntity;

    /**
     * 内容对应频道
     */
    private Integer contentCid;

    /**
     * 内容id
     */
    private String contentId;

    /**
     * 内容标题
     */
    private String contentName;

    /**
     * 内容类型
     */
    private Integer contentType;

    /**
     * 手动条数
     */
    private Integer contentManulNum;

    /**
     * 总条数
     */
    private Integer contentTotal;

    /**
     * TODO
     */
    private String contentPicSize;
    /**
     * 刷新频率
     */
    private Integer contentRate;
    /**
     * TODO
     */
    private String contentRid;
    /**
     * 排序 1按权重排序 2随机排序 3按更新时间 4按创建时间
     */
    private Integer contentSort;
    /**
     * 输出样式
     */
    private String contentStyle;
    /**
     * TODO
     */
    private String contentSubName;

    /**
     * 专辑/视频 1 视频 2 专辑
     */
    private Integer contentVideoType;
    /**
     * TODO
     */
    private Integer fragId;
    /**
     * TODO
     */
    private Integer id;
    /**
     * TODO
     */
    private Integer isLock;
    /**
     * TODO
     */
    private Integer isOrder;
    /**
     * TODO
     */
    private Integer isPage;
    /**
     * TODO
     */
    private String moduleSort;
    /**
     * TODO
     */
    private Map<String, String> nameLanguageJson;
    /**
     * TODO
     */
    private Integer pid;
    /**
     * TODO
     */
    private String pushPlatform;
    /**
     * 跳转频道id
     */
    private Integer redirectCid;
    /**
     * 跳转页面id
     */
    private String redirectPageId;
    /**
     * TODO
     */
    private Integer redirectSubCid;
    /**
     * TODO
     */
    private String redirectSubPageId;
    /**
     * TODO
     */
    private Integer redirectSubType;
    /**
     * TODO
     */
    private String redirectSubUrl;
    /**
     * TODO
     */
    private Integer redirectSubVideoType;
    /**
     * 跳转类型 1 搜索 2 首页 3 h5
     */
    private Integer redirectType;
    /**
     * 跳转url
     */
    private String redirectUrl;
    /**
     * 专辑/视频 1 视频 2 专辑
     */
    private Integer redirectVideoType;
    /**
     * TODO
     */
    private Integer site;
    /**
     * TODO
     */
    private Map<String, String> subNameLanguageJson;
    /**
     * 0--读取frags下的板块内容，1--读取frags节点下subfrags下板块内容。默认值：0
     */
    private Integer fragType;

    /**
     * 子栏目栏目信息
     */
    private List<CmsFragResponse> subFrags;

    public CmsFragResponse() {
        super();
    }

    public CmsFragResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        CmsFragResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        CmsFragResponseConverter.toJson(this, json);
        return json;
    }
    
    public CmsColumnResponse getColumnEntity() {
        return columnEntity;
    }

    public void setColumnEntity(CmsColumnResponse columnEntity) {
        this.columnEntity = columnEntity;
    }

    public Integer getContentCid() {
        return contentCid;
    }

    public void setContentCid(Integer contentCid) {
        this.contentCid = contentCid;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
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

    public String getContentPicSize() {
        return contentPicSize;
    }

    public void setContentPicSize(String contentPicSize) {
        this.contentPicSize = contentPicSize;
    }

    public Integer getContentRate() {
        return contentRate;
    }

    public void setContentRate(Integer contentRate) {
        this.contentRate = contentRate;
    }

    public String getContentRid() {
        return contentRid;
    }

    public void setContentRid(String contentRid) {
        this.contentRid = contentRid;
    }

    public Integer getContentSort() {
        return contentSort;
    }

    public void setContentSort(Integer contentSort) {
        this.contentSort = contentSort;
    }

    public String getContentStyle() {
        return contentStyle;
    }

    public void setContentStyle(String contentStyle) {
        this.contentStyle = contentStyle;
    }

    public String getContentSubName() {
        return contentSubName;
    }

    public void setContentSubName(String contentSubName) {
        this.contentSubName = contentSubName;
    }

    public Integer getContentTotal() {
        return contentTotal;
    }

    public void setContentTotal(Integer contentTotal) {
        this.contentTotal = contentTotal;
    }

    public Integer getContentVideoType() {
        return contentVideoType;
    }

    public void setContentVideoType(Integer contentVideoType) {
        this.contentVideoType = contentVideoType;
    }

    public Integer getFragId() {
        return fragId;
    }

    public void setFragId(Integer fragId) {
        this.fragId = fragId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public Integer getIsOrder() {
        return isOrder;
    }

    public void setIsOrder(Integer isOrder) {
        this.isOrder = isOrder;
    }

    public Integer getIsPage() {
        return isPage;
    }

    public void setIsPage(Integer isPage) {
        this.isPage = isPage;
    }

    public String getModuleSort() {
        return moduleSort;
    }

    public void setModuleSort(String moduleSort) {
        this.moduleSort = moduleSort;
    }

    public Map<String, String> getNameLanguageJson() {
        return nameLanguageJson;
    }

    public void setNameLanguageJson(Map<String, String> nameLanguageJson) {
        this.nameLanguageJson = nameLanguageJson;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPushPlatform() {
        return pushPlatform;
    }

    public void setPushPlatform(String pushPlatform) {
        this.pushPlatform = pushPlatform;
    }

    public Integer getRedirectCid() {
        return redirectCid;
    }

    public void setRedirectCid(Integer redirectCid) {
        this.redirectCid = redirectCid;
    }

    public String getRedirectPageId() {
        return redirectPageId;
    }

    public void setRedirectPageId(String redirectPageId) {
        this.redirectPageId = redirectPageId;
    }

    public Integer getRedirectSubCid() {
        return redirectSubCid;
    }

    public void setRedirectSubCid(Integer redirectSubCid) {
        this.redirectSubCid = redirectSubCid;
    }

    public String getRedirectSubPageId() {
        return redirectSubPageId;
    }

    public void setRedirectSubPageId(String redirectSubPageId) {
        this.redirectSubPageId = redirectSubPageId;
    }

    public Integer getRedirectSubType() {
        return redirectSubType;
    }

    public void setRedirectSubType(Integer redirectSubType) {
        this.redirectSubType = redirectSubType;
    }

    public String getRedirectSubUrl() {
        return redirectSubUrl;
    }

    public void setRedirectSubUrl(String redirectSubUrl) {
        this.redirectSubUrl = redirectSubUrl;
    }

    public Integer getRedirectSubVideoType() {
        return redirectSubVideoType;
    }

    public void setRedirectSubVideoType(Integer redirectSubVideoType) {
        this.redirectSubVideoType = redirectSubVideoType;
    }

    public Integer getRedirectType() {
        return redirectType;
    }

    public void setRedirectType(Integer redirectType) {
        this.redirectType = redirectType;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public Integer getRedirectVideoType() {
        return redirectVideoType;
    }

    public void setRedirectVideoType(Integer redirectVideoType) {
        this.redirectVideoType = redirectVideoType;
    }

    public Integer getSite() {
        return site;
    }

    public void setSite(Integer site) {
        this.site = site;
    }

    public Map<String, String> getSubNameLanguageJson() {
        return subNameLanguageJson;
    }

    public void setSubNameLanguageJson(Map<String, String> subNameLanguageJson) {
        this.subNameLanguageJson = subNameLanguageJson;
    }

    public Integer getFragType() {
        return fragType;
    }

    public void setFragType(Integer fragType) {
        this.fragType = fragType;
    }

    public List<CmsFragResponse> getSubFrags() {
        return subFrags;
    }

    public void setSubFrags(List<CmsFragResponse> subFrags) {
        this.subFrags = subFrags;
    }
}
