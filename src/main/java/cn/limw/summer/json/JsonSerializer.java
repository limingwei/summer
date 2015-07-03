package cn.limw.summer.json;

/**
 * @author li
 * @version 1 (2015年7月1日 下午6:29:49)
 * @since Java7
 */
public interface JsonSerializer {
    public void serialize(Object object, JsonWriter jsonWriter, JsonSerializeFilter serializeFilter, JsonSerializerPool jsonSerializerPool);
}