package service.dto.search;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * Created by zhushenghao1 on 17/1/13.
 */
@DataObject(generateConverter = true)
public class FilterResultDto {
    private Integer total;
    private Integer curPage;
    private Integer nextPage;
    private List<AlbumInfoDto> result;

    public FilterResultDto(){}
    public FilterResultDto(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        FilterResultDtoConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        FilterResultDtoConverter.toJson(this, json);
        return json;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }

    public List<AlbumInfoDto> getResult() {
        return result;
    }

    public void setResult(List<AlbumInfoDto> result) {
        this.result = result;
    }

}
