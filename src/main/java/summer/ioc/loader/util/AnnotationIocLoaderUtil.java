package summer.ioc.loader.util;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;

/**
 * @author li
 * @version 1 (2015年10月13日 下午4:01:00)
 * @since Java7
 */
public class AnnotationIocLoaderUtil {
    public static URL getJarPath(String pkg) {
        try {
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(pkg.replace('.', '/'));
            while (resources.hasMoreElements()) {
                URL url = (URL) resources.nextElement();
                return url;
            }
            return null;
        } catch (Exception e) {
            throw (e instanceof RuntimeException) ? (RuntimeException) e : new RuntimeException(e);
        }
    }

    public static Class<?> classForName(String typeName) {
        try {
            return Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * 取/classes/之后的字符串(如果有/classes/的话),替换/为.,去掉.class
     */
    public static String getClassName(String classFileName) {
        int classesIndex = classFileName.indexOf(File.separator + "classes" + File.separator);
        int testClassesIndex = classFileName.indexOf(File.separator + "test-classes" + File.separator);

        if (classesIndex > 0) {
            return classFileName.substring(classesIndex + 9, classFileName.length() - 6).replace('/', '.').replace('\\', '.');
        } else if (testClassesIndex > 0) {
            return classFileName.substring(testClassesIndex + 14, classFileName.length() - 6).replace('/', '.').replace('\\', '.');
        } else {
            return classFileName.substring(0, classFileName.length() - 6).replace('/', '.').replace('\\', '.');
        }
    }
}