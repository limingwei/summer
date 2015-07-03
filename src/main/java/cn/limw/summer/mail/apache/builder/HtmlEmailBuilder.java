package cn.limw.summer.mail.apache.builder;

import java.util.List;

import javax.mail.internet.InternetAddress;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import cn.limw.summer.util.Asserts;
import cn.limw.summer.util.ListUtil;

/**
 * @author li
 * @version 1 (2015年1月21日 上午10:56:21)
 * @since Java7
 */
public class HtmlEmailBuilder extends MultiPartEmailBuilder<HtmlEmail> {
    private HtmlEmail htmlEmail;

    public HtmlEmail getHtmlEmail() {
        if (null == htmlEmail) {
            htmlEmail = new HtmlEmail();
        }
        return htmlEmail;
    }

    public HtmlEmail build() {
        return getHtmlEmail();
    }

    public HtmlEmailBuilder setDebug(Boolean debug) {
        getHtmlEmail().setDebug(debug);
        return this;
    }

    public HtmlEmailBuilder setHostName(String hostName) {
        getHtmlEmail().setHostName(Asserts.noNull(hostName, "hostName不可以为空"));
        return this;
    }

    public HtmlEmailBuilder setAuthentication(String username, String password) {
        Asserts.noNull(username, "username属性不可以为空");
        Asserts.noNull(password, "password属性不可以为空");
        getHtmlEmail().setAuthentication(username, password);
        return this;
    }

    public HtmlEmailBuilder setCharset(String charset) {
        getHtmlEmail().setCharset(charset);
        return this;
    }

    public HtmlEmailBuilder setFrom(String from) {
        try {
            getHtmlEmail().setFrom(from);
            return this;
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }

    public HtmlEmailBuilder addTo(String[] emails) {
        try {
            getHtmlEmail().addTo(emails);
            return this;
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }

    public HtmlEmailBuilder addCc(String[] ccs) {
        try {
            if (null != ccs && ccs.length > 0) {
                getHtmlEmail().addCc(ccs);
            }
            return this;
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }

    public HtmlEmailBuilder addBcc(String[] bccs) {
        try {
            if (null != bccs && bccs.length > 0) {
                getHtmlEmail().addBcc(bccs);
            }
            return this;
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }

    public HtmlEmailBuilder setSubject(String subject) {
        getHtmlEmail().setSubject(subject);
        return this;
    }

    public HtmlEmailBuilder setMsg(String content) {
        try {
            getHtmlEmail().setMsg(content);
            return this;
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }

    public HtmlEmailBuilder setReplyTo(List<InternetAddress> replyTo) {
        try {
            if (!ListUtil.isEmpty(replyTo)) {
                getHtmlEmail().setReplyTo(replyTo);
            }
            return this;
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }
}