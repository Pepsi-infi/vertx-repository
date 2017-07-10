package cache.dto;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.apache.commons.lang.StringUtils;

import java.util.List;

//@Document(collection="musicCache")
@DataObject(generateConverter = true,inheritConverter = true)
public class MusicCache extends BaseCache {
    private static final long serialVersionUID = 5894037829693255815L;

    public static String COLLECTION_NAME = "musicCache";


    //    @Id
    private String key;
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    @Protobuf(fieldType = FieldType.STRING, order = 1)
    private String songId;

    @Protobuf(fieldType = FieldType.STRING, order = 2)
    private String songName;

    @Protobuf(fieldType = FieldType.STRING, order = 3)
    private String singerName;

    @Protobuf(fieldType = FieldType.INT32, order = 4)
    private Integer songTypeId;

    @Protobuf(fieldType = FieldType.STRING, order = 5)
    private String songTypeName;

    @Protobuf(fieldType = FieldType.STRING, order = 6)
    private String desc;

    @Protobuf(fieldType = FieldType.INT32, order = 7)
    private Integer type;

    @Protobuf(fieldType = FieldType.STRING, order = 8)
    private String xiamiId;

    @Protobuf(fieldType = FieldType.INT64, order = 9)
    private Long mid;

    @Protobuf(fieldType = FieldType.STRING, order = 10)
    private String playPlatform;// 推送平台

    @Protobuf(fieldType = FieldType.INT64, order = 11)
    private Long ostId;

    @Protobuf(fieldType = FieldType.OBJECT, order = 12)
    private List<AudioCodes> mmsAudioCodes;

    @Protobuf(fieldType = FieldType.STRING, order = 13)
    private String sourceCode;

    @Protobuf(fieldType = FieldType.STRING, order = 14)
    private String sourceName;

    @Protobuf(fieldType = FieldType.STRING, order = 15)
    private String subTitle;

    @Protobuf(fieldType = FieldType.INT32, order = 16)
    private Integer porder;

    @Protobuf(fieldType = FieldType.STRING, order = 17)
    private String downloadPlatform;

    @Protobuf(fieldType = FieldType.STRING, order = 18)
    private String releaseDate;

    @Protobuf(fieldType = FieldType.INT32, order = 19)
    private Integer duration;

    @Protobuf(fieldType = FieldType.STRING, order = 20)
    private String areaCode;

    @Protobuf(fieldType = FieldType.STRING, order = 21)
    private String areaName;

    @Protobuf(fieldType = FieldType.STRING, order = 22)
    private String languageCode;

    @Protobuf(fieldType = FieldType.STRING, order = 23)
    private String languageName;

    @Protobuf(fieldType = FieldType.STRING, order = 24)
    private String audioFile;

    @Protobuf(fieldType = FieldType.STRING, order = 25)
    private String lyricFile;

    @Protobuf(fieldType = FieldType.STRING, order = 26)
    private String compose;

    @Protobuf(fieldType = FieldType.STRING, order = 27)
    private String authors;

    @Protobuf(fieldType = FieldType.STRING, order = 28)
    private String arranger;

    @Protobuf(fieldType = FieldType.INT32, order = 39)
    private Integer compressMode;

    @Protobuf(fieldType = FieldType.STRING, order = 30)
    private String lyricText;

    @Protobuf(fieldType = FieldType.STRING, order = 31)
    private String copyrightTypeCode;

    @Protobuf(fieldType = FieldType.STRING, order = 32)
    private String copyrightTypeName;

    @Protobuf(fieldType = FieldType.STRING, order = 33)
    private String makerCompany;

    @Protobuf(fieldType = FieldType.STRING, order = 34)
    private String category;

    @Protobuf(fieldType = FieldType.STRING, order = 35)
    private String style;

    @Protobuf(fieldType = FieldType.OBJECT, order = 36)
    private MusicCache.LeId leId;

    public MusicCache() {
    }

    public MusicCache(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        if(json != null){
            if(json.getValue("_id") != null && StringUtils.isNotBlank((String)json.getValue("_id"))){
                json.put("key",(String)json.getValue("_id"));
            }
            MusicCacheConverter.fromJson(json, this);
        }
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        MusicCacheConverter.toJson(this, json);
        return json;
    }

