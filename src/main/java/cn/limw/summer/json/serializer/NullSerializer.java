package cn.limw.summer.json.serializer;

import cn.limw.summer.json.JsonSerializeFilter;
import cn.limw.summer.json.JsonSerializer;
import cn.limw.summer.json.JsonSerializerPool;
import cn.limw.summer.json.JsonWriter;

/**
 * @author li
 * @version 1 (2015年7月1日 下午6:52:15)
 * @since Java7
 */
public class NullSerializer implements JsonSerializer {
    public static final NullSerializer INSTENSE = new NullSerializer();

    public void serialize(Object object, JsonWriter jsonWriter, JsonSerializeFilter serializeFilter) {}

    @Override
    public void serialize(Object object, JsonWriter jsonWriter, JsonSerializeFilter serializeFilter, JsonSerializerPool jsonSerializerPool) {
        // TODO Auto-generated method stub

    }
}