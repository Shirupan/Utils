/**
 * 2016.09.01
 * 深圳浩天
 * 数据库连接池
 * 需要使用ConfigManager类从资源文件读取部分参数
 */
package com.sl.java.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class ConnectionService {
	private static final String DB_BUSINESS = "business";
	private static ConnectionService instance = new ConnectionService();

	private ConnectionService() {
	}

	static {
		getInstance();
	}

	public static ConnectionService getInstance() {
		return instance;
	}

	private DataSource ds_business = setupDataSource(DB_BUSINESS);

	public synchronized Connection getConnectionForLocal() {
		try {
			return ds_business.getConnection();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	// public synchronized Connection getConnectionForLog() {
	// try {
	// return ds_islog.getConnection();
	// } catch (SQLException ex) {
	// ex.printStackTrace();
	// }
	// return null;
	// }

	/**
	 * @param initialSize
	 *            TODO
	 * @param maxActive
	 *            TODO
	 * @param maxIdle
	 *            TODO
	 * @param minIdle
	 *            TODO
	 * @return DataSource
	 */
	public static DataSource setupDataSource(String db, int initialSize, int maxActive, int maxIdle, int minIdle) {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl(ConfigManager.getConfigData(db + ".url"));
		ds.setUsername(ConfigManager.getConfigData(db + ".user"));
		ds.setPassword(ConfigManager.getConfigData(db + ".password"));
		ds.setInitialSize(initialSize);
		ds.setMaxActive(maxActive);
		ds.setMaxIdle(maxIdle);
		ds.setMinIdle(minIdle);
		ds.setMaxWait(5000);
		ds.setRemoveAbandoned(true);
		ds.setRemoveAbandonedTimeout(60);
		ds.setLogAbandoned(true);
		ds.setMinEvictableIdleTimeMillis(30 * 1000);
		ds.setTimeBetweenEvictionRunsMillis(10 * 1000);
		ds.setTestWhileIdle(true);
		ds.setTestOnBorrow(false);
		ds.setTestOnReturn(false);
		ds.setValidationQuery("select 1 from dual");
		ds.setValidationQueryTimeout(1);
		ds.setTimeBetweenEvictionRunsMillis(30000);
		ds.setNumTestsPerEvictionRun(20);
		return ds;
	}

	public static DataSource setupDataSource(String db) {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl(ConfigManager.getConfigData(db + ".url"));
		ds.setUsername(ConfigManager.getConfigData(db + ".user"));
		ds.setPassword(ConfigManager.getConfigData(db + ".password"));
		ds.setInitialSize(Integer.valueOf(ConfigManager.getConfigData(db + ".initialSize") != null
				&& ConfigManager.getConfigData(db + ".initialSize").length() > 0
						? ConfigManager.getConfigData(db + ".initialSize") : "2"));
		ds.setMaxActive(Integer.valueOf(ConfigManager.getConfigData(db + ".maxActive") != null
				&& ConfigManager.getConfigData(db + ".maxActive").length() > 0
						? ConfigManager.getConfigData(db + ".maxActive") : "10"));
		ds.setMaxIdle(Integer.valueOf(ConfigManager.getConfigData(db + ".maxIdle") != null
				&& ConfigManager.getConfigData(db + ".maxIdle").length() > 0
						? ConfigManager.getConfigData(db + ".maxIdle") : "5"));
		ds.setMinIdle(Integer.valueOf(ConfigManager.getConfigData(db + ".minIdle") != null
				&& ConfigManager.getConfigData(db + "..minIdle").length() > 0
						? ConfigManager.getConfigData(db + "..minIdle") : "2"));
		ds.setMaxWait(Long.valueOf(ConfigManager.getConfigData(db + ".maxWait") != null
				&& ConfigManager.getConfigData(db + "..maxWait").length() > 0
						? ConfigManager.getConfigData(db + "..maxWait") : "5000"));
		ds.setRemoveAbandoned(true);
		ds.setRemoveAbandonedTimeout(60);
		ds.setLogAbandoned(true);
		ds.setMinEvictableIdleTimeMillis(30 * 1000);
		ds.setTimeBetweenEvictionRunsMillis(10 * 1000);
		ds.setTestWhileIdle(true);
		ds.setTestOnBorrow(false);
		ds.setTestOnReturn(false);
		ds.setValidationQuery("select 1 from dual");
		ds.setValidationQueryTimeout(1);
		ds.setTimeBetweenEvictionRunsMillis(30000);
		ds.setNumTestsPerEvictionRun(20);
		return ds;
	}

	public static void printDataSourceStats(DataSource ds) throws SQLException {
		BasicDataSource bds = (BasicDataSource) ds;
	}

	public static void shutdownDataSource(DataSource ds) throws SQLException {
		BasicDataSource bds = (BasicDataSource) ds;
		bds.close();
	}

	public static void main(String[] args) {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		long result;
		try {
			con = ConnectionService.getInstance().getConnectionForLocal();
			ps = con.prepareStatement("select * from tb_name where x=?");
			int m = 1;
			ps.setString(m++, "");
			rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getLong("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
