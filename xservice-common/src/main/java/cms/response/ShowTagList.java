package cms.response;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Created by IntelliJ IDEA.
 * User: xuli
 * Date：16/10/31
 * Time: 11:43
 */
@DataObject(generateConverter = true)
public class ShowTagList {
    private String typeId;
    private String id;
    private String value;

    public ShowTagList() {
    }

    public ShowTagList(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        ShowTagListConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ShowTagListConverter.toJson(this, json);
        return json;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
