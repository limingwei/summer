package cn.limw.summer.dubbo;

import java.io.IOException;

import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年4月29日 下午3:09:24)
 * @since Java7
 */
public class DubboStarter {
    private static final Logger log = Logs.slf4j();

    private ApplicationContext applicationContext;

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 导出所有依赖Jar包的命令
     * mvn dependency:copy-dependencies -DoutputDirectory=target/lib
     * 执行Main的命令
     * java -jar mail-0.0.1-SNAPSHOT.jar
     * 带端口和应用名的执行命令
     * java -Ddubbo.application.name=**-user-service-1 -Ddubbo.protocol.dubbo.port=20881 -jar mail-0.0.1-SNAPSHOT.jar
     * 命令行不需要定制Main类的启动方式
     * java -Dspring.profiles.active="run" -cp .;mail-0.0.1-SNAPSHOT.jar;lib/*.jar Dubbo
     * Maven插件不需要定制Main类的启动方式
     * exec:java -Dspring.profiles.active="run" -Dexec.mainClass="Dubbo"
     * @param configLocations 默认为 spring.xml
     */
    public void run(String... configLocations) {
        try {
            if (null == configLocations || configLocations.length < 1) {
                configLocations = new String[] { "spring.xml" };//参数默认值
            }
            log.info("dobbo starting");

            ClassPathXmlApplicationContext context = newApplicationContext(configLocations);
            context.start();

            setApplicationContext(context);

            log.info("dobbo started, System.in.read()");
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ClassPathXmlApplicationContext newApplicationContext(String[] configLocations) {
        return new ClassPathXmlApplicationContext(configLocations);
    }
}