package cache.dto;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

//@Document(collection = "album")
@DataObject(generateConverter = true,inheritConverter = true)
public class Album extends BaseCache {

    public static String COLLECTION_NAME = "album";

    /**
     * 
     */
    private static final long serialVersionUID = 4534334320774243888L;

//    @Id
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Protobuf(fieldType = FieldType.INT64,order=1)
    private Long videoId;//默认播放视频id

    @Protobuf(fieldType = FieldType.INT64,order=2)
    private Long id;// 专辑ID

    @Protobuf(fieldType = FieldType.INT32,order=3)
    private Integer baseType;// 1.目录（越狱）2.专辑（越狱第一季）

    @Protobuf(fieldType = FieldType.INT32,order=4)
    private Integer type;// 正片、片花、花絮、资讯

    @Protobuf(fieldType = FieldType.STRING,order=5)
    private String typeName;// type名称

    @Protobuf(fieldType = FieldType.STRING,order=6)
    private String nameCn;// 中文名称
    // private String name_pinyin_abb_album;// 名字中每个字的拼音的首字母
    // private String name_en_album;// 英文名称

    @Protobuf(fieldType = FieldType.STRING,order=7)
    private String subTitle;// 副标题

    @Protobuf(fieldType = FieldType.STRING,order=8)
    private String alias;// 别名

    @Protobuf(fieldType = FieldType.STRING,order=9)
    private String shortDesc;// 简要描述

    @Protobuf(fieldType = FieldType.STRING,order=10)
    private String description;// 描述

    @Protobuf(fieldType = FieldType.STRING,order=11)
    private String tag;// 标签

    @Protobuf(fieldType = FieldType.FLOAT,order=12)
    private Float score;// 评分

    @Protobuf(fieldType = FieldType.INT32,order=13)
    private Integer categoryId;// 电影、电视剧、综艺、动漫

    @Protobuf(fieldType = FieldType.STRING,order=14)
    private String categoryName;// 分类名称

    @Protobuf(fieldType = FieldType.STRING,order=15)
    private String subCategory;// 动作 言情

    @Protobuf(fieldType = FieldType.STRING,order=16)
    private String subCategoryName;// 子分类名称

    @Protobuf(fieldType = FieldType.STRING,order=17)
    private String secondCate;// 二级分类

    @Protobuf(fieldType = FieldType.STRING,order=18)
    private String thirdCate;// 三级分类

    @Protobuf(fieldType = FieldType.STRING,order=19)
    private String downloadPlatform;// 允许下载平台

    @Protobuf(fieldType = FieldType.STRING,order=20)
    private String playPlatform;// 允许播放平台

    @Protobuf(fieldType = FieldType.STRING,order=21)
    private String payPlatform;// 收费平台

    @Protobuf(fieldType = FieldType.INT32,order=22)
    private Integer isPay;// 是否付费

    @Protobuf(fieldType = FieldType.INT32,order=23)
    private Integer status;// 发布状态： 0未发布 1已发布2发布失败

    @Protobuf(fieldType = FieldType.INT32,order=24)
    private Integer deleted;// 删除标记

    @Protobuf(fieldType = FieldType.INT64,order=25)
    private Long fid;// 父专辑ID

    @Protobuf(fieldType = FieldType.INT32,order=26)
    private Integer forder;// 在父专辑顺序

    @Protobuf(fieldType = FieldType.INT32,order=27)
    private Integer isEnd;// 是否完结

    @Protobuf(fieldType = FieldType.INT32,order=28)
    private Integer follownum;// 跟播到第几集

    @Protobuf(fieldType = FieldType.INT32,order=29)
    private Integer isHeight;// 是否高清

    @Protobuf(fieldType = FieldType.STRING,order=30)
    private String area;// 地区

    @Protobuf(fieldType = FieldType.STRING,order=31)
    private String areaName;// 地区名称

    @Protobuf(fieldType = FieldType.STRING,order=32)
    private String releaseDate;// 上映时间

    @Protobuf(fieldType = FieldType.STRING,order=33)
    private String letvReleaseDate;// 乐视上映时间

