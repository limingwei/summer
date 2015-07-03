package cn.limw.summer.spring.beans.factory;

/**
 * @author li
 * @version 1 (2014年12月4日 下午3:16:37)
 * @since Java7
 */
public class JustReturnFactoryBean extends SingletonFactoryBean<Object> {
    private Object value;

    public Object getObject() throws Exception {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}