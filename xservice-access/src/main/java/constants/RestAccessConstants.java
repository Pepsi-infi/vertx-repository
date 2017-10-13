package constants;

public class RestAccessConstants {

	public static final String SERVICE_NAME = "mc-access";

	public static final String SERVICE_ROOT = "/mc-access";

	public static final int HTTP_PORT = 1100;

	public static final String ONLINE_NUMBER = "/mc-access/online/number.json";

	public static final String DISPATCH = "/mc-access/user/dispatch.json";

	/**
	 * 获取TCP服务器地址 param: userTel 用户手机号
	 * 
	 */
	public static final String SERVER = "/mc-im/server.json";

	/**
	 * 默认获得该订单最新100条消息，如果timestamp存在，则获得timestamp之前100条消息
	 * 
	 * param orderNo 订单号；timestamp
	 */
	public static final String GET_OFFLINE_MESSAGE = "/mc-im/message/offline/get.json";
}
