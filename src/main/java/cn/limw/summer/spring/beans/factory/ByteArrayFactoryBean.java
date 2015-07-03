package cn.limw.summer.spring.beans.factory;

import cn.limw.summer.util.Errors;

/**
 * @author li
 * @version 1 (2014年12月5日 下午1:58:39)
 * @since Java7
 */
public class ByteArrayFactoryBean extends SingletonFactoryBean<byte[]> {
    public byte[] getObject() throws Exception {
        throw Errors.notImplemented();
    }
}