package util;


import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//import org.apache.log4j.Logger;


/**
 * @author  
 *
 */
public final class PropertiesLoaderUtils {

	private static Logger logger = LoggerFactory.getLogger(PropertiesLoaderUtils.class);
	private static String PUSH_CONFIG = "/push-config.properties";

	public static String get(String key){
		if(StringUtils.isBlank(key)){
			return null;
		}
		return (String)loadStreamProperties(PUSH_CONFIG).get(key);
	}

	/**
	 * read properties
	 * @param resourceName "/"
	 * @return Properties
	 */
	public static Properties loadStreamProperties(String resourceName) {

		Properties props = new Properties();
		InputStream is = null;

		try {
			is = PropertiesLoaderUtils.class.getResourceAsStream(resourceName);
			props.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(is != null) {
				try {
					is.close();
					is = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return props;
	}
}
