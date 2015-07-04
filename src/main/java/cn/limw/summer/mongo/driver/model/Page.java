package cn.limw.summer.mongo.driver.model;

import cn.limw.summer.mongo.driver.MongoPreparedStatement;

import com.mongodb.DBCursor;

/**
 * Page 包装分页信息
 * 
 * @author li
 * @version 1 2014年3月12日上午9:22:43
 */
public class Page {
    private MongoPreparedStatement preparedStatement;

    private Object _limit;// Holder Or Integer

    private Object _offset;// Holder Or Integer

    /**
     * 传入的可能是 Holder或Integer
     */
    public void setLimit(Object limit) {
        _limit = limit;
    }

    /**
     * 传入的可能是 Holder或Integer
     */
    public void setOffset(Object offset) {
        _offset = offset;
    }

    public Page setPreparedStatement(MongoPreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
        return this;
    }

    /**
     * 如果是 Holder则解析之
     */
    public Integer getLimit() {
        return (Integer) ((_limit instanceof Holder) ? preparedStatement.getParameter(((Holder) _limit).getIndex()) : _limit);
    }

    /**
     * 如果是 Holder则解析之
     */
    public Integer getOffset() {
        return (Integer) ((_offset instanceof Holder) ? preparedStatement.getParameter(((Holder) _offset).getIndex()) : _offset);
    }

    /**
     * 将分页信息传给DBCursor
     */
    public void setTo(DBCursor result) {
        if (null != _limit) {
            result.limit(getLimit());
        }
        if (null != _offset) {
            result.skip(getOffset());
        }
    }

    public String toString() {
        return "LIMIT " + getLimit() + " OFFSET " + getOffset();
    }
}