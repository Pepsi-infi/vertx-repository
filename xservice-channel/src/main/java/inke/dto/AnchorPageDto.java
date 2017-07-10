package inke.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import java.util.List;

/**
 * Created by wanglonghu on 17/6/9.
 */
@DataObject(generateConverter = true)
@JsonInclude(Include.NON_NULL)
public class AnchorPageDto {

    private Integer id;

    private String name;

    private Integer page;

    private Integer pageSize;

    private List<BannerDto> banner;

    private List<AnchorDto> content;

    public AnchorPageDto() {
    }

    public AnchorPageDto(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        AnchorPageDtoConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        AnchorPageDtoConverter.toJson(this, json);
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

    public List<AnchorDto> getContent() {
        return content;
    }

    public void setContent(List<AnchorDto> content) {
        this.content = content;
    }

    public List<BannerDto> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerDto> banner) {
        this.banner = banner;
    }
}
