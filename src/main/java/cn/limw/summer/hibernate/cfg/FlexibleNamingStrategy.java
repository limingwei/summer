package cn.limw.summer.hibernate.cfg;

import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.cfg.NamingStrategy;

/**
 * 可配置表和列的前缀后缀
 * @author li
 * @version 1 2014年3月27日上午9:46:58
 */
public class FlexibleNamingStrategy extends NamingStrategyWrapper {
    private NamingStrategy namingStrategy;

    private String tablePrefix = "";

    private String tableSuffix = "";

    private String columnPrefix = "";

    private String columnSuffix = "";

    /**
     * namingStrategy = new ImprovedNamingStrategy();
     */
    public FlexibleNamingStrategy() {
        super.setNamingStrategy(this.namingStrategy = new ImprovedNamingStrategy());
    }

    public FlexibleNamingStrategy(NamingStrategy namingStrategy) {
        super.setNamingStrategy(this.namingStrategy = namingStrategy);
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public void setTableSuffix(String tableSuffix) {
        this.tableSuffix = tableSuffix;
    }

    public void setColumnPrefix(String columnPrefix) {
        this.columnPrefix = columnPrefix;
    }

    public void setColumnSuffix(String columnSuffix) {
        this.columnSuffix = columnSuffix;
    }

    public String columnName(String columnName) {
        return columnPrefix + namingStrategy.columnName(columnName) + columnSuffix;
    }

    public String tableName(String tableName) {
        return tablePrefix + namingStrategy.tableName(tableName) + tableSuffix;
    }
}