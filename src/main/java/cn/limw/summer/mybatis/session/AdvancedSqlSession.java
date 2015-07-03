package cn.limw.summer.mybatis.session;

import org.apache.ibatis.session.SqlSession;

/**
 * @author li
 * @version 1 (2015年5月20日 下午2:48:44)
 * @since Java7
 */
public class AdvancedSqlSession extends AbstractAdvancedSqlSession {
    public AdvancedSqlSession(SqlSession sqlSession) {
        super(sqlSession);
    }
}