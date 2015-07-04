package li.mongo.driver.meta;

import li.mongo.driver.meta.base.AbstractDatabaseMetaData;

/**
 * @author li
 * @version 1 2014年3月7日下午1:07:32
 */
public class MongoDatabaseMetaData extends AbstractDatabaseMetaData {
    public static final MongoDatabaseMetaData INSTANCE = new MongoDatabaseMetaData();
}