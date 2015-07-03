package cn.limw.summer.mybatis.session.wrapper;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import cn.limw.summer.util.Asserts;

/**
 * @author li
 * @version 1 (2015年5月20日 下午2:48:04)
 * @since Java7
 */
public class SqlSessionWrapper implements SqlSession {
    private SqlSession sqlSession;

    public SqlSession getSqlSession() {
        return Asserts.noNull(sqlSession);
    }

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public SqlSessionWrapper() {}

    public SqlSessionWrapper(SqlSession sqlSession) {
        setSqlSession(sqlSession);
    }

    public <T> T selectOne(String statement) {
        return getSqlSession().selectOne(statement);
    }

    public <T> T selectOne(String statement, Object parameter) {
        return getSqlSession().selectOne(statement, parameter);
    }

    public <E> List<E> selectList(String statement) {
        return getSqlSession().selectList(statement);
    }

    public <E> List<E> selectList(String statement, Object parameter) {
        return getSqlSession().selectList(statement, parameter);
    }

    public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
        return getSqlSession().selectList(statement, parameter, rowBounds);
    }

    public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
        return getSqlSession().selectMap(statement, mapKey);
    }

    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
        return getSqlSession().selectMap(statement, parameter, mapKey);
    }

    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
        return getSqlSession().selectMap(statement, parameter, mapKey, rowBounds);
    }

    public void select(String statement, Object parameter, ResultHandler handler) {
        getSqlSession().select(statement, parameter, handler);
    }

    public void select(String statement, ResultHandler handler) {
        getSqlSession().select(statement, handler);
    }

    public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {
        getSqlSession().select(statement, parameter, rowBounds, handler);
    }

    public int insert(String statement) {
        return getSqlSession().insert(statement);
    }

    public int insert(String statement, Object parameter) {
        return getSqlSession().insert(statement, parameter);
    }

    public int update(String statement) {
        return getSqlSession().update(statement);
    }

    public int update(String statement, Object parameter) {
        return getSqlSession().update(statement, parameter);
    }

    public int delete(String statement) {
        return getSqlSession().delete(statement);
    }

    public int delete(String statement, Object parameter) {
        return getSqlSession().delete(statement, parameter);
    }

    public void commit() {
        getSqlSession().commit();
    }

    public void commit(boolean force) {
        getSqlSession().commit(force);
    }

    public void rollback() {
        getSqlSession().rollback();
    }

    public void rollback(boolean force) {
        getSqlSession().rollback(force);
    }

    public List<BatchResult> flushStatements() {
        return getSqlSession().flushStatements();
    }

    public void close() {
        getSqlSession().close();
    }

    public void clearCache() {
        getSqlSession().clearCache();
    }

    public Configuration getConfiguration() {
        return getSqlSession().getConfiguration();
    }

    public <T> T getMapper(Class<T> type) {
        return getSqlSession().getMapper(type);
    }

    public Connection getConnection() {
        return getSqlSession().getConnection();
    }
}