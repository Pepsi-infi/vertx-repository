package tp.live.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WaterMarkDeliveryTpResponse {
    private Integer id;

    private Integer watermarkId;

    private Integer playbillItemId;

    private Long beginTime;

    private Long endTime;

    private Integer programType;

    private String dataId;

    private String splatId;

    private Integer posx;

    private Integer posy;

    private Integer type;

    private WarterMarkTpResponse wartermark;

    public WaterMarkDeliveryTpResponse(){}
    public WaterMarkDeliveryTpResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        WaterMarkDeliveryTpResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        WaterMarkDeliveryTpResponseConverter.toJson(this, json);
        return json;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWatermarkId() {
        return watermarkId;
    }

    public void setWatermarkId(Integer watermarkId) {
        this.watermarkId = watermarkId;
    }

    public Integer getPlaybillItemId() {
        return playbillItemId;
    }

    public void setPlaybillItemId(Integer playbillItemId) {
        this.playbillItemId = playbillItemId;
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

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getSplatId() {
        return splatId;
    }

    public void setSplatId(String splatId) {
        this.splatId = splatId;
    }

    public Integer getPosx() {
        return posx;
    }

    public void setPosx(Integer posx) {
        this.posx = posx;
    }

    public Integer getPosy() {
        return posy;
    }

    public void setPosy(Integer posy) {
        this.posy = posy;
    }

    public WarterMarkTpResponse getWartermark() {
        return wartermark;
    }

    public void setWartermark(WarterMarkTpResponse wartermark) {
        this.wartermark = wartermark;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
