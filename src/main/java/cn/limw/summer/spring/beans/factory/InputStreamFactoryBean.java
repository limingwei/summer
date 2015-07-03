package cn.limw.summer.spring.beans.factory;

import java.io.InputStream;

import cn.limw.summer.util.Errors;

/**
 * @author li
 * @version 1 (2014年12月5日 下午1:58:46)
 * @since Java7
 */
public class InputStreamFactoryBean extends SingletonFactoryBean<InputStream> {
    public InputStream getObject() throws Exception {
        throw Errors.notImplemented();
    }
}