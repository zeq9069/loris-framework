package org.kyrin.loris_framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json操作类
 * @author kyrin
 *
 */
public class JsonUtil {

	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	/**
	 * POJO to json
	 */
	public static <T> String parseString(T obj) {
		String json;
		try {
			json = OBJECT_MAPPER.writeValueAsString(obj);
		} catch (Exception e) {
			logger.error("POJO to json failure", e);
			throw new RuntimeException(e);
		}
		return json;
	}

	/**
	 * json to POJO
	 */
	public static <T> T parseObject(String json, Class<T> type) {
		T pojo;
		try {
			pojo = OBJECT_MAPPER.readValue(json, type);
		} catch (Exception e) {
			logger.error("json to pojo failure", e);
			throw new RuntimeException(e);
		}
		return pojo;
	}
}
