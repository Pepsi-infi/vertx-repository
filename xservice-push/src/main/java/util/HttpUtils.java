package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class HttpUtils {

	private static Logger log = LoggerFactory.getLogger(HttpUtils.class);

	/**
	 * 定义编码格式 UTF-8
	 */
	public static final String URL_PARAM_DECODECHARSET_UTF8 = "UTF-8";

	/**
	 * 定义编码格式 GBK
	 */
	private static final String URL_PARAM_CONNECT_FLAG = "&";

	private static final String EMPTY = "";

	private static MultiThreadedHttpConnectionManager connectionManager = null;

	private static int connectionTimeOut = 60 * 1000;// 250000;

	private static int socketTimeOut = 60 * 1000;// 250000;

	private static int maxConnectionPerHost = 10;

	private static int maxTotalConnections = 10;

	private static HttpClient client;

	static {
		connectionManager = new MultiThreadedHttpConnectionManager();
		connectionManager.getParams().setConnectionTimeout(connectionTimeOut);
		connectionManager.getParams().setSoTimeout(socketTimeOut);
		connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);
		connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
		client = new HttpClient(connectionManager);
	}

	/**
	 * POST方式提交数据
	 *
	 * @param url
	 *            待请求的URL
	 * @param params
	 *            要提交的数据
	 * @param enc
	 *            编码
	 * @return 响应结果
	 * @throws java.io.IOException
	 *             IO异常
	 */
	public static String URLPost(String url, Map<String, String> params, String enc) {

		String response = EMPTY;
		PostMethod postMethod = null;
		try {
			postMethod = new PostMethod(url);
			postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
			// 将表单的值放入postMethod中
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				String value = params.get(key);
				postMethod.addParameter(key, value);
			}
			// 执行postMethod
			int statusCode = client.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
				StringBuffer stringBuffer = new StringBuffer();
				String str = "";
				while ((str = reader.readLine()) != null) {
					stringBuffer.append(str);
				}
				response = stringBuffer.toString();
			} else {
				log.error("响应状态码 = " + postMethod.getStatusCode());
			}
		} catch (HttpException e) {
			log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error("发生网络异常", e);
			e.printStackTrace();
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
				postMethod = null;
			}
		}
		return response;
	}

	/**
	 * GET方式提交数据
	 *
	 * @param url
	 *            待请求的URL
	 * @param params
	 *            要提交的数据
	 * @param enc
	 *            编码
	 * @return 响应结果
	 * @throws IOException
	 *             IO异常
	 */
	public static String URLGet(String url, Map<String, String> params, String enc) {

		String response = EMPTY;
		GetMethod getMethod = null;
		StringBuffer strtTotalURL = new StringBuffer(EMPTY);

		log.info("GET请求URL = \n" + strtTotalURL.toString());
		if (strtTotalURL.indexOf("?") == -1) {
			strtTotalURL.append(url).append("?").append(getUrl(params, enc));
		} else {
			strtTotalURL.append(url).append("&").append(getUrl(params, enc));
		}

		try {
			getMethod = new GetMethod(strtTotalURL.toString());
			getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
			// 执行getMethod
			int statusCode = client.executeMethod(getMethod);
			if (statusCode == HttpStatus.SC_OK) {
				response = getMethod.getResponseBodyAsString();
			} else {
				log.debug("响应状态码 = " + getMethod.getStatusCode());
			}
		} catch (HttpException e) {
			log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error("发生网络异常", e);
			e.printStackTrace();
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
				getMethod = null;
			}
		}
		return response;
	}

	/**
	 * 据Map生成URL字符串
	 *
	 * @param map
	 *            Map
	 * @param valueEnc
	 *            URL编码
	 * @return URL
	 */
	private static String getUrl(Map<String, String> map, String valueEnc) {

		if (null == map || map.keySet().size() == 0) {
			return (EMPTY);
		}
		StringBuffer url = new StringBuffer();
		Set<String> keys = map.keySet();
		for (Iterator<String> it = keys.iterator(); it.hasNext();) {
			String key = it.next();
			if (map.containsKey(key)) {
				String val = map.get(key);
				String str = val != null ? val : EMPTY;
				try {
					str = URLEncoder.encode(str, valueEnc);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				url.append(key).append("=").append(str).append(URL_PARAM_CONNECT_FLAG);
			}
		}
		String strURL = EMPTY;
		strURL = url.toString();
		if (URL_PARAM_CONNECT_FLAG.equals(EMPTY + strURL.charAt(strURL.length() - 1))) {
			strURL = strURL.substring(0, strURL.length() - 1);
		}
		return (strURL);
	}

	public static String post(String serverUrl, String data, long timeout) {
		StringBuilder responseBuilder = null;
		BufferedReader reader = null;
		OutputStreamWriter wr = null;

		URL url;
		try {
			url = new URL(serverUrl);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.setConnectTimeout(1000 * 5);
			wr = new OutputStreamWriter(conn.getOutputStream());

			wr.write(data);
			wr.flush();

			if (log.isDebugEnabled()) {
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				responseBuilder = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					responseBuilder.append(line + "\n");
				}
				log.debug(responseBuilder.toString());
			}
		} catch (IOException e) {
			log.error("", e);
		} finally {

			if (wr != null) {
				try {
					wr.close();
				} catch (IOException e) {
					log.error("close error", e);
				}
			}

			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					log.error("close error", e);
				}
			}

		}

		return responseBuilder.toString();
	}

	public static String httpsRequest(String requestUrl, String requestMethod, String output) {
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod(requestMethod);
			if (null != output) {
				OutputStream outputStream = connection.getOutputStream();
				outputStream.write(output.getBytes("UTF-8"));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = connection.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			connection.disconnect();
			return buffer.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "";
	}

	public static void main(String args[]) {
		System.out.println(post("http://192.168.50.91:8080/kuaidian-start/app/payment/wxpayNotify.htm",
				"fsb=y&IndexArea=product_en&CatId=&SearchText=test", 6000));
	}

	public static String doPost(String url, String reqXml) {
		PostMethod postMethod = new PostMethod(url);
		String respXml = null;
		try {
			RequestEntity entity = new StringRequestEntity(reqXml, "text/xml", "UTF-8");
			postMethod.setRequestEntity(entity);
			client.executeMethod(postMethod);
			respXml = new String(postMethod.getResponseBody(), "UTF-8");
		} catch (IOException e) {
			log.error("POST请求接口调用异常", e);
		}
		return respXml;
	}
}
