package inke.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Created by wanglonghu on 17/6/12.
 */
@DataObject(generateConverter = true)
@JsonInclude(Include.NON_NULL)
public class BannerDto {

    private Integer jump;

    private String url;

    private String img;

    private String roomId;

    public BannerDto() {
    }

    public BannerDto(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        BannerDtoConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        BannerDtoConverter.toJson(this, json);
        return json;
    }

    public Integer getJump() {
        return jump;
    }

    public void setJump(Integer jump) {
        this.jump = jump;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
