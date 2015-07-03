package cn.limw.summer.util;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author li
 * @version 1 (2015年4月14日 下午6:46:25)
 * @since Java7
 */
@RunWith(Suite.class)
@SuiteClasses({ ArrayUtilTest.class, Base64UtilTest.class, BoolUtilTest.class, NetUtilTest.class, StringUtilTest.class, TimesTest.class })
public class AllUtilTest {}