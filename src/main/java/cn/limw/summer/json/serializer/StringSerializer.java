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
public class StringSerializer implements JsonSerializer {
    public static final StringSerializer INSTENSE = new StringSerializer();

    public void serialize(Object object, JsonWriter jsonWriter, JsonSerializeFilter serializeFilter, JsonSerializerPool jsonSerializerPool) {
        jsonWriter.write((String) object);
    }
}