package cn.limw.summer.mybatis.spring;

import org.apache.ibatis.session.SqlSessionFactory;

/**
 * @author li
 * @version 1 (2014年10月24日 下午3:03:07)
 * @since Java7
 */
public class SqlSessionTemplate extends AbstractSqlSessionTemplate {
    public SqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    /**
     * Spring销毁Bean时调用,
     */
    public void close() {}
}