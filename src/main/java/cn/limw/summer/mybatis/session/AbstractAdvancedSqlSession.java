package cn.limw.summer.mybatis.session;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSession;

import cn.limw.summer.mybatis.session.wrapper.SqlSessionWrapper;
import cn.limw.summer.util.Mirrors;

/**
 * @author li
 * @version 1 (2015年5月20日 下午3:00:07)
 * @since Java7
 */
public class AbstractAdvancedSqlSession extends SqlSessionWrapper {
    private Executor _executor;

    public AbstractAdvancedSqlSession(SqlSession sqlSession) {
        super(sqlSession);
    }

    public Executor getExecutor() {
        if (null == _executor) {
            getExecutorAndCache();
        }
        return _executor;
    }

    private synchronized void getExecutorAndCache() {
        if (getSqlSession() instanceof DefaultSqlSession) {
            _executor = (Executor) Mirrors.getFieldValue(getSqlSession(), "executor");
        } else {
            throw new RuntimeException("sqlSessionType=" + getSqlSession().getClass() + "");
        }
    }
}