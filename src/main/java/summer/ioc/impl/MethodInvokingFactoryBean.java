package summer.ioc.impl;

import summer.ioc.FactoryBean;

/**
 * @author li
 * @version 1 (2015年10月13日 下午3:06:16)
 * @since Java7
 */
public class MethodInvokingFactoryBean implements FactoryBean<Object> {
    private Class<?> objectType;

    public Class<?> getObjectType() {
        return this.objectType;
    }

    public void setObjectType(Class<?> objectType) {
        this.objectType = objectType;
    }

    public Object getObject() {
        throw new RuntimeException();
    }
}