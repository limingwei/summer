package summer.ioc;

import java.util.List;

/**
 * @author li
 * @version 1 (2015年10月10日 下午12:03:01)
 * @since Java7
 */
public class BeanDefinition {
    private String id;

    private Class<?> beanType;

    private List<BeanField> beanFields;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<BeanField> getBeanFields() {
        return beanFields;
    }

    public void setBeanFields(List<BeanField> beanFields) {
        this.beanFields = beanFields;
    }

    public Class<?> getBeanType() {
        return beanType;
    }

    public void setBeanType(Class<?> beanType) {
        this.beanType = beanType;
    }

    public String toString() {
        return super.toString() + ", id=" + getId() + ", beanType=" + getBeanType();
    }
}