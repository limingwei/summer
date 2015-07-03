package cn.limw.summer.json.serializer.pool;

import java.util.Map;
import java.util.Map.Entry;

import cn.limw.summer.json.JsonSerializer;
import cn.limw.summer.json.JsonSerializerPool;
import cn.limw.summer.json.serializer.ArraySerializer;
import cn.limw.summer.json.serializer.EntrySerializer;
import cn.limw.summer.json.serializer.IntegerSerializer;
import cn.limw.summer.json.serializer.MapSerializer;
import cn.limw.summer.json.serializer.NullSerializer;
import cn.limw.summer.json.serializer.StringSerializer;

/**
 * @author li
 * @version 1 (2015年7月1日 下午6:45:04)
 * @since Java7
 */
public class DefaultJsonSerializerPool implements JsonSerializerPool {
    public JsonSerializer getJsonSerializer(Object object) {
        if (null == object) {
            return NullSerializer.INSTENSE;
        } else {
            if (object.getClass().isArray()) {
                return ArraySerializer.INSTENSE;
            } else if (object instanceof Map) {
                return MapSerializer.INSTENSE;
            } else if (object instanceof Entry) {
                return EntrySerializer.INSTENSE;
            } else if (object instanceof String) {
                return StringSerializer.INSTENSE;
            } else if (object instanceof Integer) {
                return IntegerSerializer.INSTENSE;
            }
        }
        throw new RuntimeException("不能解析 " + object + ", type=" + object.getClass());
    }
}