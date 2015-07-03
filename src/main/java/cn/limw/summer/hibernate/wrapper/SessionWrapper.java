package cn.limw.summer.hibernate.wrapper;

import java.io.Serializable;
import java.sql.Connection;

import org.hibernate.CacheMode;
import org.hibernate.Filter;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.LobHelper;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.NaturalIdLoadAccess;
import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionEventListener;
import org.hibernate.SessionFactory;
import org.hibernate.SharedSessionBuilder;
import org.hibernate.SimpleNaturalIdLoadAccess;
import org.hibernate.TypeHelper;
import org.hibernate.UnknownProfileException;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;
import org.hibernate.stat.SessionStatistics;

import cn.limw.summer.util.Asserts;

/**
 * @author li
 * @version 1 (2014年11月14日 下午2:53:29)
 * @since Java7
 */
public class SessionWrapper extends SharedSessionContractWrapper implements Session {
    private static final long serialVersionUID = 442813544712629998L;

    private Session session;

    public Session getSession() {
        return Asserts.noNull(session);
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public SessionWrapper() {}

    public SessionWrapper(Session session) {
        super(session);
        setSession(session);
    }

    public SharedSessionBuilder sessionWithOptions() {
        return getSession().sessionWithOptions();
    }

    public void flush() throws HibernateException {
        getSession().flush();
    }

    public void setFlushMode(FlushMode flushMode) {
        getSession().setFlushMode(flushMode);
    }

    public FlushMode getFlushMode() {
        return getSession().getFlushMode();
    }

    public void setCacheMode(CacheMode cacheMode) {
        getSession().setCacheMode(cacheMode);
    }

    public CacheMode getCacheMode() {
        return getSession().getCacheMode();
    }

    public SessionFactory getSessionFactory() {
        return getSession().getSessionFactory();
    }

    public Connection close() throws HibernateException {
        return getSession().close();
    }

    public void cancelQuery() throws HibernateException {
        getSession().cancelQuery();
    }

    public boolean isOpen() {
        return getSession().isOpen();
    }

    public boolean isConnected() {
        return getSession().isConnected();
    }

    public boolean isDirty() throws HibernateException {
        return getSession().isDirty();
    }

    public boolean isDefaultReadOnly() {
        return getSession().isDefaultReadOnly();
    }

    public void setDefaultReadOnly(boolean readOnly) {
        getSession().setDefaultReadOnly(readOnly);
    }

    public Serializable getIdentifier(Object object) {
        return getSession().getIdentifier(object);
    }

    public boolean contains(Object object) {
        return getSession().contains(object);
    }

    public void evict(Object object) {
        getSession().evict(object);
    }

    public Object load(Class theClass, Serializable id, LockMode lockMode) {
        return getSession().load(theClass, id, lockMode);
    }

    public Object load(Class theClass, Serializable id, LockOptions lockOptions) {
        return getSession().load(theClass, id, lockOptions);
    }

    public Object load(String entityName, Serializable id, LockMode lockMode) {
        return getSession().load(entityName, id, lockMode);
    }

    public Object load(String entityName, Serializable id, LockOptions lockOptions) {
        return getSession().load(entityName, id, lockOptions);
    }

    public Object load(Class theClass, Serializable id) {
        return getSession().load(theClass, id);
    }

    public Object load(String entityName, Serializable id) {
        return getSession().load(entityName, id);
    }

    public void load(Object object, Serializable id) {
        getSession().load(object, id);
    }

    public void replicate(Object object, ReplicationMode replicationMode) {
        getSession().replicate(object, replicationMode);
    }

    public void replicate(String entityName, Object object, ReplicationMode replicationMode) {
        getSession().replicate(entityName, object, replicationMode);
    }

    public Serializable save(Object object) {
        return getSession().save(object);
    }

    public Serializable save(String entityName, Object object) {
        return getSession().save(entityName, object);
    }

    public void saveOrUpdate(Object object) {
        getSession().saveOrUpdate(object);
    }

    public void saveOrUpdate(String entityName, Object object) {
        getSession().saveOrUpdate(entityName, object);
    }

    public void update(Object object) {
        getSession().update(object);
    }

    public void update(String entityName, Object object) {
        getSession().update(entityName, object);
    }

    public Object merge(Object object) {
        return getSession().merge(object);
    }

    public Object merge(String entityName, Object object) {
        return getSession().merge(entityName, object);
    }

    public void persist(Object object) {
        getSession().persist(object);
    }

    public void persist(String entityName, Object object) {
        getSession().persist(entityName, object);
    }

    public void delete(Object object) {
        getSession().delete(object);
    }

    public void delete(String entityName, Object object) {
        getSession().delete(entityName, object);
    }

    public void lock(Object object, LockMode lockMode) {
        getSession().lock(object, lockMode);
    }

    public void lock(String entityName, Object object, LockMode lockMode) {
        getSession().lock(entityName, object, lockMode);
    }

    public LockRequest buildLockRequest(LockOptions lockOptions) {
        return getSession().buildLockRequest(lockOptions);
    }

    public void refresh(Object object) {
        getSession().refresh(object);
    }

    public void refresh(String entityName, Object object) {
        getSession().refresh(entityName, object);
    }

    public void refresh(Object object, LockMode lockMode) {
        getSession().refresh(object, lockMode);
    }

    public void refresh(Object object, LockOptions lockOptions) {
        getSession().refresh(object, lockOptions);
    }

    public void refresh(String entityName, Object object, LockOptions lockOptions) {
        getSession().refresh(entityName, object, lockOptions);
    }

    public LockMode getCurrentLockMode(Object object) {
        return getSession().getCurrentLockMode(object);
    }

    public Query createFilter(Object collection, String queryString) {
        return getSession().createFilter(collection, queryString);
    }

    public void clear() {
        getSession().clear();
    }

    public Object get(Class clazz, Serializable id) {
        return getSession().get(clazz, id);
    }

    public Object get(Class clazz, Serializable id, LockMode lockMode) {
        return getSession().get(clazz, id, lockMode);
    }

    public Object get(Class clazz, Serializable id, LockOptions lockOptions) {
        return getSession().get(clazz, id, lockOptions);
    }

    public Object get(String entityName, Serializable id) {
        return getSession().get(entityName, id);
    }

    public Object get(String entityName, Serializable id, LockMode lockMode) {
        return getSession().get(entityName, id, lockMode);
    }

    public Object get(String entityName, Serializable id, LockOptions lockOptions) {
        return getSession().get(entityName, id, lockOptions);
    }

    public String getEntityName(Object object) {
        return getSession().getEntityName(object);
    }

    public IdentifierLoadAccess byId(String entityName) {
        return getSession().byId(entityName);
    }

    public IdentifierLoadAccess byId(Class entityClass) {
        return getSession().byId(entityClass);
    }

    public NaturalIdLoadAccess byNaturalId(String entityName) {
        return getSession().byNaturalId(entityName);
    }

    public NaturalIdLoadAccess byNaturalId(Class entityClass) {
        return getSession().byNaturalId(entityClass);
    }

    public SimpleNaturalIdLoadAccess bySimpleNaturalId(String entityName) {
        return getSession().bySimpleNaturalId(entityName);
    }

    public SimpleNaturalIdLoadAccess bySimpleNaturalId(Class entityClass) {
        return getSession().bySimpleNaturalId(entityClass);
    }

    public Filter enableFilter(String filterName) {
        return getSession().enableFilter(filterName);
    }

    public Filter getEnabledFilter(String filterName) {
        return getSession().getEnabledFilter(filterName);
    }

    public void disableFilter(String filterName) {
        getSession().disableFilter(filterName);
    }

    public SessionStatistics getStatistics() {
        return getSession().getStatistics();
    }

    public boolean isReadOnly(Object entityOrProxy) {
        return getSession().isReadOnly(entityOrProxy);
    }

    public void setReadOnly(Object entityOrProxy, boolean readOnly) {
        getSession().setReadOnly(entityOrProxy, readOnly);
    }

    public void doWork(Work work) throws HibernateException {
        getSession().doWork(work);
    }

    public <T> T doReturningWork(ReturningWork<T> work) throws HibernateException {
        return getSession().doReturningWork(work);
    }

    public Connection disconnect() {
        return getSession().disconnect();
    }

    public void reconnect(Connection connection) {
        getSession().reconnect(connection);
    }

    public boolean isFetchProfileEnabled(String name) throws UnknownProfileException {
        return getSession().isFetchProfileEnabled(name);
    }

    public void enableFetchProfile(String name) throws UnknownProfileException {
        getSession().enableFetchProfile(name);
    }

    public void disableFetchProfile(String name) throws UnknownProfileException {
        getSession().disableFetchProfile(name);
    }

    public TypeHelper getTypeHelper() {
        return getSession().getTypeHelper();
    }

    public LobHelper getLobHelper() {
        return getSession().getLobHelper();
    }

    public void addEventListeners(SessionEventListener... listeners) {
        getSession().addEventListeners(listeners);
    }
}