package cache.dto;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true,inheritConverter = true)
public class ActorCache extends BaseCache {

    @Protobuf(fieldType = FieldType.INT32,order=1)
    private Integer id;// 人物id

    @Protobuf(fieldType = FieldType.STRING,order=2)
    private String name;// 人物姓名

    @Protobuf(fieldType = FieldType.INT32,order=3)
    private Integer role;// 人物身份 1.导演 2.主演 3.嘉宾 4.主持人

    @Protobuf(fieldType = FieldType.STRING,order=4)
    private String img;// 头像


    @Protobuf(fieldType = FieldType.INT32,order=5)
    private Integer type;

    public ActorCache() {
    }

    public ActorCache(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        ActorCacheConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ActorCacheConverter.toJson(this, json);
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

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

        ActorCache a = (ActorCache) obj;
        return id != null && a.getId() != null && id.intValue() == a.getId().intValue();
    }
}
