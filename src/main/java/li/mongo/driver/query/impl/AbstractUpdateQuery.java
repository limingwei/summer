package li.mongo.driver.query.impl;

import li.mongo.driver.query.Query;

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