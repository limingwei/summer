package cn.limw.summer.dao.summer;

/**
 * @author li
 * @version 1 (2015年6月24日 下午1:34:51)
 * @since Java7
 */
public interface EntityLazyLoader {
    public boolean loaded();

    public Object load();
}