/**
 * 2016.09.01
 * 深圳浩天
 * 读取资源文件config.properties
 * */
package java.config;

import java.io.FileInputStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//import org.apache.log4j.Logger;

public class ConfigManager {
//	private final static Logger LOG = Logger.getLogger(ConfigManager.class);
	private static Properties prop = null;
	private static long lastModifyTime = 0;

	private ConfigManager() {
	}

	public static InputStream getResourceAsStream(String resource)
			throws IOException {
		InputStream in = null;
		ClassLoader loader = ConfigManager.class.getClassLoader();
		try {
			if (loader != null){
				in = loader.getResourceAsStream(resource);
//				LOG.debug("load config from loader.getResourceAsStream:"+resource);
			}
			if (in == null){
				in = ClassLoader.getSystemResourceAsStream(resource);
//				LOG.debug("load config from ClassLoader.getSystemResourceAsStream:"+resource);
			}
			if (in == null) {
				File file = new File(System.getProperty("user.dir") + "/"
						+ resource);
				if (file.exists()) {
					in = new FileInputStream(System.getProperty("user.dir")
							+ "/" + resource);
				}
//				LOG.debug("load config from System.getProperty(\"user.dir\"):"+System.getProperty("user.dir")
//						+ "/" + resource);
			}
			if (in == null) {
				String filePath = Thread.currentThread()
						.getContextClassLoader().getResource("").toString()
						.replaceAll("file:", "")
						+ resource;
				if (filePath.indexOf(":") == 2)
					filePath = filePath.substring(1, filePath.length());
				File file = new File(filePath);
				if (file.exists()) {
					in = new FileInputStream(filePath);
				}
//				LOG.debug("load config from filePath:"+filePath);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (in == null)
			throw new IOException("Could not find resource " + resource);
		return in;
	}

	private static void init(String filePath) {

		prop = new Properties();
		try {
			prop.load(getResourceAsStream("config.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getProperty(String key) {
		String result = "";
		if (prop == null) {
			init("");
		}
		try {
			if (prop.containsKey(key)) {
				result = prop.getProperty(key);
			}
		} catch (Exception exce) {
			exce.printStackTrace();
		}
		return result;
	}

	public static String getConfigData(String key) {
		return getProperty(key);
	}

	public static String getConfigData(String key, String defaultValue) {
		return getProperty(key).length() == 0 ? defaultValue : getProperty(key);
	}
}
