package cn.limw.summer.spring.beans.factory.lazyinject;

import org.springframework.context.ApplicationContext;

import cn.limw.summer.util.Asserts;

/**
 * @author li
 */
@SuppressWarnings("rawtypes")
public class LazyInjectDelegateHolder {
    private ApplicationContext applicationContext;

    private LazyInject lazyInject;

    private Class requiredType;

    private Object lazyInjectDelegateTarget;

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public LazyInject getLazyInject() {
        return lazyInject;
    }

    public Object getLazyInjectDelegateTarget() {
        if (null == lazyInjectDelegateTarget) {
            ApplicationContext _applicationContext = Asserts.noNull(getApplicationContext(), "applicationContext is null");
            lazyInjectDelegateTarget = LazyInjectUtil.doGetLazyInjectDelegateTarget(_applicationContext, getRequiredType(), getLazyInject());
        }
        return lazyInjectDelegateTarget;
    }

    public Class getRequiredType() {
        return requiredType;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setLazyInject(LazyInject lazyInject) {
        this.lazyInject = lazyInject;
    }

    public void setLazyInjectDelegateTarget(Object lazyInjectDelegateTarget) {
        this.lazyInjectDelegateTarget = lazyInjectDelegateTarget;
    }

    public void setRequiredType(Class requiredType) {
        this.requiredType = requiredType;
    }
}