package cn.limw.summer.util;

import org.junit.Test;

/**
 * @author li
 * @version 1 (2014年10月9日 上午11:41:47)
 * @since Java7
 */
public class TimesTest {
    @Test
    public void minutesLater() {}

    @Test
    public void minutesBefore() {}

    @Test
    public void between() {
        //        Assert.assertTrue(Times.between(Times.time("08:00:00"), Times.time("07:00:00"), Times.time("09:00:00")));
        //        Assert.assertFalse(Times.between(Times.time("11:00:00"), Times.time("07:00:00"), Times.time("09:00:00")));
        //        Assert.assertTrue(Times.between(Times.time("21:00:00"), Times.time("19:00:00"), Times.time("09:00:00")));
        //        Assert.assertFalse(Times.between(Times.time("07:00:00"), Times.time("19:00:00"), Times.time("09:00:00")));
    }

    @Test
    public void nowBetween() {
        //        Assert.assertFalse(Times.nowBetween(Times.time("00:00:00"), Times.time("00:00:00")));
    }

    @Test
    public void time() {
        //        System.err.println(Times.time("00:00:00").getTime());
        //        System.err.println(Times.time("23:59:59").getTime());
    }
}