package cn.limw.summer.dubbo.extension;

import com.alibaba.dubbo.common.extension.ExtensionFactory;

/**
 * @author li
 * @version 1 (2015年4月16日 下午7:03:15)
 * @since Java7
 */
public class SpringExtensionFactory implements ExtensionFactory {
    public <T> T getExtension(Class<T> type, String name) {
        return null;
    }
}