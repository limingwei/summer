package cn.limw.summer.dao.hibernate.search.filter;

import java.util.Map.Entry;

import cn.limw.summer.dao.hibernate.search.SearchCriteria;
import cn.limw.summer.dao.hibernate.search.SearchFilter;

/**
 * @author li
 * @version 1 (2014年8月11日 上午10:29:47)
 * @since Java7
 */
public class LessThanFilter extends SearchFilter {
    public String getOperator() {
        return "$lt";
    }

    public String translate(SearchCriteria searchCriteria, Entry<String, Object> entry) {
        Entry<?, ?> node = valueEntry(entry);
        return ENTITY_ALIAS + "." + field(node.getKey()) + "<:" + parameter(searchCriteria, node.getValue()) + "";
    }
}