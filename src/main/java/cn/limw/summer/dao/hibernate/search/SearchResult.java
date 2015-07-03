package cn.limw.summer.dao.hibernate.search;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 搜索结果
 * @author li
 * @version 1 (2014年8月11日 上午9:10:58)
 * @since Java7
 */
public class SearchResult implements Serializable {
    private static final long serialVersionUID = 5757543958269544932L;

    private List<?> list;

    private Integer count;

    private Set<String> fields;

    private Set<String> fetch;

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Set<String> getFields() {
        return this.fields;
    }

    public void setFetch(Set<String> fetch) {
        this.fetch = fetch;
    }

    public void setFields(Set<String> fields) {
        this.fields = fields;
    }

    public Set<String> getFetch() {
        return this.fetch;
    }

    public String toString() {
        return super.toString() + ", list=" + getList() + ", count=" + getCount();
    }
}