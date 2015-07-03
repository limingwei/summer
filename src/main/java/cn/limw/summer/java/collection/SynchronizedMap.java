package cn.limw.summer.java.collection;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import cn.limw.summer.java.collection.wrapper.MapWrapper;

/**
 * @author li
 * @version 1 (2015年6月5日 下午1:14:41)
 * @since Java7
 */
public class SynchronizedMap extends MapWrapper {
    private static final long serialVersionUID = -4890904071323291398L;

    public SynchronizedMap(Map map) {
        super(map);
    }

    public synchronized int size() {
        return super.size();
    }

    public synchronized boolean isEmpty() {
        return super.isEmpty();
    }

    public synchronized boolean containsKey(Object key) {
        return super.containsKey(key);
    }

    public synchronized boolean containsValue(Object value) {
        return super.containsValue(value);
    }

    public synchronized Object get(Object key) {
        return super.get(key);
    }

    public synchronized Object put(Object key, Object value) {
        return super.put(key, value);
    }

    public synchronized Object remove(Object key) {
        return super.remove(key);
    }

    public synchronized void putAll(Map m) {
        super.putAll(m);
    }

    public synchronized void clear() {
        super.clear();
    }

    public synchronized Set keySet() {
        return super.keySet();
    }

    public synchronized Collection values() {
        return super.values();
    }

    public synchronized Set entrySet() {
        return super.entrySet();
    }
}