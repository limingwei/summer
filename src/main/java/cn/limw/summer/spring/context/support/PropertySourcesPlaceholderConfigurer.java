package cn.limw.summer.spring.context.support;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.springframework.core.io.Resource;

import cn.limw.summer.java.util.PropertiesUtil;
import cn.limw.summer.spring.core.io.util.ResourceUtil;
import cn.limw.summer.util.Files;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2014年12月1日 下午1:32:49)
 * @since Java7
 */
public class PropertySourcesPlaceholderConfigurer extends AbstractPropertySourcesPlaceholderConfigurer {
    private static final Logger log = Logs.slf4j();

    protected void loadProperties(Properties props) throws IOException {
        try {
            super.loadProperties(props);
        } catch (FileNotFoundException e) {
            log.error("FileNotFoundException locations={}", ResourceUtil.toString(getLocations()));
            String url = getClass().getProtectionDomain().getCodeSource().getLocation().toString();
            if (url.endsWith("jar")) {
                doLoadProperties(props);
            } else {
                throw e;
            }
        }
    }

    private void doLoadProperties(Properties properties) {
        if (getLocations() != null) {
            for (Resource location : getLocations()) {
                PropertiesUtil.load(properties, Files.classPathRead(getClass(), location.getFilename()));
            }
        }
    }
}