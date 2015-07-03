package cn.limw.summer.spring.hibernate;

import java.io.IOException;
import java.util.List;

import org.hibernate.cfg.NamingStrategy;
import org.hibernate.type.BasicType;
import org.slf4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;

import cn.limw.summer.hibernate.cfg.FlexibleNamingStrategy;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.Mirrors;

/**
 * 继承 org.springframework.orm.hibernate4.LocalSessionFactoryBean , 添加生成表时候带注释的功能
 * @author li
 * @version 1 (2014年6月25日 上午9:17:52)
 * @since Java7
 */
public class SessionFactoryBean extends AbstractSessionFactoryBean {
    private static final Logger log = Logs.slf4j();

    /**
     * 用户自定义数据类型
     */
    private List<Class<?>> userTypes;

    /**
     * 表注释前缀
     */
    private String tableCommentPrefix = "";

    public void configureSessionFactoryBuilder(LocalSessionFactoryBuilder sessionFactoryBuilder) throws IOException {
        super.configureSessionFactoryBuilder(sessionFactoryBuilder);

        ((SessionFactoryBuilder) sessionFactoryBuilder).setTableCommentPrefix(getTableCommentPrefix());

        //添加的自定义类型类型
        if (null != getHibernateProperties()) {
            String userTypeStr = getHibernateProperties().getProperty("hibernate.user_types");
            log.info("configureSessionFactoryBuilder hibernate.user_types={}", userTypeStr);
            if (null != userTypeStr) {
                String[] userTypes = userTypeStr.split(",");
                setUserTypes(Mirrors.classForName(userTypes));
            }
        }

        if (null != userTypes && !userTypes.isEmpty()) {
            for (Class<?> type : userTypes) {
                sessionFactoryBuilder.registerTypeOverride((BasicType) Mirrors.newInstance(type));//注册自定义类型
            }
        }
    }

    public void setHibernatePropertiesLocation(String hibernatePropertiesLocation) {
        try {
            setHibernateProperties(PropertiesLoaderUtils.loadProperties(new ClassPathResource(hibernatePropertiesLocation)));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void setNamingStrategyType(String namingStrategyType) {
        try {
            setNamingStrategy((NamingStrategy) Class.forName(namingStrategyType).newInstance());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private FlexibleNamingStrategy getFlexibleNamingStrategy() {
        if (null == getNamingStrategy()) {
            setNamingStrategy(new FlexibleNamingStrategy());
        } else if (!(getNamingStrategy() instanceof FlexibleNamingStrategy)) {// 非空但不是指定类型
            setNamingStrategy(new FlexibleNamingStrategy(getNamingStrategy()));
        }
        return (FlexibleNamingStrategy) getNamingStrategy();
    }

    public void setTablePrefix(String tablePrefix) {
        getFlexibleNamingStrategy().setTablePrefix(tablePrefix);
    }

    public void setTableSuffix(String tableSuffix) {
        getFlexibleNamingStrategy().setTableSuffix(tableSuffix);
    }

    public void setColumnPrefix(String columnPrefix) {
        getFlexibleNamingStrategy().setColumnPrefix(columnPrefix);
    }

    public void setColumnSuffix(String columnSuffix) {
        getFlexibleNamingStrategy().setColumnSuffix(columnSuffix);
    }

    public void setUserTypes(List<Class<?>> userTypes) {
        this.userTypes = userTypes;
    }

    public String getTableCommentPrefix() {
        return tableCommentPrefix;
    }

    /**
     * 设置表注视前缀
     */
    public void setTableCommentPrefix(String tableCommentPrefix) {
        this.tableCommentPrefix = tableCommentPrefix;
    }
}