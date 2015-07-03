package cn.limw.summer.dubbo.serialize.hessian.hibernate.serializer;

import java.io.IOException;

import org.hibernate.Hibernate;
import org.hibernate.collection.internal.PersistentSet;
import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

import com.alibaba.com.caucho.hessian.io.AbstractHessianOutput;
import com.alibaba.com.caucho.hessian.io.AbstractSerializer;

/**
 * @author li
 * @version 1 (2015年2月10日 上午9:39:13)
 * @since Java7
 */
public class PersistentSetSerializer extends AbstractSerializer {
    private static final Logger log = Logs.slf4j();

    public static final PersistentCollectionSerializer INSTANCE = new PersistentCollectionSerializer();

    public void writeObject(Object obj, AbstractHessianOutput out) throws IOException {
        try {
            PersistentSet persistentSet = (PersistentSet) obj;
            if (Hibernate.isInitialized(persistentSet)) {
                out.writeObject(persistentSet.getValue()); // throw new RuntimeException("this will not happen");
            } else {
                out.writeNull();
            }
        } catch (Throwable e) {
            log.error("PersistentCollectionSerializer error type=" + obj.getClass(), e);
            out.writeNull();
        }
    }
}