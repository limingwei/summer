package cn.limw.summer.shell.expect4j;

import com.jcraft.jsch.UserInfo;

/**
 * @author li
 * @version 1 (2015年3月9日 下午1:28:08)
 * @since Java7
 */
public class SimpleUserInfo implements UserInfo {
    private String password;

    public SimpleUserInfo(String password) {
        setPassword(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public boolean promptYesNo(String str) {
        return true;
    }

    public String getPassphrase() {
        return null;
    }

    public boolean promptPassphrase(String message) {
        return true;
    }

    public boolean promptPassword(String message) {
        return true;
    }

    public void showMessage(String message) {
        System.err.println("me" + message);
    }
}