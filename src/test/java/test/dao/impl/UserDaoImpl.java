package test.dao.impl;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import test.model.User;

import com.unblocked.support.dao.hibernate.AbstractDao;

/**
 * @author 明伟
 */
@Repository
public class UserDaoImpl extends AbstractDao<User, String> {
    @Autowired
    HibernateTemplate hibernateTemplate;

    public HibernateTemplate getHibernateTemplate() {
        return this.hibernateTemplate;
    }

    public Session getCurrentSession() {
        return getSessionFactory().openSession();
    }
}