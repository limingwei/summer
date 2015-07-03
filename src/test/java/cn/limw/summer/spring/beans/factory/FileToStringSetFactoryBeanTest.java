package cn.limw.summer.spring.beans.factory;

import org.junit.Test;

import cn.limw.summer.spring.beans.factory.FileToStringSetFactoryBean;

/**
 * @author li
 * @version 1 (2014年11月25日 下午4:45:29)
 * @since Java7
 */
public class FileToStringSetFactoryBeanTest {
    @Test(expected = Exception.class)
    public void fileNotFound() throws Exception {
        FileToStringSetFactoryBean factoryBean = new FileToStringSetFactoryBean();
        factoryBean.setFilePath("~!@#$%^&*()_+");
        factoryBean.getObject();
    }
}