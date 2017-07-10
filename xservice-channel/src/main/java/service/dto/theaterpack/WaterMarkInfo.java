package service.dto.theaterpack;

import java.util.List;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
@DataObject(generateConverter = true)
public class WaterMarkInfo {
    private Integer id;

    private String name;

    private Integer type;

    private Integer position;

    private Integer displayType;

    private Integer displayFrequency;

    private Integer circleCount;

    private Integer style;

    private Integer preDuration;

    private Integer status;

    List<MaterialInfo> materialList;

    public WaterMarkInfo(){}

    public WaterMarkInfo(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        WaterMarkInfoConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        WaterMarkInfoConverter.toJson(this, json);
        return json;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getDisplayType() {
        return displayType;
    }

    public void setDisplayType(Integer displayType) {
        this.displayType = displayType;
    }

    public Integer getDisplayFrequency() {
        return displayFrequency;
    }

    public void setDisplayFrequency(Integer displayFrequency) {
        this.displayFrequency = displayFrequency;
    }

    public Integer getCircleCount() {
        return circleCount;
    }

    public void setCircleCount(Integer circleCount) {
        this.circleCount = circleCount;
    }

    public Integer getStyle() {
        return style;
    }

    public void setStyle(Integer style) {
        this.style = style;
    }

    public Integer getPreDuration() {
        return preDuration;
    }

    public void setPreDuration(Integer preDuration) {
        this.preDuration = preDuration;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<MaterialInfo> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<MaterialInfo> materialList) {
        this.materialList = materialList;
    }

}