    public JsonObject toDocumentJson() {
        JsonObject json = new JsonObject();
        MusicCacheConverter.toJson(this, json);
        json.put("_id",this.key);
        return json;
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

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Integer getPorder() {
        return porder;
    }

    public void setPorder(Integer porder) {
        this.porder = porder;
    }

    public String getDownloadPlatform() {
        return downloadPlatform;
    }

    public void setDownloadPlatform(String downloadPlatform) {
        this.downloadPlatform = downloadPlatform;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(String audioFile) {
        this.audioFile = audioFile;
    }

    public String getLyricFile() {
        return lyricFile;
    }

    public void setLyricFile(String lyricFile) {
        this.lyricFile = lyricFile;
    }

    public String getCompose() {
        return compose;
    }

    public void setCompose(String compose) {
        this.compose = compose;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getArranger() {
        return arranger;
    }

    public void setArranger(String arranger) {
        this.arranger = arranger;
    }

    public Integer getCompressMode() {
        return compressMode;
    }

    public void setCompressMode(Integer compressMode) {
        this.compressMode = compressMode;
    }

    public String getLyricText() {
        return lyricText;
    }

    public void setLyricText(String lyricText) {
        this.lyricText = lyricText;
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

    public String getMakerCompany() {
        return makerCompany;
    }

    public void setMakerCompany(String makerCompany) {
        this.makerCompany = makerCompany;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public LeId getLeId() {
        return leId;
    }

    public void setLeId(LeId leId) {
        this.leId = leId;
    }

    public List<AudioCodes> getMmsAudioCodes() {
        return mmsAudioCodes;
    }

    public void setMmsAudioCodes(List<AudioCodes> mmsAudioCodes) {
        this.mmsAudioCodes = mmsAudioCodes;
    }

    public Long getOstId() {
        return ostId;
    }

    public void setOstId(Long ostId) {
        this.ostId = ostId;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public String getPlayPlatform() {
        return playPlatform;
    }

    public void setPlayPlatform(String playPlatform) {
        this.playPlatform = playPlatform;
    }

    public Integer getSongTypeId() {
        return songTypeId;
    }

    public void setSongTypeId(Integer songTypeId) {
        this.songTypeId = songTypeId;
    }

    public String getSongTypeName() {
        return songTypeName;
    }

    public void setSongTypeName(String songTypeName) {
        this.songTypeName = songTypeName;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getXiamiId() {
        return xiamiId;
    }

    public void setXiamiId(String xiamiId) {
        this.xiamiId = xiamiId;
    }

    @Override
    public int hashCode() {
        return (songId == null) ? 0 : songId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        MusicCache m = (MusicCache) obj;
        return songId != null && m.getSongId() != null && songId.equalsIgnoreCase(m.getSongId());
    }

//    @JsonIgnore
//    public Map<String, String> getauiosCodes() {
//        Map<String, String> map = new HashMap<>();
//        if (!CollectionUtils.isEmpty(this.getMmsAudioCodes())) {
//            for (AudioCodes audioCodes : this.getMmsAudioCodes()) {
//                map.put(audioCodes.getStatus(), audioCodes.getStream());
//            }
//        }
//        return map;
//    }

    public static class LeId {
        @Protobuf(fieldType = FieldType.STRING, order = 1)
        private String leIdCommon;

        @Protobuf(fieldType = FieldType.STRING, order = 2)
        private String leIdImport;

        @Protobuf(fieldType = FieldType.STRING, order = 3)
        private String leIdSecondary;

        public String getLeIdCommon() {
            return leIdCommon;
        }

        public void setLeIdCommon(String leIdCommon) {
            this.leIdCommon = leIdCommon;
        }

        public String getLeIdImport() {
            return leIdImport;
        }

        public void setLeIdImport(String leIdImport) {
            this.leIdImport = leIdImport;
        }

        public String getLeIdSecondary() {
            return leIdSecondary;
        }

        public void setLeIdSecondary(String leIdSecondary) {
            this.leIdSecondary = leIdSecondary;
        }
    }
}
