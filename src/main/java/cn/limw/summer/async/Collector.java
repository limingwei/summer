package cn.limw.summer.async;

import java.util.ArrayList;
import java.util.List;

/**
 * @author li
 * @version 1 (2014年11月3日 下午5:00:42)
 * @since Java7
 */
public class Collector<T> implements Handler<T> {
    private List<T> list = new ArrayList<T>();

    public void handle(T target) {
        list.add(target);
    }

    public List<T> getList() {
        return list;
    }
}