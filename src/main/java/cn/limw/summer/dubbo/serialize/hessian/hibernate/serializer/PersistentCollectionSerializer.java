package cn.limw.summer.dubbo.serialize.hessian.hibernate.serializer;

import java.io.IOException;
import java.util.HashSet;

import org.hibernate.Hibernate;
import org.hibernate.collection.internal.PersistentSet;
import org.hibernate.collection.spi.PersistentCollection;
import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

import com.alibaba.com.caucho.hessian.io.AbstractHessianOutput;
import com.alibaba.com.caucho.hessian.io.AbstractSerializer;

/**
 * @author li
 * @version 1 (2014年8月22日 下午1:39:26)
 * @since Java7
 * @see org.springframework.orm.hibernate4.support.OpenSessionInViewFilter
 */
public class PersistentCollectionSerializer extends AbstractSerializer {
    private static final Logger log = Logs.slf4j();

    public static final PersistentCollectionSerializer INSTANCE = new PersistentCollectionSerializer();

    public void writeObject(Object obj, AbstractHessianOutput out) throws IOException {
        try {
            PersistentCollection persistentCollection = (PersistentCollection) obj;
            if (Hibernate.isInitialized(persistentCollection)) {
                if (persistentCollection instanceof PersistentSet) {
                    out.writeObject(new HashSet((PersistentSet) persistentCollection));
                } else {
                    log.info("writeObject() value.type={}", obj.getClass());
                    out.writeObject(persistentCollection.getValue()); // throw new RuntimeException("this will not happen");                    
                }
            } else {
                out.writeNull();
            }
        } catch (Throwable e) {
            log.error("PersistentCollectionSerializer error type=" + obj.getClass(), e);
            out.writeNull();
        }
    }
}