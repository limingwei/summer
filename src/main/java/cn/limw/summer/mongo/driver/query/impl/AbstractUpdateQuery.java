package cn.limw.summer.mongo.driver.query.impl;

import cn.limw.summer.mongo.driver.query.Query;

/**
 * @author 明伟
 */
public abstract class AbstractUpdateQuery extends Query {
    public AbstractUpdateQuery execute() {
        return null;
    }

    public Integer getUpdateCount() {
        return 0;
    }
}