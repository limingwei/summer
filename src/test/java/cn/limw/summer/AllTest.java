package cn.limw.summer;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import cn.limw.summer.time.ClockTimeTest;
import cn.limw.summer.util.AllUtilTest;

/**
 * @author li
 * @version 1 (2014年10月27日 上午10:12:17)
 * @since Java7
 */
@RunWith(Suite.class)
@SuiteClasses({ AllUtilTest.class, ClockTimeTest.class })
public class AllTest {}