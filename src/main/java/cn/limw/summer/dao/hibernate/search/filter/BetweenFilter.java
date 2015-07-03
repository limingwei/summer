package cn.limw.summer.dao.hibernate.search.filter;

import java.util.Map.Entry;

import org.springframework.util.Assert;

import cn.limw.summer.dao.hibernate.search.SearchCriteria;
import cn.limw.summer.dao.hibernate.search.SearchFilter;

import com.alibaba.fastjson.JSONArray;

/**
 * @author li
 * @version 1 (2014年8月11日 上午10:30:05)
 * @since Java7
 */
public class BetweenFilter extends SearchFilter {
    public String getOperator() {
        return "$between";
    }

    public String translate(SearchCriteria searchCriteria, Entry<String, Object> entry) {
        Entry<?, ?> node = valueEntry(entry);
        JSONArray array = (JSONArray) node.getValue();
        Assert.isTrue(array.size() == 2, "$between 需要有两个参数");
        return ENTITY_ALIAS + "." + field(node.getKey()) + " BETWEEN :" + parameter(searchCriteria, array.get(0)) + " AND :" + parameter(searchCriteria, array.get(1));
    }
}