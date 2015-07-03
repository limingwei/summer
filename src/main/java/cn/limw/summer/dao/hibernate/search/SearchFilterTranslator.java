package cn.limw.summer.dao.hibernate.search;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cn.limw.summer.util.Asserts;
import cn.limw.summer.util.Jsons;
import cn.limw.summer.util.Maps;

/**
 * 搜索过滤器解析器
 * @author li
 * @version 1 (2014年8月11日 上午10:20:07)
 * @since Java7
 */
public class SearchFilterTranslator {
    private Map<String, SearchFilter> searchFilters = new HashMap<String, SearchFilter>();

    public void translate(SearchCriteria searchCriteria) {
        Asserts.noNull(searchCriteria);

        if (null != searchCriteria.getFilterMap() && !searchCriteria.getFilterMap().isEmpty()) {
            searchCriteria.setWhereQuery("WHERE " + doTranslate(searchCriteria, searchCriteria.getFilterMap()));
        } else {
            searchCriteria.setWhereQuery("");
        }
    }

    /**
     * @see cn.limw.summer.dao.hibernate.search.SearchFilter#translate(SearchCriteria, Entry)
     */
    public String doTranslate(SearchCriteria searchCriteria, Map<String, Object> filter) {
        if (null == filter || filter.isEmpty()) {
            return "";
        } else {
            Entry<String, Object> entry = filter.entrySet().iterator().next();
            String key = entry.getKey();
            SearchFilter searchFilter = searchFilters.get(key);
            if (null != searchFilter) {
                return searchFilter.translate(searchCriteria, entry);
            } else {
                throw new RuntimeException("unsupported operator " + Maps.toString(filter) + ", " + Jsons.toJson(searchFilters.keySet()));
            }
        }
    }

    public void setSearchFilters(Map<String, SearchFilter> searchFilters) {
        this.searchFilters = searchFilters;
    }

    public Map<String, SearchFilter> getSearchFilters() {
        return searchFilters;
    }
}