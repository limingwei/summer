package cn.limw.summer.java.sql.wrapper;

import java.sql.SQLException;
import java.sql.Wrapper;

/**
 * @author li
 * @version 1 (2014年9月29日 下午2:00:58)
 * @since Java7
 */
public class WrapperWrapper implements Wrapper {
    private Wrapper wrapper;

    public WrapperWrapper() {}

    public WrapperWrapper(Wrapper wrapper) {
        this.wrapper = wrapper;
    }

    public void setWrapper(Wrapper wrapper) {
        this.wrapper = wrapper;
    }

    public Wrapper getWrapper() {
        return this.wrapper;
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return getWrapper().unwrap(iface);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return getWrapper().isWrapperFor(iface);
    }
}