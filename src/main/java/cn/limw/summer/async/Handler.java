package cn.limw.summer.async;

/**
 * @author li
 * @version 1 (2014年7月5日 下午3:52:12)
 * @since Java7
 */
public interface Handler<T> {
    public void handle(T target);
}