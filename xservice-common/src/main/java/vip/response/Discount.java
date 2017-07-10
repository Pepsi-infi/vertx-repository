package vip.response;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)

public class Discount {
    private Integer vipType; //会员类型
    private Double price; //折扣价格


    public Discount() {
    }

    public Discount(JsonObject json) {
        DiscountConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        DiscountConverter.toJson(this, json);
        return json;
    }
    
    public Integer getVipType() {
        return vipType;
    }

    public void setVipType(Integer vipType) {
        this.vipType = vipType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
