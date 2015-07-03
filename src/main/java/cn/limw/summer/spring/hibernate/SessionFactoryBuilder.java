package cn.limw.summer.spring.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.Dialect;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.DatabaseMetadata;
import org.hibernate.tool.hbm2ddl.SchemaUpdateScript;
import org.slf4j.Logger;
import org.springframework.core.io.ResourceLoader;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;

import cn.limw.summer.hibernate.observer.ServiceRegistrySessionFactoryObserver;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2014年6月25日 下午3:33:08)
 * @since Java7
 */
public class SessionFactoryBuilder extends LocalSessionFactoryBuilder {
    private static final long serialVersionUID = 8245396710590988315L;

    private static final Logger log = Logs.slf4j();

    private Map<String, String> comments;

    private String tableCommentPrefix;

    public SessionFactoryBuilder(DataSource dataSource, ResourceLoader resourceLoader) {
        super(dataSource, resourceLoader);
    }

    private String getComment(Iterator<PersistentClass> iterator, String name) {
        if (null == comments) {
            comments = SessionFactoryUtil.getComments(iterator);
            log.info("getComments size={}", comments.size());
            //            log.info("comments={}", comments);
        }
        return comments.get(name.toLowerCase());
    }

    private void setComments() {
        Iterator tableMappings = getTableMappings();
        while (tableMappings.hasNext()) {
            Table table = (Table) tableMappings.next();
            if (null == table.getComment() || table.getComment().trim().isEmpty()) {
                table.setComment(getTableCommentPrefix() + getComment(getClassMappings(), table.getName()));//添加前缀
            }
            Iterator columns = table.getColumnIterator();
            while (columns.hasNext()) {
                Column column = (Column) columns.next();
                if (null == column.getComment() || column.getComment().trim().isEmpty()) {
                    column.setComment(getComment(getClassMappings(), table.getName() + "." + column.getName()));
                }
            }
        }
    }

    /**
     * @see org.springframework.orm.hibernate4.LocalSessionFactoryBuilder#buildSessionFactory()
     */
    public SessionFactory buildSessionFactory() throws HibernateException {
        ClassLoader appClassLoader = (ClassLoader) getProperties().get(AvailableSettings.APP_CLASSLOADER);
        Thread currentThread = Thread.currentThread();
        ClassLoader threadContextClassLoader = currentThread.getContextClassLoader();
        boolean overrideClassLoader = (appClassLoader != null && !appClassLoader.equals(threadContextClassLoader));
        if (overrideClassLoader) {
            currentThread.setContextClassLoader(appClassLoader);
        }
        try {
            return doBuildSessionFactory();
        } finally {
            if (overrideClassLoader) {
                currentThread.setContextClassLoader(threadContextClassLoader);
            }
        }
    }

    /**
     * @see org.hibernate.cfg.Configuration#buildSessionFactory()
     */
    private SessionFactory doBuildSessionFactory() {
        Properties properties = getProperties();
        Environment.verifyProperties(properties);
        ConfigurationHelper.resolvePlaceHolders(properties);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(properties).build();
        setSessionFactoryObserver(new ServiceRegistrySessionFactoryObserver(serviceRegistry));
        return buildSessionFactory(serviceRegistry);
    }

    public List<SchemaUpdateScript> generateSchemaUpdateScriptList(Dialect dialect, DatabaseMetadata databaseMetadata) throws HibernateException {
        setComments();
        return super.generateSchemaUpdateScriptList(dialect, databaseMetadata);
    }

    public void setTableCommentPrefix(String tableCommentPrefix) {
        this.tableCommentPrefix = tableCommentPrefix;
    }

    public String getTableCommentPrefix() {
        return tableCommentPrefix;
    }
}