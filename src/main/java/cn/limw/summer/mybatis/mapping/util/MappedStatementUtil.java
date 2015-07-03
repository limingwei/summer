package cn.limw.summer.mybatis.mapping.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

/**
 * @author li
 * @version 1 (2015年5月20日 下午3:36:42)
 * @since Java7
 */
public class MappedStatementUtil {
    private static final String STATEMENT_TEMPLATE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">"
            + "<mapper namespace=\"common\">"
            + "<select id=\"{{STATEMENT_ID}}\" resultType=\"map\">{{SQL}}</select>"
            + "</mapper>";

    public static void insureStatement(SqlSession sqlSession, String sql, String statementId) {
        if (!MappedStatementUtil.hasStatement(sqlSession, statementId)) {
            synchronizedInsureStatement(sqlSession, sql, statementId);
        }
    }

    private synchronized static void synchronizedInsureStatement(SqlSession sqlSession, String sql, String statementId) {
        if (!MappedStatementUtil.hasStatement(sqlSession, statementId)) {
            MappedStatementUtil.addStatement(sqlSession, sql);
        }
    }

    public static void addStatement(SqlSession sqlSession, String sql) {
        String xml = STATEMENT_TEMPLATE.replace("{{STATEMENT_ID}}", sqlToStatementId(sql)).replace("{{SQL}}", sql);
        InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
        Configuration configuration = sqlSession.getConfiguration();
        String resource = sql;

        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(inputStream, configuration, resource, configuration.getSqlFragments());
        xmlMapperBuilder.parse();
    }

    public static Boolean hasStatement(SqlSession sqlSession, String statementId) {
        return sqlSession.getConfiguration().hasStatement(statementId);
    }

    public static String sqlToStatementId(String sql) {
        return sql.replace(".", "_dot_");
    }
}