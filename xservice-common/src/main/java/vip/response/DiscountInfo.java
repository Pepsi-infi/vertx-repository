package vip.response;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)

public class DiscountInfo{
    private Double price;
    private Integer vipId;
    
    public DiscountInfo() {
    }

    public DiscountInfo(JsonObject json) {
        DiscountInfoConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        DiscountInfoConverter.toJson(this, json);
        return json;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Integer getVipId() {
        return vipId;
    }
    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }
}