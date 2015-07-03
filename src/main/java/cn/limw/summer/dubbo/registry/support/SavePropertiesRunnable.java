package cn.limw.summer.dubbo.registry.support;

/**
 * @author li
 * @version 1 (2014年12月25日 下午3:45:36)
 * @since Java7
 */
public class SavePropertiesRunnable implements Runnable {
    private long version;

    private AbstractRegistry abstractRegistry;

    public SavePropertiesRunnable(long version, AbstractRegistry abstractRegistry) {
        this.version = version;
        this.abstractRegistry = abstractRegistry;
    }

    public void run() {
        abstractRegistry.doSaveProperties(version);
    }
}