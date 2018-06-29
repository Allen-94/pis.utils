package com.yuyisz.pis.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RuntimeLogs {
	// 判断运行日志表是否存在，不存在就创建
	public static void validateLogTableExist(Connection con) {
		boolean flag = DBUtil.validateTableExist("t_runtimelogs");
		if (!flag) {
			StringBuffer sql = new StringBuffer(
					"create table t_runtimelogs(id integer NOT NULL,");
			sql.append("time timestamp without time zone,level character varying(255),service character varying(255),");
			sql.append("message text,CONSTRAINT t_runtimelogs_pkey PRIMARY KEY (id))");
			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement(sql.toString());
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBUtil.close(null, ps, null);
			}
			try {
				ps = con.prepareStatement("create sequence t_runtimelogs_seq");
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBUtil.close(null, ps, null);
			}
		}
	}

	// 添加一条日志
	public static boolean addLog(String level, String service, String message) {
		Connection conn = DBUtil.getConn();
		validateLogTableExist(conn);
		PreparedStatement ps = null;
		try {
			ps = conn
					.prepareStatement("insert into t_runtimelogs values(nextval('t_runtimelogs_seq'),now(),?,?,?)");
			ps.setString(1, level);
			ps.setString(2, service);
			ps.setString(3, message);
			int row = ps.executeUpdate();
			return row > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return false;
	}
}
