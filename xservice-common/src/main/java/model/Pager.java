package model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lufei
 * Date : 2017/9/4 16:14
 * Description :
 */
public class Pager<T extends Object> implements Serializable {

    private static final long serialVersionUID = 718863056976002588L;

    public static final int DEFAULT_PAGE_SIZE = 20;
    //总条数
    private long total;
    //当前页
    private int page;
    //每页多少条
    private int size = DEFAULT_PAGE_SIZE;

    private List<T> list;

    public Pager(long total, int page, int size, List<T> list) {
        this.total = total;
        this.page = page;
        this.size = size;
        this.list = list;
    }

    public Pager() {
    }


    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
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

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
