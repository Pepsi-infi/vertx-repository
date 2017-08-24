package domain;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by weim on 2017/8/23.
 */
@DataObject(generateConverter = true)
public class PageBean implements Serializable{

    private static final long serialVersionUID = 123424534523452346L;
    private int page;
    private int pageSize;
    private List<PassengerMsgDto> list;

    private boolean hasNextPage;
    private Long total;

    public PageBean() {
    }

    public PageBean(int page, int pageSize, List<PassengerMsgDto> list, Long total) {
        this.page = page;
        this.pageSize = pageSize;
        this.list = list;
        this.total = total;
        if(total > page * pageSize){
            hasNextPage = true;
        }
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<PassengerMsgDto> getList() {
        return list;
    }

    public void setList(List<PassengerMsgDto> list) {
        this.list = list;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public PageBean(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        PageBeanConverter.fromJson(json,this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        PageBeanConverter.toJson(this, json);
        return json;
    }

}
