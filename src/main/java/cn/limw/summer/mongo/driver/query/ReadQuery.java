package cn.limw.summer.mongo.driver.query;

import java.sql.ResultSet;

/**
 * @author li
 * @version 1 2014年3月6日下午5:55:19
 */
public abstract class ReadQuery extends Query {
    private ResultSet resultSet;

    public ReadQuery setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
        return this;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }
}