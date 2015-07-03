package cn.limw.summer.dao.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.limw.summer.mybatis.MybatisParameter;

/**
 * @author li
 * @version 1 (2014年10月13日 下午2:10:38)
 * @since Java7
 */
public class MyBatisSupport {
    protected static final MybatisParameter NO_PARAMETER = MybatisParameter.NO_PARAMETER;

    private SqlSession sqlSession;

    public SqlSession getSqlSession() {
        return sqlSession;
    }

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    protected <T> List<T> selectList(String statementId, Object parameter) {
        if (NO_PARAMETER.equals(parameter)) {
            return getSqlSession().selectList(statementId);
        } else {
            return getSqlSession().selectList(statementId, parameter);
        }
    }
}