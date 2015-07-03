package cn.limw.summer.dao.hibernate.search.filter;

import java.util.Map.Entry;

import cn.limw.summer.dao.hibernate.search.SearchCriteria;
import cn.limw.summer.dao.hibernate.search.SqlArrayValueSearchFilter;

/**
 * @author li
 * @version 1 (2014年8月11日 上午10:30:05)
 * @since Java7
 */
public class InFilter extends SqlArrayValueSearchFilter {
    public InFilter() {
        setOperator("$in");
    }

    public String translate(SearchCriteria searchCriteria, Entry<String, Object> entry) {
        Entry<?, ?> node = valueEntry(entry);
        String sqlArrayValue = getSqlArrayValue(searchCriteria, node);
        return ENTITY_ALIAS + "." + field(node.getKey()) + " IN (" + sqlArrayValue + ")";
    }
}