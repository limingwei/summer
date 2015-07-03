package cn.limw.summer.hibernate.wrapper;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;
import javax.naming.Reference;

import org.hibernate.Cache;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.StatelessSessionBuilder;
import org.hibernate.TypeHelper;
import org.hibernate.engine.spi.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;

import cn.limw.summer.util.Asserts;

/**
 * @author li
 * @version 1 (2014年11月14日 下午4:07:23)
 * @since Java7
 */
public class SessionFactoryWrapper implements SessionFactory {
    private static final long serialVersionUID = -4476541347224818101L;

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return Asserts.noNull(sessionFactory);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactoryWrapper() {}

    public SessionFactoryWrapper(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    public Reference getReference() throws NamingException {
        return getSessionFactory().getReference();
    }

    public SessionFactoryOptions getSessionFactoryOptions() {
        return getSessionFactory().getSessionFactoryOptions();
    }

    public SessionBuilder withOptions() {
        return getSessionFactory().withOptions();
    }

    public Session openSession() throws HibernateException {
        return getSessionFactory().openSession();
    }

    public Session getCurrentSession() throws HibernateException {
        return getSessionFactory().getCurrentSession();
    }

    public StatelessSessionBuilder withStatelessOptions() {
        return getSessionFactory().withStatelessOptions();
    }

    public StatelessSession openStatelessSession() {
        return getSessionFactory().openStatelessSession();
    }

    public StatelessSession openStatelessSession(Connection connection) {
        return getSessionFactory().openStatelessSession(connection);
    }

    public ClassMetadata getClassMetadata(Class entityClass) {
        return getSessionFactory().getClassMetadata(entityClass);
    }

    public ClassMetadata getClassMetadata(String entityName) {
        return getSessionFactory().getClassMetadata(entityName);
    }

    public CollectionMetadata getCollectionMetadata(String roleName) {
        return getSessionFactory().getCollectionMetadata(roleName);
    }

    public Map<String, ClassMetadata> getAllClassMetadata() {
        return getSessionFactory().getAllClassMetadata();
    }

    public Map getAllCollectionMetadata() {
        return getSessionFactory().getAllCollectionMetadata();
    }

    public Statistics getStatistics() {
        return getSessionFactory().getStatistics();
    }

    public void close() throws HibernateException {
        getSessionFactory().close();
    }

    public boolean isClosed() {
        return getSessionFactory().isClosed();
    }

    public Cache getCache() {
        return getSessionFactory().getCache();
    }

    public void evict(Class persistentClass) throws HibernateException {
        getSessionFactory().evict(persistentClass);
    }

    public void evict(Class persistentClass, Serializable id) throws HibernateException {
        getSessionFactory().evict(persistentClass, id);
    }

    public void evictEntity(String entityName) throws HibernateException {
        getSessionFactory().evictEntity(entityName);
    }

    public void evictEntity(String entityName, Serializable id) throws HibernateException {
        getSessionFactory().evictEntity(entityName, id);
    }

    public void evictCollection(String roleName) throws HibernateException {
        getSessionFactory().evictCollection(roleName);
    }

    public void evictCollection(String roleName, Serializable id) throws HibernateException {
        getSessionFactory().evictCollection(roleName, id);
    }

    public void evictQueries(String cacheRegion) throws HibernateException {
        getSessionFactory().evictQueries(cacheRegion);
    }

    public void evictQueries() throws HibernateException {
        getSessionFactory().evictQueries();
    }

    public Set getDefinedFilterNames() {
        return getSessionFactory().getDefinedFilterNames();
    }

    public FilterDefinition getFilterDefinition(String filterName) throws HibernateException {
        return getSessionFactory().getFilterDefinition(filterName);
    }

    public boolean containsFetchProfileDefinition(String name) {
        return getSessionFactory().containsFetchProfileDefinition(name);
    }

    public TypeHelper getTypeHelper() {
        return getSessionFactory().getTypeHelper();
    }
}