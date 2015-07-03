package cn.limw.summer.hibernate.hql.internal.ast;

import java.util.List;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.Select;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.slf4j.Logger;

import cn.limw.summer.jsqlparser.JSqlUtil;
import cn.limw.summer.util.Logs;

/**
 * getSQL时候将cross join替换为left join
 * @author li
 * @version 1 (2014年11月14日 下午4:46:54)
 * @since Java7
 * @see ASTQueryTranslatorFactory
 * @see QueryTranslatorImpl#generate(antlr.collections.AST)
 */
public class SqlGenerator extends AbstractSqlGenerator {
    private static final Logger log = Logs.slf4j();

    private SessionFactoryImplementor sessionFactoryImplementor;

    public SqlGenerator(SessionFactoryImplementor sessionFactoryImplementor) {
        super(sessionFactoryImplementor);
        this.sessionFactoryImplementor = sessionFactoryImplementor;
    }

    public String getSQL() {
        String sql = super.getSQL();
        if (sql.contains("select ") && !sql.toLowerCase().startsWith("select count(")) { // 有者可能但不一定需要转换
            String handledSql = handleSql(sql);
            log.debug("SqlGenerator handledSql, <{}> -> <{}>", sql, handledSql);
            return handledSql;
        } else {
            return sql; // 不需要转换
        }
    }

    private String handleSql(String sql) {
        try {
            return doHandleSql(sql);
        } catch (Throwable e) {
            log.error("handleSql error, SQL=" + sql, e);
            return sql;
        }
    }

    public String doHandleSql(String sql) {
        Statement statement = JSqlUtil.parse(sql);
        Expression onExpression = null;

        if (statement instanceof Select) {
            if (sql.contains(" cross join ") && sql.contains(" order by ")) {
                List<OrderByElement> orderByElements = JSqlUtil.getOrderByElements((Select) statement);
                List<Join> joins = JSqlUtil.getJoins((Select) statement);

                for (OrderByElement orderByElement : orderByElements) {
                    for (Join join : joins) {
                        if (JSqlUtil.isJoinForOrder(join, orderByElement)) {
                            onExpression = SqlGeneratorUtil.getOnExpression(JSqlUtil.getWhere((Select) statement), JSqlUtil.getOrderByTable(orderByElement).getName());
                            JSqlUtil.leftJoin(join, onExpression);
                        }
                    }
                }
            }
        }

        if (null == onExpression) { // 不需要转换, 返回原SQL
            return sql;
        } else {
            return SqlGeneratorUtil.replaceOnExpressionToTrue(statement.toString(), onExpression.toString());
        }
    }

    public SessionFactoryImplementor getSessionFactoryImplementor() {
        return sessionFactoryImplementor;
    }
}