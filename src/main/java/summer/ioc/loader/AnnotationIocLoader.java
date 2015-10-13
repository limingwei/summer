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
import summer.ioc.loader.util.AnnotationIocLoaderUtil;
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

    private boolean isInPackage(String className) {
        for (String pkg : packages) {
            if (className.startsWith(pkg)) {
                return true;
            }
        }
        return false;
    }

    public List<BeanDefinition> getBeanDefinitions() {
        List<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();

        List<String> fileList = getClasseFiles();
        for (String classFileName : fileList) {
            try {
                String className = AnnotationIocLoaderUtil.getClassName(classFileName);
                if (!isInPackage(className)) {
                    log.info("{} is not in package", className);
                } else {
                    Class<?> type = AnnotationIocLoaderUtil.classForName(className);
                    if (null == type) {
                        // 
                    } else {
                        Bean beanAnnotation = type.getAnnotation(Bean.class);
                        if (beanAnnotation != null) {
                            BeanDefinition beanDefinition = new BeanDefinition(); // 一个新的Bean
                            beanDefinition.setBeanType(type);
                            beanDefinition.setId(beanAnnotation.value());
                            beanDefinition.setBeanFields(new ArrayList<BeanField>());

                            List<Field> fields = Reflect.getDeclaredFields(type);
                            for (Field field : fields) {
                                Inject injectAnnotation = field.getAnnotation(Inject.class);
                                if (null != injectAnnotation) {
                                    BeanField beanField = new BeanField(); // 一个新的Field
                                    beanField.setName(field.getName());
                                    beanField.setValue(injectAnnotation.value());
                                    String injectType = Reflect.isPrimitiveType(field.getType()) ? BeanField.INJECT_TYPE_VALUE : BeanField.INJECT_TYPE_REFERENCE;
                                    beanField.setInjectType(injectType);
                                    beanDefinition.getBeanFields().add(beanField);
                                }
                            }
                            beanDefinitions.add(beanDefinition);

                            log.debug("add bean: @Bean {} {}", type.getName(), beanDefinition.getId());
                        }
                    }
                }
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        log.info("getBeanDefinitions() returning {}", beanDefinitions.size());
        return beanDefinitions;
    }

    /**
     * 获取所有类文件,从class和jar
     */
    private List<String> getClasseFiles() {
        List<String> classFileList = new ArrayList<String>();
        File rootFolder = new File(".");
        log.info("root = {}", Files.getCanonicalPath(rootFolder));
        List<String> list = Files.list(rootFolder, CLASS_REGEX, true, 1);
        log.info("class 个数 {}", list.size());

        for (String each : list) {
            classFileList.add(each);
        }

        List<String> classFilesInJar = getClassFilesInJar();
        log.info("class in jar 个数 {}", classFilesInJar.size());
        for (String each : classFilesInJar) {
            classFileList.add(each);
        }

        classFileList.addAll(classFilesInJar);
        return classFileList;
    }

    private List<String> getClassFilesInJar() {
        try {
            List<String> classFileList = new ArrayList<String>();
            for (String pkg : packages) {
                URL url = AnnotationIocLoaderUtil.getJarPath(pkg);
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
}