package tp.live.response;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@DataObject(generateConverter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProgramTp {
    private Long id;// 节目ID

    private String title;// 节目名称

    private String playTime;// 节目开始播放时间，格式：yyyy-MM-dd HH:mm:ss

    private String endTime;// 节目播放结束时间，格式：yyyy-MM-dd
                           // HH:mm:ss，注意：结束时间并不一定就是实际节目的结束时间，有可能被下一个节目覆盖掉。

    private String duration;// 节目时长(单位秒)

    private Integer isRecorder;// 是否显示录播logo 0 否 1 是

    private String vid;// 视频ID

    private String viewPic;// 节目缩略图地址（120*90）。
    // 如果需要其它分辨率的图片，请将“120_90”替换为需要的分辨率。目前支持如下分辨率：
    // 200_150、290_218、400_300

    private Integer programType;// 节目类型(1 直播 0 点播)

    private TheaterIcoTp theaterIco;// 剧场角标

    private String aid;

    public ProgramTp() {
    }

    public ProgramTp(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        ProgramTpConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ProgramTpConverter.toJson(this, json);
        return json;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getIsRecorder() {
        return isRecorder;
    }

    public void setIsRecorder(Integer isRecorder) {
        this.isRecorder = isRecorder;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getViewPic() {
        return viewPic;
    }

    public void setViewPic(String viewPic) {
        this.viewPic = viewPic;
    }

    public Integer getProgramType() {
        return programType;
    }

    public void setProgramType(Integer programType) {
        this.programType = programType;
    }

    public TheaterIcoTp getTheaterIco() {
        return theaterIco;
    }

    public void setTheaterIco(TheaterIcoTp theaterIco) {
        this.theaterIco = theaterIco;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }
}
