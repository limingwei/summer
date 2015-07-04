package com.unblocked.support.datasource.combo;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import cn.limw.summer.java.sql.wrapper.ConnectionWrapper;

/**
 * ComboConnection
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年3月4日 下午5:13:56)
 */
public class ComboConnection extends ConnectionWrapper {
    private Set<Connection> connections = new HashSet<Connection>();

    public void add(Connection connection) {
        this.connections.add(connection);
    }

    public Connection getConnection() {
        return connections.iterator().next();
    }
}