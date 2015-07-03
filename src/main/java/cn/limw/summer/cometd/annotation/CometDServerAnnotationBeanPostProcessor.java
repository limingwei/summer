package cn.limw.summer.cometd.annotation;

import org.cometd.annotation.ServerAnnotationProcessor;
import org.cometd.bayeux.server.BayeuxServer;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;

import cn.limw.summer.util.Asserts;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2014年12月16日 下午2:05:06)
 * @since Java7
 */
public class CometDServerAnnotationBeanPostProcessor implements DestructionAwareBeanPostProcessor {
    private static final Logger log = Logs.slf4j();

    private BayeuxServer bayeuxServer;

    private ServerAnnotationProcessor serverAnnotationProcessor;

    public void setBayeuxServer(BayeuxServer bayeuxServer) {
        this.bayeuxServer = bayeuxServer;
    }

    public BayeuxServer getBayeuxServer() {
        return bayeuxServer;
    }

    public synchronized ServerAnnotationProcessor getServerAnnotationProcessor() {
        if (null == serverAnnotationProcessor) {
            serverAnnotationProcessor = new ServerAnnotationProcessor(Asserts.noNull(getBayeuxServer()));
        }
        return serverAnnotationProcessor;
    }

    public Object postProcessBeforeInitialization(Object bean, String name) throws BeansException {
        log.debug("postProcessBeforeInitialization({},{})", bean, name);

        getServerAnnotationProcessor().processDependencies(bean);
        getServerAnnotationProcessor().processConfigurations(bean);
        getServerAnnotationProcessor().processCallbacks(bean);
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String name) throws BeansException {
        log.debug("postProcessAfterInitialization({},{})", bean, name);
        return bean;
    }

    public void postProcessBeforeDestruction(Object bean, String name) throws BeansException {
        log.trace("postProcessBeforeDestruction({},{})", bean, name);
        getServerAnnotationProcessor().deprocessCallbacks(bean);
    }
}
