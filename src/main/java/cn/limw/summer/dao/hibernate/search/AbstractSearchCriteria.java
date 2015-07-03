package cn.limw.summer.dao.hibernate.search;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;

import cn.limw.summer.dao.Page;
import cn.limw.summer.freemarker.util.FreeMarkerUtil;
import cn.limw.summer.util.ArrayUtil;
import cn.limw.summer.util.Jsons;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.Maps;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2014年9月11日 下午4:19:38)
 * @since Java7
 */
public abstract class AbstractSearchCriteria implements Serializable {
    private static final long serialVersionUID = 6973425669720962899L;

    private static final Logger log = Logs.slf4j();

    public static final String WHERE_QUERY_DEFAULT_VALUE = "/* whereQuery default value */";

    private Map<String, Object> args = new HashMap<String, Object>();

    private Map<String, Object> filterMap;

    private String entityName;

    private String filter;

    private Set<String> fetch;

    private Set<String> fields;

    private Page page;

    private String orderByField;

    private String orderBy;

    private String sort;

    private String whereQuery = WHERE_QUERY_DEFAULT_VALUE;

    private Map<String, Object> externalData;

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getFilter() {
        return filter;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setOrderByField(String orderByField) {
        this.orderByField = orderByField;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public String getOrderByField() {
        return orderByField;
    }

    public String getEntityName() {
        return entityName;
    }

    public AbstractSearchCriteria setEntityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public String getWhereQuery() {
        return whereQuery;
    }

    public void setWhereQuery(String whereQuery) {
        this.whereQuery = whereQuery;
    }

    public Set<String> getFetch() {
        return fetch;
    }

    public Set<String> getFields() {
        return fields;
    }

    private static Map filterToMap(String _filter, Map map) {
        return Jsons.toMap(freemarkerMerge(_filter, map));
    }

    private static String freemarkerMerge(String _filter, Map map) {
        try {
            return FreeMarkerUtil.merge(_filter, map);
        } catch (Exception e) {
            log.error("e=" + e + ", msg=" + e.getMessage(), e);
            return "{'$lt':{'id',0}}";
        }
    }

    /**
     * page 不为空 且 pageSize>1 且 pageSize<Integer.MAX_VALUE的时候, 执行count
     */
    public Boolean doCount() {
        return null != getPage() && getPage().getDoCount() && getPage().getPageSize() > 1 && getPage().getPageSize() < Integer.MAX_VALUE;
    }

    public Map<String, Object> getFilterMap() {
        String _filter = getFilter();
        if (null == filterMap && !StringUtil.isEmpty(_filter)) {
            filterMap = filterToMap(_filter, getExternalData());
        } else if (null == filterMap && StringUtil.isEmpty(_filter)) {
            filterMap = Maps.newMap();
        }
        return filterMap;
    }

    /**
     * 扩展数据
     */
    public Map<String, Object> getExternalData() {
        return null == externalData ? externalData = new HashMap<String, Object>() : externalData;
    }

    /**
     * 如未传参数则默认id
     */
    public void setFields(String fields) {
        this.fields = ArrayUtil.asSet(StringUtil.isEmpty(fields) ? new String[0] : fields.split(","));
        if (null == this.fields || this.fields.isEmpty()) {
            this.fields = null; // this.fields = Arrays.asList("id");  // 默认返回当前对象所有属性
        }
    }

    public Boolean isFetchWay() {
        return null != fetch && fetch.size() > 0;
    }

    public Boolean isFieldsWay() {
        return null != fields && fields.size() > 0;
    }

    public void setFetch(Set<String> fetch) {
        this.fetch = fetch;
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    public void setArgs(Map<String, Object> args) {
        this.args = args;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSort() {
        return this.sort;
    }
}