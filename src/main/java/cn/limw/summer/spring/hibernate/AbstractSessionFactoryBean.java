package cn.limw.summer.spring.hibernate;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.cache.spi.RegionFactory;
import org.hibernate.cfg.NamingStrategy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;

/**
 * @author li
 * @version 1 (2014年8月18日 下午4:22:51)
 * @since Java7
 */
public class AbstractSessionFactoryBean extends LocalSessionFactoryBean {
    private DataSource dataSource;

    private Resource[] configLocations;

    private String[] mappingResources;

    private Resource[] mappingLocations;

    private Resource[] cacheableMappingLocations;

    private Resource[] mappingJarLocations;

    private Resource[] mappingDirectoryLocations;

    private Interceptor entityInterceptor;

    private NamingStrategy namingStrategy;

    private Object jtaTransactionManager;

    private Object multiTenantConnectionProvider;

    private Object currentTenantIdentifierResolver;

    private RegionFactory cacheRegionFactory;

    private Properties hibernateProperties;

    private Class<?>[] annotatedClasses;

    private String[] annotatedPackages;

    private String[] packagesToScan;

    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    private SessionFactory sessionFactory;

    public void afterPropertiesSet() throws IOException {
        LocalSessionFactoryBuilder sessionFactoryBuilder = new SessionFactoryBuilder(getDataSource(), getResourcePatternResolver());
        configureSessionFactoryBuilder(sessionFactoryBuilder);
        setSessionFactory(buildSessionFactory(sessionFactoryBuilder));
    }

    public void configureSessionFactoryBuilder(LocalSessionFactoryBuilder sfb) throws IOException {
        if (getConfigLocations() != null) {
            for (Resource resource : getConfigLocations()) {
                sfb.configure(resource.getURL());
            }
        }

        if (getMappingResources() != null) {
            for (String mapping : getMappingResources()) {
                Resource mr = new ClassPathResource(mapping.trim(), getResourcePatternResolver().getClassLoader());
                sfb.addInputStream(mr.getInputStream());
            }
        }

        if (getMappingLocations() != null) {
            for (Resource resource : getMappingLocations()) {
                sfb.addInputStream(resource.getInputStream());
            }
        }

        if (getCacheableMappingLocations() != null) {
            for (Resource resource : getCacheableMappingLocations()) {
                sfb.addCacheableFile(resource.getFile());
            }
        }

        if (getMappingJarLocations() != null) {
            for (Resource resource : getMappingJarLocations()) {
                sfb.addJar(resource.getFile());
            }
        }

        if (getMappingDirectoryLocations() != null) {
            for (Resource resource : getMappingDirectoryLocations()) {
                File file = resource.getFile();
                if (!file.isDirectory()) {
                    throw new IllegalArgumentException("Mapping directory location [" + resource + "] does not denote a directory");
                }
                sfb.addDirectory(file);
            }
        }

        if (getEntityInterceptor() != null) {
            sfb.setInterceptor(getEntityInterceptor());
        }

        if (getNamingStrategy() != null) {
            sfb.setNamingStrategy(getNamingStrategy());
        }

        if (getJtaTransactionManager() != null) {
            sfb.setJtaTransactionManager(getJtaTransactionManager());
        }

        if (getMultiTenantConnectionProvider() != null) {
            sfb.setMultiTenantConnectionProvider(getMultiTenantConnectionProvider());
        }

        if (getCurrentTenantIdentifierResolver() != null) {
            sfb.setCurrentTenantIdentifierResolver(getCurrentTenantIdentifierResolver());
        }

        if (getCacheRegionFactory() != null) {
            sfb.setCacheRegionFactory(getCacheRegionFactory());
        }

        if (getHibernateProperties() != null) {
            sfb.addProperties(getHibernateProperties());
        }

        if (getAnnotatedClasses() != null) {
            sfb.addAnnotatedClasses(getAnnotatedClasses());
        }

        if (getAnnotatedPackages() != null) {
            sfb.addPackages(getAnnotatedPackages());
        }

        if (getPackagesToScan() != null) {
            sfb.scanPackages(getPackagesToScan());
        }
    }

