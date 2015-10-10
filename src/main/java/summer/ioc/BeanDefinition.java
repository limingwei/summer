package summer.ioc;

/**
 * @author li
 * @version 1 (2015年10月10日 下午12:03:01)
 * @since Java7
 */
public class BeanDefinition {
    private Class<?> beanType;

    public Class<?> getBeanType() {
        return beanType;
    }

    public void setBeanType(Class<?> beanType) {
        this.beanType = beanType;
    }
}