package vip.response;

import java.util.List;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class PackageInfo {
    private Integer id;
    private Integer duration;
    private Integer weight;
    private Double price;
    private String terminal;
    private Double originalPrice;
    private String field;
    private Integer status;
    private String name;
    private Integer typeGroup;
    private Integer durationType; // 类型 1：自定义，2：按自然年月
    private String durationName; // "包月时长",
    private Integer productId;
    private Integer country;
    private Integer autoRenew; // 是否自动续费：0:否，1：是
    private Integer autoRenewPeriod; // 自动续费期数：0：不自动续费，大于0表示自动续费期数
    private String desc;
    private String pic;
    private String pic2; // 图片地址 100*100
    private String pic3; // 图片地址 800*800
    private Integer subscribeStatus; // 是否订阅当前套餐
    private Long endTime; // 到期时间
    private List<DiscountInfo> vipDiscountPriceInfo;

    public PackageInfo() {
    }

    public PackageInfo(JsonObject json) {
        PackageInfoConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        PackageInfoConverter.toJson(this, json);
        return json;
    }

    public List<DiscountInfo> getVipDiscountPriceInfo() {
        return vipDiscountPriceInfo;
    }

    public Integer getSubscribeStatus() {
        return subscribeStatus;
    }

    public void setSubscribeStatus(Integer subscribeStatus) {
        this.subscribeStatus = subscribeStatus;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public void setVipDiscountPriceInfo(List<DiscountInfo> vipDiscountPriceInfo) {
        this.vipDiscountPriceInfo = vipDiscountPriceInfo;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public Integer getAutoRenew() {
        return autoRenew;
    }

    public void setAutoRenew(Integer autoRenew) {
        this.autoRenew = autoRenew;
    }

    public Integer getAutoRenewPeriod() {
        return autoRenewPeriod;
    }

    public void setAutoRenewPeriod(Integer autoRenewPeriod) {
        this.autoRenewPeriod = autoRenewPeriod;
    }

    @Override
    public String toString() {
        return "PackageInfo [id=" + id + ", duration=" + duration + ", weight=" + weight + ", price=" + price
                + ", terminal=" + terminal + ", originalPrice=" + originalPrice + ", field=" + field + ", status="
                + status + ", name=" + name + ", typeGroup=" + typeGroup + ", durationType=" + durationType
                + ", durationName=" + durationName + ", productId=" + productId + ", country=" + country
                + ", autoRenew=" + autoRenew + ", autoRenewPeriod=" + autoRenewPeriod + ", desc=" + desc + ", pic="
                + pic + "]";
    }

}
