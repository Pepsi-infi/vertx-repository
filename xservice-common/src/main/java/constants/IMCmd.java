package constants;

import java.util.HashSet;
import java.util.Set;

public class IMCmd {

	/**
	 * heart beat
	 */
	public static final int HEART_BEAT = 0;

	/**
	 * user login
	 */
	public static final int LOGIN = 1001;

	/**
	 * user logout
	 */
	public static final int LOGOUT = 1002;

	/**
	 * msg:R
	 */
	public static final int MSG_R = 2001;

	/**
	 * msg:A
	 */
	public static final int MSG_A = 2002;

	/**
	 * msg:N
	 */
	public static final int MSG_N = 2003;

	/**
	 * ack:R
	 */
	public static final int ACK_R = 2101;

	/**
	 * ack:A
	 */
	public static final int ACK_A = 2102;

	/**
	 * ack:N
	 */
	public static final int ACK_N = 2103;

	public static final int Notification = 3001;

	/**
	 * 
	 */
	public static final int POSITION_SHARE_START = 4001;

	public static final int POSITION_SHARE_END = 4002;

	public static final int POSITION_SHARE_ING = 4003;

	public static final Set<Integer> MONGO_CMD_SET = new HashSet<Integer>();
	static {
		MONGO_CMD_SET.add(MSG_R);
		MONGO_CMD_SET.add(MSG_N);
		MONGO_CMD_SET.add(POSITION_SHARE_START);
		MONGO_CMD_SET.add(POSITION_SHARE_END);
	}
}
