package com.fanyl.c3p0;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionPool {

	private static ComboPooledDataSource ds;
	private static ConnectionPool pool;

	private ConnectionPool() {
		ds = new ComboPooledDataSource();
		try {
			ds.setDriverClass(C3P0Data.driverClass);
			ds.setJdbcUrl(C3P0Data.jdbcUrl);
			ds.setUser(C3P0Data.user);
			ds.setPassword(C3P0Data.password);
			ds.setInitialPoolSize(C3P0Data.initialPoolSize);
			ds.setMinPoolSize(C3P0Data.minPoolSize);
			ds.setMaxPoolSize(C3P0Data.maxPoolSize);
			ds.setAcquireIncrement(C3P0Data.acquireIncrement);
			ds.setMaxIdleTime(C3P0Data.maxIdleTime);
			ds.setAcquireRetryAttempts(C3P0Data.acquireRetryAttempts);
			ds.setAcquireRetryDelay(C3P0Data.acquireRetryDelay);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public static ConnectionPool getInstance() {
		if (pool == null) {
			pool = new ConnectionPool();
		}
		return pool;
	}

	public synchronized final Connection getConnection() {
		try {
			Connection conn = ds.getConnection();
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
