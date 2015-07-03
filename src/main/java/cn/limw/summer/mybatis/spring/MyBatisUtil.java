package cn.limw.summer.mybatis.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.ManyToOneType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimeType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import cn.limw.summer.freemarker.util.FreeMarkerUtil;
import cn.limw.summer.hibernate.HibernateUtil;
import cn.limw.summer.hibernate.type.BooleanType;
import cn.limw.summer.hibernate.type.TimestampType;
import cn.limw.summer.util.ArrayUtil;
import cn.limw.summer.util.Files;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.Mirrors;
import freemarker.template.Template;

/**
 * @author li
 * @version 1 (2014年10月27日 上午9:17:03)
 * @since Java7
 */
public class MyBatisUtil {
    private static final Logger log = Logs.slf4j();

    private static final Template MAPPER_GENERATOR_TEMPLATE = FreeMarkerUtil.getTemplate(Files.classPathRead("mybatis-template/mapper-generator.htm"));

    private static final Template COMMON_MAPPER_TEMPLATE = FreeMarkerUtil.getTemplate(Files.classPathRead("mybatis-template/common-mapper.htm"));

    private static final Class<?>[] BASIC_TYPES = { TimestampType.class, org.hibernate.type.TimestampType.class,// 
            BooleanType.class, DoubleType.class, IntegerType.class, LongType.class, StringType.class, TimeType.class };

    public static List<Resource> hibernateToMybatis(SessionFactory sessionFactory) {
        List<Resource> resources = new ArrayList<Resource>();
        if (null != sessionFactory) {
            Map<String, ClassMetadata> classMetadatas = sessionFactory.getAllClassMetadata();
            for (Entry<String, ClassMetadata> entry : classMetadatas.entrySet()) {
                resources.add(hibernateToMybatis(sessionFactory, entry));
            }
        }
        log.info("hibernateToMybatis({}) returns {}", sessionFactory, resources.size());
        return resources;
    }

    private static Resource hibernateToMybatis(SessionFactory sessionFactory, Entry<String, ClassMetadata> entry) {
        String entityName = entry.getKey();
        Class<?> entityType = Mirrors.classForName(entityName);
        SingleTableEntityPersister singleTableEntityPersister = (SingleTableEntityPersister) entry.getValue();
        String tableName = singleTableEntityPersister.getTableName();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("entityName", entityName);
        map.put("tableName", tableName);

        map.put("idField", HibernateUtil.getIdField(sessionFactory, entityType));
        map.put("idColumn", HibernateUtil.getIdColumn(sessionFactory, entityType));

        map.put("basicPropties", basicProps(singleTableEntityPersister));
        map.put("manyToOnePropties", manyToOnePropties(sessionFactory, singleTableEntityPersister));

        String mapperXml = FreeMarkerUtil.merge(MAPPER_GENERATOR_TEMPLATE, map);
        return new ByteArrayResource(mapperXml.getBytes(), entityName + ".mapper.gen.xml");
    }

    public static Resource commonMappers() {
        Map<String, Object> map = new HashMap<String, Object>();
        String mapperXml = FreeMarkerUtil.merge(COMMON_MAPPER_TEMPLATE, map);
        return new ByteArrayResource(mapperXml.getBytes(), "common.mapper.gen.xml");
    }

    private static List<Map<String, Object>> manyToOnePropties(SessionFactory sessionFactory, SingleTableEntityPersister singleTableEntityPersister) {
        List<Map<String, Object>> props = new ArrayList<Map<String, Object>>();
        String[] propertyNames = singleTableEntityPersister.getPropertyNames();
        for (String propertyName : propertyNames) {
            Type propertyType = singleTableEntityPersister.getPropertyType(propertyName);
            if (ManyToOneType.class.equals(propertyType.getClass())) {
                String[] propertyColumnNames = singleTableEntityPersister.getPropertyColumnNames(propertyName);
                String fieldType = singleTableEntityPersister.getPropertyType(propertyName).getName();
                Class<?> fieldClass = Mirrors.classForName(fieldType);

                Map<String, Object> prop = new HashMap<String, Object>();
                prop.put("name", propertyName);
                prop.put("type", fieldType);
                prop.put("column", propertyColumnNames[0]);
                prop.put("idField", HibernateUtil.getIdField(sessionFactory, fieldClass));
                prop.put("idColumn", HibernateUtil.getIdColumn(sessionFactory, fieldClass));
                props.add(prop);
            }
        }
        return props;
    }

    private static List<Map<String, Object>> basicProps(SingleTableEntityPersister singleTableEntityPersister) {
        List<Map<String, Object>> props = new ArrayList<Map<String, Object>>();
        String[] propertyNames = singleTableEntityPersister.getPropertyNames();
        for (String propertyName : propertyNames) {
            Type propertyType = singleTableEntityPersister.getPropertyType(propertyName);
            if (isBasicType(propertyType.getClass())) {

                String[] propertyColumnNames = singleTableEntityPersister.getPropertyColumnNames(propertyName);
                Map<String, Object> prop = new HashMap<String, Object>();
                prop.put("name", propertyName);
                prop.put("column", propertyColumnNames[0]);
                props.add(prop);
            }
        }
        return props;
    }

    private static Boolean isBasicType(Class<?> type) {
        return ArrayUtil.contains(BASIC_TYPES, type);
    }
}