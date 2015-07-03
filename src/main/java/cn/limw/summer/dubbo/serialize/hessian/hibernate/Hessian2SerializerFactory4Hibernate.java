package cn.limw.summer.dubbo.serialize.hessian.hibernate;

import org.hibernate.collection.internal.PersistentBag;
import org.hibernate.collection.internal.PersistentSet;
import org.hibernate.collection.internal.PersistentSortedSet;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;

import cn.limw.summer.dubbo.serialize.hessian.hibernate.serializer.HibernateProxySerializer;
import cn.limw.summer.dubbo.serialize.hessian.hibernate.serializer.PersistentBagSerializer;
import cn.limw.summer.dubbo.serialize.hessian.hibernate.serializer.PersistentCollectionSerializer;
import cn.limw.summer.dubbo.serialize.hessian.hibernate.serializer.PersistentSetSerializer;
import cn.limw.summer.dubbo.serialize.hessian.hibernate.serializer.PersistentSortedSetSerializer;

import com.alibaba.com.caucho.hessian.io.HessianProtocolException;
import com.alibaba.com.caucho.hessian.io.Serializer;
import com.alibaba.com.caucho.hessian.io.SerializerFactory;

/**
 * @author li
 * @version 1 (2014年8月22日 下午1:35:46)
 * @since Java7
 * @see com.alibaba.dubbo.common.serialize.support.hessian.Hessian2SerializerFactory
 */
public class Hessian2SerializerFactory4Hibernate extends SerializerFactory {
    public static final SerializerFactory SERIALIZER_FACTORY = new Hessian2SerializerFactory4Hibernate();

    public ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public Serializer getSerializer(Class type) throws HessianProtocolException {
        if (PersistentSortedSet.class.isAssignableFrom(type)) {
            return PersistentSortedSetSerializer.INSTANCE;
        } else if (PersistentSet.class.isAssignableFrom(type)) {
            return PersistentSetSerializer.INSTANCE;
        } else if (PersistentBag.class.equals(type)) {
            return PersistentBagSerializer.INSTANCE;
        } else if (PersistentCollection.class.isAssignableFrom(type)) { // PersistentCollection 包含 List Set Map
            return PersistentCollectionSerializer.INSTANCE;
        } else if (HibernateProxy.class.isAssignableFrom(type)) {
            return HibernateProxySerializer.INSTANCE;
        } else {
            return super.getSerializer(type);
        }
    }
}