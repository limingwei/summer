package cn.limw.summer.hibernate.internal;

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

/**
 * @author li
 * @version 1 (2015年5月5日 下午6:10:14)
 * @since Java7
 */
public class SessionFactoryImpl extends AbstractSessionFactory implements SessionFactory {
    private static final long serialVersionUID = 3752800690874000140L;

    public Reference getReference() throws NamingException {
        return null;
    }

    public SessionFactoryOptions getSessionFactoryOptions() {
        return null;
    }

    public SessionBuilder withOptions() {
        return null;
    }

    public Session openSession() throws HibernateException {
        return null;
    }

    public Session getCurrentSession() throws HibernateException {
        return null;
    }

    public StatelessSessionBuilder withStatelessOptions() {
        return null;
    }

    public StatelessSession openStatelessSession() {
        return null;
    }

    public StatelessSession openStatelessSession(Connection connection) {
        return null;
    }

    public ClassMetadata getClassMetadata(Class entityClass) {
        return null;
    }

    public ClassMetadata getClassMetadata(String entityName) {
        return null;
    }

    public CollectionMetadata getCollectionMetadata(String roleName) {
        return null;
    }

    public Map<String, ClassMetadata> getAllClassMetadata() {
        return null;
    }

    public Map getAllCollectionMetadata() {
        return null;
    }

    public Statistics getStatistics() {
        return null;
    }

    public void close() throws HibernateException {}

    public boolean isClosed() {
        return false;
    }

    public Cache getCache() {
        return null;
    }

    public void evict(Class persistentClass) throws HibernateException {}

    public void evict(Class persistentClass, Serializable id) throws HibernateException {}

    public void evictEntity(String entityName) throws HibernateException {}

    public void evictEntity(String entityName, Serializable id) throws HibernateException {}

    public void evictCollection(String roleName) throws HibernateException {}

    public void evictCollection(String roleName, Serializable id) throws HibernateException {}

    public void evictQueries(String cacheRegion) throws HibernateException {}

    public void evictQueries() throws HibernateException {}

    public Set getDefinedFilterNames() {
        return null;
    }

    public FilterDefinition getFilterDefinition(String filterName) throws HibernateException {
        return null;
    }

    public boolean containsFetchProfileDefinition(String name) {
        return false;
    }

    public TypeHelper getTypeHelper() {
        return null;
    }
}