package cn.limw.summer.hibernate.wrapper;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SharedSessionContract;
import org.hibernate.Transaction;
import org.hibernate.procedure.ProcedureCall;

import cn.limw.summer.util.Asserts;

/**
 * @author li
 * @version 1 (2014年11月14日 下午2:54:08)
 * @since Java7
 */
public class SharedSessionContractWrapper implements SharedSessionContract {
    private static final long serialVersionUID = 3448168858550238172L;

    private SharedSessionContract sharedSessionContract;

    public SharedSessionContract getSharedSessionContract() {
        return Asserts.noNull(sharedSessionContract);
    }

    public void setSharedSessionContract(SharedSessionContract sharedSessionContract) {
        this.sharedSessionContract = sharedSessionContract;
    }

    public SharedSessionContractWrapper() {}

    public SharedSessionContractWrapper(SharedSessionContract sharedSessionContract) {
        setSharedSessionContract(sharedSessionContract);
    }

    public String getTenantIdentifier() {
        return getSharedSessionContract().getTenantIdentifier();
    }

    public Transaction beginTransaction() {
        return getSharedSessionContract().beginTransaction();
    }

    public Transaction getTransaction() {
        return getSharedSessionContract().getTransaction();
    }

    public Query getNamedQuery(String queryName) {
        return getSharedSessionContract().getNamedQuery(queryName);
    }

    public Query createQuery(String queryString) {
        return getSharedSessionContract().createQuery(queryString);
    }

    public SQLQuery createSQLQuery(String queryString) {
        return getSharedSessionContract().createSQLQuery(queryString);
    }

    public ProcedureCall getNamedProcedureCall(String name) {
        return getSharedSessionContract().getNamedProcedureCall(name);
    }

    public ProcedureCall createStoredProcedureCall(String procedureName) {
        return getSharedSessionContract().createStoredProcedureCall(procedureName);
    }

    public ProcedureCall createStoredProcedureCall(String procedureName, Class... resultClasses) {
        return getSharedSessionContract().createStoredProcedureCall(procedureName, resultClasses);
    }

    public ProcedureCall createStoredProcedureCall(String procedureName, String... resultSetMappings) {
        return getSharedSessionContract().createStoredProcedureCall(procedureName, resultSetMappings);
    }

    public Criteria createCriteria(Class persistentClass) {
        return getSharedSessionContract().createCriteria(persistentClass);
    }

    public Criteria createCriteria(Class persistentClass, String alias) {
        return getSharedSessionContract().createCriteria(persistentClass, alias);
    }

    public Criteria createCriteria(String entityName) {
        return getSharedSessionContract().createCriteria(entityName);
    }

    public Criteria createCriteria(String entityName, String alias) {
        return getSharedSessionContract().createCriteria(entityName, alias);
    }
}