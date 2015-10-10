package summer.ioc;

import java.io.DataOutputStream;
import java.io.FileOutputStream;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;

import javassist.ClassPool;

/**
 * @author li
 * @version 1 (2015年10月10日 下午4:54:06)
 * @since Java7
 */
public class Javassist {
    public static void main(String[] args) throws Throwable {
        ReferenceConfig referenceConfig = new ReferenceConfig();
        referenceConfig.setInterface(IUserService.class);

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("11");
        RegistryConfig registry = new RegistryConfig();
        registry.setCheck(false);
        applicationConfig.setRegistry(registry);
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setCheck(false);
        Object object = referenceConfig.get();

        ClassPool classPool = ClassPool.getDefault();
        classPool.get(object.getClass().getName()).toBytecode(new DataOutputStream(new FileOutputStream("C:/Users/li/Desktop/BeanDefinition.class")));

        System.err.println("done");
    }
}