package cn.limw.summer.task.fileclean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;

import cn.limw.summer.javax.mail.TransportUtil;
import cn.limw.summer.javax.mail.internet.FileMimeBodyParts;
import cn.limw.summer.javax.mail.internet.MimeMultipart;
import cn.limw.summer.javax.mail.internet.Utf8TextMimeBodyPart;
import cn.limw.summer.mail.Mail;
import cn.limw.summer.mail.javax.JavaxMailSender;
import cn.limw.summer.task.RunOneTimeInManyApplicationContextTask;
import cn.limw.summer.time.Clock;
import cn.limw.summer.util.Files;
import cn.limw.summer.util.ListUtil;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.NetUtil;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2015年4月22日 上午11:40:27)
 * @since Java7
 */
public class AbstractLogFileCleanTask extends RunOneTimeInManyApplicationContextTask {
    private static final Logger log = Logs.slf4j();

    private String logRoot = "/home/*/log";

    private Integer toDeleteHoursLimit = 4 * 24; // 4天

    private List<File> toMailSendLogFiles;

    public String getLogRoot() {
        return logRoot;
    }

    public void setLogRoot(String logRoot) {
        this.logRoot = logRoot;
    }

    public Integer getToDeleteHoursLimit() {
        return toDeleteHoursLimit;
    }

    public void setToDeleteHoursLimit(Integer toDeleteHoursLimit) {
        this.toDeleteHoursLimit = toDeleteHoursLimit;
    }

    /**
     * 每天执行任务时写文件
     */
    private void setDidTaskOnThisServer() {
        File file = new File("/home/*/tmp/log_file_clean_task_did/" + Clock.now().toYYYY_MM_DD() + ".txt");
        file.getParentFile().mkdirs();
        Files.write((getClass() + "\t" + Clock.now().toYYYY_MM_DD_HH_MM_SS()).getBytes(), file);
    }

    /**
     * 判断这台机器上今天是否已经执行过
     */
    private Boolean getDidTaskOnThisServer() {
        return new File("/home/*/tmp/log_file_clean_task_did/" + Clock.now().toYYYY_MM_DD() + ".txt").exists();
    }

    public synchronized void doTaskInternal() {
        try {
            log.info("开始 applicationContext={}", getApplicationContext());
            if (getDidTaskOnThisServer()) {
                log.warn("这台机器上今天已经执行任务, 不再执行");
                return;
            } else {
                setDidTaskOnThisServer();
                _do_task_();
            }
        } catch (Exception e) {
            log.error("LogFileCleanTask error", e);
        }
        log.info("结束  applicationContext={}", getApplicationContext());
    }

    private void _do_task_() {
        toMailSendLogFiles = new ArrayList<File>();
        String[] paths = StringUtil.split(getLogRoot(), ",");
        for (String path : paths) {
            deleteOldFiles(new File(path), getToDeleteHoursLimit());
        }

        if (Clock.now().between(Clock.today(2, 0, 1), Clock.today(2, 59, 59))) { // 定时任务整点执行, 这部分代码仅在凌晨3:00执行
            if (ListUtil.isEmpty(toMailSendLogFiles)) {
                log.info("toMailSendLogFiles 为空, 不发邮件");
            } else {
                doSendLogFiles();
            }
        } else {
            log.info("当前时间是 " + Clock.now().toYYYY_MM_DD_HH_MM_SS() + ", 不发邮件");
        }
    }

    public void doSendLogFiles() {
        log.info("doSendLogFiles toMailSendLogFiles=" + toMailSendLogFiles);
    }

    public void mailSendLogFiles(String host, String user, String password, String to, String title) {
        log.info("发送" + title + "到 " + to);
        try {
            JavaxMailSender javaxMailSender = new JavaxMailSender();
            javaxMailSender.setHost(host);
            javaxMailSender.setUsername(user);
            javaxMailSender.setPassword(password);

            Mail mail = new Mail();
            mail.setTo(to);
            mail.setSubject(title);
            mail.setContent("邮件内容");

            MimeMessage mimeMessage = javaxMailSender.prepareMessage(mail, javaxMailSender.prepareSession());

            String mailContent = mailContent();
            mimeMessage.setContent(new MimeMultipart(new Utf8TextMimeBodyPart(mailContent), new FileMimeBodyParts(toMailSendLogFiles)));

            TransportUtil.send(mimeMessage);
        } catch (Throwable e) {
            log.error("发送" + title + "失败", e);
        }
    }

    private String mailContent() {
        String content = "";
        content += "<br/>当前时间: " + Clock.now().toYYYY_MM_DD_HH_MM_SS();
        content += "当前服务器IP: " + StringUtil.join(", ", NetUtil.ips());
        content += "<br/>applicationContext: " + getApplicationContext();
        content += "<br/><br/>以下日志文件已通过附件发送 <br/>";
        for (File file : toMailSendLogFiles) {
            content += "<br/>" + file + ", 最后修改时间: " + Clock.when(file.lastModified()).toYYYY_MM_DD_HH_MM_SS() + ", 文件大小: " + file.length() + "B =" + (file.length() / 1024) + "KB =" + (file.length() / 1024 / 1024) + "MB =" + (file.length() / 1024 / 1024 / 1024) + "GB";
        }
        return content;
    }

    private void deleteOldFiles(File file, Integer toDeleteHoursLimit) {
        if (file.exists() && file.canRead()) {
            if (file.isFile()) {
                if (isLogFile(file)) {
                    long lastModified = file.lastModified();
                    if (lastModified < Clock.now().minusHour(toDeleteHoursLimit).milliSeconds()) { // 4天前
                        log.info("删除过老的日志文件, filePath={}, lastModified={}", Files.getCanonicalPath(file), Clock.when(file.lastModified()).toYYYY_MM_DD_HH_MM_SS());
                        doDeleteFile(file); // 删除文件
                    }

                    if (lastModified > Clock.now().minusHour(toDeleteHoursLimit).milliSeconds() && lastModified <= Clock.today(0, 0, 0).milliSeconds()) { // 4天内, 今天前
                        toMailSendLogFiles.add(file);
                    }
                } else {
                    log.error("在日志目录里发现非日志文件 {}", Files.getCanonicalPath(file));
                }
            } else if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File each : files) {
                    deleteOldFiles(each, toDeleteHoursLimit);
                }
            }
        } else {
            log.error("文件 {} 不存在或不可读", Files.getCanonicalPath(file));
        }
    }

    private boolean isLogFile(File file) {
        if (null == file || !file.isFile()) {
            return false;
        }
        String name = file.getName();
        if (name.endsWith(".log")) {
            return true; // 业务日志, 或 tomcat
        } else if (name.endsWith(".txt") && name.startsWith("localhost_access_log.")) {
            return true; // tomcat
        }
        return false;
    }

    private void doDeleteFile(File file) {
        try {
            file.delete();
        } catch (Exception e) {
            log.error("删除文件 " + Files.getCanonicalPath(file) + " 失败", e);
        }
    }
}