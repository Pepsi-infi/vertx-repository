package cache.dto;

import java.util.List;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
@DataObject(generateConverter = true,inheritConverter = true)
public class LinesCache extends BaseCache {

    /**
     * 
     */
    private static final long serialVersionUID = 7197704477345540745L;

    private Long pid;// 专辑id
    private String id;// 台词id (铃声id)
    private String lines; // 台词文本
    private Long roleLeId;// 角色乐词id
    private Long starLeId;// 明星乐词id
    private Long startPlayTime;// 起播时间 偏移秒数
    private Integer type;// 台词类型 (铃声类型)1 音频 2 视频
    private String headImg;// 角色头像
    private String roleName;// 角色名称
    private String ctime;// 创建时间
    private String mtime;// 更新时间
    private Long videovid;// 台词对应的视频id----------video_vid

    private String ringName;// 铃声名称
    private List<Long> leids;// 铃声所属关注词ID（明星/剧集） (没有)
    private Integer ringCount;// 叫醒次数
    private Long mid;// 媒资id -----------vid
    private String episode;// 集数 --------------lines_desc

    public LinesCache() {
    }

    public LinesCache(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        LinesCacheConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        LinesCacheConverter.toJson(this, json);
        return json;
    }
    
    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLines() {
        return lines;
    }

    public void setLines(String lines) {
        this.lines = lines;
    }

    public Long getRoleLeId() {
        return roleLeId;
    }

    public void setRoleLeId(Long roleLeId) {
        this.roleLeId = roleLeId;
    }

    public Long getStarLeId() {
        return starLeId;
    }

    public void setStarLeId(Long starLeId) {
        this.starLeId = starLeId;
    }

    public Long getStartPlayTime() {
        return startPlayTime;
    }

    public void setStartPlayTime(Long startPlayTime) {
        this.startPlayTime = startPlayTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public String getRingName() {
        return ringName;
    }

    public void setRingName(String ringName) {
        this.ringName = ringName;
    }

    public List<Long> getLeids() {
        return leids;
    }

    public void setLeids(List<Long> leids) {
        this.leids = leids;
    }

    public Integer getRingCount() {
        return ringCount;
    }

    public void setRingCount(Integer ringCount) {
        this.ringCount = ringCount;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public Long getVideovid() {
        return videovid;
    }

    public void setVideovid(Long videovid) {
        this.videovid = videovid;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

}
