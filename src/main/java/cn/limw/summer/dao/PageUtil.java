package cn.limw.summer.dao;

import java.util.List;

/**
 * @author li
 * @version 1 (2015年6月23日 下午5:24:17)
 * @since Java7
 */
public class PageUtil {
    public static Page getPage(List<?> list) {
        return list instanceof Pager ? ((Pager) list).getPage() : null;
    }

    public static Integer getPageRecordCount(List<?> list) {
        Page page = getPage(list);
        return null == page ? null : page.getRecordCount();
    }
}