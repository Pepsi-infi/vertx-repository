package cache.dto;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true,inheritConverter = true)
public class HotWordsCache extends BaseCache {

    @Protobuf(fieldType = FieldType.INT64, order = 1)
    private Long id;// 乐词id

    @Protobuf(fieldType = FieldType.STRING, order = 2)
    private String name;// 乐词名称

    @Protobuf(fieldType = FieldType.STRING, order = 3)
    private String img;// 乐词图片

    @Protobuf(fieldType = FieldType.STRING, order = 4)
    private String attention;// 关注数

    @Protobuf(fieldType = FieldType.INT32, order = 5)
    private Integer type;

    @Protobuf(fieldType = FieldType.STRING, order = 6)
    private String categoryType;// 分类类型

    public HotWordsCache() {
    }

    public HotWordsCache(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        HotWordsCacheConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        HotWordsCacheConverter.toJson(this, json);
        return json;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    @Override
    public int hashCode() {
        return (id == null) ? 0 : id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        HotWordsCache h = (HotWordsCache) obj;
        return id != null && h.getId() != null && id.intValue() == h.getId().intValue();
    }
}
