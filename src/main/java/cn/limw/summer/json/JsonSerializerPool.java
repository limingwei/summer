package cn.limw.summer.json;

/**
 * @author li
 * @version 1 (2015年7月1日 下午6:36:36)
 * @since Java7
 */
public interface JsonSerializerPool {
    JsonSerializer getJsonSerializer(Object object);
}