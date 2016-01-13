package org.kyrin.loris_framework.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * string 操作工具类
 * @author kyrin
 *
 */
public class StringUtil {

	/**
	 * 判断是否为空
	 */
	public static boolean isEmpty(String value) {
		if (value != null) {
			value = value.trim();
		}
		return StringUtils.isEmpty(value);
	}

	/**
	 * 判断是否不空
	 */
	public static boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}

	/**
	 * 分割
	 */
	public static String[] spliteString(String content, String separatorChar) {
		return StringUtils.split(content, separatorChar);
	}

}
