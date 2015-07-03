package cn.limw.summer.setup;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;
import org.slf4j.Logger;

import cn.limw.summer.freemarker.util.FreeMarkerUtil;
import cn.limw.summer.java.io.util.BufferedReaderUtil;
import cn.limw.summer.java.util.PropertiesUtil;
import cn.limw.summer.util.Files;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.Maps;
import cn.limw.summer.util.NetUtil;

/**
 * 这不是测试, 这是环境初始化代码
 * 读取配置文件模版, 生成配置文件 复制到 /conf
 * @author li
 * @version 1 (2015年4月7日 下午5:25:14)
 * @since Java7
 */
public class SetupProjectConfig {
    private static final Logger log = Logs.slf4j();

    String project_config_template = "/home/*/source-code/*-support/bin/project-config-template/";

    String config_template = project_config_template + "*-config.properties";

    String config_output_folder = "/home/*/conf/";

    String config_path = config_output_folder + "*-config.properties";

    @Test
    public void setupProjectConfig() throws IOException {
        Map rootMap = Maps.toMap("THIS_IP", thisInnerIp());

        if (!Files.exists(config_path)) { // 如果不存在, 直接复制模版
            log.info(config_path + " not exists, creating");
            newWriteRootConfig(rootMap);
        }

        // 如果存在但缺少key, 则添加key
        Properties config = PropertiesUtil.load(new FileInputStream(config_path));
        Properties template = PropertiesUtil.load(new FileInputStream(config_template));
        if (!config.keySet().containsAll(template.keySet())) {
            updateWriteRootConfig(rootMap, config);
        }

        Properties data = PropertiesUtil.load(Files.fileInputStream(config_path));

        checkConfig(data);

        buildConfigFiles(data);
    }

    private void checkConfig(Properties properties) {
        String this_ip = "" + properties.get("THIS_IP");
        if (NetUtil.ips().contains(this_ip)) {
            // 正确的ip
        } else {
            throw new RuntimeException("THIS_IP 错误, " + this_ip + "不是本机IP");
        }
    }

    private void newWriteRootConfig(Map rootMap) throws FileNotFoundException {
        FreeMarkerUtil.merge(new FileInputStream(config_template), rootMap, Files.fileOutputStream(config_path));
    }

    private void updateWriteRootConfig(Map rootMap, Properties config) throws IOException {
        String out = addKeys(config);
        FreeMarkerUtil.merge(new ByteArrayInputStream(out.getBytes()), rootMap, Files.fileOutputStream(config_path));
    }

    private void buildConfigFiles(Properties data) throws FileNotFoundException {
        List<String> files = Files.listFiles(new File(project_config_template), ".*", true, 1);
        for (String each : files) {
            if (each.endsWith("entries")) {
                //
            } else if (!new File(each).isFile()) {
                //
            } else if (each.endsWith(config_template)) {
                //
            } else if (each.endsWith(".svn-base")) {
                //
            } else if (each.endsWith(".properties")) {
                log.info("Merge config file " + each);
                FileInputStream temp = new FileInputStream(each);
                FileOutputStream out = new FileOutputStream(config_output_folder + new File(each).getName());
                FreeMarkerUtil.merge(temp, data, out);
            } else {
                log.info("Copy config file " + each);
                FileInputStream temp = new FileInputStream(each);
                FileOutputStream out = new FileOutputStream(config_output_folder + new File(each).getName());
                Files.write(temp, out);
            }
        }
    }

    private String thisInnerIp() {
        List<String> ips = NetUtil.ips();
        for (String ip : ips) {
            if (ip.startsWith("10.")) {
                return ip;
            }
        }
        return "no-value";
    }

    private String addKeys(Properties config) throws IOException {
        log.info("config add keys");
        String out = "";
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(config_template)))) {
            String line = null;
            while ((line = BufferedReaderUtil.readLine(bufferedReader)) != null) {
                int i = line.indexOf('=');
                if (i > 0) {
                    String key = line.substring(0, i);
                    out += key + "=" + config.getProperty(key) + "\n";
                } else {
                    out += line + "\n";
                }
            }
        }
        return out;
    }
}