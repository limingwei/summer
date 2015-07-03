package cn.limw.summer.spring.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateTemplate;

/**
 * @author li
 * @version 1 (2015年6月3日 下午2:15:36)
 * @since Java7
 */
public class AbstractHibernateTemplate extends HibernateTemplate {
    public AbstractHibernateTemplate() {}

    public AbstractHibernateTemplate(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}