package cn.limw.summer.dubbo.serialize.hessian.hibernate.serializer;

import java.io.IOException;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

import com.alibaba.com.caucho.hessian.io.AbstractHessianOutput;
import com.alibaba.com.caucho.hessian.io.AbstractSerializer;

/**
 * @author li
 * @version 1 (2014年8月22日 下午2:33:20)
 * @since Java7
 */
public class HibernateProxySerializer extends AbstractSerializer {
    private static final Logger log = Logs.slf4j();

    public static final HibernateProxySerializer INSTANCE = new HibernateProxySerializer();

    public void writeObject(Object obj, AbstractHessianOutput out) throws IOException {
        try {
            HibernateProxy hibernateProxy = (HibernateProxy) obj;
            if (Hibernate.isInitialized(hibernateProxy)) {
                out.writeObject(hibernateProxy.getHibernateLazyInitializer().getImplementation()); // throw new RuntimeException("this will not happen");
            } else {
                out.writeNull();
            }
        } catch (Throwable e) {
            log.error("PersistentCollectionSerializer error type=" + obj.getClass(), e);
            out.writeNull();
        }
    }
}