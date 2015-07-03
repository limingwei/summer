package cn.limw.summer.json;

import cn.limw.summer.json.serializer.filter.NullJsonSerializeFilter;
import cn.limw.summer.json.serializer.pool.DefaultJsonSerializerPool;
import cn.limw.summer.json.writer.StringJsonWriter;
import cn.limw.summer.util.Maps;

/**
 * @author li
 * @version 1 (2015年7月1日 下午6:32:29)
 * @since Java7
 */
public class Json {
    public static void toJson(JsonWriter jsonWriter, Object object, JsonSerializeFilter serializeFilter) {
        JsonSerializerPool jsonSerializerPool = new DefaultJsonSerializerPool();
        JsonSerializer jsonSerializer = jsonSerializerPool.getJsonSerializer(object);
        jsonSerializer.serialize(object, jsonWriter, serializeFilter, jsonSerializerPool);
    }

    public static String toJson(Object object, JsonSerializeFilter serializeFilter) {
        StringJsonWriter stringJsonWriter = new StringJsonWriter();
        toJson(stringJsonWriter, object, serializeFilter);
        return stringJsonWriter.getString();
    }

    public static String toJson(Object object) {
        return toJson(object, NullJsonSerializeFilter.INSTENSE);
    }

    public static void main(String[] args) {
        System.err.println(Json.toJson(Maps.toMap("a", "1", "b", Maps.toMap("c", "2", "e", "22"), "d", new Integer[] { 1, 2, 3 })));
    }
}