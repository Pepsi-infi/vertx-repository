package tp.live.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WarterMarkTpResponse {
    private Integer id;

    private String name;

    private Integer type;

    private Integer pos;

    private Integer displayType;

    private Integer displayFrequency;

    private Integer cycleNum;

    private Integer style;

    private Integer preDuration;

    private Integer status;

    private List<ResourceItem> resourceItemList;

    public WarterMarkTpResponse(){}
    public WarterMarkTpResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        WarterMarkTpResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        WarterMarkTpResponseConverter.toJson(this, json);
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

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
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

    public Integer getCycleNum() {
        return cycleNum;
    }

    public void setCycleNum(Integer cycleNum) {
        this.cycleNum = cycleNum;
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

    public List<ResourceItem> getResourceItemList() {
        return resourceItemList;
    }

    public void setResourceItemList(List<ResourceItem> resourceItemList) {
        this.resourceItemList = resourceItemList;
    }

}
