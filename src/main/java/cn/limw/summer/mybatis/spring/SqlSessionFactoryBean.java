package cn.limw.summer.mybatis.spring;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.SessionFactory;
import org.springframework.core.io.Resource;

import cn.limw.summer.hibernate.SessionFactoryDataSource;
import cn.limw.summer.mybatis.interceptor.logger.ExecutorQueryPerformanceInterceptor;
import cn.limw.summer.util.ArrayUtil;

/**
 * @author li
 * @version 1 (2014年10月14日 下午12:03:19)
 * @since Java7
 */
public class SqlSessionFactoryBean extends AbstractSqlSessionFactoryBean {
    private SessionFactory hibernateSessionFactory; // modifier private not allowed here, 导出javadoc的时候报错

    public Interceptor[] getDefaultInterceptors() {
        return new Interceptor[] { /* new StatementHandlerQueryLogger(), */new ExecutorQueryPerformanceInterceptor() };
    }

    protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
        Resource[] localMappers = getMapperLocations();
        List<Resource> hibernateMappers = MyBatisUtil.hibernateToMybatis(getHibernateSessionFactory());
        hibernateMappers.add(MyBatisUtil.commonMappers());
        Resource[] allMappers = ArrayUtil.concat(localMappers, hibernateMappers);

        setMapperLocations(allMappers);
        return super.buildSqlSessionFactory();
    }

    public SessionFactory getHibernateSessionFactory() {
        return hibernateSessionFactory;
    }

    public void setHibernateSessionFactory(SessionFactory hibernateSessionFactory) {
        this.hibernateSessionFactory = hibernateSessionFactory;
    }

    public DataSource getDataSource() {
        if (null == super.getDataSource() && null != getHibernateSessionFactory()) {
            super.setDataSource(new SessionFactoryDataSource(getHibernateSessionFactory()));
        }
        return super.getDataSource();
    }
}