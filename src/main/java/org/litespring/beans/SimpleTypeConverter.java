package org.litespring.beans;

public class SimpleTypeConverter implements TypeConverter {

    public <T> T convertIfNecessary(Object value, Class<T> requireType) throws TypeMismatchException {
        return null;
    }
}
