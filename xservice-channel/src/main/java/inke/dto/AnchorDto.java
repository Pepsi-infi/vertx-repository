package inke.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Created by wanglonghu on 17/6/9.
 */
@DataObject(generateConverter = true)
@JsonInclude(Include.NON_NULL)
public class AnchorDto {

    private String id;

    private String portrait;

    private Long onlineNum;

    private String nick;

    private String city;

    private String liveId;

    private Integer source;

    public AnchorDto() {
    }

    public AnchorDto(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        AnchorDtoConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        AnchorDtoConverter.toJson(this, json);
        return json;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Long getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(Long onlineNum) {
        this.onlineNum = onlineNum;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }
}
