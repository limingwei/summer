package cn.limw.summer.json.serializer;

import java.util.Map.Entry;

import cn.limw.summer.json.JsonSerializeFilter;
import cn.limw.summer.json.JsonSerializer;
import cn.limw.summer.json.JsonSerializerPool;
import cn.limw.summer.json.JsonWriter;

/**
 * @author li
 * @version 1 (2015年7月1日 下午6:30:08)
 * @since Java7
 */
public class EntrySerializer implements JsonSerializer {
    public static final EntrySerializer INSTENSE = new EntrySerializer();

    public void serialize(Object object, JsonWriter jsonWriter, JsonSerializeFilter serializeFilter, JsonSerializerPool jsonSerializerPool) {
        Entry entry = (Entry) object;
        jsonWriter.write(entry.getKey() + "");
        jsonWriter.write(":");

        Object value = entry.getValue();
        JsonSerializer jsonSerializer = jsonSerializerPool.getJsonSerializer(value);
        jsonSerializer.serialize(value, jsonWriter, serializeFilter, jsonSerializerPool);
    }
}