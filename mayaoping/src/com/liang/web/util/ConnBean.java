package com.liang.web.util;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 * SST考勤机连接
 * @author Administrator
 *
 */
public class ConnBean {

	private static final Logger logger = Logger.getLogger(ConnBean.class);




	/**
	 * 获取食堂数据库连接
	 * @param cardArea
	 * @return
	 */
	public static Connection getConnMySql() throws Exception{
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String passwrod = "admin";
        String url = "jdbc:mysql://localhost:3306/app?useUnicode=true&characterEncoding=utf8";
        Connection conn = null;
		Class.forName(driver);
        conn = DriverManager.getConnection(url, userName, passwrod);
        return conn;
	}
	
	/**
	 * 关闭数据库连接
	 * @param rs
	 * @param stmt
	 * @param pstmt
	 * @param conn
	 */
    public static void close(
            PreparedStatement pstmt, Connection conn) {
        if (pstmt != null) {
            try {
                pstmt.close();
                pstmt = null;
            } catch (SQLException se) {
                Logger.getLogger(ConnBean.class).error(se.toString());
            }
        }
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException se) {
                Logger.getLogger(ConnBean.class).error(se.toString());
            }
        }
    }
}
