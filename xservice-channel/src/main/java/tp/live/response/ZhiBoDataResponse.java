package tp.live.response;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cache.utils.TimeUtil;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ZhiBoDataResponse{

    private String id;
    private String title;// 标题
    private Integer status;// 状态
    private String description;// 描述
    private String viewPic;// 预览图片
    private String beginTime;// 播放时间
    private String endTime;// 结束时间
    private String vid; // 视频iD
    private Long pid;// 专辑id（体育的专题id用这个字段）
    private String preVID;// 推荐视频id（体育的推荐视频id用这个字段）
    private String chatRoomNum;// 聊天室RoomId
    private String isChat;// 是否启用聊天室是否启用聊天（1 是 0 否）
    private String isDanmaku;// 是否开启弹幕 0：关闭 1：开启
    private String recordingId;// 录播id
    private String ch;
    private String season;// 赛季
    private String level1;// 1级赛事
    private String level2;// 2级赛事
    private String level1Id;// 1级分类id
    private String level2Id;// 2级分类id
    private String match;// 比赛轮 1/8决赛
    private String home;// 主场队
    private String guest;// 客场队
    private String homescore;// 主场得分
    private String guestscore;// 客场得分
    private String homeImgUrl;// 主场头像
    private String guestImgUrl;// 客场头像
    private String isPay;// 是否付费（1是 0否）
    private String payPlatForm;// 付费端
    private String price;// 价格
    private String vipPrice;// vip价格
    private String originPrice;
    /**
     * 付费类型
     * 1: 包年以上会员免费
     * 2:包年以上或单点免费
     * 3:会员免费
     * 4:会员或单点免费
     * 5:单点
     * 6:全屏包年以上或单点免费
     */
    private String payType;
    private String selectId;// 直播流id
    private String cibnSelectId;// 直播流id
    private String liveType;// 直播类型：music，soprts
    private Fpic focusPic;// 焦点图
    private Integer isVS;// 是否pk
    private Integer weight;// 权重
    private Integer type;// 娱乐音乐类型：演唱会脱口秀之类对应的code
    private String typeName;// 娱乐音乐类型：演唱会脱口秀之类对应的code的name
    private String screenings;// 场次
    private String musicV2Screenings;// 音乐直播场次Id
    private String commentaryLanguage;// 解说语言
    private List<SuperLiveTagTpResponse> leWord; // 乐词基本信息
    private String splatid; // 频道上线的各端标识
    private String isDolby; // 是否是杜比直播
    private String isPanoramicView; // 是否是全景直播
    private String eventId; // 直播比赛的事件id，针对此字段可以关联多视角
    private String connectionLiveIds;// 关联的直播id(多视角、多语言)
    private String branchType;

    private String buyFlag; // 边看边买开关
    private String partId; // 边看边买商品版块儿信息

    private String belongArea;// 地域

    private String drmFlag;// drm 标志
    private Map<String, String> cids;// drm cids

    public ZhiBoDataResponse() {
        super();
    }

    public ZhiBoDataResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        ZhiBoDataResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ZhiBoDataResponseConverter.toJson(this, json);
        return json;
    }

    public String getDrmFlag() {
        return drmFlag;
    }

    public void setDrmFlag(String drmFlag) {
        this.drmFlag = drmFlag;
    }

    public Map<String, String> getCids() {
        return cids;
    }

    public void setCids(Map<String, String> cids) {
        this.cids = cids;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(String vipPrice) {
        this.vipPrice = vipPrice;
    }

    public String getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(String originPrice) {
        this.originPrice = originPrice;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getConnectionLiveIds() {
        return connectionLiveIds;
    }

    public void setConnectionLiveIds(String connectionLiveIds) {
        this.connectionLiveIds = connectionLiveIds;
    }

    public String getBelongArea() {
        return belongArea;
    }

    public void setBelongArea(String belongArea) {
        this.belongArea = belongArea;
    }

    public String getPayPlatForm() {
        return payPlatForm;
    }

    public void setPayPlatForm(String payPlatForm) {
        this.payPlatForm = payPlatForm;
    }

    public String getIsPanoramicView() {
        return isPanoramicView;
    }

    public void setIsPanoramicView(String isPanoramicView) {
        this.isPanoramicView = isPanoramicView;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public String getBuyFlag() {
        return buyFlag;
    }

    public void setBuyFlag(String buyFlag) {
        this.buyFlag = buyFlag;
    }

    public String getBranchType() {
        return branchType;
    }

    public void setBranchType(String branchType) {
        this.branchType = branchType;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getIsDolby() {
        return isDolby;
    }

    public void setIsDolby(String isDolby) {
        this.isDolby = isDolby;
    }

    public String getSplatid() {
        return splatid;
    }

    public void setSplatid(String splatid) {
        this.splatid = splatid;
    }

    public String getIsChat() {
        return isChat;
    }

    public void setIsChat(String isChat) {
        this.isChat = isChat;
    }

    public String getIsDanmaku() {
        return isDanmaku;
    }

    public void setIsDanmaku(String isDanmaku) {
        this.isDanmaku = isDanmaku;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevel1Id() {
        return level1Id;
    }

    public void setLevel1Id(String level1Id) {
        this.level1Id = level1Id;
    }

    public String getLevel2Id() {
        return level2Id;
    }

    public void setLevel2Id(String level2Id) {
        this.level2Id = level2Id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getViewPic() {
        return viewPic;
    }

    public void setViewPic(String viewPic) {
        this.viewPic = viewPic;
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

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getPreVID() {
        return preVID;
    }

    public void setPreVID(String preVID) {
        this.preVID = preVID;
    }

    public String getChatRoomNum() {
        return chatRoomNum;
    }

    public void setChatRoomNum(String chatRoomNum) {
        this.chatRoomNum = chatRoomNum;
    }

    public String getRecordingId() {
        return recordingId;
    }

    public void setRecordingId(String recordingId) {
        this.recordingId = recordingId;
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getLevel1() {
        return level1;
    }

    public void setLevel1(String level1) {
        this.level1 = level1;
    }

    public String getLevel2() {
        return level2;
    }

    public void setLevel2(String level2) {
        this.level2 = level2;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getHomescore() {
        return homescore;
    }

    public void setHomescore(String homescore) {
        this.homescore = homescore;
    }

    public String getGuestscore() {
        return guestscore;
    }

    public void setGuestscore(String guestscore) {
        this.guestscore = guestscore;
    }

    public String getHomeImgUrl() {
        return homeImgUrl;
    }

    public void setHomeImgUrl(String homeImgUrl) {
        this.homeImgUrl = homeImgUrl;
    }

    public String getGuestImgUrl() {
        return guestImgUrl;
    }

    public void setGuestImgUrl(String guestImgUrl) {
        this.guestImgUrl = guestImgUrl;
    }

    public String getSelectId() {
        return selectId;
    }

    public String getIsPay() {
        return isPay;
    }

    public void setIsPay(String isPay) {
        this.isPay = isPay;
    }

    public void setSelectId(String selectId) {
        this.selectId = selectId;
    }

    public String getCibnSelectId() {
        return cibnSelectId;
    }

    public void setCibnSelectId(String cibnSelectId) {
        this.cibnSelectId = cibnSelectId;
    }

    public String getLiveType() {
        return liveType;
    }

    public void setLiveType(String liveType) {
        this.liveType = liveType;
    }

    public Fpic getFocusPic() {
        return focusPic;
    }

    public void setFocusPic(Fpic focusPic) {
        this.focusPic = focusPic;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getScreenings() {
        return screenings;
    }

    public void setScreenings(String screenings) {
        this.screenings = screenings;
    }

    public String getMusicV2Screenings() {
        return musicV2Screenings;
    }

    public void setMusicV2Screenings(String musicV2Screenings) {
        this.musicV2Screenings = musicV2Screenings;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<SuperLiveTagTpResponse> getLeWord() {
        return leWord;
    }

    public void setLeWord(List<SuperLiveTagTpResponse> leWord) {
        this.leWord = leWord;
    }

    public Integer getIsVS() {
        return isVS;
    }

    public void setIsVS(Integer isVS) {
        this.isVS = isVS;
    }

    public String getCommentaryLanguage() {
        return commentaryLanguage;
    }

    public void setCommentaryLanguage(String commentaryLanguage) {
        this.commentaryLanguage = commentaryLanguage;
    }

    public int compareTo(ZhiBoDataResponse l) {

        // 状态不同的，按状态排序
        if (this.status > l.getStatus()) {
            return -1;
        } else if (this.status < l.getStatus()) {
            return 1;
        }

        // 预告或者直播中的节目，按权重排序，权重相同的按开始时间排序
        if (System.currentTimeMillis() < TimeUtil.string2timestamp(this.endTime)
                && System.currentTimeMillis() < TimeUtil.string2timestamp(l.getEndTime())) {
            // 状态相同的，看权重值，权重高的放前面
            if (this.weight > l.getWeight()) {
                return -1;
            } else if (this.weight < l.getWeight()) {
                return 1;
            }
            // 权重相同的按开始时间排序
            if (TimeUtil.string2timestamp(this.beginTime) > TimeUtil.string2timestamp(l.getBeginTime())) {
                return 1;
            } else if (TimeUtil.string2timestamp(this.beginTime) < TimeUtil.string2timestamp(l.getBeginTime())) {
                return -1;
            }
        }

        // 回看的，或者开始时间相同的都按照结束时间排序
        if (TimeUtil.string2timestamp(this.endTime) > TimeUtil.string2timestamp(l.getEndTime())) {
            return 1;
        } else if (TimeUtil.string2timestamp(this.endTime) < TimeUtil.string2timestamp(l.getEndTime())) {
            return -1;
        }

        // 以上都规则都无法区分的数据，向后追加
        return 1;
    }
}