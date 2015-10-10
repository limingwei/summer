package summer.ioc;

import java.io.DataOutputStream;
import java.io.FileOutputStream;

import javassist.ClassPool;

/**
 * @author li
 * @version 1 (2015年10月10日 下午4:54:06)
 * @since Java7
 */
public class Javassist {
    public static void main(String[] args) throws Throwable {
        ClassPool classPool = ClassPool.getDefault();
        classPool.makeClass("aaaaaaaaaaS");
        classPool.get("aaaaaaaaaaS").toBytecode(new DataOutputStream(new FileOutputStream("C:/Users/li/Desktop/BeanDefinition.class")));
    }
}