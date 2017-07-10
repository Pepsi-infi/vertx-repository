package cache.dto;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

//@Document(collection = "video")
@DataObject(generateConverter = true,inheritConverter = true)
public class Video extends BaseCache {
    private static final Logger logger = LoggerFactory.getLogger(Video.class);
    /**
     * 
     */
    private static final long serialVersionUID = 4623932982121107053L;

    public static String COLLECTION_NAME = "video";


    //    @Id
    private String key;
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    @Protobuf(fieldType = FieldType.INT64, order = 1)
    private Long albumId;// 所属专辑id

    @Protobuf(fieldType = FieldType.INT64, order = 2)
    private Long id;// 视频ID

    @Protobuf(fieldType = FieldType.INT32, order = 3)
    private Integer type;// 视频类型

    @Protobuf(fieldType = FieldType.INT32, order = 4)
    private Integer porder;// 在专辑顺序

    @Protobuf(fieldType = FieldType.STRING, order = 5)
    private String mid;// 媒资ID;//
                       // 一个视频可能对应着多个媒资id(比如一个视频国语音轨，也有粤语的音轨，就会转出两个媒资id)

    @Protobuf(fieldType = FieldType.STRING, order = 6)
    private String nameCn;// 中文名称

    @Protobuf(fieldType = FieldType.STRING, order = 7)
    private String alias;// 别名

    @Protobuf(fieldType = FieldType.STRING, order = 8)
    private String subTitle;// 副标题

    @Protobuf(fieldType = FieldType.STRING, order = 9)
    private String shortDesc;//

    @Protobuf(fieldType = FieldType.STRING, order = 10)
    private String description;// 描述

    @Protobuf(fieldType = FieldType.STRING, order = 11)
    private String tag;// 标签

    @Protobuf(fieldType = FieldType.INT32, order = 12)
    private Integer categoryId;// 电视剧 电影

    @Protobuf(fieldType = FieldType.STRING, order = 13)
    private String categoryName;// 分类名称

    @Protobuf(fieldType = FieldType.STRING, order = 14)
    private String subCategory;// 动作 言情 惊悚 都市

    @Protobuf(fieldType = FieldType.STRING, order = 15)
    private String subCategoryName;//

    @Protobuf(fieldType = FieldType.STRING, order = 16)
    private String area;// 地区

    @Protobuf(fieldType = FieldType.STRING, order = 17)
    private String areaName;// 地区名称

    @Protobuf(fieldType = FieldType.STRING, order = 18)
    private String episode;// 在动漫频道，可能有2.5集， 也有可能一个视频文件，包含两集

    @Protobuf(fieldType = FieldType.INT32, order = 19)
    private Integer btime;// 片头时间

    @Protobuf(fieldType = FieldType.INT32, order = 20)
    private Integer etime;// 片尾时间

    @Protobuf(fieldType = FieldType.OBJECT, order = 21)
    private List<WatchingFocusCache> watchingFocus;// 看点

    @Protobuf(fieldType = FieldType.STRING, order = 22)
    private String downloadPlatform;// 允许下载平台

    @Protobuf(fieldType = FieldType.STRING, order = 23)
    private String playPlatform;// 推送平台

    @Protobuf(fieldType = FieldType.STRING, order = 24)
    private String payPlatform;// 付费平台

    @Protobuf(fieldType = FieldType.INT32, order = 25)
    private Integer duration;// 时长

    @Protobuf(fieldType = FieldType.INT32, order = 26)
    private Integer status;// 发布状态： 0;//未发布 1已发布2发布失败

    @Protobuf(fieldType = FieldType.INT32, order = 27)
    private Integer deleted;// 删除标记

    @Protobuf(fieldType = FieldType.STRING, order = 28)
    private String remark;// 备注

    @Protobuf(fieldType = FieldType.FLOAT, order = 29)
    private Float score;// 1);//

    // private Map<String, String> vPicAll;// 图片地址

    @Protobuf(fieldType = FieldType.OBJECT, order = 30)
    private List<PicAll> picAll;// 图片地址

    @Protobuf(fieldType = FieldType.STRING, order = 31)
    private String picOriginal;// 原图

    @Protobuf(fieldType = FieldType.STRING, order = 32)
    private String relativeContent;// 关联内容

    @Protobuf(fieldType = FieldType.STRING, order = 33)
    private String releaseDate;// 发行时间
    // private String schedule_publish_date;// 定时发布时间

