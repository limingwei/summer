package summer.junit;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * @author li
 * @version 1 (2015年10月15日 下午1:35:55)
 * @since Java7
 * @see org.springframework.test.context.junit4.SpringJUnit4ClassRunner
 */
public class SummerJUnit4Runner extends BlockJUnit4ClassRunner {
    public SummerJUnit4Runner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    protected Object createTest() throws Exception {
        Object testInstance = super.createTest();
        return testInstance;
    }
}