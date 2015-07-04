package cn.limw.summer.mongo.driver.meta.base;

import java.sql.SQLException;

import cn.limw.summer.java.sql.wrapper.DatabaseMetaDataWrapper;

/**
 * @author li
 * @version 1 2014年3月12日上午9:28:02
 */
public class AbstractDatabaseMetaData extends DatabaseMetaDataWrapper {
    public boolean supportsResultSetType(int type) throws SQLException {
        return false;
    }

    public boolean supportsBatchUpdates() throws SQLException {
        return false;
    }

    public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
        return false;
    }

    public boolean supportsGetGeneratedKeys() throws SQLException {
        return false;
    }

    public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
        return true;
    }

    public String getDriverName() throws SQLException {
        return "li.mongo.driver.MongoDriver";
    }

    public int getJDBCMajorVersion() throws SQLException {
        return 1;
    }

    public String getDatabaseProductName() throws SQLException {
        return "mongodb";
    }

    public String getDatabaseProductVersion() throws SQLException {
        return "2.4.9";
    }

    public int getDatabaseMajorVersion() throws SQLException {
        return 24;
    }

    public int getDatabaseMinorVersion() throws SQLException {
        return 9;
    }

    public int getDriverMajorVersion() {
        return 0;
    }

    public int getDriverMinorVersion() {
        return 1;
    }

    public String getDriverVersion() throws SQLException {
        return "0.1";
    }
}