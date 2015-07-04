package cn.limw.summer.mongo.driver.query.impl;

import cn.limw.summer.mongo.driver.query.ProxyQuery;
import cn.limw.summer.mongo.driver.query.ReadQuery;
import cn.limw.summer.mongo.driver.util.Querys;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.Select;

/**
 * @author li
 * @version 1 2014年3月24日下午4:21:56
 */
public class MaxQuery extends ReadQuery {
    private String columnName;

    public String getColumnName() {
        return null == columnName ? columnName = ((Column) Querys.getSelectFunction((Select) getStatement()).getParameters().getExpressions().get(0)).getColumnName() : columnName;
    }

    public ReadQuery execute() {
        columnName = getColumnName();
        return new ProxyQuery(this, "SELECT " + columnName + " max_" + columnName + " FROM t_users ORDER BY " + columnName + " DESC LIMIT 1").execute();
    }
}