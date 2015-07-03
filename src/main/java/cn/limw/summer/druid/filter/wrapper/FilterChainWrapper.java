package cn.limw.summer.druid.filter.wrapper;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.Ref;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Wrapper;
import java.util.Calendar;
import java.util.Map;
import java.util.Properties;

import cn.limw.summer.util.Asserts;

import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.proxy.jdbc.CallableStatementProxy;
import com.alibaba.druid.proxy.jdbc.ClobProxy;
import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import com.alibaba.druid.proxy.jdbc.DataSourceProxy;
import com.alibaba.druid.proxy.jdbc.PreparedStatementProxy;
import com.alibaba.druid.proxy.jdbc.ResultSetMetaDataProxy;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;

/**
 * @author li
 * @version 1 (2014年11月17日 下午4:09:43)
 * @since Java7
 * @see com.alibaba.druid.filter.FilterAdapter
 */
public class FilterChainWrapper implements FilterChain {
    private FilterChain filterChain;

    public FilterChainWrapper() {};

    public FilterChainWrapper(FilterChain filterChain) {
        setFilterChain(filterChain);
    }

    public FilterChain getFilterChain() {
        return Asserts.noNull(filterChain);
    }

    public void setFilterChain(FilterChain filterChain) {
        this.filterChain = filterChain;
    }

    public DataSourceProxy getDataSource() {
        return getFilterChain().getDataSource();
    }

    public int getFilterSize() {
        return getFilterChain().getFilterSize();
    }

    public FilterChain cloneChain() {
        return getFilterChain().cloneChain();
    }

    public <T> T unwrap(Wrapper wrapper, Class<T> iface) throws SQLException {
        return getFilterChain().unwrap(wrapper, iface);
    }

    public boolean isWrapperFor(Wrapper wrapper, Class<?> iface) throws SQLException {
        return getFilterChain().isWrapperFor(wrapper, iface);
    }

    public ConnectionProxy connection_connect(Properties info) throws SQLException {
        return getFilterChain().connection_connect(info);
    }

    public StatementProxy connection_createStatement(ConnectionProxy connection) throws SQLException {
        return getFilterChain().connection_createStatement(connection);
    }

    public PreparedStatementProxy connection_prepareStatement(ConnectionProxy connection, String sql) throws SQLException {
        return getFilterChain().connection_prepareStatement(connection, sql);
    }

    public CallableStatementProxy connection_prepareCall(ConnectionProxy connection, String sql) throws SQLException {
        return getFilterChain().connection_prepareCall(connection, sql);
    }

    public String connection_nativeSQL(ConnectionProxy connection, String sql) throws SQLException {
        return getFilterChain().connection_nativeSQL(connection, sql);
    }

    public void connection_setAutoCommit(ConnectionProxy connection, boolean autoCommit) throws SQLException {
        getFilterChain().connection_setAutoCommit(connection, autoCommit);
    }

    public boolean connection_getAutoCommit(ConnectionProxy connection) throws SQLException {
        return getFilterChain().connection_getAutoCommit(connection);
    }

    public void connection_commit(ConnectionProxy connection) throws SQLException {
        getFilterChain().connection_commit(connection);
    }

    public void connection_rollback(ConnectionProxy connection) throws SQLException {
        getFilterChain().connection_rollback(connection);
    }

    public void connection_close(ConnectionProxy connection) throws SQLException {
        getFilterChain().connection_close(connection);
    }

    public boolean connection_isClosed(ConnectionProxy connection) throws SQLException {
        return getFilterChain().connection_isClosed(connection);
    }

    public DatabaseMetaData connection_getMetaData(ConnectionProxy connection) throws SQLException {
        return getFilterChain().connection_getMetaData(connection);
    }

    public void connection_setReadOnly(ConnectionProxy connection, boolean readOnly) throws SQLException {
        getFilterChain().connection_setReadOnly(connection, readOnly);
    }

    public boolean connection_isReadOnly(ConnectionProxy connection) throws SQLException {
        return getFilterChain().connection_isReadOnly(connection);
    }

    public void connection_setCatalog(ConnectionProxy connection, String catalog) throws SQLException {
        getFilterChain().connection_setCatalog(connection, catalog);
    }

    public String connection_getCatalog(ConnectionProxy connection) throws SQLException {
        return getFilterChain().connection_getCatalog(connection);
    }

    public void connection_setTransactionIsolation(ConnectionProxy connection, int level) throws SQLException {
        getFilterChain().connection_setTransactionIsolation(connection, level);
    }

    public int connection_getTransactionIsolation(ConnectionProxy connection) throws SQLException {
        return getFilterChain().connection_getTransactionIsolation(connection);
    }

    public SQLWarning connection_getWarnings(ConnectionProxy connection) throws SQLException {
        return getFilterChain().connection_getWarnings(connection);
    }

    public void connection_clearWarnings(ConnectionProxy connection) throws SQLException {
        getFilterChain().connection_clearWarnings(connection);
    }

    public StatementProxy connection_createStatement(ConnectionProxy connection, int resultSetType, int resultSetConcurrency) throws SQLException {
        return getFilterChain().connection_createStatement(connection, resultSetType, resultSetConcurrency);
    }

    public PreparedStatementProxy connection_prepareStatement(ConnectionProxy connection, String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return getFilterChain().connection_prepareStatement(connection, sql, resultSetType, resultSetConcurrency);
    }

    public CallableStatementProxy connection_prepareCall(ConnectionProxy connection, String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return getFilterChain().connection_prepareCall(connection, sql, resultSetType, resultSetConcurrency);
    }

    public Map<String, Class<?>> connection_getTypeMap(ConnectionProxy connection) throws SQLException {
        return getFilterChain().connection_getTypeMap(connection);
    }

    public void connection_setTypeMap(ConnectionProxy connection, Map<String, Class<?>> map) throws SQLException {
        getFilterChain().connection_setTypeMap(connection, map);
    }

    public void connection_setHoldability(ConnectionProxy connection, int holdability) throws SQLException {
        getFilterChain().connection_setHoldability(connection, holdability);
    }

    public int connection_getHoldability(ConnectionProxy connection) throws SQLException {
        return getFilterChain().connection_getHoldability(connection);
    }

    public Savepoint connection_setSavepoint(ConnectionProxy connection) throws SQLException {
        return getFilterChain().connection_setSavepoint(connection);
    }

    public Savepoint connection_setSavepoint(ConnectionProxy connection, String name) throws SQLException {
        return getFilterChain().connection_setSavepoint(connection, name);
    }

    public void connection_rollback(ConnectionProxy connection, Savepoint savepoint) throws SQLException {
        getFilterChain().connection_rollback(connection, savepoint);
    }

    public void connection_releaseSavepoint(ConnectionProxy connection, Savepoint savepoint) throws SQLException {
        getFilterChain().connection_releaseSavepoint(connection, savepoint);
    }

