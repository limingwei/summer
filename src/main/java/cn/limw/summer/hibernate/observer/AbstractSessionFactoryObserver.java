package cn.limw.summer.hibernate.observer;

import org.hibernate.SessionFactory;
import org.hibernate.SessionFactoryObserver;

/**
 * @author li
 * @version 1 (2015年1月19日 下午2:00:53)
 * @since Java7
 */
public class AbstractSessionFactoryObserver implements SessionFactoryObserver {
    private static final long serialVersionUID = -6651944030278851540L;

    public void sessionFactoryCreated(SessionFactory factory) {}

    public void sessionFactoryClosed(SessionFactory factory) {}
}
