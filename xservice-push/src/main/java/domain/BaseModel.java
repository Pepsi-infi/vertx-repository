package domain;



import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class BaseModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 9139456423135781964L;

    /**
     * 编号
     */
    protected long id;

    /**
     * 版本号
     */
    protected int version;

    /**
     * 新增时间
     */
    protected Date createdTime;

    /**
     * 新增人
     */
    protected String createdUser;

    /**
     * 修改时间
     */
    protected Date updatedTime;

    /**
     * 修改人
     */
    protected String updatedUser;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
