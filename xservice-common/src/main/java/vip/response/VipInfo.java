package vip.response;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class VipInfo {
    private Long endTime; // 1234567890000, // 会员截止日期，时间戳
    private String name; // ":"会员名称", //会员名称
    private Integer productId; // ":1, // 会员id
    private Integer uid; // :222, // 用户id
    private Integer typeGroup; // ": 101, // 101:basic包 102：add_on包//
                               // 103：站外会员包(hulu等)
    private Integer isSubscribe; // ": 2 // 是否订阅 1：未订阅 2：已订阅
    private Long tryEndTime;// 试用截止时间
    private Long createTime;

    public VipInfo() {
    }

    public VipInfo(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        VipInfoConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        VipInfoConverter.toJson(this, json);
        return json;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getTypeGroup() {
        return typeGroup;
    }

    public void setTypeGroup(Integer typeGroup) {
        this.typeGroup = typeGroup;
    }

    public Integer getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(Integer isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public Long getTryEndTime() {
        return tryEndTime;
    }

    public void setTryEndTime(Long tryEndTime) {
        this.tryEndTime = tryEndTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "VipInfo [endTime=" + endTime + ", name=" + name + ", productId=" + productId + ", uid=" + uid
                + ", typeGroup=" + typeGroup + ", isSubscribe=" + isSubscribe + ", tryEndTime=" + tryEndTime + "]";
    }

}
