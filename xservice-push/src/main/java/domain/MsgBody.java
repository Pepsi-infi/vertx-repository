package domain;

import java.io.Serializable;
import java.util.Date;

public class MsgBody implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8699727587980280720L;
	private int pushType;//1:单条推送  2:批量推送
	private int deviceType;//设备类型  1:IOS 2:ANDROID
	private int channel;//渠道  0:apns(苹果专属推送)  1:SOKCET 2:XIAOMI 3:GCM
	private String title;//标题
	private String content;//消息内容
	private String deviceNo;//设备号
	private Date createdTime;//消息创建时间 
	private Date sendTime;//消息发送时间
	public int getPushType() {
		return pushType;
	}
	public void setPushType(int pushType) {
		this.pushType = pushType;
	}
	public int getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
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
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	

}
