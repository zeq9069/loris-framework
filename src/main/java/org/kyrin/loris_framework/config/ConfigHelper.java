package org.kyrin.loris_framework.config;

import java.util.Properties;

import org.kyrin.loris_framework.utils.PropsUtils;

/**
 * 属性文件助手类
 * @author kyrin
 *
 */
public class ConfigHelper {

	private static final Properties PRO = PropsUtils.loadProps(ConfigConstant.CONFIG_FILE);

	/**
	 * 获取 jdbc 驱动 
	 */
	public static String getJdbcDriver() {
		return PropsUtils.getString(PRO, ConfigConstant.JDBC_DRIVER);
	}

	/**
	 * 获取 jdbc url
	 */
	public static String getJdbcUrl() {
		return PropsUtils.getString(PRO, ConfigConstant.JDBC_URL);
	}

	/**
	 * 获取 jdbc 用户名
	 */
	public static String getJdbcUsername() {
		return PropsUtils.getString(PRO, ConfigConstant.JDBC_USERNAME);
	}

	/**
	 * 获取 jdbc 密码
	 */
	public static String getJdbcPassword() {
		return PropsUtils.getString(PRO, ConfigConstant.JDBC_PASSWORD);
	}

	/**
	 * 获取 应用基础包路径
	 */
	public static String getAppBasePackage() {
		return PropsUtils.getString(PRO, ConfigConstant.APP_BASE_PACKAGE);
	}

	/**
	 * 获取 jsp 路径
	 */
	public static String getAppJspPath() {
		return PropsUtils.getString(PRO, ConfigConstant.APP_JSP_PATH, "/WEB-INF/view");
	}

	/**
	 * 获取静态资源文件路径
	 */
	public static String getAppAssertPath() {
		return PropsUtils.getString(PRO, ConfigConstant.APP_ASSERT_PATH, "/assert/");
	}

	/**
	 * 获取上传文件大小的限制
	 */
	public static int getAppUploadLimit() {
		return PropsUtils.getInt(PRO, ConfigConstant.APP_UPLOAD_LIMIT);
	}
}
