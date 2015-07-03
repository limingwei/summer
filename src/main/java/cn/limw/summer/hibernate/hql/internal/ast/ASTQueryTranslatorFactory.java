package cn.limw.summer.hibernate.hql.internal.ast;

import java.util.Map;

import org.hibernate.engine.query.spi.EntityGraphQueryHint;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.hql.spi.QueryTranslator;

/**
 * @author li
 * @version 1 (2014年11月14日 下午4:12:07)
 * @since Java7
 * @see SqlGenerator
 * @see QueryTranslatorImpl#generate(antlr.collections.AST)
 */
public class ASTQueryTranslatorFactory extends AbstractASTQueryTranslatorFactory {
    public QueryTranslator createQueryTranslator(String queryIdentifier, String queryString, Map filters, SessionFactoryImplementor factory, EntityGraphQueryHint entityGraphQueryHint) {
        return new QueryTranslatorImpl(queryIdentifier, queryString, filters, factory, entityGraphQueryHint);
    }
}