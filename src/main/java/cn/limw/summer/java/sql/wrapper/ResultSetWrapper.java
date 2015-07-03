package cn.limw.summer.java.sql.wrapper;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import cn.limw.summer.util.Asserts;

/**
 * @author li
 * @version 1 (2014年9月29日 下午2:02:34)
 * @since Java7
 */
public class ResultSetWrapper extends WrapperWrapper implements ResultSet {
    private ResultSet resultSet;

    public ResultSetWrapper() {}

    public ResultSetWrapper(ResultSet resultSet) {
        setResultSet(resultSet);
    }

    public ResultSetWrapper setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
        super.setWrapper(resultSet);
        return this;
    }

    public ResultSet getResultSet() {
        return Asserts.noNull(this.resultSet);
    }

    public boolean next() throws SQLException {
        return getResultSet().next();
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        return getResultSet().getMetaData();
    }

    public void close() throws SQLException {
        getResultSet().close();
    }

    public boolean wasNull() throws SQLException {
        return getResultSet().wasNull();
    }

    public String getString(int columnIndex) throws SQLException {
        return getResultSet().getString(columnIndex);
    }

    public boolean getBoolean(int columnIndex) throws SQLException {
        return getResultSet().getBoolean(columnIndex);
    }

    public byte getByte(int columnIndex) throws SQLException {
        return getResultSet().getByte(columnIndex);
    }

    public short getShort(int columnIndex) throws SQLException {
        return getResultSet().getShort(columnIndex);
    }

    public int getInt(int columnIndex) throws SQLException {
        return getResultSet().getInt(columnIndex);
    }

    public long getLong(int columnIndex) throws SQLException {
        return getResultSet().getLong(columnIndex);
    }

    public float getFloat(int columnIndex) throws SQLException {
        return getResultSet().getFloat(columnIndex);
    }

    public double getDouble(int columnIndex) throws SQLException {
        return getResultSet().getDouble(columnIndex);
    }

    @Deprecated
    public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
        return getResultSet().getBigDecimal(columnIndex, scale);
    }

    public byte[] getBytes(int columnIndex) throws SQLException {
        return getResultSet().getBytes(columnIndex);
    }

    public Date getDate(int columnIndex) throws SQLException {
        return getResultSet().getDate(columnIndex);
    }

    public Time getTime(int columnIndex) throws SQLException {
        return getResultSet().getTime(columnIndex);
    }

    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        return getResultSet().getTimestamp(columnIndex);
    }

    public InputStream getAsciiStream(int columnIndex) throws SQLException {
        return getResultSet().getAsciiStream(columnIndex);
    }

    @Deprecated
    public InputStream getUnicodeStream(int columnIndex) throws SQLException {
        return getResultSet().getUnicodeStream(columnIndex);
    }

    public InputStream getBinaryStream(int columnIndex) throws SQLException {
        return getResultSet().getBinaryStream(columnIndex);
    }

    public String getString(String columnLabel) throws SQLException {
        return getResultSet().getString(columnLabel);
    }

    public boolean getBoolean(String columnLabel) throws SQLException {
        return getResultSet().getBoolean(columnLabel);
    }

    public byte getByte(String columnLabel) throws SQLException {
        return getResultSet().getByte(columnLabel);
    }

    public short getShort(String columnLabel) throws SQLException {
        return getResultSet().getShort(columnLabel);
    }

    public int getInt(String columnLabel) throws SQLException {
        return getResultSet().getInt(columnLabel);
    }

    public long getLong(String columnLabel) throws SQLException {
        return getResultSet().getLong(columnLabel);
    }

    public float getFloat(String columnLabel) throws SQLException {
        return getResultSet().getFloat(columnLabel);
    }

    public double getDouble(String columnLabel) throws SQLException {
        return getResultSet().getDouble(columnLabel);
    }

    @Deprecated
    public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
        return getResultSet().getBigDecimal(columnLabel, scale);
    }

    public byte[] getBytes(String columnLabel) throws SQLException {
        return getResultSet().getBytes(columnLabel);
    }

    public Date getDate(String columnLabel) throws SQLException {
        return getResultSet().getDate(columnLabel);
    }

    public Time getTime(String columnLabel) throws SQLException {
        return getResultSet().getTime(columnLabel);
    }

    public Timestamp getTimestamp(String columnLabel) throws SQLException {
        return getResultSet().getTimestamp(columnLabel);
    }

    public InputStream getAsciiStream(String columnLabel) throws SQLException {
        return getResultSet().getAsciiStream(columnLabel);
    }

    @Deprecated
    public InputStream getUnicodeStream(String columnLabel) throws SQLException {
        return getResultSet().getUnicodeStream(columnLabel);
    }

    public InputStream getBinaryStream(String columnLabel) throws SQLException {
        return getResultSet().getBinaryStream(columnLabel);
    }

    public SQLWarning getWarnings() throws SQLException {
        return getResultSet().getWarnings();
    }

    public void clearWarnings() throws SQLException {
        getResultSet().clearWarnings();
    }

    public String getCursorName() throws SQLException {
        return getResultSet().getCursorName();
    }

    public Object getObject(int columnIndex) throws SQLException {
        return getResultSet().getObject(columnIndex);
    }

    public Object getObject(String columnLabel) throws SQLException {
        return getResultSet().getObject(columnLabel);
    }

    public int findColumn(String columnLabel) throws SQLException {
        return getResultSet().findColumn(columnLabel);
    }

    public Reader getCharacterStream(int columnIndex) throws SQLException {
        return getResultSet().getCharacterStream(columnIndex);
    }

    public Reader getCharacterStream(String columnLabel) throws SQLException {
        return getResultSet().getCharacterStream(columnLabel);
    }

    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        return getResultSet().getBigDecimal(columnIndex);
    }

    public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
        return getResultSet().getBigDecimal(columnLabel);
    }

    public boolean isBeforeFirst() throws SQLException {
        return getResultSet().isBeforeFirst();
    }

    public boolean isAfterLast() throws SQLException {
        return getResultSet().isAfterLast();
    }

    public boolean isFirst() throws SQLException {
        return getResultSet().isFirst();
    }

    public boolean isLast() throws SQLException {
        return getResultSet().isLast();
    }

    public void beforeFirst() throws SQLException {
        getResultSet().beforeFirst();
    }

    public void afterLast() throws SQLException {
        getResultSet().afterLast();
    }

    public boolean first() throws SQLException {
        return getResultSet().first();
    }

    public boolean last() throws SQLException {
        return getResultSet().last();
    }

    public int getRow() throws SQLException {
        return getResultSet().getRow();
    }

    public boolean absolute(int row) throws SQLException {
        return getResultSet().absolute(row);
    }

    public boolean relative(int rows) throws SQLException {
        return getResultSet().relative(rows);
    }

    public boolean previous() throws SQLException {
        return getResultSet().previous();
    }

    public void setFetchDirection(int direction) throws SQLException {
        getResultSet().setFetchDirection(direction);
    }

    public int getFetchDirection() throws SQLException {
        return getResultSet().getFetchDirection();
    }

    public void setFetchSize(int rows) throws SQLException {
        getResultSet().setFetchSize(rows);
    }

    public int getFetchSize() throws SQLException {
        return getResultSet().getFetchSize();
    }

    public int getType() throws SQLException {
        return getResultSet().getType();
    }

    public int getConcurrency() throws SQLException {
        return getResultSet().getConcurrency();
    }

    public boolean rowUpdated() throws SQLException {
        return getResultSet().rowUpdated();
    }

    public boolean rowInserted() throws SQLException {
        return getResultSet().rowInserted();
    }

    public boolean rowDeleted() throws SQLException {
        return getResultSet().rowDeleted();
    }

    public void updateNull(int columnIndex) throws SQLException {
        getResultSet().updateNull(columnIndex);
    }

    public void updateBoolean(int columnIndex, boolean x) throws SQLException {
        getResultSet().updateBoolean(columnIndex, x);
    }

    public void updateByte(int columnIndex, byte x) throws SQLException {
        getResultSet().updateByte(columnIndex, x);
    }

    public void updateShort(int columnIndex, short x) throws SQLException {
        getResultSet().updateShort(columnIndex, x);
    }

    public void updateInt(int columnIndex, int x) throws SQLException {
        getResultSet().updateInt(columnIndex, x);
    }

    public void updateLong(int columnIndex, long x) throws SQLException {
        getResultSet().updateLong(columnIndex, x);
    }

    public void updateFloat(int columnIndex, float x) throws SQLException {
        getResultSet().updateFloat(columnIndex, x);
    }

    public void updateDouble(int columnIndex, double x) throws SQLException {
        getResultSet().updateDouble(columnIndex, x);
    }

    public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
        getResultSet().updateBigDecimal(columnIndex, x);
    }

    public void updateString(int columnIndex, String x) throws SQLException {
        getResultSet().updateString(columnIndex, x);
    }

    public void updateBytes(int columnIndex, byte[] x) throws SQLException {
        getResultSet().updateBytes(columnIndex, x);
    }

    public void updateDate(int columnIndex, Date x) throws SQLException {
        getResultSet().updateDate(columnIndex, x);
    }

    public void updateTime(int columnIndex, Time x) throws SQLException {
        getResultSet().updateTime(columnIndex, x);
    }

    public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
        getResultSet().updateTimestamp(columnIndex, x);
    }

    public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
        getResultSet().updateAsciiStream(columnIndex, x, length);
    }

    public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
        getResultSet().updateBinaryStream(columnIndex, x, length);
    }

    public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
        getResultSet().updateCharacterStream(columnIndex, x, length);
    }

    public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
        getResultSet().updateObject(columnIndex, x, scaleOrLength);
    }

    public void updateObject(int columnIndex, Object x) throws SQLException {
        getResultSet().updateObject(columnIndex, x);
    }

    public void updateNull(String columnLabel) throws SQLException {
        getResultSet().updateNull(columnLabel);
    }

    public void updateBoolean(String columnLabel, boolean x) throws SQLException {
        getResultSet().updateBoolean(columnLabel, x);
    }

    public void updateByte(String columnLabel, byte x) throws SQLException {
        getResultSet().updateByte(columnLabel, x);
    }

    public void updateShort(String columnLabel, short x) throws SQLException {
        getResultSet().updateShort(columnLabel, x);
    }

    public void updateInt(String columnLabel, int x) throws SQLException {
        getResultSet().updateInt(columnLabel, x);
    }

    public void updateLong(String columnLabel, long x) throws SQLException {
        getResultSet().updateLong(columnLabel, x);
    }

    public void updateFloat(String columnLabel, float x) throws SQLException {
        getResultSet().updateFloat(columnLabel, x);
    }

    public void updateDouble(String columnLabel, double x) throws SQLException {
        getResultSet().updateDouble(columnLabel, x);
    }

    public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
        getResultSet().updateBigDecimal(columnLabel, x);
    }

    public void updateString(String columnLabel, String x) throws SQLException {
        getResultSet().updateString(columnLabel, x);
    }

    public void updateBytes(String columnLabel, byte[] x) throws SQLException {
        getResultSet().updateBytes(columnLabel, x);
    }

    public void updateDate(String columnLabel, Date x) throws SQLException {
        getResultSet().updateDate(columnLabel, x);
    }

    public void updateTime(String columnLabel, Time x) throws SQLException {
        getResultSet().updateTime(columnLabel, x);
    }

    public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
        getResultSet().updateTimestamp(columnLabel, x);
    }

    public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
        getResultSet().updateAsciiStream(columnLabel, x, length);
    }

    public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
        getResultSet().updateBinaryStream(columnLabel, x, length);
    }

    public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
        getResultSet().updateCharacterStream(columnLabel, reader, length);
    }

    public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
        getResultSet().updateObject(columnLabel, x, scaleOrLength);
    }

    public void updateObject(String columnLabel, Object x) throws SQLException {
        getResultSet().updateObject(columnLabel, x);
    }

    public void insertRow() throws SQLException {
        getResultSet().insertRow();
    }

    public void updateRow() throws SQLException {
        getResultSet().updateRow();
    }

    public void deleteRow() throws SQLException {
        getResultSet().deleteRow();
    }

    public void refreshRow() throws SQLException {
        getResultSet().refreshRow();
    }

    public void cancelRowUpdates() throws SQLException {
        getResultSet().cancelRowUpdates();
    }

    public void moveToInsertRow() throws SQLException {
        getResultSet().moveToInsertRow();
    }

    public void moveToCurrentRow() throws SQLException {
        getResultSet().moveToCurrentRow();
    }

    public Statement getStatement() throws SQLException {
        return getResultSet().getStatement();
    }

    public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
        return getResultSet().getObject(columnIndex, map);
    }

    public Ref getRef(int columnIndex) throws SQLException {
        return getResultSet().getRef(columnIndex);
    }

    public Blob getBlob(int columnIndex) throws SQLException {
        return getResultSet().getBlob(columnIndex);
    }

    public Clob getClob(int columnIndex) throws SQLException {
        return getResultSet().getClob(columnIndex);
    }

    public Array getArray(int columnIndex) throws SQLException {
        return getResultSet().getArray(columnIndex);
    }

    public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
        return getResultSet().getObject(columnLabel, map);
    }

    public Ref getRef(String columnLabel) throws SQLException {
        return getResultSet().getRef(columnLabel);
    }

    public Blob getBlob(String columnLabel) throws SQLException {
        return getResultSet().getBlob(columnLabel);
    }

    public Clob getClob(String columnLabel) throws SQLException {
        return getResultSet().getClob(columnLabel);
    }

    public Array getArray(String columnLabel) throws SQLException {
        return getResultSet().getArray(columnLabel);
    }

    public Date getDate(int columnIndex, Calendar cal) throws SQLException {
        return getResultSet().getDate(columnIndex, cal);
    }

    public Date getDate(String columnLabel, Calendar cal) throws SQLException {
        return getResultSet().getDate(columnLabel, cal);
    }

    public Time getTime(int columnIndex, Calendar cal) throws SQLException {
        return getResultSet().getTime(columnIndex, cal);
    }

    public Time getTime(String columnLabel, Calendar cal) throws SQLException {
        return getResultSet().getTime(columnLabel, cal);
    }

    public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
        return getResultSet().getTimestamp(columnIndex, cal);
    }

    public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
        return getResultSet().getTimestamp(columnLabel, cal);
    }

    public URL getURL(int columnIndex) throws SQLException {
        return getResultSet().getURL(columnIndex);
    }

    public URL getURL(String columnLabel) throws SQLException {
        return getResultSet().getURL(columnLabel);
    }

    public void updateRef(int columnIndex, Ref x) throws SQLException {
        getResultSet().updateRef(columnIndex, x);
    }

    public void updateRef(String columnLabel, Ref x) throws SQLException {
        getResultSet().updateRef(columnLabel, x);
    }

    public void updateBlob(int columnIndex, Blob x) throws SQLException {
        getResultSet().updateBlob(columnIndex, x);
    }

    public void updateBlob(String columnLabel, Blob x) throws SQLException {
        getResultSet().updateBlob(columnLabel, x);
    }

    public void updateClob(int columnIndex, Clob x) throws SQLException {
        getResultSet().updateClob(columnIndex, x);
    }

    public void updateClob(String columnLabel, Clob x) throws SQLException {
        getResultSet().updateClob(columnLabel, x);
    }

    public void updateArray(int columnIndex, Array x) throws SQLException {
        getResultSet().updateArray(columnIndex, x);
    }

    public void updateArray(String columnLabel, Array x) throws SQLException {
        getResultSet().updateArray(columnLabel, x);
    }

    public RowId getRowId(int columnIndex) throws SQLException {
        return getResultSet().getRowId(columnIndex);
    }

    public RowId getRowId(String columnLabel) throws SQLException {
        return getResultSet().getRowId(columnLabel);
    }

    public void updateRowId(int columnIndex, RowId x) throws SQLException {
        getResultSet().updateRowId(columnIndex, x);
    }

    public void updateRowId(String columnLabel, RowId x) throws SQLException {
        getResultSet().updateRowId(columnLabel, x);
    }

    public int getHoldability() throws SQLException {
        return getResultSet().getHoldability();
    }

    public boolean isClosed() throws SQLException {
        return getResultSet().isClosed();
    }

    public void updateNString(int columnIndex, String nString) throws SQLException {
        getResultSet().updateNString(columnIndex, nString);
    }

    public void updateNString(String columnLabel, String nString) throws SQLException {
        getResultSet().updateNString(columnLabel, nString);
    }

    public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
        getResultSet().updateNClob(columnIndex, nClob);
    }

    public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
        getResultSet().updateNClob(columnLabel, nClob);
    }

    public NClob getNClob(int columnIndex) throws SQLException {
        return getResultSet().getNClob(columnIndex);
    }

    public NClob getNClob(String columnLabel) throws SQLException {
        return getResultSet().getNClob(columnLabel);
    }

    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        return getResultSet().getSQLXML(columnIndex);
    }

    public SQLXML getSQLXML(String columnLabel) throws SQLException {
        return getResultSet().getSQLXML(columnLabel);
    }

    public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
        getResultSet().updateSQLXML(columnIndex, xmlObject);
    }

    public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
        getResultSet().updateSQLXML(columnLabel, xmlObject);
    }

    public String getNString(int columnIndex) throws SQLException {
        return getResultSet().getNString(columnIndex);
    }

    public String getNString(String columnLabel) throws SQLException {
        return getResultSet().getNString(columnLabel);
    }

    public Reader getNCharacterStream(int columnIndex) throws SQLException {
        return getResultSet().getNCharacterStream(columnIndex);
    }

    public Reader getNCharacterStream(String columnLabel) throws SQLException {
        return getResultSet().getNCharacterStream(columnLabel);
    }

    public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        getResultSet().updateNCharacterStream(columnIndex, x, length);
    }

    public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        getResultSet().updateNCharacterStream(columnLabel, reader, length);
    }

    public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
        getResultSet().updateAsciiStream(columnIndex, x, length);
    }

    public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
        getResultSet().updateBinaryStream(columnIndex, x, length);
    }

    public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        getResultSet().updateCharacterStream(columnIndex, x, length);
    }

    public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
        getResultSet().updateAsciiStream(columnLabel, x, length);
    }

    public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
        getResultSet().updateBinaryStream(columnLabel, x, length);
    }

    public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        getResultSet().updateCharacterStream(columnLabel, reader, length);
    }

    public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
        getResultSet().updateBlob(columnIndex, inputStream, length);
    }

    public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
        getResultSet().updateBlob(columnLabel, inputStream, length);
    }

    public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
        getResultSet().updateClob(columnIndex, reader, length);
    }

    public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
        getResultSet().updateClob(columnLabel, reader, length);
    }

    public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
        getResultSet().updateNClob(columnIndex, reader, length);
    }

    public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
        getResultSet().updateNClob(columnLabel, reader, length);
    }

    public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
        getResultSet().updateNCharacterStream(columnIndex, x);
    }

    public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
        getResultSet().updateNCharacterStream(columnLabel, reader);
    }

    public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
        getResultSet().updateAsciiStream(columnIndex, x);
    }

    public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
        getResultSet().updateBinaryStream(columnIndex, x);
    }

    public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
        getResultSet().updateCharacterStream(columnIndex, x);
    }

    public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
        getResultSet().updateAsciiStream(columnLabel, x);
    }

    public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
        getResultSet().updateBinaryStream(columnLabel, x);
    }

    public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
        getResultSet().updateCharacterStream(columnLabel, reader);
    }

    public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
        getResultSet().updateBlob(columnIndex, inputStream);
    }

    public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
        getResultSet().updateBlob(columnLabel, inputStream);
    }

    public void updateClob(int columnIndex, Reader reader) throws SQLException {
        getResultSet().updateClob(columnIndex, reader);
    }

    public void updateClob(String columnLabel, Reader reader) throws SQLException {
        getResultSet().updateClob(columnLabel, reader);
    }

    public void updateNClob(int columnIndex, Reader reader) throws SQLException {
        getResultSet().updateNClob(columnIndex, reader);
    }

    public void updateNClob(String columnLabel, Reader reader) throws SQLException {
        getResultSet().updateNClob(columnLabel, reader);
    }

    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        return getResultSet().getObject(columnIndex, type);
    }

    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        return getResultSet().getObject(columnLabel, type);
    }
}