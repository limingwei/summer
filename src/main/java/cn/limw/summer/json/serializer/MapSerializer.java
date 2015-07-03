package cn.limw.summer.json.serializer;

import java.util.Map;
import java.util.Set;

import cn.limw.summer.json.JsonSerializeFilter;
import cn.limw.summer.json.JsonSerializer;
import cn.limw.summer.json.JsonSerializerPool;
import cn.limw.summer.json.JsonWriter;

/**
 * @author li
 * @version 1 (2015年7月1日 下午6:30:08)
 * @since Java7
 */
public class MapSerializer implements JsonSerializer {
    public static final MapSerializer INSTENSE = new MapSerializer();

    public void serialize(Object object, JsonWriter jsonWriter, JsonSerializeFilter serializeFilter, JsonSerializerPool jsonSerializerPool) {
        Map map = (Map) object;
        Set set = map.entrySet();
        jsonWriter.write("{");

        boolean first = true;
        for (Object entry : set) {
            JsonSerializer jsonSerializer = jsonSerializerPool.getJsonSerializer(entry);
            jsonSerializer.serialize(entry, jsonWriter, serializeFilter, jsonSerializerPool);

            if (first) {
                jsonWriter.write(",");
                first = false;
            }
        }
        jsonWriter.write("}");
    }
}