    public StatementProxy connection_createStatement(ConnectionProxy connection, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return getFilterChain().connection_createStatement(connection, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    public PreparedStatementProxy connection_prepareStatement(ConnectionProxy connection, String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return getFilterChain().connection_prepareStatement(connection, sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    public CallableStatementProxy connection_prepareCall(ConnectionProxy connection, String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return getFilterChain().connection_prepareCall(connection, sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    public PreparedStatementProxy connection_prepareStatement(ConnectionProxy connection, String sql, int autoGeneratedKeys) throws SQLException {
        return getFilterChain().connection_prepareStatement(connection, sql, autoGeneratedKeys);
    }

    public PreparedStatementProxy connection_prepareStatement(ConnectionProxy connection, String sql, int[] columnIndexes) throws SQLException {
        return getFilterChain().connection_prepareStatement(connection, sql, columnIndexes);
    }

    public PreparedStatementProxy connection_prepareStatement(ConnectionProxy connection, String sql, String[] columnNames) throws SQLException {
        return getFilterChain().connection_prepareStatement(connection, sql, columnNames);
    }

    public Clob connection_createClob(ConnectionProxy connection) throws SQLException {
        return getFilterChain().connection_createClob(connection);
    }

    public Blob connection_createBlob(ConnectionProxy connection) throws SQLException {
        return getFilterChain().connection_createBlob(connection);
    }

    public NClob connection_createNClob(ConnectionProxy connection) throws SQLException {
        return getFilterChain().connection_createNClob(connection);
    }

    public SQLXML connection_createSQLXML(ConnectionProxy connection) throws SQLException {
        return getFilterChain().connection_createSQLXML(connection);
    }

    public boolean connection_isValid(ConnectionProxy connection, int timeout) throws SQLException {
        return getFilterChain().connection_isValid(connection, timeout);
    }

    public void connection_setClientInfo(ConnectionProxy connection, String name, String value) throws SQLClientInfoException {
        getFilterChain().connection_setClientInfo(connection, name, value);
    }

    public void connection_setClientInfo(ConnectionProxy connection, Properties properties) throws SQLClientInfoException {
        getFilterChain().connection_setClientInfo(connection, properties);
    }

    public String connection_getClientInfo(ConnectionProxy connection, String name) throws SQLException {
        return getFilterChain().connection_getClientInfo(connection, name);
    }

    public Properties connection_getClientInfo(ConnectionProxy connection) throws SQLException {
        return getFilterChain().connection_getClientInfo(connection);
    }

    public Array connection_createArrayOf(ConnectionProxy connection, String typeName, Object[] elements) throws SQLException {
        return getFilterChain().connection_createArrayOf(connection, typeName, elements);
    }

    public Struct connection_createStruct(ConnectionProxy connection, String typeName, Object[] attributes) throws SQLException {
        return getFilterChain().connection_createStruct(connection, typeName, attributes);
    }

    public boolean resultSet_next(ResultSetProxy resultSet) throws SQLException {
        return getFilterChain().resultSet_next(resultSet);
    }

    public void resultSet_close(ResultSetProxy resultSet) throws SQLException {
        getFilterChain().resultSet_close(resultSet);
    }

    public boolean resultSet_wasNull(ResultSetProxy resultSet) throws SQLException {
        return getFilterChain().resultSet_wasNull(resultSet);
    }

    public String resultSet_getString(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getString(resultSet, columnIndex);
    }

    public boolean resultSet_getBoolean(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getBoolean(resultSet, columnIndex);
    }

    public byte resultSet_getByte(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getByte(resultSet, columnIndex);
    }

    public short resultSet_getShort(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getShort(resultSet, columnIndex);
    }

    public int resultSet_getInt(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getInt(resultSet, columnIndex);
    }

    public long resultSet_getLong(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getLong(resultSet, columnIndex);
    }

    public float resultSet_getFloat(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getFloat(resultSet, columnIndex);
    }

    public double resultSet_getDouble(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getDouble(resultSet, columnIndex);
    }

    public BigDecimal resultSet_getBigDecimal(ResultSetProxy resultSet, int columnIndex, int scale) throws SQLException {
        return getFilterChain().resultSet_getBigDecimal(resultSet, columnIndex, scale);
    }

    public byte[] resultSet_getBytes(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getBytes(resultSet, columnIndex);
    }

    public Date resultSet_getDate(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getDate(resultSet, columnIndex);
    }

    public Time resultSet_getTime(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getTime(resultSet, columnIndex);
    }

    public Timestamp resultSet_getTimestamp(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getTimestamp(resultSet, columnIndex);
    }

    public InputStream resultSet_getAsciiStream(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getAsciiStream(resultSet, columnIndex);
    }

    public InputStream resultSet_getUnicodeStream(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getUnicodeStream(resultSet, columnIndex);
    }

    public InputStream resultSet_getBinaryStream(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getBinaryStream(resultSet, columnIndex);
    }

    public String resultSet_getString(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getString(resultSet, columnLabel);
    }

    public boolean resultSet_getBoolean(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getBoolean(resultSet, columnLabel);
    }

    public byte resultSet_getByte(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getByte(resultSet, columnLabel);
    }

    public short resultSet_getShort(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getShort(resultSet, columnLabel);
    }

    public int resultSet_getInt(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getInt(resultSet, columnLabel);
    }

    public long resultSet_getLong(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getLong(resultSet, columnLabel);
    }

    public float resultSet_getFloat(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getFloat(resultSet, columnLabel);
    }

    public double resultSet_getDouble(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getDouble(resultSet, columnLabel);
    }

    public BigDecimal resultSet_getBigDecimal(ResultSetProxy resultSet, String columnLabel, int scale) throws SQLException {
        return getFilterChain().resultSet_getBigDecimal(resultSet, columnLabel, scale);
    }

    public byte[] resultSet_getBytes(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getBytes(resultSet, columnLabel);
    }

    public Date resultSet_getDate(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getDate(resultSet, columnLabel);
    }

    public Time resultSet_getTime(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getTime(resultSet, columnLabel);
    }

    public Timestamp resultSet_getTimestamp(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getTimestamp(resultSet, columnLabel);
    }

    public InputStream resultSet_getAsciiStream(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getAsciiStream(resultSet, columnLabel);
    }

    public InputStream resultSet_getUnicodeStream(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getUnicodeStream(resultSet, columnLabel);
    }

    public InputStream resultSet_getBinaryStream(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getBinaryStream(resultSet, columnLabel);
    }

    public SQLWarning resultSet_getWarnings(ResultSetProxy resultSet) throws SQLException {
        return getFilterChain().resultSet_getWarnings(resultSet);
    }

    public void resultSet_clearWarnings(ResultSetProxy resultSet) throws SQLException {
        getFilterChain().resultSet_clearWarnings(resultSet);
    }

    public String resultSet_getCursorName(ResultSetProxy resultSet) throws SQLException {
        return getFilterChain().resultSet_getCursorName(resultSet);
    }

    public ResultSetMetaData resultSet_getMetaData(ResultSetProxy resultSet) throws SQLException {
        return getFilterChain().resultSet_getMetaData(resultSet);
    }

    public Object resultSet_getObject(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getObject(resultSet, columnIndex);
    }

    public Object resultSet_getObject(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getObject(resultSet, columnLabel);
    }

    public int resultSet_findColumn(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_findColumn(resultSet, columnLabel);
    }

    public Reader resultSet_getCharacterStream(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getCharacterStream(resultSet, columnIndex);
    }

    public Reader resultSet_getCharacterStream(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getCharacterStream(resultSet, columnLabel);
    }

    public BigDecimal resultSet_getBigDecimal(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getBigDecimal(resultSet, columnIndex);
    }

    public BigDecimal resultSet_getBigDecimal(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getBigDecimal(resultSet, columnLabel);
    }

    public boolean resultSet_isBeforeFirst(ResultSetProxy resultSet) throws SQLException {
        return getFilterChain().resultSet_isBeforeFirst(resultSet);
    }

    public boolean resultSet_isAfterLast(ResultSetProxy resultSet) throws SQLException {
        return getFilterChain().resultSet_isAfterLast(resultSet);
    }

    public boolean resultSet_isFirst(ResultSetProxy resultSet) throws SQLException {
        return getFilterChain().resultSet_isFirst(resultSet);
    }

    public boolean resultSet_isLast(ResultSetProxy resultSet) throws SQLException {
        return getFilterChain().resultSet_isLast(resultSet);
    }

    public void resultSet_beforeFirst(ResultSetProxy resultSet) throws SQLException {
        getFilterChain().resultSet_beforeFirst(resultSet);
    }

    public void resultSet_afterLast(ResultSetProxy resultSet) throws SQLException {
        getFilterChain().resultSet_afterLast(resultSet);
    }

    public boolean resultSet_first(ResultSetProxy resultSet) throws SQLException {
        return getFilterChain().resultSet_first(resultSet);
    }

    public boolean resultSet_last(ResultSetProxy resultSet) throws SQLException {
        return getFilterChain().resultSet_last(resultSet);
    }

    public int resultSet_getRow(ResultSetProxy resultSet) throws SQLException {
        return getFilterChain().resultSet_getRow(resultSet);
    }

    public boolean resultSet_absolute(ResultSetProxy resultSet, int row) throws SQLException {
        return getFilterChain().resultSet_absolute(resultSet, row);
    }

    public boolean resultSet_relative(ResultSetProxy resultSet, int rows) throws SQLException {
        return getFilterChain().resultSet_relative(resultSet, rows);
    }

    public boolean resultSet_previous(ResultSetProxy resultSet) throws SQLException {
        return getFilterChain().resultSet_previous(resultSet);
    }

    public void resultSet_setFetchDirection(ResultSetProxy resultSet, int direction) throws SQLException {
        getFilterChain().resultSet_setFetchDirection(resultSet, direction);
    }

    public int resultSet_getFetchDirection(ResultSetProxy resultSet) throws SQLException {
        return getFilterChain().resultSet_getFetchDirection(resultSet);
    }

    public void resultSet_setFetchSize(ResultSetProxy resultSet, int rows) throws SQLException {
        getFilterChain().resultSet_setFetchSize(resultSet, rows);
    }

    public int resultSet_getFetchSize(ResultSetProxy resultSet) throws SQLException {
        return getFilterChain().resultSet_getFetchSize(resultSet);
    }

    public int resultSet_getType(ResultSetProxy resultSet) throws SQLException {
        return getFilterChain().resultSet_getType(resultSet);
    }

    public int resultSet_getConcurrency(ResultSetProxy resultSet) throws SQLException {
        return getFilterChain().resultSet_getConcurrency(resultSet);
    }

    public boolean resultSet_rowUpdated(ResultSetProxy resultSet) throws SQLException {
        return getFilterChain().resultSet_rowUpdated(resultSet);
    }

    public boolean resultSet_rowInserted(ResultSetProxy resultSet) throws SQLException {
        return getFilterChain().resultSet_rowInserted(resultSet);
    }

    public boolean resultSet_rowDeleted(ResultSetProxy resultSet) throws SQLException {
        return getFilterChain().resultSet_rowDeleted(resultSet);
    }

    public void resultSet_updateNull(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        getFilterChain().resultSet_updateNull(resultSet, columnIndex);
    }

    public void resultSet_updateBoolean(ResultSetProxy resultSet, int columnIndex, boolean x) throws SQLException {
        getFilterChain().resultSet_updateBoolean(resultSet, columnIndex, x);
    }

    public void resultSet_updateByte(ResultSetProxy resultSet, int columnIndex, byte x) throws SQLException {
        getFilterChain().resultSet_updateByte(resultSet, columnIndex, x);
    }

    public void resultSet_updateShort(ResultSetProxy resultSet, int columnIndex, short x) throws SQLException {
        getFilterChain().resultSet_updateShort(resultSet, columnIndex, x);
    }

    public void resultSet_updateInt(ResultSetProxy resultSet, int columnIndex, int x) throws SQLException {
        getFilterChain().resultSet_updateInt(resultSet, columnIndex, x);
    }

    public void resultSet_updateLong(ResultSetProxy resultSet, int columnIndex, long x) throws SQLException {
        getFilterChain().resultSet_updateLong(resultSet, columnIndex, x);
    }

    public void resultSet_updateFloat(ResultSetProxy resultSet, int columnIndex, float x) throws SQLException {
        getFilterChain().resultSet_updateFloat(resultSet, columnIndex, x);
    }

    public void resultSet_updateDouble(ResultSetProxy resultSet, int columnIndex, double x) throws SQLException {
        getFilterChain().resultSet_updateDouble(resultSet, columnIndex, x);
    }

    public void resultSet_updateBigDecimal(ResultSetProxy resultSet, int columnIndex, BigDecimal x) throws SQLException {
        getFilterChain().resultSet_updateBigDecimal(resultSet, columnIndex, x);
    }

    public void resultSet_updateString(ResultSetProxy resultSet, int columnIndex, String x) throws SQLException {
        getFilterChain().resultSet_updateString(resultSet, columnIndex, x);
    }

    public void resultSet_updateBytes(ResultSetProxy resultSet, int columnIndex, byte[] x) throws SQLException {
        getFilterChain().resultSet_updateBytes(resultSet, columnIndex, x);
    }

    public void resultSet_updateDate(ResultSetProxy resultSet, int columnIndex, Date x) throws SQLException {
        getFilterChain().resultSet_updateDate(resultSet, columnIndex, x);
    }

    public void resultSet_updateTime(ResultSetProxy resultSet, int columnIndex, Time x) throws SQLException {
        getFilterChain().resultSet_updateTime(resultSet, columnIndex, x);
    }

    public void resultSet_updateTimestamp(ResultSetProxy resultSet, int columnIndex, Timestamp x) throws SQLException {
        getFilterChain().resultSet_updateTimestamp(resultSet, columnIndex, x);
    }

    public void resultSet_updateAsciiStream(ResultSetProxy resultSet, int columnIndex, InputStream x, int length) throws SQLException {
        getFilterChain().resultSet_updateAsciiStream(resultSet, columnIndex, x, length);
    }

    public void resultSet_updateBinaryStream(ResultSetProxy resultSet, int columnIndex, InputStream x, int length) throws SQLException {
        getFilterChain().resultSet_updateBinaryStream(resultSet, columnIndex, x, length);
    }

    public void resultSet_updateCharacterStream(ResultSetProxy resultSet, int columnIndex, Reader x, int length) throws SQLException {
        getFilterChain().resultSet_updateCharacterStream(resultSet, columnIndex, x, length);
    }

    public void resultSet_updateObject(ResultSetProxy resultSet, int columnIndex, Object x, int scaleOrLength) throws SQLException {
        getFilterChain().resultSet_updateObject(resultSet, columnIndex, x, scaleOrLength);
    }

    public void resultSet_updateObject(ResultSetProxy resultSet, int columnIndex, Object x) throws SQLException {
        getFilterChain().resultSet_updateObject(resultSet, columnIndex, x);
    }

    public void resultSet_updateNull(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        getFilterChain().resultSet_updateNull(resultSet, columnLabel);
    }

    public void resultSet_updateBoolean(ResultSetProxy resultSet, String columnLabel, boolean x) throws SQLException {
        getFilterChain().resultSet_updateBoolean(resultSet, columnLabel, x);
    }

    public void resultSet_updateByte(ResultSetProxy resultSet, String columnLabel, byte x) throws SQLException {
        getFilterChain().resultSet_updateByte(resultSet, columnLabel, x);
    }

    public void resultSet_updateShort(ResultSetProxy resultSet, String columnLabel, short x) throws SQLException {
        getFilterChain().resultSet_updateShort(resultSet, columnLabel, x);
    }

    public void resultSet_updateInt(ResultSetProxy resultSet, String columnLabel, int x) throws SQLException {
        getFilterChain().resultSet_updateInt(resultSet, columnLabel, x);
    }

    public void resultSet_updateLong(ResultSetProxy resultSet, String columnLabel, long x) throws SQLException {
        getFilterChain().resultSet_updateLong(resultSet, columnLabel, x);
    }

    public void resultSet_updateFloat(ResultSetProxy resultSet, String columnLabel, float x) throws SQLException {
        getFilterChain().resultSet_updateFloat(resultSet, columnLabel, x);
    }

    public void resultSet_updateDouble(ResultSetProxy resultSet, String columnLabel, double x) throws SQLException {
        getFilterChain().resultSet_updateDouble(resultSet, columnLabel, x);
    }

    public void resultSet_updateBigDecimal(ResultSetProxy resultSet, String columnLabel, BigDecimal x) throws SQLException {
        getFilterChain().resultSet_updateBigDecimal(resultSet, columnLabel, x);
    }

    public void resultSet_updateString(ResultSetProxy resultSet, String columnLabel, String x) throws SQLException {
        getFilterChain().resultSet_updateString(resultSet, columnLabel, x);
    }

    public void resultSet_updateBytes(ResultSetProxy resultSet, String columnLabel, byte[] x) throws SQLException {
        getFilterChain().resultSet_updateBytes(resultSet, columnLabel, x);
    }

    public void resultSet_updateDate(ResultSetProxy resultSet, String columnLabel, Date x) throws SQLException {
        getFilterChain().resultSet_updateDate(resultSet, columnLabel, x);
    }

    public void resultSet_updateTime(ResultSetProxy resultSet, String columnLabel, Time x) throws SQLException {
        getFilterChain().resultSet_updateTime(resultSet, columnLabel, x);
    }

    public void resultSet_updateTimestamp(ResultSetProxy resultSet, String columnLabel, Timestamp x) throws SQLException {
        getFilterChain().resultSet_updateTimestamp(resultSet, columnLabel, x);
    }

    public void resultSet_updateAsciiStream(ResultSetProxy resultSet, String columnLabel, InputStream x, int length) throws SQLException {
        getFilterChain().resultSet_updateAsciiStream(resultSet, columnLabel, x, length);
    }

    public void resultSet_updateBinaryStream(ResultSetProxy resultSet, String columnLabel, InputStream x, int length) throws SQLException {
        getFilterChain().resultSet_updateBinaryStream(resultSet, columnLabel, x, length);
    }

    public void resultSet_updateCharacterStream(ResultSetProxy resultSet, String columnLabel, Reader reader, int length) throws SQLException {
        getFilterChain().resultSet_updateCharacterStream(resultSet, columnLabel, reader, length);
    }

    public void resultSet_updateObject(ResultSetProxy resultSet, String columnLabel, Object x, int scaleOrLength) throws SQLException {
        getFilterChain().resultSet_updateObject(resultSet, columnLabel, x, scaleOrLength);
    }

    public void resultSet_updateObject(ResultSetProxy resultSet, String columnLabel, Object x) throws SQLException {
        getFilterChain().resultSet_updateObject(resultSet, columnLabel, x);
    }

    public void resultSet_insertRow(ResultSetProxy resultSet) throws SQLException {
        getFilterChain().resultSet_insertRow(resultSet);
    }

    public void resultSet_updateRow(ResultSetProxy resultSet) throws SQLException {
        getFilterChain().resultSet_updateRow(resultSet);
    }

    public void resultSet_deleteRow(ResultSetProxy resultSet) throws SQLException {
        getFilterChain().resultSet_deleteRow(resultSet);
    }

    public void resultSet_refreshRow(ResultSetProxy resultSet) throws SQLException {
        getFilterChain().resultSet_refreshRow(resultSet);
    }

    public void resultSet_cancelRowUpdates(ResultSetProxy resultSet) throws SQLException {
        getFilterChain().resultSet_cancelRowUpdates(resultSet);
    }

    public void resultSet_moveToInsertRow(ResultSetProxy resultSet) throws SQLException {
        getFilterChain().resultSet_moveToInsertRow(resultSet);
    }

    public void resultSet_moveToCurrentRow(ResultSetProxy resultSet) throws SQLException {
        getFilterChain().resultSet_moveToCurrentRow(resultSet);
    }

    public Statement resultSet_getStatement(ResultSetProxy resultSet) throws SQLException {
        return getFilterChain().resultSet_getStatement(resultSet);
    }

    public Object resultSet_getObject(ResultSetProxy resultSet, int columnIndex, Map<String, Class<?>> map) throws SQLException {
        return getFilterChain().resultSet_getObject(resultSet, columnIndex, map);
    }

    public Ref resultSet_getRef(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getRef(resultSet, columnIndex);
    }

    public Blob resultSet_getBlob(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getBlob(resultSet, columnIndex);
    }

    public Clob resultSet_getClob(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getClob(resultSet, columnIndex);
    }

    public Array resultSet_getArray(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getArray(resultSet, columnIndex);
    }

    public Object resultSet_getObject(ResultSetProxy resultSet, String columnLabel, Map<String, Class<?>> map) throws SQLException {
        return getFilterChain().resultSet_getObject(resultSet, columnLabel, map);
    }

    public Ref resultSet_getRef(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getRef(resultSet, columnLabel);
    }

    public Blob resultSet_getBlob(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getBlob(resultSet, columnLabel);
    }

    public Clob resultSet_getClob(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getClob(resultSet, columnLabel);
    }

    public Array resultSet_getArray(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getArray(resultSet, columnLabel);
    }

    public Date resultSet_getDate(ResultSetProxy resultSet, int columnIndex, Calendar cal) throws SQLException {
        return getFilterChain().resultSet_getDate(resultSet, columnIndex, cal);
    }

    public Date resultSet_getDate(ResultSetProxy resultSet, String columnLabel, Calendar cal) throws SQLException {
        return getFilterChain().resultSet_getDate(resultSet, columnLabel, cal);
    }

    public Time resultSet_getTime(ResultSetProxy resultSet, int columnIndex, Calendar cal) throws SQLException {
        return getFilterChain().resultSet_getTime(resultSet, columnIndex, cal);
    }

    public Time resultSet_getTime(ResultSetProxy resultSet, String columnLabel, Calendar cal) throws SQLException {
        return getFilterChain().resultSet_getTime(resultSet, columnLabel, cal);
    }

    public Timestamp resultSet_getTimestamp(ResultSetProxy resultSet, int columnIndex, Calendar cal) throws SQLException {
        return getFilterChain().resultSet_getTimestamp(resultSet, columnIndex, cal);
    }

    public Timestamp resultSet_getTimestamp(ResultSetProxy resultSet, String columnLabel, Calendar cal) throws SQLException {
        return getFilterChain().resultSet_getTimestamp(resultSet, columnLabel, cal);
    }

    public URL resultSet_getURL(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getURL(resultSet, columnIndex);
    }

    public URL resultSet_getURL(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getURL(resultSet, columnLabel);
    }

    public void resultSet_updateRef(ResultSetProxy resultSet, int columnIndex, Ref x) throws SQLException {
        getFilterChain().resultSet_updateRef(resultSet, columnIndex, x);
    }

    public void resultSet_updateRef(ResultSetProxy resultSet, String columnLabel, Ref x) throws SQLException {
        getFilterChain().resultSet_updateRef(resultSet, columnLabel, x);
    }

    public void resultSet_updateBlob(ResultSetProxy resultSet, int columnIndex, Blob x) throws SQLException {
        getFilterChain().resultSet_updateBlob(resultSet, columnIndex, x);
    }

    public void resultSet_updateBlob(ResultSetProxy resultSet, String columnLabel, Blob x) throws SQLException {
        getFilterChain().resultSet_updateBlob(resultSet, columnLabel, x);
    }

    public void resultSet_updateClob(ResultSetProxy resultSet, int columnIndex, Clob x) throws SQLException {
        getFilterChain().resultSet_updateClob(resultSet, columnIndex, x);
    }

    public void resultSet_updateClob(ResultSetProxy resultSet, String columnLabel, Clob x) throws SQLException {
        getFilterChain().resultSet_updateClob(resultSet, columnLabel, x);
    }

    public void resultSet_updateArray(ResultSetProxy resultSet, int columnIndex, Array x) throws SQLException {
        getFilterChain().resultSet_updateArray(resultSet, columnIndex, x);
    }

    public void resultSet_updateArray(ResultSetProxy resultSet, String columnLabel, Array x) throws SQLException {
        getFilterChain().resultSet_updateArray(resultSet, columnLabel, x);
    }

    public RowId resultSet_getRowId(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getRowId(resultSet, columnIndex);
    }

    public RowId resultSet_getRowId(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getRowId(resultSet, columnLabel);
    }

    public void resultSet_updateRowId(ResultSetProxy resultSet, int columnIndex, RowId x) throws SQLException {
        getFilterChain().resultSet_updateRowId(resultSet, columnIndex, x);
    }

    public void resultSet_updateRowId(ResultSetProxy resultSet, String columnLabel, RowId x) throws SQLException {
        getFilterChain().resultSet_updateRowId(resultSet, columnLabel, x);
    }

    public int resultSet_getHoldability(ResultSetProxy resultSet) throws SQLException {
        return getFilterChain().resultSet_getHoldability(resultSet);
    }

    public boolean resultSet_isClosed(ResultSetProxy resultSet) throws SQLException {
        return getFilterChain().resultSet_isClosed(resultSet);
    }

    public void resultSet_updateNString(ResultSetProxy resultSet, int columnIndex, String nString) throws SQLException {
        getFilterChain().resultSet_updateNString(resultSet, columnIndex, nString);
    }

    public void resultSet_updateNString(ResultSetProxy resultSet, String columnLabel, String nString) throws SQLException {
        getFilterChain().resultSet_updateNString(resultSet, columnLabel, nString);
    }

    public void resultSet_updateNClob(ResultSetProxy resultSet, int columnIndex, NClob nClob) throws SQLException {
        getFilterChain().resultSet_updateNClob(resultSet, columnIndex, nClob);
    }

    public void resultSet_updateNClob(ResultSetProxy resultSet, String columnLabel, NClob nClob) throws SQLException {
        getFilterChain().resultSet_updateNClob(resultSet, columnLabel, nClob);
    }

    public NClob resultSet_getNClob(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getNClob(resultSet, columnIndex);
    }

    public NClob resultSet_getNClob(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getNClob(resultSet, columnLabel);
    }

    public SQLXML resultSet_getSQLXML(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getSQLXML(resultSet, columnIndex);
    }

    public SQLXML resultSet_getSQLXML(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getSQLXML(resultSet, columnLabel);
    }

    public void resultSet_updateSQLXML(ResultSetProxy resultSet, int columnIndex, SQLXML xmlObject) throws SQLException {
        getFilterChain().resultSet_updateSQLXML(resultSet, columnIndex, xmlObject);
    }

    public void resultSet_updateSQLXML(ResultSetProxy resultSet, String columnLabel, SQLXML xmlObject) throws SQLException {
        getFilterChain().resultSet_updateSQLXML(resultSet, columnLabel, xmlObject);
    }

    public String resultSet_getNString(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getNString(resultSet, columnIndex);
    }

    public String resultSet_getNString(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getNString(resultSet, columnLabel);
    }

    public Reader resultSet_getNCharacterStream(ResultSetProxy resultSet, int columnIndex) throws SQLException {
        return getFilterChain().resultSet_getNCharacterStream(resultSet, columnIndex);
    }

    public Reader resultSet_getNCharacterStream(ResultSetProxy resultSet, String columnLabel) throws SQLException {
        return getFilterChain().resultSet_getNCharacterStream(resultSet, columnLabel);
    }

    public void resultSet_updateNCharacterStream(ResultSetProxy resultSet, int columnIndex, Reader x, long length) throws SQLException {
        getFilterChain().resultSet_updateNCharacterStream(resultSet, columnIndex, x, length);
    }

    public void resultSet_updateNCharacterStream(ResultSetProxy resultSet, String columnLabel, Reader reader, long length) throws SQLException {
        getFilterChain().resultSet_updateNCharacterStream(resultSet, columnLabel, reader, length);
    }

    public void resultSet_updateAsciiStream(ResultSetProxy resultSet, int columnIndex, InputStream x, long length) throws SQLException {
        getFilterChain().resultSet_updateAsciiStream(resultSet, columnIndex, x, length);
    }

    public void resultSet_updateBinaryStream(ResultSetProxy resultSet, int columnIndex, InputStream x, long length) throws SQLException {
        getFilterChain().resultSet_updateBinaryStream(resultSet, columnIndex, x, length);
    }

    public void resultSet_updateCharacterStream(ResultSetProxy resultSet, int columnIndex, Reader x, long length) throws SQLException {
        getFilterChain().resultSet_updateCharacterStream(resultSet, columnIndex, x, length);
    }

    public void resultSet_updateAsciiStream(ResultSetProxy resultSet, String columnLabel, InputStream x, long length) throws SQLException {
        getFilterChain().resultSet_updateAsciiStream(resultSet, columnLabel, x, length);
    }

    public void resultSet_updateBinaryStream(ResultSetProxy resultSet, String columnLabel, InputStream x, long length) throws SQLException {
        getFilterChain().resultSet_updateBinaryStream(resultSet, columnLabel, x, length);
    }

    public void resultSet_updateCharacterStream(ResultSetProxy resultSet, String columnLabel, Reader reader, long length) throws SQLException {
        getFilterChain().resultSet_updateCharacterStream(resultSet, columnLabel, reader, length);
    }

    public void resultSet_updateBlob(ResultSetProxy resultSet, int columnIndex, InputStream inputStream, long length) throws SQLException {
        getFilterChain().resultSet_updateBlob(resultSet, columnIndex, inputStream, length);
    }

    public void resultSet_updateBlob(ResultSetProxy resultSet, String columnLabel, InputStream inputStream, long length) throws SQLException {
        getFilterChain().resultSet_updateBlob(resultSet, columnLabel, inputStream, length);
    }

    public void resultSet_updateClob(ResultSetProxy resultSet, int columnIndex, Reader reader, long length) throws SQLException {
        getFilterChain().resultSet_updateClob(resultSet, columnIndex, reader, length);
    }

    public void resultSet_updateClob(ResultSetProxy resultSet, String columnLabel, Reader reader, long length) throws SQLException {
        getFilterChain().resultSet_updateClob(resultSet, columnLabel, reader, length);
    }

    public void resultSet_updateNClob(ResultSetProxy resultSet, int columnIndex, Reader reader, long length) throws SQLException {
        getFilterChain().resultSet_updateNClob(resultSet, columnIndex, reader, length);
    }

    public void resultSet_updateNClob(ResultSetProxy resultSet, String columnLabel, Reader reader, long length) throws SQLException {
        getFilterChain().resultSet_updateNClob(resultSet, columnLabel, reader, length);
    }

    public void resultSet_updateNCharacterStream(ResultSetProxy resultSet, int columnIndex, Reader x) throws SQLException {
        getFilterChain().resultSet_updateNCharacterStream(resultSet, columnIndex, x);
    }

    public void resultSet_updateNCharacterStream(ResultSetProxy resultSet, String columnLabel, Reader reader) throws SQLException {
        getFilterChain().resultSet_updateNCharacterStream(resultSet, columnLabel, reader);
    }

    public void resultSet_updateAsciiStream(ResultSetProxy resultSet, int columnIndex, InputStream x) throws SQLException {
        getFilterChain().resultSet_updateAsciiStream(resultSet, columnIndex, x);
    }

    public void resultSet_updateBinaryStream(ResultSetProxy resultSet, int columnIndex, InputStream x) throws SQLException {
        getFilterChain().resultSet_updateBinaryStream(resultSet, columnIndex, x);
    }

    public void resultSet_updateCharacterStream(ResultSetProxy resultSet, int columnIndex, Reader x) throws SQLException {
        getFilterChain().resultSet_updateCharacterStream(resultSet, columnIndex, x);
    }

    public void resultSet_updateAsciiStream(ResultSetProxy resultSet, String columnLabel, InputStream x) throws SQLException {
        getFilterChain().resultSet_updateAsciiStream(resultSet, columnLabel, x);
    }

    public void resultSet_updateBinaryStream(ResultSetProxy resultSet, String columnLabel, InputStream x) throws SQLException {
        getFilterChain().resultSet_updateBinaryStream(resultSet, columnLabel, x);
    }

    public void resultSet_updateCharacterStream(ResultSetProxy resultSet, String columnLabel, Reader reader) throws SQLException {
        getFilterChain().resultSet_updateCharacterStream(resultSet, columnLabel, reader);
    }

    public void resultSet_updateBlob(ResultSetProxy resultSet, int columnIndex, InputStream inputStream) throws SQLException {
        getFilterChain().resultSet_updateBlob(resultSet, columnIndex, inputStream);
    }

    public void resultSet_updateBlob(ResultSetProxy resultSet, String columnLabel, InputStream inputStream) throws SQLException {
        getFilterChain().resultSet_updateBlob(resultSet, columnLabel, inputStream);
    }

    public void resultSet_updateClob(ResultSetProxy resultSet, int columnIndex, Reader reader) throws SQLException {
        getFilterChain().resultSet_updateClob(resultSet, columnIndex, reader);
    }

    public void resultSet_updateClob(ResultSetProxy resultSet, String columnLabel, Reader reader) throws SQLException {
        getFilterChain().resultSet_updateClob(resultSet, columnLabel, reader);
    }

    public void resultSet_updateNClob(ResultSetProxy resultSet, int columnIndex, Reader reader) throws SQLException {
        getFilterChain().resultSet_updateNClob(resultSet, columnIndex, reader);
    }

    public void resultSet_updateNClob(ResultSetProxy resultSet, String columnLabel, Reader reader) throws SQLException {
        getFilterChain().resultSet_updateNClob(resultSet, columnLabel, reader);
    }

    public ResultSetProxy statement_executeQuery(StatementProxy statement, String sql) throws SQLException {
        return getFilterChain().statement_executeQuery(statement, sql);
    }

    public int statement_executeUpdate(StatementProxy statement, String sql) throws SQLException {
        return getFilterChain().statement_executeUpdate(statement, sql);
    }

    public void statement_close(StatementProxy statement) throws SQLException {
        getFilterChain().statement_close(statement);
    }

    public int statement_getMaxFieldSize(StatementProxy statement) throws SQLException {
        return getFilterChain().statement_getMaxFieldSize(statement);
    }

    public void statement_setMaxFieldSize(StatementProxy statement, int max) throws SQLException {
        getFilterChain().statement_setMaxFieldSize(statement, max);
    }

    public int statement_getMaxRows(StatementProxy statement) throws SQLException {
        return getFilterChain().statement_getMaxRows(statement);
    }

    public void statement_setMaxRows(StatementProxy statement, int max) throws SQLException {
        getFilterChain().statement_setMaxRows(statement, max);
    }

    public void statement_setEscapeProcessing(StatementProxy statement, boolean enable) throws SQLException {
        getFilterChain().statement_setEscapeProcessing(statement, enable);
    }

    public int statement_getQueryTimeout(StatementProxy statement) throws SQLException {
        return getFilterChain().statement_getQueryTimeout(statement);
    }

    public void statement_setQueryTimeout(StatementProxy statement, int seconds) throws SQLException {
        getFilterChain().statement_setQueryTimeout(statement, seconds);
    }

    public void statement_cancel(StatementProxy statement) throws SQLException {
        getFilterChain().statement_cancel(statement);
    }

    public SQLWarning statement_getWarnings(StatementProxy statement) throws SQLException {
        return getFilterChain().statement_getWarnings(statement);
    }

    public void statement_clearWarnings(StatementProxy statement) throws SQLException {
        getFilterChain().statement_clearWarnings(statement);
    }

    public void statement_setCursorName(StatementProxy statement, String name) throws SQLException {
        getFilterChain().statement_setCursorName(statement, name);
    }

    public boolean statement_execute(StatementProxy statement, String sql) throws SQLException {
        return getFilterChain().statement_execute(statement, sql);
    }

    public ResultSetProxy statement_getResultSet(StatementProxy statement) throws SQLException {
        return getFilterChain().statement_getResultSet(statement);
    }

    public int statement_getUpdateCount(StatementProxy statement) throws SQLException {
        return getFilterChain().statement_getUpdateCount(statement);
    }

    public boolean statement_getMoreResults(StatementProxy statement) throws SQLException {
        return getFilterChain().statement_getMoreResults(statement);
    }

    public void statement_setFetchDirection(StatementProxy statement, int direction) throws SQLException {
        getFilterChain().statement_setFetchDirection(statement, direction);
    }

    public int statement_getFetchDirection(StatementProxy statement) throws SQLException {
        return getFilterChain().statement_getFetchDirection(statement);
    }

    public void statement_setFetchSize(StatementProxy statement, int rows) throws SQLException {
        getFilterChain().statement_setFetchSize(statement, rows);
    }

    public int statement_getFetchSize(StatementProxy statement) throws SQLException {
        return getFilterChain().statement_getFetchSize(statement);
    }

    public int statement_getResultSetConcurrency(StatementProxy statement) throws SQLException {
        return getFilterChain().statement_getResultSetConcurrency(statement);
    }

    public int statement_getResultSetType(StatementProxy statement) throws SQLException {
        return getFilterChain().statement_getResultSetType(statement);
    }

    public void statement_addBatch(StatementProxy statement, String sql) throws SQLException {
        getFilterChain().statement_addBatch(statement, sql);
    }

    public void statement_clearBatch(StatementProxy statement) throws SQLException {
        getFilterChain().statement_clearBatch(statement);
    }

    public int[] statement_executeBatch(StatementProxy statement) throws SQLException {
        return getFilterChain().statement_executeBatch(statement);
    }

    public Connection statement_getConnection(StatementProxy statement) throws SQLException {
        return getFilterChain().statement_getConnection(statement);
    }

    public boolean statement_getMoreResults(StatementProxy statement, int current) throws SQLException {
        return getFilterChain().statement_getMoreResults(statement, current);
    }

    public ResultSetProxy statement_getGeneratedKeys(StatementProxy statement) throws SQLException {
        return getFilterChain().statement_getGeneratedKeys(statement);
    }

    public int statement_executeUpdate(StatementProxy statement, String sql, int autoGeneratedKeys) throws SQLException {
        return getFilterChain().statement_executeUpdate(statement, sql, autoGeneratedKeys);
    }

    public int statement_executeUpdate(StatementProxy statement, String sql, int[] columnIndexes) throws SQLException {
        return getFilterChain().statement_executeUpdate(statement, sql, columnIndexes);
    }

    public int statement_executeUpdate(StatementProxy statement, String sql, String[] columnNames) throws SQLException {
        return getFilterChain().statement_executeUpdate(statement, sql, columnNames);
    }

    public boolean statement_execute(StatementProxy statement, String sql, int autoGeneratedKeys) throws SQLException {
        return getFilterChain().statement_execute(statement, sql, autoGeneratedKeys);
    }

    public boolean statement_execute(StatementProxy statement, String sql, int[] columnIndexes) throws SQLException {
        return getFilterChain().statement_execute(statement, sql, columnIndexes);
    }

    public boolean statement_execute(StatementProxy statement, String sql, String[] columnNames) throws SQLException {
        return getFilterChain().statement_execute(statement, sql, columnNames);
    }

    public int statement_getResultSetHoldability(StatementProxy statement) throws SQLException {
        return getFilterChain().statement_getResultSetHoldability(statement);
    }

    public boolean statement_isClosed(StatementProxy statement) throws SQLException {
        return getFilterChain().statement_isClosed(statement);
    }

    public void statement_setPoolable(StatementProxy statement, boolean poolable) throws SQLException {
        getFilterChain().statement_setPoolable(statement, poolable);
    }

    public boolean statement_isPoolable(StatementProxy statement) throws SQLException {
        return getFilterChain().statement_isPoolable(statement);
    }

    public ResultSetProxy preparedStatement_executeQuery(PreparedStatementProxy statement) throws SQLException {
        return getFilterChain().preparedStatement_executeQuery(statement);
    }

    public int preparedStatement_executeUpdate(PreparedStatementProxy statement) throws SQLException {
        return getFilterChain().preparedStatement_executeUpdate(statement);
    }

    public void preparedStatement_setNull(PreparedStatementProxy statement, int parameterIndex, int sqlType) throws SQLException {
        getFilterChain().preparedStatement_setNull(statement, parameterIndex, sqlType);
    }

    public void preparedStatement_setBoolean(PreparedStatementProxy statement, int parameterIndex, boolean x) throws SQLException {
        getFilterChain().preparedStatement_setBoolean(statement, parameterIndex, x);
    }

    public void preparedStatement_setByte(PreparedStatementProxy statement, int parameterIndex, byte x) throws SQLException {
        getFilterChain().preparedStatement_setByte(statement, parameterIndex, x);
    }

    public void preparedStatement_setShort(PreparedStatementProxy statement, int parameterIndex, short x) throws SQLException {
        getFilterChain().preparedStatement_setShort(statement, parameterIndex, x);
    }

    public void preparedStatement_setInt(PreparedStatementProxy statement, int parameterIndex, int x) throws SQLException {
        getFilterChain().preparedStatement_setInt(statement, parameterIndex, x);
    }

    public void preparedStatement_setLong(PreparedStatementProxy statement, int parameterIndex, long x) throws SQLException {
        getFilterChain().preparedStatement_setLong(statement, parameterIndex, x);
    }

    public void preparedStatement_setFloat(PreparedStatementProxy statement, int parameterIndex, float x) throws SQLException {
        getFilterChain().preparedStatement_setFloat(statement, parameterIndex, x);
    }

    public void preparedStatement_setDouble(PreparedStatementProxy statement, int parameterIndex, double x) throws SQLException {
        getFilterChain().preparedStatement_setDouble(statement, parameterIndex, x);
    }

    public void preparedStatement_setBigDecimal(PreparedStatementProxy statement, int parameterIndex, BigDecimal x) throws SQLException {
        getFilterChain().preparedStatement_setBigDecimal(statement, parameterIndex, x);
    }

    public void preparedStatement_setString(PreparedStatementProxy statement, int parameterIndex, String x) throws SQLException {
        getFilterChain().preparedStatement_setString(statement, parameterIndex, x);
    }

    public void preparedStatement_setBytes(PreparedStatementProxy statement, int parameterIndex, byte[] x) throws SQLException {
        getFilterChain().preparedStatement_setBytes(statement, parameterIndex, x);
    }

    public void preparedStatement_setDate(PreparedStatementProxy statement, int parameterIndex, Date x) throws SQLException {
        getFilterChain().preparedStatement_setDate(statement, parameterIndex, x);
    }

    public void preparedStatement_setTime(PreparedStatementProxy statement, int parameterIndex, Time x) throws SQLException {
        getFilterChain().preparedStatement_setTime(statement, parameterIndex, x);
    }

    public void preparedStatement_setTimestamp(PreparedStatementProxy statement, int parameterIndex, Timestamp x) throws SQLException {
        getFilterChain().preparedStatement_setTimestamp(statement, parameterIndex, x);
    }

    public void preparedStatement_setAsciiStream(PreparedStatementProxy statement, int parameterIndex, InputStream x, int length) throws SQLException {
        getFilterChain().preparedStatement_setAsciiStream(statement, parameterIndex, x, length);
    }

    public void preparedStatement_setUnicodeStream(PreparedStatementProxy statement, int parameterIndex, InputStream x, int length) throws SQLException {
        getFilterChain().preparedStatement_setUnicodeStream(statement, parameterIndex, x, length);
    }

    public void preparedStatement_setBinaryStream(PreparedStatementProxy statement, int parameterIndex, InputStream x, int length) throws SQLException {
        getFilterChain().preparedStatement_setBinaryStream(statement, parameterIndex, x, length);
    }

    public void preparedStatement_clearParameters(PreparedStatementProxy statement) throws SQLException {
        getFilterChain().preparedStatement_clearParameters(statement);
    }

    public void preparedStatement_setObject(PreparedStatementProxy statement, int parameterIndex, Object x, int targetSqlType) throws SQLException {
        getFilterChain().preparedStatement_setObject(statement, parameterIndex, x, targetSqlType);
    }

    public void preparedStatement_setObject(PreparedStatementProxy statement, int parameterIndex, Object x) throws SQLException {
        getFilterChain().preparedStatement_setObject(statement, parameterIndex, x);
    }

    public boolean preparedStatement_execute(PreparedStatementProxy statement) throws SQLException {
        return getFilterChain().preparedStatement_execute(statement);
    }

    public void preparedStatement_addBatch(PreparedStatementProxy statement) throws SQLException {
        getFilterChain().preparedStatement_addBatch(statement);
    }

    public void preparedStatement_setCharacterStream(PreparedStatementProxy statement, int parameterIndex, Reader reader, int length) throws SQLException {
        getFilterChain().preparedStatement_setCharacterStream(statement, parameterIndex, reader, length);
    }

    public void preparedStatement_setRef(PreparedStatementProxy statement, int parameterIndex, Ref x) throws SQLException {
        getFilterChain().preparedStatement_setRef(statement, parameterIndex, x);
    }

    public void preparedStatement_setBlob(PreparedStatementProxy statement, int parameterIndex, Blob x) throws SQLException {
        getFilterChain().preparedStatement_setBlob(statement, parameterIndex, x);
    }

    public void preparedStatement_setClob(PreparedStatementProxy statement, int parameterIndex, Clob x) throws SQLException {
        getFilterChain().preparedStatement_setClob(statement, parameterIndex, x);
    }

    public void preparedStatement_setArray(PreparedStatementProxy statement, int parameterIndex, Array x) throws SQLException {
        getFilterChain().preparedStatement_setArray(statement, parameterIndex, x);
    }

    public ResultSetMetaData preparedStatement_getMetaData(PreparedStatementProxy statement) throws SQLException {
        return getFilterChain().preparedStatement_getMetaData(statement);
    }

    public void preparedStatement_setDate(PreparedStatementProxy statement, int parameterIndex, Date x, Calendar cal) throws SQLException {
        getFilterChain().preparedStatement_setDate(statement, parameterIndex, x, cal);
    }

    public void preparedStatement_setTime(PreparedStatementProxy statement, int parameterIndex, Time x, Calendar cal) throws SQLException {
        getFilterChain().preparedStatement_setTime(statement, parameterIndex, x, cal);
    }

    public void preparedStatement_setTimestamp(PreparedStatementProxy statement, int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        getFilterChain().preparedStatement_setTimestamp(statement, parameterIndex, x, cal);
    }

    public void preparedStatement_setNull(PreparedStatementProxy statement, int parameterIndex, int sqlType, String typeName) throws SQLException {
        getFilterChain().preparedStatement_setNull(statement, parameterIndex, sqlType, typeName);
    }

    public void preparedStatement_setURL(PreparedStatementProxy statement, int parameterIndex, URL x) throws SQLException {
        getFilterChain().preparedStatement_setURL(statement, parameterIndex, x);
    }

    public ParameterMetaData preparedStatement_getParameterMetaData(PreparedStatementProxy statement) throws SQLException {
        return getFilterChain().preparedStatement_getParameterMetaData(statement);
    }

    public void preparedStatement_setRowId(PreparedStatementProxy statement, int parameterIndex, RowId x) throws SQLException {
        getFilterChain().preparedStatement_setRowId(statement, parameterIndex, x);
    }

    public void preparedStatement_setNString(PreparedStatementProxy statement, int parameterIndex, String value) throws SQLException {
        getFilterChain().preparedStatement_setNString(statement, parameterIndex, value);
    }

    public void preparedStatement_setNCharacterStream(PreparedStatementProxy statement, int parameterIndex, Reader value, long length) throws SQLException {
        getFilterChain().preparedStatement_setNCharacterStream(statement, parameterIndex, value, length);
    }

    public void preparedStatement_setNClob(PreparedStatementProxy statement, int parameterIndex, NClob value) throws SQLException {
        getFilterChain().preparedStatement_setNClob(statement, parameterIndex, value);
    }

    public void preparedStatement_setClob(PreparedStatementProxy statement, int parameterIndex, Reader reader, long length) throws SQLException {
        getFilterChain().preparedStatement_setClob(statement, parameterIndex, reader, length);
    }

    public void preparedStatement_setBlob(PreparedStatementProxy statement, int parameterIndex, InputStream inputStream, long length) throws SQLException {
        getFilterChain().preparedStatement_setBlob(statement, parameterIndex, inputStream, length);
    }

    public void preparedStatement_setNClob(PreparedStatementProxy statement, int parameterIndex, Reader reader, long length) throws SQLException {
        getFilterChain().preparedStatement_setNClob(statement, parameterIndex, reader, length);
    }

    public void preparedStatement_setSQLXML(PreparedStatementProxy statement, int parameterIndex, SQLXML xmlObject) throws SQLException {
        getFilterChain().preparedStatement_setSQLXML(statement, parameterIndex, xmlObject);
    }

    public void preparedStatement_setObject(PreparedStatementProxy statement, int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
        getFilterChain().preparedStatement_setObject(statement, parameterIndex, x, targetSqlType, scaleOrLength);
    }

    public void preparedStatement_setAsciiStream(PreparedStatementProxy statement, int parameterIndex, InputStream x, long length) throws SQLException {
        getFilterChain().preparedStatement_setAsciiStream(statement, parameterIndex, x, length);
    }

    public void preparedStatement_setBinaryStream(PreparedStatementProxy statement, int parameterIndex, InputStream x, long length) throws SQLException {
        getFilterChain().preparedStatement_setBinaryStream(statement, parameterIndex, x, length);
    }

    public void preparedStatement_setCharacterStream(PreparedStatementProxy statement, int parameterIndex, Reader reader, long length) throws SQLException {
        getFilterChain().preparedStatement_setCharacterStream(statement, parameterIndex, reader, length);
    }

    public void preparedStatement_setAsciiStream(PreparedStatementProxy statement, int parameterIndex, InputStream x) throws SQLException {
        getFilterChain().preparedStatement_setAsciiStream(statement, parameterIndex, x);
    }

    public void preparedStatement_setBinaryStream(PreparedStatementProxy statement, int parameterIndex, InputStream x) throws SQLException {
        getFilterChain().preparedStatement_setBinaryStream(statement, parameterIndex, x);
    }

    public void preparedStatement_setCharacterStream(PreparedStatementProxy statement, int parameterIndex, Reader reader) throws SQLException {
        getFilterChain().preparedStatement_setCharacterStream(statement, parameterIndex, reader);
    }

    public void preparedStatement_setNCharacterStream(PreparedStatementProxy statement, int parameterIndex, Reader value) throws SQLException {
        getFilterChain().preparedStatement_setNCharacterStream(statement, parameterIndex, value);
    }

    public void preparedStatement_setClob(PreparedStatementProxy statement, int parameterIndex, Reader reader) throws SQLException {
        getFilterChain().preparedStatement_setClob(statement, parameterIndex, reader);
    }

    public void preparedStatement_setBlob(PreparedStatementProxy statement, int parameterIndex, InputStream inputStream) throws SQLException {
        getFilterChain().preparedStatement_setBlob(statement, parameterIndex, inputStream);
    }

    public void preparedStatement_setNClob(PreparedStatementProxy statement, int parameterIndex, Reader reader) throws SQLException {
        getFilterChain().preparedStatement_setNClob(statement, parameterIndex, reader);
    }

    public void callableStatement_registerOutParameter(CallableStatementProxy statement, int parameterIndex, int sqlType) throws SQLException {
        getFilterChain().callableStatement_registerOutParameter(statement, parameterIndex, sqlType);
    }

    public void callableStatement_registerOutParameter(CallableStatementProxy statement, int parameterIndex, int sqlType, int scale) throws SQLException {
        getFilterChain().callableStatement_registerOutParameter(statement, parameterIndex, sqlType, scale);
    }

    public boolean callableStatement_wasNull(CallableStatementProxy statement) throws SQLException {
        return getFilterChain().callableStatement_wasNull(statement);
    }

    public String callableStatement_getString(CallableStatementProxy statement, int parameterIndex) throws SQLException {
        return getFilterChain().callableStatement_getString(statement, parameterIndex);
    }

    public boolean callableStatement_getBoolean(CallableStatementProxy statement, int parameterIndex) throws SQLException {
        return getFilterChain().callableStatement_getBoolean(statement, parameterIndex);
    }

    public byte callableStatement_getByte(CallableStatementProxy statement, int parameterIndex) throws SQLException {
        return getFilterChain().callableStatement_getByte(statement, parameterIndex);
    }

    public short callableStatement_getShort(CallableStatementProxy statement, int parameterIndex) throws SQLException {
        return getFilterChain().callableStatement_getShort(statement, parameterIndex);
    }

    public int callableStatement_getInt(CallableStatementProxy statement, int parameterIndex) throws SQLException {
        return getFilterChain().callableStatement_getInt(statement, parameterIndex);
    }

    public long callableStatement_getLong(CallableStatementProxy statement, int parameterIndex) throws SQLException {
        return getFilterChain().callableStatement_getLong(statement, parameterIndex);
    }

    public float callableStatement_getFloat(CallableStatementProxy statement, int parameterIndex) throws SQLException {
        return getFilterChain().callableStatement_getFloat(statement, parameterIndex);
    }

    public double callableStatement_getDouble(CallableStatementProxy statement, int parameterIndex) throws SQLException {
        return getFilterChain().callableStatement_getDouble(statement, parameterIndex);
    }

    public BigDecimal callableStatement_getBigDecimal(CallableStatementProxy statement, int parameterIndex, int scale) throws SQLException {
        return getFilterChain().callableStatement_getBigDecimal(statement, parameterIndex, scale);
    }

    public byte[] callableStatement_getBytes(CallableStatementProxy statement, int parameterIndex) throws SQLException {
        return getFilterChain().callableStatement_getBytes(statement, parameterIndex);
    }

    public Date callableStatement_getDate(CallableStatementProxy statement, int parameterIndex) throws SQLException {
        return getFilterChain().callableStatement_getDate(statement, parameterIndex);
    }

    public Time callableStatement_getTime(CallableStatementProxy statement, int parameterIndex) throws SQLException {
        return getFilterChain().callableStatement_getTime(statement, parameterIndex);
    }

    public Timestamp callableStatement_getTimestamp(CallableStatementProxy statement, int parameterIndex) throws SQLException {
        return getFilterChain().callableStatement_getTimestamp(statement, parameterIndex);
    }

    public Object callableStatement_getObject(CallableStatementProxy statement, int parameterIndex) throws SQLException {
        return getFilterChain().callableStatement_getObject(statement, parameterIndex);
    }

    public BigDecimal callableStatement_getBigDecimal(CallableStatementProxy statement, int parameterIndex) throws SQLException {
        return getFilterChain().callableStatement_getBigDecimal(statement, parameterIndex);
    }

    public Object callableStatement_getObject(CallableStatementProxy statement, int parameterIndex, Map<String, Class<?>> map) throws SQLException {
        return getFilterChain().callableStatement_getObject(statement, parameterIndex, map);
    }

    public Ref callableStatement_getRef(CallableStatementProxy statement, int parameterIndex) throws SQLException {
        return getFilterChain().callableStatement_getRef(statement, parameterIndex);
    }

    public Blob callableStatement_getBlob(CallableStatementProxy statement, int parameterIndex) throws SQLException {
        return getFilterChain().callableStatement_getBlob(statement, parameterIndex);
    }

    public Clob callableStatement_getClob(CallableStatementProxy statement, int parameterIndex) throws SQLException {
        return getFilterChain().callableStatement_getClob(statement, parameterIndex);
    }

    public Array callableStatement_getArray(CallableStatementProxy statement, int parameterIndex) throws SQLException {
        return getFilterChain().callableStatement_getArray(statement, parameterIndex);
    }

    public Date callableStatement_getDate(CallableStatementProxy statement, int parameterIndex, Calendar cal) throws SQLException {
        return getFilterChain().callableStatement_getDate(statement, parameterIndex);
    }

    public Time callableStatement_getTime(CallableStatementProxy statement, int parameterIndex, Calendar cal) throws SQLException {
        return getFilterChain().callableStatement_getTime(statement, parameterIndex, cal);
    }

    public Timestamp callableStatement_getTimestamp(CallableStatementProxy statement, int parameterIndex, Calendar cal) throws SQLException {
        return getFilterChain().callableStatement_getTimestamp(statement, parameterIndex, cal);
    }

    public void callableStatement_registerOutParameter(CallableStatementProxy statement, int parameterIndex, int sqlType, String typeName) throws SQLException {
        getFilterChain().callableStatement_registerOutParameter(statement, parameterIndex, sqlType, typeName);
    }

    public void callableStatement_registerOutParameter(CallableStatementProxy statement, String parameterName, int sqlType) throws SQLException {
        getFilterChain().callableStatement_registerOutParameter(statement, parameterName, sqlType);
    }

    public void callableStatement_registerOutParameter(CallableStatementProxy statement, String parameterName, int sqlType, int scale) throws SQLException {
        getFilterChain().callableStatement_registerOutParameter(statement, parameterName, sqlType, scale);
    }

    public void callableStatement_registerOutParameter(CallableStatementProxy statement, String parameterName, int sqlType, String typeName) throws SQLException {
        getFilterChain().callableStatement_registerOutParameter(statement, parameterName, sqlType, typeName);
    }

    public URL callableStatement_getURL(CallableStatementProxy statement, int parameterIndex) throws SQLException {
        return getFilterChain().callableStatement_getURL(statement, parameterIndex);
    }

    public void callableStatement_setURL(CallableStatementProxy statement, String parameterName, URL val) throws SQLException {
        getFilterChain().callableStatement_setURL(statement, parameterName, val);
    }

    public void callableStatement_setNull(CallableStatementProxy statement, String parameterName, int sqlType) throws SQLException {
        getFilterChain().callableStatement_setNull(statement, parameterName, sqlType);
    }

    public void callableStatement_setBoolean(CallableStatementProxy statement, String parameterName, boolean x) throws SQLException {
        getFilterChain().callableStatement_setBoolean(statement, parameterName, x);
    }

    public void callableStatement_setByte(CallableStatementProxy statement, String parameterName, byte x) throws SQLException {
        getFilterChain().callableStatement_setByte(statement, parameterName, x);
    }

    public void callableStatement_setShort(CallableStatementProxy statement, String parameterName, short x) throws SQLException {
        getFilterChain().callableStatement_setShort(statement, parameterName, x);
    }

    public void callableStatement_setInt(CallableStatementProxy statement, String parameterName, int x) throws SQLException {
        getFilterChain().callableStatement_setInt(statement, parameterName, x);
    }

    public void callableStatement_setLong(CallableStatementProxy statement, String parameterName, long x) throws SQLException {
        getFilterChain().callableStatement_setLong(statement, parameterName, x);
    }

    public void callableStatement_setFloat(CallableStatementProxy statement, String parameterName, float x) throws SQLException {
        getFilterChain().callableStatement_setFloat(statement, parameterName, x);
    }

    public void callableStatement_setDouble(CallableStatementProxy statement, String parameterName, double x) throws SQLException {
        getFilterChain().callableStatement_setDouble(statement, parameterName, x);
    }

    public void callableStatement_setBigDecimal(CallableStatementProxy statement, String parameterName, BigDecimal x) throws SQLException {
        getFilterChain().callableStatement_setBigDecimal(statement, parameterName, x);
    }

    public void callableStatement_setString(CallableStatementProxy statement, String parameterName, String x) throws SQLException {
        getFilterChain().callableStatement_setString(statement, parameterName, x);
    }

    public void callableStatement_setBytes(CallableStatementProxy statement, String parameterName, byte[] x) throws SQLException {
        getFilterChain().callableStatement_setBytes(statement, parameterName, x);
    }

    public void callableStatement_setDate(CallableStatementProxy statement, String parameterName, Date x) throws SQLException {
        getFilterChain().callableStatement_setDate(statement, parameterName, x);
    }

    public void callableStatement_setTime(CallableStatementProxy statement, String parameterName, Time x) throws SQLException {
        getFilterChain().callableStatement_setTime(statement, parameterName, x);
    }

    public void callableStatement_setTimestamp(CallableStatementProxy statement, String parameterName, Timestamp x) throws SQLException {
        getFilterChain().callableStatement_setTimestamp(statement, parameterName, x);
    }

    public void callableStatement_setAsciiStream(CallableStatementProxy statement, String parameterName, InputStream x, int length) throws SQLException {
        getFilterChain().callableStatement_setAsciiStream(statement, parameterName, x, length);
    }

    public void callableStatement_setBinaryStream(CallableStatementProxy statement, String parameterName, InputStream x, int length) throws SQLException {
        getFilterChain().callableStatement_setBinaryStream(statement, parameterName, x, length);
    }

    public void callableStatement_setObject(CallableStatementProxy statement, String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
        getFilterChain().callableStatement_setObject(statement, parameterName, x, targetSqlType, scale);
    }

    public void callableStatement_setObject(CallableStatementProxy statement, String parameterName, Object x, int targetSqlType) throws SQLException {
        getFilterChain().callableStatement_setObject(statement, parameterName, x, targetSqlType);
    }

    public void callableStatement_setObject(CallableStatementProxy statement, String parameterName, Object x) throws SQLException {
        getFilterChain().callableStatement_setObject(statement, parameterName, x);
    }

    public void callableStatement_setCharacterStream(CallableStatementProxy statement, String parameterName, Reader reader, int length) throws SQLException {
        getFilterChain().callableStatement_setCharacterStream(statement, parameterName, reader, length);
    }

    public void callableStatement_setDate(CallableStatementProxy statement, String parameterName, Date x, Calendar cal) throws SQLException {
        getFilterChain().callableStatement_setDate(statement, parameterName, x, cal);
    }

    public void callableStatement_setTime(CallableStatementProxy statement, String parameterName, Time x, Calendar cal) throws SQLException {
        getFilterChain().callableStatement_setTime(statement, parameterName, x, cal);
    }

    public void callableStatement_setTimestamp(CallableStatementProxy statement, String parameterName, Timestamp x, Calendar cal) throws SQLException {
        getFilterChain().callableStatement_setTimestamp(statement, parameterName, x, cal);
    }

    public void callableStatement_setNull(CallableStatementProxy statement, String parameterName, int sqlType, String typeName) throws SQLException {
        getFilterChain().callableStatement_setNull(statement, parameterName, sqlType, typeName);
    }

    public String callableStatement_getString(CallableStatementProxy statement, String parameterName) throws SQLException {
        return getFilterChain().callableStatement_getString(statement, parameterName);
    }

    public boolean callableStatement_getBoolean(CallableStatementProxy statement, String parameterName) throws SQLException {
        return getFilterChain().callableStatement_getBoolean(statement, parameterName);
    }

    public byte callableStatement_getByte(CallableStatementProxy statement, String parameterName) throws SQLException {
        return getFilterChain().callableStatement_getByte(statement, parameterName);
    }

    public short callableStatement_getShort(CallableStatementProxy statement, String parameterName) throws SQLException {
        return getFilterChain().callableStatement_getShort(statement, parameterName);
    }

    public int callableStatement_getInt(CallableStatementProxy statement, String parameterName) throws SQLException {
        return getFilterChain().callableStatement_getInt(statement, parameterName);
    }

    public long callableStatement_getLong(CallableStatementProxy statement, String parameterName) throws SQLException {
        return getFilterChain().callableStatement_getLong(statement, parameterName);
    }

    public float callableStatement_getFloat(CallableStatementProxy statement, String parameterName) throws SQLException {
        return getFilterChain().callableStatement_getFloat(statement, parameterName);
    }

    public double callableStatement_getDouble(CallableStatementProxy statement, String parameterName) throws SQLException {
        return getFilterChain().callableStatement_getDouble(statement, parameterName);
    }

    public byte[] callableStatement_getBytes(CallableStatementProxy statement, String parameterName) throws SQLException {
        return getFilterChain().callableStatement_getBytes(statement, parameterName);
    }

    public Date callableStatement_getDate(CallableStatementProxy statement, String parameterName) throws SQLException {
        return getFilterChain().callableStatement_getDate(statement, parameterName);
    }

    public Time callableStatement_getTime(CallableStatementProxy statement, String parameterName) throws SQLException {
        return getFilterChain().callableStatement_getTime(statement, parameterName);
    }

    public Timestamp callableStatement_getTimestamp(CallableStatementProxy statement, String parameterName) throws SQLException {
        return getFilterChain().callableStatement_getTimestamp(statement, parameterName);
    }

    public Object callableStatement_getObject(CallableStatementProxy statement, String parameterName) throws SQLException {
        return getFilterChain().callableStatement_getObject(statement, parameterName);
    }

    public BigDecimal callableStatement_getBigDecimal(CallableStatementProxy statement, String parameterName) throws SQLException {
        return getFilterChain().callableStatement_getBigDecimal(statement, parameterName);
    }

    public Object callableStatement_getObject(CallableStatementProxy statement, String parameterName, Map<String, Class<?>> map) throws SQLException {
        return getFilterChain().callableStatement_getObject(statement, parameterName, map);
    }

    public Ref callableStatement_getRef(CallableStatementProxy statement, String parameterName) throws SQLException {
        return getFilterChain().callableStatement_getRef(statement, parameterName);
    }

    public Blob callableStatement_getBlob(CallableStatementProxy statement, String parameterName) throws SQLException {
        return getFilterChain().callableStatement_getBlob(statement, parameterName);
    }

    public Clob callableStatement_getClob(CallableStatementProxy statement, String parameterName) throws SQLException {
        return getFilterChain().callableStatement_getClob(statement, parameterName);
    }

    public Array callableStatement_getArray(CallableStatementProxy statement, String parameterName) throws SQLException {
        return getFilterChain().callableStatement_getArray(statement, parameterName);
    }

    public Date callableStatement_getDate(CallableStatementProxy statement, String parameterName, Calendar cal) throws SQLException {
        return getFilterChain().callableStatement_getDate(statement, parameterName, cal);
    }

    public Time callableStatement_getTime(CallableStatementProxy statement, String parameterName, Calendar cal) throws SQLException {
        return getFilterChain().callableStatement_getTime(statement, parameterName, cal);
    }

    public Timestamp callableStatement_getTimestamp(CallableStatementProxy statement, String parameterName, Calendar cal) throws SQLException {
        return getFilterChain().callableStatement_getTimestamp(statement, parameterName, cal);
    }

    public URL callableStatement_getURL(CallableStatementProxy statement, String parameterName) throws SQLException {
        return getFilterChain().callableStatement_getURL(statement, parameterName);
    }

    public RowId callableStatement_getRowId(CallableStatementProxy statement, int parameterIndex) throws SQLException {
        return getFilterChain().callableStatement_getRowId(statement, parameterIndex);
    }

    public RowId callableStatement_getRowId(CallableStatementProxy statement, String parameterName) throws SQLException {
        return getFilterChain().callableStatement_getRowId(statement, parameterName);
    }

    public void callableStatement_setRowId(CallableStatementProxy statement, String parameterName, RowId x) throws SQLException {
        getFilterChain().callableStatement_setRowId(statement, parameterName, x);
    }

    public void callableStatement_setNString(CallableStatementProxy statement, String parameterName, String value) throws SQLException {
        getFilterChain().callableStatement_setNString(statement, parameterName, value);
    }

    public void callableStatement_setNCharacterStream(CallableStatementProxy statement, String parameterName, Reader value, long length) throws SQLException {
        getFilterChain().callableStatement_setNCharacterStream(statement, parameterName, value, length);
    }

    public void callableStatement_setNClob(CallableStatementProxy statement, String parameterName, NClob value) throws SQLException {
        getFilterChain().callableStatement_setNClob(statement, parameterName, value);
    }

    public void callableStatement_setClob(CallableStatementProxy statement, String parameterName, Reader reader, long length) throws SQLException {
        getFilterChain().callableStatement_setClob(statement, parameterName, reader, length);
    }

    public void callableStatement_setBlob(CallableStatementProxy statement, String parameterName, InputStream inputStream, long length) throws SQLException {
        getFilterChain().callableStatement_setBlob(statement, parameterName, inputStream, length);
    }

    public void callableStatement_setNClob(CallableStatementProxy statement, String parameterName, Reader reader, long length) throws SQLException {
        getFilterChain().callableStatement_setNClob(statement, parameterName, reader, length);
    }

    public NClob callableStatement_getNClob(CallableStatementProxy statement, int parameterIndex) throws SQLException {
        return getFilterChain().callableStatement_getNClob(statement, parameterIndex);
    }

    public NClob callableStatement_getNClob(CallableStatementProxy statement, String parameterName) throws SQLException {
        return getFilterChain().callableStatement_getNClob(statement, parameterName);
    }

    public void callableStatement_setSQLXML(CallableStatementProxy statement, String parameterName, SQLXML xmlObject) throws SQLException {
        getFilterChain().callableStatement_setSQLXML(statement, parameterName, xmlObject);
    }

    public SQLXML callableStatement_getSQLXML(CallableStatementProxy statement, int parameterIndex) throws SQLException {
        return getFilterChain().callableStatement_getSQLXML(statement, parameterIndex);
    }

    public SQLXML callableStatement_getSQLXML(CallableStatementProxy statement, String parameterName) throws SQLException {
        return getFilterChain().callableStatement_getSQLXML(statement, parameterName);
    }

    public String callableStatement_getNString(CallableStatementProxy statement, int parameterIndex) throws SQLException {
        return getFilterChain().callableStatement_getNString(statement, parameterIndex);
    }

    public String callableStatement_getNString(CallableStatementProxy statement, String parameterName) throws SQLException {
        return getFilterChain().callableStatement_getNString(statement, parameterName);
    }

    public Reader callableStatement_getNCharacterStream(CallableStatementProxy statement, int parameterIndex) throws SQLException {
        return getFilterChain().callableStatement_getNCharacterStream(statement, parameterIndex);
    }

    public Reader callableStatement_getNCharacterStream(CallableStatementProxy statement, String parameterName) throws SQLException {
        return getFilterChain().callableStatement_getNCharacterStream(statement, parameterName);
    }

    public Reader callableStatement_getCharacterStream(CallableStatementProxy statement, int parameterIndex) throws SQLException {
        return getFilterChain().callableStatement_getCharacterStream(statement, parameterIndex);
    }

    public Reader callableStatement_getCharacterStream(CallableStatementProxy statement, String parameterName) throws SQLException {
        return getFilterChain().callableStatement_getCharacterStream(statement, parameterName);
    }

    public void callableStatement_setBlob(CallableStatementProxy statement, String parameterName, Blob x) throws SQLException {
        getFilterChain().callableStatement_setBlob(statement, parameterName, x);
    }

    public void callableStatement_setClob(CallableStatementProxy statement, String parameterName, Clob x) throws SQLException {
        getFilterChain().callableStatement_setClob(statement, parameterName, x);
    }

    public void callableStatement_setAsciiStream(CallableStatementProxy statement, String parameterName, InputStream x, long length) throws SQLException {
        getFilterChain().callableStatement_setAsciiStream(statement, parameterName, x, length);
    }

    public void callableStatement_setBinaryStream(CallableStatementProxy statement, String parameterName, InputStream x, long length) throws SQLException {
        getFilterChain().callableStatement_setBinaryStream(statement, parameterName, x, length);
    }

    public void callableStatement_setCharacterStream(CallableStatementProxy statement, String parameterName, Reader reader, long length) throws SQLException {
        getFilterChain().callableStatement_setCharacterStream(statement, parameterName, reader, length);
    }

    public void callableStatement_setAsciiStream(CallableStatementProxy statement, String parameterName, InputStream x) throws SQLException {
        getFilterChain().callableStatement_setAsciiStream(statement, parameterName, x);
    }

    public void callableStatement_setBinaryStream(CallableStatementProxy statement, String parameterName, InputStream x) throws SQLException {
        getFilterChain().callableStatement_setBinaryStream(statement, parameterName, x);
    }

    public void callableStatement_setCharacterStream(CallableStatementProxy statement, String parameterName, Reader reader) throws SQLException {
        getFilterChain().callableStatement_setCharacterStream(statement, parameterName, reader);
    }

    public void callableStatement_setNCharacterStream(CallableStatementProxy statement, String parameterName, Reader value) throws SQLException {
        getFilterChain().callableStatement_setNCharacterStream(statement, parameterName, value);
    }

    public void callableStatement_setClob(CallableStatementProxy statement, String parameterName, Reader reader) throws SQLException {
        getFilterChain().callableStatement_setClob(statement, parameterName, reader);
    }

    public void callableStatement_setBlob(CallableStatementProxy statement, String parameterName, InputStream inputStream) throws SQLException {
        getFilterChain().callableStatement_setBlob(statement, parameterName, inputStream);
    }

    public void callableStatement_setNClob(CallableStatementProxy statement, String parameterName, Reader reader) throws SQLException {
        getFilterChain().callableStatement_setNClob(statement, parameterName, reader);
    }

    public void clob_free(ClobProxy wrapper) throws SQLException {
        getFilterChain().clob_free(wrapper);
    }

    public InputStream clob_getAsciiStream(ClobProxy wrapper) throws SQLException {
        return getFilterChain().clob_getAsciiStream(wrapper);
    }

    public Reader clob_getCharacterStream(ClobProxy wrapper) throws SQLException {
        return getFilterChain().clob_getCharacterStream(wrapper);
    }

    public Reader clob_getCharacterStream(ClobProxy wrapper, long pos, long length) throws SQLException {
        return getFilterChain().clob_getCharacterStream(wrapper, pos, length);
    }

    public String clob_getSubString(ClobProxy wrapper, long pos, int length) throws SQLException {
        return getFilterChain().clob_getSubString(wrapper, pos, length);
    }

    public long clob_length(ClobProxy wrapper) throws SQLException {
        return getFilterChain().clob_length(wrapper);
    }

    public long clob_position(ClobProxy wrapper, String searchstr, long start) throws SQLException {
        return getFilterChain().clob_position(wrapper, searchstr, start);
    }

    public long clob_position(ClobProxy wrapper, Clob searchstr, long start) throws SQLException {
        return getFilterChain().clob_position(wrapper, searchstr, start);
    }

    public OutputStream clob_setAsciiStream(ClobProxy wrapper, long pos) throws SQLException {
        return getFilterChain().clob_setAsciiStream(wrapper, pos);
    }

    public Writer clob_setCharacterStream(ClobProxy wrapper, long pos) throws SQLException {
        return getFilterChain().clob_setCharacterStream(wrapper, pos);
    }

    public int clob_setString(ClobProxy wrapper, long pos, String str) throws SQLException {
        return getFilterChain().clob_setString(wrapper, pos, str);
    }

    public int clob_setString(ClobProxy wrapper, long pos, String str, int offset, int len) throws SQLException {
        return getFilterChain().clob_setString(wrapper, pos, str, offset, len);
    }

    public void clob_truncate(ClobProxy wrapper, long len) throws SQLException {
        getFilterChain().clob_truncate(wrapper, len);
    }

    public void dataSource_recycle(DruidPooledConnection connection) throws SQLException {
        getFilterChain().dataSource_recycle(connection);
    }

    public DruidPooledConnection dataSource_connect(DruidDataSource dataSource, long maxWaitMillis) throws SQLException {
        return getFilterChain().dataSource_connect(dataSource, maxWaitMillis);
    }

    public int resultSetMetaData_getColumnCount(ResultSetMetaDataProxy metaData) throws SQLException {
        return getFilterChain().resultSetMetaData_getColumnCount(metaData);
    }

    public boolean resultSetMetaData_isAutoIncrement(ResultSetMetaDataProxy metaData, int column) throws SQLException {
        return getFilterChain().resultSetMetaData_isAutoIncrement(metaData, column);
    }

    public boolean resultSetMetaData_isCaseSensitive(ResultSetMetaDataProxy metaData, int column) throws SQLException {
        return getFilterChain().resultSetMetaData_isCaseSensitive(metaData, column);
    }

    public boolean resultSetMetaData_isSearchable(ResultSetMetaDataProxy metaData, int column) throws SQLException {
        return getFilterChain().resultSetMetaData_isSearchable(metaData, column);
    }

    public boolean resultSetMetaData_isCurrency(ResultSetMetaDataProxy metaData, int column) throws SQLException {
        return getFilterChain().resultSetMetaData_isCurrency(metaData, column);
    }

    public int resultSetMetaData_isNullable(ResultSetMetaDataProxy metaData, int column) throws SQLException {
        return getFilterChain().resultSetMetaData_isNullable(metaData, column);
    }

    public boolean resultSetMetaData_isSigned(ResultSetMetaDataProxy metaData, int column) throws SQLException {
        return getFilterChain().resultSetMetaData_isSigned(metaData, column);
    }

    public int resultSetMetaData_getColumnDisplaySize(ResultSetMetaDataProxy metaData, int column) throws SQLException {
        return getFilterChain().resultSetMetaData_getColumnDisplaySize(metaData, column);
    }

    public String resultSetMetaData_getColumnLabel(ResultSetMetaDataProxy metaData, int column) throws SQLException {
        return getFilterChain().resultSetMetaData_getColumnLabel(metaData, column);
    }

    public String resultSetMetaData_getColumnName(ResultSetMetaDataProxy metaData, int column) throws SQLException {
        return getFilterChain().resultSetMetaData_getColumnName(metaData, column);
    }

    public String resultSetMetaData_getSchemaName(ResultSetMetaDataProxy metaData, int column) throws SQLException {
        return getFilterChain().resultSetMetaData_getSchemaName(metaData, column);
    }

    public int resultSetMetaData_getPrecision(ResultSetMetaDataProxy metaData, int column) throws SQLException {
        return getFilterChain().resultSetMetaData_getPrecision(metaData, column);
    }

    public int resultSetMetaData_getScale(ResultSetMetaDataProxy metaData, int column) throws SQLException {
        return getFilterChain().resultSetMetaData_getScale(metaData, column);
    }

    public String resultSetMetaData_getTableName(ResultSetMetaDataProxy metaData, int column) throws SQLException {
        return getFilterChain().resultSetMetaData_getTableName(metaData, column);
    }

    public String resultSetMetaData_getCatalogName(ResultSetMetaDataProxy metaData, int column) throws SQLException {
        return getFilterChain().resultSetMetaData_getCatalogName(metaData, column);
    }

    public int resultSetMetaData_getColumnType(ResultSetMetaDataProxy metaData, int column) throws SQLException {
        return getFilterChain().resultSetMetaData_getColumnType(metaData, column);
    }

    public String resultSetMetaData_getColumnTypeName(ResultSetMetaDataProxy metaData, int column) throws SQLException {
        return getFilterChain().resultSetMetaData_getColumnTypeName(metaData, column);
    }

    public boolean resultSetMetaData_isReadOnly(ResultSetMetaDataProxy metaData, int column) throws SQLException {
        return getFilterChain().resultSetMetaData_isReadOnly(metaData, column);
    }

    public boolean resultSetMetaData_isWritable(ResultSetMetaDataProxy metaData, int column) throws SQLException {
        return getFilterChain().resultSetMetaData_isWritable(metaData, column);
    }

    public boolean resultSetMetaData_isDefinitelyWritable(ResultSetMetaDataProxy metaData, int column) throws SQLException {
        return getFilterChain().resultSetMetaData_isDefinitelyWritable(metaData, column);
    }

    public String resultSetMetaData_getColumnClassName(ResultSetMetaDataProxy metaData, int column) throws SQLException {
        return getFilterChain().resultSetMetaData_getColumnClassName(metaData, column);
    }
}