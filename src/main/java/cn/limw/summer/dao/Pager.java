package cn.limw.summer.dao;

import java.io.Serializable;

/**
 * @author li
 * @version 1 (2015年5月26日 下午5:05:59)
 * @since Java7
 */
public interface Pager extends Serializable {
    public Page getPage();
}