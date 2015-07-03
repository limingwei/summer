package cn.limw.summer.dao.hibernate.search;

import org.junit.Assert;
import org.junit.Test;

import cn.limw.summer.dao.hibernate.search.SearchCriteria;

/**
 * @author li
 * @version 1 (2014年10月21日 下午6:26:22)
 * @since Java7
 */
public class SearchCriteriaTest {
    @Test
    public void getOrderByHql() {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setSort("id,email");
        Assert.assertEquals(" ORDER BY entity.id DESC NULLS LAST , entity.email DESC NULLS LAST ", searchCriteria.getOrderByHql());
        searchCriteria.setSort("id:0,email");
        Assert.assertEquals(" ORDER BY entity.id ASC NULLS FIRST , entity.email DESC NULLS LAST ", searchCriteria.getOrderByHql());
        searchCriteria.setSort("id,email:1");
        Assert.assertEquals(" ORDER BY entity.id DESC NULLS LAST , entity.email DESC NULLS LAST ", searchCriteria.getOrderByHql());
        searchCriteria.setSort("id:0,email:1");
        Assert.assertEquals(" ORDER BY entity.id ASC NULLS FIRST , entity.email DESC NULLS LAST ", searchCriteria.getOrderByHql());
        searchCriteria.setSort("id:1,email:0");
        Assert.assertEquals(" ORDER BY entity.id DESC NULLS LAST , entity.email ASC NULLS FIRST ", searchCriteria.getOrderByHql());
    }
}