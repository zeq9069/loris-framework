package org.kyrin.loris_framework.utils;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 数组工具类
 * @author kyrin
 *
 */
public final class ArrayUtil {

	/**
	 * 判断数组是否为空
	 */
	public static boolean isEmpty(Object[] array) {
		return ArrayUtils.isEmpty(array);
	}

	public static boolean isNotEmpty(Object[] array) {
		return !ArrayUtils.isEmpty(array);
	}
}