    @Protobuf(fieldType = FieldType.STRING, order = 34)
    private String firstPlayTime;// 首播时间

    @Protobuf(fieldType = FieldType.INT32, order = 35)
    private Integer issue;// 期数

    @Protobuf(fieldType = FieldType.STRING, order = 36)
    private String school;// 学校

    @Protobuf(fieldType = FieldType.STRING, order = 37)
    private String starring;// 纪录片中的演员
    // private List<> vStarringList;
    // private String starring_play;// 纪录片中的人物

    @Protobuf(fieldType = FieldType.STRING, order = 38)
    private String directory;// 导演

    @Protobuf(fieldType = FieldType.STRING, order = 39)
    private String actor;// 演员

    @Protobuf(fieldType = FieldType.STRING, order = 40)
    private String actorPlay;// 饰演角色

    @Protobuf(fieldType = FieldType.INT32, order = 41)
    private Integer language;// 语言

    @Protobuf(fieldType = FieldType.STRING, order = 42)
    private String languageName;//

    @Protobuf(fieldType = FieldType.STRING, order = 43)
    private String singleName;// 动漫频道 单集名称

    @Protobuf(fieldType = FieldType.INT32, order = 44)
    private Integer style;// 分类

    @Protobuf(fieldType = FieldType.STRING, order = 45)
    private String styleName;//

    @Protobuf(fieldType = FieldType.INT32, order = 46)
    private Integer sportsType;// 篮球 足球 综合

    @Protobuf(fieldType = FieldType.STRING, order = 47)
    private String sportsTypeName;//

    @Protobuf(fieldType = FieldType.INT32, order = 48)
    private Integer letvMakeStyle;// 魅力研习社 可以说的秘密 我为校花狂 影视风向榜 午间道 星月私房话

    @Protobuf(fieldType = FieldType.STRING, order = 49)
    private String letvMakeStyleName;//

    @Protobuf(fieldType = FieldType.INT32, order = 50)
    private Integer popStyle;// 风尚in坐标 天使爱美丽 ElleTV

    @Protobuf(fieldType = FieldType.STRING, order = 51)
    private String popStyleName;//

    @Protobuf(fieldType = FieldType.INT32, order = 52)
    private Integer letvProduceStyle;// 电影 微电影

    @Protobuf(fieldType = FieldType.STRING, order = 53)
    private String letvProduceStyleName;//

    @Protobuf(fieldType = FieldType.STRING, order = 54)
    private String cartoonStyle;// 动漫分类

    @Protobuf(fieldType = FieldType.STRING, order = 55)
    private String cartoonStyleName;// 动漫分类\n名称

    @Protobuf(fieldType = FieldType.INT32, order = 56)
    private Integer fieldType;// 领域类型

    @Protobuf(fieldType = FieldType.STRING, order = 57)
    private String fieldTypeName;// 领域类型名称

    @Protobuf(fieldType = FieldType.INT32, order = 58)
    private Integer pre;// 关联类型，娱乐频道

    @Protobuf(fieldType = FieldType.STRING, order = 59)
    private String preName;// 关联类型名称

    @Protobuf(fieldType = FieldType.STRING, order = 60)
    private String instructor;// 讲师

    @Protobuf(fieldType = FieldType.STRING, order = 61)
    private String compere;// 主持人

    @Protobuf(fieldType = FieldType.STRING, order = 62)
    private String guest;// 嘉宾

    @Protobuf(fieldType = FieldType.STRING, order = 63)
    private String singer;// 歌手

    @Protobuf(fieldType = FieldType.STRING, order = 64)
    private String singerType;// 歌手类型

    @Protobuf(fieldType = FieldType.STRING, order = 65)
    private String singerTypeName;//

    @Protobuf(fieldType = FieldType.STRING, order = 66)
    private String musicAuthors;// 音乐作词人

    @Protobuf(fieldType = FieldType.STRING, order = 67)
    private String musicStyle;// 音乐风格

    @Protobuf(fieldType = FieldType.STRING, order = 68)
    private String musicStyleName;//

    @Protobuf(fieldType = FieldType.INT32, order = 69)
    private Integer travelTheme;// 旅游主题

    @Protobuf(fieldType = FieldType.STRING, order = 70)
    private String travelThemeName;// 旅游主题名称

    @Protobuf(fieldType = FieldType.INT32, order = 71)
    private Integer travelType;// 旅游类型

