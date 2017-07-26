package com.message.channel.socket.bean;

import java.io.Serializable;
import java.util.Map;

/**
 *  redis 存放socket实体
 */
public class ChatMsgVO implements Serializable {

    private static final long serialVersionUID = -7170353011695601484L;

    private String from;
    private Integer to;
    private String msgTitle;
    private String msgBody;
    private Integer type;
    private long sendTime;
    
    public ChatMsgVO(){
    	this.setSendTime(System.currentTimeMillis());
    }
    public long getSendTime() {
		return sendTime;
	}

	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}

	private Map<String, Object> otherParams;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Map<String, Object> getOtherParams() {
        return otherParams;
    }

    public void setOtherParams(Map<String, Object> otherParams) {
        this.otherParams = otherParams;
    }
}
