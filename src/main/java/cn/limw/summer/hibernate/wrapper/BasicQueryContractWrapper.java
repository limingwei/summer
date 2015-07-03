package cn.limw.summer.hibernate.wrapper;

import org.hibernate.BasicQueryContract;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.type.Type;

import cn.limw.summer.util.Asserts;

/**
 * @author li
 * @version 1 (2014年11月5日 下午6:35:39)
 * @since Java7
 */
public class BasicQueryContractWrapper implements BasicQueryContract {
    private BasicQueryContract basicQueryContract;

    public BasicQueryContractWrapper() {}

    public BasicQueryContractWrapper(BasicQueryContract basicQueryContract) {
        setBasicQueryContract(basicQueryContract);
    }

    public BasicQueryContract getBasicQueryContract() {
        return Asserts.noNull(basicQueryContract);
    }

    public void setBasicQueryContract(BasicQueryContract basicQueryContract) {
        this.basicQueryContract = basicQueryContract;
    }

    public FlushMode getFlushMode() {
        return getBasicQueryContract().getFlushMode();
    }

    public BasicQueryContract setFlushMode(FlushMode flushMode) {
        getBasicQueryContract().setFlushMode(flushMode);
        return this;
    }

    public CacheMode getCacheMode() {
        return getBasicQueryContract().getCacheMode();
    }

    public BasicQueryContract setCacheMode(CacheMode cacheMode) {
        getBasicQueryContract().setCacheMode(cacheMode);
        return this;
    }

    public boolean isCacheable() {
        return getBasicQueryContract().isCacheable();
    }

    public BasicQueryContract setCacheable(boolean cacheable) {
        getBasicQueryContract().setCacheable(cacheable);
        return this;
    }

    public String getCacheRegion() {
        return getBasicQueryContract().getCacheRegion();
    }

    public BasicQueryContract setCacheRegion(String cacheRegion) {
        getBasicQueryContract().setCacheRegion(cacheRegion);
        return this;
    }

    public Integer getTimeout() {
        return getBasicQueryContract().getTimeout();
    }

    public BasicQueryContract setTimeout(int timeout) {
        getBasicQueryContract().setTimeout(timeout);
        return this;
    }

    public Integer getFetchSize() {
        return getBasicQueryContract().getFetchSize();
    }

    public BasicQueryContract setFetchSize(int fetchSize) {
        getBasicQueryContract().setFetchSize(fetchSize);
        return this;
    }

    public boolean isReadOnly() {
        return getBasicQueryContract().isReadOnly();
    }

    public BasicQueryContract setReadOnly(boolean readOnly) {
        getBasicQueryContract().setReadOnly(readOnly);
        return this;
    }

    public Type[] getReturnTypes() {
        return getBasicQueryContract().getReturnTypes();
    }
}