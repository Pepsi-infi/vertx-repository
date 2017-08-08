package domain;

import java.util.Date;

public class Message extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 360149094386368610L;
	private String title;
	private String content;
	private int jumpFlag;
	private int destination;
	private int status;
	private int enabled;
	private Date expireTime;
	private Date sendTime;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getJumpFlag() {
		return jumpFlag;
	}
	public void setJumpFlag(int jumpFlag) {
		this.jumpFlag = jumpFlag;
	}
	public int getDestination() {
		return destination;
	}
	public void setDestination(int destination) {
		this.destination = destination;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	
}
