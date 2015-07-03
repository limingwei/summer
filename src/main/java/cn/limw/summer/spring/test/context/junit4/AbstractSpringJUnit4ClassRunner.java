package cn.limw.summer.spring.test.context.junit4;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author li
 * @version 1 (2015年6月3日 下午2:18:00)
 * @since Java7
 */
public class AbstractSpringJUnit4ClassRunner extends SpringJUnit4ClassRunner {
    public AbstractSpringJUnit4ClassRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }
}