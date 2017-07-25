package com.message.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	
	
	/**
	 * jackson
	 */
	private static ObjectMapper jsonMapper = new ObjectMapper();
	
	/** 
     * 把JavaBean转换为json字符串 
     *  
     * @return
     */  
	public static String toJsonString(Object obj){
		String json = "";
		try {
			json = jsonMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}  
		return json;
	}
	
	/** 
     * 使用泛型方法，把json字符串转换为相应的JavaBean对象。 
     * (1)转换为普通JavaBean：readValue(json,Student.class) 
     * (2)转换为List,如List<Student>,将第二个参数传递为Student 
     * [].class.然后使用Arrays.asList();方法把得到的数组转换为特定类型的List 
     *  
     * @param jsonStr 
     * @return
     */ 
	public static <T>T toJavaObject(String jsonStr, Class<T> clz){
		try {
			return jsonMapper.readValue(jsonStr, clz);
		} catch (Exception e) {
			e.printStackTrace();
		}  
		return null;
	}
}
