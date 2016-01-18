package org.kyrin.loris_framework.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射工具类
 * @author kyrin
 *
 */
public class ReflectionUtil {

	private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

	/**
	 * 创建实例
	 */
	public static Object newInstance(Class<?> clazz) {
		Object instance;
		try {
			instance = clazz.newInstance();
		} catch (Exception e) {
			logger.error("new instance failure", e);
			throw new RuntimeException(e);
		}
		return instance;
	}

	/**
	 * 调用方法
	 */
	public static Object invokeMethod(Object obj, Method method, Object... args) {
		Object result;
		method.setAccessible(true);
		try {
			if (method.getParameterCount() == 0) {
				result = method.invoke(obj);
			} else {
				result = method.invoke(obj, args);
			}

		} catch (Exception e) {
			logger.error("invoke method failure", e);
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * 设置成员变量的值
	 */
	public static void setField(Object obj, Field field, Object value) {

		try {
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception e) {
			logger.error("set field failure", e);
			throw new RuntimeException(e);
		}
	}

}