    @Protobuf(fieldType = FieldType.INT32,order=34)
    private Integer episode;// 总集数

    @Protobuf(fieldType = FieldType.STRING,order=35)
    private String playStatus;// 跟播状态

    @Protobuf(fieldType = FieldType.STRING,order=36)
    private String makerCompany;// 出品公司

    @Protobuf(fieldType = FieldType.INT32,order=37)
    private Integer directoryId;// 导演id

    @Protobuf(fieldType = FieldType.STRING,order=38)
    private String directory;// 导演

    @Protobuf(fieldType = FieldType.STRING,order=39)
    private String actor;// 演员
    // private String actor_play_album;// 饰演角色
    // private String actor_desc;// 对演员在视频中的角色的介绍
    // private String actor_play_pic;// 角色定妆照

    @Protobuf(fieldType = FieldType.STRING,order=40)
    private String starring;// 主演

    @Protobuf(fieldType = FieldType.OBJECT,order=41)
    private List<ActorCache> starringObj;

    @Protobuf(fieldType = FieldType.STRING,order=42)
    private String starringPlay;// 主演饰演角色
    // private String starring_play_pic;// 主演定妆照
    // private String starring_desc;// 主演介绍

    @Protobuf(fieldType = FieldType.OBJECT,order=43)
    private List<PicAll> picCollections;// 图片集
//    private Map<String, String> aPicCollections;// 图片集

    @Protobuf(fieldType = FieldType.STRING,order=44)
    private String picOriginal;// 原图和缩列图

    @Protobuf(fieldType = FieldType.STRING,order=45)
    private String screenWriter;// 编剧

    @Protobuf(fieldType = FieldType.STRING,order=46)
    private String maker;// 制片人

    @Protobuf(fieldType = FieldType.INT32,order=47)
    private Integer sportsType;// 体育分类

    @Protobuf(fieldType = FieldType.STRING,order=48)
    private String sportsTypeName;// 体育分类名称

    @Protobuf(fieldType = FieldType.INT32,order=49)
    private Integer letvMakeStyle;// 乐视制造分类

    @Protobuf(fieldType = FieldType.STRING,order=50)
    private String letvMakeStyleName;// 乐视制造分类名称

    @Protobuf(fieldType = FieldType.INT32,order=51)
    private Integer popStyle;// 风尚分类

    @Protobuf(fieldType = FieldType.STRING,order=52)
    private String popStyleName;// 风尚分类名称

    @Protobuf(fieldType = FieldType.INT32,order=53)
    private Integer letvProduceStyle;// 乐视出品分类

    @Protobuf(fieldType = FieldType.STRING,order=54)
    private String letvProduceStyleName;// 乐视出品分类名称

    @Protobuf(fieldType = FieldType.INT32,order=55)
    private Integer filmBaseType;// 电影频道分类 1.电影 2.微电影

    @Protobuf(fieldType = FieldType.STRING,order=56)
    private String filmBaseTypeName;// 电影分类名称

    @Protobuf(fieldType = FieldType.INT32,order=57)
    private Integer travelTheme;// 旅游主题

    @Protobuf(fieldType = FieldType.STRING,order=58)
    private String travelThemeName;// 旅游主题名称

    @Protobuf(fieldType = FieldType.INT32,order=59)
    private Integer travelType;// 旅游类型

    @Protobuf(fieldType = FieldType.STRING,order=60)
    private String travelTypeName;// 旅游类型名称

    @Protobuf(fieldType = FieldType.INT32,order=61)
    private Integer fieldType;// 领域类型

    @Protobuf(fieldType = FieldType.STRING,order=62)
    private String fieldTypeName;// 领域类型名称

    @Protobuf(fieldType = FieldType.STRING,order=63)
    private String rCompany;// 节目来源

    @Protobuf(fieldType = FieldType.INT32,order=64)
    private Integer language;// 内容语言

    @Protobuf(fieldType = FieldType.STRING,order=65)
    private String languageName;// 内容语言名称

    @Protobuf(fieldType = FieldType.STRING,order=66)
    private String fitAge;// 适应年龄

