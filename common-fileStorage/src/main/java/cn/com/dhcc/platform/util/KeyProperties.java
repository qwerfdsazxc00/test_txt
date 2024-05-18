package cn.com.dhcc.platform.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.google.common.collect.Maps;

import cn.com.dhcc.credit.platform.util.ExternalConfigPathUtil;



/**
 * 加载properties文件工具类
 * 
 * @author Jerry.chen
 * @date 2019年1月7日 重构
 */
public class KeyProperties {
	private static final String PROPERTIES_FILENAME = "config.properties";
	private static final String CONFIG_PATH_KEY = "config.path";
	private static final String FILE_ENCODEING = "UTF-8";
	
	
	private static Logger logger = LoggerFactory.getLogger(KeyProperties.class);

	private static Map<String,Properties> propertiesMap = Maps.newConcurrentMap();
	private static Object lock = new Object();

	private static Properties getProperties(String propertiesFilename) {
		if (StringUtils.isBlank(propertiesFilename)) {
			propertiesFilename = PROPERTIES_FILENAME;
		}
		Properties properties = propertiesMap.get(propertiesFilename);
		if (null == properties) {
			synchronized (lock) {
				properties = loadProperties(propertiesFilename);
				propertiesMap.put(propertiesFilename,properties);
			}
		}
		return properties;
	}

	public static String getProperty(String key) {
		Properties properties = getProperties(StringUtils.EMPTY);
		return (String) properties.get(key);
	}
	public static String getPropertyNotNull(String key) {
		Properties properties = getProperties(StringUtils.EMPTY);
		String value = (String) properties.get(key);
		if(StringUtils.isBlank(value)){
			return "";
		}
		return value;
	}
	public static String getProperty(String key,String propertiesFilename) {
		Properties properties = getProperties(propertiesFilename);
		return (String) properties.get(key);
	}
	

	/**
	 * 
	 * @return
	 * @author Jerry.chen
	 * @date 2019年1月11日
	 */
	private static Properties loadProperties(String propertiesFilename) {
		InputStream in = null;
		try {
			Properties prop = new Properties();
			/**
			 * 	根据路径获取配置路径
			 */
			String config_path_key = ExternalConfigPathUtil.getExternalConfigPath();
			String path = System.getProperty(config_path_key);
			if (null == path || (path != null && path.trim().length() == 0)) {
				PropertiesLoaderUtils.fillProperties(prop,
						new EncodedResource(new ClassPathResource(propertiesFilename), FILE_ENCODEING));
			} else {
				path = path + File.separator;
				String filePath = path + propertiesFilename;
				logger.debug("getProperties properties file path={}", filePath);
				in = new FileInputStream(filePath);
				prop.load(in);
			}
			return prop;
		} catch (Exception e) {
			logger.error("read Properties file error!!!! exception=", e);
			return null;
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("read Properties file error!!!! exception=", e);
				}
			}
		}
	}

}
