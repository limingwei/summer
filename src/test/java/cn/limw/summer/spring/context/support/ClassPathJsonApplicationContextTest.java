package cn.limw.summer.spring.context.support;

import org.junit.Test;

import cn.limw.summer.spring.context.json.ClassPathJsonApplicationContext;

/**
 * @author li
 * @version 1 (2014年12月11日 下午5:29:20)
 * @since Java7
 */
public class ClassPathJsonApplicationContextTest {
    @Test
    public void doTest() {
        try {
            ClassPathJsonApplicationContext context = new ClassPathJsonApplicationContext("spring.js");
            System.err.println(context);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}