package cn.limw.summer.hibernate.type;

import java.io.Serializable;

import org.hibernate.dialect.Dialect;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.DiscriminatorType;
import org.hibernate.type.PrimitiveType;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

import cn.limw.summer.hibernate.type.descriptor.java.BooleanTypeDescriptor;

/**
 * @author li
 * @version 1 (2015年1月28日 上午9:43:51)
 * @since Java7
 * @see org.hibernate.type.BooleanType
 */
public class BooleanType extends AbstractSingleColumnStandardBasicType<Boolean> implements PrimitiveType<Boolean>, DiscriminatorType<Boolean> {
    private static final long serialVersionUID = 6646579033869768329L;

    public static final BooleanType INSTANCE = new BooleanType();

    public BooleanType() {
        this(cn.limw.summer.hibernate.type.descriptor.sql.BooleanTypeDescriptor.INSTANCE, BooleanTypeDescriptor.INSTANCE);
    }

    protected BooleanType(SqlTypeDescriptor sqlTypeDescriptor, BooleanTypeDescriptor javaTypeDescriptor) {
        super(sqlTypeDescriptor, javaTypeDescriptor);
    }

    public String getName() {
        return "boolean";
    }

    public String[] getRegistrationKeys() {
        return new String[] { getName(), boolean.class.getName(), Boolean.class.getName() };
    }

    public Class getPrimitiveClass() {
        return boolean.class;
    }

    public Serializable getDefaultValue() {
        return Boolean.FALSE;
    }

    public Boolean stringToObject(String string) {
        return fromString(string);
    }

    public String objectToSQLString(Boolean value, Dialect dialect) {
        return dialect.toBooleanValueString(value);
    }
}