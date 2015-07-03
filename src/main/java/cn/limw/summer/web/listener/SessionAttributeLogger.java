package cn.limw.summer.web.listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2014年7月16日 下午1:15:26)
 * @since Java7
 */
public class SessionAttributeLogger implements HttpSessionAttributeListener {
    private static Logger log = Logs.slf4j();

    public void attributeAdded(HttpSessionBindingEvent event) {
        log.debug("session attributeAdded id={} source={} name={} value={}", event.getSession().getId(), event.getSource(), event.getName(), event.getValue());
    }

    public void attributeRemoved(HttpSessionBindingEvent event) {
        log.debug("session attributeRemoved id={} source={} name={} value={}", event.getSession().getId(), event.getSource(), event.getName(), event.getValue());
    }

    public void attributeReplaced(HttpSessionBindingEvent event) {
        log.debug("session attributeReplaced id={} source={} name={} value={}", event.getSession().getId(), event.getSource(), event.getName(), event.getValue());
    }
}