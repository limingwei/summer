package cn.limw.summer.java.lang;

import java.util.Properties;

import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2014年12月23日 下午2:38:54)
 * @since Java7
 */
public class SystemUtil {
    public static Properties getProperties() {
        Properties properties = new Properties();
        properties.putAll(System.getProperties());
        return properties;
    }

    public static String getProperty(String key, String defaultValue) {
        return getProperties().getProperty(key, defaultValue);
    }

    public static Properties getEnvAndProperties() {
        Properties properties = new Properties();
        properties.putAll(System.getProperties());
        properties.putAll(System.getenv());
        return properties;
    }

    public static String getPropertyOrEnv(String key, String defaultValue) {
        return System.getProperty(key, getEnv(key, defaultValue));
    }

    private static String getEnv(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value == null) ? defaultValue : value;
    }

    public static String getOsName() {
        return getPropertyOrEnv("os.name", "not specified");
    }

    public static String getJavaVersion() {
        return getPropertyOrEnv("java.version", "not specified");
    }

    public static String getJavaHome() {
        return getPropertyOrEnv("java.home", "not specified");
    }

    public static Boolean isWindows() {
        return StringUtil.containsIgnoreCase(getOsName(), "win");
    }
}