package cn.limw.summer.java.collection.wrapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author li
 * @version 1 (2015年1月16日 上午11:07:18)
 * @since Java7
 */
public class MapWrapper implements Map, Serializable {
    private static final long serialVersionUID = -448657839504985922L;

    private Map map;

    public MapWrapper() {}

    public MapWrapper(Map map) {
        setMap(map);
    }

    public Map getMap() {
        return map;
    }

    public MapWrapper setMap(Map map) {
        this.map = map;
        return this;
    }

    public int size() {
        return getMap().size();
    }

    public boolean isEmpty() {
        return getMap().isEmpty();
    }

    public boolean containsKey(Object key) {
        return getMap().containsKey(key);
    }

    public boolean containsValue(Object value) {
        return getMap().containsValue(value);
    }

    public Object get(Object key) {
        return getMap().get(key);
    }

    public Object put(Object key, Object value) {
        return getMap().put(key, value);
    }

    public Object remove(Object key) {
        return getMap().remove(key);
    }

    public void putAll(Map m) {
        getMap().putAll(m);
    }

    public void clear() {
        getMap().clear();
    }

    public Set keySet() {
        return getMap().keySet();
    }

    public Collection values() {
        return getMap().values();
    }

    public Set entrySet() {
        return getMap().entrySet();
    }
}