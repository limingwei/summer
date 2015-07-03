package cn.limw.summer.hibernate.modelchecker;

import java.util.Set;

/**
 * @author li
 * @version 1 (2014年12月8日 上午11:49:00)
 * @since Java7
 */
public class TableInfo {
    private String tableName;

    private Set<ColumnInfo> columnInfos;

    private Set<FieldInfo> fieldInfos;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Set<FieldInfo> getFieldInfos() {
        return fieldInfos;
    }

    public void setFieldInfos(Set<FieldInfo> fieldInfos) {
        this.fieldInfos = fieldInfos;
    }

    public Set<ColumnInfo> getColumnInfos() {
        return columnInfos;
    }

    public void setColumnInfos(Set<ColumnInfo> columnInfos) {
        this.columnInfos = columnInfos;
    }
}