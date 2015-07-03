package cn.limw.summer.hibernate.hql.internal.ast;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;

/**
 * @author li
 * @version 1 (2014年11月17日 上午10:57:06)
 * @since Java7
 */
public class SqlGeneratorUtil {
    public static String replaceOnExpressionToTrue(String handledSql, String onSql) {
        int onBeginIndex = handledSql.lastIndexOf(onSql);
        int onEndIndex = onBeginIndex + onSql.length();
        return handledSql.substring(0, onBeginIndex) + " (true OR " + handledSql.substring(onBeginIndex, onEndIndex) + ")" + handledSql.substring(onEndIndex);//把where中的 cross join 条件替换为永真
    }

    public static Expression getOnExpression(Expression where, String tableAlias) {
        if (where instanceof AndExpression) {
            return getOnExpressionFromAnd((AndExpression) where, tableAlias);
        } else if (where instanceof EqualsTo) {
            return getOnExpressionFromEqualsTo((EqualsTo) where, tableAlias);
        } else {
            return null;
        }
    }

    public static Expression getOnExpressionFromAnd(AndExpression andExpression, String tableAlias) {
        Expression expression = getOnExpression(andExpression.getLeftExpression(), tableAlias);
        if (null == expression) {
            expression = getOnExpression(andExpression.getRightExpression(), tableAlias);
        }
        return expression;
    }

    public static Expression getOnExpressionFromEqualsTo(EqualsTo equalsTo, String tableAlias) {
        Expression rightExpression = equalsTo.getRightExpression();
        Column column = (Column) rightExpression;
        Table table = column.getTable();
        if (table.getName().equals(tableAlias)) {
            return equalsTo;
        }
        return null;
    }
}