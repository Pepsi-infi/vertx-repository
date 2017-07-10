package service.dto;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import vip.response.VipInfo;

import java.util.List;

/**
 * Created by zhushenghao1 on 17/2/28.
 */
@DataObject(generateConverter = true,inheritConverter = true)
public class UserAddOnsInfo {
    List<VipInfo> listSub;
    List<VipInfo> listNotEnd;
    List<VipInfo> other;

    public UserAddOnsInfo(){}

    public UserAddOnsInfo(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        UserAddOnsInfoConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        UserAddOnsInfoConverter.toJson(this, json);
        return json;
    }

    public List<VipInfo> getListSub() {
        return listSub;
    }

    public UserAddOnsInfo setListSub(List<VipInfo> listSub) {
        this.listSub = listSub;
        return this;
    }

    public List<VipInfo> getListNotEnd() {
        return listNotEnd;
    }

    public UserAddOnsInfo setListNotEnd(List<VipInfo> listNotEnd) {
        this.listNotEnd = listNotEnd;
        return this;
    }

    public List<VipInfo> getOther() {
        return other;
    }

    public UserAddOnsInfo setOther(List<VipInfo> other) {
        this.other = other;
        return this;
    }
}
