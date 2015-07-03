package cn.limw.summer.spring.util;

/**
 * @author li
 * @version 1 (2014年7月7日 上午9:59:45)
 * @since Java7
 */
public class Profiles {
    public static void active(String profile) {
        System.setProperty("spring.profiles.active", profile);
    }
}