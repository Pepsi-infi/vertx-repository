package service.dto.pageCategory;

import java.util.List;
import java.util.Map;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * 分页响应类
 * @param <T>
 */
@DataObject(generateConverter = true)
public class PageCategoryResponse{

    /**
     * 总数
     */
    private Integer totalCount;

    /**
     * 当前索引
     */
    private Integer currentIndex;

    /**
     * 下一页索引
     */
    private Integer nextIndex;

    /**
     * 数据列表
     */
    private List<ChannelData> data;

    /**
     * 补充数据
     */
    private Map<String, Object> plus;

    public PageCategoryResponse() {
    }

    public PageCategoryResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        PageCategoryResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        PageCategoryResponseConverter.toJson(this, json);
        return json;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(Integer currentIndex) {
        this.currentIndex = currentIndex;
    }

    public Integer getNextIndex() {
        return nextIndex;
    }

    public void setNextIndex(Integer nextIndex) {
        this.nextIndex = nextIndex;
    }

    public Map<String, Object> getPlus() {
        return plus;
    }

    public void setPlus(Map<String, Object> plus) {
        this.plus = plus;
    }

    public List<ChannelData> getData() {
        return data;
    }

    public void setData(List<ChannelData> data) {
        this.data = data;
    }

}
