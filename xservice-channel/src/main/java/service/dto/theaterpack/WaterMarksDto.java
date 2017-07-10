package service.dto.theaterpack;

import java.util.List;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
@DataObject(generateConverter = true)

public class WaterMarksDto {

    // 节目水印
    private List<WaterMarkDeliveryInfo> programWaterMarkList;

    // 剧场水印
    private List<WaterMarkDeliveryInfo> theaterWaterMarkList;

    
    public WaterMarksDto(){}

    public WaterMarksDto(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        WaterMarksDtoConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        WaterMarksDtoConverter.toJson(this, json);
        return json;
    }
    
    
    public List<WaterMarkDeliveryInfo> getProgramWaterMarkList() {
        return programWaterMarkList;
    }

    public void setProgramWaterMarkList(List<WaterMarkDeliveryInfo> programWaterMarkList) {
        this.programWaterMarkList = programWaterMarkList;
    }

    public List<WaterMarkDeliveryInfo> getTheaterWaterMarkList() {
        return theaterWaterMarkList;
    }

    public void setTheaterWaterMarkList(List<WaterMarkDeliveryInfo> theaterWaterMarkList) {
        this.theaterWaterMarkList = theaterWaterMarkList;
    }

}
