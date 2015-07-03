package cn.limw.summer.java.sql.wrapper;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import cn.limw.summer.util.Asserts;

/**
 * @author li
 * @version 1 (2014年9月29日 下午2:05:00)
 * @since Java7
 */
public class CallableStatementWrapper extends PreparedStatementWrapper implements CallableStatement {
    private CallableStatement callableStatement;

    public CallableStatementWrapper() {}

    public CallableStatementWrapper(CallableStatement callableStatement) {
        setCallableStatement(callableStatement);
    }

    public CallableStatementWrapper setCallableStatement(CallableStatement callableStatement) {
        this.callableStatement = callableStatement;
        super.setPreparedStatement(callableStatement);
        return this;
    }

    public CallableStatement getCallableStatement() {
        return Asserts.noNull(this.callableStatement, "被代理的CallableStatement为空");
    }

    public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
        getCallableStatement().registerOutParameter(parameterIndex, sqlType);
    }

    public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws SQLException {
        getCallableStatement().registerOutParameter(parameterIndex, sqlType, scale);
    }

    public boolean wasNull() throws SQLException {
        return getCallableStatement().wasNull();
    }

    public String getString(int parameterIndex) throws SQLException {
        return getCallableStatement().getString(parameterIndex);
    }

    public boolean getBoolean(int parameterIndex) throws SQLException {
        return getCallableStatement().getBoolean(parameterIndex);
    }

    public byte getByte(int parameterIndex) throws SQLException {
        return getCallableStatement().getByte(parameterIndex);
    }

    public short getShort(int parameterIndex) throws SQLException {
        return getCallableStatement().getShort(parameterIndex);
    }

    public int getInt(int parameterIndex) throws SQLException {
        return getCallableStatement().getInt(parameterIndex);
    }

    public long getLong(int parameterIndex) throws SQLException {
        return getCallableStatement().getLong(parameterIndex);
    }

    public float getFloat(int parameterIndex) throws SQLException {
        return getCallableStatement().getFloat(parameterIndex);
    }

    public double getDouble(int parameterIndex) throws SQLException {
        return getCallableStatement().getDouble(parameterIndex);
    }

    public BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException {
        return getCallableStatement().getBigDecimal(parameterIndex);
    }

    public byte[] getBytes(int parameterIndex) throws SQLException {
        return getCallableStatement().getBytes(parameterIndex);
    }

    public Date getDate(int parameterIndex) throws SQLException {
        return getCallableStatement().getDate(parameterIndex);
    }

    public Time getTime(int parameterIndex) throws SQLException {
        return getCallableStatement().getTime(parameterIndex);
    }

    public Timestamp getTimestamp(int parameterIndex) throws SQLException {
        return getCallableStatement().getTimestamp(parameterIndex);
    }

    public Object getObject(int parameterIndex) throws SQLException {
        return getCallableStatement().getObject(parameterIndex);
    }

    public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
        return getCallableStatement().getBigDecimal(parameterIndex);
    }

    public Object getObject(int parameterIndex, Map<String, Class<?>> map) throws SQLException {
        return getCallableStatement().getObject(parameterIndex, map);
    }

    public Ref getRef(int parameterIndex) throws SQLException {
        return getCallableStatement().getRef(parameterIndex);
    }

    public Blob getBlob(int parameterIndex) throws SQLException {
        return getCallableStatement().getBlob(parameterIndex);
    }

    public Clob getClob(int parameterIndex) throws SQLException {
        return getCallableStatement().getClob(parameterIndex);
    }

    public Array getArray(int parameterIndex) throws SQLException {
        return getCallableStatement().getArray(parameterIndex);
    }

    public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
        return getCallableStatement().getDate(parameterIndex, cal);
    }

    public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
        return getCallableStatement().getTime(parameterIndex, cal);
    }

    public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
        return getCallableStatement().getTimestamp(parameterIndex, cal);
    }

    public void registerOutParameter(int parameterIndex, int sqlType, String typeName) throws SQLException {
        getCallableStatement().registerOutParameter(parameterIndex, sqlType, typeName);
    }

    public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
        getCallableStatement().registerOutParameter(parameterName, sqlType);
    }

    public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
        getCallableStatement().registerOutParameter(parameterName, sqlType, scale);
    }

    public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
        getCallableStatement().registerOutParameter(parameterName, sqlType, typeName);
    }

    public URL getURL(int parameterIndex) throws SQLException {
        return getCallableStatement().getURL(parameterIndex);
    }

    public void setURL(String parameterName, URL val) throws SQLException {
        getCallableStatement().setURL(parameterName, val);
    }

    public void setNull(String parameterName, int sqlType) throws SQLException {
        getCallableStatement().setNull(parameterName, sqlType);
    }

    public void setBoolean(String parameterName, boolean x) throws SQLException {
        getCallableStatement().setBoolean(parameterName, x);
    }

    public void setByte(String parameterName, byte x) throws SQLException {
        getCallableStatement().setByte(parameterName, x);
    }

    public void setShort(String parameterName, short x) throws SQLException {
        getCallableStatement().setShort(parameterName, x);
    }

    public void setInt(String parameterName, int x) throws SQLException {
        getCallableStatement().setInt(parameterName, x);
    }

    public void setLong(String parameterName, long x) throws SQLException {
        getCallableStatement().setLong(parameterName, x);
    }

    public void setFloat(String parameterName, float x) throws SQLException {
        getCallableStatement().setFloat(parameterName, x);
    }

    public void setDouble(String parameterName, double x) throws SQLException {
        getCallableStatement().setDouble(parameterName, x);
    }

    public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
        getCallableStatement().setBigDecimal(parameterName, x);
    }

    public void setString(String parameterName, String x) throws SQLException {
        getCallableStatement().setString(parameterName, x);
    }

    public void setBytes(String parameterName, byte[] x) throws SQLException {
        getCallableStatement().setBytes(parameterName, x);
    }

    public void setDate(String parameterName, Date x) throws SQLException {
        getCallableStatement().setDate(parameterName, x);
    }

    public void setTime(String parameterName, Time x) throws SQLException {
        getCallableStatement().setTime(parameterName, x);
    }

    public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
        getCallableStatement().setTimestamp(parameterName, x);
    }

    public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
        getCallableStatement().setAsciiStream(parameterName, x, length);
    }

    public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
        getCallableStatement().setBinaryStream(parameterName, x, length);
    }

    public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
        getCallableStatement().setObject(parameterName, x, targetSqlType, scale);
    }

    public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
        getCallableStatement().setObject(parameterName, x, targetSqlType);
    }

    public void setObject(String parameterName, Object x) throws SQLException {
        getCallableStatement().setObject(parameterName, x);
    }

    public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
        getCallableStatement().setCharacterStream(parameterName, reader, length);
    }

    public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
        getCallableStatement().setDate(parameterName, x, cal);
    }

    public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
        getCallableStatement().setTime(parameterName, x, cal);
    }

    public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
        getCallableStatement().setTimestamp(parameterName, x, cal);
    }

    public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
        getCallableStatement().setNull(parameterName, sqlType, typeName);
    }

    public String getString(String parameterName) throws SQLException {
        return getCallableStatement().getString(parameterName);
    }

    public boolean getBoolean(String parameterName) throws SQLException {
        return getCallableStatement().getBoolean(parameterName);
    }

    public byte getByte(String parameterName) throws SQLException {
        return getCallableStatement().getByte(parameterName);
    }

    public short getShort(String parameterName) throws SQLException {
        return getCallableStatement().getShort(parameterName);
    }

    public int getInt(String parameterName) throws SQLException {
        return getCallableStatement().getInt(parameterName);
    }

    public long getLong(String parameterName) throws SQLException {
        return getCallableStatement().getLong(parameterName);
    }

    public float getFloat(String parameterName) throws SQLException {
        return getCallableStatement().getFloat(parameterName);
    }

    public double getDouble(String parameterName) throws SQLException {
        return getCallableStatement().getDouble(parameterName);
    }

    public byte[] getBytes(String parameterName) throws SQLException {
        return getCallableStatement().getBytes(parameterName);
    }

    public Date getDate(String parameterName) throws SQLException {
        return getCallableStatement().getDate(parameterName);
    }

    public Time getTime(String parameterName) throws SQLException {
        return getCallableStatement().getTime(parameterName);
    }

    public Timestamp getTimestamp(String parameterName) throws SQLException {
        return getCallableStatement().getTimestamp(parameterName);
    }

    public Object getObject(String parameterName) throws SQLException {
        return getCallableStatement().getObject(parameterName);
    }

    public BigDecimal getBigDecimal(String parameterName) throws SQLException {
        return getCallableStatement().getBigDecimal(parameterName);
    }

    public Object getObject(String parameterName, Map<String, Class<?>> map) throws SQLException {
        return getCallableStatement().getObject(parameterName, map);
    }

    public Ref getRef(String parameterName) throws SQLException {
        return getCallableStatement().getRef(parameterName);
    }

    public Blob getBlob(String parameterName) throws SQLException {
        return getCallableStatement().getBlob(parameterName);
    }

    public Clob getClob(String parameterName) throws SQLException {
        return getCallableStatement().getClob(parameterName);
    }

    public Array getArray(String parameterName) throws SQLException {
        return getCallableStatement().getArray(parameterName);
    }

    public Date getDate(String parameterName, Calendar cal) throws SQLException {
        return getCallableStatement().getDate(parameterName, cal);
    }

    public Time getTime(String parameterName, Calendar cal) throws SQLException {
        return getCallableStatement().getTime(parameterName, cal);
    }

    public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
        return getCallableStatement().getTimestamp(parameterName, cal);
    }

    public URL getURL(String parameterName) throws SQLException {
        return getCallableStatement().getURL(parameterName);
    }

    public RowId getRowId(int parameterIndex) throws SQLException {
        return getCallableStatement().getRowId(parameterIndex);
    }

    public RowId getRowId(String parameterName) throws SQLException {
        return getCallableStatement().getRowId(parameterName);
    }

    public void setRowId(String parameterName, RowId x) throws SQLException {
        getCallableStatement().setRowId(parameterName, x);
    }

    public void setNString(String parameterName, String value) throws SQLException {
        getCallableStatement().setNString(parameterName, value);
    }

    public void setNCharacterStream(String parameterName, Reader value, long length) throws SQLException {
        getCallableStatement().setNCharacterStream(parameterName, value, length);
    }

    public void setNClob(String parameterName, NClob value) throws SQLException {
        getCallableStatement().setNClob(parameterName, value);
    }

    public void setClob(String parameterName, Reader reader, long length) throws SQLException {
        getCallableStatement().setClob(parameterName, reader, length);
    }

    public void setBlob(String parameterName, InputStream inputStream, long length) throws SQLException {
        getCallableStatement().setBlob(parameterName, inputStream, length);
    }

    public void setNClob(String parameterName, Reader reader, long length) throws SQLException {
        getCallableStatement().setNClob(parameterName, reader, length);
    }

    public NClob getNClob(int parameterIndex) throws SQLException {
        return getCallableStatement().getNClob(parameterIndex);
    }

    public NClob getNClob(String parameterName) throws SQLException {
        return getCallableStatement().getNClob(parameterName);
    }

    public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {
        getCallableStatement().setSQLXML(parameterName, xmlObject);
    }

    public SQLXML getSQLXML(int parameterIndex) throws SQLException {
        return getCallableStatement().getSQLXML(parameterIndex);
    }

    public SQLXML getSQLXML(String parameterName) throws SQLException {
        return getCallableStatement().getSQLXML(parameterName);
    }

    public String getNString(int parameterIndex) throws SQLException {
        return getCallableStatement().getNString(parameterIndex);
    }

    public String getNString(String parameterName) throws SQLException {
        return getCallableStatement().getNString(parameterName);
    }

    public Reader getNCharacterStream(int parameterIndex) throws SQLException {
        return getCallableStatement().getNCharacterStream(parameterIndex);
    }

    public Reader getNCharacterStream(String parameterName) throws SQLException {
        return getCallableStatement().getNCharacterStream(parameterName);
    }

    public Reader getCharacterStream(int parameterIndex) throws SQLException {
        return getCallableStatement().getCharacterStream(parameterIndex);
    }

    public Reader getCharacterStream(String parameterName) throws SQLException {
        return getCallableStatement().getCharacterStream(parameterName);
    }

    public void setBlob(String parameterName, Blob x) throws SQLException {
        getCallableStatement().setBlob(parameterName, x);
    }

    public void setClob(String parameterName, Clob x) throws SQLException {
        getCallableStatement().setClob(parameterName, x);
    }

    public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {
        getCallableStatement().setAsciiStream(parameterName, x, length);
    }

    public void setBinaryStream(String parameterName, InputStream x, long length) throws SQLException {
        getCallableStatement().setBinaryStream(parameterName, x, length);
    }

    public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
        getCallableStatement().setCharacterStream(parameterName, reader, length);
    }

    public void setAsciiStream(String parameterName, InputStream x) throws SQLException {
        getCallableStatement().setAsciiStream(parameterName, x);
    }

    public void setBinaryStream(String parameterName, InputStream x) throws SQLException {
        getCallableStatement().setBinaryStream(parameterName, x);
    }

    public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
        getCallableStatement().setCharacterStream(parameterName, reader);
    }

    public void setNCharacterStream(String parameterName, Reader value) throws SQLException {
        getCallableStatement().setNCharacterStream(parameterName, value);
    }

    public void setClob(String parameterName, Reader reader) throws SQLException {
        getCallableStatement().setClob(parameterName, reader);
    }

    public void setBlob(String parameterName, InputStream inputStream) throws SQLException {
        getCallableStatement().setBlob(parameterName, inputStream);
    }

    public void setNClob(String parameterName, Reader reader) throws SQLException {
        getCallableStatement().setNClob(parameterName, reader);
    }

    public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
        return getCallableStatement().getObject(parameterIndex, type);
    }

    public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
        return getCallableStatement().getObject(parameterName, type);
    }
}