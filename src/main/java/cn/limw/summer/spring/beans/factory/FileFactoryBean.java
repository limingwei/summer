package cn.limw.summer.spring.beans.factory;

import java.io.File;

import cn.limw.summer.util.Errors;

/**
 * @author li
 * @version 1 (2014年12月5日 下午1:59:32)
 * @since Java7
 */
public class FileFactoryBean extends SingletonFactoryBean<File> {
    public File getObject() throws Exception {
        throw Errors.notImplemented();
    }
}