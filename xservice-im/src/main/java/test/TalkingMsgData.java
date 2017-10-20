package test;

/**
 * Created by xuhao on 2017/8/30.
 */
public class TalkingMsgData {
	/**
	 * 消息来源用户手机号码
	 */
	private String fromTel = "";
	/**
	 * 消息到达用户手机号码
	 */
	private String toTel = "";
	/**
	 * 消息ID
	 * <p>
	 * 主动发送至服务端,此ID本地生成,被动接收消息时,此ID服务端生成
	 */
	private String msgId = "";
	/**
	 * 场景ID
	 * <p>
	 * 例如:mSeneType==订单,该字段为订单号
	 */
	private String sceneId = "";
	/**
	 * 场景类型
	 * <p>
	 * 例如:订单,该字段决定mSeneID的数据类型
	 */
	private String sceneType = "";
	/**
	 * 消息类型
	 * <p>
	 * 1文本 2语音，content为语音下载地址 3定位 4图片 5视频
	 */
	private int msgType = -1;
	/**
	 * 消息内容
	 * <p>
	 * 消息内容为String格式
	 */
	private String content;
	/**
	 * 时间戳(毫秒单位)
	 */
	private long timeStamp = 0;

	public String getFromTel() {
		return fromTel;
	}

	public void setFromTel(String fromTel) {
		this.fromTel = fromTel;
	}

	public String getToTel() {
		return toTel;
	}

	public void setToTel(String toTel) {
		this.toTel = toTel;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getSceneId() {
		return sceneId;
	}

	public void setSceneId(String sceneId) {
		this.sceneId = sceneId;
	}

	public String getSceneType() {
		return sceneType;
	}

	public void setSceneType(String sceneType) {
		this.sceneType = sceneType;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

}
