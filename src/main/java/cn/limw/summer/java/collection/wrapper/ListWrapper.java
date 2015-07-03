package cn.limw.summer.java.collection.wrapper;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

/**
 * @author li
 * @version 1 (2015年5月22日 上午10:50:28)
 * @since Java7
 */
public class ListWrapper<T> extends CollectionWrapper<T> implements List<T> {
    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public ListWrapper() {}

    public ListWrapper(List<T> list) {
        super(list);
        setList(list);
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        return getList().addAll(index, c);
    }

    public T get(int index) {
        return getList().get(index);
    }

    public T set(int index, T element) {
        return getList().set(index, element);
    }

    public void add(int index, T element) {
        getList().add(index, element);
    }

    public T remove(int index) {
        return getList().remove(index);
    }

    public int indexOf(Object o) {
        return getList().indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return getList().lastIndexOf(o);
    }

    public ListIterator<T> listIterator() {
        return getList().listIterator();
    }

    public ListIterator<T> listIterator(int index) {
        return getList().listIterator(index);
    }

    public List<T> subList(int fromIndex, int toIndex) {
        return getList().subList(fromIndex, toIndex);
    }
}