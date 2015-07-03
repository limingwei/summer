package cn.limw.summer.java.collection.wrapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import cn.limw.summer.java.collection.EmptyCollection;

/**
 * @author li
 * @version 1 (2015年5月22日 上午10:51:03)
 * @since Java7
 */
public class CollectionWrapper<T> implements Collection<T>, Serializable {
    private static final long serialVersionUID = 189956168177052192L;

    private Collection<T> collection;

    public Collection<T> getCollection() {
        return null == collection ? EmptyCollection.INSTANCE : collection;
    }

    public void setCollection(Collection<T> collection) {
        this.collection = collection;
    }

    public CollectionWrapper(Collection<T> collection) {
        setCollection(collection);
    }

    public CollectionWrapper() {}

    public int size() {
        return getCollection().size();
    }

    public boolean isEmpty() {
        return getCollection().isEmpty();
    }

    public boolean contains(Object o) {
        return getCollection().contains(o);
    }

    public Iterator<T> iterator() {
        return getCollection().iterator();
    }

    public Object[] toArray() {
        return getCollection().toArray();
    }

    public <T> T[] toArray(T[] a) {
        return getCollection().toArray(a);
    }

    public boolean add(T e) {
        return getCollection().add(e);
    }

    public boolean remove(Object o) {
        return getCollection().remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return getCollection().containsAll(c);
    }

    public boolean addAll(Collection<? extends T> c) {
        return getCollection().addAll(c);
    }

    public boolean removeAll(Collection<?> c) {
        return getCollection().removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return getCollection().retainAll(c);
    }

    public void clear() {
        getCollection().clear();
    }
}