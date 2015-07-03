package cn.limw.summer.fastjson.serializer.filter;

import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

import com.alibaba.fastjson.serializer.PropertyFilter;

/**
 * @author li
 * @version 1 (2014年11月20日 下午1:22:53)
 * @since Java7
 */
public class HibernatePropertyFilter implements PropertyFilter {
    public static final PropertyFilter INSTANCE = new HibernatePropertyFilter();

    public boolean apply(Object object, String name, Object value) {
        if (value instanceof HibernateProxy) {//hibernate代理对象
            LazyInitializer initializer = ((HibernateProxy) value).getHibernateLazyInitializer();
            if (initializer.isUninitialized()) {
                return false;
            }
        } else if (value instanceof PersistentCollection) {//实体关联集合一对多等
            PersistentCollection collection = (PersistentCollection) value;
            if (!collection.wasInitialized()) {
                return false;
            }
            Object val = collection.getValue();
            if (val == null) {
                return false;
            }
        }
        return true;
    }
}