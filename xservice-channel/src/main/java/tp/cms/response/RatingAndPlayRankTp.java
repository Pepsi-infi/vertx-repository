package tp.cms.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.io.Serializable;
import java.util.Map;

/**
 * 排行榜返回实体
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@DataObject(generateConverter = true,inheritConverter = true)
public class RatingAndPlayRankTp extends BaseResponse implements Serializable {
    private static final long serialVersionUID = 8147358656701470234L;

    private Long id;
    private String name;
    private String subname;
    private Integer cid;
    private String playcount;
    private String rating;
    @JsonProperty("url")
    private String webUrl;
    private Map<String, String> playPlatform;

    // private Map<String, String> picall;

    public RatingAndPlayRankTp() {
    }

    public RatingAndPlayRankTp(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        RatingAndPlayRankTpConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        RatingAndPlayRankTpConverter.toJson(this, json);
        return json;
    }

    public Map<String, String> getPlayPlatform() {
        return playPlatform;
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

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getPlaycount() {
        return playcount;
    }

    public void setPlaycount(String playcount) {
        this.playcount = playcount;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public void setPlayPlatform(Map<String, String> playPlatform) {
        this.playPlatform = playPlatform;
    }

    // public Map<String, String> getPicall() {
    // return picall;
    // }

    // public void setPicall(Map<String, String> picall) {
    // this.picall = picall;
    // }


    @Override
    public String toString() {
        return "RatingAndPlayRankTp{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subname='" + subname + '\'' +
                ", cid=" + cid +
                ", playcount='" + playcount + '\'' +
                ", rating='" + rating + '\'' +
                ", webUrl='" + webUrl + '\'' +
                ", playPlatform=" + playPlatform +
                '}';
    }
}
