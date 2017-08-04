package constant;

public class ConnectionConsts {

	//配置文件路径, 启动时需要在VM参数配置，默认dev
	public static String ENV_PATH = System.getProperty("profiles.path", "dev");

	public static String ACTIVEMQ_SERVER_URL = "active.server.url";
	
	public static String ACTIVE_SERVER_PORT = "active.server.port";

	public static String ACTIVE_QUEUE_TOPIC = "ylf";

	/**
	 * SOCKET推送地址
	 */
	public static String SOCKET_HOSTS = "SOCKET_HOSTS";

	public static String JDBC_CONFIG_PATH = "jdbc.json";

	public static String REDIS_CONFIG_PATH = "redis.json";

	public static String MQ_CONFIG_PATH = "activemq.properties";


}
