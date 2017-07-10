package cache.dto;

import java.util.List;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;


@DataObject(generateConverter = true,inheritConverter = true)
public class Stream extends BaseCache {

    @Protobuf(fieldType = FieldType.STRING,order=1)
    private String name;

    @Protobuf(fieldType = FieldType.STRING,order=2)
    private String code;

    @Protobuf(fieldType = FieldType.STRING,order=3)
    private String canPlay;// 能否播放——视频或者专辑使用

    @Protobuf(fieldType = FieldType.STRING,order=4)
    private String canDown;// 能否下载——视频或者专辑使用

    @Protobuf(fieldType = FieldType.STRING,order=5)
    private String kbps;

    @Protobuf(fieldType = FieldType.STRING,order=6)
    private String bandWidth;// 带宽

    @Protobuf(fieldType = FieldType.BOOL,order=7)
    private Boolean isDefault;// 是否默认

    @Protobuf(fieldType = FieldType.INT64,order=8)
    private Long fileSize;

    @Protobuf(fieldType = FieldType.INT32,order=9)
    private Integer ifCharge;// 是否收费 0不收费，1收费

    @Protobuf(fieldType = FieldType.OBJECT,order=10)
    private List<Stream> liveStreams;// 设置——直播码流列表

    @Protobuf(fieldType = FieldType.OBJECT,order=11)
    private List<Stream> playStreams;// 设置——播放码流列表

    public Stream() {
    }

    public Stream(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        StreamConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        StreamConverter.toJson(this, json);
        return json;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKbps() {
        return this.kbps;
    }

    public void setKbps(String kbps) {
        this.kbps = kbps;
    }

    public List<Stream> getLiveStreams() {
        return this.liveStreams;
    }

    public void setLiveStreams(List<Stream> liveStreams) {
        this.liveStreams = liveStreams;
    }

    public List<Stream> getPlayStreams() {
        return this.playStreams;
    }

    public void setPlayStreams(List<Stream> playStreams) {
        this.playStreams = playStreams;
    }

    public String getBandWidth() {
        return this.bandWidth;
    }

    public void setBandWidth(String bandWidth) {
        this.bandWidth = bandWidth;
    }

    public String getCanPlay() {
        return this.canPlay;
    }

    public void setCanPlay(String canPlay) {
        this.canPlay = canPlay;
    }

    public String getCanDown() {
        return this.canDown;
    }

    public void setCanDown(String canDown) {
        this.canDown = canDown;
    }

    public Boolean getIsDefault() {
        return this.isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getIfCharge() {
        return this.ifCharge;
    }

    public void setIfCharge(Integer ifCharge) {
        this.ifCharge = ifCharge;
    }

}
