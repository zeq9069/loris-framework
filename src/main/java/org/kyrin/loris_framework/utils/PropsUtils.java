package org.kyrin.loris_framework.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 属性工具类
 * @author kyrin
 *
 */
public class PropsUtils {

	private static Logger logger = LoggerFactory.getLogger(PropsUtils.class);

	public static Properties loadProps(String filename) {
		Properties pro = null;
		InputStream is = null;
		try {
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
			if (is == null) {
				throw new FileNotFoundException(String.format("%s fiel is not found", filename));
			}
			pro = new Properties();
			pro.load(is);
		} catch (IOException e) {
			logger.error("load properties file failure", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error("close input stream failure", e);
				}
			}
		}
		return pro;
	}

	/**
	 * 获取字符串，默认值为""
	 */
	public static String getString(Properties pro, String key) {
		return getString(pro, key, "");
	}

	/**
	 * 获取字符串，可以指定默认值
	 */
	public static String getString(Properties pro, String key, String defaultValue) {
		String value = defaultValue;
		if (pro.containsKey(key)) {
			value = pro.getProperty(key);
		}
		return value;
	}

	/**
	 * 获取整形值，默认值：0
	 */
	public static int getInt(Properties pro, String key) {
		return getInt(pro, key, 0);
	}

	/**
	 * 获取整形值，（可以指定默认直）
	 */
	public static int getInt(Properties pro, String key, int defaultValue) {
		int value = defaultValue;
		if (pro.containsKey(key)) {
			value = CastUtil.castInt(pro.getProperty(key));
		}
		return value;
	}

	/**
	 * 获取布尔值，默认值为false
	 */
	public static boolean getBoolean(Properties pro, String key) {
		return getBoolean(pro, key, false);
	}

	/**
	 * 获取布尔值 (可以指定默认值)
	 */
	public static boolean getBoolean(Properties pro, String key, boolean defaultValue) {
		boolean value = defaultValue;
		if (pro.containsKey(key)) {
			value = CastUtil.castBoolean(pro.getProperty(key));
		}
		return value;
	}
}
