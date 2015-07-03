package cn.limw.summer.dao.hibernate.search.filter;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import cn.limw.summer.BaseTest;
import cn.limw.summer.dao.hibernate.search.SearchCriteria;
import cn.limw.summer.dao.hibernate.search.SearchQueryBuilder;

/**
 * @author li
 * @version 1 (2014年9月1日 上午11:41:15)
 * @since Java7
 */
public class BetweenFilterTest extends BaseTest {
    @Resource
    SearchQueryBuilder searchQueryBuilder;

    @Test
    public void test() {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setFilter("{'$between':{'id':[2,1]}}");
        searchCriteria.setEntityName("User");
        searchQueryBuilder.doBuild(searchCriteria);
        Assert.assertEquals("FROM User entity WHERE entity.id BETWEEN :$1 AND :$2", searchCriteria.getListQuery());
    }
}