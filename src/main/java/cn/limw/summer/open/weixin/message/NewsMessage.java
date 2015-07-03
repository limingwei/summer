package cn.limw.summer.open.weixin.message;

import java.util.List;

/**
 * NewsMessage
 * @author li (limingwei@mail.com)
 * @version 1 (2013年12月18日 下午5:31:53)
 */
public class NewsMessage extends Message {
    private static final long serialVersionUID = -3837296431645834217L;

    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public String getType() {
        return MessageType.NEWS;
    }

    public Integer getArticleCount() {
        return null == getArticles() ? 0 : getArticles().size();
    }

    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode()) + "[id=" + getId() + ", type=" + getType() + ", from=" + getFrom() + ", to=" + getTo() // 
                + ", createTime=" + getCreateTime() + ", articles" + getArticles() + "]";
    }
}