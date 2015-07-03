package cn.limw.summer.java.collection.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author li
 * @version 1 (2015年1月23日 下午12:51:18)
 * @since Java7
 */
public class MapStatic {
    private static final Map<String, Map<String, Object>> MAPS = new HashMap<String, Map<String, Object>>();

    public static Map<String, Object> of(String name) {
        Map<String, Object> map = MAPS.get(name);
        if (null == map) {
            synchronized (MAPS) {
                map = MAPS.get(name);
                if (null == map) {
                    MAPS.put(name, map = new HashMap<String, Object>());
                }
            }
        }
        return map;
    }
}