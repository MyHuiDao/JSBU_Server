package com.huidao.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {

	/**
	 * 根据key读取value
	 * 
	 * @Title: getProperties_3 @Description: 第三种方式： 相对路径，
	 *         properties文件需在classpath目录下， 比如：config.properties在包com.test.config下，
	 *         路径就是/com/test/config/config.properties @param filePath @param
	 *         keyWord @return @return String @throws
	 */
	public static String getProperties(String filePath, String keyWord) {

		Properties prop = new Properties();
		String value = null;
		try {
			InputStream inputStream = getInputStream(filePath);
			prop.load(inputStream);
			value = prop.getProperty(keyWord);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	public static InputStream getInputStream(String filePath) {
		Map<String, InputStream> maps = new HashMap<String, InputStream>();
		InputStream inputStream = maps.get(filePath);
		if (inputStream == null) {
			maps.put(filePath, PropertiesUtil.class.getResourceAsStream(filePath));
			return PropertiesUtil.class.getResourceAsStream(filePath);
		}
		return inputStream;
	}

}
