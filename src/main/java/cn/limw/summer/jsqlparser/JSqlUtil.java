package cn.limw.summer.jsqlparser;

import java.io.StringReader;
import java.util.List;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.ParseException;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

/**
 * @author li
 * @version 1 (2014年11月17日 上午10:53:14)
 * @since Java7
 */
public class JSqlUtil {
    public static final Expression NULL_VALUE = new NullValue();

    public static Statement parse(String sql) {
        try {
            return new CCJSqlParser(new StringReader(sql)).Statement();
        } catch (ParseException e) {
            String message = (e + ", sql=" + sql).replaceAll("\n", " ").replaceAll("\t", " ");
            throw new RuntimeException(message);
        } catch (Exception e) {
            throw new RuntimeException(e + ", sql=" + sql, e);
        }
    }

    public static List<OrderByElement> getOrderByElements(Select select) {
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        return plainSelect.getOrderByElements();
    }

    public static List<Join> getJoins(Select select) {
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        return plainSelect.getJoins();
    }

    public static Expression getWhere(Select select) {
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        return plainSelect.getWhere();
    }

    public static Boolean isJoinForOrder(Join join, OrderByElement orderByElement) {
        Expression orderByElementExpression = orderByElement.getExpression();
        if (orderByElementExpression instanceof Column) {
            Column column = (Column) orderByElementExpression;
            Table orderByTable = column.getTable();

            FromItem rightItem = join.getRightItem();
            if (rightItem instanceof Table) {
                Table crossJoinTable = (Table) rightItem;
                if (crossJoinTable.getAlias().toString().trim().equals(orderByTable.getName().trim())) {
                    return true;
                }
            }
        }

        return false;
    }

    public static Table getOrderByTable(OrderByElement orderByElement) {
        Expression orderByElementExpression = orderByElement.getExpression();
        if (orderByElementExpression instanceof Column) {
            Column column = (Column) orderByElementExpression;
            return column.getTable();
        }
        return null;
    }

    public static void leftJoin(Join join, Expression onExpression) {
        join.setCross(false);//cross join 转 left join
        join.setLeft(true);
        join.setOnExpression(onExpression);
    }

    public static List<SelectItem> getSelectItems(Select select) {
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        return plainSelect.getSelectItems();
    }

    public static String getColumnName(SelectExpressionItem selectExpressionItem) {
        Expression expression = selectExpressionItem.getExpression();
        if (expression instanceof Column) {
            Column column = (Column) expression;
            return column.getColumnName();
        } else {
            return null;
            //net.sf.jsqlparser.statement.select.SubSelect
        }
    }
}