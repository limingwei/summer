package summer.junit;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import summer.ioc.IocContext;
import summer.ioc.annotation.Inject;
import summer.util.Reflect;

/**
 * @author li
 * @version 1 (2015年10月15日 下午1:35:55)
 * @since Java7
 * @see org.springframework.test.context.junit4.SpringJUnit4ClassRunner
 */
public class SummerJUnit4Runner extends BlockJUnit4ClassRunner {
    private static IocContext iocContext;

    public SummerJUnit4Runner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    public synchronized IocContext getIocContext() {
        try {
            if (null == iocContext) {
                iocContext = (IocContext) getClass().getMethod("getIocContext").invoke(this);
            }
            return iocContext;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Object createTest() throws Exception {
        Object testInstance = super.createTest();
        List<Field> fields = Reflect.getDeclaredFields(getTestClass().getJavaClass());
        for (Field field : fields) {
            Inject inject = field.getAnnotation(Inject.class);
            if (null != inject) {
                Reflect.setFieldValue(this, field, getIocContext().getBean(field.getType(), inject.value()));
            }
        }
        return testInstance;
    }
}