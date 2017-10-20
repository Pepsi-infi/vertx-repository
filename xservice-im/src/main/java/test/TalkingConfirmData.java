package test;

/**
 * Created by xuhao on 2017/8/30.
 */

public class TalkingConfirmData {
	/**
	 * 消息ID
	 * <p>
	 * 主动发送至服务端,此ID本地生成,被动接收消息时,此ID服务端生成
	 */
	private String msgId = "";
	/**
	 * 服务器确认该条消息的时间戳
	 * <p>
	 * 该时间戳为服务器时间,用于做排序
	 */
	private long timeStamp = 0;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

}
