package summer.dao.result;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import summer.dao.ResultSetTransformer;
import summer.log.Logger;
import summer.util.Log;
import summer.util.Reflect;

/**
 * @author li
 * @version 1 (2015年10月24日 下午6:37:35)
 * @since Java7
 */
public class EntityListResultSetTransformer<T> implements ResultSetTransformer<List<T>> {
    private static final Logger log = Log.slf4j();

    private Class<T> entityType;

    public EntityListResultSetTransformer(Class<T> type) {
        this.entityType = type;
    }

    public List<T> transform(ResultSet resultSet) {
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            String[] columnLabels = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columnLabels[i] = resultSetMetaData.getColumnLabel(i + 1);
            }

            List<T> mapList = new ArrayList<T>();
            while (resultSet.next()) {
                T newInstance = entityType.newInstance();

                for (int i = 0; i < columnCount; i++) {
                    Object value = resultSet.getObject(i + 1);
                    String name = columnLabels[i];

                    int index = name.indexOf('$');
                    if (index > 0) {
                        //
                    } else {
                        Field declaredField = getDeclaredField(entityType, name);
                        if (null != declaredField) {
                            declaredField.setAccessible(true);
                            declaredField.set(newInstance, value);
                        }
                    }
                }

                mapList.add(newInstance);
            }
            return mapList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Field getDeclaredField(Class<T> entityType, String name) {
        try {
            return Reflect.getDeclaredField(entityType, name);
        } catch (Exception e) {
            log.info("field " + name + " on " + entityType + " not found");
            return null;
        }
    }
}