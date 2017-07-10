package tp.live.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
@DataObject(generateConverter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Fpic {

    private String pic1_746_419 = "";
    private String pic2_960_540 = "";
    private String pic5_1920_1080 = "";

    public Fpic() {
    }

    public Fpic(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        FpicConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        FpicConverter.toJson(this, json);
        return json;
    }
    
    public String getPic5_1920_1080() {
        return pic5_1920_1080;
    }

    public void setPic5_1920_1080(String pic5_1920_1080) {
        this.pic5_1920_1080 = pic5_1920_1080;
    }

    public String getPic1_746_419() {
        return pic1_746_419;
    }

    public void setPic1_746_419(String pic1_746_419) {
        this.pic1_746_419 = pic1_746_419;
    }

    public String getPic2_960_540() {
        return pic2_960_540;
    }

    public void setPic2_960_540(String pic2_960_540) {
        this.pic2_960_540 = pic2_960_540;
    }

}