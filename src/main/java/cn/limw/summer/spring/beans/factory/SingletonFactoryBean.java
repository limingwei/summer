package cn.limw.summer.spring.beans.factory;

import org.springframework.beans.factory.FactoryBean;

import cn.limw.summer.util.Errors;
import cn.limw.summer.util.Mirrors;

/**
 * @author li
 * @version 1 (2014年11月17日 下午1:36:13)
 * @since Java7
 */
public class SingletonFactoryBean<T> implements FactoryBean<T> {
    private Class<?> objectType;

    public T getObject() throws Exception {
        throw Errors.notImplemented();
    }

    public Class<?> getObjectType() {
        return null != objectType ? objectType : (objectType = Mirrors.getActualTypeArgument(getClass(), 0));
    }

    public boolean isSingleton() {
        return true;
    }
}