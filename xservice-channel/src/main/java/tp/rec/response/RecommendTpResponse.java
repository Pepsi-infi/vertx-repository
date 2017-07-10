package tp.rec.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.List;


/**
 * 频道页推荐类
 */
@DataObject(generateConverter = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecommendTpResponse {

    private String area;
    private String blockname;
    private String bucket;
    private Integer cid;
    private String cityLevel;
    private String cityWhiteList;
    private Integer cms_num;
    private String conFieldDetailList;
    private String conFieldTypeList;
    private Integer contentCid;
    private String contentId;
    private Integer contentManulNum;
    private String contentName;
    private String contentSubName;// 内容副标题
    private String contentRate;
    private String contentSort;
    private String contentStyle;
    private Integer contentTotal;
    private String contentType;
    private String contentVideoType;
    private String fragEndTime;
    private String fragId;
    private String fragStartTime;
    private String id;
    private String moduleSort;
    private Integer num;
    private String redFieldDetailList;
    private String redFieldTypeList;
    private String redirectCid;
    private String redirectPageId;
    private String redirectType;
    private String redirectUrl;
    private String redirectVideoType;
    private String reid;
    private String type;
    private String typeid;
    private List<RecData> rec;
    /* 模块副标题的跳转相关* */
    private String redirectSubCid;
    private String redirectSubPageId;
    private String redirectSubType;
    private String redirectSubUrl;
    private String redirectSubVideoType;
    private String redSubFieldTypeList;
    private String redSubFieldDetailList;

    private List<SubFrag> subFrags;//板块的子版块，目前保存副标题信息
    private String contentRid;//cms的推荐或者搜索id

    public RecommendTpResponse() {
    }

    public RecommendTpResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        RecommendTpResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        RecommendTpResponseConverter.toJson(this, json);
        return json;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBlockname() {
        return blockname;
    }

    public void setBlockname(String blockname) {
        this.blockname = blockname;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
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

    public Integer getCms_num() {
        return cms_num;
    }

    public void setCms_num(Integer cms_num) {
        this.cms_num = cms_num;
    }

    public String getConFieldDetailList() {
        return conFieldDetailList;
    }

    public void setConFieldDetailList(String conFieldDetailList) {
        this.conFieldDetailList = conFieldDetailList;
    }

    public String getConFieldTypeList() {
        return conFieldTypeList;
    }

    public void setConFieldTypeList(String conFieldTypeList) {
        this.conFieldTypeList = conFieldTypeList;
    }

    public Integer getContentCid() {
        return contentCid;
    }

    public void setContentCid(Integer contentCid) {
        this.contentCid = contentCid;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public Integer getContentManulNum() {
        return contentManulNum;
    }

    public void setContentManulNum(Integer contentManulNum) {
        this.contentManulNum = contentManulNum;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getContentSubName() {
        return contentSubName;
    }

    public void setContentSubName(String contentSubName) {
        this.contentSubName = contentSubName;
    }

    public String getContentRate() {
        return contentRate;
    }

    public void setContentRate(String contentRate) {
        this.contentRate = contentRate;
    }

    public String getContentSort() {
        return contentSort;
    }

    public void setContentSort(String contentSort) {
        this.contentSort = contentSort;
    }

    public String getContentStyle() {
        return contentStyle;
    }

    public void setContentStyle(String contentStyle) {
        this.contentStyle = contentStyle;
    }

    public Integer getContentTotal() {
        return contentTotal;
    }

    public void setContentTotal(Integer contentTotal) {
        this.contentTotal = contentTotal;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentVideoType() {
        return contentVideoType;
    }

    public void setContentVideoType(String contentVideoType) {
        this.contentVideoType = contentVideoType;
    }

    public String getFragEndTime() {
        return fragEndTime;
    }

    public void setFragEndTime(String fragEndTime) {
        this.fragEndTime = fragEndTime;
    }

    public String getFragId() {
        return fragId;
    }

    public void setFragId(String fragId) {
        this.fragId = fragId;
    }

    public String getFragStartTime() {
        return fragStartTime;
    }

    public void setFragStartTime(String fragStartTime) {
        this.fragStartTime = fragStartTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModuleSort() {
        return moduleSort;
    }

    public void setModuleSort(String moduleSort) {
        this.moduleSort = moduleSort;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getRedFieldDetailList() {
        return redFieldDetailList;
    }

    public void setRedFieldDetailList(String redFieldDetailList) {
        this.redFieldDetailList = redFieldDetailList;
    }

    public String getRedFieldTypeList() {
        return redFieldTypeList;
    }

    public void setRedFieldTypeList(String redFieldTypeList) {
        this.redFieldTypeList = redFieldTypeList;
    }

    public String getRedirectCid() {
        return redirectCid;
    }

    public void setRedirectCid(String redirectCid) {
        this.redirectCid = redirectCid;
    }

    public String getRedirectPageId() {
        return redirectPageId;
    }

    public void setRedirectPageId(String redirectPageId) {
        this.redirectPageId = redirectPageId;
    }

    public String getRedirectType() {
        return redirectType;
    }

    public void setRedirectType(String redirectType) {
        this.redirectType = redirectType;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectVideoType() {
        return redirectVideoType;
    }

    public void setRedirectVideoType(String redirectVideoType) {
        this.redirectVideoType = redirectVideoType;
    }

    public String getReid() {
        return reid;
    }

    public void setReid(String reid) {
        this.reid = reid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public List<RecData> getRec() {
        return rec;
    }

    public void setRec(List<RecData> rec) {
        this.rec = rec;
    }

    public String getRedirectSubCid() {
        return redirectSubCid;
    }

    public void setRedirectSubCid(String redirectSubCid) {
        this.redirectSubCid = redirectSubCid;
    }

    public String getRedirectSubPageId() {
        return redirectSubPageId;
    }

    public void setRedirectSubPageId(String redirectSubPageId) {
        this.redirectSubPageId = redirectSubPageId;
    }

    public String getRedirectSubType() {
        return redirectSubType;
    }

    public void setRedirectSubType(String redirectSubType) {
        this.redirectSubType = redirectSubType;
    }

    public String getRedirectSubUrl() {
        return redirectSubUrl;
    }

    public void setRedirectSubUrl(String redirectSubUrl) {
        this.redirectSubUrl = redirectSubUrl;
    }

    public String getRedirectSubVideoType() {
        return redirectSubVideoType;
    }

    public void setRedirectSubVideoType(String redirectSubVideoType) {
        this.redirectSubVideoType = redirectSubVideoType;
    }

    public String getRedSubFieldTypeList() {
        return redSubFieldTypeList;
    }

    public void setRedSubFieldTypeList(String redSubFieldTypeList) {
        this.redSubFieldTypeList = redSubFieldTypeList;
    }

    public String getRedSubFieldDetailList() {
        return redSubFieldDetailList;
    }

    public void setRedSubFieldDetailList(String redSubFieldDetailList) {
        this.redSubFieldDetailList = redSubFieldDetailList;
    }

    public List<SubFrag> getSubFrags() {
        return subFrags;
    }

    public void setSubFrags(List<SubFrag> subFrags) {
        this.subFrags = subFrags;
    }

    public String getContentRid() {
        return contentRid;
    }

    public void setContentRid(String contentRid) {
        this.contentRid = contentRid;
    }

    /**
     * 频道首页中的数据部分来自CMS，部分来自推荐
     * 推荐和CMS的数据格式不一致
     * 推荐格式继承与CMS的格式,从而使得json解析可以同时接受CMS和推荐的数据
     */
//    public static class RecData extends CmsBlockContent {
//        private String album_type;
//        private String cgidefault;
//        private Integer cid;
//        private String createtime;
//        private String director;
//        private String duration;
//        private String episodes;
//        private String float_flag;
//        private String is_pay;
//        private String is_rec;
//        private String isalbum;
//        private String isend;
//        private String jump;
//        @JsonProperty("pic320*200")
//        private String pic320_200;
//        private String picHT;
//        private String picST;
//        private Long pid;
//        private String pidname;
//        private String playurl;
//        private Float score;
//        private String starring;
//        private String subtitle;
//        private String title;
//        private String updatetime;
//        private String vcount; // 更新至多少集
//        private Long vid;
//        private String vid_episode;
//        private String video_pic;
//        private String video_type;
//        private Long zid;
//        private String album_release_date;
//        private String album_sub_category;
//        private String pidsubtitle;
//        private String videoFollowTime;
//        private String album_area;
//        private String latest_auto_video_pic;// 综艺最新一期视频图（CDN图片前缀）
//
//        private Map<String, Object> picCollections; // 推荐为移动端给出的各种比例图片
//        private String vidsubtitle;
//
//        private String vtypeFlag;//2,全景
//        @JsonProperty("pic400*225")
//        private String pic400_225;
//
//        public String getVidsubtitle() {
//            return vidsubtitle;
//        }
//
//        public void setVidsubtitle(String vidsubtitle) {
//            this.vidsubtitle = vidsubtitle;
//        }
//
//        public String getDefaultPic() {
//            if (StringUtils.isNotBlank(pic320_200)) {
//                return pic320_200;
//            } else if (StringUtils.isNotBlank(picHT)) {
//                return picHT;
//            }
//            return null;
//        }
//
//        /**
//         * 指定比例存在多张图时,返回分辨率最高的一张
//         * 指定比例不存在时，随机返回一张任意比例的图
//         * @param width
//         * @param height
//         * @return
//         */
//        public String getImageByRatio(Integer width, Integer height) {
//            if (this.picCollections != null && width != null && height != null) {
//                String key = width + ":" + height; // 推荐给出的key为 16:9 4:3 16:10
//                Map<String, String> picMap = null;
//                Object obj = picCollections.get(key);
//                if (obj instanceof Map) {
//                    try {
//                        picMap = (Map<String, String>) obj;
//                    } catch (Exception e) {
//                        return null;
//                    }
//                } else {
//                    return null;
//                }
//
//                if (picMap != null) {
//                    // TODO 存在对应比例的图片，则取分辨率最高的
//                    int max = -1;
//                    String pic = null;
//                    for (Map.Entry<String, String> entry : picMap.entrySet()) {
//                        try {
//                            String ratioKey = entry.getKey();
//                            int widthKey = Integer.parseInt(ratioKey.substring(0, ratioKey.indexOf("*")));
//                            if (widthKey > max && StringUtils.isNotBlank(entry.getValue())) {
//                                max = widthKey;
//                                pic = entry.getValue();
//                            }
//                        } catch (Exception e) {
//                            break;
//                        }
//                    }
//                    return pic;
//                } else {
//                    // TODO 不存在对应比例的图片，随机填充一张图
//                    for (Map.Entry<String, Object> entry : picCollections.entrySet()) {
//                        if (entry.getValue() != null) {
//                            Object tobj = entry.getValue();
//                            Collection<String> vals = null;
//                            if (tobj instanceof Map) {
//                                vals = ((Map) tobj).values();
//                            } else {
//                                break;
//                            }
//                            for (Iterator<String> val = vals.iterator(); val.hasNext();) {
//                                String str = val.next();
//                                if (StringUtils.isNotBlank(str)) {
//                                    return str;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            return null;
//        }
//
//        public String getLatest_auto_video_pic() {
//            return latest_auto_video_pic;
//        }
//
//        public void setLatest_auto_video_pic(String latest_auto_video_pic) {
//            this.latest_auto_video_pic = latest_auto_video_pic;
//        }
//
//        public String getAlbum_area() {
//            return album_area;
//        }
//
//        public void setAlbum_area(String album_area) {
//            this.album_area = album_area;
//        }
//
//        public Map<String, Object> getPicCollections() {
//            return picCollections;
//        }
//
//        public void setPicCollections(Map<String, Object> picCollections) {
//            this.picCollections = picCollections;
//        }
//
//        public String getPic320_200() {
//            return pic320_200;
//        }
//
//        public void setPic320_200(String pic320_200) {
//            this.pic320_200 = pic320_200;
//        }
//
//        public String getAlbum_release_date() {
//            return album_release_date;
//        }
//
//        public void setAlbum_release_date(String album_release_date) {
//            this.album_release_date = album_release_date;
//        }
//
//        public String getAlbum_sub_category() {
//            return album_sub_category;
//        }
//
//        public void setAlbum_sub_category(String album_sub_category) {
//            this.album_sub_category = album_sub_category;
//        }
//
//        public String getPidsubtitle() {
//            return pidsubtitle;
//        }
//
//        public void setPidsubtitle(String pidsubtitle) {
//            this.pidsubtitle = pidsubtitle;
//        }
//
//        public String getVideoFollowTime() {
//            return videoFollowTime;
//        }
//
//        public void setVideoFollowTime(String videoFollowTime) {
//            this.videoFollowTime = videoFollowTime;
//        }
//
//        public Long getZid() {
//            return zid;
//        }
//
//        public void setZid(Long zid) {
//            this.zid = zid;
//        }
//
//        public String getAlbum_type() {
//            return album_type;
//        }
//
//        public void setAlbum_type(String album_type) {
//            this.album_type = album_type;
//        }
//
//        public String getCgidefault() {
//            return cgidefault;
//        }
//
//        public void setCgidefault(String cgidefault) {
//            this.cgidefault = cgidefault;
//        }
//
//        public Integer getCid() {
//            return cid;
//        }
//
//        public void setCid(Integer cid) {
//            this.cid = cid;
//        }
//
//        public String getCreatetime() {
//            return createtime;
//        }
//
//        public void setCreatetime(String createtime) {
//            this.createtime = createtime;
//        }
//
//        public String getDirector() {
//            return director;
//        }
//
//        public void setDirector(String director) {
//            this.director = director;
//        }
//
//        public String getDuration() {
//            return duration;
//        }
//
//        public void setDuration(String duration) {
//            this.duration = duration;
//        }
//
//        public String getEpisodes() {
//            return episodes;
//        }
//
//        public void setEpisodes(String episodes) {
//            this.episodes = episodes;
//        }
//
//        public String getFloat_flag() {
//            return float_flag;
//        }
//
//        public void setFloat_flag(String float_flag) {
//            this.float_flag = float_flag;
//        }
//
//        public String getIs_pay() {
//            return is_pay;
//        }
//
//        public void setIs_pay(String is_pay) {
//            this.is_pay = is_pay;
//        }
//
//        public String getIs_rec() {
//            return is_rec;
//        }
//
//        public void setIs_rec(String is_rec) {
//            this.is_rec = is_rec;
//        }
//
//        public String getIsalbum() {
//            return isalbum;
//        }
//
//        public void setIsalbum(String isalbum) {
//            this.isalbum = isalbum;
//        }
//
//        public String getIsend() {
//            return isend;
//        }
//
//        public void setIsend(String isend) {
//            this.isend = isend;
//        }
//
//        public String getJump() {
//            return jump;
//        }
//
//        public void setJump(String jump) {
//            this.jump = jump;
//        }
//
//        public String getPicHT() {
//            return picHT;
//        }
//
//        public void setPicHT(String picHT) {
//            this.picHT = picHT;
//        }
//
//        public String getPicST() {
//            return picST;
//        }
//
//        public void setPicST(String picST) {
//            this.picST = picST;
//        }
//
//        public Long getPid() {
//            return pid;
//        }
//
//        public void setPid(Long pid) {
//            this.pid = pid;
//        }
//
//        public String getPidname() {
//            return pidname;
//        }
//
//        public void setPidname(String pidname) {
//            this.pidname = pidname;
//        }
//
//        public String getPlayurl() {
//            return playurl;
//        }
//
//        public void setPlayurl(String playurl) {
//            this.playurl = playurl;
//        }
//
//        public Float getScore() {
//            return score;
//        }
//
//        public void setScore(Float score) {
//            this.score = score;
//        }
//
//        public String getStarring() {
//            return starring;
//        }
//
//        public void setStarring(String starring) {
//            this.starring = starring;
//        }
//
//        public String getSubtitle() {
//            return subtitle;
//        }
//
//        public void setSubtitle(String subtitle) {
//            this.subtitle = subtitle;
//        }
//
//        @Override
//        public String getTitle() {
//            return title;
//        }
//
//        @Override
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        public String getUpdatetime() {
//            return updatetime;
//        }
//
//        public void setUpdatetime(String updatetime) {
//            this.updatetime = updatetime;
//        }
//
//        public String getVcount() {
//            return vcount;
//        }
//
//        public void setVcount(String vcount) {
//            this.vcount = vcount;
//        }
//
//        public Long getVid() {
//            return vid;
//        }
//
//        public void setVid(Long vid) {
//            this.vid = vid;
//        }
//
//        public String getVid_episode() {
//            return vid_episode;
//        }
//
//        public void setVid_episode(String vid_episode) {
//            this.vid_episode = vid_episode;
//        }
//
//        public String getVideo_pic() {
//            return video_pic;
//        }
//
//        public void setVideo_pic(String video_pic) {
//            this.video_pic = video_pic;
//        }
//
//        public String getVideo_type() {
//            return video_type;
//        }
//
//        public void setVideo_type(String video_type) {
//            this.video_type = video_type;
//        }
//
//        public String getVtypeFlag() {
//            return vtypeFlag;
//        }
//
//        public void setVtypeFlag(String vtypeFlag) {
//            this.vtypeFlag = vtypeFlag;
//        }
//
//        public String getPic400_225() {
//            return pic400_225;
//        }
//
//        public void setPic400_225(String pic400_225) {
//            this.pic400_225 = pic400_225;
//        }
//
//        public String getImageByRatioAndSize(String sizeRatio,String picSize) {
//            if (this.picCollections != null && StringUtils.isNotBlank(sizeRatio) && StringUtils.isNotBlank(picSize)) {
//                Map<String, String> picMap = null;
//                Object obj = picCollections.get(sizeRatio);
//                if (obj instanceof Map) {
//                    try {
//                        picMap = (Map<String, String>) obj;
//                    } catch (Exception e) {
//                        return null;
//                    }
//                } else {
//                    return null;
//                }
//                if (picMap != null) {
//                    String pic = picMap.get(picSize);
//                    return pic;
//                }
//            }
//            return null;
//        }
//    }


//    public static class SubFrag {
//        private List<RecData> blockContents;
//        private String contentStyle;
//
//        public List<RecData> getBlockContents() {
//            return blockContents;
//        }
//
//        public void setBlockContents(List<RecData> blockContents) {
//            this.blockContents = blockContents;
//        }
//
//        public String getContentStyle() {
//            return contentStyle;
//        }
//
//        public void setContentStyle(String contentStyle) {
//            this.contentStyle = contentStyle;
//        }
//    }

}
