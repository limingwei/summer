package cn.limw.summer.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import cn.limw.summer.java.collection.NiceToStringMap;

/**
 * @author li ( limingwei@mail.com )
 * @version 1 ( 2014年5月21日 下午3:39:31 )
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class Maps {
    public static Map newMap() {
        return new HashMap();
    }

    public static <K, V> String toString(Map<K, V> map) {
        Iterator<Entry<K, V>> i = map.entrySet().iterator();
        if (!i.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (;;) {
            Entry<K, V> e = i.next();
            K key = e.getKey();
            V value = e.getValue();
            sb.append(key == map ? "(this Map)" : key);
            sb.append('=');
            if (null != value && value.getClass().isArray()) {
                sb.append(ArrayUtil.toString((Object[]) value));
            } else if (null != value && value instanceof Collection) {
                sb.append(ArrayUtil.toString(((Collection) value).toArray()));
            } else {
                sb.append(value == map ? "(this Map)" : value);
            }
            if (!i.hasNext())
                return sb.append('}').toString();
            sb.append(',').append(' ');
        }
    }

    public static Boolean isEmpty(Map map) {
        return null == map || map.isEmpty();
    }

    public static Map append(Map map, Object... keyAndValues) {
        Map result = new HashMap();
        result.putAll(map);
        result.putAll(toMap(keyAndValues));
        return result;
    }

    public static Map toMap(Object... items) {
        if (null != items && items.length % 2 != 0) {
            throw new IllegalArgumentException("Count of items must be even !!!");// 个数必须为偶数,抛出异常
        } else {
            Map map = new HashMap();
            for (int i = 0; null != items && i < items.length; i = i + 2) {
                map.put(items[i], items[i + 1]);
            }
            return new NiceToStringMap(map);
        }
    }

    public static <K, V> Map<K, V> noNull(Map<K, V> map) {
        return null == map ? new HashMap<K, V>() : map;
    }

    public static Map<String, String> sortMap(final Map<String, String> map) {
        TreeMap<String, String> treeMap = new TreeMap<String, String>(new Comparator<String>() {
            public int compare(String key1, String key2) {
                return (key1 + map.get(key1)).compareTo(key2 + map.get(key2));
            }
        });
        treeMap.putAll(map);
        return treeMap;
    }

    public static <K, V> Map<K, V> select(Map<K, V> map, K... keys) {
        Map<K, V> result = new HashMap<K, V>();
        for (int i = 0; null != keys && i < keys.length; i++) {
            result.put(keys[i], map.get(keys[i]));
        }
        return result;
    }

    public static void put(Map map, String key, Object value) {
        map.put(key, value);
    }

    public static <T> Map<T, T> sameKeyValueMap(Collection<T> values) {
        Map<T, T> map = new HashMap<T, T>();
        for (T value : values) {
            map.put(value, value);
        }
        return map;
    }

    public static Map remove(Map input, String... keysToRemove) {
        for (String key : keysToRemove) {
            input.remove(key);
        }
        return input;
    }

    public static Map merge(Map... maps) {
        Map result = new HashMap();
        for (Map map : maps) {
            result.putAll(map);
        }
        return result;
    }
}