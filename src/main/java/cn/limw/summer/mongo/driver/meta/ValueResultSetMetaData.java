package cn.limw.summer.mongo.driver.meta;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.limw.summer.mongo.driver.ValueResultSet;
import cn.limw.summer.mongo.driver.meta.base.AbstractResultSetMetaData;

/**
 * @author li
 * @version 1 2014年3月12日上午11:17:10
 */
public class ValueResultSetMetaData extends AbstractResultSetMetaData {
    private List<String> columnLabels = new ArrayList<String>();

    private ValueResultSet valueResultSet;

    public ValueResultSetMetaData(ValueResultSet valueResultSet) {
        this.valueResultSet = valueResultSet;
    }

    /**
     * 添加一个列标题
     */
    public void addColumnLabel(String column) {
        if (!columnLabels.contains(column)) {
            columnLabels.add(column);
        }
    }

    /**
     * 返回列数,返回第一行的宽度
     */
    public int getColumnCount() throws SQLException {
        return valueResultSet.getRow(0).size();
    }

    /**
     * 得到列标题
     */
    public String getColumnLabel(int column) throws SQLException {
        return columnLabels.get(column - 1);
    }
}