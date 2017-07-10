package service.dto.page;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Created by zhushenghao1 on 16/12/21.
 */
@DataObject(generateConverter = true)
public class ChannelInfo {
    private String pageid; // 对应频道的cms pageid
    private String addOnId; // 对应频道的cms addOnId
    private String name; // 频道名称
    private String mzcid; // 频道id
    private String type; // 频道跳转类型
    private String pic; // 频道背景大图
    private String pic1; // 频道背景图标
    private String pic2;
    private String url; // 频道需要跳转网页时的URL
    private String dataUrl; // 频道首页数据地址

    private String cmsId;//cms对应的频道id,用于数据上报
    private Integer locked;//条目被锁定，1锁定
    private Integer skipType;//跳转类型

    public ChannelInfo(){}

    public ChannelInfo(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        ChannelInfoConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ChannelInfoConverter.toJson(this, json);
        return json;
    }

    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
    }

    public String getAddOnId() {
        return addOnId;
    }

    public void setAddOnId(String addOnId) {
        this.addOnId = addOnId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMzcid() {
        return mzcid;
    }

    public void setMzcid(String mzcid) {
        this.mzcid = mzcid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getCmsId() {
        return cmsId;
    }

    public void setCmsId(String cmsId) {
        this.cmsId = cmsId;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public Integer getSkipType() {
        return skipType;
    }

    public void setSkipType(Integer skipType) {
        this.skipType = skipType;
    }
}