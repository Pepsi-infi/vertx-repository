package domain;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.io.Serializable;

/**
 *  消息体
 * @author yanglf
 *
 */
@DataObject(generateConverter = true)
public class MsgRecord extends BaseModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -593071374368333868L;
	private String amqpMsgId;//消费后生成的msgId
	private String msgBody;//消息体内容  json格式的字符串
	private int status;//消息状态  0消费失败  1消费成功  2未知
	private Integer channel;//推送渠道PushTypeEnum.srcCode
	
	public String getAmqpMsgId() {
		return amqpMsgId;
	}
	public void setAmqpMsgId(String amqpMsgId) {
		this.amqpMsgId = amqpMsgId;
	}
	
	
	public String getMsgBody() {
		return msgBody;
	}
	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public MsgRecord() {
	}
	public MsgRecord(JsonObject json) {
		// A converter is generated to easy the conversion from and to JSON.
		MsgRecordConverter.fromJson(json,this);
	}

	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		MsgRecordConverter.toJson(this, json);
		return json;
	}
	
}
