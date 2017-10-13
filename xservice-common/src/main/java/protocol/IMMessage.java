package protocol;

public class IMMessage {

	/**
	 * 用户手机号
	 */
	public static final String key_userTel = "userTel";

	/**
	 * 消息来源用户手机号码，必传
	 */
	public static final String key_fromTel = "fromTel";

	/**
	 * 消息到达用户手机号码，必传
	 */
	public static final String key_toTel = "toTel";

	/**
	 * 消息ID,
	 * <p>
	 * 主动发送至服务端,此ID本地生成,被动接收消息时,此ID服务端生成
	 */
	public static final String key_msgId = "msgId";

	/**
	 * 场景ID,
	 * <p>
	 * 例如:mSeneType==订单,该字段为订单号
	 */
	public static final String key_sceneId = "sceneId";

	/**
	 * 场景类型,
	 * <p>
	 * 例如:订单,该字段决定mSeneID的数据类型 0订单
	 */
	public static final String key_sceneType = "sceneType";

	/**
	 * 消息类型
	 * <p>
	 * ,1文本 2语音，content为语音下载地址 3定位 4图片 5视频
	 */
	public static final String key_msgType = "msgType";

	/**
	 * 消息内容
	 * <p>
	 * ,消息内容为String格式,msgType为语音、图片、视频，该字段为下载地址。
	 */
	public static final String key_content = "content";

	/**
	 * 时间戳(毫秒单位)
	 */
	public static final String key_timeStamp = "timeStamp";

	/**
	 * 经度
	 */
	public static final String key_lon = "lon";

	/**
	 * 纬度
	 */
	public static final String key_lat = "lat";
}
