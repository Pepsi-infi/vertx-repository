package service.dto;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import utils.BaseResponse;

import java.util.List;

/**
 * Created by lufei
 * Date : 2017/8/2 14:25
 * Description :
 */
@DataObject(generateConverter = true)
public class MsgStatResultPage extends BaseResponse {
    public static final int DEFAULT_PAGE_SIZE = 20;
    private List<MsgStatResultDto> data;
    //总条数
//    private int total;
    //当前页
    private int page;
    //每页多少条
    private int size = MsgStatResultPage.DEFAULT_PAGE_SIZE;

    public List<MsgStatResultDto> getData() {
        return data;
    }

    public void setData(List<MsgStatResultDto> data) {
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    public MsgStatResultPage(List<MsgStatResultDto> data, int page, int size) {
        super();
        this.data = data;
        this.page = page;
        this.size = size;
    }

    public MsgStatResultPage() {
        super();
    }

    public MsgStatResultPage(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        MsgStatResultPageConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        MsgStatResultPageConverter.toJson(this, json);
        return json;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MsgStatResultPage{");
        sb.append("data=").append(data);
        sb.append(", page=").append(page);
        sb.append(", size=").append(size);
        sb.append('}');
        return sb.toString();
    }
}
