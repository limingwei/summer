package cn.limw.summer.dubbo.serialize.hessian.hibernate.serializer;

import java.io.IOException;
import java.util.ArrayList;

import org.hibernate.Hibernate;
import org.hibernate.collection.internal.PersistentBag;
import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

import com.alibaba.com.caucho.hessian.io.AbstractHessianOutput;
import com.alibaba.com.caucho.hessian.io.AbstractSerializer;

/**
 * @author li
 * @version 1 (2014年12月4日 下午5:56:45)
 * @since Java7
 */
public class PersistentBagSerializer extends AbstractSerializer {
    private static final Logger log = Logs.slf4j();

    public static final PersistentBagSerializer INSTANCE = new PersistentBagSerializer();

    public void writeObject(Object obj, AbstractHessianOutput out) throws IOException {
        try {
            PersistentBag persistentBag = (PersistentBag) obj;
            if (Hibernate.isInitialized(persistentBag)) {
                out.writeObject(new ArrayList(persistentBag)); // throw new RuntimeException("this will not happen");
            } else {
                out.writeNull();
            }
        } catch (Throwable e) {
            log.error("PersistentBagSerializer error type=" + obj.getClass(), e);
            out.writeNull();
        }
    }
}