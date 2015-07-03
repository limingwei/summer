package cn.limw.summer.spring.beans.factory;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import cn.limw.summer.java.lang.SystemUtil;

public class Demo {
    public static void main(String[] args) {
        Map<String, String> getenv = System.getenv(); // 系统环境
        for (Entry<String, String> entry : getenv.entrySet()) {
            System.out.println("env -> " + entry.getKey() + "=" + entry.getValue());
        }
        final Properties properties = SystemUtil.getProperties(); // java环境
        for (Entry<Object, Object> entry : properties.entrySet()) {
            System.out.println("properties -> " + entry.getKey() + "=" + entry.getValue());
        }
    }
}