package org.kyrin.loris_framework.helper;

import java.util.HashMap;
import java.util.Map;

/**
 * Bean 助手类
 * @author kyrin
 *
 */
public class BeanHelper {

	private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();

	/**
	 * 获取	Bean Map
	 */
	public static Map<Class<?>, Object> getBeanMap() {
		return BEAN_MAP;
	}

	/**
	 * 获取	bean 实例
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		if (!BEAN_MAP.containsKey(clazz)) {
			throw new RuntimeException("can not get bean by class : " + clazz);
		}
		return (T) BEAN_MAP.get(clazz);
	}
}
