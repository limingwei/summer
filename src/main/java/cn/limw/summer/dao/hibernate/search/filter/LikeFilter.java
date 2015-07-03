package cn.limw.summer.dao.hibernate.search.filter;

import java.util.Map.Entry;

import cn.limw.summer.dao.hibernate.search.SearchCriteria;
import cn.limw.summer.dao.hibernate.search.SearchFilter;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2014年8月11日 上午10:29:40)
 * @since Java7
 */
public class LikeFilter extends SearchFilter {
    public String getOperator() {
        return "$like";
    }

    public String translate(SearchCriteria searchCriteria, Entry<String, Object> entry) {
        Entry<?, ?> node = valueEntry(entry);
        Object field = node.getKey();
        String value = (String) node.getValue();
        return "CAST(" + ENTITY_ALIAS + "." + field(field) + " as string) LIKE :" + parameter(searchCriteria, likeValue(value)) + "";
    }

    /**
     * HTTP参数不能使用%, 用$代替
     */
    private String likeValue(String value) {
        return StringUtil.isEmpty(value) ? value : value.replace("$", "%");
    }
}