    @Protobuf(fieldType = FieldType.STRING,order=67)
    private String cast;// 动漫频道的声优

    @Protobuf(fieldType = FieldType.STRING,order=68)
    private String dub;// 配音

    @Protobuf(fieldType = FieldType.STRING,order=69)
    private String producer;// 监制

    @Protobuf(fieldType = FieldType.STRING,order=70)
    private String compere;// 主持人

    @Protobuf(fieldType = FieldType.OBJECT,order=71)
    private List<ActorCache> compereObj;// 主持人对象

    @Protobuf(fieldType = FieldType.STRING,order=72)
    private String instructor;// 讲师

    @Protobuf(fieldType = FieldType.STRING,order=73)
    private String music_authors;// 音乐作词人

    @Protobuf(fieldType = FieldType.STRING,order=74)
    private String musicStyle;// 音乐风格

    @Protobuf(fieldType = FieldType.STRING,order=75)
    private String musicStyleName;// 音乐风格名称

    @Protobuf(fieldType = FieldType.STRING,order=76)
    private String recordCompany;// 唱片公司

    @Protobuf(fieldType = FieldType.STRING,order=77)
    private String issueCompany;// 发行公司

    @Protobuf(fieldType = FieldType.STRING,order=78)
    private String musicCompose;// 音乐作曲者

    @Protobuf(fieldType = FieldType.INT32,order=79)
    private Integer programStyle;// 项目分类

    @Protobuf(fieldType = FieldType.STRING,order=80)
    private String programStyleName;// 项目分类名称

    @Protobuf(fieldType = FieldType.STRING,order=81)
    private String playTvName;// 播出电视台名称 可能有多个电视台

    @Protobuf(fieldType = FieldType.STRING,order=82)
    private String playTv;// 播出电视台 可能有多个电视台

    @Protobuf(fieldType = FieldType.STRING,order=83)
    private String supervise;// 动漫监督

    @Protobuf(fieldType = FieldType.STRING,order=84)
    private String originator;// 原作

    @Protobuf(fieldType = FieldType.STRING,order=85)
    private String recreationType;// 娱乐频道 娱乐分类 原娱乐频道的专辑类型

    @Protobuf(fieldType = FieldType.STRING,order=86)
    private String recreationTypeName;// 娱乐频道 娱乐分类 原娱乐频道的专辑类型

    @Protobuf(fieldType = FieldType.STRING,order=87)
    private String carfilmType;// 汽车频道的专辑类型

    @Protobuf(fieldType = FieldType.STRING,order=88)
    private String carfilmTypeName;// 汽车频道的专辑类型

    @Protobuf(fieldType = FieldType.STRING,order=89)
    private String firstPlayTime;//

    @Protobuf(fieldType = FieldType.STRING,order=90)
    private String school;// 公开课频道 学校字段

    @Protobuf(fieldType = FieldType.INT32,order=91)
    private Integer isFollow;// 是否跟播

    @Protobuf(fieldType = FieldType.STRING,order=92)
    private String playStreams;// 专辑所有的码流

    @Protobuf(fieldType = FieldType.INT32,order=93)
    private Integer isyuanxian;// 是否网络院线:1是 0否

    @Protobuf(fieldType = FieldType.INT64,order=94)
    private Long createTime;// 创建时间

    @Protobuf(fieldType = FieldType.INT64,order=95)
    private Long updateTime;// 修改时间

    @Protobuf(fieldType = FieldType.STRING,order=96)
    private String style;// 对应letvMakeStyle、popStyle、letvProduceStyle、soprtsType和filmBaseType

    @Protobuf(fieldType = FieldType.STRING,order=97)
    private String styleName;// 相应的名称

    @Protobuf(fieldType = FieldType.INT32,order=98)
    private Integer recommLevel;// 推荐指数

    @Protobuf(fieldType = FieldType.INT32,order=99)
    private Integer copyrightType;// 版权类型

    @Protobuf(fieldType = FieldType.STRING,order=100)
    private String copyrightTypeName;// 版权名称

    @Protobuf(fieldType = FieldType.STRING,order=101)
    private String copyrightStart;// 版权开始时间

