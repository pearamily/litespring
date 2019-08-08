package org.litespring.beans;

import org.litespring.beans.propertyeditors.CustomBooleanEditor;
import org.litespring.beans.propertyeditors.CustomNumberEditor;
import org.litespring.util.ClassUtils;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

public class SimpleTypeConverter implements TypeConverter {

    private Map<Class<?>, PropertyEditor> defaultEditors;


    public SimpleTypeConverter() {
    }


    public <T> T convertIfNecessary(Object value, Class<T> requireType) throws TypeMismatchException {
        if (ClassUtils.isAssignableValue(requireType, value)) {//judge that value can directly set to that type?
            return (T) value;
        } else {
            if (value instanceof String) {
                PropertyEditor editor = findDefaultEditor(requireType);
                try {
                    editor.setAsText((String) value);
                } catch (IllegalArgumentException e) {
                    throw new TypeMismatchException(value, requireType);
                }
                return (T) editor.getValue();
            } else {
                throw new RuntimeException("Todo: cant convert value for" + value + " class " + requireType);
            }
        }

    }

    private  PropertyEditor findDefaultEditor(Class<?> requireType) {
        PropertyEditor editor = this.getDefaultEditor(requireType);
        if (editor == null) {
            throw new RuntimeException("Editor for " + requireType + "  has not be implemented");

        }
        return editor;

    }

    private PropertyEditor getDefaultEditor(Class<?> requireType) {

        if (this.defaultEditors == null) {
            createDefaultEditors();
        }

        return this.defaultEditors.get(requireType);
    }

    private void createDefaultEditors() {

        this.defaultEditors = new HashMap<Class<?>, PropertyEditor>(64);

        this.defaultEditors.put(boolean.class, new CustomBooleanEditor(false));
        this.defaultEditors.put(Boolean.class, new CustomBooleanEditor(true));


        this.defaultEditors.put(int.class, new CustomNumberEditor(Integer.class,false));
        this.defaultEditors.put(Integer.class, new CustomNumberEditor(Integer.class,true));
    }


}
