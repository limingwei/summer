package cn.limw.summer.dao.hibernate.search;

import java.util.List;
import java.util.Map;

import org.hibernate.transform.Transformers;
import org.slf4j.Logger;

import cn.limw.summer.dao.IDao;
import cn.limw.summer.dao.hibernate.AbstractDao;
import cn.limw.summer.spring.beans.factory.lazyinject.LazyInjectSupport;
import cn.limw.summer.util.Asserts;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.StringUtil;
import freemarker.core.InvalidReferenceException;

/**
 * 搜索服务
 * @author li
 * @version 1 (2014年8月12日 上午10:17:13)
 * @since Java7
 */
public class SearchService {
    private static final Logger log = Logs.slf4j();

    private SearchQueryBuilder searchQueryBuilder;

    private Class<?> entityType;

    private IDao<?, ?> dao;

    public SearchService(SearchQueryBuilder searchQueryBuilder, Class<?> entityType, IDao<?, ?> dao) {
        this.searchQueryBuilder = searchQueryBuilder;
        this.entityType = entityType;
        this.dao = dao;
    }

    public SearchResult search(SearchCriteria searchCriteria) {
        try {
            return doSearch(searchCriteria);
        } catch (Throwable e) {
            if (e instanceof InvalidReferenceException) {
                log.error("" + e);
                return EmptySearchResult.INSTANCE; // 此种类型错误返回空结果集
            } else {
                throw new RuntimeException("search error, " + e, e);
            }
        }
    }

    public SearchResult doSearch(SearchCriteria searchCriteria) {
        Asserts.noNull(searchCriteria, "参数searchCriteria不可以为空");
        long start = System.currentTimeMillis();

        searchCriteria.setFetch(SearchUtil.fieldsToFetch(searchCriteria.getFields(), getDao(), entityType));

        searchCriteria.setEntityName(entityType.getName());

        searchQueryBuilder.doBuild(searchCriteria); // Freemarker异常

        String listHql = searchCriteria.getListQuery();
        Map<String, Object> args = searchCriteria.getArgs();

        long time = System.currentTimeMillis() - start;
        log(searchCriteria, listHql, time);

        List<?> list = null;
        if (searchCriteria.isFetchWay()) { // Fetch方式
            list = getDao().getHibernateDao().listByHql(searchCriteria.getPage(), listHql, args);
        } else if (searchCriteria.isFieldsWay()) { // 返回指定列的方式
            list = getDao().getHibernateDao().listByHql(Transformers.aliasToBean(entityType), searchCriteria.getPage(), listHql, new Object[] { args });
        } else if (!StringUtil.isEmpty(searchCriteria.getSelectQuery())) {
            list = getDao().getHibernateDao().listByHql(searchCriteria.getPage(), listHql, args);//自定义Select语句方式
        } else {
            list = getDao().getHibernateDao().listByHql(searchCriteria.getPage(), listHql, args);//简单 From Entity 方式
        }

        SearchResult searchResult = new SearchResult();
        searchResult.setFetch(searchCriteria.getFetch());
        searchResult.setFields(searchCriteria.getFields());
        searchResult.setList(list);

        if (searchCriteria.doCount()) {
            String countHql = searchCriteria.getCountQuery();
            searchResult.setCount(getDao().getHibernateDao().countByHql(countHql, args));
        }

        log.info("search(SearchCriteria), filter=" + searchCriteria.getFilter() + " result.list.size=" + searchResult.getList().size() + ", result.count=" + searchResult.getCount());
        return searchResult;
    }

    public AbstractDao<?, ?> getDao() {
        Asserts.noNull(dao, "dao is null");

        if (dao instanceof AbstractDao) {
            return (AbstractDao<?, ?>) dao;
        } else if (dao instanceof LazyInjectSupport) {
            AbstractDao<?, ?> lazyInjectDelegateTarget = (AbstractDao<?, ?>) ((LazyInjectSupport) dao)._getLazyInjectDelegateHolder_().getLazyInjectDelegateTarget();
            return Asserts.noNull(lazyInjectDelegateTarget, "lazyInjectDelegateTarget is null");
        } else {
            throw new RuntimeException("getDao error, dao is " + dao);
        }
    }

    public void log(SearchCriteria searchCriteria, String listHql, long time) {
        if (time > 40) {
            log.error("SearchFilter build Hql: {} -> {}, 耗时 {} ms", searchCriteria.getFilter(), listHql, time);
        } else if (time > 20) {
            log.warn("SearchFilter build Hql: {} -> {}, 耗时 {} ms", searchCriteria.getFilter(), listHql, time);
        } else if (time > 10) {
            log.info("SearchFilter build Hql: {} -> {}, 耗时 {} ms", searchCriteria.getFilter(), listHql, time);
        } else if (time > 5) {
            log.debug("SearchFilter build Hql: {} -> {}, 耗时 {} ms", searchCriteria.getFilter(), listHql, time);
        } else {
            log.trace("SearchFilter build Hql: {} -> {}, 耗时 {} ms", searchCriteria.getFilter(), listHql, time);
        }
    }

    public Integer count(SearchCriteria searchCriteria) {
        if (null == searchCriteria.getEntityName()) {
            searchCriteria.setEntityName(entityType.getName());
        }
        searchQueryBuilder.doBuild(searchCriteria);
        String countHql = searchCriteria.getCountQuery();
        Map<String, Object> args = searchCriteria.getArgs();
        return getDao().getHibernateDao().countByHql(countHql, args);
    }
}