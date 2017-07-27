package util;


import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

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
	private static String[] resources={"/push-config.properties","/activeMQ.properties","/mysql.properties","/redis.properties"};
	public static Properties multiProp;
	public static Properties singleProp;
	
	static{
		multiProp=loadMultiProperties();
		singleProp=loadStreamProperties();
	}


	/**
	 *
	 * @return
	 */
	public static Properties loadStreamProperties() {

		if(singleProp!=null){
			return singleProp;
		}

		String config = System.getProperty("push.config", PUSH_CONFIG) ;

		singleProp=new Properties();
		InputStream is = null;

		try {
			is = PropertiesLoaderUtils.class.getResourceAsStream(config);
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
