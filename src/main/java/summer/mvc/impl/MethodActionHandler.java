package summer.mvc.impl;

import java.lang.reflect.Method;

import summer.ioc.BeanDefinition;
import summer.mvc.ActionHandler;

/**
 * @author li
 * @version 1 (2015年10月10日 上午11:23:43)
 * @since Java7
 */
public class MethodActionHandler implements ActionHandler {
    private BeanDefinition beanDefinition;

    private Method method;

    public void setBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinition = beanDefinition;
    }

    public BeanDefinition getBeanDefinition() {
        return beanDefinition;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}