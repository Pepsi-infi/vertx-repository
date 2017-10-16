package module.c2c.protocol;

public class SQIMBody {

	/**
	 * 用户手机号
	 */
	private String userTel;

	/**
	 * 消息来源用户手机号码，必传
	 */
	private String fromTel;

	/**
	 * 身份标识， 0司机 1乘客
	 */
	private Integer identity;

	/**
	 * 消息到达用户手机号码，必传
	 */
	private String toTel;

	/**
	 * 消息ID,
	 * <p>
	 * 主动发送至服务端,此ID本地生成,被动接收消息时,此ID服务端生成
	 */
	private String msgId;

	/**
	 * 场景ID,
	 * <p>
	 * 例如:mSeneType==订单,该字段为订单号
	 */
	private String sceneId;

	/**
	 * 场景类型,
	 * <p>
	 * 例如:订单,该字段决定mSeneID的数据类型 0订单
	 */
	private Integer sceneType;

	/**
	 * 消息类型
	 * <p>
	 * ,1文本 2语音，content为语音下载地址 3定位 4图片 5视频
	 */
	private Integer msgType;

	/**
	 * 消息内容
	 * <p>
	 * ,消息内容为String格式,msgType为语音、图片、视频，该字段为下载地址。
	 */
	private String content;

	/**
	 * 时间戳(毫秒单位)
	 */
	private Long timeStamp;

	/**
	 * 经度
	 */
	private Double lon;

	/**
	 * 纬度
	 */
	private Double lat;

	/**
	 * 位置短地址
	 */
	private String sAddress;

	/**
	 * 位置长地址
	 */
	private String address;

	/**
	 * 音频 时长，单位 秒
	 */
	private Integer duration;

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

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

	public Integer getSceneType() {
		return sceneType;
	}

	public void setSceneType(Integer sceneType) {
		this.sceneType = sceneType;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public String getsAddress() {
		return sAddress;
	}

	public void setsAddress(String sAddress) {
		this.sAddress = sAddress;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getIdentity() {
		return identity;
	}

	public void setIdentity(Integer identity) {
		this.identity = identity;
	}
}
