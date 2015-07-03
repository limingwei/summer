package cn.limw.summer.dao.hibernate.search.filter;

import java.util.Map.Entry;

import cn.limw.summer.dao.hibernate.search.SearchCriteria;
import cn.limw.summer.dao.hibernate.search.SqlArrayValueSearchFilter;

import com.alibaba.fastjson.JSONArray;

/**
 * @author li
 * @version 1 (2014年8月11日 上午11:25:15)
 * @since Java7
 */
public class IsNotNullFilter extends SqlArrayValueSearchFilter {
    public String getOperator() {
        return "$is_not_null";
    }

    public String translate(SearchCriteria searchCriteria, Entry<String, Object> entry) {
        String hql = "";
        Object value = entry.getValue();
        if (value instanceof JSONArray) {
            JSONArray array = (JSONArray) value;
            int size = array.size();
            for (int i = 0; i < size; i++) {
                hql += ENTITY_ALIAS + "." + field(array.get(i)) + " IS NOT NULL";
                if (i < size - 1) {
                    hql += " AND ";
                }
            }
        } else {
            hql += ENTITY_ALIAS + "." + value + " IS NOT NULL";
        }
        return hql;
    }
}