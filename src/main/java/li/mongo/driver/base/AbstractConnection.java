package li.mongo.driver.base;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import cn.limw.summer.java.sql.wrapper.ConnectionWrapper;

/**
 * @author li
 * @version 1 2014年3月12日上午9:28:06
 */
public class AbstractConnection extends ConnectionWrapper {
    public void clearWarnings() throws SQLException {}

    public void setAutoCommit(boolean autoCommit) throws SQLException {}

    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    /**
     * 自动提交
     */
    public boolean getAutoCommit() throws SQLException {
        return true;
    }

    public boolean isReadOnly() throws SQLException {
        return false;
    }

    /**
     * 无事务
     */
    public int getTransactionIsolation() throws SQLException {
        return Connection.TRANSACTION_NONE;
    }

    public int getHoldability() throws SQLException {
        return ResultSet.CLOSE_CURSORS_AT_COMMIT;
    }

    /**
     * @param resultSetType ResultSet.TYPE_FORWARD_ONLY
     * @param resultSetConcurrency ResultSet.CONCUR_READ_ONLY
     */
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return createStatement();
    }
}