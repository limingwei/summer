package com.unblocked.upsss.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.unblocked.support.junit.SpringHibernateTest;
import com.unblocked.upsss.util.Criteria;

/**
 * ${entity.type}ServiceTest
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2013年12月31日 下午1:20:28)
 */
public class ${entity.type}ServiceTest extends SpringHibernateTest {
    @Autowired
    I${entity.type}Service ${entity.type?uncap_first}Service;

    @Test
    public void search() {
        Criteria criteria = new Criteria();
        System.err.println(${entity.type?uncap_first}Service.search(criteria));
    }
}