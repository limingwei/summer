package cn.limw.summer.spring.aop.interceptor.timeout;

import javax.annotation.Resource;

import org.junit.Test;

import cn.limw.summer.BaseTest;

/**
 * @author li
 * @version 1 (2015年3月19日 下午4:29:09)
 * @since Java7
 */
public class TimeoutInterceptorTest extends BaseTest {
    @Resource
    TimeoutWork timeoutWork;

    @Test
    public void doWork() {
        timeoutWork.doWork(2);
    }
}