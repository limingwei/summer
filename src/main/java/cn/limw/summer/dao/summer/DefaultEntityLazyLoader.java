package cn.limw.summer.dao.summer;

/**
 * @author li
 * @version 1 (2015年6月24日 下午1:45:00)
 * @since Java7
 */
public class DefaultEntityLazyLoader implements EntityLazyLoader {
    private boolean loaded = false;

    public boolean loaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public Object load() {
        return null;
    }
}