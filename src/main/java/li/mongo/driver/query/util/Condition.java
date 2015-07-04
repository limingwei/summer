package li.mongo.driver.query.util;

import java.util.List;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.schema.Column;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.unblocked.support.util.Errors;

/**
 * @author 明伟
 */
public class Condition {
    public static DBObject eq(DBObject condition, EqualsTo equalsTo) {
        Column left = (Column) equalsTo.getLeftExpression();
        condition.put(left.getColumnName(), getValue(equalsTo.getRightExpression()));
        return condition;
    }

    public static DBObject gt(DBObject condition, GreaterThan greaterThan) {
        Column left = (Column) greaterThan.getLeftExpression();
        condition.put(left.getColumnName(), new BasicDBObject("$gt", getValue(greaterThan.getRightExpression())));
        return condition;
    }

    private static Object getValue(Expression expression) {
        if (expression instanceof LongValue) {
            return ((LongValue) expression).getValue();
        } else if (expression instanceof StringValue) {
            return ((StringValue) expression).getValue();
        } else {
            throw Errors.wrap("未支持的数据类型 " + expression + " " + expression.getClass());
        }
    }

    public static DBObject lt(DBObject condition, MinorThan minorThan) {
        Column left = (Column) minorThan.getLeftExpression();
        condition.put(left.getColumnName(), new BasicDBObject("$lt", getValue(minorThan.getRightExpression())));
        return condition;
    }

    public static DBObject ge(DBObject condition, GreaterThanEquals greaterThanEquals) {
        Column left = (Column) greaterThanEquals.getLeftExpression();
        condition.put(left.getColumnName(), new BasicDBObject("$gte", getValue(greaterThanEquals.getRightExpression())));
        return condition;
    }

    public static DBObject le(DBObject condition, MinorThanEquals minorThanEquals) {
        Column left = (Column) minorThanEquals.getLeftExpression();
        condition.put(left.getColumnName(), new BasicDBObject("$lte", getValue(minorThanEquals.getRightExpression())));
        return condition;
    }

    public static DBObject in(DBObject condition, InExpression inExpression) {
        Column left = (Column) inExpression.getLeftExpression();
        ExpressionList right = (ExpressionList) inExpression.getRightItemsList();
        List<Expression> list = right.getExpressions();
        BasicDBList values = new BasicDBList();
        for (Expression expression : list) {
            values.add(getValue(expression));
        }
        condition.put(left.getColumnName(), new BasicDBObject("$in", values));
        return condition;
    }

    public static DBObject and(DBObject condition, AndExpression andExpression) {
        Expression left = andExpression.getLeftExpression();
        Expression right = andExpression.getRightExpression();

        expressionToCondition(condition, left);
        expressionToCondition(condition, right);
        return condition;
    }

    public static DBObject or(DBObject condition, OrExpression orExpression) {
        return null;
    }

    public static DBObject expressionToCondition(DBObject condition, Expression expression) {
        if (expression instanceof EqualsTo) {
            return eq(condition, (EqualsTo) expression);
        } else if (expression instanceof GreaterThan) {
            return gt(condition, (GreaterThan) expression);
        } else if (expression instanceof MinorThan) {
            return lt(condition, (MinorThan) expression);
        } else if (expression instanceof GreaterThanEquals) {
            return ge(condition, (GreaterThanEquals) expression);
        } else if (expression instanceof MinorThanEquals) {
            return le(condition, (MinorThanEquals) expression);
        } else if (expression instanceof InExpression) {
            return in(condition, (InExpression) expression);
        } else if (expression instanceof AndExpression) {
            return and(condition, (AndExpression) expression);
        } else if (expression instanceof OrExpression) {
            return or(condition, (OrExpression) expression);
        } else {
            throw Errors.wrap("不支持的Where筛选\t" + expression.getClass() + "\t" + expression);
        }
    }
}