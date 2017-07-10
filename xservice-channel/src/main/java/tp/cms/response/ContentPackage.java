package tp.cms.response;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * CMS内容包元素类
 * CMS接口中在同一集合中返回不同产品包
 * 这边解析只能把不同元素合为一个大元素解析
 */
@DataObject(generateConverter = true,inheritConverter = true)
public class ContentPackage extends BaseResponse {
    /**
     * 
     */
    private static final long serialVersionUID = -7822576067075818458L;
    // 所有专题包共用属性--------start
    private String id;// 包id
    private String name;// 包名称
    private String porder;// 在包中排序
    private String ptype;// 特辑包类型，1：专辑包，2：视频包，3：直播包
    // 所有专题包共用属性--------end

    private List<ContentItem> dataList = new ArrayList<ContentItem>();// 专辑列表

    public ContentPackage() {
    }

    public ContentPackage(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        ContentPackageConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ContentPackageConverter.toJson(this, json);
        return json;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public List<ContentItem> getDataList() {
        return dataList;
    }

    public void setDataList(List<ContentItem> dataList) {
        this.dataList = dataList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPorder() {
        return porder;
    }

    public void setPorder(String porder) {
        this.porder = porder;
    }

    @Override
    public String toString() {
        return "ContentPackage{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", porder='" + porder + '\'' +
                ", ptype='" + ptype + '\'' +
                ", dataList=" + dataList +
                '}';
    }

    /**
     * 由于CMS接口数据问题
     * 直播、视频、专辑合用此类
     */
//    public static class ContentItem {
//        // 公共属性----------start
//        private String id;// 直播id
//        private String subTitle;// 副标题
//        private String nameCn;// 名称
//        private String description;// 描述
//        private Long duration;// 时长
//        private String releaseDate;// 上映时间
//        private Map<String, String> category;// 电影、电视剧、综艺、动漫
//        private Map<String, String> subCategory;// 动作 言情
//        private Map<String, String> downloadPlatform;
//        private Object playPlatform;
//        // 公共属性----------end
//        // 直播中需要---------start
//        private String rname;// 专辑名称
//        private String rid;// 场次id
//        private String pic43;// 原图地址
//        // 直播中需要---------end
//        // 专辑中需要---------start
//        private String actor;// 演员
//        private List<Object> actorDesc;// 对演员在视频中的角色的介绍
//        private String actorPlay;// 饰演角色// 角色定妆照
//        private String actorPlayPic;
//        private String alias;// 别名
//        private String nameEn;// 英文名称
//        private String allowForgienPlatform;// 是否海外播放
//        private String area;// 地区
//        private Map<String, String> directory;// 导演
//       // private Map<String, String> starring;// 主演
//        private String isEnd;// 是否完结
//        private String tag;// 标签
//        private Map<String, String> albumType;
//        private String episode;// 集数
//        private String isHomemade;// 是否自制
//        private String nowEpisodes;// 当前更新到集数
//        private String nowIssue;// 当前版权
//        private String officialUrl;
//        private Map<String, String> picCollections;
//        private Map<String, String> platformVideoInfo;
//        private Map<String, String> platformVideoNum;
//        private String relationId;// 关联id，用于推荐使用
//        // 专辑中需要---------end
//        // 视频包需要---------start
//        private Long pid;// 专题id
//        private String porder;// 在专辑中顺序
//        private Map<String, String> videoType;// 视频类型
//        private String issue;
//        private String mid;// 媒资ID
//        private String transCodePrefix;// 转码视频截图前缀
//        private Map<String, String> picAll;// 图片地址
//        private String singer;
//
//        // 视频包需要---------end
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getSubTitle() {
//            return subTitle;
//        }
//
//        public void setSubTitle(String subTitle) {
//            this.subTitle = subTitle;
//        }
//
//        public String getNameCn() {
//            return nameCn;
//        }
//
//        public void setNameCn(String nameCn) {
//            this.nameCn = nameCn;
//        }
//
//        public String getDescription() {
//            return description;
//        }
//
//        public void setDescription(String description) {
//            this.description = description;
//        }
//
//        public Long getDuration() {
//            return duration;
//        }
//
//        public void setDuration(Long duration) {
//            this.duration = duration;
//        }
//
//        public String getReleaseDate() {
//            return releaseDate;
//        }
//
//        public void setReleaseDate(String releaseDate) {
//            this.releaseDate = releaseDate;
//        }
//
//        public Map<String, String> getCategory() {
//            return category;
//        }
//
//        public void setCategory(Map<String, String> category) {
//            this.category = category;
//        }
//
//        public Map<String, String> getSubCategory() {
//            return subCategory;
//        }
//
//        public void setSubCategory(Map<String, String> subCategory) {
//            this.subCategory = subCategory;
//        }
//
//        public Map<String, String> getDownloadPlatform() {
//            return downloadPlatform;
//        }
//
//        public void setDownloadPlatform(Map<String, String> downloadPlatform) {
//            this.downloadPlatform = downloadPlatform;
//        }
//
//        public Object getPlayPlatform() {
//            return playPlatform;
//        }
//
//        public void setPlayPlatform(Object playPlatform) {
//            this.playPlatform = playPlatform;
//        }
//
//        public String getRname() {
//            return rname;
//        }
//
//        public void setRname(String rname) {
//            this.rname = rname;
//        }
//
//        public String getRid() {
//            return rid;
//        }
//
//        public void setRid(String rid) {
//            this.rid = rid;
//        }
//
//        public String getPic43() {
//            return pic43;
//        }
//
//        public void setPic43(String pic43) {
//            this.pic43 = pic43;
//        }
//
//        public String getActor() {
//            return actor;
//        }
//
//        public void setActor(String actor) {
//            this.actor = actor;
//        }
//
//        public List<Object> getActorDesc() {
//            return actorDesc;
//        }
//
//        public void setActorDesc(List<Object> actorDesc) {
//            this.actorDesc = actorDesc;
//        }
//
//        public String getActorPlay() {
//            return actorPlay;
//        }
//
//        public void setActorPlay(String actorPlay) {
//            this.actorPlay = actorPlay;
//        }
//
//        public String getActorPlayPic() {
//            return actorPlayPic;
//        }
//
//        public void setActorPlayPic(String actorPlayPic) {
//            this.actorPlayPic = actorPlayPic;
//        }
//
//        public String getAlias() {
//            return alias;
//        }
//
//        public void setAlias(String alias) {
//            this.alias = alias;
//        }
//
//        public String getNameEn() {
//            return nameEn;
//        }
//
//        public void setNameEn(String nameEn) {
//            this.nameEn = nameEn;
//        }
//
//        public String getAllowForgienPlatform() {
//            return allowForgienPlatform;
//        }
//
//        public void setAllowForgienPlatform(String allowForgienPlatform) {
//            this.allowForgienPlatform = allowForgienPlatform;
//        }
//
//        public String getArea() {
//            return area;
//        }
//
//        public void setArea(String area) {
//            this.area = area;
//        }
//
//        public Map<String, String> getDirectory() {
//            return directory;
//        }
//
//        public void setDirectory(Map<String, String> directory) {
//            this.directory = directory;
//        }
//
////        public Map<String, String> getStarring() {
////            return starring;
////        }
////
////        public void setStarring(Map<String, String> starring) {
////            this.starring = starring;
////        }
//
//        public String getIsEnd() {
//            return isEnd;
//        }
//
//        public void setIsEnd(String isEnd) {
//            this.isEnd = isEnd;
//        }
//
//        public String getTag() {
//            return tag;
//        }
//
//        public void setTag(String tag) {
//            this.tag = tag;
//        }
//
//        public Map<String, String> getAlbumType() {
//            return albumType;
//        }
//
//        public void setAlbumType(Map<String, String> albumType) {
//            this.albumType = albumType;
//        }
//
//        public String getEpisode() {
//            return episode;
//        }
//
//        public void setEpisode(String episode) {
//            this.episode = episode;
//        }
//
//        public String getIsHomemade() {
//            return isHomemade;
//        }
//
//        public void setIsHomemade(String isHomemade) {
//            this.isHomemade = isHomemade;
//        }
//
//        public String getNowEpisodes() {
//            return nowEpisodes;
//        }
//
//        public void setNowEpisodes(String nowEpisodes) {
//            this.nowEpisodes = nowEpisodes;
//        }
//
//        public String getNowIssue() {
//            return nowIssue;
//        }
//
//        public void setNowIssue(String nowIssue) {
//            this.nowIssue = nowIssue;
//        }
//
//        public String getOfficialUrl() {
//            return officialUrl;
//        }
//
//        public void setOfficialUrl(String officialUrl) {
//            this.officialUrl = officialUrl;
//        }
//
//        public Map<String, String> getPicCollections() {
//            return picCollections;
//        }
//
//        public void setPicCollections(Map<String, String> picCollections) {
//            this.picCollections = picCollections;
//        }
//
//        public Map<String, String> getPlatformVideoInfo() {
//            return platformVideoInfo;
//        }
//
//        public void setPlatformVideoInfo(Map<String, String> platformVideoInfo) {
//            this.platformVideoInfo = platformVideoInfo;
//        }
//
//        public Map<String, String> getPlatformVideoNum() {
//            return platformVideoNum;
//        }
//
//        public void setPlatformVideoNum(Map<String, String> platformVideoNum) {
//            this.platformVideoNum = platformVideoNum;
//        }
//
//        public String getRelationId() {
//            return relationId;
//        }
//
//        public void setRelationId(String relationId) {
//            this.relationId = relationId;
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
//        public String getPorder() {
//            return porder;
//        }
//
//        public void setPorder(String porder) {
//            this.porder = porder;
//        }
//
//        public Map<String, String> getVideoType() {
//            return videoType;
//        }
//
//        public void setVideoType(Map<String, String> videoType) {
//            this.videoType = videoType;
//        }
//
//        public String getIssue() {
//            return issue;
//        }
//
//        public void setIssue(String issue) {
//            this.issue = issue;
//        }
//
//        public String getMid() {
//            return mid;
//        }
//
//        public void setMid(String mid) {
//            this.mid = mid;
//        }
//
//        public String getTransCodePrefix() {
//            return transCodePrefix;
//        }
//
//        public void setTransCodePrefix(String transCodePrefix) {
//            this.transCodePrefix = transCodePrefix;
//        }
//
//        public Map<String, String> getPicAll() {
//            return picAll;
//        }
//
//        public void setPicAll(Map<String, String> picAll) {
//            this.picAll = picAll;
//        }
//
//        public String getSinger() {
//            return singer;
//        }
//
//        public void setSinger(String singer) {
//            this.singer = singer;
//        }
//
//    }
}
