package cn.limw.summer.java.sql.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author li
 * @version 1 (2014年11月20日 下午4:27:09)
 * @since Java7
 */
public class ResultSetUtil {
    public static <T> T get(ResultSet resultSet, Class<T> type, String name) {
        try {
            return (T) resultSet.getObject(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean hasColumn(ResultSet resultSet, String name) {
        try {
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                if (resultSet.getMetaData().getColumnLabel(i).equals(name)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Map<String, Object>> toMapList(ResultSet resultSet) {
        try {
            if (null == resultSet) {
                return null;
            }
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            Integer columnCount = resultSetMetaData.getColumnCount();

            String[] columnLabels = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columnLabels[i] = resultSetMetaData.getColumnLabel(i + 1);
            }

            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            while (resultSet.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                for (String columnLabel : columnLabels) {
                    map.put(columnLabel, resultSet.getObject(columnLabel));
                }
                mapList.add(map);
            }
            resultSet.close();
            return mapList;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}