    @Protobuf(fieldType = FieldType.STRING,order=102)
    private String copyrightEnd;// 版权结束时间

    @Protobuf(fieldType = FieldType.STRING,order=103)
    private String copyrightCompany;// 版权公司

    @Protobuf(fieldType = FieldType.INT32,order=104)
    private Integer duration;// 时长

    @Protobuf(fieldType = FieldType.INT32,order=105)
    private Integer isHomemade;// 是否是自制剧

    @Protobuf(fieldType = FieldType.INT32,order=106)
    private Integer pushflag;// 是否TV版外跳0TV版 1Tv版外跳

    @Protobuf(fieldType = FieldType.INT32,order=107)
    private Integer attr;// 1正片

    @Protobuf(fieldType = FieldType.STRING,order=108)
    private String relationAlbumId;

    @Protobuf(fieldType = FieldType.STRING,order=109)
    private String nowEpisodes;// 更新至{nowEpisodes}集

    @Protobuf(fieldType = FieldType.STRING,order=110)
    private String nowIssue;// 更新至{nowIssue}期

    @Protobuf(fieldType = FieldType.STRING,order=111)
    private String relationId;// 花絮专辑关联到正片专辑

    @Protobuf(fieldType = FieldType.INT64,order=112)
    private Long videoFollowTime;

    @Protobuf(fieldType = FieldType.STRING,order=113)
    private String varietyShow;// 纪录片是不是综艺类型,1是 0否

    @Protobuf(fieldType = FieldType.STRING,order=114)
    private String contentRatingId;// 内容分级id

    @Protobuf(fieldType = FieldType.STRING,order=115)
    private String contentRatingValue;// 内容分级value

    @Protobuf(fieldType = FieldType.STRING,order=116)
    private String contentRatingDesc;// 内容分级描述（界面显示用）

    @Protobuf(fieldType = FieldType.INT32,order=117)
    private Integer ismobile;// 是否有移动版权 1有版权，0无版权

    @Protobuf(fieldType = FieldType.INT64,order=118)
    private Long vv;// 播放数

    @Protobuf(fieldType = FieldType.INT64,order=119)
    private Long commentCnt;// 评论数

    @Protobuf(fieldType = FieldType.INT32,order=120)
    private Integer isPlayMark;// 是否是独播，1是，0否
    /**
     * 视频码流code列表
     */

    @Protobuf(fieldType = FieldType.STRING,order=121)
    private List<String> videoStreams;

    /**
     * 视频播放码流对象列表
     */

    @Protobuf(fieldType = FieldType.OBJECT,order=122)
    private List<Stream> streams;
    /**
     * 单个音频列表
     */

    @Protobuf(fieldType = FieldType.OBJECT,order=123)
    private List<MusicCache> audioInfoObj;
    /**
     * 音乐专辑列表
     */

    @Protobuf(fieldType = FieldType.OBJECT,order=124)
    private List<OstCache> ost;
    /**
     * 明星列表
     */

    @Protobuf(fieldType = FieldType.OBJECT,order=125)
    private List<ActorCache> starList;
    /**
     * 乐词列表
     */

    @Protobuf(fieldType = FieldType.OBJECT,order=126)
    private List<HotWordsCache> hotWords;


    @Protobuf(fieldType = FieldType.INT32,order=127)
    private Integer isDanmaku;// 是否弹幕 1为是，0为否。优先级为视频弹幕设置》专辑弹幕设置》频道弹幕设置


    @Protobuf(fieldType = FieldType.STRING,order=128)
    private String pic;// 专辑图片前缀


    @Protobuf(fieldType = FieldType.INT64,order=129)
    private List<Long> hotWordIds;

    @Protobuf(fieldType = FieldType.INT64,order=130)
    private List<Long> starIds;

    @Protobuf(fieldType = FieldType.INT64,order=131)
    private List<Long> directoryIds;// 导演

    @Protobuf(fieldType = FieldType.INT64,order=132)
    private List<Long> starringIds;// 主演、固定嘉宾

    @Protobuf(fieldType = FieldType.INT64,order=133)
    private List<Long> compereIds;// 主持人

