package cache.dto;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.apache.commons.lang.StringUtils;

import java.util.List;

//@Document(collection="ostCache")
@DataObject(generateConverter = true,inheritConverter = true)
public class OstCache extends BaseCache {
    private static final long serialVersionUID = -364251869530404514L;
    public static String COLLECTION_NAME = "ostCache";

//    @Id
    private String key;
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    @Protobuf(fieldType = FieldType.INT64, order = 1)
    private Long id;// 音乐专辑id

    @Protobuf(fieldType = FieldType.STRING, order = 2)
    private String name;// 音乐专辑名称

    @Protobuf(fieldType = FieldType.STRING, order = 3)
    private String img;// 音乐专辑图片

    @Protobuf(fieldType = FieldType.STRING, order = 4)
    private String artist;// 艺术家

    @Protobuf(fieldType = FieldType.STRING, order = 5)
    private String type;// 数据类型

    @Protobuf(fieldType = FieldType.STRING, order = 6)
    private String description; // 描述

    @Protobuf(fieldType = FieldType.INT32, order = 7)
    private Integer xiamiId;

    @Protobuf(fieldType = FieldType.STRING, order = 8)
    private String sourceCode;

    @Protobuf(fieldType = FieldType.STRING, order = 9)
    private String sourceName;

    @Protobuf(fieldType = FieldType.INT32, order = 10)
    private Integer categoryId; // 所属频道ID

    @Protobuf(fieldType = FieldType.STRING, order = 11)
    private String categoryName; // 所属频道名称

    @Protobuf(fieldType = FieldType.STRING, order = 12)
    private String subTitle; // 专辑别名

    @Protobuf(fieldType = FieldType.STRING, order = 13)
    private String albumTypeCode; // 专辑类型，录音室专辑EP单曲

    @Protobuf(fieldType = FieldType.STRING, order = 14)
    private String albumTypeName;

    @Protobuf(fieldType = FieldType.STRING, order = 15)
    private String downloadPlatform; // 下载平台

    @Protobuf(fieldType = FieldType.STRING, order = 16)
    private String playPlatform; // 播放平台

    @Protobuf(fieldType = FieldType.STRING, order = 17)
    private String areaCode; // 国家

    @Protobuf(fieldType = FieldType.STRING, order = 18)
    private String areaName;

    @Protobuf(fieldType = FieldType.STRING, order = 19)
    private String LanguageCode;// 语言

    @Protobuf(fieldType = FieldType.STRING, order = 20)
    private String languageName;

    @Protobuf(fieldType = FieldType.STRING, order = 21)
    private String copyrightTypeCode;// 版权

    @Protobuf(fieldType = FieldType.STRING, order = 22)
    private String copyrightTypeName;

    @Protobuf(fieldType = FieldType.STRING, order = 23)
    private String releaseDate; // 发行时间

    @Protobuf(fieldType = FieldType.STRING, order = 24)
    private String issueCompany;// 发行公司

    @Protobuf(fieldType = FieldType.INT32, order = 25)
    private Integer episode; // 集数

    @Protobuf(fieldType = FieldType.OBJECT, order = 26)
    private List<PicAll> picCollections;// 图片集

    public OstCache() {
    }

    public OstCache(JsonObject json) {
        if(json != null){
            if(json.getValue("_id") != null && StringUtils.isNotBlank((String)json.getValue("_id"))){
                json.put("key",(String)json.getValue("_id"));
            }
            OstCacheConverter.fromJson(json, this);
        }
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        OstCacheConverter.toJson(this, json);
        return json;
    }

    public JsonObject toDocumentJson() {
        JsonObject json = new JsonObject();
        OstCacheConverter.toJson(this, json);
        json.put("_id",this.key);
        return json;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getXiamiId() {
        return xiamiId;
    }

    public void setXiamiId(Integer xiamiId) {
        this.xiamiId = xiamiId;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
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

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getAlbumTypeCode() {
        return albumTypeCode;
    }

    public void setAlbumTypeCode(String albumTypeCode) {
        this.albumTypeCode = albumTypeCode;
    }

    public String getAlbumTypeName() {
        return albumTypeName;
    }

    public void setAlbumTypeName(String albumTypeName) {
        this.albumTypeName = albumTypeName;
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

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getCopyrightTypeCode() {
        return copyrightTypeCode;
    }

    public void setCopyrightTypeCode(String copyrightTypeCode) {
        this.copyrightTypeCode = copyrightTypeCode;
    }

    public String getCopyrightTypeName() {
        return copyrightTypeName;
    }

    public void setCopyrightTypeName(String copyrightTypeName) {
        this.copyrightTypeName = copyrightTypeName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getIssueCompany() {
        return issueCompany;
    }

    public void setIssueCompany(String issueCompany) {
        this.issueCompany = issueCompany;
    }

    public Integer getEpisode() {
        return episode;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public List<PicAll> getPicCollections() {
        return picCollections;
    }

    public void setPicCollections(List<PicAll> picCollections) {
        this.picCollections = picCollections;
    }

}
