package com.zebsoft.zzz.util;

import java.util.Properties;

/**
 * 读取配置文件
 * 
 */
public class ConfigInfo {
	private static Properties cache = new Properties();
	static {
		try {
			cache.load(ConfigInfo.class.getClassLoader().getResourceAsStream(
					"conf/properties/info.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取指定key的值
	 * 
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		return cache.getProperty(key);
	}
}
