package cn.limw.summer.json.serializer;

import cn.limw.summer.json.JsonSerializeFilter;
import cn.limw.summer.json.JsonSerializer;
import cn.limw.summer.json.JsonSerializerPool;
import cn.limw.summer.json.JsonWriter;

/**
 * @author li
 * @version 1 (2015年7月1日 下午6:30:08)
 * @since Java7
 */
public class ArraySerializer implements JsonSerializer {
    public static final ArraySerializer INSTENSE = new ArraySerializer();

    public void serialize(Object object, JsonWriter jsonWriter, JsonSerializeFilter serializeFilter, JsonSerializerPool jsonSerializerPool) {
        jsonWriter.write("[");
        Object[] array = (Object[]) object;
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                jsonWriter.write(",");
            }
            JsonSerializer jsonSerializer = jsonSerializerPool.getJsonSerializer(array[i]);
            jsonSerializer.serialize(array[i], jsonWriter, serializeFilter, jsonSerializerPool);
        }
        jsonWriter.write("]");
    }
}