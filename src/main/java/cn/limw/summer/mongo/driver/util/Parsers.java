package cn.limw.summer.mongo.driver.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.SignedExpression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.update.Update;

import org.apache.log4j.Logger;

import cn.limw.summer.mongo.driver.MongoPreparedStatement;
import cn.limw.summer.mongo.driver.model.Holder;
import cn.limw.summer.mongo.driver.model.Like;
import cn.limw.summer.util.Errors;
import cn.limw.summer.util.Maps;

import com.mongodb.QueryOperators;

/**
 * SqlParserUtil
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年3月5日 上午11:42:16)
 */
public class Parsers {
    private static final Logger log = Logs.get();

    /**
     * 返回Where过滤条件
     */
    public static Expression getWhereExpression(Statement statement) {
        Expression where;
        if (statement instanceof Select) {
            PlainSelect plainSelect = (PlainSelect) ((Select) statement).getSelectBody();
            where = plainSelect.getWhere();
        } else if (statement instanceof Delete) {
            where = ((Delete) statement).getWhere();
        } else if (statement instanceof Update) {
            where = ((Update) statement).getWhere();
        } else {
            throw Errors.wrap(statement + " " + statement.getClass() + " 没有 WhereExpression ");
        }
        log.trace("getWhereExpression() whereExpression = " + where + ", statement = " + statement);
        return where;
    }

    /**
     * 返回操作表名
     */
    public static String getTableName(Statement statement) {
        String tableName;
        if (statement instanceof Select) {
            PlainSelect plainSelect = (PlainSelect) ((Select) statement).getSelectBody();
            tableName = ((Table) plainSelect.getFromItem()).getName();
        } else if (statement instanceof Delete) {
            tableName = ((Delete) statement).getTable().getName();
        } else if (statement instanceof Insert) {
            tableName = ((Insert) statement).getTable().getName();
        } else if (statement instanceof Update) {
            tableName = ((Update) statement).getTables().get(0).getName();
        } else {
            throw Errors.wrap("statement=" + (null == statement ? null : statement.getClass()));
        }
        log.trace("getTableName() tableName = " + tableName + ", statement = " + statement);
        return tableName;
    }

    /**
     * 返回所有列的别名信息
     */
    public static Map<String, String> getFieldAlias(Select select) {
        SelectBody selectBody = select.getSelectBody();
        PlainSelect plainSelect = (PlainSelect) selectBody;
        List<SelectItem> selectItems = plainSelect.getSelectItems();
        Map<String, String> aliasMap = new HashMap<String, String>();
        if (!(selectItems.size() == 1 && selectItems.get(0) instanceof AllColumns)) {
            for (SelectItem selectItem : selectItems) {
                SelectExpressionItem selectExpressionItem = (SelectExpressionItem) selectItem;
                Expression expression = selectExpressionItem.getExpression();
                Alias alias = selectExpressionItem.getAlias();
                String columnName = (null == expression ? null : expression.toString().trim());
                String aliasName = (null == alias ? null : alias.getName().trim());
                if (null != columnName) {
                    aliasMap.put(columnName.replace(getTableAlias(select) + ".", ""), aliasName);
                }
            }
        }
        log.trace("getFieldAlias() fieldAlias = " + aliasMap + ", select = " + select);
        return aliasMap;
    }

    public static Map<String, String> getAliasOfFields(Select select, Map<String, Object> fields) {
        log.trace("getAliasOfFields() select = " + select + ", fields = " + fields);
        Map<String, String> allFieldAlias = getFieldAlias(select);
        Map<String, String> aliasOfFields = new HashMap<String, String>();
        for (String field : fields.keySet()) {
            aliasOfFields.put(field, allFieldAlias.get(field));
        }
        return aliasOfFields;
    }

    /**
     * t_users user0_
     */
    public static String getTableAlias(Select select) {
        SelectBody selectBody = select.getSelectBody();
        PlainSelect plainSelect = (PlainSelect) selectBody;
        Table table = (Table) plainSelect.getFromItem();
        Alias alias = table.getAlias();
        String tableAlias = null == alias ? null : alias.getName();
        log.trace("getTableAlias() tableAlias = " + tableAlias + ", select = " + select);
        return tableAlias;
    }

    /**
     * 返回别名或者列名
     */
    public static String getAliasOrExpression(Select select, Integer index) {
        Map<String, String> alias = getFieldAlias(select);
        List<Entry<String, String>> entries = new ArrayList<Entry<String, String>>(alias.entrySet());
        Entry<String, String> entry = entries.get(index);
        return null == entry.getValue() ? entry.getKey() : entry.getValue();
    }

