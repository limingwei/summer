package cn.limw.summer.dao.hibernate;

import org.hibernate.SessionFactory;

/**
 * @author li
 * @version 1 (2014年12月8日 上午11:28:29)
 * @since Java7
 */
public class Daos {
    public static HibernateDao newDao(SessionFactory sessionFactory) {
        HibernateDao hibernateDao = new HibernateDao();
        hibernateDao.setSessionFactory(sessionFactory);
        return hibernateDao;
    }
}