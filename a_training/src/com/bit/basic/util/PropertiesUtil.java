package com.bit.basic.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 *  获取properties文件内容的工具类
 * @author zhanhk
 */
public class PropertiesUtil {
	
	//存放配置文件的所有的key-value
	public static Map<String,String> allParam = new HashMap<String, String>();
	
	/**
	 * 默认的properties文件名
	 */
	private static String PROPERTIES_NAME = "config.properties";
	
	/**
	 * 根据文件名称-key，返回相应key的值
	 * 文件名默认为config.properties，文件编码默认为UTF-8
	 * @date 2016年11月7日
	 */
    public static String getStringByKey(String key){
		return getPropertiesByKey(PROPERTIES_NAME, key, "UTF-8");
    }
    
    /**
	 * 根据文件名称-key，返回相应key的值
	 * 文件名默认为config.properties，文件编码默认为UTF-8
	 * @date 2016年11月7日
	 */
    public static int getIntegerByKey(String key) {
    	return Integer.parseInt(getPropertiesByKey(PROPERTIES_NAME, key, "UTF-8"));
    }
    
    /**
	 * 根据文件名称-key，返回相应key的值
	 * 文件名默认为config.properties，文件编码默认为UTF-8
	 * @date 2016年11月7日
	 */
    public static boolean getBooleanByKey(String key) {
    	return Boolean.parseBoolean(getPropertiesByKey(PROPERTIES_NAME, key, "UTF-8"));
    }
    
    /**
	 * 根据文件名称-key，返回相应key的值
	 * 文件名默认为config.properties，文件编码默认为UTF-8
	 * @date 2016年11月7日
	 */
    public static long getLongByKey(String key) {
    	return Long.parseLong(getPropertiesByKey(PROPERTIES_NAME, key, "UTF-8"));
    }
    
    /**
     * 根据文件名，key返回value值，文件编码默认为UTF-8
     * @date 2016年11月7日
     */
    public static String getPropertiesByKey(String fileName, String key) {
    	return getPropertiesByKey(fileName, key, "UTF-8");
    }
    
	/**
	 * 根据文件名，key，编码返回value值
	 * @date 2016年11月7日
	 */
	public static String getPropertiesByKey(String fileName, String key, String encode) {
		try {
			if (allParam.containsKey(key)) {
				return allParam.get(key);
			} else {
				InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
				// 解决中文乱码
				BufferedReader bf = new BufferedReader(new InputStreamReader(in, encode));
				Properties p = new Properties();
				p.load(bf);
				Set<Entry<Object, Object>> allKey = p.entrySet();
				for (Entry<Object, Object> entry : allKey) {
					allParam.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
				}
				in.close();
				return allParam.get(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
}
