package cn.limw.summer.fastjson.parser.deserializer;

import java.lang.reflect.Type;

import cn.limw.summer.time.Clock;
import cn.limw.summer.time.DateFormatPool;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.TimeDeserializer;

/**
 * @author li
 * @version 1 (2015年1月29日 下午2:03:22)
 * @since Java7
 */
public class SqlTimeDeserializer extends TimeDeserializer {
    public final static SqlTimeDeserializer INSTANCE = new SqlTimeDeserializer();

    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) {
        JSONLexer lexer = parser.getLexer();
        if (lexer.token() == JSONToken.COMMA) {
            lexer.nextToken(JSONToken.LITERAL_STRING);

            if (lexer.token() != JSONToken.LITERAL_STRING) {
                throw new JSONException("syntax error");
            }

            lexer.nextTokenWithColon(JSONToken.LITERAL_INT);

            if (lexer.token() != JSONToken.LITERAL_INT) {
                throw new JSONException("syntax error");
            }

            long time = lexer.longValue();
            lexer.nextToken(JSONToken.RBRACE);
            if (lexer.token() != JSONToken.RBRACE) {
                throw new JSONException("syntax error");
            }
            lexer.nextToken(JSONToken.COMMA);

            return (T) new java.sql.Time(time);
        }

        Object val = parser.parse();

        if (val == null) {
            return null;
        }

        if (val instanceof java.sql.Time) {
            return (T) val;
        } else if (val instanceof Number) {
            return (T) new java.sql.Time(((Number) val).longValue());
        } else if (val instanceof String) {
            String strVal = (String) val;
            if (strVal.length() == 0) {
                return null;
            }

            long longVal;
            JSONScanner dateLexer = new JSONScanner(strVal);
            if (dateLexer.scanISO8601DateIfMatch()) {
                longVal = dateLexer.getCalendar().getTimeInMillis();
            } else if (strVal.contains(":")) { // 新添加的代码 适应 格式为 08:00:00 的时间值
                dateLexer.close();
                return (T) Clock.when(strVal, DateFormatPool.HH_MM_SS).toSqlTime();
            } else {
                longVal = Long.parseLong(strVal);
            }
            dateLexer.close();
            return (T) new java.sql.Time(longVal);
        }

        throw new JSONException("parse error");
    }

    public int getFastMatchToken() {
        return JSONToken.LITERAL_INT;
    }
}