package cn.limw.summer.mybatis.spring;

import static org.springframework.util.Assert.notNull;
import static org.springframework.util.ObjectUtils.isEmpty;
import static org.springframework.util.StringUtils.hasLength;
import static org.springframework.util.StringUtils.tokenizeToStringArray;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import cn.limw.summer.util.Files;

/**
 * 复制 org.mybatis.spring.SqlSessionFactoryBean 的代码, 添加了Getter Setter
 * @author li
 * @version 1 (2014年10月24日 上午11:55:04)
 * @since Java7
 */
public class AbstractSqlSessionFactoryBean extends SqlSessionFactoryBean {
    private static final Log logger = LogFactory.getLog(AbstractSqlSessionFactoryBean.class);

    private SqlSessionFactory sqlSessionFactory;

    private Resource configLocation;

    private Resource[] mapperLocations;

    private DataSource dataSource;

    private TransactionFactory transactionFactory;

    private Properties configurationProperties;

    private SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

    private String environment = AbstractSqlSessionFactoryBean.class.getSimpleName(); // EnvironmentAware requires spring 3.1

    private boolean failFast;

    private Interceptor[] plugins;

    private TypeHandler<?>[] typeHandlers;

    private String typeHandlersPackage;

    private Class<?>[] typeAliases;

    private String typeAliasesPackage;

    private Class<?> typeAliasesSuperType;

    private DatabaseIdProvider databaseIdProvider; // issue #19. No default provider.

    private ObjectFactory objectFactory;

    private ObjectWrapperFactory objectWrapperFactory;

