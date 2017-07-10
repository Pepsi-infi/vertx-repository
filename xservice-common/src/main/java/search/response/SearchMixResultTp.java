package search.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.List;


/**
 * 搜检索返回结果，结果进行mix;video,album混合排序
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@DataObject(generateConverter = true,inheritConverter = true)
public class SearchMixResultTp extends BaseResponse {

    /**
     *
     */
    private static final long serialVersionUID = 3986063294610254665L;
    private Integer data_count; // 总数
    private Integer album_count; // 专辑数
    private Integer video_count; // 视频数
    private Integer star_count; // 明星数
    private Long response_time; // 响应时间

    private List<SearchCategory> category_count_list;
    private List<SearchMixResult> data_list;

    public SearchMixResultTp() {
    }

    public SearchMixResultTp(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        SearchMixResultTpConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SearchMixResultTpConverter.toJson(this, json);
        return json;
    }

    public Integer getData_count() {
        return data_count;
    }

    public void setData_count(Integer data_count) {
        this.data_count = data_count;
    }

    public Integer getAlbum_count() {
        return album_count;
    }

    public void setAlbum_count(Integer album_count) {
        this.album_count = album_count;
    }

    public Integer getVideo_count() {
        return video_count;
    }

    public void setVideo_count(Integer video_count) {
        this.video_count = video_count;
    }

    public Integer getStar_count() {
        return star_count;
    }

    public void setStar_count(Integer star_count) {
        this.star_count = star_count;
    }

    public Long getResponse_time() {
        return response_time;
    }

    public void setResponse_time(Long response_time) {
        this.response_time = response_time;
    }

    public List<SearchMixResult> getData_list() {
        return data_list;
    }

    public void setData_list(List<SearchMixResult> data_list) {
        this.data_list = data_list;
    }

    public List<SearchCategory> getCategory_count_list() {
        return category_count_list;
    }

    public void setCategory_count_list(List<SearchCategory> category_count_list) {
        this.category_count_list = category_count_list;
    }
}
