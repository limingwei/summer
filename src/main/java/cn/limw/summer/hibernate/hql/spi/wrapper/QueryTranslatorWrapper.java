package cn.limw.summer.hibernate.hql.spi.wrapper;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.QueryException;
import org.hibernate.ScrollableResults;
import org.hibernate.engine.spi.QueryParameters;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.event.spi.EventSource;
import org.hibernate.hql.spi.ParameterTranslations;
import org.hibernate.hql.spi.QueryTranslator;
import org.hibernate.type.Type;

import cn.limw.summer.util.Asserts;

/**
 * @author li
 * @version 1 (2014年11月14日 下午4:17:24)
 * @since Java7
 */
public class QueryTranslatorWrapper implements QueryTranslator {
    private QueryTranslator queryTranslator;

    public QueryTranslatorWrapper() {}

    public QueryTranslatorWrapper(QueryTranslator queryTranslator) {
        this.queryTranslator = queryTranslator;
    }

    public QueryTranslator getQueryTranslator() {
        return Asserts.noNull(queryTranslator);
    }

    public void setQueryTranslator(QueryTranslator queryTranslator) {
        this.queryTranslator = queryTranslator;
    }

    public void compile(Map replacements, boolean shallow) throws QueryException, MappingException {
        getQueryTranslator().compile(replacements, shallow);
    }

    public List list(SessionImplementor session, QueryParameters queryParameters) throws HibernateException {
        return getQueryTranslator().list(session, queryParameters);
    }

    public Iterator iterate(QueryParameters queryParameters, EventSource session) throws HibernateException {
        return getQueryTranslator().iterate(queryParameters, session);
    }

    public ScrollableResults scroll(QueryParameters queryParameters, SessionImplementor session) throws HibernateException {
        return getQueryTranslator().scroll(queryParameters, session);
    }

    public int executeUpdate(QueryParameters queryParameters, SessionImplementor session) throws HibernateException {
        return getQueryTranslator().executeUpdate(queryParameters, session);
    }

    public Set<Serializable> getQuerySpaces() {
        return getQueryTranslator().getQuerySpaces();
    }

    public String getQueryIdentifier() {
        return getQueryTranslator().getQueryIdentifier();
    }

    public String getSQLString() {
        return getQueryTranslator().getSQLString();
    }

    public List<String> collectSqlStrings() {
        return getQueryTranslator().collectSqlStrings();
    }

    public String getQueryString() {
        return getQueryTranslator().getQueryString();
    }

    public Map getEnabledFilters() {
        return getQueryTranslator().getEnabledFilters();
    }

    public Type[] getReturnTypes() {
        return getQueryTranslator().getReturnTypes();
    }

    public String[] getReturnAliases() {
        return getQueryTranslator().getReturnAliases();
    }

    public String[][] getColumnNames() {
        return getQueryTranslator().getColumnNames();
    }

    public ParameterTranslations getParameterTranslations() {
        return getQueryTranslator().getParameterTranslations();
    }

    public void validateScrollability() throws HibernateException {
        getQueryTranslator().validateScrollability();
    }

    public boolean containsCollectionFetches() {
        return getQueryTranslator().containsCollectionFetches();
    }

    public boolean isManipulationStatement() {
        return getQueryTranslator().isManipulationStatement();
    }

    public Class getDynamicInstantiationResultType() {
        return getQueryTranslator().getDynamicInstantiationResultType();
    }
}