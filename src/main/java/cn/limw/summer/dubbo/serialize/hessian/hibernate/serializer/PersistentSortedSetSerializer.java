package cn.limw.summer.dubbo.serialize.hessian.hibernate.serializer;

import java.io.IOException;

import org.hibernate.Hibernate;
import org.hibernate.collection.internal.PersistentSortedSet;
import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

import com.alibaba.com.caucho.hessian.io.AbstractHessianOutput;

/**
 * @author li
 * @version 1 (2015年2月10日 上午10:02:19)
 * @since Java7
 */
public class PersistentSortedSetSerializer {
    private static final Logger log = Logs.slf4j();

    public static final PersistentCollectionSerializer INSTANCE = new PersistentCollectionSerializer();

    public void writeObject(Object obj, AbstractHessianOutput out) throws IOException {
        try {
            PersistentSortedSet persistentSortedSet = (PersistentSortedSet) obj;
            if (Hibernate.isInitialized(persistentSortedSet)) {
                out.writeObject(persistentSortedSet.getValue()); // throw new RuntimeException("this will not happen");
            } else {
                out.writeNull();
            }
        } catch (Throwable e) {
            log.error("PersistentCollectionSerializer error type=" + obj.getClass(), e);
            out.writeNull();
        }
    }
}