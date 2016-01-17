package org.kyrin.loris_framework.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.log4j.Logger;

/**
 * 数据库操作类
 * @author kyrin
 *
 */
public class DatabaseHelper {

	private static Logger logger = Logger.getLogger(DatabaseHelper.class);

	private static final QueryRunner QUERY_RUNNER;
	private static final ThreadLocal<Connection> CONNS;
	private static final BasicDataSource DATA_SOURCE;

	private static String default_config = "config.properties";
	private static final String USERNAME;
	private static final String PASSWORD;
	private static final String URL;
	private static final String DRIVER;

	static {
		CONNS = new ThreadLocal<Connection>();
		QUERY_RUNNER = new QueryRunner();

		Properties pro = PropsUtils.loadProps(default_config);
		USERNAME = PropsUtils.getString(pro, "jdbc.username");
		PASSWORD = PropsUtils.getString(pro, "jdbc.password");
		URL = PropsUtils.getString(pro, "jdbc.url");
		DRIVER = PropsUtils.getString(pro, "jdbc.driver");

		DATA_SOURCE = new BasicDataSource();
		DATA_SOURCE.setUrl(URL);
		DATA_SOURCE.setDriverClassName(DRIVER);
		DATA_SOURCE.setUsername(USERNAME);
		DATA_SOURCE.setPassword(PASSWORD);

	}

	public static Connection getConnection() {
		Connection conn = CONNS.get();
		if (conn == null) {
			try {
				conn = DATA_SOURCE.getConnection();
			} catch (SQLException e) {
				logger.error("get connection failure", e);
				throw new RuntimeException(e);
			} finally {
				CONNS.set(conn);
			}
		}
		return conn;
	}
	
	public static void beginTransaction(){
		Connection conn=getConnection();
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			logger.error("begin transaction failure",e);
			throw new RuntimeException(e);
		}finally{
			CONNS.set(conn);
		}
	}
	
	public static void commitTransaction(){
		Connection conn=getConnection();
		try {
			conn.commit();
			conn.close();
		} catch (SQLException e) {
			logger.error("commit transaction failure",e);
			throw new RuntimeException(e);
		}finally{
			CONNS.remove();
		}
	}
	
	public static void rollbackTransaction(){
		Connection conn=getConnection();
		try {
			conn.rollback();
			conn.close();
		} catch (SQLException e) {
			logger.error("rollback transaction failure",e);
			throw new RuntimeException(e);
		}finally{
			CONNS.remove();
		}
	}
	
	

	/**
	 * 查询
	 * @param entityClass
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, String... params) {
		List<T> entityList = null;
		try {
			entityList = QUERY_RUNNER.query(getConnection(), sql, new BeanListHandler<T>(entityClass), params);
		} catch (SQLException e) {
			logger.error("query entity list failure", e);
			throw new RuntimeException(e);
		}
		return entityList;
	}

	/**
	 * 查询
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
		T entity = null;
		try {
			entity = QUERY_RUNNER.query(getConnection(), sql, new BeanHandler<T>(entityClass), params);
		} catch (SQLException e) {
			logger.error("query entity failure", e);
			throw new RuntimeException(e);
		}
		return entity;
	}

	/**
	 * 通用查询
	 * @param sql
	 * @param params
	 * @return
	 */
	public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
		List<Map<String, Object>> result = null;
		try {
			result = QUERY_RUNNER.query(getConnection(), sql, new MapListHandler(), params);
		} catch (SQLException e) {
			logger.error("query entity list failure", e);
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * 通用更新
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int executeUpdate(String sql, Object... params) {
		int result = 0;
		try {
			result = QUERY_RUNNER.update(getConnection(), sql, params);
		} catch (SQLException e) {
			logger.error("execut update failure", e);
			throw new RuntimeException(e);
		}
		return result;
	}

}
