package cn.limw.summer.hibernate.modelchecker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.hibernate.type.Type;
import org.slf4j.Logger;

import cn.limw.summer.dao.Page;
import cn.limw.summer.dao.hibernate.Daos;
import cn.limw.summer.util.Logs;

/**
 * 检查hibernate对象结构和数据库中表结构是否对应匹配
 * @author li
 * @version 1 (2014年12月8日 上午10:19:49)
 * @since Java7
 */
public class ModelChecker {
    private static final Logger log = Logs.slf4j();

    private SessionFactory sessionFactory;

    private List<TableInfo> tableInfos = new ArrayList<TableInfo>();

    public ModelChecker(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void doCheck() {
        log.info("start sessionFactory={}", sessionFactory);
        Map<String, ClassMetadata> classMetadatas = sessionFactory.getAllClassMetadata();

        for (Entry<String, ClassMetadata> entry : classMetadatas.entrySet()) {
            log.info("do check for {}", entry.getKey());
            doCheckClassMetadata(entry.getValue());
        }

        printTableInfos(tableInfos);
    }

    private void printTableInfos(List<TableInfo> tableInfos) {
        for (TableInfo tableInfo : tableInfos) {
            List<FieldInfo> fieldInfos = new ArrayList<FieldInfo>(tableInfo.getFieldInfos());
            Collections.sort(fieldInfos, new Comparator<FieldInfo>() {
                public int compare(FieldInfo o1, FieldInfo o2) {
                    return o1.getPropertyName().compareTo(o2.getPropertyName());
                }
            });

            List<ColumnInfo> columnInfos = new ArrayList<ColumnInfo>(tableInfo.getColumnInfos());
            Collections.sort(columnInfos, new Comparator<ColumnInfo>() {
                public int compare(ColumnInfo o1, ColumnInfo o2) {
                    return o1.getColumnName().compareTo(o2.getColumnName());
                }
            });
            if (fieldInfos.size() != columnInfos.size()) {
                String str = tableInfo.getTableName() + " 属性数量不等 ";
                for (FieldInfo fieldInfo : fieldInfos) {
                    str += "[" + fieldInfo.getPropertyName() + "]";
                }
                str += "列";
                for (ColumnInfo columnInfo : columnInfos) {
                    str += "[" + columnInfo.getColumnName() + "]";
                }
                System.err.println(str);
            }
        }
    }

    private void doCheckClassMetadata(ClassMetadata classMetadata) {
        if (classMetadata instanceof SingleTableEntityPersister) {
            doCheckSingleTable((SingleTableEntityPersister) classMetadata);
        }
    }

    private void doCheckSingleTable(SingleTableEntityPersister singleTableEntityPersister) {
        TableInfo tableInfo = new TableInfo();
        String tableName = singleTableEntityPersister.getTableName().replace("`", "");
        tableInfo.setTableName(tableName);
        tableInfo.setColumnInfos(getColumnInfos(tableName));
        tableInfo.setFieldInfos(getFieldInfos(singleTableEntityPersister));
        tableInfos.add(tableInfo);
    }

    private Set<FieldInfo> getFieldInfos(SingleTableEntityPersister singleTableEntityPersister) {
        Set<FieldInfo> fieldInfos = new HashSet<FieldInfo>();
        String[] propertyNames = singleTableEntityPersister.getPropertyNames();
        boolean[] propertyNullability = singleTableEntityPersister.getPropertyNullability();
        Type[] types = singleTableEntityPersister.getPropertyTypes();
        for (int i = 0; i < propertyNames.length; i++) {
            if ("org.hibernate.type.BagType".equals(types[i].getClass().getName())) {
                continue;
            }
            System.err.println(types[i].getName() + types[i].getClass());

            FieldInfo fieldInfo = new FieldInfo();
            fieldInfo.setPropertyName(propertyNames[i]);
            fieldInfo.setPropertyNullability(propertyNullability[i]);
            fieldInfos.add(fieldInfo);
        }

        FieldInfo fieldInfo = new FieldInfo();
        fieldInfo.setPropertyName(singleTableEntityPersister.getIdentifierPropertyName());
        fieldInfo.setPropertyNullability(false);
        fieldInfos.add(fieldInfo);

        return fieldInfos;
    }

    private Set<ColumnInfo> getColumnInfos(String tableName) {
        String sql = "SELECT * FROM information_schema.`COLUMNS` WHERE TABLE_SCHEMA=? AND TABLE_NAME=?";
        Object[] args = { "tablename", tableName };
        List<Map> maps = Daos.newDao(sessionFactory).listMap(Page.MAX, sql, args);
        Set<ColumnInfo> columnInfos = new HashSet<ColumnInfo>();
        for (Map map : maps) {
            ColumnInfo columnInfo = new ColumnInfo();
            columnInfo.setColumnComment((String) map.get("COLUMN_COMMENT"));
            columnInfo.setIsNullable(map.get("IS_NULLABLE").equals("YES"));
            columnInfo.setColumnType((String) map.get("COLUMN_TYPE"));
            columnInfo.setColumnName((String) map.get("COLUMN_NAME"));
            columnInfos.add(columnInfo);
        }
        return columnInfos;
    }
}