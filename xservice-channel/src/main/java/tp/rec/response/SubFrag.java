package tp.rec.response;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: xuli
 * Date：16/10/19
 * Time: 11:17
 */
@DataObject(generateConverter = true)
public class SubFrag {

    private List<RecData> blockContents;
    private String contentStyle;


    public SubFrag() {
    }

    public SubFrag(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        SubFragConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SubFragConverter.toJson(this, json);
        return json;
    }

    public List<RecData> getBlockContents() {
        return blockContents;
    }

    public void setBlockContents(List<RecData> blockContents) {
        this.blockContents = blockContents;
    }

    public String getContentStyle() {
        return contentStyle;
    }

    public void setContentStyle(String contentStyle) {
        this.contentStyle = contentStyle;
    }
}
