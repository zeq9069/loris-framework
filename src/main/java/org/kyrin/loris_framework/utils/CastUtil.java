package org.kyrin.loris_framework.utils;

/**
 * 转换操作工具类
 * @author kyrin
 *
 */
public class CastUtil {

	/**
	 * 转换对象为String，默认""
	 */
	public static String castString(Object obj) {
		return castString(obj, "");
	}

	/**
	 * 转换对象为String，可以指定默认值
	 */
	public static String castString(Object obj, String defaultValue) {
		return obj != null ? String.valueOf(obj) : defaultValue;
	}

	/**
	 * 转换对象为double，默认0
	 */
	public static double castDouble(Object obj) {
		return castDouble(obj, 0);
	}

	/**
	 * 转换对象为double，可以指定默认值
	 */
	public static double castDouble(Object obj, double defaultValue) {
		double value = defaultValue;
		if (obj != null) {
			String strValue = castString(obj);
			if (StringUtil.isNotEmpty(strValue)) {
				try {
					value = Double.parseDouble(strValue);
				} catch (NumberFormatException e) {
					value = defaultValue;
				}
			}
		}
		return value;
	}

	/**
	 * 转换对象为long，默认0
	 */
	public static long castLong(Object obj) {
		return castLong(obj, 0);
	}

	/**
	 * 转换对象为long，可以指定默认值
	 */
	public static long castLong(Object obj, long defaultValue) {
		long value = defaultValue;
		if (obj != null) {
			String strValue = castString(obj);
			if (StringUtil.isNotEmpty(strValue)) {
				try {
					value = Long.parseLong(strValue);
				} catch (NumberFormatException e) {
					value = defaultValue;
				}
			}
		}
		return value;
	}

	/**
	 * 转换对象为int，默认0
	 */
	public static int castInt(Object obj) {
		return castInt(obj, 0);
	}

	/**
	 * 转换对象为int，可以指定默认值
	 */
	public static int castInt(Object obj, int defaultValue) {
		int value = defaultValue;
		if (obj != null) {
			String strValue = castString(obj);
			if (StringUtil.isNotEmpty(strValue)) {
				try {
					value = Integer.parseInt(strValue);
				} catch (NumberFormatException e) {
					value = defaultValue;
				}
			}
		}
		return value;
	}

	/**
	 * 转换对象为boolean，默认false
	 */
	public static boolean castBoolean(Object obj) {
		return castBoolean(obj, false);
	}

	/**
	 * 转换对象为boolean，可以指定默认值
	 */
	public static boolean castBoolean(Object obj, boolean defaultValue) {
		boolean value = defaultValue;
		if (obj != null) {
			value = Boolean.parseBoolean(castString(obj));
		}
		return value;
	}
}
