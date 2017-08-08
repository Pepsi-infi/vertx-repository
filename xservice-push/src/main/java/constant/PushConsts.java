package constant;

/**
 * 消息推送常量
 */
public class PushConsts {
	
	public final static String AD_PASSENGER_MSG_PREFIX = "AD_PASSENGER_MSG";
	
	public final static String apnsToken = "apnsToken";
	
	public final static String PUSH_CHANNEL_VERTICLE_PREFIX = "PUSH_CHANNEL_VERTICLE_PREFIX_";

	public static final int XIAOMI_PASS_THROUGH_TONGZHILAN = 0;
	
	public static final int XIAOMI_PASS_THROUGH_TOUCHUAN = 1;

	public static final int XIAOMI_NOTIFY_TYPE_DEFAULT_SOUND = 1;

	// ---------------------------------------- SOCKET推送相关的常量字段 ----------------------------------------

	// 用户消息队列key（乘客）
	public static final String _MSG_LIST_PASSENGER = "MSGLIST_PASSENGER";

	//SOCKET推送，下游报文体中的method
	public static final String SOCKET_SEND_METHOD = "sendmsg";

	//SOCKET推送，params中的一个字段默认值用到
	public static final int ZERO = 0;


	// ---------------------------------------------- 上报相关常量 ---------------------------------------------

	//AppCode 乘客端1001, 司机端1002
	public static final Integer MsgStat_APPCODE_ENGER = 1001;

	//系统类型， 安卓端:1; ISO:2;
	public static final Integer MsgStat_OSTYPE_ANDROID = 1;

	//上报类型 1发送，2接收
	public static final Integer MsgStat_ACTION_SEND = 1;
}
