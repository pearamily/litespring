package org.litespring.beans;

public class TypeMismatchException extends  Exception {

    private transient Object value;

    private Class<?> requiredType;

    public TypeMismatchException(Object value, Class<?> requiredType) {

        super("Failed to convert value :" + value + " to type" + requiredType);
        this.requiredType = requiredType;
        this.value = value;

    }

    public Object getValue() {
        return value;
    }

    public Class<?> getRequiredType() {
        return requiredType;
    }
}
