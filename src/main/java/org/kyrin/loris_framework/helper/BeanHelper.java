package org.kyrin.loris_framework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.kyrin.loris_framework.utils.ReflectionUtil;

/**
 * Bean 助手类
 * @author kyrin
 *
 */
public class BeanHelper {

	/**
	 * 定义 bean 映射 （bean 与 实例的映射）
	 */
	private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();

	static{
		Set<Class<?>> beanClassSet=ClassHelper.getBeanClassSet();
		for(Class<?> clazz:beanClassSet){
			Object obj=ReflectionUtil.newInstance(clazz);
			BEAN_MAP.put(clazz, obj);
		}
	}
	
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
