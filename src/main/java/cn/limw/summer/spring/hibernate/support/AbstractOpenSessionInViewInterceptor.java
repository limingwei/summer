package cn.limw.summer.spring.hibernate.support;

import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.hibernate4.support.OpenSessionInViewInterceptor;

/**
 * @author li
 * @version 1 (2015年5月6日 下午5:34:55)
 * @since Java7
 */
public class AbstractOpenSessionInViewInterceptor extends OpenSessionInViewInterceptor implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    public SessionFactory getSessionFactory() {
        if (null == super.getSessionFactory()) {
            super.setSessionFactory(getSessionFactoryFromApplicationContext());
        }
        return super.getSessionFactory();
    }

    private SessionFactory getSessionFactoryFromApplicationContext() {
        try {
            return applicationContext.getBean(SessionFactory.class);
        } catch (NoSuchBeanDefinitionException e) {
            return null;
        }
    }
}