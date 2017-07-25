package com.message.util;

import org.apache.commons.lang.StringUtils;

import java.util.Properties;
import java.util.Random;

/**
 * @author
 * 
 */
public final class ApplicationProperties {
	private static final String resourceName = "sysconfig.properties";
	private static Properties property = new Properties();

	static {
		ApplicationProperties.init();
	}

	/**
	 * 初始化
	 */
	public static void init() {
		ApplicationProperties.setProperty(PropertiesLoaderUtils.loadUrlProperties(resourceName));
	}

	/**
	 * @return Properties
	 */
	public static Properties getProperty() {
		// System.out.println(property.isEmpty());
		if (property == null || property.isEmpty() || property.size() == 0) {
			ApplicationProperties.init();
		}
		return property;
	}

	public static void setProperty(Properties property) {
		ApplicationProperties.property = property;
	}

	/**
	 * 获取属性值
	 * 
	 * @param key
	 * @return String
	 */
	public static String getPropertyValue(String key) throws Exception {
		if (StringUtils.isEmpty(key)) {
			return "";
		}
		String value = System.getProperty(key);
		if(!StringUtils.isEmpty(value)) {
			return value;
		}
		return (String) ApplicationProperties.getProperty().get(key);
	}

	/**
	 * 取tcp 协议服务地址（司机端）
	 * 
	 * @return
	 */
	public static String[] tcpHost() {
		String ss = get("TCP_HOSTS");
		String[] servers = ss.split(",");
		Random random = new Random();
		int index = random.nextInt(servers.length);
		String host = servers[index];
		return host.split(":");
	}
	
	/**
	 * 取tcp 协议服务地址（乘客端）
	 * 
	 * @return
	 */
	public static String[] tcpHostPassenger() {
		String ss = get("TCP_HOSTS_PASSENGER");
		String[] servers = ss.split(",");
		Random random = new Random();
		int index = random.nextInt(servers.length);
		String host = servers[index];
		return host.split(":");
	}

	/**
	 * 取udp协议服务地址
	 * 
	 * @return
	 */
	public static String[] udpHost() {
		String ss = get("UDP_HOSTS");
		String[] servers = ss.split(",");
		Random random = new Random();
		int index = random.nextInt(servers.length);
		String host = servers[index];
		return host.split(":");
	}
	
	/**
	 * 取udp协议服务地址
	 * 
	 * @return
	 */
	public static String[] udpHostPassenger() {
		String ss = get("UDP_HOSTS_PASSENGER");
		String[] servers = ss.split(",");
		Random random = new Random();
		int index = random.nextInt(servers.length);
		String host = servers[index];
		return host.split(":");
	}

	/**
	 * 取udp协议服务地址
	 * 
	 * @return
	 */
	public static String[] socketHost() {
		String ss = get("SOCKET_HOSTS");
		String[] servers = ss.split(",");
		return servers;
	}
	
	/**
	 * 取udp协议服务地址
	 * 
	 * @return
	 */
	public static String[] socketHostPassenger() {
		String ss = get("SOCKET_HOSTS_PASSENGER");
		String[] servers = ss.split(",");
		return servers;
	}

	/**
	 * 判断当前的环境, 是否是开发环境，还是生产环境
	 * @return
	 */
	public static boolean isDev() {

		String runMode = get("runMode");
		if (!"".equalsIgnoreCase(runMode) && "formal".equalsIgnoreCase(runMode)) {
			return false;
		}
		return true;

	}

	public static String get(String key) {
		String value = "";
		try {
			value = getPropertyValue(key);
		} catch (Exception e) {
		}
		return value;
	}

    /**
     * 取分享地址
     *
     * @return
     */
    public static String shareUrl() {
        return get("SHARE_URL");
    }

    /**
     * 取分享地址，测试环境
     *
     * @return
     */
    public static String shareUrlTest() {
        return get("SHARE_URL_TEST");
    }

    /**
     * 取分享图标地址
     *
     * @return
     */
    public static String shareIcon() {
        return get("SHARE_ICON");
    }

	public static void main(String[] args) {
	}
}