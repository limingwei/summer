package cn.limw.summer.mail.util;

import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;

import cn.limw.summer.java.lang.SystemUtil;
import cn.limw.summer.javax.mail.PasswordAuthenticator;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.Maps;
import cn.limw.summer.util.StringUtil;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.pop3.POP3Folder;
import com.sun.mail.util.DecodingException;
import com.sun.mail.util.MailSSLSocketFactory;

/**
 * @author li
 * @version 1 (2014年7月5日 下午4:24:01)
 * @since Java7
 */
public class MailUtil {
    private static final Logger log = Logs.slf4j();

    private static final Map<String, String> RECEIVE_HOSTS = Maps.toMap("@qq.com", "pop.qq.com", "@mail.com", "pop3.mail.com");

    private static final Map<String, String> SEND_HOSTS = Maps.toMap("@qq.com", "smtp.qq.com", "@mail.com", "smtp.mail.com");

    public static String getPrefix(String address) {
        int index = address.indexOf('@');
        if (index < 0) {
            throw new RuntimeException("can not get email prefix for address [" + address + "]");
        }
        return address.substring(0, index);
    }

    public static String getSuffix(String address) {
        int index = address.indexOf('@');
        if (index < 0) {
            throw new RuntimeException("can not get email suffix for address '" + address + "'");
        }
        return address.substring(index);
    }

    public static String guessReceiveHost(String address) {
        return RECEIVE_HOSTS.get(getSuffix(address).toLowerCase());
    }

    public static String guessSendHost(String address) {
        return SEND_HOSTS.get(getSuffix(address).toLowerCase());
    }

