package com.yuyisz.pis.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	// 数据库访问IP
	private static final String DB_IP = SystemApi.findDBInfo().split(",")[0];
	// 数据库驱动类名
	private static final String CLASS_NAME = "com.pivotal.jdbc.GreenplumDriver";
	// 数据库url
	private static final String DB_URL = "jdbc:pivotal:greenplum://" + DB_IP
			+ ":5432;DatabaseName=pis100";
	// 用户名
	private static final String NAME = SystemApi.findDBInfo().split(",")[1];
	// 密码
	private static final String PWD = SystemApi.findDBInfo().split(",")[2];

	// 加载驱动
	static {
		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// 创建数据库连接
	public static Connection getConn() {
		try {
			return DriverManager.getConnection(DB_URL, NAME, PWD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 关闭数据库连接释放资源
	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 检查某个表是否存在
	public static boolean validateTableExist(String tableName) {
		Connection conn = getConn();
		ResultSet rs = null;
		try {
			rs = conn.getMetaData().getTables(null, null, tableName, null);
			if (rs.next())
				return true;
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, null, rs);
		}
		return false;
	}
}
