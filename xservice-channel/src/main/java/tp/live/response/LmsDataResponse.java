package tp.live.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LmsDataResponse{
    private String anchorId;
    private String anchorName;// 主播昵称
    private String anchorSex;// 性别
    private String chatRoomId;// 聊天室Id
    private String ctime;// 创建时间
    private String headPic;// 主播头像
    private String id;
    private String imageScala;// 画面比例
    private String interactionId;
    private String isBarrage;// 弹幕开关
    private String isChatRoom;// 聊天室开关
    private String isLike;// 点赞开关
    private String isProp;// 道具开关
    private String isRward;// 打赏开关
    private String isShare;// 分享开关
    private String liveRoomDesc;
    private String liveRoomName;// 房间名称
    private String liveRoomPic;// 封面图
    private String liveRoomStatus;
    private String liveRoomStreamId;// 流Id
    private String liveRoomType;// 分类 格式：xx/xx/xx
    private String liveRoomWeight;// 权重
    private String mtime;// 更新时间
    private String partnerRoomId; // 第三方直播间id
    private String roomId; // 云计算房间Id
    private String shareDoc;
    private String sid;// 第三方来源 5:斗鱼 6:聚美...
    private String sourceName;// 第三方cp名称
    private String status;// 状态
    private String streamInfo;// 流信息
    private String version;

    
    public LmsDataResponse() {
        super();
    }

    public LmsDataResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        LmsDataResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        LmsDataResponseConverter.toJson(this, json);
        return json;
    }
    
    public String getAnchorId() {
        return anchorId;
    }

    public void setAnchorId(String anchorId) {
        this.anchorId = anchorId;
    }

    public String getAnchorName() {
        return anchorName;
    }

    public void setAnchorName(String anchorName) {
        this.anchorName = anchorName;
    }

    public String getAnchorSex() {
        return anchorSex;
    }

    public void setAnchorSex(String anchorSex) {
        this.anchorSex = anchorSex;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageScala() {
        return imageScala;
    }

    public void setImageScala(String imageScala) {
        this.imageScala = imageScala;
    }

    public String getInteractionId() {
        return interactionId;
    }

    public void setInteractionId(String interactionId) {
        this.interactionId = interactionId;
    }

    public String getIsBarrage() {
        return isBarrage;
    }

    public void setIsBarrage(String isBarrage) {
        this.isBarrage = isBarrage;
    }

    public String getIsChatRoom() {
        return isChatRoom;
    }

    public void setIsChatRoom(String isChatRoom) {
        this.isChatRoom = isChatRoom;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public String getIsProp() {
        return isProp;
    }

    public void setIsProp(String isProp) {
        this.isProp = isProp;
    }

    public String getIsRward() {
        return isRward;
    }

    public void setIsRward(String isRward) {
        this.isRward = isRward;
    }

    public String getIsShare() {
        return isShare;
    }

    public void setIsShare(String isShare) {
        this.isShare = isShare;
    }

    public String getLiveRoomDesc() {
        return liveRoomDesc;
    }

    public void setLiveRoomDesc(String liveRoomDesc) {
        this.liveRoomDesc = liveRoomDesc;
    }

    public String getLiveRoomName() {
        return liveRoomName;
    }

    public void setLiveRoomName(String liveRoomName) {
        this.liveRoomName = liveRoomName;
    }

    public String getLiveRoomPic() {
        return liveRoomPic;
    }

    public void setLiveRoomPic(String liveRoomPic) {
        this.liveRoomPic = liveRoomPic;
    }

    public String getLiveRoomStatus() {
        return liveRoomStatus;
    }

    public void setLiveRoomStatus(String liveRoomStatus) {
        this.liveRoomStatus = liveRoomStatus;
    }

    public String getLiveRoomStreamId() {
        return liveRoomStreamId;
    }

    public void setLiveRoomStreamId(String liveRoomStreamId) {
        this.liveRoomStreamId = liveRoomStreamId;
    }

    public String getLiveRoomType() {
        return liveRoomType;
    }

    public void setLiveRoomType(String liveRoomType) {
        this.liveRoomType = liveRoomType;
    }

    public String getLiveRoomWeight() {
        return liveRoomWeight;
    }

    public void setLiveRoomWeight(String liveRoomWeight) {
        this.liveRoomWeight = liveRoomWeight;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public String getPartnerRoomId() {
        return partnerRoomId;
    }

    public void setPartnerRoomId(String partnerRoomId) {
        this.partnerRoomId = partnerRoomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getShareDoc() {
        return shareDoc;
    }

    public void setShareDoc(String shareDoc) {
        this.shareDoc = shareDoc;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStreamInfo() {
        return streamInfo;
    }

    public void setStreamInfo(String streamInfo) {
        this.streamInfo = streamInfo;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
