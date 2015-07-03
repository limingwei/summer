package cn.limw.summer.web.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author li
 * @version 1 (2014年7月18日 上午10:41:25)
 * @since Java7
 * @see org.springframework.web.util.UriUtils#decode(String, String)
 */
public class QueryStringUtil {
    public static String fromMap(Map map) {
        StringBuilder stringBuilder = new StringBuilder("?");
        if (null != map && !map.isEmpty()) {
            Set<Entry> entrySet = map.entrySet();
            for (Entry entry : entrySet) {
                Object key = entry.getKey();
                Object value = entry.getValue();
                if (null != value && value.getClass().isArray()) {
                    Object[] values = (Object[]) value;
                    for (Object each : values) {
                        stringBuilder.append(key).append('=').append(each).append('&');
                    }
                } else {
                    stringBuilder.append(key).append('=').append(value).append("&");
                }
            }
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }
}