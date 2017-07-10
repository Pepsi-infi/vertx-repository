package service.dto.channelPage;

import java.util.List;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Created by zhushenghao1 on 17/1/3.
 */
@DataObject(generateConverter = true,inheritConverter = true)
public class AddOnInfoDto extends BaseDto {
    private Integer productId;//会员id
    private Integer addOnId;//套餐包id
    private String addOnName;
    private String addOnBackPic;//背景图
    private String addOnLogoPic;
    private String addOnPrice;
    private String addOnDesc;
    private Integer addOnSubscribeStatus;// 0未试用未订阅 1已订阅或已试用 2未订阅不在服务期内 3未订阅在服务期内
    private Integer addOnTotalContentNum;// 套餐包下所有内容个数，包括视频和专辑
    private Long endTime;//服务期到期时间
    private List<AddOnPriceInfo> addOnPricesInfo;//addOn存在折扣价格时,所有价格的展示文案;

    public AddOnInfoDto() {
    }

    public AddOnInfoDto(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        AddOnInfoDtoConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        AddOnInfoDtoConverter.toJson(this, json);
        return json;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getAddOnId() {
        return addOnId;
    }

    public void setAddOnId(Integer addOnId) {
        this.addOnId = addOnId;
    }

    public String getAddOnName() {
        return addOnName;
    }

    public void setAddOnName(String addOnName) {
        this.addOnName = addOnName;
    }

    public String getAddOnBackPic() {
        return addOnBackPic;
    }

    public void setAddOnBackPic(String addOnBackPic) {
        this.addOnBackPic = addOnBackPic;
    }

    public String getAddOnLogoPic() {
        return addOnLogoPic;
    }

    public void setAddOnLogoPic(String addOnLogoPic) {
        this.addOnLogoPic = addOnLogoPic;
    }

    public String getAddOnPrice() {
        return addOnPrice;
    }

    public void setAddOnPrice(String addOnPrice) {
        this.addOnPrice = addOnPrice;
    }

    public String getAddOnDesc() {
        return addOnDesc;
    }

    public void setAddOnDesc(String addOnDesc) {
        this.addOnDesc = addOnDesc;
    }

    public Integer getAddOnSubscribeStatus() {
        return addOnSubscribeStatus;
    }

    public void setAddOnSubscribeStatus(Integer addOnSubscribeStatus) {
        this.addOnSubscribeStatus = addOnSubscribeStatus;
    }

    public Integer getAddOnTotalContentNum() {
        return addOnTotalContentNum;
    }

    public void setAddOnTotalContentNum(Integer addOnTotalContentNum) {
        this.addOnTotalContentNum = addOnTotalContentNum;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public List<AddOnPriceInfo> getAddOnPricesInfo() {
        return addOnPricesInfo;
    }

    public void setAddOnPricesInfo(List<AddOnPriceInfo> addOnPricesInfo) {
        this.addOnPricesInfo = addOnPricesInfo;
    }

    @Override
    public String toString() {
        return "AddOnDto [productId=" + productId + ", addOnId=" + addOnId
                + ", addOnName=" + addOnName + ", addOnBackPic=" + addOnBackPic
                + ", addOnLogoPic=" + addOnLogoPic + ", addOnPrice="
                + addOnPrice + ", addOnDesc=" + addOnDesc
                + ", addOnSubscribeStatus=" + addOnSubscribeStatus
                + ", addOnTotalContentNum=" + addOnTotalContentNum
                + ", endTime=" + endTime + "]";
    }

    public static class AddOnPriceInfo {
        private Integer priceType; //会员类型
        private String priceInfo; //折扣价格

        public AddOnPriceInfo(){}

        public Integer getPriceType() {
            return priceType;
        }

        public void setPriceType(Integer priceType) {
            this.priceType = priceType;
        }

        public String getPriceInfo() {
            return priceInfo;
        }

        public void setPriceInfo(String priceInfo) {
            this.priceInfo = priceInfo;
        }
    }
}