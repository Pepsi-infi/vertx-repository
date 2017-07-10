package search.response;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * 搜检索返回结果mix混排
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@DataObject(generateConverter = true,inheritConverter = true)
public class SearchMixResult extends BaseResponse {

    /**
     *
     */
    private static final long serialVersionUID = 4931558685722728998L;
    private Integer dataType;
    private Long aid;
    private Long vid;
    private Long sid;
    private String name;
    private String subname;
    private String englishName;
    private String otherName;
    private String trueName;
    private String birthday;
    private String gender;
    private String area;
    private String areaName;
    private String description;
    private String display;// 是否有关联影片
    private String isActor;
    private String isDirector;
    private String isAvail;
    private String postS1;
    private String postS2;
    private String postS3;
    private String postH1;
    private String postH2;
    private String isEnd;
    private String postOrgin;
    private String firstWord;
    private String ctime;
    private String nameQuanpin;
    private String nameJianpin;
    private String albumSrc;
    private String episodes;
    private String nowEpisodes;
    private Long playCount;
    private String url;

    private String ispay;
    private String title;
    private String display_tag;
    private Integer category;
    private String categoryName;
    private String list_order;
    private Map<String, String> images;
    private Map<String, String> pushFlag;
    private Map<String, String> directory;
    private List<Map<String, String>> starring;

    private String duration;
    private String subCategoryName;
    private String rating;
    private String releaseDate;
    private String mtime;

    private String videoType;

    public SearchMixResult() {
    }

    public SearchMixResult(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        SearchMixResultConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SearchMixResultConverter.toJson(this, json);
        return json;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public String getIspay() {
        return ispay;
    }

    public void setIspay(String ispay) {
        this.ispay = ispay;
    }

    public Map<String, String> getDirectory() {
        return directory;
    }

    public void setDirectory(Map<String, String> directory) {
        this.directory = directory;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public Long getVid() {
        return vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getIsActor() {
        return isActor;
    }

    public void setIsActor(String isActor) {
        this.isActor = isActor;
    }

    public String getIsDirector() {
        return isDirector;
    }

    public void setIsDirector(String isDirector) {
        this.isDirector = isDirector;
    }

    public String getIsAvail() {
        return isAvail;
    }

    public void setIsAvail(String isAvail) {
        this.isAvail = isAvail;
    }

    public String getPostS1() {
        return postS1;
    }

    public void setPostS1(String postS1) {
        this.postS1 = postS1;
    }

    public String getPostS2() {
        return postS2;
    }

    public void setPostS2(String postS2) {
        this.postS2 = postS2;
    }

    public String getPostS3() {
        return postS3;
    }

    public void setPostS3(String postS3) {
        this.postS3 = postS3;
    }

    public String getPostH1() {
        return postH1;
    }

    public void setPostH1(String postH1) {
        this.postH1 = postH1;
    }

    public String getPostH2() {
        return postH2;
    }

    public void setPostH2(String postH2) {
        this.postH2 = postH2;
    }

    public String getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(String isEnd) {
        this.isEnd = isEnd;
    }

    public String getPostOrgin() {
        return postOrgin;
    }

    public void setPostOrgin(String postOrgin) {
        this.postOrgin = postOrgin;
    }

    public String getFirstWord() {
        return firstWord;
    }

    public void setFirstWord(String firstWord) {
        this.firstWord = firstWord;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getNameQuanpin() {
        return nameQuanpin;
    }

    public void setNameQuanpin(String nameQuanpin) {
        this.nameQuanpin = nameQuanpin;
    }

    public String getNameJianpin() {
        return nameJianpin;
    }

    public void setNameJianpin(String nameJianpin) {
        this.nameJianpin = nameJianpin;
    }

    public String getAlbumSrc() {
        return albumSrc;
    }

    public void setAlbumSrc(String albumSrc) {
        this.albumSrc = albumSrc;
    }

    public String getEpisodes() {
        return episodes;
    }

    public void setEpisodes(String episodes) {
        this.episodes = episodes;
    }

    public String getNowEpisodes() {
        return nowEpisodes;
    }

    public void setNowEpisodes(String nowEpisodes) {
        this.nowEpisodes = nowEpisodes;
    }

    public Long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Long playCount) {
        this.playCount = playCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisplay_tag() {
        return display_tag;
    }

    public void setDisplay_tag(String display_tag) {
        this.display_tag = display_tag;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getList_order() {
        return list_order;
    }

    public void setList_order(String list_order) {
        this.list_order = list_order;
    }

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }

    public Map<String, String> getPushFlag() {
        return pushFlag;
    }

    public void setPushFlag(Map<String, String> pushFlag) {
        this.pushFlag = pushFlag;
    }

    public String getImage(int width, int height) {
        if (this.images != null && this.images.size() > 0) {
            return this.images.get(width + "*" + height);
        }
        return null;
    }

    public List<Map<String, String>> getStarring() {
        return starring;
    }

    public void setStarring(List<Map<String, String>> starring) {
        this.starring = starring;
    }

    public String getStars() {
        if (this.starring != null && this.starring.size() > 0) {
            StringBuilder sBuilder = new StringBuilder();
            for (Map<String, String> starMap : this.starring) {
                for (String star : starMap.values()) {
                    if (star != null && !"".equals(star.trim())) {
                        sBuilder.append(star).append(",");
                    }
                }
            }
            if (sBuilder.length() > 0) {
                return sBuilder.substring(0, sBuilder.length() - 1);
            } else {
                return null;
            }
        }
        return null;
    }

    public String getDirector() {
        if (this.directory != null && directory.size() > 0) {
            StringBuilder sBuilder = new StringBuilder();
            for (String star : directory.values()) {
                if (star != null && !"".equals(star.trim())) {
                    sBuilder.append(star).append(",");
                }
            }
            if (sBuilder.length() > 0) {
                return sBuilder.substring(0, sBuilder.length() - 1);
            } else {
                return null;
            }
        }
        return null;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }
}
