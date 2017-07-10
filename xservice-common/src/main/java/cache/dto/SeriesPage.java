package cache.dto;

import java.util.List;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
@DataObject(generateConverter = true)
public class SeriesPage {

    private Integer page;

    private Integer pageSize;

    private List<Video> positiveSeries;
    
    public SeriesPage(){}
    
    public SeriesPage(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        SeriesPageConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SeriesPageConverter.toJson(this, json);
        return json;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<Video> getPositiveSeries() {
        return positiveSeries;
    }

    public void setPositiveSeries(List<Video> positiveSeries) {
        this.positiveSeries = positiveSeries;
    }


    @Override
    public String toString() {
        return "SeriesPage [page=" + page + ", pageSize=" + pageSize
                + ", positiveSeries=" + positiveSeries + "]";
    }
    
}
