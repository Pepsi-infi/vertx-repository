package cache.dto;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: xuli
 * Date：16/2/25
 * Time: 15:25
 */
//@Document(collection="pageCache")
@DataObject(generateConverter = true,inheritConverter = true)
public class PageCache extends BaseCache{
    private static final long serialVersionUID = -8490978090233961064L;

    public static String COLLECTION_NAME = "pageCache";

    @Protobuf(fieldType = FieldType.OBJECT,order = 1)
    private List<Page> index;

//    @Id
    private String key; //专辑id

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public PageCache() {
    }

    public PageCache(JsonObject json) {
        if(json != null){
            if(json.getValue("_id") != null && StringUtils.isNotBlank((String)json.getValue("_id"))){
                json.put("key",(String)json.getValue("_id"));
            }
            PageCacheConverter.fromJson(json, this);
        }
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        PageCacheConverter.toJson(this, json);
        return json;
    }

//    public JsonObject toDocumentJson() {
//        JsonObject json = new JsonObject();
//        PageCacheConverter.toJson(this, json);
//        json.put("_id",this.key);
//        return json;
//    }

    public PageCache(List<Page> index) {
        this.index = index;
    }

    public List<Page> getIndex() {
        return index;
    }

    public void setIndex(List<Page> index) {
        this.index = index;
    }
}
