package cn.limw.summer.dao.hibernate.search.filter;

import java.util.Map.Entry;

import cn.limw.summer.dao.hibernate.search.SearchCriteria;
import cn.limw.summer.dao.hibernate.search.SearchFilter;
import cn.limw.summer.util.StringUtil;

import com.alibaba.fastjson.JSONArray;

/**
 * @author li
 * @version 1 (2014年8月11日 上午10:30:41)
 * @since Java7
 */
public class AndFilter extends SearchFilter {
    public String getOperator() {
        return "$and";
    }

    public String translate(SearchCriteria searchCriteria, Entry<String, Object> entry) {
        return StringUtil.join(" AND ", "(", ")", translateItems(searchCriteria, getItems((JSONArray) entry.getValue())));
    }
}