    /**
     * 得到左表达式
     */
    public static String getLeft(Expression expression) {
        Expression left;
        if (expression instanceof BinaryExpression) {
            left = ((BinaryExpression) expression).getLeftExpression();
        } else if (expression instanceof InExpression) {
            left = ((InExpression) expression).getLeftExpression();
        } else if (expression instanceof IsNullExpression) {
            left = ((IsNullExpression) expression).getLeftExpression();
        } else {
            throw Errors.wrap("没有左表达式 " + expression + " " + (null == expression ? " null value " : expression.getClass()));
        }
        if (left instanceof Column) {
            return ((Column) left).getColumnName();
        } else {
            throw Errors.wrap("不支持的左表达式 " + left + " " + left.getClass());
        }
    }

    /**
     * 得到右表达式
     */
    public static Object getRight(Expression expression, MongoPreparedStatement preparedStatement) {
        if (expression instanceof EqualsTo) {// =
            return getRightValue((EqualsTo) expression, preparedStatement);
        } else if (expression instanceof LikeExpression) {// LIKE
            return getRightValue((LikeExpression) expression, preparedStatement);
        } else if (expression instanceof NotEqualsTo) {
            return Maps.toMap(QueryOperators.NE, getRightValue((NotEqualsTo) expression, preparedStatement));
        } else if (expression instanceof MinorThan) {
            return Maps.toMap(QueryOperators.LT, getRightValue((MinorThan) expression, preparedStatement));
        } else if (expression instanceof MinorThanEquals) {
            return Maps.toMap(QueryOperators.LTE, getRightValue((MinorThanEquals) expression, preparedStatement));
        } else if (expression instanceof GreaterThan) {
            return Maps.toMap(QueryOperators.GT, getRightValue((GreaterThan) expression, preparedStatement));
        } else if (expression instanceof GreaterThanEquals) {
            return Maps.toMap(QueryOperators.GTE, getRightValue((GreaterThanEquals) expression, preparedStatement));
        } else if (expression instanceof InExpression) {
            return Maps.toMap(((InExpression) expression).isNot() ? QueryOperators.NIN : QueryOperators.IN, getRightValue((InExpression) expression, preparedStatement));
        } else if (expression instanceof IsNullExpression) {
            return null;
        } else {
            throw Errors.wrap("expression=" + expression + " " + expression.getClass());
        }
    }

    /**
     * 得到右表达式的值
     */
    public static Object getRightValue(Expression expression, MongoPreparedStatement preparedStatement) {
        if (expression instanceof LikeExpression) {
            return new Like(((LikeExpression) expression).isNot(), getValueOrHolder(((LikeExpression) expression).getRightExpression(), preparedStatement));
        } else if (expression instanceof BinaryExpression) {
            return getValueOrHolder(((BinaryExpression) expression).getRightExpression(), preparedStatement);
        } else if (expression instanceof InExpression) {
            ItemsList rightItemsList = ((InExpression) expression).getRightItemsList();
            if (rightItemsList instanceof ExpressionList) {
                List<Expression> expressions = ((ExpressionList) rightItemsList).getExpressions();
                Object[] array = new Object[expressions.size()];
                for (int i = 0; i < expressions.size(); i++) {
                    array[i] = getValueOrHolder(expressions.get(i), preparedStatement);
                }
                return array;
            } else {
                throw Errors.wrap("inExpression.getRightItemsList() not instanceof ExpressionList");
            }
        } else {
            throw Errors.wrap(expression + " " + expression.getClass());
        }
    }

    /**
     * 得到值,从PreparedstatementParameters或者原生的
     */
    public static Object getValueOrHolder(Expression expression, final MongoPreparedStatement preparedStatement) {
        if (expression instanceof NullValue) {
            return null;
        } else if (expression instanceof StringValue) {
            return ((StringValue) expression).getValue();
        } else if (expression instanceof LongValue) {
            return ((LongValue) expression).getValue();
        } else if (expression instanceof DoubleValue) {
            return ((DoubleValue) expression).getValue();
        } else if (expression instanceof SignedExpression) {
            char sign = ((SignedExpression) expression).getSign();
            Object value = getValueOrHolder(((SignedExpression) expression).getExpression(), preparedStatement);
            if (value instanceof Holder) {
                ((Holder) value).setSign(sign);
            } else if (value instanceof Long) {
                return 0 - (Long) value;
            } else if (value instanceof Double) {
                return 0.0 - (Double) value;
            }
            return value;
        } else if (expression instanceof JdbcParameter) {
            Integer index = preparedStatement.getNextParameterIndex();
            if (preparedStatement.isNull(index)) {
                return null;
            } else {
                return new Holder(index);
            }
        } else {
            throw Errors.wrap("not supported expression  " + expression + " " + expression.getClass());
        }
    }
}