package cms.response;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: xuli
 * Date：16/10/31
 * Time: 11:42
 */
@DataObject(generateConverter = true)
public class ExtendJson {
    private String extendCid;
    private String extendPage;
    private String extendPid;
    private String extendRange;
    private Map<String, String> extendPicAll;

    public ExtendJson() {
    }

    public ExtendJson(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        ExtendJsonConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ExtendJsonConverter.toJson(this, json);
        return json;
    }

    public String getExtendCid() {
        return extendCid;
    }

    public void setExtendCid(String extendCid) {
        this.extendCid = extendCid;
    }

    public String getExtendPage() {
        return extendPage;
    }

    public void setExtendPage(String extendPage) {
        this.extendPage = extendPage;
    }

    public String getExtendPid() {
        return extendPid;
    }

    public void setExtendPid(String extendPid) {
        this.extendPid = extendPid;
    }

    public String getExtendRange() {
        return extendRange;
    }

    public void setExtendRange(String extendRange) {
        this.extendRange = extendRange;
    }

    public Map<String, String> getExtendPicAll() {
        return extendPicAll;
    }

    public void setExtendPicAll(Map<String, String> extendPicAll) {
        this.extendPicAll = extendPicAll;
    }
}
