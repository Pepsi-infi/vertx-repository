package tp.live.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
@DataObject(generateConverter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SuperLiveTagTpResponse {
    private String id;
    private String name;
    private String lename;
    private String tagIcon;

    public SuperLiveTagTpResponse() {
        super();
    }

    public SuperLiveTagTpResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        SuperLiveTagTpResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SuperLiveTagTpResponseConverter.toJson(this, json);
        return json;
    }
    
    public String getTagIcon() {
        return tagIcon;
    }

    public void setTagIcon(String tagIcon) {
        this.tagIcon = tagIcon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLename() {
        return lename;
    }

    public void setLename(String lename) {
        this.lename = lename;
    }

}
