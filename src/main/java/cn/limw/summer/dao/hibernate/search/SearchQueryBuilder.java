package cn.limw.summer.dao.hibernate.search;

import java.util.HashMap;
import java.util.List;

/**
 * 搜索Hql组装器
 * @author li
 * @version 1 (2014年8月11日 上午9:21:33)
 * @since Java7
 */
public class SearchQueryBuilder {
    private SearchFilterTranslator searchFilterTranslator = new SearchFilterTranslator();

    public void setSearchFilters(List<SearchFilter> filters) {
        searchFilterTranslator.setSearchFilters(new HashMap<String, SearchFilter>());
        addSearchFilters(filters);
    }

    public void addSearchFilters(List<SearchFilter> filters) {
        for (SearchFilter searchFilter : filters) {
            addSearchFilter(searchFilter);
        }
    }

    public void addSearchFilter(SearchFilter searchFilter) {
        searchFilterTranslator.getSearchFilters().put(searchFilter.getOperator(), searchFilter.setTranslator(searchFilterTranslator));
    }

    public void doBuild(SearchCriteria searchCriteria) {
        if (SearchCriteria.WHERE_QUERY_DEFAULT_VALUE.equals(searchCriteria.getWhereQuery())) {
            searchFilterTranslator.translate(searchCriteria);
        }
    }
}