    public SessionFactory getObject() {
        return this.sessionFactory;
    }

    public Class<?> getObjectType() {
        return (this.sessionFactory != null ? this.sessionFactory.getClass() : SessionFactory.class);
    }

    public void destroy() {
        this.sessionFactory.close();
    }

    public Properties getHibernateProperties() {
        if (this.hibernateProperties == null) {
            this.hibernateProperties = new Properties();
        }
        return this.hibernateProperties;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
    }

    public void setNamingStrategy(NamingStrategy namingStrategy) {
        super.setNamingStrategy(this.namingStrategy = namingStrategy);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setConfigLocation(Resource configLocation) {
        this.configLocations = new Resource[] { configLocation };
    }

    public void setConfigLocations(Resource... configLocations) {
        this.configLocations = configLocations;
    }

    public void setMappingResources(String... mappingResources) {
        this.mappingResources = mappingResources;
    }

    public void setMappingLocations(Resource... mappingLocations) {
        this.mappingLocations = mappingLocations;
    }

    public void setCacheableMappingLocations(Resource... cacheableMappingLocations) {
        this.cacheableMappingLocations = cacheableMappingLocations;
    }

    public void setMappingJarLocations(Resource... mappingJarLocations) {
        this.mappingJarLocations = mappingJarLocations;
    }

    public void setMappingDirectoryLocations(Resource... mappingDirectoryLocations) {
        this.mappingDirectoryLocations = mappingDirectoryLocations;
    }

    public void setEntityInterceptor(Interceptor entityInterceptor) {
        this.entityInterceptor = entityInterceptor;
    }

    public void setJtaTransactionManager(Object jtaTransactionManager) {
        this.jtaTransactionManager = jtaTransactionManager;
    }

    public void setMultiTenantConnectionProvider(Object multiTenantConnectionProvider) {
        this.multiTenantConnectionProvider = multiTenantConnectionProvider;
    }

    public void setCurrentTenantIdentifierResolver(Object currentTenantIdentifierResolver) {
        this.currentTenantIdentifierResolver = currentTenantIdentifierResolver;
    }

    public void setCacheRegionFactory(RegionFactory cacheRegionFactory) {
        this.cacheRegionFactory = cacheRegionFactory;
    }

    public void setHibernateProperties(Properties hibernateProperties) {
        this.hibernateProperties = hibernateProperties;
    }

    public void setAnnotatedClasses(Class<?>... annotatedClasses) {
        this.annotatedClasses = annotatedClasses;
    }

    public void setAnnotatedPackages(String... annotatedPackages) {
        this.annotatedPackages = annotatedPackages;
    }

    public void setPackagesToScan(String... packagesToScan) {
        this.packagesToScan = packagesToScan;
    }

    public NamingStrategy getNamingStrategy() {
        return namingStrategy;
    }

    public Resource[] getConfigLocations() {
        return configLocations;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public ResourcePatternResolver getResourcePatternResolver() {
        return resourcePatternResolver;
    }

    public String[] getMappingResources() {
        return mappingResources;
    }

    public Resource[] getMappingLocations() {
        return mappingLocations;
    }

    public Resource[] getCacheableMappingLocations() {
        return cacheableMappingLocations;
    }

    public Resource[] getMappingJarLocations() {
        return mappingJarLocations;
    }

    public Resource[] getMappingDirectoryLocations() {
        return mappingDirectoryLocations;
    }

    public Interceptor getEntityInterceptor() {
        return entityInterceptor;
    }

    public Object getJtaTransactionManager() {
        return jtaTransactionManager;
    }

    public Object getMultiTenantConnectionProvider() {
        return multiTenantConnectionProvider;
    }

    public Object getCurrentTenantIdentifierResolver() {
        return currentTenantIdentifierResolver;
    }

    public RegionFactory getCacheRegionFactory() {
        return cacheRegionFactory;
    }

    public Class<?>[] getAnnotatedClasses() {
        return annotatedClasses;
    }

    public String[] getAnnotatedPackages() {
        return annotatedPackages;
    }

    public String[] getPackagesToScan() {
        return packagesToScan;
    }
}