package cn.limw.summer.dao.hibernate.search;

import java.util.Collections;

/**
 * @author li
 * @version 1 (2015年6月15日 上午10:22:11)
 * @since Java7
 */
public class EmptySearchResult extends SearchResult {
    public static final SearchResult INSTANCE = new EmptySearchResult();

    private static final long serialVersionUID = 8073003332596370477L;

    public EmptySearchResult() {
        setCount(0);
        setFetch(Collections.EMPTY_SET);
        setFields(Collections.EMPTY_SET);
        setList(Collections.EMPTY_LIST);
    }
}