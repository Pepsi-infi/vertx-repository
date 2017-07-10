package service.dto.theaterpack;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class WaterMarkDeliveryInfo {

    private Integer waterMarkId;
    private Integer playbillId;
    private Long beginTime;
    private Long endTime;
    private Integer programType;
    private String programId;
    private String splatld;
    private Integer type;
    private WaterMarkInfo waterMarkInfo;

    public WaterMarkDeliveryInfo(){}

    public WaterMarkDeliveryInfo(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        WaterMarkDeliveryInfoConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        WaterMarkDeliveryInfoConverter.toJson(this, json);
        return json;
    }
    public Integer getWaterMarkId() {
        return waterMarkId;
    }

    public void setWaterMarkId(Integer waterMarkId) {
        this.waterMarkId = waterMarkId;
    }

    public Integer getPlaybillId() {
        return playbillId;
    }

    public void setPlaybillId(Integer playbillId) {
        this.playbillId = playbillId;
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getProgramType() {
        return programType;
    }

    public void setProgramType(Integer programType) {
        this.programType = programType;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getSplatld() {
        return splatld;
    }

    public void setSplatld(String splatld) {
        this.splatld = splatld;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public WaterMarkInfo getWaterMarkInfo() {
        return waterMarkInfo;
    }

    public void setWaterMarkInfo(WaterMarkInfo waterMarkInfo) {
        this.waterMarkInfo = waterMarkInfo;
    }

}
