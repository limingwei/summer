package cn.limw.summer.dao;

import java.util.List;

import cn.limw.summer.java.collection.wrapper.ListWrapper;
import cn.limw.summer.util.Jsons;

/**
 * @author li
 * @version 1 (2015年5月26日 下午5:08:06)
 * @since Java7
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ListWithPage extends ListWrapper implements Pager {
    private static final long serialVersionUID = -1843918093974604939L;

    private Page page;

    public ListWithPage(List list, Page page) {
        super(list);
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

    public String toString() {
        try {
            return super.toString() + ", " + getPage() + ", " + Jsons.toJson(this);
        } catch (Throwable e) {
            return super.toString();
        }
    }
}