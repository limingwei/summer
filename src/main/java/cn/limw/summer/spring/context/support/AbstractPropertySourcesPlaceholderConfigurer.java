package cn.limw.summer.spring.context.support;

import org.springframework.core.io.Resource;

/**
 * 和 org.springframework.context.support.PropertySourcesPlaceholderConfigurer 无异, 仅添加一些Getter
 * @author li
 * @version 1 (2014年12月1日 下午2:00:25)
 * @since Java7
 */
public class AbstractPropertySourcesPlaceholderConfigurer extends org.springframework.context.support.PropertySourcesPlaceholderConfigurer {
    private Resource[] locations;

    private String fileEncoding;

    public String getFileEncoding() {
        return fileEncoding;
    }

    public void setLocations(Resource[] locations) {
        this.locations = locations;
        super.setLocations(locations);
    }

    public void setLocation(Resource location) {
        setLocations(new Resource[] { location });
    }

    public Resource[] getLocations() {
        return locations;
    }

    public void setFileEncoding(String fileEncoding) {
        this.fileEncoding = fileEncoding;
        super.setFileEncoding(fileEncoding);
    }
}