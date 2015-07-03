package cn.limw.summer.shiro.web.session.manager;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.slf4j.Logger;

import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年5月6日 下午6:18:38)
 * @since Java7
 */
public class DefaultWebSessionManager extends AbstractDefaultWebSessionManager {
    private static final Logger log = Logs.slf4j();

    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        try {
            return super.retrieveSession(sessionKey);
        } catch (UnknownSessionException e) {
            log.error("UnknownSessionException", e);
            throw e;
        }
    }
}