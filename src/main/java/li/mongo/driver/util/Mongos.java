package li.mongo.driver.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import li.mongo.driver.MongoPreparedStatement;
import li.mongo.driver.model.Holder;
import li.mongo.driver.model.Page;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.Limit;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.update.Update;

import org.apache.log4j.Logger;

import com.mongodb.QueryOperators;
import com.unblocked.support.util.Errors;
import com.unblocked.support.util.MapUtil;

/**
 * @author li
 * @version 1 2014年3月7日下午12:58:04
 */
public class Mongos {
    private static final Logger log = Logs.get();

    public static final String SET = "$set", UNSET = "$unset", INC = "$inc";

    /**
     * 组装MongoDb形式的插入列
     */
    public static Map<String, Object> getInsertFields(Insert insert, MongoPreparedStatement preparedStatement) {
        Map<String, Object> insertFields = new HashMap<String, Object>();
        List<Column> columns = insert.getColumns();
        ExpressionList expressionList = (ExpressionList) insert.getItemsList();
        for (int i = 0; i < columns.size(); i++) {
            Expression expression = expressionList.getExpressions().get(i);
            insertFields.put(columns.get(i).getColumnName(), Parsers.getValueOrHolder(expression, preparedStatement));
        }
        log.trace("getInsertFields() insertFields = " + insertFields + ", insert = " + insert + ", preparedStatement = " + preparedStatement);
        return insertFields;
    }

    /**
     * 组装MongoDb形式的更新列
     */
    public static Map<String, Object> getUpdateFields(Update update, MongoPreparedStatement preparedStatement) {
        Map<String, Object> updateFields = new HashMap<String, Object>();
        Map<String, Object> setValues = new HashMap<String, Object>();
        Map<String, Object> unSetValues = new HashMap<String, Object>();
        Map<String, Object> incValues = new HashMap<String, Object>();
        List<Column> columns = update.getColumns();
        List<Expression> expressions = update.getExpressions();
        for (int i = 0; i < columns.size(); i++) {
            Expression expression = expressions.get(i);
            if (expression instanceof Addition) {
                Expression left = ((Addition) expression).getLeftExpression();
                if (left instanceof Column) {
                    Column column = (Column) left;
                    if (column.getColumnName().equals(columns.get(i).getColumnName())) {
                        Expression right = ((Addition) expression).getRightExpression();
                        incValues.put(column.getColumnName(), Parsers.getValueOrHolder(right, preparedStatement));
                    } else {
                        throw new RuntimeException("自增字段左表达式和右表达式的初始量需要是同一个列");
                    }
                } else {
                    throw new RuntimeException("自增字段左表达式只允许是列");
                }
            } else {
                Object valueOrHolder = Parsers.getValueOrHolder(expression, preparedStatement);
                if (null == valueOrHolder) {
                    unSetValues.put(columns.get(i).getColumnName(), 1);// unset
                } else {
                    setValues.put(columns.get(i).getColumnName(), valueOrHolder);
                }
            }
        }
        updateFields.put(SET, setValues);
        updateFields.put(UNSET, unSetValues);
        updateFields.put(INC, incValues);
        log.trace("getUpdateFields() update = " + update + ", updateFields = " + updateFields + ", preparedStatement = " + preparedStatement);
        return updateFields;
    }