    public static String getContent(Message message) {
        try {
            return readContent(message.getContent());
        } catch (DecodingException e) {
            return e.getMessage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String readContent(Object content) throws Exception {
        if (content instanceof String) {
            return (String) content;
        } else if (content instanceof MimeMultipart) {
            return readContent(((MimeMultipart) content).getBodyPart(0).getContent());
        } else {
            throw new RuntimeException(content.getClass().toString());
        }
    }

    public static String getSubject(Message message) {
        try {
            return message.getSubject();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getUid(Message message) {
        try {
            Folder folder = message.getFolder();
            if (folder instanceof POP3Folder) {
                return ((POP3Folder) folder).getUID(message);
            } else if (folder instanceof IMAPFolder) {
                return "" + ((IMAPFolder) folder).getUID(message);
            } else {
                throw new RuntimeException("unkonwn message type " + message.getClass().getName() + ", folder type " + folder.getClass().getName());
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static InternetAddress[] toAddressArray(String address) {
        InternetAddress internetAddress = toAddress(address);
        return null == internetAddress ? null : new InternetAddress[] { internetAddress };
    }

    public static InternetAddress toAddress(String address) {
        try {
            return StringUtil.isEmpty(address) ? null : new InternetAddress(address);
        } catch (AddressException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<InternetAddress> toAddressList(String address) {
        InternetAddress[] addressArray = toAddressArray(address);
        return null == addressArray ? null : Arrays.asList(addressArray);
    }

    public static String getRecipients(Message message, RecipientType recipientType) {
        try {
            return addressToString(message.getRecipients(recipientType));
        } catch (AddressException e) {
            return e.getMessage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFrom(Message message) {
        try {
            return addressToString(message.getFrom());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getTo(Message message) {
        return getRecipients(message, RecipientType.TO);
    }

    public static String getCc(Message message) {
        return getRecipients(message, RecipientType.CC);
    }

    public static String getBcc(Message message) {
        return getRecipients(message, RecipientType.BCC);
    }

    public static String addressToString(Address... addresses) {
        List<String> values = new ArrayList<String>();
        if (null != addresses) {
            for (Address address : addresses) {
                values.add(((InternetAddress) address).getAddress());
            }
        }
        return StringUtil.join(",", values);
    }

    public static void addRecipients(Message message, RecipientType recipientType, String... addresses) {
        try {
            for (String each : addresses) {
                message.addRecipient(recipientType, new InternetAddress(each));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Boolean isMailAddress(String mailAddress) {
        if (StringUtil.isEmpty(mailAddress)) {
            return false;
        }
        String pattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        try {
            return Pattern.matches(pattern, mailAddress);
        } catch (PatternSyntaxException e) {}
        return false;
    }

    public static String getDomain(String mailAddress) {
        if (isMailAddress(mailAddress)) {
            return mailAddress.substring(mailAddress.indexOf('@') + 1);
        } else {
            return "";
        }
    }

    public static boolean testEmail(String protocol, String host, String email, String password, boolean isSsl, String port) {
        if (protocol.equals("smtp")) {
            return testSMTP(host, email, password, isSsl, port);
        } else {
            boolean flag = false;
            Store store = null;
            try {
                store = getStore(protocol, host, email, password, isSsl, port);
                store.connect(email, password);
                flag = store.isConnected();
            } catch (Exception e) {
                log.error("" + e, e);
            } finally {
                if (store != null) {
                    try {
                        store.close();
                    } catch (MessagingException e) {
                        log.error("" + e, e);
                    }
                }
            }
            return flag;
        }

    }

    public static boolean testSMTP(String host, String email, String password, boolean isSsl, String port) {
        boolean flag = false;
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.transport.protocol", "smtp");
        if (isSsl) {
            props.put("mail.smtp.ssl.enable", "true");
            MailSSLSocketFactory sf = null;
            try {
                sf = new MailSSLSocketFactory();
                sf.setTrustAllHosts(true);
                props.put("mail.smtp.ssl.socketFactory", sf);
            } catch (GeneralSecurityException e) {
                log.error("" + e, e);
            }
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");
        }
        if (StringUtil.isEmpty(port)) {
            if (isSsl) {
                props.put("mail.smtp.port", "465");
                props.put("mail.smtp.socketFactory.port", "465");
            } else {
                props.put("mail.smtp.port", "25");
                props.put("mail.smtp.socketFactory.port", "25");
            }
        } else {
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.socketFactory.port", port);
        }
        props.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(props, new PasswordAuthenticator(email, password));
        Transport transport = null;
        try {
            transport = session.getTransport();
            transport.connect(host, email, password);
            flag = transport.isConnected();
        } catch (NoSuchProviderException e) {
            log.error("" + e, e);
        } catch (MessagingException e) {
            log.error("" + e, e);
        } finally {
            if (transport != null && transport.isConnected()) {
                try {
                    transport.close();
                } catch (MessagingException e) {}
            }
        }
        return flag;
    }

    private static Store getStore(String protocol, String host, final String email, final String password, boolean isSsl, String port) {
        Store store = null;
        Properties props = SystemUtil.getProperties();
        try {
            if (isSsl) {
                Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
                MailSSLSocketFactory sf = new MailSSLSocketFactory();
                sf.setTrustAllHosts(true);
                props.setProperty("mail." + protocol + ".ssl.enable", "true");
                props.put("mail." + protocol + ".ssl.socketFactory", sf);
                props.setProperty("mail." + protocol + ".socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.setProperty("mail." + protocol + ".socketFactory.fallback", "false");
                props.setProperty("mail.store.protocol", protocol);
                props.setProperty("mail." + protocol + ".host", host);
                if (!StringUtil.isEmpty(port)) {
                    props.setProperty("mail." + protocol + ".port", port);
                    props.setProperty("mail." + protocol + ".socketFactory.port", port);
                }
            } else {
                props.setProperty("mail.store.protocol", protocol);
                props.setProperty("mail." + protocol + ".host", host);
                if (!StringUtil.isEmpty(port)) {
                    props.setProperty("mail." + protocol + ".port", port);
                }
            }
            props.setProperty("mail." + protocol + ".disabletop", "true");
            props.setProperty("mail." + protocol + ".useStartTLS", "true");
            Session session = Session.getInstance(props);
            store = session.getStore(protocol);
        } catch (Exception e) {
            log.error("" + e, e);
        }

        return store;
    }
}