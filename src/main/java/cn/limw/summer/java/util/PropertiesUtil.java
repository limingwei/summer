package cn.limw.summer.java.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;

/**
 * @author li
 * @version 1 (2014年12月1日 下午4:48:22)
 * @since Java7
 */
public class PropertiesUtil {
    public static void load(Properties properties, InputStream inputStream) {
        try {
            properties.load(new BufferedReader(new InputStreamReader(inputStream)));
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Properties load(InputStream inputStream) {
        Properties properties = new Properties();
        load(properties, inputStream);
        return properties;
    }

    public static Properties fromMapToProperties(Map map) {
        Properties properties = new Properties();
        properties.putAll(map);
        return properties;
    }
}