    public void setObjectFactory(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    public ObjectFactory getObjectFactory() {
        return objectFactory;
    }

    public void setObjectWrapperFactory(ObjectWrapperFactory objectWrapperFactory) {
        this.objectWrapperFactory = objectWrapperFactory;
    }

    public ObjectWrapperFactory getObjectWrapperFactory() {
        return objectWrapperFactory;
    }

    public DatabaseIdProvider getDatabaseIdProvider() {
        return databaseIdProvider;
    }

    public void setDatabaseIdProvider(DatabaseIdProvider databaseIdProvider) {
        this.databaseIdProvider = databaseIdProvider;
    }

    public void setPlugins(Interceptor[] plugins) {
        this.plugins = plugins;
    }

    public Interceptor[] getPlugins() {
        return plugins;
    }

    public void setTypeAliasesPackage(String typeAliasesPackage) {
        this.typeAliasesPackage = typeAliasesPackage;
    }

    public String getTypeAliasesPackage() {
        return typeAliasesPackage;
    }

    public void setTypeAliasesSuperType(Class<?> typeAliasesSuperType) {
        this.typeAliasesSuperType = typeAliasesSuperType;
    }

    public Class<?> getTypeAliasesSuperType() {
        return typeAliasesSuperType;
    }

    public void setTypeHandlersPackage(String typeHandlersPackage) {
        this.typeHandlersPackage = typeHandlersPackage;
    }

    public String getTypeHandlersPackage() {
        return typeHandlersPackage;
    }

    public void setTypeHandlers(TypeHandler<?>[] typeHandlers) {
        this.typeHandlers = typeHandlers;
    }

    public TypeHandler<?>[] getTypeHandlers() {
        return typeHandlers;
    }

    public void setTypeAliases(Class<?>[] typeAliases) {
        this.typeAliases = typeAliases;
    }

    public Class<?>[] getTypeAliases() {
        return typeAliases;
    }

    public void setFailFast(boolean failFast) {
        this.failFast = failFast;
    }

    public boolean getFailFast() {
        return this.failFast;
    }

    public void setConfigLocation(Resource configLocation) {
        this.configLocation = configLocation;
    }

    public Resource getConfigLocation() {
        return configLocation;
    }

    public void setMapperLocations(Resource[] mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public Resource[] getMapperLocations() {
        return mapperLocations;
    }

    public void setConfigurationProperties(Properties sqlSessionFactoryProperties) {
        this.configurationProperties = sqlSessionFactoryProperties;
    }

    public Properties getConfigurationProperties() {
        return configurationProperties;
    }

    public void setDataSource(DataSource dataSource) {
        if (dataSource instanceof TransactionAwareDataSourceProxy) {
            this.dataSource = ((TransactionAwareDataSourceProxy) dataSource).getTargetDataSource();
        } else {
            this.dataSource = dataSource;
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setSqlSessionFactoryBuilder(SqlSessionFactoryBuilder sqlSessionFactoryBuilder) {
        this.sqlSessionFactoryBuilder = sqlSessionFactoryBuilder;
    }

    public SqlSessionFactoryBuilder getSqlSessionFactoryBuilder() {
        return sqlSessionFactoryBuilder;
    }

    public void setTransactionFactory(TransactionFactory transactionFactory) {
        this.transactionFactory = transactionFactory;
    }

    public TransactionFactory getTransactionFactory() {
        return transactionFactory;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getEnvironment() {
        return environment;
    }

    public void afterPropertiesSet() throws Exception {
        notNull(getDataSource(), "Property 'dataSource' is required");
        notNull(getSqlSessionFactoryBuilder(), "Property 'sqlSessionFactoryBuilder' is required");
        this.sqlSessionFactory = buildSqlSessionFactory();
    }

    protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
        Configuration configuration;

        XMLConfigBuilder xmlConfigBuilder = null;
        if (getConfigLocation() != null) {
            xmlConfigBuilder = new XMLConfigBuilder(getConfigLocation().getInputStream(), null, getConfigurationProperties());
            configuration = xmlConfigBuilder.getConfiguration();
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Property 'configLocation' not specified, using default MyBatis Configuration");
            }
            configuration = new Configuration();
            configuration.setVariables(getConfigurationProperties());
        }

        if (getObjectFactory() != null) {
            configuration.setObjectFactory(getObjectFactory());
        }

        if (getObjectWrapperFactory() != null) {
            configuration.setObjectWrapperFactory(getObjectWrapperFactory());
        }

        if (hasLength(getTypeAliasesPackage())) {
            String[] typeAliasPackageArray = tokenizeToStringArray(getTypeAliasesPackage(), ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
            for (String packageToScan : typeAliasPackageArray) {
                configuration.getTypeAliasRegistry().registerAliases(packageToScan, getTypeAliasesSuperType() == null ? Object.class : getTypeAliasesSuperType());
                if (logger.isDebugEnabled()) {
                    logger.debug("Scanned package: '" + packageToScan + "' for aliases");
                }
            }
        }

        if (!isEmpty(getTypeAliases())) {
            for (Class<?> typeAlias : getTypeAliases()) {
                configuration.getTypeAliasRegistry().registerAlias(typeAlias);
                if (logger.isDebugEnabled()) {
                    logger.debug("Registered type alias: '" + typeAlias + "'");
                }
            }
        }

        Interceptor[] interceptors = isEmpty(getPlugins()) ? getDefaultInterceptors() : getPlugins();
        for (Interceptor plugin : interceptors) {
            configuration.addInterceptor(plugin);
            if (logger.isDebugEnabled()) {
                logger.debug("Registered plugin: '" + plugin + "'");
            }
        }

        if (hasLength(getTypeHandlersPackage())) {
            String[] typeHandlersPackageArray = tokenizeToStringArray(getTypeHandlersPackage(), ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
            for (String packageToScan : typeHandlersPackageArray) {
                configuration.getTypeHandlerRegistry().register(packageToScan);
                if (logger.isDebugEnabled()) {
                    logger.debug("Scanned package: '" + packageToScan + "' for type handlers");
                }
            }
        }

        if (!isEmpty(getTypeHandlers())) {
            for (TypeHandler<?> typeHandler : getTypeHandlers()) {
                configuration.getTypeHandlerRegistry().register(typeHandler);
                if (logger.isDebugEnabled()) {
                    logger.debug("Registered type handler: '" + typeHandler + "'");
                }
            }
        }

        if (xmlConfigBuilder != null) {
            try {
                xmlConfigBuilder.parse();

                if (logger.isDebugEnabled()) {
                    logger.debug("Parsed configuration file: '" + getConfigLocation() + "'");
                }
            } catch (Exception ex) {
                throw new NestedIOException("Failed to parse config resource: " + getConfigLocation(), ex);
            } finally {
                ErrorContext.instance().reset();
            }
        }

        if (getTransactionFactory() == null) {
            setTransactionFactory(new SpringManagedTransactionFactory());
        }

        Environment environment = new Environment(getEnvironment(), getTransactionFactory(), getDataSource());
        configuration.setEnvironment(environment);

        if (getDatabaseIdProvider() != null) {
            try {
                configuration.setDatabaseId(getDatabaseIdProvider().getDatabaseId(getDataSource()));
            } catch (SQLException e) {
                throw new NestedIOException("Failed getting a databaseId", e);
            }
        }

        if (!isEmpty(getMapperLocations())) {
            for (Resource mapperLocation : getMapperLocations()) {
                if (mapperLocation == null) {
                    continue;
                }

                try {
                    XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(mapperLocation.getInputStream(), configuration, mapperLocation.toString(), configuration.getSqlFragments());
                    xmlMapperBuilder.parse();
                } catch (Exception e) {
                    throw new NestedIOException("Failed to parse mapping resource: '" + mapperLocation + "', " + Files.toString(mapperLocation.getInputStream()), e);
                } finally {
                    ErrorContext.instance().reset();
                }

                if (logger.isDebugEnabled()) {
                    logger.debug("Parsed mapper file: '" + mapperLocation + "'");
                }
            }
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Property 'mapperLocations' was not specified or no matching resources found");
            }
        }

        return getSqlSessionFactoryBuilder().build(configuration);
    }

    public Interceptor[] getDefaultInterceptors() {
        return new Interceptor[0];
    }

    public SqlSessionFactory getObject() throws Exception {
        if (this.sqlSessionFactory == null) {
            afterPropertiesSet();
        }
        return this.sqlSessionFactory;
    }

    public Class<? extends SqlSessionFactory> getObjectType() {
        return this.sqlSessionFactory == null ? SqlSessionFactory.class : this.sqlSessionFactory.getClass();
    }

    public void onApplicationEvent(ApplicationEvent event) {
        if (getFailFast() && event instanceof ContextRefreshedEvent) {
            this.sqlSessionFactory.getConfiguration().getMappedStatementNames();
        }
    }
}