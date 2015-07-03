package cn.limw.summer.hibernate.observer;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;
import org.hibernate.service.ServiceRegistry;

/**
 * @author li
 * @version 1 (2015年1月19日 下午2:02:32)
 * @since Java7
 */
public class ServiceRegistrySessionFactoryObserver extends AbstractSessionFactoryObserver {
    private static final long serialVersionUID = -8074037214122794174L;

    private ServiceRegistry serviceRegistry;

    public ServiceRegistrySessionFactoryObserver(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    public void sessionFactoryClosed(SessionFactory factory) {
        ((StandardServiceRegistryImpl) getServiceRegistry()).destroy();
    }

    public ServiceRegistry getServiceRegistry() {
        return serviceRegistry;
    }
}