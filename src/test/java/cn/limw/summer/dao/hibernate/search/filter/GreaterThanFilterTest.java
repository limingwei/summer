package cn.limw.summer.dao.hibernate.search.filter;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import cn.limw.summer.BaseTest;
import cn.limw.summer.dao.hibernate.search.SearchCriteria;
import cn.limw.summer.dao.hibernate.search.SearchQueryBuilder;

/**
 * @author li
 * @version 1 (2014年9月28日 下午2:46:18)
 * @since Java7
 */
public class GreaterThanFilterTest extends BaseTest {
    @Resource
    SearchQueryBuilder searchQueryBuilder;

    @Test
    public void test() {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setFilter("{'$gt':{'id':2}}");
        searchCriteria.setEntityName("User");
        searchQueryBuilder.doBuild(searchCriteria);
        Assert.assertEquals("FROM User entity WHERE entity.id>:$1", searchCriteria.getListQuery());
    }
}