    @Protobuf(fieldType = FieldType.STRING, order = 72)
    private String travelTypeName;// 旅游类型名称

    @Protobuf(fieldType = FieldType.STRING, order = 73)
    private String maker;// 音乐频道制片人监制

    @Protobuf(fieldType = FieldType.STRING, order = 74)
    private String recordCompany;// 唱片公司

    @Protobuf(fieldType = FieldType.STRING, order = 75)
    private String copyrightCompany;// 版权公司

    @Protobuf(fieldType = FieldType.STRING, order = 76)
    private String copyrightStart;// 版权开始时间

    @Protobuf(fieldType = FieldType.STRING, order = 77)
    private String copyrightEnd;// 版权结束时间

    @Protobuf(fieldType = FieldType.STRING, order = 78)
    private String copyrightType;// 版权类型

    @Protobuf(fieldType = FieldType.STRING, order = 79)
    private String copyrightTypeName;// 版权类型名称

    @Protobuf(fieldType = FieldType.STRING, order = 80)
    private String disableType;// 屏蔽类型

    @Protobuf(fieldType = FieldType.STRING, order = 81)
    private String disableTypeName;// 屏蔽类型名称

    @Protobuf(fieldType = FieldType.STRING, order = 82)
    private String issueCompany;// 发行公司

    @Protobuf(fieldType = FieldType.STRING, order = 83)
    private String musicCompose;// 音乐作曲者

    @Protobuf(fieldType = FieldType.STRING, order = 84)
    private String team;// 球队(体育)

    @Protobuf(fieldType = FieldType.STRING, order = 85)
    private String transCodePrefix;// 转码视频截图前缀

    @Protobuf(fieldType = FieldType.STRING, order = 86)
    private String recreationType;// 娱乐分类(娱乐)

    @Protobuf(fieldType = FieldType.STRING, order = 87)
    private String recreationTypeName;//

    @Protobuf(fieldType = FieldType.STRING, order = 88)
    private String playStreams;// 所有的码流

    @Protobuf(fieldType = FieldType.STRING, order = 89)
    private String carfilmType;// 汽车频道影片分类

    @Protobuf(fieldType = FieldType.STRING, order = 90)
    private String carfilmTypeName;//

    @Protobuf(fieldType = FieldType.INT64, order = 91)
    private Long createTime;// 创建时间

    @Protobuf(fieldType = FieldType.INT64, order = 92)
    private Long updateTime;// 修改时间

    @Protobuf(fieldType = FieldType.STRING, order = 93)
    private String midStreams;// 媒资id和码流的对应关系

    @Protobuf(fieldType = FieldType.INT32, order = 94)
    private Integer attr;// 视频属性是否正片（1：正片）

    @Protobuf(fieldType = FieldType.INT32, order = 95)
    private Integer logonum;// 0有默认水印1无默认水印

    @Protobuf(fieldType = FieldType.STRING, order = 96)
    private String drmFlagId;

    @Protobuf(fieldType = FieldType.STRING, order = 97)
    private String pic;

    @Protobuf(fieldType = FieldType.INT32, order = 98)
    private Integer isPlayLock;// 家长锁功能0否，1是

    @Protobuf(fieldType = FieldType.INT32, order = 99)
    private Integer ismobile;// 是否有移动版权 1有版权，0无版权

    @Protobuf(fieldType = FieldType.INT64, order = 100)
    private Long vv;// 播放数

    @Protobuf(fieldType = FieldType.INT64, order = 101)
    private Long commentCnt;// 评论数

    // --扩展属性
    /**
     * 视频码流code列表
     */

    @Protobuf(fieldType = FieldType.STRING, order = 102)
    private List<String> videoStreams;

    /**
     * 媒资id
     */

    @Protobuf(fieldType = FieldType.INT64, order = 103)
    private Long midL;

    /**
     * 内容分类（电影、电视剧）
     */
    // private Integer categoryId;

    /**
     * 视频播放码流对象列表
     */

    @Protobuf(fieldType = FieldType.OBJECT, order = 104)
    private List<Stream> streams;

    @Protobuf(fieldType = FieldType.OBJECT, order = 105)
    private List<Stream> normalStreams;

    @Protobuf(fieldType = FieldType.OBJECT, order = 106)
    private List<Stream> theatreStreams;

    @Protobuf(fieldType = FieldType.OBJECT, order = 107)
    private List<Stream> threeDStreams;

