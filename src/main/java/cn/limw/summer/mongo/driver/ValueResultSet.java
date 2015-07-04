package cn.limw.summer.mongo.driver;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.limw.summer.mongo.driver.base.AbstractResultSet;
import cn.limw.summer.mongo.driver.meta.ValueResultSetMetaData;

/**
 * @author li
 * @version 1 2014年3月12日上午9:38:07
 */
public class ValueResultSet extends AbstractResultSet {
    private List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();

    private ValueResultSetMetaData valueResultSetMetaData = new ValueResultSetMetaData(this);

    /**
     * 顺序读取参数指针
     */
    private Integer index = 0;

    /**
     * 如果有下一跳,返回true并向前移动指针
     */
    public boolean next() throws SQLException {
        return values.size() > index++;
    }

    /**
     * 返回当前行的指定列
     */
    public Object getObject(String columnLabel) throws SQLException {
        return values.get(index - 1).get(columnLabel);
    }

    /**
     * @param index 行数
     * @param column 列名
     * @param value 值
     */
    public void setValue(Integer index, String column, Object value) {
        valueResultSetMetaData.addColumnLabel(column);
        getRow(index).put(column, value);
    }

    /**
     * 返回第N行的Map,若不够,则添加之
     */
    public Map<String, Object> getRow(Integer index) {
        int _num = index - values.size();
        for (int i = 0; i <= _num; i++) {
            values.add(new HashMap<String, Object>());
        }
        return values.get(index);
    }

    public ValueResultSetMetaData getMetaData() throws SQLException {
        return this.valueResultSetMetaData;
    }
}