package cn.limw.summer.google.code.kaptcha.util;

import java.util.Properties;

import cn.limw.summer.spring.beans.factory.SingletonFactoryBean;

import com.google.code.kaptcha.util.Config;

/**
 * @author li
 * @version 1 (2014年11月14日 上午10:34:08)
 * @since Java7
 */
public class ConfigFactoryBean extends SingletonFactoryBean<Config> {
    private Properties properties;

    public Config getObject() throws Exception {
        return new Config(getProperties());
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}