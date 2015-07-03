package cn.limw.summer.hibernate.type.descriptor.sql;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicBinder;
import org.hibernate.type.descriptor.sql.BasicExtractor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptorRegistry;

/**
 * @author li
 * @version 1 (2015年1月28日 上午9:46:50)
 * @since Java7
 * @see org.hibernate.type.descriptor.sql.BooleanTypeDescriptor
 */
public class BooleanTypeDescriptor implements SqlTypeDescriptor {
    private static final long serialVersionUID = -1512013596619705434L;

    public static final BooleanTypeDescriptor INSTANCE = new BooleanTypeDescriptor();

    public BooleanTypeDescriptor() {
        SqlTypeDescriptorRegistry.INSTANCE.addDescriptor(this);
    }

    public int getSqlType() {
        return Types.BOOLEAN;
    }

    public boolean canBeRemapped() {
        return true;
    }

    public <X> ValueBinder<X> getBinder(final JavaTypeDescriptor<X> javaTypeDescriptor) {
        return new BasicBinder<X>(javaTypeDescriptor, this) {
            protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options) throws SQLException {
                st.setBoolean(index, javaTypeDescriptor.unwrap(value, Boolean.class, options));
            }
        };
    }

    public <X> ValueExtractor<X> getExtractor(final JavaTypeDescriptor<X> javaTypeDescriptor) {
        return new BasicExtractor<X>(javaTypeDescriptor, this) {
            protected X doExtract(ResultSet rs, String name, WrapperOptions options) throws SQLException {
                return javaTypeDescriptor.wrap(rs.getBoolean(name), options);
            }

            protected X doExtract(CallableStatement statement, int index, WrapperOptions options) throws SQLException {
                return javaTypeDescriptor.wrap(statement.getBoolean(index), options);
            }

            protected X doExtract(CallableStatement statement, String name, WrapperOptions options) throws SQLException {
                return javaTypeDescriptor.wrap(statement.getBoolean(name), options);
            }
        };
    }
}