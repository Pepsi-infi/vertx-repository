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
public class AnchorListWapperDto {
    //http://wiki.letv.cn/pages/viewpage.action?pageId=68147143

    private Integer status = 1;

    private List<AnchorPageDto> data;

    public AnchorListWapperDto() {
    }

    public AnchorListWapperDto(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        AnchorListWapperDtoConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        AnchorListWapperDtoConverter.toJson(this, json);
        return json;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<AnchorPageDto> getData() {
        return data;
    }

    public void setData(List<AnchorPageDto> data) {
        this.data = data;
    }
}
