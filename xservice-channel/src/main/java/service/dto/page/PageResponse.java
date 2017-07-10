package service.dto.page;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import utils.BaseResponse;

import java.util.List;

/**
 * Created by zhushenghao1 on 16/12/1.
 * 基于index的分页Response
 */
@DataObject(generateConverter = true,inheritConverter = true)
public class PageResponse extends BaseResponse{
    private List<ChannelList> data;
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

    public PageResponse(){}
    public PageResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        PageResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        PageResponseConverter.toJson(this, json);
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

    public List<ChannelList> getData() {
        return data;
    }

    public void setData(List<ChannelList> data) {
        this.data = data;
    }

    public void addData(List<ChannelList> data) {
        if(this.data==null){
            this.data=data;
        }
        else {
            this.data.addAll(data);
        }
    }
}
