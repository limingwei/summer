package cn.limw.summer.spring.beans.factory.json;

import java.util.Map;
import java.util.Map.Entry;

import cn.limw.summer.util.Maps;

/**
 * @author li
 * @version 1 (2014年12月11日 下午6:14:36)
 * @since Java7
 */
public class JsonToXmlBeanDefinitionUtil {
    public static String getBeanId(Entry<String, Object> entry) {
        return entry.getKey();
    }

    public static String getBeanClass(Entry<String, Object> entry) {
        return (String) ((Map<String, Object>) entry.getValue()).get("type");
    }

    public static String getProperties(Entry<String, Object> entry) {
        Map<String, Object> fields = (Map<String, Object>) ((Map<String, Object>) entry.getValue()).get("fields");
        String config = "";
        for (Entry<String, Object> each : Maps.noNull(fields).entrySet()) {
            config += " <property name=\"" + each.getKey() + "\" value=\"" + escape("" + each.getValue()) + "\"/> ";
        }
        return config;
    }

    private static String escape(String value) {
        return value.replace("&", "&amp;");
    }
}