    @Protobuf(fieldType = FieldType.OBJECT, order = 108)
    private List<Stream> dolbyStreams;

    @Protobuf(fieldType = FieldType.OBJECT, order = 109)
    private List<Stream> dtsStreams;

    /**
         * 
         */

    @Protobuf(fieldType = FieldType.INT32, order = 110)
    private Integer page;

    @Protobuf(fieldType = FieldType.OBJECT, order = 111)
    private List<ActorCache> guestList;// 关注嘉宾

    @Protobuf(fieldType = FieldType.INT64, order = 112)
    private List<Long> segmentIds;// 视频关联片段id列表

    @Protobuf(fieldType = FieldType.INT32, order = 113)
    private Integer isDanmaku;// 是否弹幕 1为是，0为否。优先级为视频弹幕设置》专辑弹幕设置》频道弹幕设置

    @Protobuf(fieldType = FieldType.INT64, order = 114)
    private List<Long> guestIds;// 嘉宾ids
    
    @Protobuf(fieldType = FieldType.OBJECT, order = 115)
    private List<Stream> VRstreams;//全景视频码流
    
    @Protobuf(fieldType = FieldType.STRING, order = 116)
    private String vtypeFlag;//码率标识，字符串格式，可能包含多个。码率标识说明：1：杜比 2：全景 3: 2k 4：1080p 5：4k 6：3D 7：DTS

    @Protobuf(fieldType = FieldType.STRING,order=117)
    private String contentRatingId;// 内容分级id

    @Protobuf(fieldType = FieldType.STRING,order=118)
    private String contentRatingValue;// 内容分级value
    
    @Protobuf(fieldType = FieldType.STRING,order=119)
    private String coopPlatform;//合作平台

    @Protobuf(fieldType = FieldType.STRING,order=120)
    private String statusCode;// 4001为ip受限

    public Video() {
    }

    public Video(JsonObject json) {
        if(json != null){
            if(json.getValue("_id") != null && StringUtils.isNotBlank((String)json.getValue("_id"))){
                json.put("key",(String)json.getValue("_id"));
            }
            VideoConverter.fromJson(json, this);
        }
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        VideoConverter.toJson(this, json);
        return json;
    }

