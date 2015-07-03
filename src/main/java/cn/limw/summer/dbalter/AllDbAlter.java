package cn.limw.summer.dbalter;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;

import cn.limw.summer.spring.context.AbstractApplicationContextAware;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年5月18日 下午3:29:20)
 * @since Java7
 */
public class AllDbAlter extends AbstractApplicationContextAware {
    private static final Logger log = Logs.slf4j();

    public void run() {
        Map<String, DbAlter> map = getApplicationContext().getBeansOfType(DbAlter.class);
        Set<Entry<String, DbAlter>> entrySet = map.entrySet();
        log.info("dbAlters={}", entrySet);

        for (Entry<String, DbAlter> entry : entrySet) {
            DbAlter value = entry.getValue();
            try {
                value.run();
            } catch (Throwable e) {
                log.error("run " + value + " error", e);
            }
        }
    }
}