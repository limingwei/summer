package cn.limw.summer.javax.mail;

import javax.mail.AuthenticationFailedException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

import cn.limw.summer.javax.mail.exception.AuthenticationFailedRuntimeException;

/**
 * @author li
 * @version 1 (2015年2月12日 下午1:08:38)
 * @since Java7
 */
public class StoreUtil {
    public static Store connect(Session session, String protocol, String host, Integer port, String username, String password) {
        try {
            URLName urlName = new URLName(protocol, host, port, null /* String file */, username, password);
            Store store = session.getStore(urlName);
            store.connect();
            return store;
        } catch (AuthenticationFailedException e) {
            throw new AuthenticationFailedRuntimeException(e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() + ", session=" + session + ", protocol=" + protocol + ", host=" + host + ", port=" + port + ", username=" + username + ", password=" + password, e);
        }
    }
}