    public JsonObject toDocumentJson() {
        JsonObject json = new JsonObject();
        VideoConverter.toJson(this, json);
        json.put("_id",this.key);
        return json;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPorder() {
        return porder;
    }

    public void setPorder(Integer porder) {
        this.porder = porder;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public Integer getBtime() {
        return btime;
    }

    public void setBtime(Integer btime) {
        this.btime = btime;
    }

    public Integer getEtime() {
        return etime;
    }

    public void setEtime(Integer etime) {
        this.etime = etime;
    }

    public List<WatchingFocusCache> getWatchingFocus() {
        return watchingFocus;
    }

    public void setWatchingFocus(List<WatchingFocusCache> watchingFocus) {
        this.watchingFocus = watchingFocus;
    }

    public String getDownloadPlatform() {
        return downloadPlatform;
    }

    public void setDownloadPlatform(String downloadPlatform) {
        this.downloadPlatform = downloadPlatform;
    }

    public String getPlayPlatform() {
        return playPlatform;
    }

    public void setPlayPlatform(String playPlatform) {
        this.playPlatform = playPlatform;
    }

    public String getPayPlatform() {
        return payPlatform;
    }

    public void setPayPlatform(String payPlatform) {
        this.payPlatform = payPlatform;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public List<PicAll> getPicAll() {
        return picAll;
    }

    public void setPicAll(List<PicAll> picAll) {
        this.picAll = picAll;
    }

    public String getPicOriginal() {
        return picOriginal;
    }

    public void setPicOriginal(String picOriginal) {
        this.picOriginal = picOriginal;
    }

    public String getRelativeContent() {
        return relativeContent;
    }

    public void setRelativeContent(String relativeContent) {
        this.relativeContent = relativeContent;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getFirstPlayTime() {
        return firstPlayTime;
    }

    public void setFirstPlayTime(String firstPlayTime) {
        this.firstPlayTime = firstPlayTime;
    }

    public Integer getIssue() {
        return issue;
    }

    public void setIssue(Integer issue) {
        this.issue = issue;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getStarring() {
        return starring;
    }

    public void setStarring(String starring) {
        this.starring = starring;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getActorPlay() {
        return actorPlay;
    }

    public void setActorPlay(String actorPlay) {
        this.actorPlay = actorPlay;
    }

    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getSingleName() {
        return singleName;
    }

    public void setSingleName(String singleName) {
        this.singleName = singleName;
    }

    public Integer getStyle() {
        return style;
    }

    public void setStyle(Integer style) {
        this.style = style;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public Integer getSportsType() {
        return sportsType;
    }

    public void setSportsType(Integer sportsType) {
        this.sportsType = sportsType;
    }

    public String getSportsTypeName() {
        return sportsTypeName;
    }

    public void setSportsTypeName(String sportsTypeName) {
        this.sportsTypeName = sportsTypeName;
    }

    public Integer getLetvMakeStyle() {
        return letvMakeStyle;
    }

    public void setLetvMakeStyle(Integer letvMakeStyle) {
        this.letvMakeStyle = letvMakeStyle;
    }

    public String getLetvMakeStyleName() {
        return letvMakeStyleName;
    }

    public void setLetvMakeStyleName(String letvMakeStyleName) {
        this.letvMakeStyleName = letvMakeStyleName;
    }

    public Integer getPopStyle() {
        return popStyle;
    }

    public void setPopStyle(Integer popStyle) {
        this.popStyle = popStyle;
    }

    public String getPopStyleName() {
        return popStyleName;
    }

    public void setPopStyleName(String popStyleName) {
        this.popStyleName = popStyleName;
    }

    public Integer getLetvProduceStyle() {
        return letvProduceStyle;
    }

    public void setLetvProduceStyle(Integer letvProduceStyle) {
        this.letvProduceStyle = letvProduceStyle;
    }

    public String getLetvProduceStyleName() {
        return letvProduceStyleName;
    }

    public void setLetvProduceStyleName(String letvProduceStyleName) {
        this.letvProduceStyleName = letvProduceStyleName;
    }

    public String getCartoonStyle() {
        return cartoonStyle;
    }

    public void setCartoonStyle(String cartoonStyle) {
        this.cartoonStyle = cartoonStyle;
    }

    public String getCartoonStyleName() {
        return cartoonStyleName;
    }

    public void setCartoonStyleName(String cartoonStyleName) {
        this.cartoonStyleName = cartoonStyleName;
    }

    public Integer getFieldType() {
        return fieldType;
    }

    public void setFieldType(Integer fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldTypeName() {
        return fieldTypeName;
    }

    public void setFieldTypeName(String fieldTypeName) {
        this.fieldTypeName = fieldTypeName;
    }

    public Integer getPre() {
        return pre;
    }

    public void setPre(Integer pre) {
        this.pre = pre;
    }

    public String getPreName() {
        return preName;
    }

    public void setPreName(String preName) {
        this.preName = preName;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getCompere() {
        return compere;
    }

    public void setCompere(String compere) {
        this.compere = compere;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getSingerType() {
        return singerType;
    }

    public void setSingerType(String singerType) {
        this.singerType = singerType;
    }

    public String getSingerTypeName() {
        return singerTypeName;
    }

    public void setSingerTypeName(String singerTypeName) {
        this.singerTypeName = singerTypeName;
    }

    public String getMusicAuthors() {
        return musicAuthors;
    }

    public void setMusicAuthors(String musicAuthors) {
        this.musicAuthors = musicAuthors;
    }

    public String getMusicStyle() {
        return musicStyle;
    }

    public void setMusicStyle(String musicStyle) {
        this.musicStyle = musicStyle;
    }

    public String getMusicStyleName() {
        return musicStyleName;
    }

    public void setMusicStyleName(String musicStyleName) {
        this.musicStyleName = musicStyleName;
    }

    public Integer getTravelTheme() {
        return travelTheme;
    }

    public void setTravelTheme(Integer travelTheme) {
        this.travelTheme = travelTheme;
    }

    public String getTravelThemeName() {
        return travelThemeName;
    }

    public void setTravelThemeName(String travelThemeName) {
        this.travelThemeName = travelThemeName;
    }

    public Integer getTravelType() {
        return travelType;
    }

    public void setTravelType(Integer travelType) {
        this.travelType = travelType;
    }

    public String getTravelTypeName() {
        return travelTypeName;
    }

    public void setTravelTypeName(String travelTypeName) {
        this.travelTypeName = travelTypeName;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getRecordCompany() {
        return recordCompany;
    }

    public void setRecordCompany(String recordCompany) {
        this.recordCompany = recordCompany;
    }

    public String getCopyrightCompany() {
        return copyrightCompany;
    }

    public void setCopyrightCompany(String copyrightCompany) {
        this.copyrightCompany = copyrightCompany;
    }

    public String getCopyrightStart() {
        return copyrightStart;
    }

    public void setCopyrightStart(String copyrightStart) {
        this.copyrightStart = copyrightStart;
    }

    public String getCopyrightEnd() {
        return copyrightEnd;
    }

    public void setCopyrightEnd(String copyrightEnd) {
        this.copyrightEnd = copyrightEnd;
    }

    public String getCopyrightType() {
        return copyrightType;
    }

    public void setCopyrightType(String copyrightType) {
        this.copyrightType = copyrightType;
    }

    public String getCopyrightTypeName() {
        return copyrightTypeName;
    }

    public void setCopyrightTypeName(String copyrightTypeName) {
        this.copyrightTypeName = copyrightTypeName;
    }

    public String getDisableType() {
        return disableType;
    }

    public void setDisableType(String disableType) {
        this.disableType = disableType;
    }

    public String getDisableTypeName() {
        return disableTypeName;
    }

    public void setDisableTypeName(String disableTypeName) {
        this.disableTypeName = disableTypeName;
    }

    public String getIssueCompany() {
        return issueCompany;
    }

    public void setIssueCompany(String issueCompany) {
        this.issueCompany = issueCompany;
    }

    public String getMusicCompose() {
        return musicCompose;
    }

    public void setMusicCompose(String musicCompose) {
        this.musicCompose = musicCompose;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getTransCodePrefix() {
        return transCodePrefix;
    }

    public void setTransCodePrefix(String transCodePrefix) {
        this.transCodePrefix = transCodePrefix;
    }

    public String getRecreationType() {
        return recreationType;
    }

    public void setRecreationType(String recreationType) {
        this.recreationType = recreationType;
    }

    public String getRecreationTypeName() {
        return recreationTypeName;
    }

    public void setRecreationTypeName(String recreationTypeName) {
        this.recreationTypeName = recreationTypeName;
    }

    public String getPlayStreams() {
        return playStreams;
    }

    public void setPlayStreams(String playStreams) {
        this.playStreams = playStreams;
    }

    public String getCarfilmType() {
        return carfilmType;
    }

    public void setCarfilmType(String carfilmType) {
        this.carfilmType = carfilmType;
    }

    public String getCarfilmTypeName() {
        return carfilmTypeName;
    }

    public void setCarfilmTypeName(String carfilmTypeName) {
        this.carfilmTypeName = carfilmTypeName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getMidStreams() {
        return midStreams;
    }

    public void setMidStreams(String midStreams) {
        this.midStreams = midStreams;
    }

    public Integer getAttr() {
        return attr;
    }

    public void setAttr(Integer attr) {
        this.attr = attr;
    }

    public Integer getLogonum() {
        return logonum;
    }

    public void setLogonum(Integer logonum) {
        this.logonum = logonum;
    }

    public String getDrmFlagId() {
        return drmFlagId;
    }

    public void setDrmFlagId(String drmFlagId) {
        this.drmFlagId = drmFlagId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getIsPlayLock() {
        return isPlayLock;
    }

    public void setIsPlayLock(Integer isPlayLock) {
        this.isPlayLock = isPlayLock;
    }

    public Integer getIsmobile() {
        return ismobile;
    }

    public void setIsmobile(Integer ismobile) {
        this.ismobile = ismobile;
    }

    public Long getVv() {
        return vv;
    }

    public void setVv(Long vv) {
        this.vv = vv;
    }

    public Long getCommentCnt() {
        return commentCnt;
    }

    public void setCommentCnt(Long commentCnt) {
        this.commentCnt = commentCnt;
    }

    public List<String> getVideoStreams() {
        return videoStreams;
    }

    public void setVideoStreams(List<String> videoStreams) {
        this.videoStreams = videoStreams;
    }

    public Long getMidL() {
        return midL;
    }

    public void setMidL(Long midL) {
        this.midL = midL;
    }

    public List<Stream> getStreams() {
        return streams;
    }

    public void setStreams(List<Stream> streams) {
        this.streams = streams;
    }

    public List<Stream> getNormalStreams() {
        return normalStreams;
    }

    public void setNormalStreams(List<Stream> normalStreams) {
        this.normalStreams = normalStreams;
    }

    public List<Stream> getTheatreStreams() {
        return theatreStreams;
    }

    public void setTheatreStreams(List<Stream> theatreStreams) {
        this.theatreStreams = theatreStreams;
    }

    public List<Stream> getThreeDStreams() {
        return threeDStreams;
    }

    public void setThreeDStreams(List<Stream> threeDStreams) {
        this.threeDStreams = threeDStreams;
    }

    public List<Stream> getDolbyStreams() {
        return dolbyStreams;
    }

    public void setDolbyStreams(List<Stream> dolbyStreams) {
        this.dolbyStreams = dolbyStreams;
    }

    public List<Stream> getDtsStreams() {
        return dtsStreams;
    }

    public void setDtsStreams(List<Stream> dtsStreams) {
        this.dtsStreams = dtsStreams;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<ActorCache> getGuestList() {
        return guestList;
    }

    public void setGuestList(List<ActorCache> guestList) {
        this.guestList = guestList;
    }

    public List<Long> getSegmentIds() {
        return segmentIds;
    }

    public void setSegmentIds(List<Long> segmentIds) {
        this.segmentIds = segmentIds;
    }

    public Integer getIsDanmaku() {
        return isDanmaku;
    }

    public void setIsDanmaku(Integer isDanmaku) {
        this.isDanmaku = isDanmaku;
    }

    public List<Long> getGuestIds() {
        return guestIds;
    }

    public void setGuestIds(List<Long> guestIds) {
        this.guestIds = guestIds;
    }
    
    public List<Stream> getVRstreams() {
        return VRstreams;
    }

    public void setVRstreams(List<Stream> vRstreams) {
        VRstreams = vRstreams;
    }

    public String getVtypeFlag() {
        return vtypeFlag;
    }

    public void setVtypeFlag(String vtypeFlag) {
        this.vtypeFlag = vtypeFlag;
    }

//    public String getPic(Integer width, Integer hight) {
//        if (this.picAll == null && this.transCodePrefix == null) {
//            return "";
//        }
//        String img = null;
//        try {
//            if (picAll != null) {
//                Map<String, String> picAll = new HashMap<>();
//                for (PicAll pic : this.picAll) {
//                    picAll.put(pic.getPicKey(), pic.getPicValue());
//                }
//                img = picAll.get(width + "*" + hight);
//            }
//        } catch (Exception e) {
//
//        }
//        if (this.transCodePrefix != null && this.transCodePrefix.trim().length() > 0 && img == null) {
//            img = this.transCodePrefix + "/thumb/2_" + width + "_" + hight + ".jpg";
//        }
//        return img == null ? "" : img;
//    }
//
//    public Boolean isPositive() {
//        Boolean isPositive = true;
//        // 电影、电视剧、动漫、综艺、纪录片有正片的概念
//        if (this.categoryId != null
//                && (this.categoryId == 1 || this.categoryId == 2 || this.categoryId == 5 || 11 == this.categoryId || 16 == this.categoryId)) {
//            if (this.type == null || this.type != 180001) {
//                isPositive = false;
//            }
//        }
//        return isPositive;
//    }
//
//    public boolean isChargeVideo() {
//        if (this.payPlatform != null && payPlatform.contains("141003")) {
//            return true;
//        }
//        return false;
//    }

//    public boolean isCharge() {
//        if (this.payPlatform != null && this.payPlatform.contains("141007") && this.playPlatform != null
//                && this.playPlatform.contains("420007")) {
//            return true;
//        }
//        return false;
//    }
    
    public String getContentRatingId() {
        return contentRatingId;
    }
    public void setContentRatingId(String contentRatingId) {
        this.contentRatingId = contentRatingId;
    }
    public String getContentRatingValue() {
        return contentRatingValue;
    }
    public void setContentRatingValue(String contentRatingValue) {
        this.contentRatingValue = contentRatingValue;
    }
    
    public String getCoopPlatform() {
        return coopPlatform;
    }
    public void setCoopPlatform(String coopPlatform) {
        this.coopPlatform = coopPlatform;
    }
    
    public String getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    @Override
    public String toString() {
        return "Video [albumId=" + albumId + ", id=" + id
                + ", type=" + type + ", nameCn=" + nameCn + ", playStreams="
                + playStreams + ", page=" + page + ", contentRatingId="
                + contentRatingId + ", coopPlatform=" + coopPlatform
                + ", statusCode=" + statusCode + "]";
    }

}
