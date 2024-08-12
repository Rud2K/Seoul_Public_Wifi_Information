package service;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	private static Properties properties = new Properties();
	
	static {
		try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties")) {
			if (input != null) {
				properties.load(input);
			}
		} catch (Exception e) {
			throw new RuntimeException("config.properties 파일을 불러오는데 실패했습니다: " +  e);
		}
	}
	
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
}
