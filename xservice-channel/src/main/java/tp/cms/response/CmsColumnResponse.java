package tp.cms.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CmsColumnResponse {

    /**
     * ID
     */
    private Integer id;

    /**
     * 状态（1：上线; 0：未上线）
     */
    private Integer status;

    /**
     * 栏目名称
     */
    private String columnName;

    /**
     * 字体颜色
     */
    private String fontColor;
    /**
     * 字体大小
     */
    private String fontSize;

    /**
     * 图标URL
     */
    private String icon;

    /**
     * 栏目选中时的图片
     */
    private String icon2;

    /**
     * 播放类型 (1：轮播； 2：卫视；3：直播； 4：焦点)
     */
    private Integer columnType;

    /**
     * 栏目颜色
     */
    private String columnColor;

    /**
     * 一级栏目id
     */
    private Integer pid;

    /**
     * 归属地
     */
    private String area;

    /**
     * TODO 意义未知
     */
    private String orderr;

    /**
     * 产品分类(24:手机; 14:tv)
     */
    private Integer product;

    /**
     * TODO 意义未知
     */
    private Integer site;

    /**
     * 是否有查询url()
     */
    private Integer hasDataSearchAPI;

    /**
     * 查询url
     */
    private String searchUrl;

    /**
     * 栏目内容
     */
    private List<CmsColumnContentResponse> columnContent;

    
    public CmsColumnResponse() {
        super();
    }

    public CmsColumnResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        CmsColumnResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        CmsColumnResponseConverter.toJson(this, json);
        return json;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon2() {
        return icon2;
    }

    public void setIcon2(String icon2) {
        this.icon2 = icon2;
    }

    public Integer getColumnType() {
        return columnType;
    }

    public void setColumnType(Integer columnType) {
        this.columnType = columnType;
    }

    public String getColumnColor() {
        return columnColor;
    }

    public void setColumnColor(String columnColor) {
        this.columnColor = columnColor;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getOrderr() {
        return orderr;
    }

    public void setOrderr(String orderr) {
        this.orderr = orderr;
    }

    public Integer getProduct() {
        return product;
    }

    public void setProduct(Integer product) {
        this.product = product;
    }

    public Integer getSite() {
        return site;
    }

    public void setSite(Integer site) {
        this.site = site;
    }

    public Integer getHasDataSearchAPI() {
        return hasDataSearchAPI;
    }

    public void setHasDataSearchAPI(Integer hasDataSearchAPI) {
        this.hasDataSearchAPI = hasDataSearchAPI;
    }

    public String getSearchUrl() {
        return searchUrl;
    }

    public void setSearchUrl(String searchUrl) {
        this.searchUrl = searchUrl;
    }

    public List<CmsColumnContentResponse> getColumnContent() {
        return columnContent;
    }

    public void setColumnContent(List<CmsColumnContentResponse> columnContent) {
        this.columnContent = columnContent;
    }

    @Override
    public String toString() {
        return "CmsColumnResponse [id=" + id + ", status=" + status + ", columnName=" + columnName + ", fontColor="
                + fontColor + ", fontSize=" + fontSize + ", icon=" + icon + ", icon2=" + icon2 + ", columnType="
                + columnType + ", columnColor=" + columnColor + ", pid=" + pid + ", area=" + area + ", orderr="
                + orderr + ", product=" + product + ", site=" + site + ", hasDataSearchAPI=" + hasDataSearchAPI
                + ", searchUrl=" + searchUrl + ", columnContent=" + columnContent + "]";
    }
}
