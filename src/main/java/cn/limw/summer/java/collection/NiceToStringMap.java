package cn.limw.summer.java.collection;

import java.util.Map;

import cn.limw.summer.java.collection.wrapper.MapWrapper;
import cn.limw.summer.util.Maps;

/**
 * @author li
 * @version 1 (2015年1月16日 上午11:04:44)
 * @since Java7
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class NiceToStringMap extends MapWrapper {
    private static final long serialVersionUID = -9080826329560794873L;

    public NiceToStringMap(Map map) {
        super(map);
    }

    public String toString() {
        return Maps.toString(this);
    }
}