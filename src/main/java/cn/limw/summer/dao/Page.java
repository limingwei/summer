package cn.limw.summer.dao;

import java.io.Serializable;

/**
 * @author li
 * @version 1 (2014年6月24日 下午12:55:45)
 * @since Java7
 */
public class Page implements Serializable {
    private static final long serialVersionUID = -171559867947241546L;

    /**
     * 包含Integer.MAX_VALUE条记录的分页对象
     */
    public static final Page MAX = new Page(1, Integer.MAX_VALUE);

    /**
     * 包含一条记录的分页对象
     */
    public static final Page ONE = new Page(1, 1);

    /**
     * 单页记录条数
     */
    private Integer pageSize = 10;// 单页记录数

    /**
     * 页码
     */
    private Integer pageNumber = 1;// 当前页数

    /**
     * 记录数
     */
    private Integer recordCount = 0;// 总记录数

    private Integer from;

    /**
     * 是否查询总数返回
     */
    private Boolean doCount = true;

    /**
     * 默认构造函数
     */
    public Page() {}

    /**
     * 带有pageNumber,pageSize两个参数的构造函数
     */
    public Page(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    /**
     * pageNumber = 1; count = false;
     */
    public Page(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 返回当前页码
     */
    public Integer getPageNumber() {
        return this.pageNumber;
    }

    /**
     * 设置页码,跳转到第 pn 页
     */
    public Page setPageNumber(Integer pn) {
        this.pageNumber = (null == pn || pn < 1) ? 1 : pn;
        return this;
    }

    /**
     * 返回单页记录数
     */
    public Integer getPageSize() {
        return this.pageSize;
    }

    /**
     * 设置单页记录数,如果不想是用默认值 20 的话
     */
    public Page setPageSize(Integer pageSize) {
        this.pageSize = (null == pageSize || pageSize < 1) ? 1 : pageSize;
        return this;
    }

    /**
     * 设置总记录数,会根据recordCount同步设置pageCount
     */
    public Page setRecordCount(Integer recordCount) {
        this.recordCount = (null == recordCount || recordCount < 1) ? 0 : recordCount;
        return this;
    }

    /**
     * 返回总记录数
     */
    public Integer getRecordCount() {
        return this.recordCount;
    }

    /**
     * 得到总页数
     */
    public Integer getPageCount() {
        return (getRecordCount() / getPageSize()) + ((0 == getRecordCount() % getPageSize()) ? 0 : 1);
    }

    /**
     * 返回上一页pageNumber,经过判断,使不会小于 1
     */
    public Integer getPrevious() {
        return getPageNumber() > 1 ? getPageNumber() - 1 : 1;
    }

    /**
     * 返回下一页pageNumber,经过判断,使不会大于getPageCount
     */
    public Integer getNext() {
        return getPageNumber() + 1 < getPageCount() ? getPageNumber() + 1 : getPageCount();
    }

    /**
     * 是否第一页
     */
    public Boolean getIsFirst() {
        return getPageNumber() <= 1;
    }

    /**
     * 是否最后一页
     */
    public Boolean getIsLast() {
        return getPageNumber() >= getPageCount();
    }

    public Page setFrom(Integer from) {
        this.from = from;
        return this;
    }

    /**
     * 返回当前页的第一条记录的序列号
     */
    public Integer getFrom() {
        return null != from ? from : (getPageNumber() - 1) * getPageSize();
    }

    public Boolean getDoCount() {
        return doCount;
    }

    public void setDoCount(Boolean doCount) {
        this.doCount = doCount;
    }

    public String toString() {
        return super.toString() + " [PageCount=" + getPageCount() + ", PageNumber=" + getPageNumber() + ", PageSize=" + getPageSize() + ", RecordCount=" + getRecordCount() + ", doCount=" + getDoCount() + "]";
    }
}