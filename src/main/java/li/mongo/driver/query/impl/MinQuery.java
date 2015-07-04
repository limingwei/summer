package li.mongo.driver.query.impl;

import li.mongo.driver.query.ProxyQuery;
import li.mongo.driver.query.ReadQuery;
import li.mongo.driver.util.Querys;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.Select;

/**
 * @author li
 * @version 1 2014年3月24日下午4:22:09
 */
public class MinQuery extends ReadQuery {
    private String columnName;

    public String getColumnName() {
        return null == columnName ? columnName = ((Column) Querys.getSelectFunction((Select) getStatement()).getParameters().getExpressions().get(0)).getColumnName() : columnName;
    }

    public ReadQuery execute() {
        columnName = getColumnName();
        return new ProxyQuery(this, "SELECT " + columnName + " max_" + columnName + " FROM t_users ORDER BY " + columnName + " ASC LIMIT 1").execute();
    }
}