    /**
     * 组装Mongo形式的Where查询条件
     */
    public static Map<String, Object> getWhere(Expression expression, MongoPreparedStatement preparedStatement) {
        Map<String, Object> where;
        if (null == expression) {
            where = new HashMap<String, Object>();
        } else if (expression instanceof AndExpression) {
            List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
            values.add(getWhere(((AndExpression) expression).getLeftExpression(), preparedStatement));
            values.add(getWhere(((AndExpression) expression).getRightExpression(), preparedStatement));
            where = MapUtil.toMap(QueryOperators.AND, values);
        } else if (expression instanceof OrExpression) {
            List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
            values.add(getWhere(((OrExpression) expression).getLeftExpression(), preparedStatement));
            values.add(getWhere(((OrExpression) expression).getRightExpression(), preparedStatement));
            where = MapUtil.toMap(QueryOperators.OR, values);
        } else if (expression instanceof Parenthesis) {// 括号
            where = getWhere(((Parenthesis) expression).getExpression(), preparedStatement);
        } else if (expression instanceof InExpression) {
            ExpressionList expressionList = (ExpressionList) ((InExpression) expression).getRightItemsList();
            List<Expression> expressions = expressionList.getExpressions();
            List<Object> valueList = new ArrayList<Object>();
            for (Expression ex : expressions) {
                Object valueOrHolder = Parsers.getValueOrHolder(ex, preparedStatement);
                valueList.add(valueOrHolder);
            }
            where = MapUtil.toMap(QueryOperators.IN, valueList.toArray());
        } else if (expression instanceof IsNullExpression) {
            Map<String, Object> map = MapUtil.toMap(((IsNullExpression) expression).isNot() ? QueryOperators.NIN : QueryOperators.IN, new Object[] { null });
            where = MapUtil.toMap(Parsers.getLeft(expression), map);
        } else {
            where = MapUtil.toMap(Parsers.getLeft(expression), Parsers.getRight(expression, preparedStatement));
        }
        return where;
    }

    /**
     * 查询返回的列
     */
    public static Map<String, Object> getReturnFields(Select select) {
        SelectBody selectBody = select.getSelectBody();
        PlainSelect plainSelect = (PlainSelect) selectBody;
        List<SelectItem> selectItems = plainSelect.getSelectItems();
        Map<String, Object> returnFields = new HashMap<String, Object>();
        if (!(selectItems.size() == 1 && selectItems.get(0) instanceof AllColumns)) {// 如果查询返回所有列,则方法返回new Term()
            for (SelectItem selectItem : selectItems) {
                if (selectItem instanceof SelectExpressionItem) {
                    Expression expression = ((SelectExpressionItem) selectItem).getExpression();
                    if (expression instanceof Column) {
                        returnFields.put(((Column) expression).getColumnName(), 1);
                    } else {
                        throw Errors.wrap("不支持的数据返回 " + expression + " " + expression.getClass());
                    }
                } else {
                    throw Errors.wrap("不支持的数据返回 " + selectItem + " " + selectItem.getClass());
                }
            }
        }
        log.trace("getReturnFields()  returnFields = " + returnFields + ", select = " + select);
        return returnFields;
    }

    public static Map<String, Object> getOrderBy(Select select) {
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        List<OrderByElement> orderByElements = plainSelect.getOrderByElements();

        Map<String, Object> orderBy = new HashMap<String, Object>();
        if (null != orderByElements && !orderByElements.isEmpty()) {
            for (OrderByElement element : orderByElements) {
                Column column = (Column) element.getExpression();
                orderBy.put(column.getColumnName(), element.isAsc() ? 1 : -1);
            }
        }
        log.trace("getOrderBy() orderBy = " + orderBy + ", select = " + select);
        return orderBy;
    }

    public static Page getPage(Select select, MongoPreparedStatement preparedStatement) {
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        Limit limit = plainSelect.getLimit();
        Page page = new Page();
        if (null != limit) {
            if (Querys.isLimitBA(preparedStatement.getSql())) {// 这里主要照顾的是limit中问号参数的获取顺序
                page.setOffset(limit.isOffsetJdbcParameter() ? new Holder(preparedStatement.getNextParameterIndex()) : new Long(limit.getOffset()).intValue());// 如果是指不是问号则不会getNextParameterIndex
                page.setLimit(limit.isRowCountJdbcParameter() ? new Holder(preparedStatement.getNextParameterIndex()) : new Long(limit.getRowCount()).intValue());
            } else {
                page.setLimit(limit.isRowCountJdbcParameter() ? new Holder(preparedStatement.getNextParameterIndex()) : new Long(limit.getRowCount()).intValue());
                page.setOffset(limit.isOffsetJdbcParameter() ? new Holder(preparedStatement.getNextParameterIndex()) : new Long(limit.getOffset()).intValue());
            }
        }
        log.trace("getPage() select = " + select + " preparedStatement = " + preparedStatement);
        return page;
    }
}