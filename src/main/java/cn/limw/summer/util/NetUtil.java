package cn.limw.summer.util;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;

import cn.limw.summer.java.collection.NiceToStringList;

/**
 * @author li
 * @version 1 (2014年10月15日 上午11:06:21)
 * @since Java7
 */
public class NetUtil {
    private static final Logger log = Logs.slf4j();

    @SuppressWarnings("unchecked")
    public static List<String> ips() {
        try {
            List<String> ips = new ArrayList<String>();
            for (Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces(); networkInterfaces.hasMoreElements();) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                for (Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses(); inetAddresses.hasMoreElements();) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    String hostAddress = inetAddress.getHostAddress();
                    ips.add(hostAddress);
                }
            }
            return null == ips ? null : new NiceToStringList(ips);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Boolean reachable(String host, Integer port) {
        return !(new InetSocketAddress(host, port).isUnresolved());
    }

    public static String getLocalHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "UnknownLocalHost";
        }
    }

    public static String publicIp() {
        List<String> ips = ips();
        for (String ip : ips) {
            if (!StringUtil.isEmpty(ip) && !ip.startsWith("127.") && !ip.startsWith("10.")) {
                return ip;
            }
        }
        log.error("publicIp return null, ips=" + StringUtil.join(",", ips));
        return null;
    }

    public static List<String> ipsWithout127() {
        return ArrayUtil.remove(ips(), "127.0.0.1");
    }
}