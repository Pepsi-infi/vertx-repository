package domain;

import java.io.Serializable;

/**
 *  消息体
 * @author yanglf
 *
 */
public class AmqpConsumeMessage extends BaseModel implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -593071374368333868L;
	private String amqpMsgId;//消费后生成的msgId
	private MsgBody msgBody;//消息体内容  json格式的字符串
	private int status;//消息状态  0消费失败  1消费成功  2未知
	private String channel;//推送渠道  socket  xiaomi  gcm 
	
	public String getAmqpMsgId() {
		return amqpMsgId;
	}
	public void setAmqpMsgId(String amqpMsgId) {
		this.amqpMsgId = amqpMsgId;
	}
	
	public MsgBody getMsgBody() {
		return msgBody;
	}
	public void setMsgBody(MsgBody msgBody) {
		this.msgBody = msgBody;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	
}
