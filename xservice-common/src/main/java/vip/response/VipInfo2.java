package vip.response;
//package vip.response;
//
//import io.vertx.codegen.annotations.DataObject;
//import io.vertx.core.json.JsonObject;
//
//@DataObject(generateConverter = true)
//public class VipInfo {
//    private Integer productId;// 会员id
//    private String name;// 会员名称
//    private Integer typeGroup;// 101:basic包 102：add_on包 103：站外会员包(hulu等)
//    private TrialData trialData;
//
//    
//    public VipInfo() {
//    }
//
//    public VipInfo(JsonObject json) {
//        VipInfoConverter.fromJson(json, this);
//    }
//
//    public JsonObject toJson() {
//        JsonObject json = new JsonObject();
//        VipInfoConverter.toJson(this, json);
//        return json;
//    }
//    public Integer getProductId() {
//        return productId;
//    }
//
//    public void setProductId(Integer productId) {
//        this.productId = productId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Integer getTypeGroup() {
//        return typeGroup;
//    }
//
//    public void setTypeGroup(Integer typeGroup) {
//        this.typeGroup = typeGroup;
//    }
//
//    public TrialData getTrialData() {
//        return trialData;
//    }
//
//    public void setTrialData(TrialData trialData) {
//        this.trialData = trialData;
//    }
//
//    @Override
//    public String toString() {
//        return "VipInfo [productId=" + productId + ", name=" + name + ", typeGroup=" + typeGroup + ", trialData="
//                + trialData + "]";
//    }
//}
