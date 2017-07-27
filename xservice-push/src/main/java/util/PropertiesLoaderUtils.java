package util;


import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.mvel2.util.ThisLiteral;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

//import org.apache.log4j.Logger;


/**
 * @author  
 *
 */
public final class PropertiesLoaderUtils {

	private static Logger logger = LoggerFactory.getLogger(PropertiesLoaderUtils.class);
	private static String PUSH_CONFIG = "/push-config.properties";
	private static String[] resources={"/push-config.properties","/activeMQ.properties","/mysql.properties","/redis.properties"};
	public static Properties multiProp;
	public static Properties singleProp;
	
	static{
		multiProp=loadMultiProperties();
		singleProp=loadStreamProperties(PUSH_CONFIG);
	}
	

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

		if(singleProp!=null){
			return singleProp;
		}
		singleProp=new Properties();
		InputStream is = null;

		try {
			is = PropertiesLoaderUtils.class.getResourceAsStream(resourceName);
			singleProp.load(is);
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
		return singleProp;
	}
	
	public static Properties loadMultiProperties() {
		
		
		if(multiProp!=null){
			return multiProp;
		}	
		multiProp=new Properties();
		InputStream is = null;

		try {
			for(String location:resources){
				is = PropertiesLoaderUtils.class.getResourceAsStream(location);
				multiProp.load(is);
			}
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
		return multiProp;
	}
	
	public static void main(String[] args) {
		System.out.println(PropertiesLoaderUtils.multiProp.getProperty("xiaomi.packagename"));
	}
}
