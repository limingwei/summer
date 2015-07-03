package cn.limw.summer.async;

import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年3月25日 下午5:56:26)
 * @since Java7
 */
public class LimitedHandler<T> implements Handler<T> {
    private static final Logger log = Logs.slf4j();

    private Integer limit;

    private Integer counter;

    public LimitedHandler(Integer limit) {
        this.limit = limit;
        this.counter = 0;

        log.info("LimitedHandler inited limit=" + limit);
    }

    public final void handle(T target) {
        counter++;

        if (counter < limit) {
            doHandle(target);
        } else if (counter == limit) { // 等于
            whenReachLimit();
        } else { // 大于
            whenOverLimit();
        }
    }

    /**
     * 超过限制
     */
    public void whenOverLimit() {}

    /**
     * 达到限制
     */
    public void whenReachLimit() {
        log.info("LimitedHandler执行次数 " + counter + " 已经达到上限 " + limit + " , 后面的将不会执行");
    }

    public void doHandle(T target) {
        log.info("{} doHandle() {}", this, target);
    }
}