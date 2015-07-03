package cn.limw.summer.hibernate.wrapper;

import java.util.Collection;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.MappingException;
import org.hibernate.SQLQuery;
import org.hibernate.engine.query.spi.sql.NativeSQLQueryReturn;
import org.hibernate.type.Type;

import cn.limw.summer.util.Asserts;

/**
 * @author li
 * @version 1 (2014年11月5日 下午6:34:12)
 * @since Java7
 */
public class SqlQueryWrapper extends QueryWrapper implements SQLQuery {
    private SQLQuery sqlQuery;

    public SqlQueryWrapper() {}

    public SqlQueryWrapper(SQLQuery sqlQuery) {
        super(sqlQuery);
        setSqlQuery(sqlQuery);
    }

    public SQLQuery getSqlQuery() {
        return Asserts.noNull(sqlQuery);
    }

    public void setSqlQuery(SQLQuery sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    public Collection<String> getSynchronizedQuerySpaces() {
        return getSqlQuery().getSynchronizedQuerySpaces();
    }

    public SQLQuery addSynchronizedQuerySpace(String querySpace) {
        getSqlQuery().addSynchronizedQuerySpace(querySpace);
        return this;
    }

    public SQLQuery addSynchronizedEntityName(String entityName) throws MappingException {
        getSqlQuery().addSynchronizedEntityName(entityName);
        return this;
    }

    public SQLQuery addSynchronizedEntityClass(Class entityClass) throws MappingException {
        getSqlQuery().addSynchronizedEntityClass(entityClass);
        return this;
    }

    public SQLQuery setResultSetMapping(String name) {
        getSqlQuery().setResultSetMapping(name);
        return this;
    }

    public boolean isCallable() {
        return getSqlQuery().isCallable();
    }

    public List<NativeSQLQueryReturn> getQueryReturns() {
        return getSqlQuery().getQueryReturns();
    }

    public SQLQuery addScalar(String columnAlias) {
        getSqlQuery().addScalar(columnAlias);
        return this;
    }

    public SQLQuery addScalar(String columnAlias, Type type) {
        getSqlQuery().addScalar(columnAlias, type);
        return this;
    }

    public RootReturn addRoot(String tableAlias, String entityName) {
        return getSqlQuery().addRoot(tableAlias, entityName);
    }

    public RootReturn addRoot(String tableAlias, Class entityType) {
        return getSqlQuery().addRoot(tableAlias, entityType);
    }

    public SQLQuery addEntity(String entityName) {
        getSqlQuery().addEntity(entityName);
        return this;
    }

    public SQLQuery addEntity(String tableAlias, String entityName) {
        getSqlQuery().addEntity(tableAlias, entityName);
        return this;
    }

    public SQLQuery addEntity(String tableAlias, String entityName, LockMode lockMode) {
        getSqlQuery().addEntity(tableAlias, entityName, lockMode);
        return this;
    }

    public SQLQuery addEntity(Class entityType) {
        getSqlQuery().addEntity(entityType);
        return this;
    }

    public SQLQuery addEntity(String tableAlias, Class entityType) {
        getSqlQuery().addEntity(tableAlias, entityType);
        return this;
    }

    public SQLQuery addEntity(String tableAlias, Class entityName, LockMode lockMode) {
        getSqlQuery().addEntity(tableAlias, entityName, lockMode);
        return this;
    }

    public FetchReturn addFetch(String tableAlias, String ownerTableAlias, String joinPropertyName) {
        return getSqlQuery().addFetch(tableAlias, ownerTableAlias, joinPropertyName);
    }

    public SQLQuery addJoin(String tableAlias, String path) {
        getSqlQuery().addJoin(tableAlias, path);
        return this;
    }

    public SQLQuery addJoin(String tableAlias, String ownerTableAlias, String joinPropertyName) {
        getSqlQuery().addJoin(tableAlias, ownerTableAlias, joinPropertyName);
        return this;
    }

    public SQLQuery addJoin(String tableAlias, String path, LockMode lockMode) {
        getSqlQuery().addJoin(tableAlias, path, lockMode);
        return this;
    }
}