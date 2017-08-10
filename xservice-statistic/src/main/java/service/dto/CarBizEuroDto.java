package service.dto;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Created by lufei
 * Date : 2017/8/9 18:02
 * Description :
 */
@DataObject(generateConverter = true)
public class CarBizEuroDto {
    private Long id;
    //客户Id
    private Long customerId;
    //手机号
    private String phone;
    //token
    private String deviceToken;


    public CarBizEuroDto() {
        super();
    }

    public CarBizEuroDto(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        CarBizEuroDtoConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        CarBizEuroDtoConverter.toJson(this, json);
        return json;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CarBizEuroDto{");
        sb.append("id=").append(id);
        sb.append(", customerId=").append(customerId);
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", deviceToken='").append(deviceToken).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
