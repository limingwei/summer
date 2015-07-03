package cn.limw.summer.java.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author li
 * @version 1 (2015年5月28日 上午10:29:29)
 * @since Java7
 */
public class EmptyCollection implements Collection, Serializable {
    private static final long serialVersionUID = -359950422274987588L;

    public static final Collection INSTANCE = new EmptyCollection();

    public int size() {
        return 0;
    }

    public boolean isEmpty() {
        return true;
    }

    public boolean contains(Object o) {
        return false;
    }

    public Iterator iterator() {
        return EmptyIterator.INSTANCE;
    }

    public Object[] toArray() {
        return new Object[0];
    }

    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    public boolean add(Object e) {
        return false;
    }

    public boolean remove(Object o) {
        return false;
    }

    public boolean containsAll(Collection c) {
        return false;
    }

    public boolean addAll(Collection c) {
        return false;
    }

    public boolean removeAll(Collection c) {
        return false;
    }

    public boolean retainAll(Collection c) {
        return false;
    }

    public void clear() {}
}