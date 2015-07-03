package cn.limw.summer.hibernate.hql.internal.ast;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.hql.internal.ast.SqlGenerator;

/**
 * @author li
 * @version 1 (2015年6月3日 下午2:09:41)
 * @since Java7
 */
public class AbstractSqlGenerator extends SqlGenerator {
    public AbstractSqlGenerator(SessionFactoryImplementor sessionFactoryImplementor) {
        super(sessionFactoryImplementor);
    }
}