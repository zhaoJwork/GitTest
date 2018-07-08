package com.lin.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;


public class JsonUtil {

	/**
	 * 对象转换成JSON字符串
	 * 
	 * @param obj
	 *            需要转换的对象
	 * @return 对象的string字符
	 */
	public static String toJson(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}

	/**
	 * JSON字符串转换成对象
	 * 
	 * @param jsonString
	 *            需要转换的字符串
	 * @param type
	 *            需要转换的对象类型
	 * @return 对象
	 */
	public static <T> T fromJson(String jsonString, Class<T> type) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(jsonString, type);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
