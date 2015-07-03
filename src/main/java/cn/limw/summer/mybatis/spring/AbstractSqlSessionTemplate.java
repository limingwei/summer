package cn.limw.summer.mybatis.spring;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

/**
 * @author li
 * @version 1 (2015年6月3日 下午2:12:15)
 * @since Java7
 */
public class AbstractSqlSessionTemplate extends SqlSessionTemplate {
    public AbstractSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }
}