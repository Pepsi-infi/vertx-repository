package cache.dto;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
@DataObject(generateConverter = true,inheritConverter = true)
public class SeriesCache extends BaseCache {
    private static final long serialVersionUID = 6615516541098123217L;

    public static String COLLECTION_NAME = "seriesCache";
    private String Key; // 主键

    @Protobuf(fieldType = FieldType.INT64, order = 1)
    private List<Long> vidList;

    public SeriesCache() {
    }

    public SeriesCache(JsonObject json) {
        if(json != null){
            if(json.getValue("_id") != null && StringUtils.isNotBlank((String)json.getValue("_id"))){
                json.put("key",(String)json.getValue("_id"));
            }
            SeriesCacheConverter.fromJson(json, this);
        }
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SeriesCacheConverter.toJson(this, json);
        return json;
    }

//    public JsonObject toDocumentJson() {
//        JsonObject json = new JsonObject();
//        SeriesCacheConverter.toJson(this, json);
//        json.put("_id",this.key);
//        return json;
//    }
    
    public List<Long> getVidList() {
        return vidList;
    }

    public void setVidList(List<Long> vidList) {
        this.vidList = vidList;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

}
