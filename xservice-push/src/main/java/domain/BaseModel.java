package domain;



import java.io.Serializable;
import java.util.Date;

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
     * 新增时间
     */
    protected Date createdTime;

    /**
     * 修改时间
     */
    protected Date updatedTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

    
}
