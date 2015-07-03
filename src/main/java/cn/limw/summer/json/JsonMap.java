package cn.limw.summer.json;

import java.util.Map;

import cn.limw.summer.java.collection.wrapper.MapWrapper;
import cn.limw.summer.util.Nums;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年6月23日 上午11:54:23)
 * @since Java7
 */
@SuppressWarnings("rawtypes")
public class JsonMap extends MapWrapper {
    private static final long serialVersionUID = -8950804197202074880L;

    public JsonMap(Map map) {
        super(map);
    }

    public String getString(String key) {
        return StringUtil.toString(get(key), null);
    }

    public Integer getInteger(String key) {
        return Nums.toInt(get(key), null);
    }

    public Long getLong(String key) {
        return Nums.toLong(get(key), null);
    }
}