package cn.limw.summer.shell;

/**
 * @author li
 * @version 1 (2015年3月9日 下午1:31:42)
 * @since Java7
 */
public class ShellServerMeta {
    private String host;

    private Integer port;

    private String username;

    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}