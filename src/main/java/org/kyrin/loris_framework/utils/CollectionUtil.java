package org.kyrin.loris_framework.utils;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

/**
 * 集合操作工具类
 * @author kyrin
 *
 */
public class CollectionUtil {

	/**
	 * 判断collection是否为空
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return CollectionUtils.isEmpty(collection);
	}

	/**
	 * 判断collection是否非空
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	/**
	 * 判断map是否为空
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return MapUtils.isEmpty(map);
	}

	/**
	 * 判断map是否非空
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}
}
