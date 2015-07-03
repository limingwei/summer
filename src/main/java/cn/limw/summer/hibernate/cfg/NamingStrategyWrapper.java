package cn.limw.summer.hibernate.cfg;

import org.hibernate.cfg.NamingStrategy;

import cn.limw.summer.util.Asserts;

/**
 * @author li
 * @version 1 2014年3月27日上午11:17:09
 */
public class NamingStrategyWrapper implements NamingStrategy {
    private NamingStrategy namingStrategy;

    public NamingStrategyWrapper() {}

    public NamingStrategyWrapper(NamingStrategy namingStrategy) {
        this.namingStrategy = namingStrategy;
    }

    public NamingStrategy getNamingStrategy() {
        return Asserts.noNull(namingStrategy);
    }

    public void setNamingStrategy(NamingStrategy namingStrategy) {
        this.namingStrategy = namingStrategy;
    }

    public String classToTableName(String className) {
        return getNamingStrategy().classToTableName(className);
    }

    public String propertyToColumnName(String propertyName) {
        return getNamingStrategy().propertyToColumnName(propertyName);
    }

    public String tableName(String tableName) {
        return getNamingStrategy().tableName(tableName);
    }

    public String columnName(String columnName) {
        return getNamingStrategy().columnName(columnName);
    }

    public String collectionTableName(String ownerEntity, String ownerEntityTable, String associatedEntity, String associatedEntityTable, String propertyName) {
        return getNamingStrategy().collectionTableName(ownerEntity, ownerEntityTable, associatedEntity, associatedEntityTable, propertyName);
    }

    public String joinKeyColumnName(String joinedColumn, String joinedTable) {
        return getNamingStrategy().joinKeyColumnName(joinedColumn, joinedTable);
    }

    public String foreignKeyColumnName(String propertyName, String propertyEntityName, String propertyTableName, String referencedColumnName) {
        return getNamingStrategy().foreignKeyColumnName(propertyName, propertyEntityName, propertyTableName, referencedColumnName);
    }

    public String logicalColumnName(String columnName, String propertyName) {
        return getNamingStrategy().logicalColumnName(columnName, propertyName);
    }

    public String logicalCollectionTableName(String tableName, String ownerEntityTable, String associatedEntityTable, String propertyName) {
        return getNamingStrategy().logicalCollectionTableName(tableName, ownerEntityTable, associatedEntityTable, propertyName);
    }

    public String logicalCollectionColumnName(String columnName, String propertyName, String referencedColumn) {
        return getNamingStrategy().logicalCollectionColumnName(columnName, propertyName, referencedColumn);
    }
}