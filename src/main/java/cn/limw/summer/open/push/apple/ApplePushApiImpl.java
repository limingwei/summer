package cn.limw.summer.open.push.apple;

import javapns.Push;
import javapns.communication.KeystoreManager;
import javapns.notification.Payload;
import javapns.notification.PushedNotifications;

import org.slf4j.Logger;

import cn.limw.summer.util.Jsons;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2014年11月26日 上午11:24:03)
 * @since Java7
 */
public class ApplePushApiImpl implements ApplePushApi {
    private static final Logger log = Logs.slf4j();

    private String keyStore;

    private String password;

    private Boolean production = false; // true 产品模式 false 开发模式

    public String getKeyStore() {
        return keyStore;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getProduction() {
        return production;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setKeyStore(String keyStore) {
        this.keyStore = keyStore;
    }

    public void setProduction(Boolean production) {
        this.production = production;
    }

    public String push(ApplePushMessage message) {
        try {
            log.info("push() production={}, keyStore={}, password={}, message={}", getProduction(), getKeyStore(), getPassword(), message.toMap());
            KeystoreManager.validateKeystoreParameter(getKeyStore());
            PushedNotifications notifications = Push.payload(new Payload(message.getBody()) {}, getKeyStore(), getPassword()/* 密码 */, getProduction() /* 产品模式false=开发模式 */, message.getDeviceId());
            log.info("ApplePushMessage={}, PushedNotifications ={}", message.toMap(), Jsons.toJson(notifications));
            return notifications.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}