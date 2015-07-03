package cn.limw.summer.java.collection;

import java.io.Serializable;
import java.util.Iterator;

/**
 * @author li
 * @version 1 (2015年5月28日 上午10:35:45)
 * @since Java7
 */
public class EmptyIterator implements Iterator, Serializable {
    public static final Iterator INSTANCE = new EmptyIterator();

    public boolean hasNext() {
        return false;
    }

    public Object next() {
        return null;
    }

    public void remove() {}
}