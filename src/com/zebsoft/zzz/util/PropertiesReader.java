package com.zebsoft.zzz.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author ROC 2014年4月2日10:03:15
 *         <p>
 *         Properties文件的读取，单例模式
 *         </p>
 */

public class PropertiesReader {

	private static PropertiesReader propertiesReader = null;

	private PropertiesReader() {

	}

	public static synchronized PropertiesReader getPropertiesReader() {
		if (null == propertiesReader)
			propertiesReader = new PropertiesReader();
		return propertiesReader;
	}

	public Properties getProperties() {
		Properties properties = new Properties();
		InputStream is = null;
		try {
			is = PropertiesReader.class.getClassLoader().getResourceAsStream("conf/properties/DataMapping.properties");
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

}
