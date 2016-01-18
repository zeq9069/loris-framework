package org.kyrin.loris_framework.config;

/**
 * 配置项常量
 * @author kyrin
 *
 */
public interface ConfigConstant {

	String CONFIG_FILE = "loris.properties";

	String JDBC_DRIVER = "loris.framework.jdbc.driver";
	String JDBC_URL = "loris.framework.jdbc.url";
	String JDBC_USERNAME = "loris.framework.jdbc.username";
	String JDBC_PASSWORD = "loris.framework.jdbc.password";

	String APP_BASE_PACKAGE = "loris.framework.app.base_package";
	String APP_JSP_PATH = "loris.framework.app.jsp_path";
	String APP_ASSERT_PATH = "loris.framework.app.assert_path";

	String APP_UPLOAD_LIMIT = "loris.framework.app.upload_limit";
}
