package cn.limw.summer.hibernate.wrapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.Type;

import cn.limw.summer.util.Asserts;

/**
 * @author li
 * @version 1 (2014年11月5日 下午6:31:51)
 * @since Java7
 */
public class QueryWrapper extends BasicQueryContractWrapper implements Query {
    private Query query;

    public QueryWrapper() {}

    public QueryWrapper(Query query) {
        super(query);
        setQuery(query);
    }

    public Query getQuery() {
        return Asserts.noNull(query);
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public String getQueryString() {
        return getQuery().getQueryString();
    }

    public Integer getMaxResults() {
        return getQuery().getMaxResults();
    }

    public Query setMaxResults(int maxResults) {
        getQuery().setMaxResults(maxResults);
        return this;
    }

    public Integer getFirstResult() {
        return getQuery().getFirstResult();
    }

    public Query setFirstResult(int firstResult) {
        getQuery().setFirstResult(firstResult);
        return this;
    }

    public Query setFlushMode(FlushMode flushMode) {
        getQuery().setFlushMode(flushMode);
        return this;
    }

    public Query setCacheMode(CacheMode cacheMode) {
        getQuery().setCacheMode(cacheMode);
        return this;
    }

    public Query setCacheable(boolean cacheable) {
        getQuery().setCacheable(cacheable);
        return this;
    }

    public Query setCacheRegion(String cacheRegion) {
        getQuery().setCacheRegion(cacheRegion);
        return this;
    }

    public Query setTimeout(int timeout) {
        getQuery().setTimeout(timeout);
        return this;
    }

    public Query setFetchSize(int fetchSize) {
        getQuery().setFetchSize(fetchSize);
        return this;
    }

    public Query setReadOnly(boolean readOnly) {
        getQuery().setReadOnly(readOnly);
        return this;
    }

    public LockOptions getLockOptions() {
        return getQuery().getLockOptions();
    }

    public Query setLockOptions(LockOptions lockOptions) {
        getQuery().setLockOptions(lockOptions);
        return this;
    }

    public Query setLockMode(String alias, LockMode lockMode) {
        getQuery().setLockMode(alias, lockMode);
        return this;
    }

    public String getComment() {
        return getQuery().getComment();
    }

    public Query setComment(String comment) {
        getQuery().setComment(comment);
        return this;
    }

    public Query addQueryHint(String hint) {
        getQuery().addQueryHint(hint);
        return this;
    }

    public String[] getReturnAliases() {
        return getQuery().getReturnAliases();
    }

    public String[] getNamedParameters() {
        return getQuery().getNamedParameters();
    }

    public Iterator iterate() {
        return getQuery().iterate();
    }

    public ScrollableResults scroll() {
        return getQuery().scroll();
    }

    public ScrollableResults scroll(ScrollMode scrollMode) {
        return getQuery().scroll(scrollMode);
    }

    public List list() {
        return getQuery().list();
    }

    public Object uniqueResult() {
        return getQuery().uniqueResult();
    }

    public int executeUpdate() {
        return getQuery().executeUpdate();
    }

    public Query setParameter(int position, Object val, Type type) {
        getQuery().setParameter(position, val, type);
        return this;
    }

    public Query setParameter(String name, Object val, Type type) {
        getQuery().setParameter(name, val, type);
        return this;
    }

    public Query setParameter(int position, Object val) {
        getQuery().setParameter(position, val);
        return this;
    }

    public Query setParameter(String name, Object val) {
        getQuery().setParameter(name, val);
        return this;
    }

    public Query setParameters(Object[] values, Type[] types) {
        getQuery().setParameters(values, types);
        return this;
    }

    public Query setParameterList(String name, Collection values, Type type) {
        getQuery().setParameterList(name, values, type);
        return this;
    }

    public Query setParameterList(String name, Collection values) {
        getQuery().setParameterList(name, values);
        return this;
    }

    public Query setParameterList(String name, Object[] values, Type type) {
        getQuery().setParameterList(name, values, type);
        return this;
    }

    public Query setParameterList(String name, Object[] values) {
        getQuery().setParameterList(name, values);
        return this;
    }

    public Query setProperties(Object bean) {
        getQuery().setProperties(bean);
        return this;
    }

    public Query setProperties(Map bean) {
        getQuery().setProperties(bean);
        return this;
    }

    public Query setString(int position, String val) {
        getQuery().setString(position, val);
        return this;
    }

    public Query setCharacter(int position, char val) {
        getQuery().setCharacter(position, val);
        return this;
    }

    public Query setBoolean(int position, boolean val) {
        getQuery().setBoolean(position, val);
        return this;
    }

    public Query setByte(int position, byte val) {
        getQuery().setByte(position, val);
        return this;
    }

    public Query setShort(int position, short val) {
        getQuery().setShort(position, val);
        return this;
    }

    public Query setInteger(int position, int val) {
        getQuery().setInteger(position, val);
        return this;
    }

    public Query setLong(int position, long val) {
        getQuery().setLong(position, val);
        return this;
    }

    public Query setFloat(int position, float val) {
        getQuery().setFloat(position, val);
        return this;
    }

    public Query setDouble(int position, double val) {
        getQuery().setDouble(position, val);
        return this;
    }

    public Query setBinary(int position, byte[] val) {
        getQuery().setBinary(position, val);
        return this;
    }

    public Query setText(int position, String val) {
        getQuery().setText(position, val);
        return this;
    }

    public Query setSerializable(int position, Serializable val) {
        getQuery().setSerializable(position, val);
        return this;
    }

    public Query setLocale(int position, Locale locale) {
        getQuery().setLocale(position, locale);
        return this;
    }

    public Query setBigDecimal(int position, BigDecimal number) {
        getQuery().setBigDecimal(position, number);
        return this;
    }

    public Query setBigInteger(int position, BigInteger number) {
        getQuery().setBigInteger(position, number);
        return this;
    }

    public Query setDate(int position, Date date) {
        getQuery().setDate(position, date);
        return this;
    }

    public Query setTime(int position, Date date) {
        getQuery().setTime(position, date);
        return this;
    }

    public Query setTimestamp(int position, Date date) {
        getQuery().setTimestamp(position, date);
        return this;
    }

    public Query setCalendar(int position, Calendar calendar) {
        getQuery().setCalendar(position, calendar);
        return this;
    }

    public Query setCalendarDate(int position, Calendar calendar) {
        getQuery().setCalendarDate(position, calendar);
        return this;
    }

    public Query setString(String name, String val) {
        getQuery().setString(name, val);
        return this;
    }

    public Query setCharacter(String name, char val) {
        getQuery().setCharacter(name, val);
        return this;
    }

    public Query setBoolean(String name, boolean val) {
        getQuery().setBoolean(name, val);
        return this;
    }

    public Query setByte(String name, byte val) {
        getQuery().setByte(name, val);
        return this;
    }

    public Query setShort(String name, short val) {
        getQuery().setShort(name, val);
        return this;
    }

    public Query setInteger(String name, int val) {
        getQuery().setInteger(name, val);
        return this;
    }

    public Query setLong(String name, long val) {
        getQuery().setLong(name, val);
        return this;
    }

    public Query setFloat(String name, float val) {
        getQuery().setFloat(name, val);
        return this;
    }

    public Query setDouble(String name, double val) {
        getQuery().setDouble(name, val);
        return this;
    }

    public Query setBinary(String name, byte[] val) {
        getQuery().setBinary(name, val);
        return this;
    }

    public Query setText(String name, String val) {
        getQuery().setText(name, val);
        return this;
    }

    public Query setSerializable(String name, Serializable val) {
        getQuery().setSerializable(name, val);
        return this;
    }

    public Query setLocale(String name, Locale locale) {
        getQuery().setLocale(name, locale);
        return this;
    }

    public Query setBigDecimal(String name, BigDecimal number) {
        getQuery().setBigDecimal(name, number);
        return this;
    }

    public Query setBigInteger(String name, BigInteger number) {
        getQuery().setBigInteger(name, number);
        return this;
    }

    public Query setDate(String name, Date date) {
        getQuery().setDate(name, date);
        return this;
    }

    public Query setTime(String name, Date date) {
        getQuery().setTime(name, date);
        return this;
    }

    public Query setTimestamp(String name, Date date) {
        getQuery().setTimestamp(name, date);
        return this;
    }

    public Query setCalendar(String name, Calendar calendar) {
        getQuery().setCalendar(name, calendar);
        return this;
    }

    public Query setCalendarDate(String name, Calendar calendar) {
        getQuery().setCalendarDate(name, calendar);
        return this;
    }

    public Query setEntity(int position, Object val) {
        getQuery().setEntity(position, val);
        return this;
    }

    public Query setEntity(String name, Object val) {
        getQuery().setEntity(name, val);
        return this;
    }

    public Query setResultTransformer(ResultTransformer transformer) {
        getQuery().setResultTransformer(transformer);
        return this;
    }
}