    /*电视特有增加*/
    //todo:下面新增的缓存都没有改
    @Protobuf(fieldType = FieldType.INT32,order=134)
    private Integer cntv;// 1:cntv上线 0 cntv下线

    @Protobuf(fieldType = FieldType.INT32,order=135)
    private Integer cibn;// 国广

    @Protobuf(fieldType = FieldType.INT32,order=136)
    private Integer wasu;// 华数
    
    @Protobuf(fieldType = FieldType.STRING,order=137)
    private String positiveStreams;//正片码流
    
    @Protobuf(fieldType = FieldType.STRING,order=138)
    private String nonPositiveStreams;//正片码流
    
    @Protobuf(fieldType = FieldType.STRING,order=139)
    private String coopPlatform;//合作平台

    public Album() {
    }

    public Album(JsonObject json) {
        if(json != null){
            if(json.getValue("_id") != null && StringUtils.isNotBlank((String)json.getValue("_id"))){
                json.put("key",(String)json.getValue("_id"));
            }
            AlbumConverter.fromJson(json, this);
        }
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        AlbumConverter.toJson(this, json);
        return json;
    }

    public JsonObject toDocumentJson() {
        JsonObject json = new JsonObject();
        AlbumConverter.toJson(this, json);
        json.put("_id",this.key);
        return json;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBaseType() {
        return baseType;
    }

    public void setBaseType(Integer baseType) {
        this.baseType = baseType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
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

    public String getSecondCate() {
        return secondCate;
    }

    public void setSecondCate(String secondCate) {
        this.secondCate = secondCate;
    }

    public String getThirdCate() {
        return thirdCate;
    }

    public void setThirdCate(String thirdCate) {
        this.thirdCate = thirdCate;
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

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
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

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public Integer getForder() {
        return forder;
    }

    public void setForder(Integer forder) {
        this.forder = forder;
    }

    public Integer getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(Integer isEnd) {
        this.isEnd = isEnd;
    }

    public Integer getFollownum() {
        return follownum;
    }

    public void setFollownum(Integer follownum) {
        this.follownum = follownum;
    }

    public Integer getIsHeight() {
        return isHeight;
    }

    public void setIsHeight(Integer isHeight) {
        this.isHeight = isHeight;
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLetvReleaseDate() {
        return letvReleaseDate;
    }

    public void setLetvReleaseDate(String letvReleaseDate) {
        this.letvReleaseDate = letvReleaseDate;
    }

    public Integer getEpisode() {
        return episode;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public String getPlayStatus() {
        return playStatus;
    }

    public void setPlayStatus(String playStatus) {
        this.playStatus = playStatus;
    }

    public String getMakerCompany() {
        return makerCompany;
    }

    public void setMakerCompany(String makerCompany) {
        this.makerCompany = makerCompany;
    }

    public Integer getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(Integer directoryId) {
        this.directoryId = directoryId;
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

    public String getStarring() {
        return starring;
    }

    public void setStarring(String starring) {
        this.starring = starring;
    }

    public List<ActorCache> getStarringObj() {
        return starringObj;
    }

    public void setStarringObj(List<ActorCache> starringObj) {
        this.starringObj = starringObj;
    }

    public String getStarringPlay() {
        return starringPlay;
    }

    public void setStarringPlay(String starringPlay) {
        this.starringPlay = starringPlay;
    }

    public List<PicAll> getPicCollections() {
        return picCollections;
    }

    public void setPicCollections(List<PicAll> picCollections) {
        this.picCollections = picCollections;
    }

    public String getPicOriginal() {
        return picOriginal;
    }

    public void setPicOriginal(String picOriginal) {
        this.picOriginal = picOriginal;
    }

    public String getScreenWriter() {
        return screenWriter;
    }

    public void setScreenWriter(String screenWriter) {
        this.screenWriter = screenWriter;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
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

    public Integer getFilmBaseType() {
        return filmBaseType;
    }

    public void setFilmBaseType(Integer filmBaseType) {
        this.filmBaseType = filmBaseType;
    }

    public String getFilmBaseTypeName() {
        return filmBaseTypeName;
    }

    public void setFilmBaseTypeName(String filmBaseTypeName) {
        this.filmBaseTypeName = filmBaseTypeName;
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

    public String getrCompany() {
        return rCompany;
    }

    public void setrCompany(String rCompany) {
        this.rCompany = rCompany;
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

    public String getFitAge() {
        return fitAge;
    }

    public void setFitAge(String fitAge) {
        this.fitAge = fitAge;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getDub() {
        return dub;
    }

    public void setDub(String dub) {
        this.dub = dub;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getCompere() {
        return compere;
    }

    public void setCompere(String compere) {
        this.compere = compere;
    }

    public List<ActorCache> getCompereObj() {
        return compereObj;
    }

    public void setCompereObj(List<ActorCache> compereObj) {
        this.compereObj = compereObj;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getMusic_authors() {
        return music_authors;
    }

    public void setMusic_authors(String music_authors) {
        this.music_authors = music_authors;
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

    public String getRecordCompany() {
        return recordCompany;
    }

    public void setRecordCompany(String recordCompany) {
        this.recordCompany = recordCompany;
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

    public Integer getProgramStyle() {
        return programStyle;
    }

    public void setProgramStyle(Integer programStyle) {
        this.programStyle = programStyle;
    }

    public String getProgramStyleName() {
        return programStyleName;
    }

    public void setProgramStyleName(String programStyleName) {
        this.programStyleName = programStyleName;
    }

    public String getPlayTvName() {
        return playTvName;
    }

    public void setPlayTvName(String playTvName) {
        this.playTvName = playTvName;
    }

    public String getPlayTv() {
        return playTv;
    }

    public void setPlayTv(String playTv) {
        this.playTv = playTv;
    }

    public String getSupervise() {
        return supervise;
    }

    public void setSupervise(String supervise) {
        this.supervise = supervise;
    }

    public String getOriginator() {
        return originator;
    }

    public void setOriginator(String originator) {
        this.originator = originator;
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

    public String getFirstPlayTime() {
        return firstPlayTime;
    }

    public void setFirstPlayTime(String firstPlayTime) {
        this.firstPlayTime = firstPlayTime;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Integer getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(Integer isFollow) {
        this.isFollow = isFollow;
    }

    public String getPlayStreams() {
        return playStreams;
    }

    public void setPlayStreams(String playStreams) {
        this.playStreams = playStreams;
    }

    public Integer getIsyuanxian() {
        return isyuanxian;
    }

    public void setIsyuanxian(Integer isyuanxian) {
        this.isyuanxian = isyuanxian;
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

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public Integer getRecommLevel() {
        return recommLevel;
    }

    public void setRecommLevel(Integer recommLevel) {
        this.recommLevel = recommLevel;
    }

    public Integer getCopyrightType() {
        return copyrightType;
    }

    public void setCopyrightType(Integer copyrightType) {
        this.copyrightType = copyrightType;
    }

    public String getCopyrightTypeName() {
        return copyrightTypeName;
    }

    public void setCopyrightTypeName(String copyrightTypeName) {
        this.copyrightTypeName = copyrightTypeName;
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

    public String getCopyrightCompany() {
        return copyrightCompany;
    }

    public void setCopyrightCompany(String copyrightCompany) {
        this.copyrightCompany = copyrightCompany;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getIsHomemade() {
        return isHomemade;
    }

    public void setIsHomemade(Integer isHomemade) {
        this.isHomemade = isHomemade;
    }

    public Integer getPushflag() {
        return pushflag;
    }

    public void setPushflag(Integer pushflag) {
        this.pushflag = pushflag;
    }

    public Integer getAttr() {
        return attr;
    }

    public void setAttr(Integer attr) {
        this.attr = attr;
    }

    public String getRelationAlbumId() {
        return relationAlbumId;
    }

    public void setRelationAlbumId(String relationAlbumId) {
        this.relationAlbumId = relationAlbumId;
    }

    public String getNowEpisodes() {
        return nowEpisodes;
    }

    public void setNowEpisodes(String nowEpisodes) {
        this.nowEpisodes = nowEpisodes;
    }

    public String getNowIssue() {
        return nowIssue;
    }

    public void setNowIssue(String nowIssue) {
        this.nowIssue = nowIssue;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public Long getVideoFollowTime() {
        return videoFollowTime;
    }

    public void setVideoFollowTime(Long videoFollowTime) {
        this.videoFollowTime = videoFollowTime;
    }

    public String getVarietyShow() {
        return varietyShow;
    }

    public void setVarietyShow(String varietyShow) {
        this.varietyShow = varietyShow;
    }

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

    public String getContentRatingDesc() {
        return contentRatingDesc;
    }

    public void setContentRatingDesc(String contentRatingDesc) {
        this.contentRatingDesc = contentRatingDesc;
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

    public Integer getIsPlayMark() {
        return isPlayMark;
    }

    public void setIsPlayMark(Integer isPlayMark) {
        this.isPlayMark = isPlayMark;
    }

    public List<String> getVideoStreams() {
        return videoStreams;
    }

    public void setVideoStreams(List<String> videoStreams) {
        this.videoStreams = videoStreams;
    }

    public List<Stream> getStreams() {
        return streams;
    }

    public void setStreams(List<Stream> streams) {
        this.streams = streams;
    }

    public List<MusicCache> getAudioInfoObj() {
        return audioInfoObj;
    }

    public void setAudioInfoObj(List<MusicCache> audioInfoObj) {
        this.audioInfoObj = audioInfoObj;
    }

    public List<OstCache> getOst() {
        return ost;
    }

    public void setOst(List<OstCache> ost) {
        this.ost = ost;
    }

    public List<ActorCache> getStarList() {
        return starList;
    }

    public void setStarList(List<ActorCache> starList) {
        this.starList = starList;
    }

    public List<HotWordsCache> getHotWords() {
        return hotWords;
    }

    public void setHotWords(List<HotWordsCache> hotWords) {
        this.hotWords = hotWords;
    }

    public Integer getIsDanmaku() {
        return isDanmaku;
    }

    public void setIsDanmaku(Integer isDanmaku) {
        this.isDanmaku = isDanmaku;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public List<Long> getHotWordIds() {
        return hotWordIds;
    }

    public void setHotWordIds(List<Long> hotWordIds) {
        this.hotWordIds = hotWordIds;
    }

    public List<Long> getStarIds() {
        return starIds;
    }

    public void setStarIds(List<Long> starIds) {
        this.starIds = starIds;
    }

    public List<Long> getDirectoryIds() {
        return directoryIds;
    }

    public void setDirectoryIds(List<Long> directoryIds) {
        this.directoryIds = directoryIds;
    }

    public List<Long> getStarringIds() {
        return starringIds;
    }

    public void setStarringIds(List<Long> starringIds) {
        this.starringIds = starringIds;
    }

    public List<Long> getCompereIds() {
        return compereIds;
    }

    public void setCompereIds(List<Long> compereIds) {
        this.compereIds = compereIds;
    }

    public Integer getCntv() {
        return cntv;
    }

    public void setCntv(Integer cntv) {
        this.cntv = cntv;
    }

    public Integer getCibn() {
        return cibn;
    }

    public void setCibn(Integer cibn) {
        this.cibn = cibn;
    }

    public Integer getWasu() {
        return wasu;
    }

    public void setWasu(Integer wasu) {
        this.wasu = wasu;
    }

    public String getPositiveStreams() {
        return positiveStreams;
    }

    public void setPositiveStreams(String positiveStreams) {
        this.positiveStreams = positiveStreams;
    }
    
    public String getNonPositiveStreams() {
        return nonPositiveStreams;
    }

    public void setNonPositiveStreams(String nonPositiveStreams) {
        this.nonPositiveStreams = nonPositiveStreams;
    }

    public String getCoopPlatform() {
        return coopPlatform;
    }

    public void setCoopPlatform(String coopPlatform) {
        this.coopPlatform = coopPlatform;
    }

    @Override
    public String toString() {
        return "Album [videoId=" + videoId + ", id=" + id
                + ", type=" + type + ", nameCn=" + nameCn + ", categoryId="
                + categoryId + ", coopPlatform=" + coopPlatform + "]";
    }


//    public String getPic(Integer width, Integer hight) {
//        if (this.picCollections == null) {
//            return "";
//        }
//        try {
//            if (picCollections != null) {
//                Map<String,String> picAll = new HashMap<>();
//                for(Video.PicAll pic:this.picCollections){
//                    picAll.put(pic.getPicKey(),pic.getPicValue());
//                }
//                return picAll.get(width + "*" + hight);
//            }
//        } catch (Exception e) {
//
//        }
//        return "";
//    }

    /**
     * 根据key值取得第一个不为空的图片
     * @return
     */
//    public String getPic(String[] keys) {
//        String p = null;
//        if (this.picCollections == null || keys == null || keys.length <= 0) {
//            return p;
//        }
//        try {
//            if (picCollections != null) {
//                Map<String,String> picAll = new HashMap<>();
//                for(Video.PicAll pic:this.picCollections){
//                    picAll.put(pic.getPicKey(),pic.getPicValue());
//                }
//                for (String key : keys) {
//                    pic = StringUtils.trimToNull(picAll.get(key));
//                    if (pic != null) {
//                        break;
//                    }
//                }
//            }
//        } catch (Exception e) {
//        }
//        return pic;
//    }
    
//    public Boolean isPositive() {
//        // 电影、电视剧有正片的概念
//        Boolean isPositive = true;
//        if (this.categoryId != null && (this.categoryId == 1 || this.categoryId == 2 || this.categoryId == 11)) {
//            if (this.type == null || this.type != 180001) {
//                isPositive = false;
//            }
//        }
//        if (this.categoryId != null && this.categoryId == 19) {// 乐视制造 可以上
//                                                                 // 生肖和星座和正片
//            if (this.type == null || (this.type != 181204 && this.type != 181205 && this.type != 180001)) {
//                isPositive = false;
//            }
//        }
//        return isPositive;
//    }
    
//    public boolean isMobPay() {
//        if (this.isPay != null && this.isPay == 1 && this.payPlatform != null && payPlatform.contains("141003")) {
//            return true;
//        }
//        return false;
//    }

    /**
     * 是否TV版收费
     * @return
     */
//    public boolean isTVPay() {
//        if (this.payPlatform != null && this.payPlatform.contains("141007")) {
//            return true;
//        }
//        return false;
//    }

//    public String getSubCategoryNameForTV() {
//        String subCategoryName = this.getSubCategoryName();
//        Long albumId = this.getId();
//        if (albumId == 92849 || albumId == 95134 || albumId == 96123) {
//            subCategoryName = ("纸牌屋合集" + ",") + subCategoryName;
//        }
//        return subCategoryName;
//    }
//    public String getPic(Integer width, Integer hight) {
//        if (this.picCollections == null) {
//            return "";
//        }
//        try {
//            if (picCollections != null) {
//                Map<String,String> picAll = new HashMap<>();
//                for(Video.PicAll pic:this.picCollections){
//                    picAll.put(pic.getPicKey(),pic.getPicValue());
//                }
//                return picAll.get(width + "*" + hight);
//            }
//        } catch (Exception e) {
//
//        }
//        return "";
//    }
//    
//    public Boolean isPositive() {
//        // 电影、电视剧有正片的概念
//        Boolean isPositive = true;
//        if (this.categoryId != null && (this.categoryId == 1 || this.categoryId == 2)) {
//            if (this.type == null || this.type != 180001) {
//                isPositive = false;
//            }
//        }
//        if (this.categoryId != null && this.categoryId == 19) {// 乐视制造 可以上
//                                                                 // 生肖和星座和正片
//            if (this.type == null || (this.type != 181204 && this.type != 181205 && this.type != 180001)) {
//                isPositive = false;
//            }
//        }
//        return isPositive;
//    }
//    
//    public boolean isMobPay() {
//        if (this.isPay != null && this.isPay == 1 && this.payPlatform != null && payPlatform.contains("141003")) {
//            return true;
//        }
//        return false;
//    }
    
}
