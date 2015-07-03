package cn.limw.summer.java.collection;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 固定最大size的LinkedHashMap
 * @author li
 * @version 1 (2014年8月12日 上午9:36:47)
 * @since Java7
 */
@SuppressWarnings("rawtypes")
public class FixedSizeLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
    private static final long serialVersionUID = 6862246208674868957L;

    public FixedSizeLinkedHashMap(Integer fixedSize) {
        super();
        setFixedSize(fixedSize);
    }

    private Integer fixedSize;

    public Integer getFixedSize() {
        return fixedSize;
    }

    public void setFixedSize(Integer fixedSize) {
        this.fixedSize = fixedSize;
    }

    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > getFixedSize();
    }
}