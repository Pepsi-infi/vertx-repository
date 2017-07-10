package tp.cms.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CmsColumnContentResponse {

    /**
     * ID
     */
    private Long id;
    /**
     * 所属栏目id
     */
    private Integer columnId;
    /**
     * 内容ID
     */
    private String dataId;
    /**
     * 内容名称
     */
    private String dataName;
    /**
     * 是否推荐
     */
    private Integer isRecommend;
    /**
     * 直播类型(0：直播占位；1：轮播； 2：卫视；3：直播； 4：焦点; 5：咪咕 6:第三方直播站位 7:活动)
     */
    private Integer liveType;
    /**
     * TODO 意义未知
     */
    private Integer orderr;
    /**
     * 图片地址
     */
    private String pic;
    /**
     * 活动时的转类型
     */
    private Integer skipType;
    /**
     * 互动是的跳转目标
     */
    private String skipValue;
    /**
     * 角标
     */
    private String corner1;

    /**
     * 跳转H5 打开方式 0自适应 1竖屏 2横屏
     */
    private String outputType;

    public CmsColumnContentResponse() {
        super();
    }

    public CmsColumnContentResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        CmsColumnContentResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        CmsColumnContentResponseConverter.toJson(this, json);
        return json;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getColumnId() {
        return columnId;
    }

    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public Integer getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Integer getLiveType() {
        return liveType;
    }

    public void setLiveType(Integer liveType) {
        this.liveType = liveType;
    }

    public Integer getOrderr() {
        return orderr;
    }

    public void setOrderr(Integer orderr) {
        this.orderr = orderr;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getSkipType() {
        return skipType;
    }

    public void setSkipType(Integer skipType) {
        this.skipType = skipType;
    }

    public String getSkipValue() {
        return skipValue;
    }

    public void setSkipValue(String skipValue) {
        this.skipValue = skipValue;
    }

    public String getCorner1() {
        return corner1;
    }

    public void setCorner1(String corner1) {
        this.corner1 = corner1;
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    @Override
    public String toString() {
        return "CmsColumnContentResponse [id=" + id + ", columnId=" + columnId + ", dataId=" + dataId + ", dataName="
                + dataName + ", isRecommend=" + isRecommend + ", liveType=" + liveType + ", orderr=" + orderr
                + ", pic=" + pic + ", skipType=" + skipType + ", skipValue=" + skipValue + ", corner1=" + corner1
                + ", outputType=" + outputType + "]";
    }
}
