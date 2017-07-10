package tp.live.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourceItem {
    private String url;
    private Integer pxWidth;
    private Integer pxHeight;
    private Integer duration;
    private List<Doc> docItemList;
    private String document;

    public ResourceItem() {
    }

    public ResourceItem(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        ResourceItemConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ResourceItemConverter.toJson(this, json);
        return json;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPxWidth() {
        return pxWidth;
    }

    public void setPxWidth(Integer pxWidth) {
        this.pxWidth = pxWidth;
    }

    public Integer getPxHeight() {
        return pxHeight;
    }

    public void setPxHeight(Integer pxHeight) {
        this.pxHeight = pxHeight;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<Doc> getDocItemList() {
        return docItemList;
    }

    public void setDocItemList(List<Doc> docItemList) {
        this.docItemList = docItemList;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

}