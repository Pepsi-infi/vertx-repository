package vip.response;

import java.util.List;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)

public class PackageInfoResponse {
    private Integer id; // 套餐id
    private Integer duration; // 时常
    private Double price; // 价格
    private String terminal; // 终端
    private Double originalPrice; // 原始价格
    private String field; // 时常单位
    private String name; // 套餐名称
    private Integer typeGroup; // 会员类型
    private Integer durationType; // 类型 1：自定义，2：按自然年月
    private String durationName; // "包月时长",
    private Integer productId; // 会员id
    private String desc; // 套餐描述
    private String pic; // 图片地址
    private Integer subscribeStatus; // 是否订阅当前套餐
    private Long endTime; // 到期时间
    private List<Discount> discountInfos; //折扣信息

    public PackageInfoResponse() {
    }

    public PackageInfoResponse(JsonObject json) {
        PackageInfoResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        PackageInfoResponseConverter.toJson(this, json);
        return json;
    }
    
    public List<Discount> getDiscountInfos() {
        return discountInfos;
    }

    public void setDiscountInfos(List<Discount> discountInfos) {
        this.discountInfos = discountInfos;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTypeGroup() {
        return typeGroup;
    }

    public void setTypeGroup(Integer typeGroup) {
        this.typeGroup = typeGroup;
    }

    public Integer getDurationType() {
        return durationType;
    }

    public void setDurationType(Integer durationType) {
        this.durationType = durationType;
    }

    public String getDurationName() {
        return durationName;
    }

    public void setDurationName(String durationName) {
        this.durationName = durationName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getSubscribeStatus() {
        return subscribeStatus;
    }

    public void setSubscribeStatus(Integer subscribeStatus) {
        this.subscribeStatus = subscribeStatus;
    }
}
