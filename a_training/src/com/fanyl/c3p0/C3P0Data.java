package com.fanyl.c3p0;

public class C3P0Data {

	public static String driverClass = PropertiesUtil.getPropertiesByKey("c3p0.properties", "c3p0.driverClass");
	public static String jdbcUrl = PropertiesUtil.getPropertiesByKey("c3p0.properties", "c3p0.jdbcUrl");
	public static String user = PropertiesUtil.getPropertiesByKey("c3p0.properties", "c3p0.user");
	public static String password = PropertiesUtil.getPropertiesByKey("c3p0.properties", "c3p0.password");
	public static int initialPoolSize = Integer
			.parseInt(PropertiesUtil.getPropertiesByKey("c3p0.properties", "c3p0.initialPoolSize"));
	public static int minPoolSize = Integer
			.parseInt(PropertiesUtil.getPropertiesByKey("c3p0.properties", "c3p0.minPoolSize"));
	public static int maxPoolSize = Integer
			.parseInt(PropertiesUtil.getPropertiesByKey("c3p0.properties", "c3p0.maxPoolSize"));
	public static int acquireIncrement = Integer
			.parseInt(PropertiesUtil.getPropertiesByKey("c3p0.properties", "c3p0.acquireIncrement"));
	public static int maxIdleTime = Integer
			.parseInt(PropertiesUtil.getPropertiesByKey("c3p0.properties", "c3p0.maxIdleTime"));
	public static int acquireRetryAttempts = Integer
			.parseInt(PropertiesUtil.getPropertiesByKey("c3p0.properties", "c3p0.acquireRetryAttempts"));
	public static int acquireRetryDelay = Integer
			.parseInt(PropertiesUtil.getPropertiesByKey("c3p0.properties", "c3p0.acquireRetryDelay"));
}
