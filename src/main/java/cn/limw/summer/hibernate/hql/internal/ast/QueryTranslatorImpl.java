package cn.limw.summer.hibernate.hql.internal.ast;

import java.util.Map;

import org.hibernate.QueryException;
import org.hibernate.engine.query.spi.EntityGraphQueryHint;
import org.hibernate.engine.spi.SessionFactoryImplementor;

import antlr.RecognitionException;
import antlr.collections.AST;

/**
 * 将generate方法中SqlGenerator类替换
 * @author li
 * @version 1 (2014年11月14日 下午4:23:41)
 * @since Java7
 * @see SqlGenerator
 */
public class QueryTranslatorImpl extends AbstractQueryTranslator {
    public QueryTranslatorImpl(String queryIdentifier, String query, Map enabledFilters, SessionFactoryImplementor factory, EntityGraphQueryHint entityGraphQueryHint) {
        super(queryIdentifier, query, enabledFilters, factory, entityGraphQueryHint);
    }

    public void generate(AST sqlAst) throws QueryException, RecognitionException {
        if (getSQLString() == null) {
            SqlGenerator sqlGenerator = new SqlGenerator(getFactory());
            sqlGenerator.statement(sqlAst);
            setSql(sqlGenerator.getSQL());
            if (LOG.isDebugEnabled()) {
                LOG.debugf("HQL: %s", getHql());
                LOG.debugf("SQL: %s", getSQLString());
            }
            sqlGenerator.getParseErrorHandler().throwQueryException();
            setCollectedParameterSpecifications(sqlGenerator.getCollectedParameters());
        }
    }
}