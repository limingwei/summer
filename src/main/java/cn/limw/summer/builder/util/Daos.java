package cn.limw.summer.builder.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cn.limw.summer.builder.model.Field;

/**
 * Daos
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年1月8日 下午6:04:14)
 */
public class Daos {
    /**
     * 通过查询系统表返回一个表的所有列
     */
    public static List<Field> getFieldsOfTable(Connection connection, String table) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            List<Field> fields = new ArrayList<Field>();
            String sql = " SELECT * FROM information_schema.columns WHERE table_schema='" + connection.getCatalog() + "' AND table_name='" + table + "' ";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                fields.add(new Field().set("column", resultSet.getString("COLUMN_NAME"))//
                        .set("comment", resultSet.getString("COLUMN_COMMENT"))//
                        .set("key", resultSet.getString("COLUMN_KEY"))//
                        .set("type", resultSet.getString("COLUMN_TYPE")));
            }
            return fields;
        } catch (Exception e) {
            throw Errors.wrap(e);
        } finally {
            close(resultSet, preparedStatement, connection);
        }
    }

    public static void close(AutoCloseable... closeables) {
        try {
            for (AutoCloseable closeable : closeables) {
                closeable.close();
            }
        } catch (Exception e) {
            throw Errors.wrap(e);
        }
    }

    public static Connection getConnection(String url, String user, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw Errors.wrap(e);
        }
    }
}