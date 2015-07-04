package cn.limw.summer.mongo.driver.query;

import cn.limw.summer.mongo.driver.query.impl.SelectQuery;
import cn.limw.summer.mongo.driver.util.Parsers;
import cn.limw.summer.mongo.driver.util.Querys;
import net.sf.jsqlparser.statement.select.Select;

/**
 * @author li
 * @version 1 2014年3月24日下午4:35:34
 */
public class ProxyQuery extends ReadQuery {
    private String alias;

    private Query query;

    private String sql;

    public ProxyQuery(Query query, String sql) {
        this.query = query;
        this.sql = sql;
    }

    public ProxyQuery(String string) {}

    public String getAlias() {
        return null == alias ? alias = Parsers.getAliasOrExpression((Select) query.getStatement(), 0) : alias;
    }

    public ReadQuery execute() {
        SelectQuery selectQuery = new SelectQuery();
        selectQuery.setPreparedStatement(query.getPreparedStatement());
        selectQuery.setStatement(Querys.parseQuery(sql).getStatement());
        // selectQuery.setWhere(Mongos.getWhere(Parsers.getWhereExpression(selectQuery.getStatement()), getPreparedStatement()));
        return selectQuery.execute();

        // ValueResultSet resultSet = new ValueResultSet();
        // resultSet.setValue(0, getAlias(), 1);
        // return super.setResultSet(resultSet);
    }
}