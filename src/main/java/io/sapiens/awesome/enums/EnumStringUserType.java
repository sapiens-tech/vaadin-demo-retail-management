package com.redcross.infra.enums;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class EnumStringUserType implements UserType, ParameterizedType {

  private static final int[] SQL_TYPES = new int[] {Hibernate.STRING.sqlType()};

  private Class<? extends StringEnum> enumClass;

  @Override
  public Object deepCopy(Object value) {
    return value;
  }

  @Override
  public boolean equals(Object x, Object y) {
    if (x == y) {
      return true;
    } else if (x == null) {
      return false;
    } else {
      return x.equals(y);
    }
  }

  @Override
  public boolean isMutable() {
    return false;
  }

  @Override
  public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws SQLException {
    String value = rs.getString(names[0]);
    if (rs.wasNull()) {
      return null;
    }

    for (StringEnum e : enumClass.getEnumConstants()) {
      if (e.getValue().equals(value)) {
        return e;
      }
    }

    throw new HibernateException(
        "Value " + value + " is not found in " + enumClass.getCanonicalName());
  }

  @Override
  public void nullSafeSet(PreparedStatement st, Object value, int index) throws SQLException {
    if (value == null) {
      st.setNull(index, Hibernate.STRING.sqlType());
    } else {
      st.setString(index, ((StringEnum) value).getValue());
    }
  }

  @Override
  public Object replace(Object original, Object target, Object owner) {
    return original;
  }

  @Override
  public Class returnedClass() {
    return enumClass;
  }

  @Override
  public int[] sqlTypes() {
    return SQL_TYPES;
  }

  @Override
  public void setParameterValues(Properties parameters) {
    String enumClassName =
        parameters.getProperty("enumStringClassName"); // parameter which holds enumClassName
    try {
      enumClass = Class.forName(enumClassName).asSubclass(StringEnum.class);
    } catch (ClassNotFoundException e) {
      throw new HibernateException("Enum class not found: " + enumClassName, e);
    }

    if (!enumClass.isEnum()) {
      throw new ClassCastException(enumClass.getCanonicalName() + " must be an enum type.");
    }
  }

  @Override
  public Serializable disassemble(Object value) {
    return (Serializable) value;
  }

  @Override
  public Object assemble(Serializable cached, Object owner) {
    return cached;
  }

  @Override
  public int hashCode(Object x) {
    return x.hashCode();
  }
}
