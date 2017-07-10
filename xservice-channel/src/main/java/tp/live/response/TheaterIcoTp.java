package tp.live.response;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@DataObject(generateConverter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TheaterIcoTp {

    private Integer id;

    private String name;// 剧场名称

    private String imgurl;// 剧场角标地址。（从 imgUrl 更名为 imgurl ）

    public TheaterIcoTp() {
    }

    public TheaterIcoTp(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        TheaterIcoTpConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        TheaterIcoTpConverter.toJson(this, json);
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

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
