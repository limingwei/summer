package cn.limw.summer.mybatis.spring;

import org.apache.ibatis.session.SqlSessionFactory;

import cn.limw.summer.spring.beans.factory.SingletonFactoryBean;

/**
 * @author li
 * @version 1 (2014年11月14日 上午10:26:05)
 * @since Java7
 */
public class SqlSessionTemplateFactoryBean extends SingletonFactoryBean<SqlSessionTemplate> {
    private SqlSessionFactory sqlSessionFactory;

    public SqlSessionTemplate getObject() throws Exception {
        return new SqlSessionTemplate(getSqlSessionFactory());
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
}