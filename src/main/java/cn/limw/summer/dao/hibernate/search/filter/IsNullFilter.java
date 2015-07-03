package cn.limw.summer.dao.hibernate.search.filter;

import java.util.Map.Entry;

import cn.limw.summer.dao.hibernate.search.SearchCriteria;
import cn.limw.summer.dao.hibernate.search.SqlArrayValueSearchFilter;
import cn.limw.summer.util.StringUtil;

import com.alibaba.fastjson.JSONArray;

/**
 * @author li
 * @version 1 (2014年8月11日 上午11:25:19)
 * @since Java7
 */
public class IsNullFilter extends SqlArrayValueSearchFilter {
    public String getOperator() {
        return "$is_null";
    }

    public String translate(SearchCriteria searchCriteria, Entry<String, Object> entry) {
        Object value = entry.getValue();
        String[] items;
        if (value instanceof JSONArray) {
            JSONArray array = (JSONArray) value;
            int size = array.size();
            items = new String[size];
            for (int i = 0; i < size; i++) {
                items[i] = ENTITY_ALIAS + "." + field(array.get(i)) + " IS NULL";
            }
        } else {
            items = new String[] { ENTITY_ALIAS + "." + value + " IS NULL" };
        }
        return StringUtil.join(" AND ", items);
    }
}