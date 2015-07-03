package cn.limw.summer.dao.hibernate.search;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.limw.summer.util.Maps;
import cn.limw.summer.util.StringUtil;

/**
 * 搜索条件
 * @author li
 * @version 1 (2014年8月11日 上午9:10:25)
 * @since Java7
 */
public class SearchCriteria extends AbstractSearchCriteria {
    private static final long serialVersionUID = -6927806359406461681L;

    private String selectQuery;

    private String fetchQuery = "";

    public SearchCriteria() {}

    public SearchCriteria(String filter) {
        setFilter(filter);
    }

    public SearchCriteria(SearchCriteria searchCriteria, String filter) {
        setFilter(filter);

        Map<String, Object> externalData = searchCriteria.getExternalData();
        for (Entry<String, Object> entry : externalData.entrySet()) {
            setExternalData(entry.getKey(), entry.getValue());
        }
    }

    public void setArg(String fieldValueKey, Object value) {
        getArgs().put(fieldValueKey, value);
    }

    public SearchCriteria and(String filter) {
        if (null == getFilter()) {
            setFilter(filter);
        } else {
            setFilter("{\"$and\":[" + getFilter() + "," + filter + "]}");
        }
        return this;
    }

    public SearchCriteria or(String filter) {
        if (null == getFilter()) {
            setFilter(filter);
        } else {
            setFilter("{\"$or\":[" + getFilter() + "," + filter + "]}");
        }
        return this;
    }

    public String getOrderByHql() {
        if (!StringUtil.isEmpty(getSort())) {
            String orderByHql = " ORDER BY ";
            String[] sortArray = StringUtil.split(getSort(), ",");
            for (int i = 0; i < sortArray.length; i++) {
                if (sortArray[i].contains(":")) {
                    String[] array = sortArray[i].split(":");
                    String orderBy = ("0".equals(array[1]) || "asc".equalsIgnoreCase(array[i])) ? " ASC NULLS FIRST " : " DESC NULLS LAST ";
                    orderByHql += SearchFilter.ENTITY_ALIAS + "." + array[0] + orderBy;
                } else {
                    orderByHql += SearchFilter.ENTITY_ALIAS + "." + sortArray[i] + " DESC NULLS LAST ";
                }
                if (i < sortArray.length - 1) {
                    orderByHql += ", ";
                }
            }
            return orderByHql;
        } else if (!StringUtil.isEmpty(getOrderBy()) && !StringUtil.isEmpty(getOrderByField())) {
            return " ORDER BY " + SearchFilter.ENTITY_ALIAS + "." + SearchFilter.field(getOrderByField()) + " " + getOrderBy() + " NULLS " + ("DESC".equalsIgnoreCase(getOrderBy()) ? "LAST" : "FIRST");
        } else {
            return "";
        }
    }

    public String getListQuery() {
        String hql = "";

        if (isFetchWay()) {//fetch 方式
            Set<String> fetchFields = getFetch();//Set转List
            hql = "FROM " + getEntityName() + " " + SearchFilter.ENTITY_ALIAS + " ";
            for (String fetchField : fetchFields) {
                hql += " LEFT JOIN FETCH " + SearchFilter.ENTITY_ALIAS + "." + SearchFilter.field(fetchField);
            }
        } else if (isFieldsWay()) {//select as 方式
            Set<String> selectFields = getFields();
            hql = "SELECT ";

            boolean first = true;
            for (String selectField : selectFields) {
                if (first) {
                    first = false;
                } else {
                    hql += ", ";
                }
                hql += SearchFilter.ENTITY_ALIAS + "." + SearchFilter.field(selectField) + " AS " + selectField;
            }

            hql += " FROM " + getEntityName() + " " + SearchFilter.ENTITY_ALIAS;
        } else if (!StringUtil.isEmpty(getSelectQuery())) {
            hql = getSelectQuery() + " FROM " + getEntityName() + " " + SearchFilter.ENTITY_ALIAS + getFetchQuery();
        } else {//select * 方式
            hql = "FROM " + getEntityName() + " " + SearchFilter.ENTITY_ALIAS;
        }
        hql += " " + getWhereQuery() + getOrderByHql();
        return hql;
    }

    public String getCountQuery() {
        return "SELECT COUNT(" + SearchFilter.ENTITY_ALIAS + ") FROM " + getEntityName() + " " + SearchFilter.ENTITY_ALIAS + " " + getWhereQuery();
    }

    /**
     * 指定返回数据的Hql语句
     * @param selectQuery
     */
    public SearchCriteria setSelectQuery(String selectQuery) {
        this.selectQuery = selectQuery;
        return this;
    }

    public String getSelectQuery() {
        return selectQuery;
    }

    public String toString() {
        return super.toString() + ", filter=" + getFilter() + ", externalData=" + Maps.toString(getExternalData());
    }

    public void setExternalData(String key, Object value) {
        getExternalData().put(key, value);
    }

    public Object getExternalData(String key) {
        return getExternalData().get(key);
    }

    public SearchCriteria setFetchQuery(String fetchQuery) {
        this.fetchQuery = fetchQuery;
        return this;
    }

    public String getFetchQuery() {
        return fetchQuery;
    }
}