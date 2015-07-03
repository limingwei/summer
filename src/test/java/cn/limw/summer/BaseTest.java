package cn.limw.summer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import cn.limw.summer.spring.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author li
 * @version 1 (2014年9月1日 上午11:44:04)
 * @since Java7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class BaseTest {
    @Test
    public void _void_() {}
}