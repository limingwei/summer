package cn.limw.summer.time;

import org.junit.Assert;
import org.junit.Test;

import cn.limw.summer.time.Clock;

/**
 * @author li
 * @version 1 (2015年4月30日 上午9:11:05)
 * @since Java7
 */
public class ClockTimeTest {
    @Test
    public void plus() {
        System.err.println(Clock.now());
        System.err.println(Clock.now().plusMinute(10));
        System.err.println(Clock.now().plusMinute(100));
    }

    @Test
    public void timeBetween() {
        Assert.assertTrue(Clock.now().setTime(10, 0, 0).timeBetween(Clock.now().setTime(9, 0, 0), Clock.now().setTime(18, 0, 0)));
        Assert.assertFalse(Clock.now().setTime(8, 0, 0).timeBetween(Clock.now().setTime(9, 0, 0), Clock.now().setTime(18, 0, 0)));

        Assert.assertTrue(Clock.now().setTime(20, 0, 0).timeBetween(Clock.now().setTime(18, 0, 0), Clock.now().setTime(9, 0, 0)));
    }

    @Test
    public void timeBetween2() {
        Assert.assertFalse(Clock.now().setTime(15, 0, 0).timeBetween(Clock.now().setTime(18, 0, 0), Clock.now().setTime(9, 0, 0)));
    }

    @Test
    public void between() {
        Assert.assertFalse(Clock.now().between(Clock.today(2, 0, 1), Clock.today(2, 59, 59)));
    }
}