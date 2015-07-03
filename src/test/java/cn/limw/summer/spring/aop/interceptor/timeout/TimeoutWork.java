package cn.limw.summer.spring.aop.interceptor.timeout;

import cn.limw.summer.spring.aop.timelog.Timelog;
import cn.limw.summer.spring.aop.timeout.Timeout;
import cn.limw.summer.time.Clock;
import cn.limw.summer.util.Threads;

/**
 * @author li
 * @version 1 (2015年3月19日 下午4:30:17)
 * @since Java7
 */
public class TimeoutWork {
    @Timelog
    @Timeout(4)
    public void doWork(Integer seconds) {
        System.err.println(Clock.now().toYYYY_MM_DD_HH_MM_SS() + " 开始任务");
        System.err.println(Clock.now().toYYYY_MM_DD_HH_MM_SS() + " 睡眠 " + seconds + " 秒");
        Threads.sleep(seconds * 1000);
        System.err.println(Clock.now().toYYYY_MM_DD_HH_MM_SS() + " 结束任务");
    }
}