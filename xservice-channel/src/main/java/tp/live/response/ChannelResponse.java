package tp.live.response;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 直播频道信息
 */
@DataObject(generateConverter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChannelResponse {
    public static final String pic1_746_419 = "pic1_746_419";
    public static final String pic2_960_540 = "pic2_960_540";
    public static final String pic5_1920_1080 = "pic5_1920_1080";
    public static final String COPYRIGHT_YES = "1"; // 乐视有版权
    public static final String COPYRIGHT_NO = "0"; // 乐视无版权
    private String channelId;// 频道ID
    private String relaId; // 第三方cp的真实频道id
    private String src_id; // 第三发cp来源
    private String numericKeys;// 频道数字键
    private String channelEname;// 频道英文名称
    private String channelName;// 乐视频道中文名称
    private String cibnChannelName;// 国广频道中文名称，TV端产品应该显示这个名称
    private String beginTime;// 频道启用开始时间，如："2012-09-01 20:24:37"此字段可以用作频道的上线时间，前端根据此字段来判断频道是否是新频道。
    private String endTime;// 频道启用结束时间，为空表示频道一直启用
    private String channelClass;// 频道主题分类
                                // http://st.live.letv.com/live/code/00020.json
    private String satelliteTvType;// 卫视频道分类，参考词典《卫视分类编码》
                                   // http://api.live.letv.com/v1/dictionary/00037
    private String subLiveType;// 二级分类
    private String subLiveTypeName;// 二级分类名称
    private String belongBrand;// 频道所属品牌，参考《频道所属品牌信息获取接口》
                               // http://st.live.letv.com/live/service/allBrands.json
    private Map<String, String> defaultLogo;// 频道默认LOGO
    private String demandId;// 需求方，参考词典《频道需求方编码》
                            // http://st.live.letv.com/live/code/00001.json
    private String signal;// 信号源，参考词典《信号源编码》http://st.live.letv.com/live/code/00002.json
    private String is3D;// 是否为3D频道（1是 0否）
    private String is4K;// 是否为4K频道（0 否 1 4K 2 伪4K）
    private String ch;// 渠道号
    private String orderNo;// 序号，客户端排序用
    private String isRecommend;// 是否推荐 （1 是 0 否）
    private String pcWatermarkUrl;// PC端台标地址
    private String watermarkUrl;// TV端台标地址
    private String cibnWatermarkUrl;// 国广TV端台标地址
    private String childLock;// 是否启用童锁 （1 是 0 否）
    private String copyright;// 频道版权（1 PC， 2 TV，4 手机 ）如 ： 1,2,4
    private String channelDesc;// 频道简介
//    private List<ChannelStreamResponse> streams;// 频道流信息
//    private List<ChannelProgramDetail> programs; // 接推荐数据带过来的节目单
    private String postH3;// 频道LOGO竖图 120*90
    private String postOrigin;// 频道LOGO原图
    private String postS1;// 频道LOGO横图150*200
    private String postS2;// 频道LOGO横图120*160
    private String postS3;// 频道LOGO横图96*128
    private String postS4;// 频道LOGO横图150*150
    private String postS5;// 频道LOGO横图30*30
    private String isCopyRight; // 乐视是否有版权 0:无版权 1:有版权
    private String programSource; // 版权方来源
    private String splatid; // 频道上线各端的标识
    private String chatRoomNum;// 聊天室号
    private String isChat;// 是否为聊天室。1：是，其他：不是
    private String sourceId; // 节目来源 2：卫视 7：轮播 9：咪咕
    private String streamUrl; // 第三方cp的调度地址
    private String isDanmaku;// 是否开启弹幕 0：关闭 1：开启
    private String isPicCollections; // 频道图是否与播放信息保持一致 0：否 1：是
    private String isCollect; // 是否被收藏(推荐部门给出的属性) 1:是
    private String belongArea;// 所属地域 100:大陆，101:香港，102:美国，103:印度
    private String buyFlag; // 是否开启边看边买
    private String partId; // 边看边买的版块id
    private String isPay;// 是否付费
    private String drmFlag;// drm标志

    
    public ChannelResponse() {
        super();
    }

    public ChannelResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        ChannelResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ChannelResponseConverter.toJson(this, json);
        return json;
    }
    
    public String getDrmFlag() {
        return drmFlag;
    }

    public void setDrmFlag(String drmFlag) {
        this.drmFlag = drmFlag;
    }

    public String getIsPay() {
        return isPay;
    }

    public void setIsPay(String isPay) {
        this.isPay = isPay;
    }

    public String getBelongArea() {
        return belongArea;
    }

    public void setBelongArea(String belongArea) {
        this.belongArea = belongArea;
    }

    public String getBuyFlag() {
        return buyFlag;
    }

    public void setBuyFlag(String buyFlag) {
        this.buyFlag = buyFlag;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }

    public String getIsPicCollections() {
        return isPicCollections;
    }

    public void setIsPicCollections(String isPicCollections) {
        this.isPicCollections = isPicCollections;
    }

    public String getIsDanmaku() {
        return isDanmaku;
    }

    public void setIsDanmaku(String isDanmaku) {
        this.isDanmaku = isDanmaku;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getChatRoomNum() {
        return chatRoomNum;
    }

    public void setChatRoomNum(String chatRoomNum) {
        this.chatRoomNum = chatRoomNum;
    }

    public String getIsChat() {
        return isChat;
    }

    public void setIsChat(String isChat) {
        this.isChat = isChat;
    }

    public String getSplatid() {
        return splatid;
    }

    public void setSplatid(String splatid) {
        this.splatid = splatid;
    }

    public String getIsCopyRight() {
        return isCopyRight;
    }

    public void setIsCopyRight(String isCopyRight) {
        this.isCopyRight = isCopyRight;
    }

    public String getProgramSource() {
        return programSource;
    }

    public void setProgramSource(String programSource) {
        this.programSource = programSource;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getRelaId() {
        return relaId;
    }

    public void setRelaId(String relaId) {
        this.relaId = relaId;
    }

    public String getSrc_id() {
        return src_id;
    }

    public void setSrc_id(String src_id) {
        this.src_id = src_id;
    }

    public String getNumericKeys() {
        return numericKeys;
    }

    public void setNumericKeys(String numericKeys) {
        this.numericKeys = numericKeys;
    }

    public String getChannelEname() {
        return channelEname;
    }

    public void setChannelEname(String channelEname) {
        this.channelEname = channelEname;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getCibnChannelName() {
        return cibnChannelName;
    }

    public void setCibnChannelName(String cibnChannelName) {
        this.cibnChannelName = cibnChannelName;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getChannelClass() {
        return channelClass;
    }

    public void setChannelClass(String channelClass) {
        this.channelClass = channelClass;
    }

    public String getSatelliteTvType() {
        return satelliteTvType;
    }

    public void setSatelliteTvType(String satelliteTvType) {
        this.satelliteTvType = satelliteTvType;
    }

    public String getBelongBrand() {
        return belongBrand;
    }

    public void setBelongBrand(String belongBrand) {
        this.belongBrand = belongBrand;
    }

    public Map<String, String> getDefaultLogo() {
        return defaultLogo;
    }

    public void setDefaultLogo(Map<String, String> defaultLogo) {
        this.defaultLogo = defaultLogo;
    }

    public String getDemandId() {
        return demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
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

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getPcWatermarkUrl() {
        return pcWatermarkUrl;
    }

    public void setPcWatermarkUrl(String pcWatermarkUrl) {
        this.pcWatermarkUrl = pcWatermarkUrl;
    }

    public String getWatermarkUrl() {
        return watermarkUrl;
    }

    public void setWatermarkUrl(String watermarkUrl) {
        this.watermarkUrl = watermarkUrl;
    }

    public String getCibnWatermarkUrl() {
        return cibnWatermarkUrl;
    }

    public void setCibnWatermarkUrl(String cibnWatermarkUrl) {
        this.cibnWatermarkUrl = cibnWatermarkUrl;
    }

    public String getChildLock() {
        return childLock;
    }

    public void setChildLock(String childLock) {
        this.childLock = childLock;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getChannelDesc() {
        return channelDesc;
    }

    public void setChannelDesc(String channelDesc) {
        this.channelDesc = channelDesc;
    }

    public String getPostH3() {
        return postH3;
    }

    public void setPostH3(String postH3) {
        this.postH3 = postH3;
    }

    public String getPostOrigin() {
        return postOrigin;
    }

    public void setPostOrigin(String postOrigin) {
        this.postOrigin = postOrigin;
    }

    public String getPostS1() {
        return postS1;
    }

    public void setPostS1(String postS1) {
        this.postS1 = postS1;
    }

    public String getPostS2() {
        return postS2;
    }

    public void setPostS2(String postS2) {
        this.postS2 = postS2;
    }

    public String getPostS3() {
        return postS3;
    }

    public void setPostS3(String postS3) {
        this.postS3 = postS3;
    }

    public String getPostS4() {
        return postS4;
    }

    public void setPostS4(String postS4) {
        this.postS4 = postS4;
    }

    public String getPostS5() {
        return postS5;
    }

    public void setPostS5(String postS5) {
        this.postS5 = postS5;
    }

    public String getSubLiveType() {
        return subLiveType;
    }

    public void setSubLiveType(String subLiveType) {
        this.subLiveType = subLiveType;
    }

    public String getSubLiveTypeName() {
        return subLiveTypeName;
    }

    public void setSubLiveTypeName(String subLiveTypeName) {
        this.subLiveTypeName = subLiveTypeName;
    }

    @Override
    public String toString() {
        return "ChannelResponse [channelId=" + channelId + ", relaId=" + relaId + ", src_id=" + src_id
                + ", numericKeys=" + numericKeys + ", channelEname=" + channelEname + ", channelName=" + channelName
                + ", cibnChannelName=" + cibnChannelName + ", beginTime=" + beginTime + ", endTime=" + endTime
                + ", channelClass=" + channelClass + ", satelliteTvType=" + satelliteTvType + ", subLiveType="
                + subLiveType + ", subLiveTypeName=" + subLiveTypeName + ", belongBrand=" + belongBrand
                + ", defaultLogo=" + defaultLogo + ", demandId=" + demandId + ", signal=" + signal + ", is3D=" + is3D
                + ", is4K=" + is4K + ", ch=" + ch + ", orderNo=" + orderNo + ", isRecommend=" + isRecommend
                + ", pcWatermarkUrl=" + pcWatermarkUrl + ", watermarkUrl=" + watermarkUrl + ", cibnWatermarkUrl="
                + cibnWatermarkUrl + ", childLock=" + childLock + ", copyright=" + copyright + ", channelDesc="
                + channelDesc + ", postH3=" + postH3
                + ", postOrigin=" + postOrigin + ", postS1=" + postS1 + ", postS2=" + postS2 + ", postS3=" + postS3
                + ", postS4=" + postS4 + ", postS5=" + postS5 + ", isCopyRight=" + isCopyRight + ", programSource="
                + programSource + ", splatid=" + splatid + ", chatRoomNum=" + chatRoomNum + ", isChat=" + isChat
                + ", sourceId=" + sourceId + ", streamUrl=" + streamUrl + ", isDanmaku=" + isDanmaku
                + ", isPicCollections=" + isPicCollections + ", isCollect=" + isCollect + ", belongArea=" + belongArea
                + ", buyFlag=" + buyFlag + ", partId=" + partId + ", isPay=" + isPay + ", drmFlag=" + drmFlag + "]";
    }
}