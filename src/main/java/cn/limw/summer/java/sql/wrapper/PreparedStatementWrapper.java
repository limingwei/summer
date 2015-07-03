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
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

import cn.limw.summer.util.Asserts;

/**
 * @author li
 * @version 1 (2014年9月29日 下午2:04:14)
 * @since Java7
 */
public class PreparedStatementWrapper extends StatementWrapper implements PreparedStatement {
    private PreparedStatement preparedStatement;

    public PreparedStatementWrapper() {}

    public PreparedStatementWrapper(PreparedStatement prepareStatement) {
        setPreparedStatement(prepareStatement);
    }

    public PreparedStatementWrapper setPreparedStatement(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
        super.setStatement(preparedStatement);
        return this;
    }

    public PreparedStatement getPreparedStatement() {
        return Asserts.noNull(this.preparedStatement, "被代理的PreparedStatement为空");
    }

    public ResultSet executeQuery() throws SQLException {
        return new ResultSetWrapper(getPreparedStatement().executeQuery());
    }

    public int executeUpdate() throws SQLException {
        return getPreparedStatement().executeUpdate();
    }

    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        getPreparedStatement().setNull(parameterIndex, sqlType);
    }

    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        getPreparedStatement().setBoolean(parameterIndex, x);
    }

    public void setByte(int parameterIndex, byte x) throws SQLException {
        getPreparedStatement().setByte(parameterIndex, x);
    }

    public void setShort(int parameterIndex, short x) throws SQLException {
        getPreparedStatement().setShort(parameterIndex, x);
    }

    public void setInt(int parameterIndex, int x) throws SQLException {
        getPreparedStatement().setInt(parameterIndex, x);
    }

    public void setLong(int parameterIndex, long x) throws SQLException {
        getPreparedStatement().setLong(parameterIndex, x);
    }

    public void setFloat(int parameterIndex, float x) throws SQLException {
        getPreparedStatement().setFloat(parameterIndex, x);
    }

    public void setDouble(int parameterIndex, double x) throws SQLException {
        getPreparedStatement().setDouble(parameterIndex, x);
    }

    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        getPreparedStatement().setBigDecimal(parameterIndex, x);
    }

    public void setString(int parameterIndex, String x) throws SQLException {
        getPreparedStatement().setString(parameterIndex, x);
    }

    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        getPreparedStatement().setBytes(parameterIndex, x);
    }

    public void setDate(int parameterIndex, Date x) throws SQLException {
        getPreparedStatement().setDate(parameterIndex, x);
    }

    public void setTime(int parameterIndex, Time x) throws SQLException {
        getPreparedStatement().setTime(parameterIndex, x);
    }

    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        getPreparedStatement().setTimestamp(parameterIndex, x);
    }

    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        getPreparedStatement().setAsciiStream(parameterIndex, x, length);
    }

    @Deprecated
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        getPreparedStatement().setUnicodeStream(parameterIndex, x, length);
    }

    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        getPreparedStatement().setBinaryStream(parameterIndex, x, length);
    }

    public void clearParameters() throws SQLException {
        getPreparedStatement().clearParameters();
    }

    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        getPreparedStatement().setObject(parameterIndex, x, targetSqlType);
    }

    public void setObject(int parameterIndex, Object x) throws SQLException {
        getPreparedStatement().setObject(parameterIndex, x);
    }

    public boolean execute() throws SQLException {
        return getPreparedStatement().execute();
    }

    public void addBatch() throws SQLException {
        getPreparedStatement().addBatch();
    }

    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        getPreparedStatement().setCharacterStream(parameterIndex, reader, length);
    }

    public void setRef(int parameterIndex, Ref x) throws SQLException {
        getPreparedStatement().setRef(parameterIndex, x);
    }

    public void setBlob(int parameterIndex, Blob x) throws SQLException {
        getPreparedStatement().setBlob(parameterIndex, x);
    }

    public void setClob(int parameterIndex, Clob x) throws SQLException {
        getPreparedStatement().setClob(parameterIndex, x);
    }

    public void setArray(int parameterIndex, Array x) throws SQLException {
        getPreparedStatement().setArray(parameterIndex, x);
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        return getPreparedStatement().getMetaData();
    }

    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        getPreparedStatement().setDate(parameterIndex, x, cal);
    }

    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        getPreparedStatement().setTime(parameterIndex, x, cal);
    }

    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        getPreparedStatement().setTimestamp(parameterIndex, x, cal);
    }

    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
        getPreparedStatement().setNull(parameterIndex, sqlType, typeName);
    }

    public void setURL(int parameterIndex, URL x) throws SQLException {
        getPreparedStatement().setURL(parameterIndex, x);
    }

    public ParameterMetaData getParameterMetaData() throws SQLException {
        return getPreparedStatement().getParameterMetaData();
    }

    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        getPreparedStatement().setRowId(parameterIndex, x);
    }

    public void setNString(int parameterIndex, String value) throws SQLException {
        getPreparedStatement().setNString(parameterIndex, value);
    }

    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        getPreparedStatement().setNClob(parameterIndex, value, length);
    }

    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        getPreparedStatement().setNClob(parameterIndex, value);
    }

    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        getPreparedStatement().setClob(parameterIndex, reader);
    }

    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        getPreparedStatement().setBlob(parameterIndex, inputStream, length);
    }

    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        getPreparedStatement().setNClob(parameterIndex, reader, length);
    }

    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        getPreparedStatement().setSQLXML(parameterIndex, xmlObject);
    }

    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
        getPreparedStatement().setObject(parameterIndex, x, targetSqlType, scaleOrLength);
    }

    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        getPreparedStatement().setAsciiStream(parameterIndex, x, length);
    }

    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        getPreparedStatement().setBinaryStream(parameterIndex, x, length);
    }

    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        getPreparedStatement().setCharacterStream(parameterIndex, reader, length);
    }

    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        getPreparedStatement().setAsciiStream(parameterIndex, x);
    }

    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        getPreparedStatement().setBinaryStream(parameterIndex, x);
    }

    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        getPreparedStatement().setCharacterStream(parameterIndex, reader);
    }

    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        getPreparedStatement().setNCharacterStream(parameterIndex, value);
    }

    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        getPreparedStatement().setClob(parameterIndex, reader);
    }

    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        getPreparedStatement().setBlob(parameterIndex, inputStream);
    }

    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        getPreparedStatement().setNClob(parameterIndex, reader);
    }
}