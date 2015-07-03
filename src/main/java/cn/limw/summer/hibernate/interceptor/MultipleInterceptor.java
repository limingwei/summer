package cn.limw.summer.hibernate.interceptor;

import java.io.Serializable;
import java.util.List;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Interceptor;
import org.hibernate.type.Type;

/**
 * onSave – 保存数据的时候调用，数据还没有保存到数据库.
 * onFlushDirty – 更新数据时调用，但数据还没有更新到数据库
 * onDelete – 删除时调用.
 * preFlush – 保存，删除，更新 在提交之前调用 (通常在 postFlush 之前).
 * postFlush – 提交之后调用(commit之后)
 * 组合多个Interceptor为一个
 * @author li
 * @version 1 (2014年10月20日 下午4:15:57)
 * @since Java7
 */
public class MultipleInterceptor extends EmptyInterceptor {
    private static final long serialVersionUID = 270184986170613062L;

    private List<Interceptor> interceptors;

    /**
     * 保存前
     */
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        for (Interceptor interceptor : getInterceptors()) {
            interceptor.onSave(entity, id, state, propertyNames, types);
        }
        return super.onSave(entity, id, state, propertyNames, types);
    }

    /**
     * 更新前
     */
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        for (Interceptor interceptor : getInterceptors()) {
            interceptor.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
        }
        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public void setInterceptors(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
    }
}