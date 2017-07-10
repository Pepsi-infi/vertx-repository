package tp.live.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProgramWaterMarkData {
    private List<WaterMarkDeliveryTpResponse> items;
    private Integer page;
    private Integer pageSize;
    private Integer total;

    public ProgramWaterMarkData(){}
    public ProgramWaterMarkData(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        ProgramWaterMarkDataConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ProgramWaterMarkDataConverter.toJson(this, json);
        return json;
    }
    
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<WaterMarkDeliveryTpResponse> getItems() {
        return items;
    }

    public void setItems(List<WaterMarkDeliveryTpResponse> items) {
        this.items = items;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
