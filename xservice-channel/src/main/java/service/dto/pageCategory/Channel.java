package service.dto.pageCategory;

import constants.ChannelConstants;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * 频道
 */
@DataObject(generateConverter = true, inheritConverter=true)
public class Channel extends BaseChannel {

    /**
     * 
     */
    private static final long serialVersionUID = 9008575583831111538L;

    /**
     * 频道ID private int id; // 频道ID
     */
    private Integer channelId;

    /**
     * 频道名称 private String name;// 菜单名称
     */
    private String name;
    private String subName;
    private String channelName;

    /**
     * 父频道ID
     */
    private Integer parentChannelId;

    /**
     * 标题icon private String bigPic;// 焦点图
     */
    private String img;

    /**
     * 标题icon private String smallPic;// 小图
     */
    private String titleIcon;

    private String pic1;

    private String pic2;

    /**
     * 标题背景色 private String color;// 颜色值对应频道墙
     */
    private String titleBgColor;

    /**
     * 标题焦点1 private String focus;// 看点，二级标题
     */
    private String titleFocus1;

    /**
     * 标题焦点2 private String focus2;// 总集或部
     */
    private String titleFocus2;

    /**
     * 内容分类 private int cid;// 内容分类ID
     */
    private Integer categoryId;

    /**
     * 频道code
     */
    private String channelCode;//

    /**
     * 专辑id
     */
    private Integer albumId;//

    /**
     * 专题id
     */
    private Integer pid;//

    /**
     * 默认码流
     */
    private String defaultStream;
    private String defaultStreamName;
    /**
     * 兼容原先的老逻辑
     * @return
     */
    private int dataType;

    /**
     * 频道子类型 0 跳转频道首页 1 跳转cp列表详情页 levidi 1.0.*使用
     */
    private Integer subType;

    /**
     * 会员id
     */
    private Integer productId;

    /**
     * 会员名称
     */
    private String productName;

    /**
     * 是否订阅（可扩展）0、未订阅 1、订阅
     */
    private Integer isSelected;

    /**
     * 页面ID
     */
    private Integer pageId;

    public Channel(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        ChannelConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ChannelConverter.toJson(this, json);
        return json;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public Integer getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Integer isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public int getDataType() {
        return ChannelConstants.DataType.DATA_TYPE_CHANNEL;
    }

    @Override
    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public Integer getChannelId() {
        return this.channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentChannelId() {
        return this.parentChannelId;
    }

    public void setParentChannelId(Integer parentChannelId) {
        this.parentChannelId = parentChannelId;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitleBgColor() {
        return this.titleBgColor;
    }

    public void setTitleBgColor(String titleBgColor) {
        this.titleBgColor = titleBgColor;
    }

    public String getTitleFocus1() {
        return this.titleFocus1;
    }

    public void setTitleFocus1(String titleFocus1) {
        this.titleFocus1 = titleFocus1;
    }

    public String getTitleFocus2() {
        return this.titleFocus2;
    }

    public void setTitleFocus2(String titleFocus2) {
        this.titleFocus2 = titleFocus2;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getChannelName() {
        return this.channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getTitleIcon() {
        return this.titleIcon;
    }

    public void setTitleIcon(String titleIcon) {
        this.titleIcon = titleIcon;
    }

    public String getChannelCode() {
        return this.channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public Integer getAlbumId() {
        return this.albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getDefaultStream() {
        return this.defaultStream;
    }

    public void setDefaultStream(String defaultStream) {
        this.defaultStream = defaultStream;
    }

    public String getDefaultStreamName() {
        return this.defaultStreamName;
    }

    public void setDefaultStreamName(String defaultStreamName) {
        this.defaultStreamName = defaultStreamName;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public Channel() {

    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
