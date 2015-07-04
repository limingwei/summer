package com.unblocked.upsss.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.unblocked.support.junit.SpringHibernateTest;
import com.unblocked.upsss.util.Criteria;

/**
 * ${entity.type}DaoTest
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2013年12月31日 下午1:21:09)
 */
public class ${entity.type}DaoTest extends SpringHibernateTest {
    @Autowired
    I${entity.type}Dao ${entity.type?uncap_first}Dao;

    @Test
    public void search() {
        Criteria criteria = new Criteria();
        System.err.println(${entity.type?uncap_first}Dao.search(criteria));
    }
}