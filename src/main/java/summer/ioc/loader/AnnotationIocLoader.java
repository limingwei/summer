package summer.ioc.loader;

import java.io.File;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import summer.ioc.BeanDefinition;
import summer.ioc.BeanField;
import summer.ioc.IocLoader;
import summer.ioc.annotation.Bean;
import summer.ioc.annotation.Inject;
import summer.log.Logger;
import summer.util.Files;
import summer.util.Log;
import summer.util.Reflect;

/**
 * @author li
 * @version 1 (2015年10月10日 上午10:37:06)
 * @since Java7
 */
public class AnnotationIocLoader implements IocLoader {
    private static final Logger log = Log.slf4j();

    private static final String CLASS_REGEX = "^.*\\.class$";

    private String[] packages;

    public AnnotationIocLoader(String[] packages) {
        this.packages = packages;
    }

    public List<BeanDefinition> getBeanDefinitions() {
        List<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();

        List<String> fileList = getClasseFiles();
        for (String classFileName : fileList) {
            try {
                Class<?> type = Reflect.classForName(getClassName(classFileName));
                Bean beanAnnotation = type.getAnnotation(Bean.class);
                if (beanAnnotation != null) {
                    BeanDefinition iocBean = new BeanDefinition();// 一个新的Bean
                    iocBean.setBeanType(type);
                    iocBean.setId(beanAnnotation.value());
                    iocBean.setBeanFields(new ArrayList<BeanField>());

                    List<Field> fields = Reflect.getDeclaredFields(type);
                    for (Field field : fields) {
                        Inject inject = field.getAnnotation(Inject.class);
                        if (null != inject) {
                            BeanField attribute = new BeanField();// 一个新的Field
                            attribute.setName(field.getName());
                            attribute.setValue(inject.value());
                            iocBean.getBeanFields().add(attribute);
                        }
                    }
                    beanDefinitions.add(iocBean);
                    log.debug("ADD BEAN: @Bean ? ?", type.getName(), iocBean.getId());
                }
            } catch (Throwable e) {} // class not found 啥的，太多了，就先不打日志了
        }

        log.info("getBeanDefinitions() returning {}", beanDefinitions.size());
        return beanDefinitions;
    }

    /**
     * 取/classes/之后的字符串(如果有/classes/的话),替换/为.,去掉.class
     */
    private static String getClassName(String classFileName) {
        int classesIndex = classFileName.indexOf(File.separator + "classes" + File.separator);
        return classFileName.substring(classesIndex > 0 ? classesIndex + 9 : 0, classFileName.length() - 6).replace('/', '.').replace('\\', '.');
    }

    /**
     * 获取所有类文件,从class和jar
     */
    private List<String> getClasseFiles() {
        List<String> classFileList = new ArrayList<String>();

        File rootFolder = Files.root(); // test-classes
        System.err.println("rootFolder=" + rootFolder);
        List<String> list = Files.list(rootFolder, CLASS_REGEX, true, 1);

        for (String each : list) {
            System.err.println(each);
        }

        List<String> classFilesInJar = getClassFilesInJar();
        for (String each : classFilesInJar) {
            System.err.println(each);
        }

        classFileList.addAll(classFilesInJar);
        return classFileList;
    }

    private List<String> getClassFilesInJar() {
        try {
            List<String> classFileList = new ArrayList<String>();
            for (String pkg : packages) {
                URL url = getJarPath(pkg);
                if (null != url) {
                    // 得到协议的名称  
                    String protocol = url.getProtocol();
                    // 如果是以文件的形式保存在服务器上  
                    if ("jar".equals(protocol)) {
                        JarFile jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
                        Enumeration<JarEntry> entries = jarFile.entries();
                        while (entries.hasMoreElements()) {
                            JarEntry jarEntry = (JarEntry) entries.nextElement();
                            if (!jarEntry.isDirectory()) {
                                String entryName = jarEntry.getName();
                                if (entryName.endsWith(".class")) {
                                    classFileList.add(entryName);
                                }
                            }
                        }
                    }
                }
            }
            return classFileList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private URL getJarPath(String pkg) {
        try {
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(pkg.replace('.', '/'));
            while (resources.hasMoreElements()) {
                URL url = (URL) resources.nextElement();
                return url;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}