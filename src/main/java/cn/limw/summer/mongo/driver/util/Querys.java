package cn.limw.summer.mongo.driver.util;

import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.update.Update;

import org.apache.log4j.Logger;

import cn.limw.summer.mongo.driver.query.Query;
import cn.limw.summer.mongo.driver.query.impl.CountQuery;
import cn.limw.summer.mongo.driver.query.impl.DeleteQuery;
import cn.limw.summer.mongo.driver.query.impl.InsertQuery;
import cn.limw.summer.mongo.driver.query.impl.MaxQuery;
import cn.limw.summer.mongo.driver.query.impl.MinQuery;
import cn.limw.summer.mongo.driver.query.impl.SelectQuery;
import cn.limw.summer.mongo.driver.query.impl.UpdateQuery;
import cn.limw.summer.util.Errors;

/**
 * @author li
 * @version 1 2014年3月12日上午11:29:15
 */
public class Querys {
    private static final Logger log = Logs.get();

    private static final String COUNT = "COUNT", MAX = "MAX", MIN = "MIN";;

    private static final CCJSqlParserManager SQL_PARSER_MANAGER = new CCJSqlParserManager();

    /**
     * SQL解析缓存
     */
    private static final Map<String, Query> QUERY_CACHE = new ConcurrentHashMap<String, Query>();

    /**
     * 解析SQL语句,有缓存
     */
    public static Query parseQuery(String sql) {
        try {
            Query query = QUERY_CACHE.get(sql);
            if (null == query) {
                query = doParse(sql);
                QUERY_CACHE.put(sql, query);
            }
            return query;
        } catch (Throwable e) {
            throw Errors.wrap(sql + " " + e.getMessage(), e);
        }
    }

    private synchronized static Query doParse(String sql) throws Throwable {
        Query query;
        Statement statement = SQL_PARSER_MANAGER.parse(new StringReader(sql));
        if (statement instanceof Select) {
            if (isCount((Select) statement)) {
                query = new CountQuery();
            } else if (isMax((Select) statement)) {
                query = new MaxQuery();
            } else if (isMin((Select) statement)) {
                query = new MinQuery();
            } else {
                query = new SelectQuery();
            }
        } else if (statement instanceof Insert) {
            query = new InsertQuery();
        } else if (statement instanceof Delete) {
            query = new DeleteQuery();
        } else if (statement instanceof Update) {
            query = new UpdateQuery();
        } else {
            throw Errors.wrap("error query " + sql);
        }
        query.setStatement(statement);
        log.trace("doParse() sql = " + sql + ", query = " + query);
        return query;
    }

    /**
     * 第一种 LIMIT A 第二种 LIMIT B,A 第三种 LIMIT A OFFSET B
     */
    public static Boolean isLimitBA(String sql) {
        sql = sql.toUpperCase();
        int limit_index = sql.indexOf(" LIMIT ");
        if (limit_index < 0) {
            return false;
        }
        int comma_index = sql.indexOf((int) ',', limit_index);
        if (comma_index < 0) {
            return false;
        }
        String _b = sql.substring(limit_index + " LIMIT ".length(), comma_index).trim();
        return _b.equals("?") || ConvertUtil.isInt(_b);
    }

    /**
     * 是否Count查询
     */
    private static Boolean isCount(Select select) {
        Function function = getSelectFunction(select);
        return null != function && function.getName().equalsIgnoreCase(COUNT);
    }

    /**
     * 是否Min查询
     */
    private static Boolean isMin(Select select) {
        Function function = getSelectFunction(select);
        return null != function && function.getName().equalsIgnoreCase(MIN);
    }

    /**
     * 是否Max查询
     */
    private static Boolean isMax(Select select) {
        Function function = getSelectFunction(select);
        return null != function && function.getName().equalsIgnoreCase(MAX);
    }

    /**
     * getFirstSelectItemAsFunctionOrNull
     * 
     * @param select
     * @return
     */
    public static Function getSelectFunction(Select select) {
        SelectBody selectBody = select.getSelectBody();
        PlainSelect plainSelect = (PlainSelect) selectBody;
        List<SelectItem> selectItems = plainSelect.getSelectItems();
        if (1 == selectItems.size() && selectItems.get(0) instanceof SelectExpressionItem) {
            SelectExpressionItem selectExpressionItem = ((SelectExpressionItem) selectItems.get(0));
            Expression expression = selectExpressionItem.getExpression();
            return expression instanceof Function ? (Function) expression : null;
        }
        return null;
    }
}