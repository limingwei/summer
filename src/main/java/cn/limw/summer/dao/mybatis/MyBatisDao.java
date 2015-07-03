package cn.limw.summer.dao.mybatis;

import java.util.List;
import java.util.Map;

import cn.limw.summer.dao.Page;
import cn.limw.summer.mybatis.mapping.util.MappedStatementUtil;
import cn.limw.summer.util.ListUtil;

/**
 * @author li
 * @version 1 (2014年10月8日 上午10:09:59)
 * @since Java7
 */
public class MyBatisDao extends MyBatisSupport {
    public <T> T findById(Class<T> type, Integer id) {
        return getSqlSession().selectOne(type.getName() + ".find", id);
    }

    public <T> Boolean save(T entity) {
        return getSqlSession().insert(entity.getClass().getName() + ".save", entity) > 0;
    }

    public <T> List<T> list(Class<T> type, Page page) {
        return selectList(type.getName() + ".list", page);
    }

    public <T> List<Map<String, Object>> listMap(Class<T> type, Page page) {
        return selectList(type.getName() + ".listMap", page);
    }

    /**
     * @see org.apache.ibatis.session.defaults.DefaultSqlSession
     */
    public List<Map<String, Object>> listMap(String sql, Object parameter) {
        String statementId = "common." + MappedStatementUtil.sqlToStatementId(sql);
        MappedStatementUtil.insureStatement(getSqlSession(), sql, statementId);
        return selectList(statementId, parameter);
    }

    public Map<String, Object> findMap(String sql, Object parameter) {
        return ListUtil.first(listMap(sql, parameter));
    }

    public List<Map<String, Object>> listMap(String sql) {
        return listMap(sql, NO_PARAMETER);
    }

    public Map<String, Object> findMap(String sql) {
        return findMap(sql, NO_PARAMETER);
    }
}