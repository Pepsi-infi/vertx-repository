package cms.response;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;


@DataObject(generateConverter = true)
public class CmsBlockContent {

    private String androidUrl;
    private String iosUrl;
    private String bid; // 版块ID
    private String cityLevel;
    private String cityWhiteList;
    private String content; // CMS 版块内容关键配置字段
    private String ctime; // 创建时间
    private String mtime;
    private String startTime;
    private String endTime;
    private ExtendJson extendJson;
    private String id; // CMS id
    private String mobilePic;
    private String padPic;
    private String pic1;
    private String pic2;
    private Map<String, String> playPlatform; // 播放平台 例如 420003_1: Android
    private String position;
    private String priority;
    private String pushflag; // 推送平台
    private String remark;
    private String shorDesc;
    private String showTag;
    private String skipPage;
    private List<ShowTagList> showTagList;
    private String skipType;
    private String skipUrl;
    private String title;
    private String subTitle;
    private String tag;
    private String tagUrl;
    private String tvPic;
    private String tvUrl;
    private String type;
    private String url;
    private Map<String, String> picList;
    private String versionPlatform;// 领先版过滤

    /** 服务端自己添加的，传数据用，有用 begin */
    private String playcount;// 排行用的数据
    private Integer source;// 数据来源，3,排行
    private String area;// 专辑或者视频所属地区

    /** 服务端自己添加的，传数据用，有用，end */


    public CmsBlockContent() {
    }


    public CmsBlockContent(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        CmsBlockContentConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        CmsBlockContentConverter.toJson(this, json);
        return json;
    }


    public String getVersionPlatform() {
        return versionPlatform;
    }

    public void setVersionPlatform(String versionPlatform) {
        this.versionPlatform = versionPlatform;
    }

    public Map<String, String> getPicList() {
        return picList;
    }

    public void setPicList(Map<String, String> picList) {
        this.picList = picList;
    }

    public String getAndroidUrl() {
        return androidUrl;
    }

    public void setAndroidUrl(String androidUrl) {
        this.androidUrl = androidUrl;
    }

    public String getIosUrl() {
        return iosUrl;
    }

    public void setIosUrl(String iosUrl) {
        this.iosUrl = iosUrl;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getCityLevel() {
        return cityLevel;
    }

    public void setCityLevel(String cityLevel) {
        this.cityLevel = cityLevel;
    }

    public String getCityWhiteList() {
        return cityWhiteList;
    }

    public void setCityWhiteList(String cityWhiteList) {
        this.cityWhiteList = cityWhiteList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public ExtendJson getExtendJson() {
        return extendJson;
    }

    public void setExtendJson(ExtendJson extendJson) {
        this.extendJson = extendJson;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobilePic() {
        return mobilePic;
    }

    public void setMobilePic(String mobilePic) {
        this.mobilePic = mobilePic;
    }

    public String getPadPic() {
        return padPic;
    }

    public void setPadPic(String padPic) {
        this.padPic = padPic;
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

    public Map<String, String> getPlayPlatform() {
        return playPlatform;
    }

    public void setPlayPlatform(Map<String, String> playPlatform) {
        this.playPlatform = playPlatform;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPushflag() {
        return pushflag;
    }

    public void setPushflag(String pushflag) {
        this.pushflag = pushflag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShorDesc() {
        return shorDesc;
    }

    public void setShorDesc(String shorDesc) {
        this.shorDesc = shorDesc;
    }

    public String getShowTag() {
        return showTag;
    }

    public void setShowTag(String showTag) {
        this.showTag = showTag;
    }

    public String getSkipPage() {
        return skipPage;
    }

    public void setSkipPage(String skipPage) {
        this.skipPage = skipPage;
    }

    public String getSkipType() {
        return skipType;
    }

    public void setSkipType(String skipType) {
        this.skipType = skipType;
    }

    public String getSkipUrl() {
        return skipUrl;
    }

    public void setSkipUrl(String skipUrl) {
        this.skipUrl = skipUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTagUrl() {
        return tagUrl;
    }

    public void setTagUrl(String tagUrl) {
        this.tagUrl = tagUrl;
    }

    public String getTvPic() {
        return tvPic;
    }

    public void setTvPic(String tvPic) {
        this.tvPic = tvPic;
    }

    public String getTvUrl() {
        return tvUrl;
    }

    public void setTvUrl(String tvUrl) {
        this.tvUrl = tvUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ShowTagList> getShowTagList() {
        return showTagList;
    }

    public void setShowTagList(List<ShowTagList> showTagList) {
        this.showTagList = showTagList;
    }

    public String getPlaycount() {
        return playcount;
    }

    public void setPlaycount(String playcount) {
        this.playcount = playcount;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

//    public static class ExtendJson {
//        private String extendCid;
//        private String extendPage;
//        private String extendPid;
//        private String extendRange;
//        private Map<String, String> extendPicAll;
//
//        public String getExtendCid() {
//            return extendCid;
//        }
//
//        public void setExtendCid(String extendCid) {
//            this.extendCid = extendCid;
//        }
//
//        public String getExtendPage() {
//            return extendPage;
//        }
//
//        public void setExtendPage(String extendPage) {
//            this.extendPage = extendPage;
//        }
//
//        public String getExtendPid() {
//            return extendPid;
//        }
//
//        public void setExtendPid(String extendPid) {
//            this.extendPid = extendPid;
//        }
//
//        public String getExtendRange() {
//            return extendRange;
//        }
//
//        public void setExtendRange(String extendRange) {
//            this.extendRange = extendRange;
//        }
//
//        public Map<String, String> getExtendPicAll() {
//            return extendPicAll;
//        }
//
//        public void setExtendPicAll(Map<String, String> extendPicAll) {
//            this.extendPicAll = extendPicAll;
//        }
//
//    }

//    public static class ShowTagList {
//        private String typeId;
//        private String id;
//        private String value;
//
//        public String getTypeId() {
//            return typeId;
//        }
//
//        public void setTypeId(String typeId) {
//            this.typeId = typeId;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getValue() {
//            return value;
//        }
//
//        public void setValue(String value) {
//            this.value = value;
//        }
//
//    }

    @Override
    public String toString() {
        return "CmsBlockContent [androidUrl=" + androidUrl + ", iosUrl=" + iosUrl + ", bid=" + bid + ", cityLevel="
                + cityLevel + ", cityWhiteList=" + cityWhiteList + ", content=" + content + ", ctime=" + ctime
                + ", mtime=" + mtime + ", startTime=" + startTime + ", endTime=" + endTime + ", extendJson="
                + extendJson + ", id=" + id + ", mobilePic=" + mobilePic + ", padPic=" + padPic + ", pic1=" + pic1
                + ", pic2=" + pic2 + ", playPlatform=" + playPlatform + ", position=" + position + ", priority="
                + priority + ", pushflag=" + pushflag + ", remark=" + remark + ", shorDesc=" + shorDesc + ", showTag="
                + showTag + ", skipPage=" + skipPage + ", showTagList=" + showTagList + ", skipType=" + skipType
                + ", skipUrl=" + skipUrl + ", title=" + title + ", subTitle=" + subTitle + ", tag=" + tag + ", tagUrl="
                + tagUrl + ", tvPic=" + tvPic + ", tvUrl=" + tvUrl + ", type=" + type + ", url=" + url + "]";
    }

}
