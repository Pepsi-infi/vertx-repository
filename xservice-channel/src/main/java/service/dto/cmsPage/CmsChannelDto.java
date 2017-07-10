package service.dto.cmsPage;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class CmsChannelDto {

    public static final String BRANCH_TYPE_MULTI_PROGRAM = "1";
    public static final String BRANCH_TYPE_MULTI_LANGUAGE = "2";
    public static final String CHANNEL_SOURCE_REC = "1"; // 频道来源为推荐

    private String channelId; // 频道id
    private String channelName; // 频道名称
    private String channelPic; // 频道图片
    private String channelBigPic; // 频道大图
    private String isPay; // 是否付费 1付费 0:免费
    private String type; // 频道类型 2:直播 3:轮播 4:卫视台
    private String isLiveFromTV; // 直播是否来自电视台节目 1: 是 0: 不是
    private String src; // 频道来源，cms,rec等
    private Integer weight; // 权重
    private String liveType; // 直播类型名称，例如 sports music other
    private String channelEname; // 频道的英文名称
    private String signal; // 直播数据源分类 2:卫视台 7:轮播台
    private String channelClass; // 轮播的分类id
    private String streamTips; // 盗播流提示
    private String numericKeys; // 台号
    private String branchType; // 多直播分支类型
    private String selfCopyRight; // 是否自有版权
    private String fav; // 是否收藏
    private String isDolby; // 是否是杜比直播
    private String isPanoramicView; // 是否是全景直播
    private String waterLogo; // 轮播台的水印角标
    private String isArtificialRecommend; // 是否是直播后台人工强推的频道
    private String orderNo;// 序号，客户端排序用
    private String is3D;// 是否为3D频道（1是 0否）
    private String is4K;// 是否为4K频道（0 否 1 4K 2 伪4K）
//    private boolean isSupportPushVideo = true;
    private String isDrm;// 是否是drm
    private Integer isAnchor = 0; // 是否是主播 第三方直播用
    private String avator; // 主播头像
    private String nickName; // 主播昵称
    private String thirdLiveId; // 第三方直播id
    private String cpName; // 第三方直播cp名称
    /**
     * 资源类型(http://wiki.letv.cn/pages/viewpage.action?pageId=60952336#id-接口使用说明-
     * 常用字典表)
     */
    private Integer rType;
    /**
     * 跳转目标
     */
    private String rValue;

    /**
     * 打开H5横竖屏方式 0自适应 1竖屏 2横屏
     */
    private String webViewType;
    private String channelDesc;
    private String corner;

    private String columnId;

    public CmsChannelDto() {
        super();
    }

    public CmsChannelDto(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        CmsChannelDtoConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        CmsChannelDtoConverter.toJson(this, json);
        return json;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getIsDrm() {
        return isDrm;
    }

    public void setIsDrm(String isDrm) {
        this.isDrm = isDrm;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getIs3D() {
        return is3D;
    }

    public void setIs3D(String is3d) {
        is3D = is3d;
    }

    public String getIs4K() {
        return is4K;
    }

    public void setIs4K(String is4k) {
        is4K = is4k;
    }

    public String getIsLiveFromTV() {
        return isLiveFromTV;
    }

    public void setIsLiveFromTV(String isLiveFromTV) {
        this.isLiveFromTV = isLiveFromTV;
    }

    public String getIsPanoramicView() {
        return isPanoramicView;
    }

    public void setIsPanoramicView(String isPanoramicView) {
        this.isPanoramicView = isPanoramicView;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getIsArtificialRecommend() {
        return isArtificialRecommend;
    }

    public void setIsArtificialRecommend(String isArtificialRecommend) {
        this.isArtificialRecommend = isArtificialRecommend;
    }

    public String getBranchType() {
        return branchType;
    }

    public void setBranchType(String branchType) {
        this.branchType = branchType;
    }

    public String getWaterLogo() {
        return waterLogo;
    }

    public void setWaterLogo(String waterLogo) {
        this.waterLogo = waterLogo;
    }

    public String getIsDolby() {
        return isDolby;
    }

    public void setIsDolby(String isDolby) {
        this.isDolby = isDolby;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public String getSelfCopyRight() {
        return selfCopyRight;
    }

    public void setSelfCopyRight(String selfCopyRight) {
        this.selfCopyRight = selfCopyRight;
    }

    public String getNumericKeys() {
        return numericKeys;
    }

    public void setNumericKeys(String numericKeys) {
        this.numericKeys = numericKeys;
    }

    public String getStreamTips() {
        return streamTips;
    }

    public void setStreamTips(String streamTips) {
        this.streamTips = streamTips;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    public String getChannelClass() {
        return channelClass;
    }

    public void setChannelClass(String channelClass) {
        this.channelClass = channelClass;
    }

    public String getLiveType() {
        return liveType;
    }

    public void setLiveType(String liveType) {
        this.liveType = liveType;
    }

    public String getChannelEname() {
        return channelEname;
    }

    public void setChannelEname(String channelEname) {
        this.channelEname = channelEname;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelPic() {
        return channelPic;
    }

    public void setChannelPic(String channelPic) {
        this.channelPic = channelPic;
    }

    public String getChannelBigPic() {
        return channelBigPic;
    }

    public void setChannelBigPic(String channelBigPic) {
        this.channelBigPic = channelBigPic;
    }

    public String getIsPay() {
        return isPay;
    }

    public void setIsPay(String isPay) {
        this.isPay = isPay;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIsAnchor() {
        return isAnchor;
    }

    public void setIsAnchor(Integer isAnchor) {
        this.isAnchor = isAnchor;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getThirdLiveId() {
        return thirdLiveId;
    }

    public void setThirdLiveId(String thirdLiveId) {
        this.thirdLiveId = thirdLiveId;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public Integer getrType() {
        return rType;
    }

    public void setrType(Integer rType) {
        this.rType = rType;
    }

    public String getrValue() {
        return rValue;
    }

    public void setrValue(String rValue) {
        this.rValue = rValue;
    }

    public String getWebViewType() {
        return webViewType;
    }

    public void setWebViewType(String webViewType) {
        this.webViewType = webViewType;
    }

    public String getChannelDesc() {
        return channelDesc;
    }

    public void setChannelDesc(String channelDesc) {
        this.channelDesc = channelDesc;
    }

    public String getCorner() {
        return corner;
    }

    public void setCorner(String corner) {
        this.corner = corner;
    }
}